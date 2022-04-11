package CalorieTrackerFP.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Food extends UserMapData {

    private HashMap<String,Integer> foodMap = new HashMap<>();

//    dataName, int calories
//    public Food() {
//        //super(dataName, calories);
//    }

    /**
     * making an override for the getMap method in the UserData class that makes a clones map of the food map and returns
     * it for later use in the Main class
     */
    @Override
    public HashMap<String,Integer> getMap() {
        HashMap<String,Integer> clonedMap = new HashMap<>(foodMap);
        return clonedMap;
    }

    public List[] getMapData() {
        List<String> foodNames = new ArrayList<String>();
        List<String> calorieAmounts = new ArrayList<String>();

        for (String key : foodMap.keySet()) {
            foodNames.add(key);
            calorieAmounts.add(String.valueOf(foodMap.get(key)));
        }
        List[] output = {foodNames, calorieAmounts};

        return output;
    }

    /**
     * This function is an override for the setMapData method in the UserData class and with its parameter updates the foodMap
     * @param calories is calories inputted
     * @param food is food inputted
     */
    @Override
    public void addToMap(String food, int calories) {
        //adds together the calories of duplicate items
        if (this.foodMap.containsKey(food)) {
            int newCalories = this.foodMap.get(food) + calories;
            this.foodMap.put(food, newCalories);
        } else {
            this.foodMap.put(food, calories);
        }
    }

    /**
     * This function takes all the information in the food hashmap and turns it into a string so that it is readable
     * if the user asks for the corresponding menu option
     * @return the string output for the food map
     */
    @Override
    public String toString() {
        String output = "\n";
        // Using a loop for the hashmap to print out all the food with their corresponding calories
        for(String key:foodMap.keySet()){
            String newThing = "Food: " + key + " | Calories: " + foodMap.get(key) + "\n";
            output += newThing;
            //System.out.println("Food: " + key + " | Calories: " + foodMap.get(key));
        }
        return output;
    }
}
