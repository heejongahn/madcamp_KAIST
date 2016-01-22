var express = require('express');
var signature = require('cookie-signature');
var Shop = require('../models/shop');
var User = require('../models/user');
var router = express.Router();

router.route('/subscribe')
  .post(function(req, res, next) {
    var user = req.session.user;
    Shop.findOne({_id: req.body.shopid}, function(err, shop) {
      if (err) { res.json({'error': err}); }
      user.subscribe(shop);
      res.json({'ok': true});
    });
  });

router.route('/unsubscribe')
  .post(function(req, res, next) {
    var user = req.session.user;
    Shop.findOne({_id: req.body.shopid}, function(err, shop) {
      if (err) { res.json({'error': err}); }
      user.unsubscribe(shop);
      res.json({'ok': true});
    });
  });

router.route('/posts')
  .get(function(req, res, next) {
    var user = req.session.user;
    user.getPosts(function (err, posts) {
      if (err) { res.json({'error': err}); }
      else { res.json({'ok': true, 'posts': posts}); }
    });
  });

router.route('/shops')
  .get(function(req, res, next) {
    var user = req.session.user;
    user.getShops(function (err, shops) {
      if (err) { res.json({'error': err}); }
      else { res.json({'ok': true, 'shops': shops}); }
    });
  });

router.route('/signin')
  .post(function(req, res, next) {
    User.findOne({username: req.body.username},
      function (err, user) {
        if (err) { // Error
          res.json({'error': err});
        } else if (user) {
          user.comparePassword(req.body.password, function(err, isMatch) {
            if (err) { res.json({'error': err}); }
            else if (isMatch) { // Login success
              req.session.user = user;
              res.json(
                {'ok': true,
                 'connect.sid': 's:' + signature.sign(req.session.id, 'supersecret')
                });
            } else { // Login failure
              res.json({'ok': false});
            }
          });
        }
      });
  });

router.route('/signout')
  .get(function(req, res, next) {
    req.session.destroy(function(err) {
      if (err) { res.json({'error': err}) };
      res.json({'ok': true});
    });
  });

router.route('/signup')
  .get(function(req, res, next) {
    res.render('shop/signup', { title: 'Sign Up' });
  })
  .post(function(req, res, next) {
    var user = new User();

    user.username = req.body.username;
    user.password = req.body.password;

    user.save(function (err) {
        if (err) { res.json({'error' : err}); }
    });

    res.json({'ok': true});
  });


module.exports = router;
