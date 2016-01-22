var express = require('express');
var Shop = require('../models/shop');
var User = require('../models/user');
var router = express.Router();

router.route('/posts')
  .get(function(req, res, next) {
    var shopId = res.query.shopid;

    Shop.findOne({_id: shopId},
      function(err, shop) {
        if (err) { res.json({'error': err}); }
        else if (shop == undefined) { res.json({'ok': false}); }
        else {
          res.json({'ok': true, 'posts': shop.posts});
        }
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
          res.json({'error': err});
        } else if (shop) {
          shop.comparePassword(req.body.password, function(err, isMatch) {
            if (err) { res.json({'error': err}); }
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
        if (err) { res.json({'error' : err}); }
    });

    res.redirect('/shops/signin');
  });


module.exports = router;
