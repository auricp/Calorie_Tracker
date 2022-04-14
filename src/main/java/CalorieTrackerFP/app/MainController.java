package CalorieTrackerFP.app;

/*
  names: Colton Gowans, Auric Poku
  date: April 12th
  tutorial: Tut 07
 */

import CalorieTrackerFP.calories.Calories;
import CalorieTrackerFP.data.Exercise;
import CalorieTrackerFP.data.Food;
import CalorieTrackerFP.data.TableEntry;
import CalorieTrackerFP.data.UserMapData;
import CalorieTrackerFP.person.Person;
import CalorieTrackerFP.util.Reader;
import CalorieTrackerFP.util.Writer;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;


/**
 * Controller used for the entire project to allow the gui to be event driven
 */
public class MainController {

    static final double inchToCMConversion = 2.54;
    static final double lbsToKgConversion = 2.205;

    public static String[] args;

    @FXML
    private Label currentDateLabel;

    @FXML
    private DatePicker datePicker;

    @FXML
    private TextField lbsToKg;

    @FXML
    private TextField lbsToKgOutput;

    @FXML
    private TextField InToCm;

    @FXML
    private TextField InToCmOutput;

    @FXML
    private TextField ageInput;

    @FXML
    private TextField heightInput;

    @FXML
    private Label errorMsg;

    @FXML
    private RadioButton bfRadio;

    @FXML
    private Label successMsg;

    @FXML
    private TextField hipInput;

    @FXML
    private RadioButton bmiRadio;

    @FXML
    private TextField neckInput;

    @FXML
    private TextField waistInput;

    @FXML
    private TextField weightInput;

    @FXML
    private ChoiceBox<String> goalChoose;

    @FXML
    private ChoiceBox<String> genderChoose;

    @FXML
    private TextArea viewUserInfoTextArea;

    @FXML
    private TextArea bmiBfPrint;

    @FXML
    private RadioButton addExerciseButton;

    @FXML
    private RadioButton addFoodButton;

    @FXML
    private Label itemNameLabel;

    @FXML
    private TextField mapInputCalories;

    @FXML
    private TextField mapItemInput;

    @FXML
    private Button addItemToMapButton;

    @FXML
    private RadioButton viewExercisesButton;

    @FXML
    private RadioButton viewFoodButton;

    @FXML
    private TableView<TableEntry> mapTable;

    @FXML
    private Button generateGraphButton;

    @FXML
    private BarChart<String, Number> datesVsCaloriesGraph;

    @FXML
    private CategoryAxis datesXAxis;

    @FXML
    private NumberAxis caloriesYAxis;


    // The following couple of lines are setting our initial variables that we need for the controller
    private final String[] goals = new String[]{"muscle", "loss", "maintenance"};
    private final String[] genders = new String[]{"man", "woman"};

    Person User;
    Food foodMap;
    Exercise exerciseMap;

    LocalDate now = LocalDate.from(LocalDateTime.now());

    LocalDate currentDateInProgram = now;

    HashMap<LocalDate, ArrayList<UserMapData>> dateListHashMap = new HashMap<>();

    /**
     * This function runs once on startup, so it is used to initialize lots of different aspects of our program that need
     * to have certain things initialized.
     */
    @FXML
    public void initialize(){
        //make a new user, and new foodMap and exerciseMap
        User = new Person();
        foodMap = new Food();
        exerciseMap = new Exercise();

        //if the user gave a file to load from initially in the command line
        if (args.length == 1) {
            command();
        }

        //set the choice pickers items
        goalChoose.getItems().addAll(goals);
        genderChoose.getItems().addAll(genders);

        //set the current date to the real world current date
        currentDateLabel.setText(now.toString());

        //initialize the program with its first date (current day), which currently is empty on start up
        dateListHashMap.put(currentDateInProgram, new ArrayList<>());

        //turn off all the animations for the graph (because it screws everything up)
        datesXAxis.setAnimated(false);
        caloriesYAxis.setAnimated(false);
        datesVsCaloriesGraph.setAnimated(false);
    }

    /**
     * This function updates the hashmap that stores the selected date as a key, and both the food and exercise maps as values
     */
    void updateDateHashmap() {
        //put the current foodMap and exerciseMap into a list to be stored in the hashmap
        ArrayList<UserMapData> maps = new ArrayList<>() {{
            add(foodMap);
            add(exerciseMap);
        }};
        /* store the two maps with their respective day inside the hashmap, only if at least one of them has data
        otherwise, don't add that date to the hashmap */
        if (!maps.isEmpty()) {
            dateListHashMap.put(currentDateInProgram, maps);
        }
    }

    /**
     * This function allows the user to pick their current date using a calendar popup in the program. Called every time
     * the user switches the day, so the program can store the foodMap and exerciseMap to the specific day they were
     * editing it for
     */
    @FXML
    void datePickerPicked() {

        //save the programs current foodMap and exerciseMap to the hashmap of dates, with the old date as the identifier

        //put the current foodMap and exerciseMap into the date hashmap
        updateDateHashmap();

        //old maps are stored into the dateHashMap, so make some blank maps for the new day
        foodMap = new Food();
        exerciseMap = new Exercise();

        //old date is stored into date hashmap, so give program the newly inputted date
        LocalDate userDate = datePicker.getValue();
        currentDateInProgram = userDate;
        currentDateLabel.setText(userDate.toString());

        //getting the new foodMap and exerciseMap from the hashmap, because we switched to a different day (if there is any)

        //if the date is already in the hashmap (there is already inputted data), otherwise, just use the blank maps already created
        if (dateListHashMap.containsKey(userDate)) {
            //System.out.println("Contains date already!");
            //get the list of the stored maps from the hashmap using the date
            ArrayList<UserMapData> listOfMaps = dateListHashMap.get(userDate);
            //get each individual map from the list of maps
            UserMapData foodMapFromHashmap = listOfMaps.get(0);
            UserMapData exerciseMapFromHashmap = listOfMaps.get(1);
            //set the programs foodMap and exerciseMap to the ones from the hashmap
            foodMap = (Food) foodMapFromHashmap;
            exerciseMap = (Exercise) exerciseMapFromHashmap;
            //the inputted date does not have previous data associated with it
        }
    }

    /**
     * Allows the message updates to clear the other non updated message (either success or error)
     * @param message The error/success message to be displayed
     * @param whichLabel the choice of which label to assign the message to
     * @param inputColour the color of the message
     */
    void updateALabel(String message, String whichLabel, Color inputColour) {
        // Two different cases to see whether to update the error or success message
        if (whichLabel.equals("error")) {
            // setting the non-selected label to blank for a cleaner GUI
            successMsg.setText("");
            errorMsg.setText(message);
            // changing the color of the txt
            errorMsg.setTextFill(inputColour);
        } else if (whichLabel.equals("success")) {
            errorMsg.setText("");
            successMsg.setText(message);
            successMsg.setTextFill(inputColour);
        }
    }

    /**
     * This function allows the user to update their information in the top left of the GUI. It grabs what info is not
     * null in all the fields and updates the user info
     */
    @FXML
    void infoUpdate() {
        if(goalChoose.getValue() != null){
            User.setGoal(goalChoose.getValue());
        }
        if(genderChoose.getValue() != null){
            User.setGender(genderChoose.getValue());
        }
        // The following try catches go through all the inputs to see if the user inputs were valid (no letters only numbers)
        try {
            if (!this.ageInput.getText().equals("")) {
                this.User.setAge(Integer.parseInt(this.ageInput.getText()));
            }
            // updating the success label if there is not an error in parsing the string
            updateALabel("Info updated!","success",Color.GREEN);
        } catch (Exception var7) {
            // updating the error message if an error does occur
            this.updateALabel(this.ageInput.getText() + "is invalid for age", "error", Color.RED);
        }
        try {
            if (!this.weightInput.getText().equals("")) {
                this.User.setWeight(Double.parseDouble(this.weightInput.getText()));
            }
            updateALabel("Info updated!","success",Color.GREEN);
        } catch (Exception var6) {
            this.updateALabel(this.weightInput.getText() + " is invalid for weight", "error", Color.RED);
        }
        try {
            if (!this.heightInput.getText().equals("")) {
                this.User.setHeight(Double.parseDouble(this.heightInput.getText()));
            }
            updateALabel("Info updated!","success",Color.GREEN);
        } catch (Exception var5) {
            this.updateALabel(this.heightInput.getText() + " is invalid for height", "error", Color.RED);
        }
        try {
            if (!this.neckInput.getText().equals("")) {
                this.User.setNeckMeasurement(Double.parseDouble(this.neckInput.getText()));
            }
            updateALabel("Info updated!","success",Color.GREEN);
        } catch (Exception var4) {
            this.updateALabel(this.neckInput.getText() + " is invalid for neck measurement", "error", Color.RED);
        }
        try {
            if (!this.waistInput.getText().equals("")) {
                this.User.setWaistMeasurement(Double.parseDouble(this.waistInput.getText()));
            }
        } catch (Exception var3) {
            this.updateALabel(this.waistInput.getText() + " is invalid for waist measurement", "error", Color.RED);
        }
        try {
            if (!this.hipInput.getText().equals("")) {
                this.User.setHipMeasurement(Double.parseDouble(this.hipInput.getText()));
            }
        } catch (Exception var2) {
            this.updateALabel(this.hipInput.getText() + " is invalid for hip measurement", "error", Color.RED);
        }
    }

    /**
     * This function displays all the users inputted information in a text area below the button. If the user did not
     * enter a specific type of data (the data will display as 0)
     */
    @FXML
    void viewUserInfoButton() {
        // Using new line in order to print all the users' info in a clean manner
        String print = "Goal: " + User.getGoal() + "\nGender: " + User.getGender() + "\nAge: " + User.getAge() + "\nWeight: " + User.getWeight() + "kg\nHeight: " + User.getHeight() + "cm\nNeck: " + User.getNeckMeasurement() + "cm\nWaist: " + User.getWaistMeasurement() + "cm\nHip: " + User.getHipMeasurement() + "cm";
        viewUserInfoTextArea.setText(print);
    }

    /**
     * This function generates the users Bmi or Body fat percentage depending on which radio button they clicked
     */
    @FXML
    void generateBfBmi() {
        try{
            if(bmiRadio.isSelected()){
                // If there is not enough information to print out the bmi then tell the user that they are missing a measurement
                if (String.valueOf(this.User.getBmi()).equals("NaN")) {
                    this.updateALabel("Missing one of: weight/height", "error", Color.RED);
                }else{
                    // Printing out the users BMI by using a method in the person class
                    bmiBfPrint.setText("Bmi is " + String.format("%.2f", User.getBmi()));
                    successMsg.setText("Bmi successfully calculated");
                }
                // Using the same method as above but instead for body fat percentage
            }else if(bfRadio.isSelected()){
                if (String.valueOf(this.User.getBodyFat()).equals("NaN")) {
                    if (User.getGender().equals("man")) {
                        this.updateALabel("Missing one of: weight/height/neck/waist", "error", Color.RED);
                    } else {
                        this.updateALabel("Missing one of: weight/height/neck/waist/hip", "error", Color.RED);
                    }
                }
                bmiBfPrint.setText("Body fat % is " + String.format("%.2f", User.getBodyFat()));
                successMsg.setText("Body fat % calculated");
            }
        }catch (Exception e){
           errorMsg.setText("Missing one of: weight/height/neck/waist/hip");
        }
    }

    /**
     * This function changes the add item spot of the gui to be tailored for adding a food
     */
    @FXML
    void addFoodButtonToggled() {
        // setting other radioButton to false, which will unselect it
        addExerciseButton.setSelected(false);
        itemNameLabel.setText("Food:");
        addItemToMapButton.setText("Add food");
    }

    /**
     * This function changes the add item spot of the gui to be tailored for adding an exercise
     */
    @FXML
    void addExerciseButtonToggled() {
        // setting other radioButton to false, which will unselect it
        addFoodButton.setSelected(false);
        itemNameLabel.setText("Exercise:");
        addItemToMapButton.setText("Add exercise");
    }

    /**
     * This function declares the calculation to be a body fat calculation when asked to calculate something
     */
    @FXML
    void bodyFatToggled() {
        bmiRadio.setSelected(false);
    }

    /**
     * This function declares the calculation to be a bmi calculation when asked to calculate something
     */
    @FXML
    void bmiToggled() {
        bfRadio.setSelected(false);
    }

    /**
     * This function is called when the user clicks on the button to add a new food/exercise to the program. If their
     * call is valid, this function adds the data item to the respective map, while also updating the table view of
     * the two maps to include this new item, and refreshing the graph to incorporate the new data
     */
    @FXML
    void addItemToMapButtonClicked() {
        // seeing whether the user wants to add a food or exercise
        boolean addFoodButtonPressed = addFoodButton.isSelected();
        boolean addExerciseButtonPressed = addExerciseButton.isSelected();

        // if the user pressed either of the radio buttons do the following
        if (addFoodButtonPressed || addExerciseButtonPressed) {
            if (!mapItemInput.getText().equals("") && !mapInputCalories.getText().equals("")) {
                try {
                    // getting the number of calories that the user inputted
                    int calories = Integer.parseInt(mapInputCalories.getText());
                    if (addFoodButtonPressed) {
                        // if the user entered a food then get the name of that food
                        String food = mapItemInput.getText();
                        boolean allLetters = food.chars().allMatch(Character::isLetter);
                        if (allLetters) {
                            // add the food as well as its caloric amount to a hashmap
                            foodMap.addToMap(food, calories);
                            mapItemInput.setText("");
                            mapInputCalories.setText("");
                            // updating success label for the user
                            updateALabel("Successfully inputted food!", "success", Color.GREEN);
                            viewFoodButton.setSelected(true);
                            //redraw the table to incorporate the item
                            viewFoodButtonPressed();
                            //add the new foodMap to this current day
                            updateDateHashmap();
                        } else {
                            throw new Exception();
                        }
                    } else {
                        // if the exercise radio button is pressed then do the following
                        String exercise = mapItemInput.getText();
                        boolean allLetters = exercise.chars().allMatch(Character::isLetter);
                        if (allLetters) {
                            //add the exercise name as well as the amount of calories to the exercise hashmap
                            exerciseMap.addToMap(exercise, calories);
                            updateALabel("Successfully inputted exercise!", "success", Color.GREEN);
                            viewExercisesButton.setSelected(true);
                            //redraw the table to incorporate the item
                            viewExercisesButtonPressed();
                            //add the new foodMap to this current day
                            updateDateHashmap();
                        } else {
                            throw new Exception();
                        }
                    }
                    //if there is an active graph, update it
                    if (generateGraphButton.getText().equals("Refresh")) {
                        generateGraphButtonPressed();
                    }
                } catch (Exception e) {
                    updateALabel("Please enter in strings for item inputs and integers for calories", "error" ,Color.RED);
                }
            } else {
                updateALabel("Please input both an item and its associated calories", "error", Color.RED);
            }
        } else {
            updateALabel("Please select either a food or exercise", "error", Color.RED);
        }
    }

    /**
     * This function allows the user to view all the exercises that they have performed on the programs current day,
     * by using a table on the right side of the program to show all exercise-calorie combos
     */
    @FXML
    void viewExercisesButtonPressed() {
        //if the user deselects both buttons, just don't any table
        if ((!viewFoodButton.isSelected()) && (!viewExercisesButton.isSelected())) {
            mapTable.getItems().clear();
            mapTable.getColumns().clear();
        } else {
            viewFoodButton.setSelected(false);
            //get an easy-to-access view of the exerciseMap's exercise names and associated calories
            ArrayList<ArrayList<String>> exerciseMapData = exerciseMap.getMapData();
            ArrayList<String> exerciseNames = exerciseMapData.get(0);
            ArrayList<String> calorieAmounts = exerciseMapData.get(1);

            ObservableList<TableEntry> tableEntries = FXCollections.observableArrayList();

            //adding all the names and calories into an object to later be displayed from the table
            for (int i = 0; i < exerciseNames.size(); i++) {
                tableEntries.add(new TableEntry(exerciseNames.get(i), calorieAmounts.get(i)));
            }
            //reset the table before we redraw it
            mapTable.getItems().clear();
            mapTable.getColumns().clear();

            // Using the tableColumn constructor in order to set up the table with exercises on the left-hand side and calories on the right side
            TableColumn<TableEntry, String> itemColumn = new TableColumn<>("Exercises");
            /* functionality which is really weird, but it will go and grab every field "name" from the tableEntries
            object and add all of them into the itemColumn */
            itemColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
            TableColumn<TableEntry, String> calorieColumn = new TableColumn<>("Calories");
            calorieColumn.setCellValueFactory(new PropertyValueFactory<>("calories"));

            //set the items of the table to be all the data we are adding
            mapTable.setItems(tableEntries);
            //add all the columns we will be having in our table
            mapTable.getColumns().addAll(itemColumn, calorieColumn);
        }
    }

    /**
     * This function allows the user to view all the food that they have performed on the programs current day,
     * by using a table on the right side of the program to show all food-calorie combos
     */
    @FXML
    void viewFoodButtonPressed() {
        //if the user deselects both buttons, just don't any table
        if ((!viewExercisesButton.isSelected()) && (!viewFoodButton.isSelected())) {
            mapTable.getItems().clear();
            mapTable.getColumns().clear();
        } else {
            viewExercisesButton.setSelected(false);
            //get an easy-to-access view of the foodMap's exercise names and associated calories
            ArrayList<ArrayList<String>> foodMapData = foodMap.getMapData();
            ArrayList<String> foodNames = foodMapData.get(0);
            ArrayList<String> calorieAmounts = foodMapData.get(1);

            ObservableList<TableEntry> tableEntries = FXCollections.observableArrayList();

            //adding all the names and calories into an object to later be displayed from the table
            for (int i = 0; i < foodNames.size(); i++) {
                tableEntries.add(new TableEntry(foodNames.get(i), calorieAmounts.get(i)));
            }
            //reset the table before we redraw it
            mapTable.getItems().clear();
            mapTable.getColumns().clear();

            // Using the tableColumn constructor in order to set up the table with food on the left-hand side and calories on the right side
            TableColumn<TableEntry, String> itemColumn = new TableColumn<>("Food");
            /* functionality which is really weird, but it will go and grab every field "name" from the tableEntries
            object and add all of them into the itemColumn */
            itemColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
            TableColumn<TableEntry, String> calorieColumn = new TableColumn<>("Calories");
            calorieColumn.setCellValueFactory(new PropertyValueFactory<>("calories"));

            //set the items of the table to be all the data we are adding
            mapTable.setItems(tableEntries);
            //add all the columns we will be having in our table
            mapTable.getColumns().addAll(itemColumn, calorieColumn);
        }

    }

    /**
     * This function generates a graph that shows the user their target calories for a day, and how many calories you
     * have currently consumed in the day. It generates this data for the current day, as well as the previous 6 days,
     * to give the user a comprehensive review
     */
    @FXML
    void generateGraphButtonPressed() {

        //if all the required fields to make a graph have data
        if ((!(User.getGoal() == null)) && (!(User.getWeight() == 0)) && (!(User.getHeight() == 0))) {
            //clear the graph, as we will be re drawing it
            clearGraphPressed();

            int calorieTotal = Calories.getCalorieTotal(User.getWeight(), User.getGoal());

            //setting the first bar for the bar graph (number of calories needed)
            XYChart.Series<String, Number> series = new XYChart.Series<>();
            series.setName("Daily calories needed");

            // looping through 7 days and making 7 bars for each of the days
            for (int i = 0; i < 7; i++) {
                String currentDay = currentDateInProgram.toString();
                String previousDay = LocalDate.parse(currentDay).minusDays(i).toString();

                series.getData().add(new XYChart.Data<>(previousDay, calorieTotal));
            }

            //add the max calorie bars to the graph
            datesVsCaloriesGraph.getData().add(series);

            //setting the second type of bar (number of calories eaten)
            XYChart.Series<String, Number> series2 = new XYChart.Series<>();
            series2.setName("Daily calories recorded");

            //looping through all 7 days and making the 7 bars showing how many calories are consumed for each different day
            for (int i = 0; i < 7; i++) {
                String currentDay = currentDateInProgram.toString();
                String previousDay = LocalDate.parse(currentDay).minusDays(i).toString();
                LocalDate thePreviousDate = LocalDate.parse(previousDay);

                /* if the day exists already in the hashmap, it's got some data for our graph. Otherwise, we can just
                make the bar for that day be 0 calories consumed */
                if (dateListHashMap.containsKey(thePreviousDate)) {
                    /* if both of the maps for a day are not empty (can be empty by choosing day in program but never actually
                    adding data to the maps) */
                    if (!dateListHashMap.get(thePreviousDate).isEmpty()) {
                        ArrayList<UserMapData> maps = dateListHashMap.get(thePreviousDate);
                        UserMapData foodMap = maps.get(0);
                        UserMapData exerciseMap = maps.get(1);
                        int remainingCalories = Calories.calculateRemainingCals(foodMap.getMap(), exerciseMap.getMap(), calorieTotal);

                        //add that date's bar of consumed calories
                        series2.getData().add(new XYChart.Data<>(previousDay, remainingCalories));
                    } else {
                        series2.getData().add(new XYChart.Data<>(previousDay, 0));
                    }
                } else {
                    series2.getData().add(new XYChart.Data<>(previousDay, 0));
                }
            }
            //add the bars of consumed calories to our graph
            datesVsCaloriesGraph.getData().add(series2);
            updateALabel("Successfully created graph!", "success", Color.GREEN);
            //after creating a graph, the button to "create" it again should be titled refresh
            generateGraphButton.setText("Refresh");

        //If the above doesn't go through then tell the user that they are missing a piece of information
        } else {
            updateALabel("Missing one of: goal/weight/height to create the graph", "error", Color.RED);
        }
    }

    /**
     * This function clears all data from the graph
     */
    @FXML
    void clearGraphPressed() {
        datesVsCaloriesGraph.getData().clear();
        //if there is now no graph, change the button from "refresh" to "create"
        generateGraphButton.setText("Create");
        updateALabel("Successfully cleared the graph!", "success", Color.LIMEGREEN);
    }

    /**
     * This function converts lbs to kg and inches to cm to help the user
     */
    @FXML
    void convertMeasurement() {
        // checking if the text box is not empty and if so do the conversion
        if(!lbsToKg.getText().equals("")){
            try{
                // using the universal lbs to kg conversion number in order to convert it and then print it out to the rightmost text-field
                double weightLbs = Double.parseDouble(lbsToKg.getText());
                double weightKg = weightLbs/lbsToKgConversion;
                lbsToKgOutput.setText(String.format("%.2f",weightKg) + "kg");
            }catch(Exception e){
                errorMsg.setText("Not a valid input for weight");
            }
            // if the user wants to convert inches instead do the following
        }else if(!InToCm.getText().equals("")){
            try{
                // using the inch to cm conversion convert the inputted inches into cm
                double heightInch = Double.parseDouble(InToCm.getText());
                double heightCm = heightInch * inchToCMConversion;
                // printing the converted cm measurement to the rightmost text field
                InToCmOutput.setText(String.format("%.2f",heightCm) + "cm");
            }catch(Exception e){
                errorMsg.setText("Not a valid input for height");
            }
        }
        // if the user enters both lbs and inches than do both of the above conversions simultaneously
        if(!lbsToKg.getText().equals("") && !InToCm.getText().equals("")){
            try{
                double weightLbs = Double.parseDouble(lbsToKg.getText());
                double weightKg = weightLbs/lbsToKgConversion;
                lbsToKgOutput.setText(String.format("%.2f",weightKg) + "kg");
                double heightInch = Double.parseDouble(InToCm.getText());
                double heightCm = heightInch * inchToCMConversion;
                InToCmOutput.setText(String.format("%.2f",heightCm) + "cm");
            }catch(Exception e){
                errorMsg.setText("Invalid input for weight or height");
            }
        }
    }

    /**
     * This function opens the file chooser for the user and allows them to save a txt file to their computer containing
     * all the programs' data, which can also be used as a file to load back into the program
     */
    @FXML
    void saveFile() {
        try{
            FileChooser fileChooser = new FileChooser();
            fileChooser.setInitialDirectory(new File("."));
            fileChooser.setInitialFileName("savedInfo.txt");
            //only allow the user to save files as a txt file
            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("TXT", "*.txt"));
            //open the file saving window
            File chosen = fileChooser.showSaveDialog(new Stage());
            //actually save the contents of the program as the contents of the file
            Writer.saveFile(User, dateListHashMap, chosen);
            updateALabel("File saved!", "success", Color.LIMEGREEN);
        }catch(Exception e) {
            updateALabel("Error saving a file!", "error", Color.RED);
        }
    }

    /**
     * Loads the file that the user chose from their computer. Has to be a csv formatted file, as well as formatted
     * to be loadable by our program
     */
    @FXML
    void loadFile() {
        try{
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Select a file to open");
            //only allow the user to load txt files
            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("TXT", "*.txt"));
            fileChooser.setInitialDirectory(new File("."));
            //open up the load file window
            File chosen = fileChooser.showOpenDialog(new Stage());
            //read the inputted file, and take back the new dateListHashMap data that the file contained
            dateListHashMap = Reader.readFile(User, chosen);
            successMsg.setText("File loaded!");
            /* if the current date is one of the dates we load from a file, make sure to change the programs current foodMap
            and exerciseMap to the ones in the file (or else nothing works until you choose a diff day) */
            if (dateListHashMap.containsKey(currentDateInProgram)) {
                foodMap = (Food) (dateListHashMap.get(currentDateInProgram)).get(0);
                exerciseMap = (Exercise) (dateListHashMap.get(currentDateInProgram)).get(1);
            }
        }catch(Exception e){
            errorMsg.setText("File couldn't load!");
        }
    }

    /**
     * Displays a hand dandy little pop up showing our information
     */
    @FXML
    void InfoPopup() {
        Alert ProgramInfo = new Alert(Alert.AlertType.INFORMATION, "Creators: Auric Adubofour-Poku/Colton Gowans \n" +
                "Emails: auric.adubofourpoku@ucalgary.ca/colton.gowans@ucalgary.ca \n" +
                "Version: 1.0 \n" +
                "Info: A calorie tracker for people to see their fitness journey");
        ProgramInfo.show();
        ProgramInfo.setHeaderText("Program Details");
        ProgramInfo.setTitle("Project Details");
    }

    /**
     * Used to read the file that is inputted from the command line
     */
    public void command(){
        try{
            String filename = args[0];
            File file = new File(filename);
            //get a new dateListHashMap from the info in the file
            dateListHashMap = Reader.readFile(User, file);
            updateALabel("File loaded!", "success", Color.LIMEGREEN);
            //if the current date is one of the dates we load from a file, make sure to change the programs current foodMap
            //and exerciseMap to the ones in the file (or else nothing works until you choose a diff day)
            if (dateListHashMap.containsKey(currentDateInProgram)) {
                foodMap = (Food) (dateListHashMap.get(currentDateInProgram)).get(0);
                exerciseMap = (Exercise) (dateListHashMap.get(currentDateInProgram)).get(1);
            }
        }catch(Exception e){
            updateALabel("Could not load file from command line", "error", Color.RED);
        }
    }

    /**
     * Exits the program
     */
    @FXML
    void exitProgram() {
        Platform.exit();
    }
}
