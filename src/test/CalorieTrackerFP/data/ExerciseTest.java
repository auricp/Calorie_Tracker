package CalorieTrackerFP.data;

import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class ExerciseTest {

    /**
     * This tests to see if the add to map function can successfully add an exercise and calories for that exercise
     */
    @Test
    void addToMap() {
        // setting the expected string with new lines on both sides
        String expected = "\nExercise: Running | Calories: 200\n";
        // creating a new exercise object and adding running with its corresponding calories (200)
        Exercise result = new Exercise();
        result.addToMap("Running",200);
        // Using assert equals to check if the expected string matches the toString for the made up add to map function
        assertEquals(expected,result.toString());
    }

    /**
     * This tests to see if the toString function will successfully print a created exercise map as a string
     */
    @Test
    void testToString() {
        // Setting the expected string
        String expected = "\nExercise: " + "Running" + " | Calories: " + 100 + "\n";
        // Creating a new exercise object, then adding a Key and value to the map
        Exercise result = new Exercise();
        result.addToMap("Running",100);
        // checking to see if the expected string is equal to the exercise map with toString
        assertEquals(expected,result.toString());
    }
}