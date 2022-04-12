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
import java.util.List;
import java.util.Scanner;

public class Writer {
    /**
     * save to a file when the user asks to, by carefully grabbing the info from our user object and our program hashmap
     * and writing it perfectly to the file
     * @param ourUser user object to get measurements from
     * @param localDateArrayListHashMap program hashMap to get food and exercise data from
     * @param filename file to save the data to
     */
    public static void saveFile(Person ourUser, HashMap<LocalDate, ArrayList<UserMapData>> localDateArrayListHashMap, File filename){

        try{

            //first, save all the use info to the file

            String filenameWrite = filename.getAbsolutePath();
            // Initializing a bufferwriter to write to each of the lines individually)
            BufferedWriter infoWriter = new BufferedWriter(new FileWriter(filenameWrite));
            // Next chunk of lines are individually adding all of the users information to the file with the identifying words in front of them
            // Cannot use a loop since all information is received from a different getter in the person class
            infoWriter.write("Goal:" + ourUser.getGoal());
            infoWriter.newLine();
            infoWriter.write("Gender:" + ourUser.getGender());
            infoWriter.newLine();
            infoWriter.write("Age:" + ourUser.getAge());
            infoWriter.newLine();
            infoWriter.write("Weight(kg):" + ourUser.getWeight());
            infoWriter.newLine();
            infoWriter.write("Height(cm):" + ourUser.getHeight());
            infoWriter.newLine();
            infoWriter.write("Neck(cm):" + ourUser.getNeckMeasurement());
            infoWriter.newLine();
            infoWriter.write("Waist(cm):" + ourUser.getWaistMeasurement());
            infoWriter.newLine();
            infoWriter.write("Hip(cm):" + ourUser.getHipMeasurement());

            //second, for all the dates entered, save the corresponding food and exercise data

            for (LocalDate hashMapDate : localDateArrayListHashMap.keySet()) {
                infoWriter.newLine();
                LocalDate dateToWrite = hashMapDate;
                infoWriter.write(String.valueOf(dateToWrite) + ",");

                //get the foodMap stored in the hashMap for that day
                Food localFoodMap = (Food) localDateArrayListHashMap.get(hashMapDate).get(0);
                infoWriter.write("FOOD,");
                //System.out.println(localFoodMap);
                ArrayList<ArrayList<String>> foodMapInfo = localFoodMap.getMapData();
                ArrayList<String> foodNames = foodMapInfo.get(0);
                ArrayList<String> foodCalories = foodMapInfo.get(1);
                for (int i = 0; i < foodNames.size(); i++) {
                    infoWriter.write(String.valueOf(foodNames.get(i)) + ",");
                    infoWriter.write(String.valueOf(foodCalories.get(i)) + ",");

                }

                Exercise localExerciseMap = (Exercise) localDateArrayListHashMap.get(hashMapDate).get(1);
                infoWriter.write("EXERCISE,");
                ArrayList<ArrayList<String>> exerciseMapInfo = localExerciseMap.getMapData();
                ArrayList<String> exerciseNames = exerciseMapInfo.get(0);
                ArrayList<String> exerciseCalories = exerciseMapInfo.get(1);
                for (int i = 0; i < exerciseNames.size(); i++) {
                    infoWriter.write(String.valueOf(exerciseNames.get(i)) + ",");
                    infoWriter.write(String.valueOf(exerciseCalories.get(i)) + ",");
                }
            }

            infoWriter.close();

            // Placing a catch block in order to print out a file error if the program is unable to save the data to the specified file
        }catch (IOException e){
            System.out.print("File error");
            System.out.println(e);
        }
    }
}
