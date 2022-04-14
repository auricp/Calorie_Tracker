package CalorieTrackerFP.data;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * UserMapDate is used as an abstract class to specify what we want our maps to be able to do
 */
public abstract class UserMapData {

    /**
     * Maps need to be able to return a copy of that map if we ever need it
     * @return a copy of the map
     */
    public abstract HashMap<String,Integer> getMap();

    /**
     * Maps need to be able to return a nice version of the items and calories so that we can easily work with them
     * @return two arrayLists that contain an arraylist for the names and the calories
     */
    public abstract ArrayList<ArrayList<String>> getMapData();

    /**
     * Maps need to be able to add a new item-calorie combo to them
     * @param item the item to be added
     * @param calories the calories of the item
     */
    public abstract void addToMap(String item, int calories);
}
