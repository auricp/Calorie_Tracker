module CPSC233Demo3 {
    requires javafx.controls;
    requires javafx.fxml;

    opens CalorieTrackerFP.app to javafx.fxml;
    exports CalorieTrackerFP.app;
}