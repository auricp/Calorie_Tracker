package CalorieTrackerFP.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class UserMapData {

    public abstract HashMap<String,Integer> getMap();

    public abstract ArrayList<ArrayList<String>> getMapData();

    public abstract void addToMap(String item, int calories);
}
