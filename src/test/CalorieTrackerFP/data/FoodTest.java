package CalorieTrackerFP.data;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FoodTest {

    /**
     * This tests to see if the add to map function can successfully add an food and calories for that food
     */
    @Test
    void addToMap() {
        // setting the expected string with new lines on both sides
        String expected = "\nFood: Burger | Calories: 200\n";
        // creating a new food object and adding running with its corresponding calories (200)
        Food result = new Food();
        result.addToMap("Burger",200);
        // Using assert equals to check if the expected string matches the toString for the made up add to map function
        assertEquals(expected,result.toString());
    }

    /**
     * This tests to see if the toString function will successfully print a created exercise map as a string
     */
    @Test
    void testToString() {
        // Setting the expected string
        String expected = "\nFood: Burger | Calories: 50\n";
        // Creating a new food object, then adding a Key and value to the map
        Food result = new Food();
        result.addToMap("Burger",50);
        // checking to see if the expected string is equal to the food map with toString
        assertEquals(expected,result.toString());

    }
}