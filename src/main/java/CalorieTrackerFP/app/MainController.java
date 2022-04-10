package CalorieTrackerFP.app;

import CalorieTrackerFP.person.Person;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

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

    Person User = new Person();

    private String[] goals = new String[]{"muscle", "loss", "maintenance"};
    private String[] genders = new String[]{"man", "woman"};

    @FXML
    public void initialize(){
        goalChoose.getItems().addAll(goals);
        genderChoose.getItems().addAll(genders);
    }

    @FXML
    void infoUpdate(MouseEvent event) {
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
}
