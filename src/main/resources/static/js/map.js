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

	// info.update(layer.feature.properties);
}

var geojson;

function resetHighlight(e) {
	geojson.resetStyle(e.target);
	// info.update();
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
var layerGroup = new L.LayerGroup();
layerGroup.addTo(map);

// --------------------------------------------------------------------------------------------------------------------
// var mn_neighbor_layer = new L.GeoJSON(mn_neighbor_geojson, {
// 	onEachFeature: showNeighbors
// });

// // var mn_neighbor_layer = new L.GeoJSON(mn_neighbor_geojson);
// layerGroup.addLayer(mn_neighbor_layer);

// function addNeighorLayer(e) {
//   // e = event
// 	console.log(e);
// 	// console.log(mn_neighbor_geojson.features[0].features[0].neighbors);
// 	// for (var i = 0; i < (mn_neighbor_geojson.features[0].features[0].neighbors).length; i++) {
// 	// 	console.log(mn_neighbor_geojson.features[0].features[0].neighbors[i]);
// 	// 	var neighbor_geojson = mn_neighbor_geojson.features[0].features[0].neighbors[i];
// 	// 	var neighbor_layer = new L.GeoJSON(neighbor_geojson);
// 	// 	layerGroup.addLayer(neighbor_layer);
// 	// 	neighbor_layer.setStyle({fillColor :'red'});
// 	// }
//   // You can make your ajax call declaration here
// 	//$.ajax(...
// }

// function showNeighbors(feature, layer) {
//     //bind click
//     // layer.on({
// 		// 		click: addNeighorLayer
// 		// });
// 		layer.on('click', function(e) {
// 			console.log(e)
// 			console.log("HERE", (feature.neighbors).length);
// 			for (var i = 0; i < (feature.neighbors).length; i++) {
// 				var neighbor_geojson = feature.neighbors[i];
// 				var neighbor_layer = new L.GeoJSON(neighbor_geojson);
// 				layerGroup.addLayer(neighbor_layer);
// 				neighbor_layer.setStyle({fillColor :'red'});
// 			}
// 		})
// }
// --------------------------------------------------------------------------------------------------------------------

var mn_state_layer = new L.GeoJSON(mn_geojson);
var md_state_layer = new L.GeoJSON(md_geojson);
var fl_state_layer = new L.GeoJSON(fl_geojson);

layerGroup.addLayer(mn_state_layer);
layerGroup.addLayer(md_state_layer);
layerGroup.addLayer(fl_state_layer);

var mn_districts_layer = new L.GeoJSON(mn_districts_geojson, {
	onEachFeature: showMNPrecincts
});
var md_districts_layer = new L.GeoJSON(md_districts_geojson, {
	onEachFeature: showMDPrecincts
});
var fl_districts_layer = new L.GeoJSON(fl_districts_geojson, {
	onEachFeature: showFLPrecincts
});

var mn_precincts_layer = new L.GeoJSON(mn_precincts_geojson, {
	onEachFeature: showMNPrecinctInfo
});
var md_precincts_layer = new L.GeoJSON(md_precincts_geojson, {
	onEachFeature: showMDPrecinctInfo
});
var fl_precincts_layer = new L.GeoJSON(fl_precincts_geojson, {
	onEachFeature: showFLPrecinctInfo
});

function addMNPrecinctsLayer(e) {
  // e = event
	console.log(e);
	layerGroup.addLayer(mn_precincts_layer);
  // You can make your ajax call declaration here
  //$.ajax(... 
}

function addMDPrecinctsLayer(e) {
  // e = event
	// console.log(e);
	layerGroup.addLayer(md_precincts_layer);
  // You can make your ajax call declaration here
  //$.ajax(... 
}

function addFLPrecinctsLayer(e) {
  // e = event
	// console.log(e);
	layerGroup.addLayer(fl_precincts_layer);
  // You can make your ajax call declaration here
  //$.ajax(... 
}

function showMNPrecincts(feature, layer) {
    //bind click
    layer.on({
				click: addMNPrecinctsLayer,
		});
}

function showMDPrecincts(feature, layer) {
	//bind click
	layer.on({
			click: addMDPrecinctsLayer
	});
}

function showFLPrecincts(feature, layer) {
	//bind click
	layer.on({
			click: addFLPrecinctsLayer
	});
}

// Popup code
function showMNPrecinctInfo(feature, layer) {
	layer.on('mouseover', function(e) {
		console.log(feature.properties);


		var popup = L.popup()
   	.setLatLng(e.latlng) 
   	.setContent(feature.properties.black_pop)
  	.openOn(map);
	});
}

function showMDPrecinctInfo(feature, layer) {
	layer.on('mouseover', function(e) {
		console.log(feature.properties);


		var popup = L.popup()
   	.setLatLng(e.latlng) 
   	.setContent(feature.properties.black_pop)
  	.openOn(map);
	});
}

function showFLPrecinctInfo(feature, layer) {
	layer.on('mouseover', function(e) {
		console.log(feature.properties);


		var popup = L.popup()
   	.setLatLng(e.latlng) 
   	.setContent(feature.properties.black_pop)
  	.openOn(map);
	});
}

function findMinnesota() {
	map.setView([ 46.39241, -94.63623 ], 7);
	// District boundries 
	layerGroup.addLayer(mn_districts_layer);
	// L.geoJson(mn_districts_geojson).addTo(map);
	map.on('zoomend', function() {
		if (map.getZoom() < 7) {
			layerGroup.removeLayer(mn_districts_layer);
			layerGroup.removeLayer(mn_precincts_layer);
		}
		else if (map.getZoom() == 7) {
			layerGroup.addLayer(mn_districts_layer);
		}
		// L.geoJson(mn_precincts_geojson).addTo(map);
					// geojson = L.geoJson(mn_precincts_geojson, {
			// 	style: style,
			// 	onEachFeature: onEachFeature
			// }).addTo(map);
	});
}

function findFlorida() {
	map.setView([ 27.994402, -81.760254 ], 7);
	// District boundries
	layerGroup.addLayer(fl_districts_layer);
	map.on('zoomend', function() {
		if (map.getZoom() < 7) {
			layerGroup.removeLayer(fl_districts_layer);
			layerGroup.removeLayer(fl_precincts_layer);
		}
		else if (map.getZoom() == 7) {
			layerGroup.addLayer(fl_districts_layer);
		}
		// L.geoJson(mn_precincts_geojson).addTo(map);
					// geojson = L.geoJson(mn_precincts_geojson, {
			// 	style: style,
			// 	onEachFeature: onEachFeature
			// }).addTo(map);
	});
	
}

function findMaryland() {
	map.setView([ 39.045753, -76.641273 ], 7);
	// District boundries
	layerGroup.addLayer(md_districts_layer);
	map.on('zoomend', function() {
		if (map.getZoom() < 7) {
			layerGroup.removeLayer(md_districts_layer);
			layerGroup.removeLayer(md_precincts_layer);
		}
		else if (map.getZoom() == 7) {
			layerGroup.addLayer(md_districts_layer);
		}
		// L.geoJson(mn_precincts_geojson).addTo(map);
					// geojson = L.geoJson(mn_precincts_geojson, {
			// 	style: style,
			// 	onEachFeature: onEachFeature
			// }).addTo(map);
	});
}

// With JQuery
$("#majorityMinority").slider({});
$("#populationDeviation").slider({});
$("#population").slider({});
$("#polsbyPopper").slider({});
$("#edgeCut").slider({});
$("#efficencyGap").slider({});
$("#meanMedian").slider({});