package Controller;

import java.net.URL;
import java.util.ResourceBundle;

import Model.CityLodge;
import Model.InvalidIdException;
import Model.InvalidInputException;
import Model.RentException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class RentRoomController implements Initializable {
	
	
	@FXML
	private TextField custIdInput;
	
	@FXML
	private TextField rentDaysInput;
	
	@FXML
	private DatePicker rentDateInput;
	
	private static CityLodge lodge;
	private String roomID;
	
	public RentRoomController(String roomID) {
		
		this.roomID = roomID;
		lodge = CityLodge.getRooms();
	}
	
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		lodge = CityLodge.getRooms();
	

	}
	
	
	public void rentRoomOkButtonListener(ActionEvent event) {
		
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle("Error Alert");

		Node eNode = (Node) event.getSource();
		Stage window = (Stage)eNode.getScene().getWindow();
		initialize(null,null);
		
		String custID = custIdInput.getText().trim();
		String InputrentDays = rentDaysInput.getText().trim();
		int rentDays = Integer.parseInt(InputrentDays);
				
		try {
			
			if(custID.length()==0 || InputrentDays.length()==0 || rentDateInput.getValue()==null) {
				
				throw new InvalidInputException("Error: Fields can not be empty.");
			}
			
				
	        String rentDate = rentDateInput.getEditor().getText();
	        
	        lodge.rentRoom(roomID, custID, rentDays, rentDate);

			window.close();
		}

		catch(InvalidInputException e) {
			alert.setContentText(e.getMessage());
			alert.setHeaderText("Something Went Wrong");
			alert.showAndWait();
		}
		catch (InvalidIdException e) {
			alert.setContentText(e.getMessage());
			alert.setHeaderText("Something Went Wrong");
			alert.showAndWait();
		}
		catch (RentException e) {
			alert.setContentText(e.getMessage());
			alert.setHeaderText("Something Went Wrong");
			alert.showAndWait();
		}
		catch (Exception e) {
			alert.setContentText("Something Went Wrong");
			alert.setHeaderText("Something Went Wrong");
			alert.showAndWait();
		}
	}
	
	public void rentRoomCancelButtonListener(ActionEvent event) {
		
		Node eNode = (Node) event.getSource();
		Stage window = (Stage)eNode.getScene().getWindow();
		window.close();
	}
		
		
		
	

}
