var express = require('express');
var signature = require('cookie-signature');
var Shop = require('../models/shop');
var User = require('../models/user');
var router = express.Router();

router.route('/subscribe')
  .post(function(req, res, next) {
    var user = req.session.user;
    Shop.findOne({_id: req.body.shopid}, function(err, shop) {
      user.subscribe(shop);
    });
  });

router.route('/unsubscribe')
  .post(function(req, res, next) {
    var user = req.session.user;
    Shop.findOne({_id: req.body.shopid}, function(err, shop) {
      user.unsubscribe(shop);
    });
  });

router.route('/signin')
  .post(function(req, res, next) {
    User.findOne({username: req.body.username},
      function (err, user) {
        if (err) { // Error
          res.send({'error': err});
        } else if (user) {
          user.comparePassword(req.body.password, function(err, isMatch) {
            if (err) { res.send({'error': err}); }
            else if (isMatch) { // Login success
              req.session.user = user;
              res.send(
                {'ok': true,
                 'connect.sid': 's:' + signature.sign(req.session.id, 'supersecret')
                });
            } else { // Login failure
              res.send({'ok': false});
            }
          });
        }
      });
  });

router.route('/signout')
  .get(function(req, res, next) {
    req.session.destroy(function(err) {
      if (err) { res.send({'error': err}) };
      res.send({'ok': true});
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
        if (err) { res.send({'error' : err}); }
    });

    res.send({'ok': true});
  });


module.exports = router;
