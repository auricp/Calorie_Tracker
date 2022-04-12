package CalorieTrackerFP.data;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FoodTest {

    @Test
    void addToMap() {
        String expected = "\nFood: Burger | Calories: 200\n";
        Food result = new Food();
        result.addToMap("Burger",200);
        assertEquals(expected,result.toString());
    }

    @Test
    void testToString() {
        String expected = "\nFood: Burger | Calories: 50\n";
        Food result = new Food();
        result.addToMap("Burger",50);
        assertEquals(expected,result.toString());

    }
}