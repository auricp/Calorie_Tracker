package CalorieTrackerFP.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * The main class of the program, runs the first functions that are run in the program
 */
public class Main extends Application {

    public static final String version = "1.0";

    /**
     * Ran on first start up, does some pretty standard stuff
     * @param args command line arguments that the user typed in
     */
    public static void main(String[] args) {
        //give the arguments to our mainController
        MainController.args = args;
        launch(args);
    }

    /**
     * loads up our program window by using our fxml file data
     * @param stage basically the program window
     * @throws IOException throws an error if it can't find the fxml file
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Main.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1200, 800);
        stage.setTitle("Calorie Tracker" + version);
        stage.setScene(scene);
        stage.show();
    }
}
