$("#batchFieldSelectValue").slider({});
$("#batchMajorityMinorityMin").slider({});
$("#batchMajorityMinorityMax").slider({});
$("#batchConvexHull").slider({});
$("#batchPolsbyPopper").slider({});
$("#batchEdgeCut").slider({});
$("#batchEfficencyGap").slider({});
$("#batchMeanMedian").slider({});
$("#batchPopulationDeviation").slider({});

$('#batchFieldSelect').change(function(event) {
    var value = $('#batchFieldSelect').val();
    console.log(value);
});

$('#batchFieldSelectValue').change(function(event) {
    var value = $('#batchFieldSelectValue').val();
    $('#batchFieldSelectTicks').text(": " + value + " %");
});

$('#batchMajorityMinorityMin').change(function(event) {
    var value = $('#batchMajorityMinorityMin').val();
    $('#batchMajMinTicks').text(": " + value + " %");
});

$('#batchMajorityMinorityMax').change(function(event) {
    var value = $('#batchMajorityMinorityMax').val();
    $('#batchMajMaxTicks').text(": " + value + " %");
});

$('#batchConvexHull').change(function(event) {
    var value = $('#batchConvexHull').val();
    $('#batchConvexHullTicks').text(": " + value + " %");
});

$('#batchPolsbyPopper').change(function(event) {
    var value = $('#batchPolsbyPopper').val();
    $('#batchPolsbyPopperTicks').text(": " + value + " %");
});

$('#batchEdgeCut').change(function(event) {
    var value = $('#batchEdgeCut').val();
    $('#batchEdgeCutTicks').text(": " + value + " %");
});

$('#batchEfficencyGap').change(function(event) {
    var value = $('#batchEfficencyGap').val();
    $('#batchEffGapTicks').text(": " + value + " %");
});

$('#batchMeanMedian').change(function(event) {
    var value = $('#batchMeanMedian').val();
    $('#batchMeanMedianTicks').text(": " + value + " %");
});

$('#batchPopulationDeviation').change(function(event) {
    var value = $('#batchPopulationDeviation').val();
    $('#batchPopDevTicks').text(": " + value + " %");
});

$('#batchSubmit').click(function(event) {
    var batchFieldSelect = $('#batchFieldSelect').val();
    var batchFieldSelectValue = $('#batchFieldSelectValue').val();
    var numRuns = $('#numRuns').val();
    var batchNumDistricts = $('#batchNumDistricts').val();
    var batchNumGoalDistrictsMinMax = $('#batchNumGoalDistrictsMinMax').val();
    var batchMajorityMinorityMin = $('#batchMajorityMinorityMin').val();
    var batchMajorityMinorityMax = $('#batchMajorityMinorityMax').val();
    var batchConvexHull = $('#batchConvexHull').val();
    var batchtargetDemo = $('#batchtargetDemo').val();
    var batchPolsbyPopper = $('#batchPolsbyPopper').val();
    var batchEdgeCut = $('#batchEdgeCut').val();
    var batchEfficencyGap = $('#batchEfficencyGap').val();
    var batchMeanMedian = $('#batchMeanMedian').val();
    var batchPopulationDeviation = $('#batchPopulationDeviation').val();

    $.ajax({
        type: 'POST',
        url: '/batchRoute',
        data: JSON.stringify({
            stateid: stateID,
            batchFieldSelect: batchFieldSelect,
            batchFieldSelectValue: batchFieldSelectValue,
            numRuns: numRuns,
            batchNumDistricts: batchNumDistricts,
            batchNumGoalDistrictsMinMax: batchNumGoalDistrictsMinMax,
            batchMajorityMinorityMin: batchMajorityMinorityMin,
            batchMajorityMinorityMax: batchMajorityMinorityMax,
            batchConvexHull: batchConvexHull,
            batchtargetDemo: batchtargetDemo,
            batchPolsbyPopper: batchPolsbyPopper,
            batchEdgeCut: batchEdgeCut,
            batchEfficencyGap: batchEfficencyGap,
            batchMeanMedian: batchMeanMedian,
            batchPopulationDeviation: batchPopulationDeviation,
        }),
        contentType: 'application/json',
        success: runBatch(),
        error: function() {
            console.log("Error");
        }
    });
});

function runBatch(){
    // Run the batch
}