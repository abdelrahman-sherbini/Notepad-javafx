package gov.iti.jets;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.event.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.*;
import javafx.scene.input.*;
import javafx.scene.control.Label;
// import java.beans.EventHandler;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
// import java.lang.classfile.Label;

/**
 * JavaFX App
 */
public class App extends Application {

    @Override
    public void start(Stage stage) throws IOException {
	stage.setTitle("High copy notepad");
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/notepadd.fxml"));
		BorderPane root2 = loader.load();
		MainController controller = loader.getController();
		controller.setStage(stage);

        var scene = new Scene(root2, 640, 480);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}
