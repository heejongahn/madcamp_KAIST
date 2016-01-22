var express = require('express');
var router = express.Router();
var cookieParser = require('cookie-parser');
var signature = require('cookie-signature');

/* GET home page. */
router.get('/', function(req, res, next) {
  console.log(req.session.id);
  console.log(req.sessionID);
  console.log(signature.sign(req.session.id, 'supersecret'));
  console.log(cookieParser.signedCookie(req.cookies['connect.sid'], 'supersecret'));
  console.log(req.cookies);
  res.render('index', { title: 'Express' });
});

module.exports = router;
