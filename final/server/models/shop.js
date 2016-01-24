var mongoose = require('mongoose'),
    Schema = mongoose.Schema,
    ObjectId = Schema.Types.ObjectId,
    bcrypt = require('bcrypt'),
    SALT_WORK_FACTOR = 10;

var PostSchema = new Schema({
  date: {type: Date, required: true},
  body: {type: String, required: true}
});

var ShopSchema = new Schema({
  accountid: { type: String, required: true, index: { unique: true } },
  password: { type: String, required: true },
  shopname: { type: String, required: true },
  phonenum: { type: String, required: true },
  photo: { type: String },
  /*
  location: {
    lon: {type: Number},
    lat: {type: Number},
    required: true},
  */
  userIds: [{type: ObjectId, ref: 'User', default: []}],
  posts: [PostSchema]
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

module.exports = mongoose.model('Shop', ShopSchema);
