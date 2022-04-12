package CalorieTrackerFP.calories;

import java.util.HashMap;

public class Calories {

    //constants
    //used to perform conversions
    static final double feetToCMConversion = 30.48;
    static final double inchToCMConversion = 2.54;
    static final double lbsToKgConversion = 2.205;
    static final double cmToMetreConversion = 100;
    /**
     *This function calculates the users daily needed calories based off of their weight and height
     */
    public static int getCalorieTotal(double weight, String userGoal) {
        int calories = 0;
        if(userGoal.equals("maintenance")){
            double caloriesD = lbsToKgConversion * weight * 15;
            calories = (int) caloriesD;
        }else if(userGoal.equals("loss")){
            double caloriesD = (lbsToKgConversion * weight * 15) - 500;
            calories = (int) caloriesD;
        }else if(userGoal.equals("muscle")){
            double caloriesD = (lbsToKgConversion * weight * 15) + 500;
            calories = (int) caloriesD;
        }
        return calories;
    }

    /**
     * Shows the user the amount of calories they have left to consume in the day, different outputs depend on if they
     * are over the goal or still under the goal
     * @return
     */
    public static int calculateRemainingCals(HashMap<String,Integer> foodMap, HashMap<String,Integer> exerciseMap, int calorieTotal) {
        int calorieDifference = getCalorieDifference(foodMap, exerciseMap);
        int remainingCals = getRemainingCals(calorieTotal, calorieDifference);
        // absolute valuing this number lets us more nicely print to the user when the number is a negative value, because
        // the difference in calories (food cals - exercise cals) is greater than the total cals for their goal
        remainingCals = Math.abs(remainingCals);

        return calorieDifference;
//        // using formatting to nicely print the amount of calories that the user has eaten out of how many they need in a day
//        if (calorieDifference < calorieTotal) {
//            System.out.printf("You have consumed %d/%d calories for the day. You are still under the maximum amount " +
//                    "that you should consume (%d calories left to consume)\n", calorieDifference, calorieTotal, remainingCals);
//        } else if (calorieDifference == calorieTotal) {
//            System.out.printf("You have consumed %d/%d calories for the day. You hit your goal exactly! " +
//                    "(%d calories left to consume)\n", calorieDifference, calorieTotal, remainingCals);
//        } else if (calorieDifference > calorieTotal) {
//            System.out.printf("You have consumed %d/%d calories for the day. You are over the maximum amount that " +
//                    "you should be consuming! (%d calories over your goal!)\n", calorieDifference, calorieTotal, Math.abs(remainingCals));
//        }
    }

    public static int getRemainingCals(int calorieTotal, int calorieDifference) {
        int remainingCals = calorieTotal - calorieDifference;
        return remainingCals;
    }

    public static int getCalorieDifference(HashMap<String, Integer> foodMap, HashMap<String, Integer> exerciseMap) {
        int foodCals = 0;
        int exerciseCals = 0;
        for (String key: foodMap.keySet()) {
            int eachFoodCalorieCount = foodMap.get(key);
            foodCals += eachFoodCalorieCount;
        }
        for (String key: exerciseMap.keySet()) {
            int eachExerciseCalorieCount = exerciseMap.get(key);
            exerciseCals += eachExerciseCalorieCount;
        }
        //Calories remaining in the day = calories you have consumed - calories you have expended
        int calorieDifference = foodCals - exerciseCals;
        return calorieDifference;
    }
}
