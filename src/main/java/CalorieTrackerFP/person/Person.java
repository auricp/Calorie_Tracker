package CalorieTrackerFP.person;

/**
 * Person is used to store all the measurement info of our user. It has all required getters and setters for each of
 * a persons measurements, as well as functionality to calculate certain health metrics using a users' measurement data
 */
public class Person {
    private int age;
    private String goal;
    private String gender;
    private double height;
    private double weight;
    private double neckMeasurement = 0;
    private double waistMeasurement = 0;
    private double hipMeasurement = 0;

    /**
     * changes the users' goal
     * @param goal the new user goal
     */
    public void setGoal(String goal){
        //method to ask for changing goal
        this.goal = goal;
    }

    /**
     * gets the current user goal
     * @return user goal
     */
    public String getGoal(){
        return goal;
    }

    /**
     * changes the users' gender
     * @param gender new gender
     */
    public void setGender(String gender){
        this.gender = gender;
    }

    /**
     * gets the current users gender
     * @return user gender
     */
    public String getGender() {
        return gender;
    }

    /**
     * changes the users' age
     * @param AgeYr new age
     */
    public void setAge(int AgeYr){
        age = AgeYr;
    }

    /**
     * gets the users' current age
     * @return current age
     */
    public int getAge(){
        return age;
    }

    /**
     * change the users height
     * @param height
     */
    public void setHeight(double height){
        //do function to ask for new height
        this.height = height;
    }

    /**
     * get the current user height
     * @return current height
     */
    public double getHeight(){
        //do function to get height
        return height;
    }

    /**
     * change the user's weight
     * @param weight new weight
     */
    public void setWeight(double weight){
        //do function to ask for new weight. In kg
        this.weight = weight;
    }

    /**
     * get the current user weight
     * @return current weight
     */
    public double getWeight(){
        return weight;
    }

    /**
     * get the current neck measurement
     * @return current neck measurement
     */
    public double getNeckMeasurement(){
        return neckMeasurement;
    }

    /**
     * change the current neck measurement
     * @param neckCm new neck measurement
     */
    public void setNeckMeasurement(double neckCm){
        neckMeasurement = neckCm;
    }

    /**
     * get the current waist measurement
     * @return current waist measurement
     */
    public double getWaistMeasurement(){
        return waistMeasurement;
    }

    /**
     * change the users' waist measurement
     * @param waistCm new waist measurement
     */
    public void setWaistMeasurement(double waistCm){
        waistMeasurement = waistCm;
    }

    /**
     * get the users' current hip measurement
     * @return current hip measurement
     */
    public double getHipMeasurement() {
        return hipMeasurement;
    }

    /**
     * change the users' hip measurement
     * @param hipMeasurement new hip measurement
     */
    public void setHipMeasurement(double hipMeasurement) {
        this.hipMeasurement = hipMeasurement;
    }

    /**
     * To string method that prints out all the users measurements. Will only include neck waist and hip measurements if they are entered into the program
     * @return the string form of all of this information for easy viewing
     */
    @Override
    public String toString() {
        String output = "";
        output += "Your measurements: \n";
        output += "Age: " + age + "\n";
        output += "Goal: " + goal + "\n";
        output += "Weight: " + weight + "\n";
        output += "Height: " + height + "\n";
        if (neckMeasurement > 0.0) {
            output += "Neck measurement: " + neckMeasurement + "\n";
        }
        if (waistMeasurement > 0.0) {
            output += "Waist measurement: " + waistMeasurement + "\n";
        }
        if (hipMeasurement > 0.0) {
            output += "Hip measurement: " + hipMeasurement + "\n";
        }
        return output;
    }

    /**
     * Calculates the users' personal BMI reading based off of the measurements they have inputted
     * @return the users' BMI
     */
    public double getBmi(){
        double heightM = this.height / 100;
        double heightMSquared = Math.pow(heightM, 2);
        double bmi = this.getWeight() / heightMSquared;
        return bmi;
    }

    /**
     * Calculates the users' personal body fat percentage based off of the measurements they have inputted
     * @return the users' body fat percentage
     */
    public double getBodyFat(){
        //slightly different body fat calculations for each gender
        if(gender.equals("woman")){
            double bodyFatPercentageForWoman = 495 / ((1.29579 - (0.35004 * Math.log10(waistMeasurement + hipMeasurement - neckMeasurement))) + (0.22100 * Math.log10(height))) - 450;
            return bodyFatPercentageForWoman;
        }else if(gender.equals("man")){
            double bodyFatPercentageForMan = 495 / ((1.0324 - (0.19077 * Math.log10(waistMeasurement - neckMeasurement))) + (0.15456 * Math.log10(height))) - 450;
            return bodyFatPercentageForMan;
        }
        return 0;
    }
}
