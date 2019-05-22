$('#delete').css('display','none');

$('#registerSubmit').click(function(event){
	event.preventDefault();
	var email = $('#emailRegister').val();
	var uname = $('#usernameRegister').val();
	var pword = $('#passwordRegister').val();
	$.ajax({
        type: 'POST',
        url: '/users/adduser',
        dataType: 'json',
        data: JSON.stringify({
            email: email,
            username: uname,
            passWord: pword
        }),
        contentType: 'application/json',
        success: function (returnUser) {
            console.log(returnUser);
            $('#registerSuccess').css('display','block');
        },
        error: function() {
            $('#registerError').css('display','block');
        }
    });
});

$('#loginSubmit').click(function(event){
	event.preventDefault();
	var uname = $('#usernameLogin').val();
	var pword = $('#passwordLogin').val();
	$.ajax({
        type: 'POST',
        url: '/users/login',
        dataType: 'json',
        data: JSON.stringify({
            username: uname,
            password: pword
        }),
        contentType: 'application/json',
        success: function (returnUser) {
            console.log(returnUser);
            console.log(returnUser.role);
            if (returnUser.role !== "admin") {
                console.log("Test");
                $('.loginvalid').css('display','block');
                $('#userDisplay').text("Welcome, " + returnUser.username);
                $('#register').css('display','none');
                $('#login').css('display','none');
                $('#registerLabel').css('display','none');
                $('#loginLabel').css('display','none');
                $('#logoutLabel').css('display','block');
            }
            else {
                console.log("Admin User");
                $('.loginvalid').css('display','block');
                $('#userDisplay').text("Welcome, " + returnUser.username);
                $('#registerAnchor').text("Create User");
                $('#register').css('display','');
                $('#deleteLabel').css('display','block');
                $('#delete').css('display','');
                $('#loginLabel').css('display','none');
                $('#login').css('display','none');
                $('#logoutLabel').css('display','block');
            }
        },
        error: function() {
            $('#loginError').css('display','block');
        }
    });
});

$('#deleteSubmit').click(function(event) {
    event.preventDefault();
    var uname = $('#deleteInput').val();
    $.ajax({
        type: 'DELETE',
        url: '/users',
        data: JSON.stringify({
            username: uname
        }),
        contentType: 'application/json',
        success: function () {
            $('#deleteSuccess').css('display','block');
        },
        error: function() {
            $('#deleteError').css('display','block');
        }
    })
});

$('#logoutLabel').click(function(event) {
    $('.loginvalid').css('display','none');
    $('#userDisplay').text("Guest User");
    $('#registerLabel').css('display','');
    $('#register').css('display','');
    $('#deleteLabel').css('display','none');
    $('#delete').css('display','none');
    $('#login').css('display','');
    $('#loginLabel').css('display','');
    $('#logoutLabel').css('display','none');
});


// var color0 = getRandomColor();
// var color1 = getRandomColor();
// var color2 = getRandomColor();
// var color3 = getRandomColor();
 
// function getDist(geoID) {
//     for (var i = 0; i < districtID.length; i++) {
//         if (districtID[i].includes(geoID)) {
//             return i;
//         }
//     }
// }

// get color depending on population density value
function getMYColor(features) {
    // console.log(features.properties.GEO_ID);
    // console.log(getDist(features.properties.GEO_ID));
    if (getDist(features.properties.GEO_ID) === 0) {
        return color0;
    } else if (getDist(features.properties.GEO_ID) === 1) {
        return color1;
    } else if (getDist(features.properties.GEO_ID) === 2) {
        return color2;
    } else if (getDist(features.properties.GEO_ID) === 3) {
        return color3;
    }
	// console.log(features.properties.GEO_ID);
}
function colorStyle(features) {
	return {
		weight: 2,
		opacity: 1,
		color: 'white',
		dashArray: '3',
		fillOpacity: 0.7,
		fillColor: getAColor(features.properties.GEO_ID)
		// fillColor: getColor(features.properties.GEO_ID)
	};
}

$('#configSubmit').click(function(event) {
    var goalDistricts = $('#numDistricts').val();
    var majorityMinorityMin = $('#majorityMinorityMin').val();
    var majorityMinorityMax = $('#majorityMinorityMax').val();
    var convexHull = $('#convexHull').val();
    var targetDemo = $('#targetDemo').val();
    var numGoalDistricts = $('#numGoalDistricts').val();
    var polsbyPopper = $('#polsbyPopper').val();
    var edgeCut = $('#edgeCut').val();
    var efficencyGap = $('#efficencyGap').val();
    var meanMedian = $('#meanMedian').val();
    var populationDeviation = $('#populationDeviation').val();
    var population = $('#population').val();

    var fetchData = true;

    $('#loading').css('display','');
    $.ajax({
        type: 'POST',
        url: '/map/runAlgorithm',
        data: JSON.stringify({
            stateid: stateID,
            goalDistricts: goalDistricts,
            majorityMinorityMinPercentage: majorityMinorityMin,
            majorityMinorityMaxPercentage: majorityMinorityMax,
            majorityMinorityDemographic: targetDemo,
            goalMajorityMinorityDistricts: numGoalDistricts,
            convexHull: convexHull,
            polsbyPopper: polsbyPopper,
            edgeCut: edgeCut,
            efficiencyGap: efficencyGap,
            meanMedian: meanMedian,
            allowedPopulationDeviation: populationDeviation,
            population: population
        }),
        contentType: 'application/json',
        success: poll(),
        error: function() {
            console.log("Error");
            return;
        }
    });
});

var geoIDMap = new Map();
var colorMap = new Map();
var geoList = [];
var prescicntSetCount = new Set();


function phase1(data){

}
var timer = 1000;
function poll(){
	setTimeout(function(){
		  $.ajax({ url: "map/getData", success: function(data){
		        //Update your dashboard gauge
		    console.log("In GET request");
            console.log("Recieved data: " + data.districtData);

            if (data.stage == "DONE"){
                console.log("In done");
                timer = 100000000;
                return;
            }

             // console.log(getAColor("1500000US270879401003"));

             // Update color table
             if (data.stage == "PHASE_ONE"){
                //phase1(data);
                //for (var i = 0; i < data.districtData.length; i++){
                    updateColorTable(data.districtData);
                    switch (stateID){
                        case 27:
                            layerGroup.removeLayer(mn_precincts_layer);
                             var mn_precincts_layer_colored = new L.GeoJSON(mn_precincts_geojson, {
                                    style: colorStyle
                            });
                            layerGroup.addLayer(mn_precincts_layer_colored);
                            break;
                        case 24:
                            layerGroup.removeLayer(md_precincts_layer);
                             var md_precincts_layer_colored = new L.GeoJSON(md_precincts_geojson, {
                                    style: colorStyle
                            });
                            layerGroup.addLayer(md_precincts_layer_colored);
                            break;
                        case 12:
                            layerGroup.removeLayer(fl_precincts_layer);
                             var fl_precincts_layer_colored = new L.GeoJSON(fl_precincts_geojson, {
                                    style: colorStyle
                            });
                            layerGroup.addLayer(fl_precincts_layer_colored);
                            break;
                    }
             //   }
             }else{
                console.log("phase one complete");
             }


             //else updateColorTable(data.districtData);

//             var mn_precincts_layer_colored = new L.GeoJSON(mn_precincts_geojson, {
//                 style: colorStyle
//             });

		     console.log("total count " + prescicntSetCount.size);
		 }, dataType: "json" , complete: poll });

	}, timer);

	
}

var thePrecincts = ["270879401003", "270879401003", "270879401001"];
$('#test').click(function(event) {

    // console.log(getAColor("1500000US270879401003"));

    layerGroup.removeLayer(mn_precincts_layer);
    // Update color table
    updateColorTable(thePrecincts);
    var mn_precincts_layer_colored = new L.GeoJSON(mn_precincts_geojson, {
        style: colorStyle
    });
    layerGroup.addLayer(mn_precincts_layer_colored);
});


function updateColorTable(prescinctsToColor) {
    // check if district geoID exists
    console.log("Using chunk >" + prescinctsToColor[0]);
    if (!geoIDMap.has(prescinctsToColor[0])){
        // if not then create a set for  the prescicnts in that distrcits

        var d =  new Set();
        geoIDMap.set(prescinctsToColor[0], d);
        geoList.push(prescinctsToColor[0]);
        // chose a color for the district
        var color = getRandomColor();
        colorMap.set(prescinctsToColor[0], color);
        console.log("adding district >" + prescinctsToColor[0] + " with color-> " + color);
    }

    for (var i = 1; i < prescinctsToColor.length; i++){
        //checkAndRemoveFromOtherDistrict(prescinctsToColor[i])
        //add prescicnt to District
        geoIDMap.get(prescinctsToColor[0]).add(prescinctsToColor[i]);
        prescicntSetCount.add(prescinctsToColor[i]);
    }

}
function checkAndRemoveFromOtherDistrict(prescinct) {
    for(var key in geoIDMap) {
        var set = geoIDMap.get(key);
        if (set.has(prescinct)) {
            set.remove(prescinct);
        }
    }
}

function getAColor(prescicnt) {
    var subStringID = prescicnt.substring(9);
    for (var i = 0; i < geoList.length; i++ ) {
        var set = geoIDMap.get(geoList[i]);
        if (set.has(subStringID)) {
            var color = colorMap.get(geoList[i]);
            console.log("adding color to district " + subStringID + " with color " + color);
            return color;
        }
    }
    return "#FFFFFF"
}



$('#majorityMinorityMin').change(function(event) {
    var value = $('#majorityMinorityMin').val();
    $('#majMinTicks').text(": " + value + " %");
});

$('#majorityMinorityMax').change(function(event) {
    var value = $('#majorityMinorityMax').val();
    $('#majMaxTicks').text(": " + value + " %");
});

$('#convexHull').change(function(event) {
    var value = $('#convexHull').val();
    $('#convexHullTicks').text(": " + value + " %");
});

$('#populationDeviation').change(function(event) {
    var value = $('#populationDeviation').val();
    $('#popDevTicks').text(": " + value + " %");
});

$('#population').change(function(event) {
    var value = $('#population').val();
    $('#popTicks').text(": " + value + " %");
});

$('#polsbyPopper').change(function(event) {
    var value = $('#polsbyPopper').val();
    $('#polsbyPopperTicks').text(": " + value + " %");
});

$('#edgeCut').change(function(event) {
    var value = $('#edgeCut').val();
    $('#edgeCutTicks').text(": " + value + " %");
});

$('#efficencyGap').change(function(event) {
    var value = $('#efficencyGap').val();
    $('#effGapTicks').text(": " + value + " %");
});

$('#meanMedian').change(function(event) {
    var value = $('#meanMedian').val();
    $('#meanMedianTicks').text(": " + value + " %");
});