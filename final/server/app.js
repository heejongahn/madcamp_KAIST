var express = require('express');
var path = require('path');
var favicon = require('serve-favicon');
var logger = require('morgan');
var cookieParser = require('cookie-parser');
var bodyParser = require('body-parser');
var sassMiddleware = require('node-sass-middleware');
var session = require('express-session');
var fileStore = require('session-file-store')(session);

var routes = require('./routes/index');
var users = require('./routes/users');
var shops = require('./routes/shops');

// mongoose (https://github.com/Automattic/mongoose)
var mongoose = require('mongoose');
mongoose.connect('mongodb://localhost/test');
var db = mongoose.connection;
db.on('error', console.error.bind(console, 'connection error:'));

var app = express();

// view engine setup
app.set('views', path.join(__dirname, 'views'));
app.set('view engine', 'jade');

// Sass middleware
app.use(
  sassMiddleware({
    src: path.join(__dirname,'sass'),
    dest: path.join(__dirname, 'public/stylesheets'),
    outputStyle: 'compressed',
    prefix: '/stylesheets',
  }),
  express.static(path.join(__dirname, 'public')));

// Session
app.use(session({
  store: new fileStore(),
  resave: false,
  secret: 'supersecret'}));

app.use(function(req,res,next){
    res.locals.session = req.session;
    next();
});


// uncomment after placing your favicon in /public
//app.use(favicon(path.join(__dirname, 'public', 'favicon.ico')));
app.use(logger('dev'));
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: false }));
app.use(cookieParser());

app.use('/', routes);
app.use('/user/', users);
app.use('/shop/', shops);

// catch 404 and forward to error handler
app.use(function(req, res, next) {
  var err = new Error('Not Found');
  err.status = 404;
  next(err);
});

// error handlers

// development error handler
// will print stacktrace
if (app.get('env') === 'development') {
  app.use(function(err, req, res, next) {
    res.status(err.status || 500);
    res.render('error', {
      message: err.message,
      error: err
    });
  });
}

// production error handler
// no stacktraces leaked to user
app.use(function(err, req, res, next) {
  res.status(err.status || 500);
  res.render('error', {
    message: err.message,
    error: {}
  });
});


module.exports = app;
