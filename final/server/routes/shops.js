var express = require('express');
var Shop = require('../models/shop');
var User = require('../models/user');
var router = express.Router();

router.route('/test')
  .get(function(req, res, next) {
    User.findOne(function(err, user) {
      Shop.findOne(function(err, shop) {
        user.subscribe(shop);
        res.redirect('/');
      });
    });
  });

router.route('/signin')
  .get(function(req, res, next) {
    res.render('shops/signin', { title: 'Sign In' });
  })
  .post(function(req, res, next) {
    Shop.findOne({accountid: req.body.accountid},
      function (err, shop) {
        if (err) {
          res.send({'error': err});
        } else if (shop) {
          shop.comparePassword(req.body.password, function(err, isMatch) {
            if (err) { res.send({'error': err}); }
            else if (isMatch) {
              req.session.user = shop;
              res.redirect('/');
            } else {
              res.redirect('/shops/signin');
            }
          });
        }
      });
  });

router.route('/signout')
  .get(function(req, res, next) {
    req.session.destroy(function(err) {
      if (err) { console.log(err) };
      res.redirect('/');
    });
  });

router.route('/signup')
  .get(function(req, res, next) {
    res.render('shops/signup', { title: 'Sign Up' });
  })
  .post(function(req, res, next) {
    var shop = new Shop();

    shop.accountid = req.body.accountid;
    shop.password = req.body.password;
    shop.shopname = req.body.shopname;
    shop.phonenum = req.body.phonenum;

    /*
      'accountid': req.body.accountid,
      'password': req.body.password,
      'shopname': req.body.shopname,
      'phonenum': req.body.phonenum});
    */

    shop.save(function (err) {
        if (err) { res.send({'error' : err}); }
    });

    res.redirect('/shops/signin');
  });


module.exports = router;
