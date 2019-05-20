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
    public static String invalidDistrictException = "All clusters used in the algorithm must be districts";

    //Weights
    public static double fullWeight = 1.0;
    public static double halfWeight = 0.5;
    public static double noWeight = 0.0;
    public static boolean notWeighted = false;

    //Ratings
    public static double MAX_RATING = 2;
    public static double MAX_PERCENTAGE = 2;
    public static double TARGET_POPULATION = 10000;


    // for statistics
    public static String EFFICIENCY_GAP_VALUE = "efficiencyGapValue";
    public static String STEPHAMCGEE_VALUE = "stephaMcGeeValue";
    public static String PAULSBY_POPPER_VALUE = "paulsbyPoperValue";
    public static String SEATS_BY_PARTY = "seatsByParty";
    public static String OBJECTIVE_FUNCTION = "objectiveFunction";

    //for Move
   public static double noChange = 0;

   //for phase two increment
    public static int initialIteration = 0;
    public static int nextIteration = 1;
    public static int skipIterations = 10;
    public static int iterationQuantity = 1000;

    //For Border Comparisons
    public static boolean isOnBorder = true;
    public static boolean isNotOnBorder = false;
    public static boolean borderInitialized = true;
    public static int borderExpand = 1;
    public static int borderContract = -1;
    public static int enclosed = 0;
    
    //For phase response
    public static String phaseOneResponse = "PHASE_ONE";
    public static String phaseTwoResponse = "PHASE_TWO";
}

