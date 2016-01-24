var mongoose = require('mongoose'),
    Schema = mongoose.Schema,
    ObjectId = Schema.Types.ObjectId;

var PostSchema = new Schema({
  date: {type: Date, required: true},
  body: {type: String, required: true},
  shopId: {type: ObjectId, ref: 'Shop', required: true}
});

module.exports = mongoose.model('Post', PostSchema);
