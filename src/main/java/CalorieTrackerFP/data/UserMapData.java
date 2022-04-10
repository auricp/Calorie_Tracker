package CalorieTrackerFP.data;

import java.util.HashMap;

public abstract class UserMapData {

    public abstract HashMap<String,Integer> getMap();

    public abstract void addToMap(String item, int calories);
}
