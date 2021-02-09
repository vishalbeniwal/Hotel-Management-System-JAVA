package Controller;

//Importing required packages
import java.net.URL;

import java.util.ResourceBundle;

import Model.CityLodge;
import Model.InvalidIdException;
import Model.InvalidInputException;
import Model.MaintenanceException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;

//Defining the class to handle the operation on complete maintenance button
public class CompleteMaintenanceController implements Initializable {
	
	//Referencing elements form the associated fmxl file in view package
	@FXML
	private DatePicker completeMaintDatePicker;
	
	@FXML
	private Button completeMaintOkButton;
	
	@FXML
	private Button completeMaintCancelButton;
	
	private String roomID;
	
	public static CityLodge lodge;
	
	//Implementing the constructor for this class to maintain the room ID
	public CompleteMaintenanceController(String roomID) {
		
		this.roomID = roomID;
		lodge = CityLodge.getRooms();
	}
	
	//Implementing initialize method
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		lodge = CityLodge.getRooms();

	}	
	
	//Defining method to take action when clicked OK button on the dialog box to set complete maintenance
	public void handlecompleteMaintOkButton(ActionEvent event) throws MaintenanceException, InvalidInputException {
		
		Node eNode = (Node) event.getSource();
	    Stage window = (Stage)eNode.getScene().getWindow();

		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle("Error Display");
		
		try {
			
			if(completeMaintDatePicker.getValue()==null) {
				
				throw new InvalidInputException("ALERT: Please give input in the required fields");
			}
		
		String completeMaintenanceDate = completeMaintDatePicker.getEditor().getText();
		lodge.completeMaintenance(roomID, completeMaintenanceDate);
		window.close();
		
		}
		
		catch (InvalidIdException e) {
			alert.setContentText(e.getMessage());
			alert.setHeaderText("Something Went Wrong");
			alert.showAndWait();
		}
		catch(InvalidInputException e) {
			alert.setContentText(e.getMessage());
			alert.setHeaderText("Something Went Wrong");
			alert.showAndWait();
		}
		catch (MaintenanceException e) {
			alert.setContentText(e.getMessage());
			alert.setHeaderText("Something Went Wrong");
			alert.showAndWait();
		}
		catch (Exception e) {
			alert.setContentText(e.getMessage());
			alert.setHeaderText("Something Went Wrong");
			alert.showAndWait();
		}
	}
	
	//Defining method to take action when clicked CANCEL button on the dialog box to set complete maintenance
	public void handleCompleteMaintCancelButton(ActionEvent event) {
		
		Node eNode = (Node) event.getSource();
	    Stage window = (Stage)eNode.getScene().getWindow();
	    window.close();
	}

}
	
	
	

