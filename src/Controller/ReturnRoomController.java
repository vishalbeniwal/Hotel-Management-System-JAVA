package Controller;

import java.net.URL;
import java.util.ResourceBundle;

import Model.CityLodge;
import Model.InvalidIdException;
import Model.InvalidInputException;
import Model.ReturnException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;

public class ReturnRoomController {
	
	@FXML
	private DatePicker returnDatePicker;
	
	private static CityLodge lodge;
	private String roomID;
	
	public ReturnRoomController(String roomID) {
		
		this.roomID = roomID;
		lodge = CityLodge.getRooms();
	}
	
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		lodge = CityLodge.getRooms();

	}
	
	public void returnRoomOkButtonListener(ActionEvent event) {
		
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle("Error Alert");

		Node eNode = (Node) event.getSource();
		Stage window = (Stage)eNode.getScene().getWindow();
			
		try {		
	    String returnDate = returnDatePicker.getEditor().getText();
	        
	    lodge.returnRoom(roomID, returnDate);

	     window.close();
		}

		catch(InvalidInputException e) {
			alert.setContentText(e.getMessage());
			alert.setHeaderText(null);
			alert.showAndWait();
		}
		catch (InvalidIdException e) {
			alert.setContentText(e.getMessage());
			alert.setHeaderText(null);
			alert.showAndWait();
		}
		catch (ReturnException e) {
			alert.setContentText(e.getMessage());
			alert.setHeaderText(null);
			alert.showAndWait();
		}
		catch (Exception e) {
			alert.setContentText("Something Went Wrong");
			alert.setHeaderText(null);
			alert.showAndWait();
		}
	}

	
	public void returnRoomCancelButtonListener(ActionEvent event) {
		
		Node eNode = (Node) event.getSource();
		Stage window = (Stage)eNode.getScene().getWindow();
		window.close();
	}

}
