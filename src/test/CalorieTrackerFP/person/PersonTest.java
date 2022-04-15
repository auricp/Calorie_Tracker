package CalorieTrackerFP.person;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PersonTest {
    // Creating a new person named user
    Person user = new Person();


    /**
     * ALL THE FOLLOWING METHOD COMMENTS ARE REDUNDANT SO COMMENTS FOR FIRST METHOD IS THE SAME FOR ALL
     */


    /**
     * All the following tests check to see if the Person object setters and getters work for the majority of the methods with different inputts
     */
    @Test
    void setGoal() {
        // Setting the expected string or integer (for all tests in this class)
        String expected = "muscle";
        // Using the setter the set that expected variable to the Person
        user.setGoal("muscle");
        // Using assert equals to check to see if the expected variable equals the getter for the Person (set to user) for all functions
        assertEquals(expected,user.getGoal());
    }

    @Test
    void setGender() {
        String expected = "woman";
        user.setGender("woman");
        assertEquals(expected,user.getGender());
    }

    @Test
    void setAge() {
        int expected = 18;
        user.setAge(18);
        assertEquals(expected,user.getAge());
    }

    @Test
    void setHeight() {
        int expected = 183;
        user.setHeight(183);
        assertEquals(expected,user.getHeight());
    }

    @Test
    void setWeight() {
        int expected = 76;
        user.setWeight(76);
        assertEquals(expected,user.getWeight());
    }
}