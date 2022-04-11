package CalorieTrackerFP.util;

import CalorieTrackerFP.person.Person;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Reader {
    public static void readFile(Person ourUser, File chosen) throws FileNotFoundException {
        // making a new file for the inputted filename and initializing a scanner and arraylist
        Scanner fileReader = new Scanner(chosen);
        ArrayList<String> infoLines = new ArrayList<>();
        // reading every line in the file and adding each line to the recently created arrayList
        while(fileReader.hasNextLine()){
            String compile = fileReader.nextLine();
            infoLines.add(compile);
        }

        // The following lines are removing the identifying words in front of the actual data and then setting that data to the user
        String[] goalList = infoLines.get(0).split(":");
        String goal = goalList[1];
        ourUser.setGoal(goal);

        String[] genderList = infoLines.get(1).split(":");
        String gender = genderList[1];
        ourUser.setGender(gender);

        String[] AgelList = infoLines.get(2).split(":");
        int age = Integer.parseInt(AgelList[1]);
        ourUser.setAge(age);

        String[] weightList = infoLines.get(3).split(":");
        double weight = Double.parseDouble(weightList[1]);
        ourUser.setWeight(weight);

        String[] HeightList = infoLines.get(4).split(":");
        double height = Double.parseDouble(HeightList[1]);
        ourUser.setHeight(height);

        // only adding the following information to the user if they are inputted in the file (these are the optional pieces of information)
        if(infoLines.size() > 5){
            String[] neckList = infoLines.get(5).split(":");
            double neck = Double.parseDouble(neckList[1]);
            ourUser.setNeckMeasurement(neck);
        }if (infoLines.size() > 6){
            String[] waistList = infoLines.get(6).split(":");
            double waist = Double.parseDouble(waistList[1]);
            ourUser.setWaistMeasurement(waist);
        }if (infoLines.size() > 7){
            String[] hipList = infoLines.get(7).split(":");
            double hip = Double.parseDouble(hipList[1]);
            ourUser.setHipMeasurement(hip);
        }
    }
}
