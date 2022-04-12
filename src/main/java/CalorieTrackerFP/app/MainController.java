package CalorieTrackerFP.app;

/*
Colton Gowans, Auric Poku
April 12th
Tut 07
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
import javafx.event.ActionEvent;
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
import javafx.stage.Window;

import java.io.File;
import java.lang.reflect.Array;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainController {

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

//    @FXML
//    private TextArea calculationTextArea;

    public static String[] args;

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
     * This function simply makes the new user as well as the food and exercise hashmaps and sets up the functionality of drop down menus and dates
     */
    @FXML
    public void initialize(){
        User = new Person();
        foodMap = new Food();
        exerciseMap = new Exercise();

        command();

        goalChoose.getItems().addAll(goals);
        genderChoose.getItems().addAll(genders);

        //System.out.println("current date: " + now);
        currentDateLabel.setText(now.toString());

        dateListHashMap.put(currentDateInProgram, new ArrayList<>());

        datesXAxis.setAnimated(false);
        caloriesYAxis.setAnimated(false);
        datesVsCaloriesGraph.setAnimated(false);
    }

    /**
     * This function updates the hashmap that stores the selected date as a key, and both the food and exercise maps as values
     */
    void updateDateHashmap() {
        //save the programs current foodMap and exerciseMap to the hashmap of dates, with the old date as the identifier

        //put the current foodMap and exerciseMap into a list to be stored in the hashmap
        ArrayList<UserMapData> maps = new ArrayList<>() {{
            add(foodMap);
            add(exerciseMap);
        }};


        //store the two maps with their respective day inside the hashmap, only if at least one of them has data
        //otherwise, don't add that date to the hashmap
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

        updateDateHashmap();

        //old maps are stored into the dateHashMap, so make some blank objects
        foodMap = new Food();
        exerciseMap = new Exercise();

        //System.out.println(datePicker.getValue());
        LocalDate userDate = datePicker.getValue();

        currentDateInProgram = userDate;
        currentDateLabel.setText(userDate.toString());

        //getting the new foodMap and exerciseMap from the hashmap, because we switched to a different day

        //if the date is already in the hashmap (there is already inputted data)
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
     * This function allows the user to update their information in the top left of the GUI
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
     * This function displays all of the users inputted information in a text area below the button. If the user did not enter a specific type of data (the data will display as 0)
     */
    @FXML
    void viewUserInfoButton() {
        // Using new line in order to print all of the users info in a clean manner
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
     * This function clears the other radio button if this one is pressed
     */
    @FXML
    void addFoodButtonToggled() {
        // setting other radioButton to false to unselect it
        addExerciseButton.setSelected(false);
        itemNameLabel.setText("Food:");
        addItemToMapButton.setText("Add food");
//        mapItemInput.setText("");
//        mapInputCalories.setText("");
    }

    /**
     * This function clears the other radio button if this one is pressed
     */
    @FXML
    void addExerciseButtonToggled() {
        addFoodButton.setSelected(false);
        itemNameLabel.setText("Exercise:");
        addItemToMapButton.setText("Add exercise");
//        mapItemInput.setText("");
//        mapInputCalories.setText("");
    }

    /**
     * This function clears the other radio button if this one is pressed
     */
    @FXML
    void bodyFatToggled() {
        bmiRadio.setSelected(false);
    }

    /**
     * This function clears the other radio button if this one is pressed
     */
    @FXML
    void BmiToggled() {
        bfRadio.setSelected(false);
    }

    /**
     * This function adds the given food or exercise as well as its corrosponding calories to a hashmap for the specific day
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
                            viewFoodButtonPressed();
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
                            viewExercisesButtonPressed();
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
     * This function allows the user to view all the exercises that they performed on a specific day on the table located on the lefthand side of the window
     */
    @FXML
    void viewExercisesButtonPressed() {
        //if the user deselects both buttons
        if ((!viewFoodButton.isSelected()) && (!viewExercisesButton.isSelected())) {
            mapTable.getItems().clear();
            mapTable.getColumns().clear();
        } else {
            // adding the hashmap for food into a list for easier access
            viewFoodButton.setSelected(false);
            ArrayList<ArrayList<String>> exerciseMapData = exerciseMap.getMapData();
            ArrayList<String> exerciseNames = exerciseMapData.get(0);
            ArrayList<String> calorieAmounts = exerciseMapData.get(1);

            ObservableList<TableEntry> tableEntries = FXCollections.observableArrayList();

            // looping through all the exercises and adding them to the table
            for (int i = 0; i < exerciseNames.size(); i++) {
                tableEntries.add(new TableEntry((String) exerciseNames.get(i), (String) calorieAmounts.get(i)));
            }
            // clearing the maps after they are added to the tables
            mapTable.getItems().clear();
            mapTable.getColumns().clear();

            // Using the tableColumn methods in order to set up the table with exercises on the lefthand side and calories on the right side
            TableColumn<TableEntry, String> itemColumn = new TableColumn<>("Exercises");
            itemColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

            TableColumn<TableEntry, String> calorieColumn = new TableColumn<>("Calories");
            calorieColumn.setCellValueFactory(new PropertyValueFactory<>("calories"));

            // setting the items in the exercise maps as the table entries
            mapTable.setItems(tableEntries);
            mapTable.getColumns().addAll(itemColumn, calorieColumn);
        }
    }

    /**
     * This function does the same as the one above, but instead of exercises it does it for food
     */
    @FXML
    void viewFoodButtonPressed() {
        //if the user deselects both buttons
        if ((!viewExercisesButton.isSelected()) && (!viewFoodButton.isSelected())) {
            mapTable.getItems().clear();
            mapTable.getColumns().clear();
        } else {
            // deselecting the other radio button and storing the info in the food hashmap to a list
            viewExercisesButton.setSelected(false);
            ArrayList<ArrayList<String>> foodMapData = foodMap.getMapData();
            ArrayList<String> foodNames = foodMapData.get(0);
            ArrayList<String> calorieAmounts = foodMapData.get(1);

            ObservableList<TableEntry> tableEntries = FXCollections.observableArrayList();

            //  looping through the food list and adding all of it to the display table
            for (int i = 0; i < foodNames.size(); i++) {
                tableEntries.add(new TableEntry((String) foodNames.get(i), (String) calorieAmounts.get(i)));
            }
            // clearing both maps so that new info can be added
            mapTable.getItems().clear();
            mapTable.getColumns().clear();

            // Setting up the table food name and food calorie enteries
            TableColumn<TableEntry, String> itemColumn = new TableColumn<>("Food");
            itemColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

            TableColumn<TableEntry, String> calorieColumn = new TableColumn<>("Calories");
            calorieColumn.setCellValueFactory(new PropertyValueFactory<>("calories"));

            mapTable.setItems(tableEntries);
            mapTable.getColumns().addAll(itemColumn, calorieColumn);
        }

    }

    /**
     * This function generates the users graph for total calories and calories currently eaten in a 7 day range
     */
    @FXML
    void generateGraphButtonPressed() {

        // clearing the graph if there is no information
        if ((!(User.getGoal() == null)) && (!(User.getWeight() == 0)) && (!(User.getHeight() == 0))) {
            clearGraphPressed();

            int calorieTotal = Calories.getCalorieTotal(User.getWeight(), User.getGoal());

            // setting the first bar for the bar graph (number of calories needed)
            XYChart.Series<String, Number> series = new XYChart.Series<>();
            series.setName("Daily calories needed");

            // looping through 7 days and adding that information to the bar graph to print
            for (int i = 0; i < 7; i++) {
                String currentDay = currentDateInProgram.toString();
                String previousDay = LocalDate.parse(currentDay).minusDays(i).toString();

                //System.out.println("added a new max calories");
                series.getData().add(new XYChart.Data<>(previousDay, calorieTotal));
            }

            datesVsCaloriesGraph.getData().add(series);

            // adding the second type of bar (number of calories eaten) to the graph
            XYChart.Series<String, Number> series2 = new XYChart.Series<>();
            series2.setName("Daily calories recorded");

            // looping through all days and getting info from hashmaps to display as a bar graph
            for (int i = 0; i < 7; i++) {
                String currentDay = currentDateInProgram.toString();
                String previousDay = LocalDate.parse(currentDay).minusDays(i).toString();
                LocalDate thePreviousDate = LocalDate.parse(previousDay);

                // Checking if the previous day is avaliable and if so print that information to the graph
                if (dateListHashMap.containsKey(thePreviousDate)) {
                    if (!dateListHashMap.get(thePreviousDate).isEmpty()) {
                        ArrayList<UserMapData> maps = dateListHashMap.get(thePreviousDate);
                        UserMapData foodMap = maps.get(0);
                        UserMapData exerciseMap = maps.get(1);
                        int remainingCalories = Calories.calculateRemainingCals(foodMap.getMap(), exerciseMap.getMap(), calorieTotal);

                        //System.out.println("added a new remaining calories (amount)");
                        series2.getData().add(new XYChart.Data<>(previousDay, remainingCalories));
                    } else {
                        series2.getData().add(new XYChart.Data<>(previousDay, 0));
                    }
                } else {
                    //System.out.println("added a new remaining calories (0)");
                    series2.getData().add(new XYChart.Data<>(previousDay, 0));
                }
            }
            //System.out.println("\n");
            datesVsCaloriesGraph.getData().add(series2);
            updateALabel("Successfully created graph!", "success", Color.GREEN);
            generateGraphButton.setText("Refresh");

            // If the above doesnt go through then tell the user that they are missing a piece of information
        } else {
            updateALabel("Missing one of: goal/weight/height to create the graph", "error", Color.RED);
        }

    }

    /**
     * This function simply clears the graph after the user presses the radio button a second time
     */
    @FXML
    void clearGraphPressed() {
        // clearing all the data on the graph and printing a success message
        datesVsCaloriesGraph.getData().clear();
        generateGraphButton.setText("Create");
        updateALabel("Successfully cleared the graph!", "success", Color.LIMEGREEN);
    }

    /**
     * This function converts lbs to kg and inches to cm to help the user
     */
    @FXML
    void convertMeasurement(ActionEvent event) {
        // checking if the textbox is not empty and if so do the conversion
        if(!lbsToKg.getText().equals("")){
            try{
                // using the universal lbs to kg conversion number in order to convert it and then print it out to the rightmost textfield
                double lbsToKgConversion = 2.205;
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
                double inchToCmConversion = 2.54;
                double heightInch = Double.parseDouble(InToCm.getText());
                double heightCm = heightInch * inchToCmConversion;
                // printing the converted cm measurement to the rightmost text field
                InToCmOutput.setText(String.format("%.2f",heightCm) + "cm");
            }catch(Exception e){
                errorMsg.setText("Not a valid input for height");
            }
        }
        // if the user enters both lbs and inches than do both of the above conversions simutaneously
        if(!lbsToKg.getText().equals("") && !InToCm.getText().equals("")){
            try{
                double lbsToKgConversion = 2.205;
                double weightLbs = Double.parseDouble(lbsToKg.getText());
                double weightKg = weightLbs/lbsToKgConversion;
                lbsToKgOutput.setText(String.format("%.2f",weightKg) + "kg");
                double inchToCmConversion = 2.54;
                double heightInch = Double.parseDouble(InToCm.getText());
                double heightCm = heightInch * inchToCmConversion;
                InToCmOutput.setText(String.format("%.2f",heightCm) + "cm");
            }catch(Exception e){
                errorMsg.setText("Invalid input for weight or height");
            }
        }
    }

    /**
     * This function opens the file chooser for the user and allows them to save a txt file wherever they want
     */
    @FXML
    void saveFile() {
        try{
            FileChooser fileChooser = new FileChooser();
            fileChooser.setInitialDirectory(new File("."));
            fileChooser.setInitialFileName("savedInfo.txt.txt");
            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("TXT", "*.txt"));
            File chosen = fileChooser.showSaveDialog(new Stage());
            Writer.saveFile(User, dateListHashMap, chosen);
            successMsg.setText("File saved!");
        }catch(Exception e){
            errorMsg.setText("file error");
        }
    }

    @FXML
    void loadFile() {
        try{
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Select a file to open");
            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("TXT", "*.txt"));
            fileChooser.setInitialDirectory(new File("."));
            File chosen = fileChooser.showOpenDialog((Window)null);
            dateListHashMap = Reader.readFile(User, chosen);
            successMsg.setText("File loaded!");
            //set the programs foodMap and exerciseMap to the newly loaded ones (start the user on the first date inputted)
            //System.out.println(dateListHashMap);
            //if the current date is one of the dates we load from a file, make sure to change the programs current foodMap
            //and exerciseMap to the ones in the file (or else nothing works until you choose a diff day)
            if (dateListHashMap.containsKey(currentDateInProgram)) {
                foodMap = (Food) (dateListHashMap.get(currentDateInProgram)).get(0);
                exerciseMap = (Exercise) (dateListHashMap.get(currentDateInProgram)).get(1);
            }
        }catch(Exception e){
            errorMsg.setText("File couldn't load!");
        }
    }

    @FXML
    void InfoPopup(ActionEvent event) {
        Alert ProgramInfo = new Alert(Alert.AlertType.INFORMATION, "Creators: Auric Adubofour-Poku/Colton Gowans \nEmails: auric.adubofourpoku@ucalgary.ca/colton.gowans@ucalgary.ca \nVersion: 1.0 \nInfo: A calorie tracker for people to see their fitness journey");
        ProgramInfo.show();
        ProgramInfo.setHeaderText("Program Details");
        ProgramInfo.setTitle("Project Details");
    }

    public void command(){
        try{
            if(args.length == 1){
                //System.out.println("file in command line");
                String filename = args[0];
                File file = new File(filename);
                dateListHashMap = Reader.readFile(User, file);
                successMsg.setText("File loaded!");
                //set the programs foodMap and exerciseMap to the newly loaded ones (start the user on the first date inputted)
                //System.out.println(dateListHashMap);
                //if the current date is one of the dates we load from a file, make sure to change the programs current foodMap
                //and exerciseMap to the ones in the file (or else nothing works until you choose a diff day)
                if (dateListHashMap.containsKey(currentDateInProgram)) {
                    foodMap = (Food) (dateListHashMap.get(currentDateInProgram)).get(0);
                    exerciseMap = (Exercise) (dateListHashMap.get(currentDateInProgram)).get(1);
                }
            } else {
                updateALabel("File from command line was not a valid input!", "error", Color.RED);
            }
        }catch(Exception e){
            updateALabel("Could not load file from command line", "error", Color.RED);
        }
    }

    @FXML
    void exitProgram(ActionEvent event) {
        Platform.exit();
    }
}
