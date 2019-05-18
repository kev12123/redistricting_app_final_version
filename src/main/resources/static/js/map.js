var map = L.map('map', { zoomControl: false }).setView([ 39.381266, -97.922211 ], 5);
L.tileLayer(
	'https://api.tiles.mapbox.com/v4/{id}/{z}/{x}/{y}.png?access_token=pk.eyJ1IjoicGhhc2VvbiIsImEiOiJjanNjNzRlcTQwaTE0NGJtcDkxYXg0bTMzIn0.4lqy-XjXZOmpNHAolv8I1w',
	{
		attribution:
			'Map data &copy; <a href="https://www.openstreetmap.org/">OpenStreetMap</a> contributors, <a href="https://creativecommons.org/licenses/by-sa/2.0/">CC-BY-SA</a>, Imagery © <a href="https://www.mapbox.com/">Mapbox</a>',
		maxZoom: 18,
		id: 'mapbox.light',
		accessToken: 'your.mapbox.access.token'
	}
).addTo(map);

// control that shows state info on hover
var info = L.control();

function getRandomColor() {
	var letters = '0123456789ABCDEF';
	var color = '#';
	for (var i = 0; i < 6; i++) {
	  color += letters[Math.floor(Math.random() * 16)];
	}
	return color;
  }

var color1 = getRandomColor();
var color2 = getRandomColor();
var color3 = getRandomColor();
var color4 = getRandomColor();
var color5 = getRandomColor();
var color6 = getRandomColor();
var color7 = getRandomColor();
var color8 = getRandomColor();
// get color depending on population density value
function getColor(district) {
	return district > 7
		? color1
		: district > 6
			? color2
			: district > 5
				? color3
				: district > 4 ? color4 : district > 3 ? color5 : district > 2 ? color6 : district > 1 ? color7 : color8;
}

function style(feature) {
	return {
		weight: 2,
		opacity: 1,
		color: 'white',
		dashArray: '3',
		fillOpacity: 0.7,
		fillColor: getColor(feature.properties.CongDist)
	};
}

function highlightFeature(e) {
	var layer = e.target;

	layer.setStyle({
		weight: 5,
		color: '#666',
		dashArray: '',
		fillOpacity: 0.7
	});

	if (!L.Browser.ie && !L.Browser.opera && !L.Browser.edge) {
		layer.bringToFront();
	}

	info.update(layer.feature.properties);
}

var geojson;

function resetHighlight(e) {
	geojson.resetStyle(e.target);
	info.update();
}

function zoomToFeature(e) {
	map.fitBounds(e.target.getBounds());
}

function onEachFeature(feature, layer) {
	layer.on({
		mouseover: highlightFeature,
		mouseout: resetHighlight,
		click: zoomToFeature
	});
}


// Default is to show borders of states
L.geoJson(mn_geojson).addTo(map);
L.geoJson(fl_geojson).addTo(map);
L.geoJson(md_geojson).addTo(map);

function findMinnesota() {
	map.setView([ 46.39241, -94.63623 ], 7);
	geojson = L.geoJson(mn_geojson, {
		style: style,
		onEachFeature: onEachFeature
	}).addTo(map);
}

function findFlorida() {
	map.setView([ 27.994402, -81.760254 ], 7);
	
}

function findMaryland() {
	map.setView([ 39.045753, -76.641273 ], 7);
}

$('#registerSubmit').click(function(event){
	event.preventDefault();
	var email = $('#emailRegister').val();
	var uname = $('#usernameRegister').val();
	var pword = $('#passwordRegister').val();
	console.log(email);
	console.log(uname);
	console.log(pword);
	$.ajax({
			type: 'POST',
			url: '/users/adduser',
			dataType: 'json',
			data: JSON.stringify({
				email: email,
				username: uname,
				password: pword
			}),
			contentType: 'application/json',
			success: $(".onloginvalid").show()
		});
});