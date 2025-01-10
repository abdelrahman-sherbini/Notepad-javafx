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

	TextArea area = new TextArea();
	MenuBar bar = new MenuBar();


	Menu file = new Menu("File");

	MenuItem mNew = new MenuItem("New");
	MenuItem mOpen = new MenuItem("Open...");
	MenuItem mSave = new MenuItem("Save");
	SeparatorMenuItem separator = new SeparatorMenuItem();
	MenuItem mExit = new MenuItem("Exit");

	mNew.setAccelerator(new KeyCodeCombination(KeyCode.N, KeyCombination.CONTROL_DOWN));
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
			newWindow2.setTitle("High copy notepad");
			newWindow2.setScene(secondScene2);
			
			newWindow2.setX(stage.getX() + 200);
			newWindow2.setY(stage.getY() + 100);
			
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
				
				newWindow2.setX(stage.getX() + 200);
				newWindow2.setY(stage.getY() + 100);
				
				newWindow2.show();
			}
		}
	});

	mSave.setOnAction(e -> {
		// DirectoryChooser directoryChooser = new DirectoryChooser();
		// File selectedDirectory = directoryChooser.showDialog(stage);
		FileChooser fil_chooser = new FileChooser();
		File filee = fil_chooser.showSaveDialog(stage);
		if(filee != null){
			// Files.write( Paths.get(file), area.getText().getBytes(),"utf8");
		    try (
			FileWriter fw = new FileWriter(filee)) {
				fw.write(area.getText());
			} catch (IOException e1) {
				e1.printStackTrace();
			}
                }

	});


	mExit.setOnAction(e -> {

	stage.close();
	});
	file.getItems().addAll(mNew,mOpen,mSave,separator,mExit);

	Menu edit = new Menu("Edit");

	MenuItem mUndo = new MenuItem("Undo");
	SeparatorMenuItem separator2 = new SeparatorMenuItem();
	MenuItem mCut = new MenuItem("Cut");
	MenuItem mCopy = new MenuItem("Copy");
	MenuItem mPaste = new MenuItem("Paste");
	MenuItem mDelete = new MenuItem("Delete");
	SeparatorMenuItem separator3 = new SeparatorMenuItem();
	MenuItem mSelect = new MenuItem("Select All");
	
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

	edit.getItems().addAll(mUndo,separator2,mCut,mCopy,mPaste,mDelete,separator3,mSelect);

	Menu help = new Menu("Help");

	MenuItem mAbout = new MenuItem("About Notepad");

	help.getItems().add(mAbout);

	/* EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() { 
            public void handle(ActionEvent e) 
            { 
                l.setText("\t\t\t\t" + ((MenuItem)e.getSource()).getText() +  
                                                               " selected"); 
            } 
        }; */

	mAbout.setOnAction(e->{
				Label secondLabel = new Label("NotePad chinese version.");
				StackPane secondaryLayout = new StackPane();
				secondaryLayout.getChildren().add(secondLabel);

				Scene secondScene = new Scene(secondaryLayout, 230, 100);

				Stage newWindow = new Stage();
				newWindow.setTitle("About");
				newWindow.setScene(secondScene);

				newWindow.setX(stage.getX() + 100);
				newWindow.setY(stage.getY() + 50);

				newWindow.show();
	});
	bar.getMenus().addAll(file,edit,help);

        //area.setPrefHeight(480);
        //area.setPrefWidth(630);
	BorderPane root = new BorderPane();
	root.setTop(bar);
	root.setCenter(area);
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