package CalorieTrackerFP.calories;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CalorieCalculationsTest {

    /**
     * This test checks to see if the calorie total function works with the chosen goal and weight
     */
    @Test
    void getCalorieTotal() {
        // Setting the expected number of calories as well as the goal and weight that we are going to check
        int expectedTotal = 2815;
        String goal = "muscle";
        double weight = 70.0;
        // Calling the function and placing the return value into the result variable
        int result = CalorieCalculations.getCalorieTotal(weight,goal);
        // Using assert equals to check if the expected and result variables are equal
        assertEquals(expectedTotal,result);
    }
}