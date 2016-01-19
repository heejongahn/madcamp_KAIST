var express = require('express');
var Shop = require('../models/shop');
var router = express.Router();

router.route('/signin')
  .get(function(req, res, next) {
    res.render('shop/signin', { title: 'Sign In' });
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
              req.session.user = req.body.accountid;
              console.log('password match');
              res.redirect('/');
            } else {
              console.log('invalid password');
              res.redirect('/shop/signin');
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
    res.render('shop/signup', { title: 'Sign Up' });
  })
  .post(function(req, res, next) {
    var shop = new Shop();

    shop.accountid = req.body.accountid;
    shop.password = req.body.password;
    shop.shopname = req.body.shopname;
    shop.phonenum = req.body.phonenum;

    console.log(shop);

    /*
      'accountid': req.body.accountid,
      'password': req.body.password,
      'shopname': req.body.shopname,
      'phonenum': req.body.phonenum});
    */

    shop.save(function (err) {
        if (err) { res.send({'error' : err}); }
    });

    res.redirect('/shop/signin');
  });


module.exports = router;
