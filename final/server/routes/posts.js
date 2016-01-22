var express = require('express');
var signature = require('cookie-signature');
var Shop = require('../models/shop');
var User = require('../models/user');
var router = express.Router();

router.route('/')
  .get(function(req, res, next) {
    var user = req.session.user;
    user.getPosts(function (err, posts) {
      if (err) { res.send({'error': err}); }
      else { res.send({'ok': true, 'posts': posts}); }
    });
  });

module.exports = router;
