package gov.iti.jets;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
// import javafx.application.Application;
import javafx.event.*;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.*;
// import javafx.stage.*;
import javafx.scene.input.*;


public class MainController {

    @FXML
    private MenuItem mNew;

	@FXML
    private BorderPane border;

    @FXML
    private MenuItem mOpen;

    @FXML
    private MenuItem mSave;

    @FXML
    private MenuItem mExit;

    @FXML
    private MenuItem mUndo;

    @FXML
    private MenuItem mCut;

    @FXML
    private MenuItem mCopy;

    @FXML
    private MenuItem mPaste;

    @FXML
    private MenuItem mDelete;

    @FXML
    private MenuItem mSelect;

    @FXML
    private MenuItem mAbout;

    @FXML
    private TextArea area;

    private Stage stage;

	private File saveFile;

	private boolean edited= false;
    // Method to set Stage reference
    public void setStage(Stage stage) {
        this.stage = stage;
    }

	public void setArea(String area){
		this.area.setText(area);
	}
    @FXML
    private void initialize() {
	mNew.setAccelerator(new KeyCodeCombination(KeyCode.N, KeyCombination.CONTROL_DOWN));
	mOpen.setAccelerator(new KeyCodeCombination(KeyCode.O, KeyCombination.CONTROL_DOWN));
	mSave.setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN));
	mNew.setOnAction(new EventHandler<ActionEvent>() {
		@Override
		public void handle(ActionEvent event) {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/notepadd.fxml"));
		BorderPane root2 = new BorderPane();
		try {
			root2 = loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		MainController controller = loader.getController();
		
		Scene secondScene2 = new Scene(root2, 640, 480);
		
		Stage newWindow2 = new Stage();
		controller.setStage(newWindow2);
			newWindow2.setTitle("Notepad ta2leed");
			newWindow2.setScene(secondScene2);
			
			newWindow2.setX(stage.getX() + 100);
			newWindow2.setY(stage.getY() + 50);
			
			newWindow2.show();

	}
});
		
mOpen.setOnAction(new EventHandler<ActionEvent>() {
	@Override
	public void handle(ActionEvent event) {
		FileChooser fil_chooser = new FileChooser();
		File filee = fil_chooser.showOpenDialog(stage);
		StringBuilder content = new StringBuilder();
		if(filee != null){
			// Files.write( Paths.get(file), area.getText().getBytes(),"utf8");
			try (
			FileReader fw = new FileReader(filee)) {
				int nextChar;
				while ((nextChar = fw.read()) != -1) {
					content.append((char) nextChar);
				}
				// area.setText(String.valueOf(content));

			} catch (IOException e1) {
				e1.printStackTrace();
			}
			String txt = String.valueOf(content);
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/notepadd.fxml"));
			BorderPane root2 = new BorderPane();
			try {
				root2 = loader.load();
			} catch (IOException e) {
				e.printStackTrace();
			}
			MainController controller = loader.getController();
			controller.setArea(txt);
			Scene secondScene2 = new Scene(root2, 640, 480);
			
			Stage newWindow2 = new Stage();
			controller.setStage(newWindow2);
			newWindow2.setTitle("High copy notepad");
			newWindow2.setScene(secondScene2);
			
			newWindow2.setX(stage.getX() + 100);
			newWindow2.setY(stage.getY() + 50);
			
			newWindow2.show();
		}
	}
});


	mSave.setOnAction(e -> {
		edited = true;
		stage.setTitle("High copy notepad");
		// DirectoryChooser directoryChooser = new DirectoryChooser();
		// File selectedDirectory = directoryChooser.showDialog(stage);
		if(saveFile ==null){

			FileChooser fil_chooser = new FileChooser();
			File filee = fil_chooser.showSaveDialog(stage);
			saveFile = filee;
			if(filee != null){
				// Files.write( Paths.get(file), area.getText().getBytes(),"utf8");
				try (
					FileWriter fw = new FileWriter(filee)) {
						fw.write(area.getText());
					} catch (IOException e1) {
						e1.printStackTrace();
					}
                }
			}else{
				try (
					FileWriter fw = new FileWriter(saveFile)) {
						fw.write(area.getText());
					} catch (IOException e1) {
						e1.printStackTrace();
					}
			}
				
	});

	area.setOnKeyPressed((e) ->{
		stage.setTitle("*High copy notepad");
		edited = false;
	});

	mExit.setOnAction(e -> {
        if(!edited){
        Alert alert = new Alert(AlertType.CONFIRMATION, "Didn't save, Are you sure you want to exit?", ButtonType.YES, ButtonType.NO);
        alert.initOwner(stage);
		alert.initModality(Modality.WINDOW_MODAL);
		alert.showAndWait();
        if (alert.getResult() == ButtonType.YES) {
            stage.close();
        }
        }else{
           
        stage.close();
        }
	});

	
	mUndo.setOnAction(e -> {
	area.undo();

	});	

	mCut.setOnAction(e -> {
	String selected = area.getSelectedText();
	Clipboard clipboard = Clipboard.getSystemClipboard();
	ClipboardContent content = new ClipboardContent();
	content.putString(selected);
	clipboard.setContent(content);
	IndexRange range = area.getSelection();
	area.deleteText(range);

	});

	mCopy.setOnAction(e -> {
	String selected = area.getSelectedText();
	Clipboard clipboard = Clipboard.getSystemClipboard();
	ClipboardContent content = new ClipboardContent();
	content.putString(selected);
	clipboard.setContent(content);
	});

	mSelect.setOnAction(e -> {
	area.selectAll();
	});
	// area.minHeightProperty().bind(border.heightProperty());
	// area.minWidthProperty().bind(border.widthProperty());
	mPaste.setOnAction(e ->{
	String pasteString = Clipboard.getSystemClipboard().getString();
	IndexRange range = area.getSelection();
	//area.deleteText(range);
	area.replaceText(range,pasteString);
	//System.out.println(pasteString);

	});

	mDelete.setOnAction(e ->{

		IndexRange range = area.getSelection();
		area.deleteText(range);
	});

	mAbout.setOnAction(e->{
				Label secondLabel = new Label("NotePad chinese version.");
				StackPane secondaryLayout = new StackPane();
				secondaryLayout.getChildren().add(secondLabel);

				Scene secondScene = new Scene(secondaryLayout, 230, 100);

				Stage newWindow = new Stage();
				newWindow.setTitle("About");
				newWindow.setScene(secondScene);

				newWindow.setX(stage.getX() + 200);
				newWindow.setY(stage.getY() + 100);

				newWindow.show();
	});

    }

}