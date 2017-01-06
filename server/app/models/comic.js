var mongoose     = require('mongoose');
var Schema       = mongoose.Schema;

var ComicSchema   = new Schema({
    title: String,
    picture_url: String, 
    short_description: String,
    price: Number,
    date_of_publication: Date,
    edition_number: Number
});

module.exports = mongoose.model('Comic', ComicSchema);