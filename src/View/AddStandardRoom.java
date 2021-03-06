package View;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class AddStandardRoom {
	
	public void loadWindow() {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle("Error Alert");
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/View/AddRoomStandardView.fxml"));
			Parent root = loader.load();		
			Scene scene = new Scene(root);
		
			Stage primaryStage = new Stage();
			primaryStage.setTitle("Add Room Window");
			primaryStage.setScene(scene);
			primaryStage.initModality(Modality.APPLICATION_MODAL); 
			primaryStage.showAndWait();
		}
		catch (IOException e1) {
			alert.setContentText("Error: Could not load Add Room Window");
			alert.setHeaderText(null);
			alert.showAndWait();
		}
	}
}