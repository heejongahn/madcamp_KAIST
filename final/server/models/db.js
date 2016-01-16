// mongoose (https://github.com/Automattic/mongoose) setup
var mongoose = require('mongoose');
mongoose.connect('mongodb://localhost/test');
var db = mongoose.connection;
db.on('error', console.error.bind(console, 'connection error:'));

exports.db = db;
