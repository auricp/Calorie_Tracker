package CalorieTrackerFP.data;

import java.util.HashMap;

public class Exercise extends UserMapData {

    private HashMap<String,Integer> exerciseMap = new HashMap<>();

    @Override
    public HashMap<String, Integer> getMap() {
        return null;
    }

    /**
     * This function is an override for the setMapData method in the UserData class and with its parameter updates the exerciseMap
     * @param calories is calories inputted
     * @param exercise is exercise inputted
     */
    @Override
    public void addToMap(String exercise, int calories) {
        this.exerciseMap.put(exercise, calories);
    }
}
