var express = require('express');
var signature = require('cookie-signature');
var Shop = require('../models/shop');
var User = require('../models/user');
var router = express.Router();

var ObjectId = require('mongoose').Types.ObjectId;

router.route('/subscribe')
  .post(function(req, res, next) {
    User.findOne({_id: req.session.userId}, function(err, user) {
      if (err) { res.json({'error': err}); }

      else if (user) {
        Shop.findOne({_id: new ObjectId(req.body.shopid)}, function(err, shop) {
          if (err) { res.json({'error': err}); }
          else if (shop) {
            user.subscribe(shop, console.log);
            res.json({'ok': true});
          } else {
            res.json({'ok': false, 'reason': 'no such shop'});
          }
        });
      } else {
        res.json({'ok': false, 'reason': 'not logged in'});
      }
    });
  });

router.route('/unsubscribe')
  .post(function(req, res, next) {
    User.findOne({_id: req.session.userId}, function(err, user) {
      if (err) { res.json({'error': err}); }

      else if (user) {
        Shop.findOne({_id: new ObjectId(req.body.shopid)}, function(err, shop) {
          if (err) { res.json({'error': err}); }
          else if (shop) {
            user.unsubscribe(shop, console.log);
            res.json({'ok': true});
          } else {
            res.json({'ok': false, 'reason': 'no such shop'});
          }
        });
      } else {
        res.json({'ok': false, 'reason': 'not logged in'});
      }
    });
  });

router.route('/posts')
  .get(function(req, res, next) {
    User.findOne({_id: req.session.userId}, function(err, user) {
      if (err) { res.json({'error': err}); }
      else if (!user) { res.json({'ok': false, 'reason': 'not logged in as an user'}); }
      else {
        user.getPosts(function (err, posts) {
          console.log(posts);
          if (err) { res.json({'error': err}); }
          else { res.json({'ok': true, 'posts': posts}); }
        });
      }
    });
  });

router.route('/shops')
  .get(function(req, res, next) {
    User.findOne({_id: req.session.userId}, function(err, user) {
      if (err) { res.json({'error': err}); }
      else if (!user) { res.json({'ok': false, 'reason': 'not logged in as an user'}); }
      else {
        user.getShops(function (err, shops) {
          if (err) { res.json({'error': err}); }
          else { console.log(shops); res.json({'ok': true, 'shops': shops}); }
        });
      }
    });
  });

router.route('/signin')
  .get(function(req, res, next) {
    res.render('users/signin', {title: 'Sign In' });
  })
  .post(function(req, res, next) {
    console.log(req.body)
    User.findOne({ 'email': req.body.email},
      function (err, user) {
        if (err) { // Error
          console.log(err);
          res.json({'error': err});
        } else if (user) {
          user.comparePassword(req.body.password, function(err, isMatch) {
            if (err) {
              console.log(err);
              res.json({'error': err});
            } else if (isMatch) { // Login success
              req.session.userId = user.id;
              res.json(
                {'ok': true,
                 'connect.sid': 's:' + signature.sign(req.session.id, 'supersecret')
                });
            } else { // Login failure
              console.log('casea');
              res.json({'ok': false, 'reason': 'invalid password'});
            }
          });
        } else {
          console.log('caseb');
          res.json({'ok': false, 'reason': 'no such user'});
        }
      });
  });

router.route('/signout')
  .get(function(req, res, next) {
    if (req.session) {
      req.session.destroy(function(err) {
        if (err) { res.json({'error': err}) }
        else { res.json({'ok': true}); }
      });
    } else {
      res.json({'ok': false, 'reason': 'session already expired'});
    }
  });

router.route('/signup')
  .get(function(req, res, next) {
    res.render('users/signup', { title: 'Sign Up' });
  })
  .post(function(req, res, next) {
    var user = new User();

    user.email = req.body.email;
    user.password = req.body.password;

    user.save(function (err) {
        if (err) {
          console.log(err);
          res.json({'error' : err});
        } else {
          res.json({'ok': true});
        }
    });
  });


module.exports = router;
