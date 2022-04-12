package CalorieTrackerFP.data;

import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class ExerciseTest {

    @Test
    void addToMap() {
        Exercise expected = new Exercise();
        expected.addToMap("Burger",200);
        Exercise result = new Exercise();
        result.addToMap("Burger",200);
        assertEquals(expected.toString(),result.toString());
    }

    @Test
    void testToString() {
        String expected = "\nExercise: " + "Running" + " | Calories: " + 100 + "\n";
        Exercise result = new Exercise();
        result.addToMap("Running",100);
        assertEquals(expected,result.toString());
    }
}