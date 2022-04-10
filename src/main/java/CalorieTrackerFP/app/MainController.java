package CalorieTrackerFP.app;

import CalorieTrackerFP.data.Exercise;
import CalorieTrackerFP.data.Food;
import CalorieTrackerFP.person.Person;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import java.io.File;
import java.io.IOException;

public class MainController {
    @FXML
    private TextField ageInput;

    @FXML
    private TextField heightInput;

    @FXML
    private Label errorMsg;

    @FXML
    private Label successMsg;

    @FXML
    private TextField hipInput;

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
    private RadioButton bodyFatPercentageButton;

    @FXML
    private RadioButton bodyMassIndexButton;

    @FXML
    private TextArea calculationTextArea;



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
            errorMsg.setText(message);
            errorMsg.setTextFill(inputColour);
        } else if (whichLabel.equals("success")) {
            successMsg.setText(message);
            errorMsg.setTextFill(inputColour);
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
    }

    @FXML
    void viewUserInfoButton(ActionEvent event) {
        viewUserInfoTextArea.setText("AYO");
    }

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

    @FXML
    void addExerciseButtonToggled(ActionEvent event) {
        addFoodButton.setSelected(false);
        itemNameLabel.setText("Exercise:");
        addItemToMapButton.setText("Add exercise");
    }

    @FXML
    void addFoodButtonToggled(ActionEvent event) {
        addExerciseButton.setSelected(false);
        itemNameLabel.setText("Food:");
        addItemToMapButton.setText("Add food");

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
                    updateALabel("Error, please enter in strings for item inputs and integers for calories", "error" ,Color.RED);
                }
            } else {
                updateALabel("Please input both an item and its associated calories", "error", Color.RED);
            }
        } else {
            updateALabel("Please select a data type to enter", "error", Color.RED);
        }
    }



}
