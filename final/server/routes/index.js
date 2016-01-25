var express = require('express');
var router = express.Router();
var cookieParser = require('cookie-parser');
var request = require('request');

/* GET home page. */
router.get('/', function(req, res, next) {
  res.render('index', { title: 'Express' });
});

router.get('/address', function(req, res, next){
  var query = req.query.query;
  var url = encodeURI('https://maps.googleapis.com/maps/api/place/textsearch/json?key=AIzaSyBQvMW8rWdeMEbD4u4QG_y4yRqpmQVgBQU&language=ko&query=' + query);

  request.get(url, function (error, response, body) {
    if (!error) {
      res.json(JSON.parse(body));
    } else {
      res.json({'error': error});
    }
  });
});

module.exports = router;
