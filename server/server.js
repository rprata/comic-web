// Setup
var express    = require('express');
var bodyParser = require('body-parser');
var app        = express();
var morgan     = require('morgan');
var methodOverride = require('method-override');

app.use(morgan('dev')); // log requests to the console

// configure body parser
app.use(express.static(__dirname + '/public'));
app.use(bodyParser.urlencoded({ extended: true }));
app.use(bodyParser.json());
app.use(bodyParser.json({ type: 'application/vnd.api+json' }));
app.use(methodOverride());


var port = process.env.PORT || 8080; // set our port

var mongoose = require('mongoose');

mongoose.Promise = global.Promise;

mongoose.connect('mongodb://localhost/comics-api');

var Comic = require('./app/models/comic');

// Routes for comic-api
// create router
var router = express.Router();

// middleware to use for all requests
router.use(function(req, res, next) {
	console.log('Request to Comic API.');
	next();
});

// test route (Hello World Test)
router.get('/', function(req, res) {
	res.json({ message: 'Hello World Comic-API' });	
});

// on routes that end in /comics
// ----------------------------------------------------
router.route('/comics')

	// create a comic (accessed at POST http://localhost:8080/comics)
	.post(function(req, res) {
		
		var comic = new Comic();
		comic.title = req.body.title;
		comic.picture_url = req.body.picture_url;
		comic.short_description = req.body.short_description;
		comic.price = req.body.price;
		comic.date_of_publication = req.body.date_of_publication;
		comic.edition_number = req.body.edition_number;

		comic.save(function(err) {
			if (err)
				res.send(err);
			res.json({ message: 'Comic was created!' });
		});

		
	})

	// get all the comics (accessed at GET http://localhost:8080/api/comics)
	.get(function(req, res) {
		Comic.find(function(err, comics) {
			if (err)
				res.send(err);

			res.json(comics);
		});
	});

router.route('/comics/:comic_id')

	// get comic by id (GET /comics/:comic_id)
	.get(function(req, res) {
		Comic.findById(req.params.comic_id, function(err, comic) {
			if (err)
				res.send(err);
			res.json(comic);
		});
	})

	// update
	.put(function(req, res) {
		Comic.findById(req.params.comic_id, function(err, comic) {

			if (err)
				res.send(err);

			comic.title = req.body.title;
			comic.picture_url = req.body.picture_url;
			comic.short_description = req.body.short_description;
			comic.price = req.body.price;
			comic.date_of_publication = req.body.date_of_publication;
			comic.edition_number = req.body.edition_number;

			comic.save(function(err) {
				if (err)
					res.send(err);

				res.json({ message: 'Comic updated!' });
			});

		});
	})

	// delete the comic by id
	.delete(function(req, res) {
		Comic.remove({
			_id: req.params.comic_id
		}, function(err, comic) {
			if (err)
				res.send(err);

			res.json({ message: 'Successfully deleted' });
		});
	});

	// application web
	app.get('/index', function(req, res) {
		res.sendfile('./public/index.html'); // load the single view file (angular will handle the page changes on the front-end)
	});


app.use('/api', router);

// Start Comic-api Server
app.listen(port);
console.log('Comic-api server starts on: ' + port);
