
package Controller;

//Importing required packages
import java.util.ResourceBundle;
import Model.AddRoomException;
import Model.InvalidIdException;
import Model.InvalidInputException;
import Model.CityLodge;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.net.URL;

//This Controller class will define actions for "add Suite type room" from the menu item
public class AddSuiteRoomController implements Initializable{

	//Referencing elements form the associated fmxl file in view package
	@FXML 
	private TextField addSuiteIdField;
	@FXML 
	private TextField addSuiteBedField;
	@FXML 
	private TextField addSuiteFeaturesField;
	@FXML 
	private DatePicker lastMaintDatePicker;
	@FXML 
	private Button addSuiteButtonOk;
	@FXML 
	private Button addSuitebuttonCancel;;
	
	private static CityLodge lodge;
	
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		lodge = CityLodge.getRooms();
	}
	
	//Defining method to take action when clicked OK button on the dialog box for adding Suite type room
	public void addSuiteOkButtonListner(ActionEvent event){
		
		Node eNode = (Node) event.getSource();
		Stage window = (Stage)eNode.getScene().getWindow();
	
		String roomId = addSuiteIdField.getText().trim();
		String noOfBeds = addSuiteBedField.getText().trim();
		String features = addSuiteFeaturesField.getText().trim();
		
        Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle("Error Alert");
		
		try {
			
			if(roomId.length()==0 || noOfBeds.length()==0 || features.length()==0 || 
					lastMaintDatePicker.getValue()==null) {
				
				throw new InvalidInputException("Errors: Fields can not be empty.");
			}

	        String lastMaintenanceDate = lastMaintDatePicker.getEditor().getText();
			
	      //adding room object using method defined in CityLodge class in model package
	        lodge.addRoom(roomId, noOfBeds, "Suite",features,"Available",lastMaintenanceDate);
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
		catch (AddRoomException e) {
			
			alert.setContentText(e.getMessage());
			alert.setHeaderText("Something Went Wrong");
			alert.showAndWait();
		}
					
	}
	
	//Defining method to take action when clicked CANCEL button on the dialog box for adding suite type room
	public void addSuiteCancelButtonListner(ActionEvent event) {
		
		Node eNode = (Node) event.getSource();
		Stage window = (Stage)eNode.getScene().getWindow();
	    window.close();
	}

}
	

