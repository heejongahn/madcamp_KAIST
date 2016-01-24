var express = require('express');
var Shop = require('../models/shop');
var User = require('../models/user');
var Post = require('../models/post');
var router = express.Router();
var multer = require('multer');
var storage = multer.diskStorage({
  destination: function (req, file, cb) {
    cb(null, './public/uploads')
  },
  filename: function (req, file, cb) {
    cb(null, Date.now() + file.originalname);
  }
});

var upload = multer({ storage: storage });

var ObjectId = require('mongoose').Types.ObjectId;

// 특정 상점의 포스트
router.route('/posts')
  .get(function(req, res, next) {
    var shopId = ObjectId(req.query.shopid);

    Shop.findOne({_id: shopId},
      function(err, shop) {
        if (err) { res.json({'error': err}); } // error
        else if (shop) {
          shop.getPosts(function (err, posts) {
            if (err) { res.send({'error': err}); }
            else { res.json({'ok': true, 'posts': posts}); } // ok
          });
        } else { res.json({'ok': false, 'reason': 'no such shop'}); } // no such shop
      });
  });

// 내 상점의 포스트
router.route('/feed')
  .get(function(req, res, next) {
    Shop.findOne({_id: req.session.shopId}, function(err, shop) {
      if (err) { res.send({'error': err}); }
      else if (shop) {
        shop.getPosts(function (err, posts) {
          if (err) { res.send({'error': err}); }
          else { res.render('shops/feed', { posts: posts }); }
        });
      } else { res.json({'ok': false, 'reason': 'not logged in as a shop'}); }
    });
  })
  .post(function(req, res, next) {
    Shop.findOne({_id: req.session.shopId}, function(err, shop) {
      if (err) { res.send({'error': err}); }

      else if (shop) {
        var body = req.body.body;

        var post = new Post();
        post.body = body;
        post.date = new Date;
        post.shopId = shop._id;

        post.save(function(err) {
          if (err) { res.send({'error': err}); }
          else {res.redirect('/shop/feed/'); }
        });
      }
      else { res.json({'ok': false, 'reason': 'not logged in as a shop'}); }
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
              req.session.shopId = shop.id;
              res.redirect('/');
            } else {
              res.redirect('/shop/signin');
            }
          });
        } else {
          res.json({'ok': false, 'reason': 'no such shop'});
        }
      });
  });

router.route('/signout')
  .get(function(req, res, next) {
    if (req.session) {
      req.session.destroy(function(err) {
        if (err) { res.json({'error': err}) }
        else { res.redirect('/'); }
      });
    } else {
      res.json({'ok': false, 'reason': 'session already expired'});
    }
  });

router.route('/signup')
  .get(function(req, res, next) {
    res.render('shops/signup', { title: 'Sign Up' });
  })
  .post(upload.single('photo'), function(req, res, next) {
    var shop = new Shop();
    console.log(req.body);
    console.log(req.file);

    shop.accountid = req.body.accountid;
    shop.password = req.body.password;
    shop.shopname = req.body.shopname;
    shop.phonenum = req.body.phonenum;
    shop.photo = req.file.filename;

    /*
      'accountid': req.body.accountid,
      'password': req.body.password,
      'shopname': req.body.shopname,
      'phonenum': req.body.phonenum});
    */

    shop.save(function (err) {
        if (err) { res.json({'error' : err}); }
        else { res.redirect('/shop/signin'); }
    });
  });

router.route('/mypage')
  .get(function(req, res, next) {
    Shop.findOne({_id: req.session.shopId}, function(err, shop) {
      if (err) { return res.json({'error': err}); }
      res.render('shops/mypage', { title: 'My page', shop: shop});
    });
  })
  .post(upload.single('photo'), function(req, res, next) {
    Shop.findOne({_id: req.session.shopId}, function(err, shop) {
      if (err) { return res.json({'error': err}); }
      else {
        if (req.body.password) {
          shop.password = req.body.password;
        }
        shop.shopname = req.body.shopname;
        shop.phonenum = req.body.phonenum;
        if (req.file) {
          shop.photo = req.file.filename;
        }

        shop.save(function (err) {
            if (err) { res.json({'error' : err}); }
            else { res.redirect('/shop/signin'); }
        });
      }
    });
  });

module.exports = router;
