package CalorieTrackerFP.util;

import CalorieTrackerFP.data.Exercise;
import CalorieTrackerFP.data.Food;
import CalorieTrackerFP.data.UserMapData;
import CalorieTrackerFP.person.Person;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

public class Writer {
    /**
     * save to a file when the user asks, by carefully grabbing the info from our user object and our program hashmap
     * and writing it perfectly to the file
     * @param ourUser user object to get measurements from
     * @param localDateArrayListHashMap program hashMap to get food and exercise data from
     * @param filename file to save the data to
     */
    public static void saveFile(Person ourUser, HashMap<LocalDate, ArrayList<UserMapData>> localDateArrayListHashMap, File filename) throws IOException {


        //first, save all the user info to the file

        String filenameWrite = filename.getAbsolutePath();
        // Initializing a buffer writer to write to each of the lines individually
        BufferedWriter infoWriter = new BufferedWriter(new FileWriter(filenameWrite));
        // Next chunk of lines are individually adding all the users' information to the file with the identifying words in front of them
        // Cannot use a loop since all information is received from a different getter in the person class
        infoWriter.write("Goal," + ourUser.getGoal());
        infoWriter.newLine();
        infoWriter.write("Gender," + ourUser.getGender());
        infoWriter.newLine();
        infoWriter.write("Age," + ourUser.getAge());
        infoWriter.newLine();
        infoWriter.write("Weight(kg)," + ourUser.getWeight());
        infoWriter.newLine();
        infoWriter.write("Height(cm)," + ourUser.getHeight());
        infoWriter.newLine();
        infoWriter.write("Neck(cm)," + ourUser.getNeckMeasurement());
        infoWriter.newLine();
        infoWriter.write("Waist(cm)," + ourUser.getWaistMeasurement());
        infoWriter.newLine();
        infoWriter.write("Hip(cm)," + ourUser.getHipMeasurement());

        //second, for all the dates entered, save the corresponding food and exercise data

        //first thing on these lines is the date of the corresponding info
        for (LocalDate hashMapDate : localDateArrayListHashMap.keySet()) {
            infoWriter.newLine();
            infoWriter.write((hashMapDate) + ",");

            //get the foodMap stored in the hashMap for that day
            Food localFoodMap = (Food) localDateArrayListHashMap.get(hashMapDate).get(0);
            infoWriter.write("FOOD,");
            ArrayList<ArrayList<String>> foodMapInfo = localFoodMap.getMapData();
            ArrayList<String> foodNames = foodMapInfo.get(0);
            ArrayList<String> foodCalories = foodMapInfo.get(1);
            //write the food-calorie combos for however many we have
            for (int i = 0; i < foodNames.size(); i++) {
                infoWriter.write((foodNames.get(i)) + ",");
                infoWriter.write((foodCalories.get(i)) + ",");

            }

            //get the exercise map stored in the hashMap for that day
            Exercise localExerciseMap = (Exercise) localDateArrayListHashMap.get(hashMapDate).get(1);
            infoWriter.write("EXERCISE,");
            ArrayList<ArrayList<String>> exerciseMapInfo = localExerciseMap.getMapData();
            ArrayList<String> exerciseNames = exerciseMapInfo.get(0);
            ArrayList<String> exerciseCalories = exerciseMapInfo.get(1);
            //write the exercise-calorie combos for as many as we have
            for (int i = 0; i < exerciseNames.size(); i++) {
                infoWriter.write((exerciseNames.get(i)) + ",");
                infoWriter.write((exerciseCalories.get(i)) + ",");
            }
        }
        //closing the writer!
        infoWriter.flush();
        infoWriter.close();
    }
}
