$('#delete').css('display','none');

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
        dataType: 'json',
        data: JSON.stringify({
            username: uname
        }),
        contentType: 'application/json',
        success: function () {
            $('#deleteSuccess').css('display','block');
        },
        error: function () {
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

    $.ajax({
        type: 'POST',
        url: '/map/runAlgorithm',
        dataType: 'json',
        data: JSON.stringify({
            stateID: stateID,
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
        success: function (returnValue) {
            layerGroup.removeLayer(mn_precincts_layer);
            console.log(returnValue);
        },
        error: function() {
            layerGroup.removeLayer(mn_precincts_layer);
            console.log("Error");
        }
    });
});

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