package View;

import java.io.IOException;

import Controller.ReturnRoomController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ReturnRoomDialog {

			
	public void loadWindow(String roomID) {
		
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Error Alert");
				
			try {
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(getClass().getResource("/View/ReturnRoomView.fxml"));
				loader.setController(new ReturnRoomController(roomID));
				Parent root = loader.load();
				Scene scene = new Scene(root); 
				Stage primaryDialog = new Stage();
				primaryDialog.setTitle("Return Room Form");
				primaryDialog.setScene(scene); 
				primaryDialog.initModality(Modality.APPLICATION_MODAL); 
				primaryDialog.showAndWait();
				}
				catch(IOException e) {
					
					alert.setContentText("Error: Could not load Return Dialog Window");
					alert.setHeaderText(null);
					alert.showAndWait();
				}
			}
		}
