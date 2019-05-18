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
	console.log(uname);
	console.log(pword);
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
            $('.loginvalid').css('display','block');
            $('#userDisplay').text("Welcome, " + returnUser.username);
            $('#register').css('display','none');
            $('#login').css('display','none');
            $('#registerLabel').css('display','none');
            $('#loginLabel').css('display','none');
            $('#logoutLabel').css('display','block');
        },
        error: function() {
            $('#loginError').css('display','block');
        }
    });
});

$('#configSubmit').click(function(event) {
    var goalDistricts = $('#numDistricts').val();
    var minorityMajority = $('#majorityMinority').val();
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
            minorityMajority: minorityMajority,
            majorityMinorityDemographic: targetDemo,
            numGoalDistricts: numGoalDistricts,
            polsbyPopper: polsbyPopper,
            edgeCut: edgeCut,
            efficencyGap: efficencyGap,
            meanMedian: meanMedian,
            populationDeviation: populationDeviation,
            population: population
        }),
        contentType: 'application/json',
        success: function (returnValue) {
            console.log(returnValue);
        },
        error: function() {
            console.log("Error");
        }
    });
});

$('#majorityMinority').change(function(event) {
    var value = $('#majorityMinority').val();
    $('#majMinTicks').text(": " + value + " %");
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