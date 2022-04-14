package CalorieTrackerFP.util;

import CalorieTrackerFP.data.Exercise;
import CalorieTrackerFP.data.Food;
import CalorieTrackerFP.data.UserMapData;
import CalorieTrackerFP.person.Person;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Reader is used to read the information that is inside a file formatted for our program, and extract the needed data
 * to pass back to the program, allowing the user to load the same data from a file that they can edit in the program.
 */
public class Reader {

    /**
     * opens the given file and carefully extracts the information to first create the user's measurements, and then add
     * any food or exercises that the user may have given as well in the file.
     * @param ourUser the user object to add the measurements to
     * @param chosen the file to open
     * @return a hashmap of <LocalDate, ArrayList<UserMapData>>, which stores the foodMap and exerciseMaps based on the date
     * @throws FileNotFoundException when we had a problem reading from the file (formatted wrong most likely)
     */
    public static HashMap<LocalDate, ArrayList<UserMapData>> readFile(Person ourUser, File chosen) throws FileNotFoundException {
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

        // The following lines are removing the identifying words in front of the actual data and then setting that data to the user

        /* split the line by :, with the stuff on the right (index 1) being the actual measurement. Many following
        lines use the same functionality */
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

        //if there is more data than just the user info (food data)
        if (amountOfLines > 7) {
            /* start at 8 because the first 0-7 indices are always user info, everything else until the end of the file
            is guaranteed to start with a date */
            for (int i = 8; i < amountOfLines; i++) {
                //make new maps for the new line of the file, to add to and later store based on date of the line
                Food foodMap = new Food();
                Exercise exerciseMap = new Exercise();
                String dateToAdd = null;

                String currentInfoLine = infoLines.get(i);
                String[] currentInfoLineArray = currentInfoLine.split(",");

                //once you see "EXERCISE" in the file, the next pair of name,calories is now meant for the exerciseMap
                boolean moveOnToExerciseMap = false;
                //System.out.println("about to loop through the stuff on the specific line with a length of " + currentInfoLineArray.length);
                for (int j = 0; j < currentInfoLineArray.length; j++) {
                    //first thing is always the date
                    dateToAdd = currentInfoLineArray[0];

                    String str = currentInfoLineArray[j];
                    if (str.equals("EXERCISE")) {
                        moveOnToExerciseMap = true;
                    }
                    boolean allLetters = str.chars().allMatch(Character::isLetter);
                    //if the line is all letters (and not the starting "FOOD" or "EXERCISE", because those are just file identifiers)
                    if ((allLetters) && (!str.equals("FOOD")) && (!str.equals("EXERCISE"))) {
                        //if we are still adding to the foodMap
                        if (!moveOnToExerciseMap) {
                            /* add the name, and associated calorie amount, which is guaranteed to be the next thing
                            separated by a comma in the file */
                            foodMap.addToMap(str, Integer.parseInt(currentInfoLineArray[j+1]));
                        } else {
                            exerciseMap.addToMap(str, Integer.parseInt(currentInfoLineArray[j+1]));
                        }
                    }
                }
                //make the arraylist to store the two maps in the dateList hashMap
                ArrayList<UserMapData> maps = new ArrayList<>() {{
                    add(foodMap);
                    add(exerciseMap);
                }};
                //add the lines foodMap and exerciseMap to the big map, associated with its date
                dateListHashMap.put(LocalDate.parse(dateToAdd), maps);
            }

        }
        return dateListHashMap;
    }
}
