package CalorieTrackerFP.util;

import CalorieTrackerFP.person.Person;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Writer {
    public static void saveFile(Person ourUser, File filename){

        try{
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
            if(ourUser.getWaistMeasurement() != 0){
                infoWriter.write("Neck(cm):" + ourUser.getNeckMeasurement());
                infoWriter.newLine();
            }
            if(ourUser.getWaistMeasurement() != 0){
                infoWriter.write("Waist(cm):" + ourUser.getWaistMeasurement());
                infoWriter.newLine();
            }
            if(ourUser.getHipMeasurement() != 0){
                infoWriter.write("Hip(cm):" + ourUser.getHipMeasurement());
            }
            infoWriter.close();
            // Placing a catch block in order to print out a file error if the program is unable to save the data to the specified file
        }catch (IOException e){
            System.out.print("File error");
        }
    }
}
