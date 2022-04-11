package CalorieTrackerFP.app;

import CalorieTrackerFP.data.Exercise;
import CalorieTrackerFP.data.Food;
import CalorieTrackerFP.data.TableEntry;
import CalorieTrackerFP.person.Person;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.util.List;

public class MainController {
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

    Person User = new Person();

    Food foodMap = new Food();

    Exercise exerciseMap = new Exercise();

    private String[] goals = new String[]{"muscle", "loss", "maintenance"};
    private String[] genders = new String[]{"man", "woman"};

    @FXML
    public void initialize(){
        goalChoose.getItems().addAll(goals);
        genderChoose.getItems().addAll(genders);


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

    }

    @FXML
    void addExerciseButtonToggled(ActionEvent event) {
        addFoodButton.setSelected(false);
        itemNameLabel.setText("Exercise:");
        addItemToMapButton.setText("Add exercise");
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
        viewFoodButton.setSelected(false);
        List[] exerciseMapData = exerciseMap.getMapData();
        List<String> exerciseNames = exerciseMapData[0];
        List<String> calorieAmounts = exerciseMapData[1];

        ObservableList<TableEntry> tableEntries = FXCollections.observableArrayList();

        for (int i = 0; i < exerciseNames.size(); i++) {
            System.out.println(exerciseNames.get(i));
            System.out.println(calorieAmounts.get(i));
            tableEntries.add(new TableEntry(exerciseNames.get(i), calorieAmounts.get(i)));
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

    @FXML
    void viewFoodButtonPressed(ActionEvent event) {
        viewExercisesButton.setSelected(false);
        List[] foodMapData = foodMap.getMapData();
        List<String> foodNames = foodMapData[0];
        List<String> calorieAmounts = foodMapData[1];

        ObservableList<TableEntry> tableEntries = FXCollections.observableArrayList();

        for (int i = 0; i < foodNames.size(); i++) {
            System.out.println(foodNames.get(i));
            System.out.println(calorieAmounts.get(i));
            tableEntries.add(new TableEntry(foodNames.get(i), calorieAmounts.get(i)));
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
