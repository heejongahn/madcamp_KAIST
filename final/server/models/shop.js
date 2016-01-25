var mongoose = require('mongoose'),
    Schema = mongoose.Schema,
    ObjectId = Schema.Types.ObjectId,
    bcrypt = require('bcrypt'),
    SALT_WORK_FACTOR = 10;

var Post = require('./post');

var ShopSchema = new Schema({
  accountid: { type: String, required: true, index: { unique: true } },
  password: { type: String, required: true },
  shopname: { type: String, required: true },
  phonenum: { type: String, required: true },
  photo: { type: String },
  location: {
    lon: {type: Number, required: true},
    lat: {type: Number, required: true},
    address: {type: String, required: true},
    },
  userIds: [{type: ObjectId, ref: 'User', default: []}],
});

ShopSchema.pre('save', function(next) {
    var shop = this;

    bcrypt.genSalt(SALT_WORK_FACTOR, function(err, salt) {
        if (err) {
          console.log('Error saving password');
          return next(err);
        }

        bcrypt.hash(shop.password, salt, function(err, hash) {
            if (err) {
              console.log('Error saving password');
              return next(err);
            }

            shop.password = hash;
            next();
        });
    });
});

ShopSchema.methods.comparePassword = function(candidatePassword, cb) {
    bcrypt.compare(candidatePassword, this.password, function(err, isMatch) {
        if (err) {
          return cb(err);
        }
        cb(null, isMatch);
    });
};

ShopSchema.methods.getPosts = function(cb) {
  Post.find({ shopId: this._id }, function(err, posts) {
    if (err) { return cb(err); }
    cb(null, posts);
  });
};

module.exports = mongoose.model('Shop', ShopSchema);
