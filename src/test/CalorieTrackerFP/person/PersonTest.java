package CalorieTrackerFP.person;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PersonTest {
    Person user = new Person();

    @Test
    void setGoal() {
        String expected = "muscle";
        user.setGoal("muscle");
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