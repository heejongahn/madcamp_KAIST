var express = require('express');
var router = express.Router();
var cookieParser = require('cookie-parser');
var signature = require('cookie-signature');

/* GET home page. */
router.get('/', function(req, res, next) {
  res.render('index', { title: 'Express' });
});

module.exports = router;
