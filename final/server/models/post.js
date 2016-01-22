var mongoose = require('mongoose'),
    Schema = mongoose.Schema,
    ObjectId = Schema.Types.ObjectId,

var PostSchema = new Schema({
  username: { type: String, required: true, index: { unique: true } },
  password: { type: String, required: true },
  shopIds: [{type: ObjectId, ref: 'Shop', default: []}]
});

UserSchema.pre('save', function(next) {
    var user = this;

    bcrypt.genSalt(SALT_WORK_FACTOR, function(err, salt) {
        if (err) {
          return next(err);
        }

        bcrypt.hash(user.password, salt, function(err, hash) {
            if (err) {
              return next(err);
            }

            user.password = hash;
            next();
        });
    });
});


UserSchema.methods.comparePassword = function(candidatePassword, cb) {
    bcrypt.compare(candidatePassword, this.password, function(err, isMatch) {
        if (err) {
          return cb(err);
        }
        cb(null, isMatch);
    });
};

UserSchema.methods.subscribe = function(shop, cb) {
  this.shopIds.push(shop.id);
  this.save();
  shop.userIds.push(this.id);
  shop.save();
};

UserSchema.methods.unsubscribe = function(shop, cb) {
  this.shopIds.splice(this.shopIds.indexOf(shop.id), 1);
  this.save();
  shop.userIds.splice(shop.userIds.indexOf(this.id), 1);
  shop.save();
};

module.exports = mongoose.model('User', UserSchema);
