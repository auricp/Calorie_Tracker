package CalorieTrackerFP.data;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Exercise is a class that has all the functionality for how to store the users' exercises for the program. We can
 * perform many operations with the stored exercises
 */
public class Exercise extends UserMapData {

    //the holy grail hashMap that will store the users exercises as the keys and the associated calories as the value
    private final HashMap<String,Integer> exerciseMap = new HashMap<>();

    /**
     * Gives a copy of the exerciseMap
     * @return a copy of the exerciseMap
     */
    @Override
    public HashMap<String, Integer> getMap() {
        return new HashMap<>(exerciseMap);
    }

    /**
     * get an organized view of the stuff in the exerciseMap, which is an arrayList of the names, and an arrayList
     * of the calories (calories in calorieAmounts have the same index as their name counterpart by design)
     * @return the two maps of exercises and associated calories
     */
    @Override
    public ArrayList<ArrayList<String>> getMapData() {
        //make the two arraylists that will have the nice info we want
        ArrayList<String> exerciseNames = new ArrayList<>();
        ArrayList<String> calorieAmounts = new ArrayList<>();

        //for however many exercises, add them into the new arrayLists
        for (String key : exerciseMap.keySet()) {
            exerciseNames.add(key);
            calorieAmounts.add(String.valueOf(exerciseMap.get(key)));
        }

        //make an arrayList to return the two arrayLists
        return new ArrayList<>() {
            {
                add(exerciseNames);
                add(calorieAmounts);
            }
        };
    }

    /**
     * This function adds a new exercise and its calories to our exerciseMap
     * @param calories is the inputted calories
     * @param exercise is the inputted exercise
     */
    @Override
    public void addToMap(String exercise, int calories) {
        //adds together the calories of duplicate items if the inputted exercise is a duplicate
        if (exerciseMap.containsKey(exercise)) {
            int newCalories = exerciseMap.get(exercise) + calories;
            exerciseMap.put(exercise, newCalories);
        } else {
            exerciseMap.put(exercise, calories);
        }
    }

    /**
     * This function takes all the information in the exercise hashmap and turns it into a string (was mostly used
     * for debugging purposes to view the map nicely)
     * @return the string output for the exercise map
     */
    @Override
    public String toString() {
        StringBuilder output = new StringBuilder("\n");
        // Using a loop for the hashmap to add all the exercises and
        for(String key:exerciseMap.keySet()){
            String newThing = "Exercise: " + key + " | Calories: " + exerciseMap.get(key) + "\n";
            output.append(newThing);
        }
        return output.toString();
    }
}
