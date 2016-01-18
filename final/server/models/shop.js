var mongoose = require('mongoose'),
    Schema = mongoose.Schema,
    ObjectId = Schema.Types.ObjectId,
    bcrypt = require('bcrypt'),
    SALT_WORK_FACTOR = 10;

var shopSchema = new Schema({
  accountid: { type: String, required: true, index: { unique: true } },
  password: { type: String, required: true },
  shopname: { type: String, required: true },
  phonenum: { type: String, required: true },
  location: {
    lon: {type: Number},
    lat: {type: Number},
    required: true},
  userIds: [{type: ObjectId, ref: 'User'}]
});

shopSchema.pre('save', function(next) {
    var shop = this;

    bcrypt.genSalt(SALT_WORK_FACTOR, function(err, salt) {
        if (err) {
          return next(err);
        }

        bcrypt.hash(shop.password, salt, function(err, hash) {
            if (err) {
              return next(err);
            }

            shop.password = hash;
            next();
        });
    });
});


shopSchema.methods.comparePassword = function(candidatePassword, cb) {
    bcrypt.compare(candidatePassword, this.password, function(err, isMatch) {
        if (err) {
          return cb(err);
        }
        cb(null, isMatch);
    });
};

module.exports = mongoose.model('Shop', shopSchema);
