package CalorieTrackerFP.app;

import CalorieTrackerFP.calories.Calories;
import CalorieTrackerFP.data.Exercise;
import CalorieTrackerFP.data.Food;
import CalorieTrackerFP.data.TableEntry;
import CalorieTrackerFP.data.UserMapData;
import CalorieTrackerFP.person.Person;
import CalorieTrackerFP.util.Reader;
import CalorieTrackerFP.util.Writer;
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
    private Button generateGraphButton;

    @FXML
    private BarChart<String, Number> datesVsCaloriesGraph;

    @FXML
    private CategoryAxis datesXAxis;

    @FXML
    private NumberAxis caloriesYAxis;



    private String[] goals = new String[]{"muscle", "loss", "maintenance"};
    private String[] genders = new String[]{"man", "woman"};

    Person User;
    Food foodMap;
    Exercise exerciseMap;

    LocalDate now = LocalDate.from(LocalDateTime.now());

    LocalDate currentDateInProgram = now;

    HashMap<LocalDate, ArrayList<UserMapData>> dateListHashMap = new HashMap<>();

    @FXML
    public void initialize(){
        User = new Person();
        foodMap = new Food();
        exerciseMap = new Exercise();

        goalChoose.getItems().addAll(goals);
        genderChoose.getItems().addAll(genders);

        System.out.println("current date: " + now);
        currentDateLabel.setText(now.toString());

        dateListHashMap.put(currentDateInProgram, new ArrayList<>());

        datesXAxis.setAnimated(false);
        caloriesYAxis.setAnimated(false);
        datesVsCaloriesGraph.setAnimated(false);
    }

    public void commandFile(File filename) {
        try {
            Reader.readFile(this.User, filename);
            this.updateALabel("File loaded!", "success", Color.GREEN);
        } catch (Exception var3) {
            this.updateALabel("File not loaded", "error", Color.RED);
        }

    }

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
            System.out.println("Date: " + hashmapDate + " Maps: " + dateListHashMap.get(hashmapDate));
        }

        //getting the new foodMap and exerciseMap from the hashmap, because we switched to a different day

        //if the date is already in the hashmap (there is already inputted data)
        if (dateListHashMap.containsKey(userDate)) {
            System.out.println("Contains date already!");
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
            //if this date isn't in the hashmap already, then there's no maps to load from it
        }
    }

    void updateALabel(String message, String whichLabel, Color inputColour) {
        if (whichLabel.equals("error")) {
            successMsg.setText("");
            errorMsg.setText(message);
            errorMsg.setTextFill(inputColour);
        } else if (whichLabel.equals("success")) {
            errorMsg.setText("");
            successMsg.setText(message);
            successMsg.setTextFill(inputColour);
        } else {
            //wrong input for label on our part
        }
    }

    @FXML
    void infoUpdate() {
        this.User.setGoal((String)this.goalChoose.getValue());
        this.User.setGender((String)this.genderChoose.getValue());
        try {
            if (!this.ageInput.getText().equals("")) {
                this.User.setAge(Integer.parseInt(this.ageInput.getText()));
            }
            updateALabel("Info updated!","success",Color.GREEN);
        } catch (Exception var7) {
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

    @FXML
    void viewUserInfoButton(ActionEvent event) {
        String print = "Goal: " + User.getGoal() + "\nGender: " + User.getGender() + "\nAge: " + User.getAge() + "\nWeight: " + User.getWeight() + "kg\nHeight: " + User.getHeight() + "cm\nNeck: " + User.getNeckMeasurement() + "cm\nWaist: " + User.getWaistMeasurement() + "cm\nHip: " + User.getHipMeasurement() + "cm";
        viewUserInfoTextArea.setText(print);
    }

    @FXML
    void generateBfBmi(MouseEvent event) {
        try{
            if(bmiRadio.isSelected()){
                if (String.valueOf(this.User.getBmi()).equals("NaN")) {
                    this.updateALabel("Missing one of: weight/height", "error", Color.RED);
                }else{
                    bmiBfPrint.setText("Bmi is " + String.format("%.2f", User.getBmi()));
                    successMsg.setText("Bmi successfully calculated");
                }
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

    @FXML
    void addFoodButtonToggled(ActionEvent event) {
        addExerciseButton.setSelected(false);
        itemNameLabel.setText("Food:");
        addItemToMapButton.setText("Add food");
//        mapItemInput.setText("");
//        mapInputCalories.setText("");
    }

    @FXML
    void addExerciseButtonToggled(ActionEvent event) {
        addFoodButton.setSelected(false);
        itemNameLabel.setText("Exercise:");
        addItemToMapButton.setText("Add exercise");
//        mapItemInput.setText("");
//        mapInputCalories.setText("");
    }

    @FXML
    void bodyFatToggled(ActionEvent event) {
        bmiRadio.setSelected(false);
    }

    @FXML
    void BmiToggled(ActionEvent event) {
        bfRadio.setSelected(false);
    }

    @FXML
    void addItemToMapButtonClicked(ActionEvent event) {
        boolean addFoodButtonPressed = addFoodButton.isSelected();
        boolean addExerciseButtonPressed = addExerciseButton.isSelected();

        if (addFoodButtonPressed || addExerciseButtonPressed) {
            if (!mapItemInput.equals("") && !mapInputCalories.equals("")) {
                try {
                    int calories = Integer.parseInt(mapInputCalories.getText());
                    if (addFoodButtonPressed) {
                        String food = mapItemInput.getText();
                        boolean allLetters = food.chars().allMatch(Character::isLetter);
                        if (allLetters) {
                            foodMap.addToMap(food, calories);
                            mapItemInput.setText("");
                            mapInputCalories.setText("");
                            updateALabel("Successfully inputted food!", "success", Color.GREEN);
                            viewFoodButton.setSelected(true);
                            viewFoodButtonPressed();
                            updateDateHashmap();
                        } else {
                            throw new Exception();
                        }
                    } else {
                        String exercise = mapItemInput.getText();
                        boolean allLetters = exercise.chars().allMatch(Character::isLetter);
                        if (allLetters) {
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

    @FXML
    void viewExercisesButtonPressed() {
        //if the user deselects both buttons
        if ((!viewFoodButton.isSelected()) && (!viewExercisesButton.isSelected())) {
            mapTable.getItems().clear();
            mapTable.getColumns().clear();
        } else {
            viewFoodButton.setSelected(false);
            List[] exerciseMapData = exerciseMap.getMapData();
            List exerciseNames = exerciseMapData[0];
            List calorieAmounts = exerciseMapData[1];

            ObservableList<TableEntry> tableEntries = FXCollections.observableArrayList();

            for (int i = 0; i < exerciseNames.size(); i++) {
                System.out.println(exerciseNames.get(i));
                System.out.println(calorieAmounts.get(i));
                tableEntries.add(new TableEntry((String) exerciseNames.get(i), (String) calorieAmounts.get(i)));
            }
            mapTable.getItems().clear();
            mapTable.getColumns().clear();

            TableColumn<TableEntry, String> itemColumn = new TableColumn<>("Exercises");
            itemColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

            TableColumn<TableEntry, String> calorieColumn = new TableColumn<>("Calories");
            calorieColumn.setCellValueFactory(new PropertyValueFactory<>("calories"));

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
                System.out.println(foodNames.get(i));
                System.out.println(calorieAmounts.get(i));
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
    void generateGraphButtonPressed() {

        if ((!(User.getGoal() == null)) && (!(User.getWeight() == 0)) && (!(User.getHeight() == 0))) {
            clearGraphPressed();

            int calorieTotal = Calories.getCalorieTotal(User.getWeight(), User.getGoal());

            XYChart.Series<String, Number> series = new XYChart.Series<>();
            series.setName("Daily calories needed");

            for (int i = 0; i < 7; i++) {
                String currentDay = currentDateInProgram.toString();
                String previousDay = LocalDate.parse(currentDay).minusDays(i).toString();

                System.out.println("added a new max calories");
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

                        System.out.println("added a new remaining calories (amount)");
                        series2.getData().add(new XYChart.Data<>(previousDay, remainingCalories));
                    } else {
                        System.out.println("maps did not have anything in them!");
                        System.out.println("added a new remaining calories (0) hehe");
                        series2.getData().add(new XYChart.Data<>(previousDay, 0));
                    }
                } else {
                    System.out.println("added a new remaining calories (0)");
                    series2.getData().add(new XYChart.Data<>(previousDay, 0));
                }
            }
            System.out.println("\n");
            datesVsCaloriesGraph.getData().add(series2);
            updateALabel("Successfully created graph!", "success", Color.GREEN);
            generateGraphButton.setText("Refresh");

        } else {
            updateALabel("Missing one of: goal/weight/height to create the graph", "error", Color.RED);
        }

    }

    @FXML
    void clearGraphPressed() {
        datesVsCaloriesGraph.getData().clear();
        generateGraphButton.setText("Create");
        updateALabel("Successfully cleared the graph!", "success", Color.LIMEGREEN);
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
            FileChooser fileChooser = new FileChooser();
            fileChooser.setInitialDirectory(new File("."));
            fileChooser.setInitialFileName("savedInfo.txt.txt");
            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("TXT", "*.txt"));
            File chosen = fileChooser.showSaveDialog(new Stage());
            Writer.saveFile(User, chosen);
            successMsg.setText("File saved!");
        }catch(Exception e){
            errorMsg.setText("file error");
        }
    }

    @FXML
    void loadFile(ActionEvent event) {
        try{
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Select a file to open");
            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("TXT", "*.txt"));
            fileChooser.setInitialDirectory(new File("."));
            File chosen = fileChooser.showOpenDialog((Window)null);
            //dateListHashMap = Reader.readFile(User, chosen);
            Reader.readFile(User, chosen);
            successMsg.setText("File loaded!");
        }catch(Exception e){
            errorMsg.setText("File couldn't load!");
        }
    }
}
