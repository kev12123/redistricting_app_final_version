package com.appRestful.api.utility;

public class Utility {
	
	  // For Cluster joining in Candidate Pair array in State Class.
    public static int source = 0;
    public static int destination = 1;
    public static int pair_size = 2;
    public static double targetClusterPercentageMerged = .8;
    public static int noPairsFound = 0;


    //Error Messages
    public static String noDataExceptionMessage = "There is no recorded data for this object";
    public static String notInitializedExceptionMessage = "The Algorithm has not been Initialized";
    public static String dataAlreadyPresentExceptionMessage = "This Object Already Has Data";
    public static String districtGoalOutOfBounds = "The goal number of districts is greater than or equal to the number of districts present";
    public static String InvalidDistrictException = "All clusters used in the algorithm must be districts";

    //Weights
    public static final double FULL_WEIGHT = 1.0;
    public static final double HALF_WEIGHT = 0.5;
    public static final double NO_WEIGHT = 0.0;
    public static final boolean NOT_WEIGHTED = false;

    //Ratings
    public static final double MAX_RATING = 2;
    public static final double MAX_PERCENTAGE = 2;
    public static final double TARGET_POPULATION = 10000;


    // for statistics
    public static final String EFFICIENCY_GAP_VALUE = "efficiencyGapValue";
    public static final String STEPHAMCGEE_VALUE = "stephaMcGeeValue";
    public static final String PAULSBY_POPPER_VALUE = "paulsbyPoperValue";
    public static final String SEATS_BY_PARTY = "seatsByParty";
    public static final String OBJECTIVE_FUNCTION = "objectiveFunction";

}
