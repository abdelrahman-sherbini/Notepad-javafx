module gov.iti.jets {
    requires javafx.controls;
    requires javafx.fxml;
    opens gov.iti.jets to javafx.fxml;

    exports gov.iti.jets;
}