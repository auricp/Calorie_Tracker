package CalorieTrackerFP.app;

import CalorieTrackerFP.person.Person;
import CalorieTrackerFP.util.Reader;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    public static final String version = "1.0";

    public static void main(String[] args) {
        launch(args);
        if (args.length == 1) {
//            Reader.readFile()
            String fileToRead = args[0];
        }
    }

    public String filename(String[] args){
        if (args.length == 1){
           String name = args[0];
           return name;
        }
        return "0";
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Main.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1200, 800);
        stage.setTitle("Calorie Tracker" + version);
        stage.setScene(scene);
        stage.show();
    }



}
