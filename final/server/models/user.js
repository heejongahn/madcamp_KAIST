var mongoose = require('mongoose'),
    Schema = mongoose.Schema,
    ObjectId = Schema.Types.ObjectId,
    bcrypt = require('bcrypt'),
    SALT_WORK_FACTOR = 10;

var UserSchema = new Schema({
  email: { type: String, required: true, index: { unique: true } },
  password: { type: String, required: true },
  shopIds: [{type: ObjectId, ref: 'Shop', default: []}]
});

var Shop = require('./shop');

UserSchema.pre('save', function(next) {
    var user = this;

    if (user.isModified('password')) {
      bcrypt.genSalt(SALT_WORK_FACTOR, function(err, salt) {
          if (err) {
            console.log('Error saving password');
            return next(err);
          }

          bcrypt.hash(user.password, salt, function(err, hash) {
              if (err) {
                console.log('Error saving password');
                return next(err);
              }

              user.password = hash;
              next();
          });
      });
    }
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
  this.save(function(err) { if (err) { console.log(err);} });
  shop.userIds.push(this.id);
  shop.save(function(err) { if (err) { console.log(err);} });
};

UserSchema.methods.unsubscribe = function(shop, cb) {
  this.shopIds.splice(this.shopIds.indexOf(shop.id), 1);
  this.save(function(err) { if (err) { console.log(err);} });
  shop.userIds.splice(shop.userIds.indexOf(this.id), 1);
  shop.save(function(err) { if (err) { console.log(err);} });
};

UserSchema.methods.getShops = function(cb) {
  var query = Shop.find({_id: { $in: this.shopIds }});
  query.select('-password');

  query.exec(function (err, shops) {
    if (err) { return cb(err); }
    cb(null, shops);
  });
};


UserSchema.methods.getPosts = function(cb) {
  var posts = [];
  this.getShops(function (err, shops) {
    if (err) { return cb(err); }

    for (i=0; i<shops.length; i++) {
      shops[i].getPosts(function(err, postsOfShop) {
        if (err) { return cb(err); }
        for (j=0; j<postsOfShop.length; j++) {
          posts.push(postsOfShop[j]);
        }
      });
    }
    cb(null, posts);
  });
}

module.exports = mongoose.model('User', UserSchema);
