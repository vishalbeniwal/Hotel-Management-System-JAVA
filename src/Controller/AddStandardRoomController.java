package Controller;

//Importing required packages
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import Model.AddRoomException;
import Model.InvalidIdException;
import Model.InvalidInputException;
import Model.Database.DbException;
import Model.CityLodge;

//This Controller class will define actions for "add standard room" from the menu item
public class AddStandardRoomController implements Initializable {
	
	//Referencing elements form the associated fmxl file in view package
	@FXML
	private Label noOfBeds;	
	@FXML
	private Label roomID;		
	@FXML
	private Label features;	
	@FXML 
	private TextField addRoomIdField;	
	@FXML 
	private TextField addRoomBedsField;
	@FXML 
	private TextField addRoomFeaturesField;	
	@FXML 
	private Button addRoomOKButton;
	@FXML 
	private Button addRoomCancelButton;
	
	private static CityLodge lodge;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		lodge = CityLodge.getRooms();
	}
	
	//Defining method to take action when clicked OK button on the dialog box for adding standard room
	public void addRoomOkButtonlistner(ActionEvent event) throws DbException {
		
		Node eNode = (Node) event.getSource();
	    Stage window = (Stage)eNode.getScene().getWindow();
		
		String roomId = addRoomIdField.getText().trim();
		String noOfBeds = addRoomBedsField.getText().trim();
		String features = addRoomFeaturesField.getText().trim();
		
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle("Error Display");
		
		try {
			
			if(roomId.length()==0 || noOfBeds.length()==0 || features.length()==0) {
				
				throw new InvalidInputException("ALERT: Fields can not be empty");
			}
			//adding room object using method defined in CityLodge class in model package
			lodge.addRoom(roomId, noOfBeds, "Standard",features,"Available","");
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
		catch (NumberFormatException e) {
			
			alert.setContentText("Invalid Input for the number of beds. Please input a number.");
			alert.setHeaderText("Something Went Wrong");
			alert.showAndWait();
		}
		catch (AddRoomException e) {
			
			alert.setContentText(e.getMessage());
			alert.setHeaderText("Something Went Wrong");
			alert.showAndWait();
		}
	}
		
	//Defining method to take action when clicked CANCEL button on the dialog box for adding standard room
	public void addRoomCancelButtonListner(ActionEvent event) {
		
		Node eNode = (Node) event.getSource();
		Stage window = (Stage)eNode.getScene().getWindow();
		window.close();
	}
			
}
