package CalorieTrackerFP.calories;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CalorieCalculationsTest {

    @Test
    void getCalorieTotal() {
        int expectedTotal = 2815;
        String goal = "muscle";
        double weight = 70.0;
        int result = CalorieCalculations.getCalorieTotal(weight,goal);
        assertEquals(expectedTotal,result);
    }
}