package CalorieTrackerFP.app;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

public class MainController {

    @FXML
    private TextArea viewUserInfoTextArea;

    @FXML
    void viewUserInfoButton(ActionEvent event) {
        viewUserInfoTextArea.setText("AYO");
    }

}
