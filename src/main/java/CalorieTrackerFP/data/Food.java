package CalorieTrackerFP.data;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Exercise is a class that has all the functionality for how to store the users' food for the program. We can
 * perform many operations with the stored food
 */
public class Food extends UserMapData {

    private final HashMap<String,Integer> foodMap = new HashMap<>();

    /**
     * Gives a copy of the foodMap
     * @return a copy of the foodMap
     */
    @Override
    public HashMap<String,Integer> getMap() {
        return new HashMap<>(foodMap);
    }
    /**
     * get an organized view of the stuff in the foodMap, which is an arrayList of the names, and an arrayList
     * of the calories (calories in calorieAmounts have the same index as their name counterpart by design)
     * @return the two maps of food and associated calories
     */
    public ArrayList<ArrayList<String>> getMapData() {
        //make the two arraylists that will have the nice info we want
        ArrayList<String> foodNames = new ArrayList<>();
        ArrayList<String> calorieAmounts = new ArrayList<>();

        //for however much food, add them into the new arrayLists
        for (String key : foodMap.keySet()) {
            foodNames.add(key);
            calorieAmounts.add(String.valueOf(foodMap.get(key)));
        }

        //make an arrayList to return the two arrayLists

        return new ArrayList<>() {
            {
                add(foodNames);
                add(calorieAmounts);
            }
        };
    }

    /**
     * This function adds a new exercise and its calories to our exerciseMap
     * @param calories is the inputted calories
     * @param food is the inputted exercise
     */
    @Override
    public void addToMap(String food, int calories) {
        //adds together the calories of duplicate items if the inputted food is a duplicate
        if (foodMap.containsKey(food)) {
            int newCalories = foodMap.get(food) + calories;
            foodMap.put(food, newCalories);
        } else {
            foodMap.put(food, calories);
        }
    }

    /**
     * This function takes all the information in the food hashmap and turns it into a string (was mostly used
     * for debugging purposes to view the map nicely)
     * @return the string output for the food map
     */
    @Override
    public String toString() {
        StringBuilder output = new StringBuilder("\n");
        // Using a loop for the hashmap to print out all the food with their corresponding calories
        for(String key:foodMap.keySet()){
            String newThing = "Food: " + key + " | Calories: " + foodMap.get(key) + "\n";
            output.append(newThing);
            //System.out.println("Food: " + key + " | Calories: " + foodMap.get(key));
        }
        return output.toString();
    }
}
