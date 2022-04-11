package CalorieTrackerFP.data;

public class TableEntry {

    private String name;
    private String calories;

    public TableEntry(String name, String calories) {
        this.name = name;
        this.calories = calories;
    }

    public String getName() {
        return name;
    }

    public String getCalories() {
        return calories;
    }
}
