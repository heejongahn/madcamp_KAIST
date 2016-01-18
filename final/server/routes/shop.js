var express = require('express');
var Shop = require('../models/shop');
var router = express.Router();

router.route('/signin')
  .get(function(req, res, next) {
    res.render('account/signin', { title: 'Sign In' });
  })
  .post(function(req, res, next) {
    console.log(req.body.accountid);
    console.log(req.body.password);
    Shop.findOne({accountid: req.body.accountid},
      function (err, shop) {
        if (err) {
          console.log('findOne error');
          res.send({'error': err});
        } else if (shop) {
          console.log(shop);
          shop.comparePassword(req.body.password, function(err, isMatch) {
            if (err) { res.send({'error': err}); }
            else if (isMatch) {
              console.log('password match');
            } else {
              console.log('invalid password');
            }
          });
        }});
    res.render('account/signin', { title: 'Sign In' });
  });

router.route('/signup')
  .get(function(req, res, next) {
    res.render('account/signup', { title: 'Sign Up' });
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

    res.redirect('/account/signin');
  });


module.exports = router;
