package View;


import java.io.IOException;

import Controller.CompleteMaintenanceController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class CompleteMaintenanceWindow  {

	
	public void loadWindow(String roomID) {
		
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle("Error Alert");
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/View/CompleteMaintenance.fxml"));
			loader.setController(new CompleteMaintenanceController(roomID));
			Parent root = loader.load();
			Scene scene = new Scene(root); 
			
			Stage dialogBoxReturn = new Stage();
			dialogBoxReturn.setTitle("Complete Maintenance"); 
			dialogBoxReturn.setScene(scene);
			dialogBoxReturn.initModality(Modality.APPLICATION_MODAL); 
			dialogBoxReturn.showAndWait();
		}
		catch(IOException e) {
			alert.setContentText("Error: Could not load Complete Maintenance Window");
			alert.setHeaderText(null);
			alert.showAndWait();
		}
	}
}
