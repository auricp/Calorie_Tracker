package CalorieTrackerFP.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Exercise extends UserMapData {

    private HashMap<String,Integer> exerciseMap = new HashMap<>();

    @Override
    public HashMap<String, Integer> getMap() {
        HashMap<String,Integer> clonedMap = new HashMap<>(exerciseMap);
        return clonedMap;
    }

    public List[] getMapData() {
        List<String> exerciseNames = new ArrayList<String>();
        List<String> calorieAmounts = new ArrayList<String>();

        for (String key : exerciseMap.keySet()) {
            exerciseNames.add(key);
            calorieAmounts.add(String.valueOf(exerciseMap.get(key)));
        }
        List[] output = {exerciseNames, calorieAmounts};

        return output;
    }

    /**
     * This function is an override for the setMapData method in the UserData class and with its parameter updates the exerciseMap
     * @param calories is calories inputted
     * @param exercise is exercise inputted
     */
    @Override
    public void addToMap(String exercise, int calories) {
        //adds together the calories of duplicate items
        if (this.exerciseMap.containsKey(exercise)) {
            int newCalories = this.exerciseMap.get(exercise) + calories;
            this.exerciseMap.put(exercise, newCalories);
        } else {
            this.exerciseMap.put(exercise, calories);
        }
    }

    /**
     * This function takes all the information in the exercise hashmap and turns it into a string so that it is readable
     * if the user asks for the corresponding menu option
     * @return the string output for the food map
     */
    @Override
    public String toString() {
        String output = "\n";
        // Using a loop for the hashmap to print out all the food with their corresponding calories
        for(String key:exerciseMap.keySet()){
            String newThing = "Exercise: " + key + " | Calories: " + exerciseMap.get(key) + "\n";
            output += newThing;
        }
        return output;
    }
}
