package CalorieTrackerFP.app;

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
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

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
    private RadioButton generateGraph;

    @FXML
    private BarChart<String, Number> datesVsCaloriesGraph;

    @FXML
    private CategoryAxis datesXAxis;

    @FXML
    private NumberAxis caloriesYAxis;


    // The following couple of lines are setting our initial variables that we need for the controller
    private String[] goals = new String[]{"muscle", "loss", "maintenance"};
    private String[] genders = new String[]{"man", "woman"};
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

        goalChoose.getItems().addAll(goals);
        genderChoose.getItems().addAll(genders);

        //Setting up current date to have a hash map
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

        //store the two maps with their respective day inside the hashmap
        dateListHashMap.put(currentDateInProgram, maps);
    }

    /**
     * This function allows the user to pick their current date using a calendar popup in the program
     */
    @FXML
    void datePickerPicked(ActionEvent event) {

        //save the programs current foodMap and exerciseMap to the hashmap of dates, with the old date as the identifier

        updateDateHashmap();

        //old maps are stored into the dateHashMap, so make some blank objects
        foodMap = new Food();
        exerciseMap = new Exercise();

        //System.out.println(datePicker.getValue());
        LocalDate userDate = datePicker.getValue();

        currentDateInProgram = userDate;
        currentDateLabel.setText(userDate.toString());

        for (LocalDate hashmapDate : dateListHashMap.keySet()) {
        }



        //getting the new foodMap and exerciseMap from the hashmap, because we switched to a different day

        //if the date is already in the hashmap (there is already inputted data)
        if (dateListHashMap.containsKey(userDate)) {
            //get the list of the stored maps from the hashmap using the date
            ArrayList<UserMapData> listOfMaps = dateListHashMap.get(userDate);
            //get each individual map from the list of maps
            UserMapData foodMapFromHashmap = listOfMaps.get(0);
            UserMapData exerciseMapFromHashmap = listOfMaps.get(1);
            //set the programs foodMap and exerciseMap to the ones from the hashmap
            foodMap = (Food) foodMapFromHashmap;
            exerciseMap = (Exercise) exerciseMapFromHashmap;
            //the inputted date does not have previous data associated with it
        } else {
            foodMap = this.foodMap;
            exerciseMap = this.exerciseMap;
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
        } else {
            //wrong input for label on our part
        }
    }

    /**
     * This function allows the user to update their information in the top left of the GUI
     */
    @FXML
    void infoUpdate() {
        this.User.setGoal((String)this.goalChoose.getValue());
        this.User.setGender((String)this.genderChoose.getValue());
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
    void viewUserInfoButton(ActionEvent event) {
        // Using new line in order to print all of the users info in a clean manner
        String print = "Goal: " + User.getGoal() + "\nGender: " + User.getGender() + "\nAge: " + User.getAge() + "\nWeight: " + User.getWeight() + "kg\nHeight: " + User.getHeight() + "cm\nNeck: " + User.getNeckMeasurement() + "cm\nWaist: " + User.getWaistMeasurement() + "cm\nHip: " + User.getHipMeasurement() + "cm";
        viewUserInfoTextArea.setText(print);
    }

    /**
     * This function generates the users Bmi or Body fat percentage depending on which radio button they clicked
     * @param event
     */
    @FXML
    void generateBfBmi(MouseEvent event) {
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
                bmiBfPrint.setText("Body fat % is " + String.format("%.2f", User.getBodyFat()));
                successMsg.setText("Body fat % calculated");
            }
        }catch (Exception e){
           errorMsg.setText("Missing one of: weight/height/neck/waist/hip");
        }
    }

    /**
     @FXML
     void bodyFatPercentageButtonToggled(ActionEvent event) {
     bodyMassIndexButton.setSelected(false);
     //do the body fat % calc
     calculationTextArea.setText("Your body fat percentage is ");
     }

     @FXML
     void bodyMassIndexButtonToggled(ActionEvent event) {
     bodyFatPercentageButton.setSelected(false);
     //do the bmi calc
     calculationTextArea.setText("Your BMI is ");
     }
     */

    /**
     * This function clears the other radio button if this one is pressed
     */
    @FXML
    void addFoodButtonToggled(ActionEvent event) {
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
    void addExerciseButtonToggled(ActionEvent event) {
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
    void bodyFatToggled(ActionEvent event) {
        bmiRadio.setSelected(false);
    }

    /**
     * This function clears the other radio button if this one is pressed
     */
    @FXML
    void BmiToggled(ActionEvent event) {
        bfRadio.setSelected(false);
    }

    /**
     * This function adds the given food or exercise as well as its corrosponding calories to a hashmap for the specific day
     */
    @FXML
    void addItemToMapButtonClicked(ActionEvent event) {
        // seeing whether the user wants to add a food or exercise
        boolean addFoodButtonPressed = addFoodButton.isSelected();
        boolean addExerciseButtonPressed = addExerciseButton.isSelected();

        // if the user pressed either of the radio buttons do the following
        if (addFoodButtonPressed || addExerciseButtonPressed) {
            if (!mapItemInput.equals("") && !mapInputCalories.equals("")) {
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
                    // the following 3 catches are for the above errors
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
            List[] exerciseMapData = exerciseMap.getMapData();
            List exerciseNames = exerciseMapData[0];
            List calorieAmounts = exerciseMapData[1];

            ObservableList<TableEntry> tableEntries = FXCollections.observableArrayList();

            // looping through all of the exercises and adding them to the table
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

    @FXML
    void viewFoodButtonPressed() {
        //if the user deselects both buttons
        if ((!viewExercisesButton.isSelected()) && (!viewFoodButton.isSelected())) {
            mapTable.getItems().clear();
            mapTable.getColumns().clear();
        } else {
            viewExercisesButton.setSelected(false);
            List[] foodMapData = foodMap.getMapData();
            List foodNames = foodMapData[0];
            List calorieAmounts = foodMapData[1];

            ObservableList<TableEntry> tableEntries = FXCollections.observableArrayList();

            for (int i = 0; i < foodNames.size(); i++) {
                tableEntries.add(new TableEntry((String) foodNames.get(i), (String) calorieAmounts.get(i)));
            }
            mapTable.getItems().clear();
            mapTable.getColumns().clear();

            TableColumn<TableEntry, String> itemColumn = new TableColumn<>("Food");
            itemColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

            TableColumn<TableEntry, String> calorieColumn = new TableColumn<>("Calories");
            calorieColumn.setCellValueFactory(new PropertyValueFactory<>("calories"));

            mapTable.setItems(tableEntries);
            mapTable.getColumns().addAll(itemColumn, calorieColumn);
        }

    }

    @FXML
    void generateGraphButton(ActionEvent event) {
        if (generateGraph.isSelected()) {
            if ((!(User.getGoal() == null)) && (!(User.getWeight() == 0)) && (!(User.getHeight() == 0))) {

                int calorieTotal = Calories.getCalorieTotal(User.getWeight(), User.getGoal());

//        XYChart.Series<String, Number> series2 = new XYChart.Series<>();
//        series2.setName("Daily calories recorded");
//        series2.getData().add(new XYChart.Data<>("day 1", 200));
//        series2.getData().add(new XYChart.Data<>("day 2", 400));
//        series2.getData().add(new XYChart.Data<>("day 3", 2100));
//        series2.getData().add(new XYChart.Data<>("day 4", 1700));
//        series2.getData().add(new XYChart.Data<>("day 5", 2600));
//        series2.getData().add(new XYChart.Data<>("day 6", 100));
//        series2.getData().add(new XYChart.Data<>("day 7", 1500));

                XYChart.Series<String, Number> series = new XYChart.Series<>();
                series.setName("Daily calories needed");

                for (int i = 0; i < 7; i++) {
                    String currentDay = currentDateInProgram.toString();
                    String previousDay = LocalDate.parse(currentDay).minusDays(i).toString();
//                LocalDate thePreviousDate = LocalDate.parse(previousDay);

                    series.getData().add(new XYChart.Data<>(previousDay, calorieTotal));
                }

                datesVsCaloriesGraph.getData().add(series);

                XYChart.Series<String, Number> series2 = new XYChart.Series<>();
                series2.setName("Daily calories recorded");

                for (int i = 0; i < 7; i++) {
                    String currentDay = currentDateInProgram.toString();
                    String previousDay = LocalDate.parse(currentDay).minusDays(i).toString();
                    LocalDate thePreviousDate = LocalDate.parse(previousDay);

                    if (dateListHashMap.containsKey(thePreviousDate)) {
                        if (!dateListHashMap.get(thePreviousDate).isEmpty()) {
                            ArrayList<UserMapData> maps = dateListHashMap.get(thePreviousDate);
                            UserMapData foodMap = maps.get(0);
                            UserMapData exerciseMap = maps.get(1);
                            int remainingCalories = Calories.calculateRemainingCals(foodMap.getMap(), exerciseMap.getMap(), calorieTotal);


                            series2.getData().add(new XYChart.Data<>(previousDay, remainingCalories));
                        } else {
                            series2.getData().add(new XYChart.Data<>(previousDay, 0));
                        }
                    } else {
                        series2.getData().add(new XYChart.Data<>(previousDay, 0));
                    }
                }
                datesVsCaloriesGraph.getData().add(series2);
                updateALabel("Successfully created graph!", "success", Color.GREEN);
            } else {
                updateALabel("Enter in weight, height, and a goal before using the graph", "error", Color.RED);
            }
        } else {
            datesVsCaloriesGraph.getData().clear();
        }
    }

    @FXML
    void convertMeasurement(ActionEvent event) {
        if(!lbsToKg.getText().equals("")){
            try{
                double lbsToKgConversion = 2.205;
                double weightLbs = Double.parseDouble(lbsToKg.getText());
                double weightKg = weightLbs/lbsToKgConversion;
                lbsToKgOutput.setText(String.format("%.2f",weightKg) + "kg");
            }catch(Exception e){
                errorMsg.setText("Not a valid input for weight");
            }
        }else if(!InToCm.getText().equals("")){
            try{
                double inchToCmConversion = 2.54;
                double heightInch = Double.parseDouble(InToCm.getText());
                double heightCm = heightInch * inchToCmConversion;
                InToCmOutput.setText(String.format("%.2f",heightCm) + "cm");
            }catch(Exception e){
                errorMsg.setText("Not a valid input for height");
            }
        }
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

    @FXML
    void saveFile(ActionEvent event) {
        try{
            FileChooser file = new FileChooser();
            file.setInitialDirectory(new File("."));
            file.setInitialFileName("savedInfo.txt");
            file.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("TXT", "*.txt"));
            File chosen = file.showSaveDialog(new Stage());
            Writer.saveFile(User, chosen);
            successMsg.setText("File saved!");
        }catch(Exception e){
            errorMsg.setText("file error");
        }
    }

    @FXML
    void loadFile(ActionEvent event) {
        try{
            FileChooser file = new FileChooser();
            File chosen = file.showOpenDialog((Window)null);
            Reader.readFile(User,chosen);
            successMsg.setText("File loaded!");
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

    @FXML
    void exitProgram(ActionEvent event) {
        Platform.exit();
    }
}
