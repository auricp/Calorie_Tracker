package CalorieTrackerFP.app;

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

public class MainController {

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
    private TableColumn<?, ?> itemColumn;

    @FXML
    private TableColumn<?, ?> caloriesColumn;

    private String[] goals = new String[]{"muscle", "loss", "maintenance"};
    private String[] genders = new String[]{"man", "woman"};

    Person User;
    Food foodMap;
    Exercise exerciseMap;

    LocalDate now = LocalDate.from(LocalDateTime.now());
    LocalDate previousDate = now;

    HashMap<LocalDate, ArrayList<UserMapData>> dateListHashMap = new HashMap<>();

    @FXML
    public void initialize(){
        User = new Person();
        foodMap = new Food();
        exerciseMap = new Exercise();

        goalChoose.getItems().addAll(goals);
        genderChoose.getItems().addAll(genders);

        System.out.println("current date: " + now);
    }

    @FXML
    void datePickerPicked(ActionEvent event) {

        //save the programs current foodMap and exerciseMap to the hashmap of dates, with the old date as the identifier

        //System.out.println(datePicker.getValue());
        LocalDate userDate = datePicker.getValue();

        //System.out.println("adding to date " + previousDate);

        //put the current foodMap and exerciseMap into a list to be stored in the hashmap
        ArrayList<UserMapData> maps = new ArrayList<>() {{
            add(foodMap);
            add(exerciseMap);
        }};

        //store the two maps with their respective day inside the hashmap
        dateListHashMap.put(previousDate, maps);

        //old maps are stored into the dateHashMap, so make some blank objects
        foodMap = new Food();
        exerciseMap = new Exercise();

        for (LocalDate hashmapDate : dateListHashMap.keySet()) {
            System.out.println("Date: " + hashmapDate + " Maps: " + dateListHashMap.get(hashmapDate));
        }
        System.out.println("\n");

        previousDate = userDate;

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
        User.setGoal(goalChoose.getValue());
        User.setGender(genderChoose.getValue());
        try{
            if(ageInput.getText().equals("")){
                System.out.print("Wont update age");
            }else{
                User.setAge(Integer.parseInt(ageInput.getText()));
            }
        }catch(Exception e){
            System.out.print("Error");
            errorMsg.setText(ageInput.getText() + "is invalid for age");
        }
        try{
            if(weightInput.getText().equals("")){
                System.out.print("Wont update weight");
            }else{
                User.setWeight(Double.parseDouble(weightInput.getText()));
            }
        }catch(Exception e){
            errorMsg.setText(weightInput.getText() + " is invalid for weight");
            System.out.print("Error");
        }
        try{
            if(heightInput.getText().equals("")){
                System.out.print("Wont update height");
            }else{
                User.setHeight(Double.parseDouble(heightInput.getText()));
            }
        }catch(Exception e){
            errorMsg.setText(heightInput.getText() + " is invalid for height");
            System.out.print("Error");
        }
        try{
            if(neckInput.getText().equals("")){
                System.out.print("Wont update neck measurements");
            }else{
                User.setNeckMeasurement(Double.parseDouble(neckInput.getText()));
            }
        }catch(Exception e){
            errorMsg.setText(neckInput.getText() + " is invalid for neck measurement");
            System.out.print("Error");
        }
        try{
            if(waistInput.getText().equals("")){
                System.out.print("Wont update waist measurement");
            }else{
                User.setWaistMeasurement(Double.parseDouble(waistInput.getText()));
            }
        }catch(Exception e){
            errorMsg.setText(waistInput.getText() + " is invalid for waist measurement");
            System.out.print("Error");
        }
        try{
            if(hipInput.getText().equals("")){
                System.out.print("Wont update hip measurement");
            }else{
                User.setHipMeasurement(Double.parseDouble(hipInput.getText()));
            }
        }catch(Exception e){
            errorMsg.setText(hipInput.getText() + " is invalid for hip measurement");
            System.out.print("Error");
        }
        successMsg.setText("User info updated!");
        System.out.print("\nBmi is " + User.getBmi() );
    }

    @FXML
    void viewUserInfoButton(ActionEvent event) {
        viewUserInfoTextArea.setText("AYO");
    }

    @FXML
    void generateBfBmi(MouseEvent event) {
        try{
            if(bmiRadio.isSelected()){
                bmiBfPrint.setText("Bmi is " + String.format("%.2f", User.getBmi()));
                successMsg.setText("Bmi successfully calculated");
            }else if(bfRadio.isSelected()){
                bmiBfPrint.setText("Body fat % is " + String.format("%.2f", User.getBodyFat()));
                successMsg.setText("Body fat % calculated");
            }
        }catch (Exception e){
            errorMsg.setText("Bmi could not be calculated");
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
                        } else {
                            throw new Exception();
                        }
                    } else {
                        String exercise = mapItemInput.getText();
                        boolean allLetters = exercise.chars().allMatch(Character::isLetter);
                        if (allLetters) {
                            exerciseMap.addToMap(exercise, calories);
                            updateALabel("Successfully inputted exercise!", "success", Color.GREEN);
                        } else {
                            throw new Exception();
                        }
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
    void viewExercisesButtonPressed(ActionEvent event) {
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
    void viewFoodButtonPressed(ActionEvent event) {
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
}
