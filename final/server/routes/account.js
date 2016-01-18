var express = require('express');
var router = express.Router();

router.route('/signin')
  .get(function(req, res, next) {
    res.render('signin', { title: 'Sign In' });
  })
  .post(function(req, res, next) {
    console.log(req.body.accountid);
    console.log(req.body.password);
    res.render('signin', { title: 'Sign In' });
  });

module.exports = router;
