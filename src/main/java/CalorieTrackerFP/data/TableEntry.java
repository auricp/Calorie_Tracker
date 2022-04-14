package CalorieTrackerFP.data;

/**
 * TableEntry is used to put date into the tables. It was easiest to create a new class with just the functionality of
 * being added to the table so that PropertyValueFactory is easier to use
 */
public class TableEntry {

    //fields for names and calories that will be added to the table
    private final String name;
    private final String calories;

    /**
     * Stores the name-calorie combo that wants to be added to the table into the fields of this class, which we can
     * then add to the table
     * @param name name of item we want to add to the table
     * @param calories calorie value associated with the name we want to add
     */
    public TableEntry(String name, String calories) {
        this.name = name;
        this.calories = calories;
    }

    /**
     * getter for the name field (need these getters to work with PropertyValueFactory, not sure why it's greyed out tbh)
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * getter for the calories' field
     * @return the calories
     */
    public String getCalories() {
        return calories;
    }
}
