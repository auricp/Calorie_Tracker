package CalorieTrackerFP.util;

import CalorieTrackerFP.data.Food;
import CalorieTrackerFP.data.UserMapData;
import CalorieTrackerFP.person.Person;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

public class Reader {
    public static HashMap<LocalDate, ArrayList> readFile(Person ourUser, File chosen) throws FileNotFoundException {
        // making a new file for the inputted filename and initializing a scanner and arraylist
        Scanner fileReader = new Scanner(chosen);
        ArrayList<String> infoLines = new ArrayList<>();
        // reading every line in the file and adding each line to the recently created arrayList
        int amountOfLines = 0;
        while(fileReader.hasNextLine()){
            String compile = fileReader.nextLine();
            infoLines.add(compile);
            amountOfLines++;
        }
        System.out.println("Info lines:\n" + infoLines);

        // The following lines are removing the identifying words in front of the actual data and then setting that data to the user

        String[] goalList = infoLines.get(0).split(":");
        String goal = goalList[1];
        ourUser.setGoal(goal);

        String[] genderList = infoLines.get(1).split(":");
        String gender = genderList[1];
        ourUser.setGender(gender);

        String[] ageList = infoLines.get(2).split(":");
        int age = Integer.parseInt(ageList[1]);
        ourUser.setAge(age);

        String[] weightList = infoLines.get(3).split(":");
        double weight = Double.parseDouble(weightList[1]);
        ourUser.setWeight(weight);

        String[] HeightList = infoLines.get(4).split(":");
        double height = Double.parseDouble(HeightList[1]);
        ourUser.setHeight(height);

        String[] neckList = infoLines.get(5).split(":");
        double neck = Double.parseDouble(neckList[1]);
        ourUser.setNeckMeasurement(neck);

        String[] waistList = infoLines.get(6).split(":");
        double waist = Double.parseDouble(waistList[1]);
        ourUser.setWaistMeasurement(waist);

        String[] hipList = infoLines.get(7).split(":");
        double hip = Double.parseDouble(hipList[1]);
        ourUser.setHipMeasurement(hip);

        //the next lines add the food and exercise data to a dateListHashMap, which is returned to the program and stored as the new one

        HashMap<LocalDate, ArrayList<UserMapData>> dateListHashMap = new HashMap<>();
        Food foodMap = new Food();

        //if there is more data than just the user info (food data)
        if (amountOfLines > 7) {
            System.out.println("more than just user info");
            //start at 8 because the first 0-7 indices are always user info, everything else until the end of the file
            //is guaranteed to start with a date
            for (int i = 8; i < amountOfLines; i++) {
                String currentInfoLine = infoLines.get(i);
                String[] currentInfoLineArray = currentInfoLine.split(",");
                String datePart = currentInfoLineArray[0];
                System.out.println(currentInfoLineArray);
                boolean moveOnToExerciseMap = false;
                for (int j = 0; j < currentInfoLineArray.length; j++) {
                    String str = currentInfoLineArray[j];
                    if (str.equals("EXERCISE")) {
                        moveOnToExerciseMap = true;
                    }

                    boolean allLetters = str.chars().allMatch(Character::isLetter);
                    //if the line is all letters (and not the starting "FOOD"
                    if ((allLetters) && (!str.equals("FOOD"))) {
                        //if we are still adding to the foodMap
                        if (!moveOnToExerciseMap) {
                            foodMap.addToMap(str, );
                        } else {

                        }
                    }
                }



                    System.out.println(str);
                }
//                System.out.println("Date: " + datePart);

//                String currentStr = null;
//                while (!currentStr.equals("EXERCISE")) {
//                    currentStr
//                }
//                String[] foodPart = currentInfoLine.split("FOOD");
//
//                String[] exercisePart = currentInfoLine.split("EXERCISE");
//                for (String str : foodPart) {
//                    System.out.println(str);
//                }
//                System.out.println(foodPart[1]);

//                String[] arrayOfCurrentInfoLine = currentInfoLine.split(",");
//                System.out.println("item at " + i + " spot is " + infoLines.get(i));
//
//                LocalDate fileDate = currentInfoLine;
            }
        }
        LocalDate dateToAdd;


        System.out.println("# of lines: " + amountOfLines);




        return null;
    }
}
