package CalorieTrackerFP.calories;

import java.util.HashMap;

/**
 * CalorieCalculations is used to perform different calculations with the calories of both the foods and exercises in our program
 */
public class CalorieCalculations {

    //constant used to convert between imperial and metric
    static final double lbsToKgConversion = 2.205;
    /**
     *This function calculates the users daily needed calories based off of their weight and their goal
     */
    public static int getCalorieTotal(double weight, String userGoal) {
        int calories = 0;
        switch (userGoal) {
            case "maintenance" -> {
                double caloriesD = lbsToKgConversion * weight * 15;
                calories = (int) caloriesD;
            }
            case "loss" -> {
                double caloriesD = (lbsToKgConversion * weight * 15) - 500;
                calories = (int) caloriesD;
            }
            case "muscle" -> {
                double caloriesD = (lbsToKgConversion * weight * 15) + 500;
                calories = (int) caloriesD;
            }
        }
        return calories;
    }

    /**
     * Calculates the total amount of calories consumed between the eaten foods and the performed exercises (by adding
     * food calories and subtracting exercise calories)
     * @param foodMap current program foods
     * @param exerciseMap current program exercise
     * @return the amount of calories that have been consumed
     */
    public static int getCaloriesConsumed(HashMap<String, Integer> foodMap, HashMap<String, Integer> exerciseMap) {
        int foodCals = 0;
        int exerciseCals = 0;
        //add each of the foods calories together
        for (String key: foodMap.keySet()) {
            int eachFoodCalorieCount = foodMap.get(key);
            foodCals += eachFoodCalorieCount;
        }
        //add each of the exercises calories together
        for (String key: exerciseMap.keySet()) {
            int eachExerciseCalorieCount = exerciseMap.get(key);
            exerciseCals += eachExerciseCalorieCount;
        }
        //calories remaining in the day = calories you have consumed from food - calories you have expended from exercise
        return foodCals - exerciseCals;
    }
}
