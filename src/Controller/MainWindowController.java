package Controller;

//Importing required packages
import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.ResourceBundle;
import Model.AddRoomException;
import Model.CityLodge;
import Model.HotelRoom;
import Model.Database.DbException;
import Model.util.DatabaseFunction;
import Model.util.FileAccess;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.HBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import View.AddStandardRoom;
import View.AddSuiteRoom;
import View.MainWindow;

//Class defined to manage all the functionality on the main page of the application
public class MainWindowController implements Initializable {

	private static CityLodge lodge;
	
	//Referencing elements form the associated fmxl file in view package
	@FXML
	private MenuItem importButton;

	@FXML
	private MenuItem exportButton;

	@FXML
	private MenuItem loadDataMenuItem;

	@FXML
	private MenuItem refreshMenuItem;
	@FXML
	private MenuItem saveDataMenuItem;

	@FXML
	private ListView<HBox> mainListView;

	private static ObservableList<HBox> listComponents;

	//Implementing initialize method to initialize the components on the screen
	@SuppressWarnings("static-access")
	public void initialize(URL arg0, ResourceBundle arg1) {

		lodge = lodge.getRooms();
		listComponents = FXCollections.observableArrayList();
		displayList();
	}

	//method to handle action on add room using the menu item
	public void handleAddRoomMenuItem(ActionEvent event) {

		AddStandardRoom view = new AddStandardRoom();
		//loading the add room window on click
		view.loadWindow();

	}
	//method to handle action on quit the program
	public void quitProgramListener(ActionEvent event) {

		Platform.exit();
		System.exit(0);

	}
	
	//method to handle action on add suite type room using the menu item
	public void handleAddSuiteMenuItem(ActionEvent event) {

		AddSuiteRoom view = new AddSuiteRoom();
		//loading add suite room dialog on click
		view.loadWindow();
	}

	//method to handle import data menu item
	@FXML
	public void importDataFromMenuListener() throws InvocationTargetException {

		Stage stage = null;
		FileChooser fileChooser = new FileChooser();

		Alert alertPopup = new Alert(Alert.AlertType.ERROR);
		Alert infoPopup = new Alert(Alert.AlertType.INFORMATION);
		alertPopup.setTitle("Error Window");
		
		//Opening the file explorer to choose the file to import
		File selectedFile = fileChooser.showOpenDialog(stage);
		
		if (selectedFile != null) {
			
			String path = selectedFile.getAbsolutePath();
			
			try {
				
				mainListView.getItems().clear();
				FileAccess.importFromFile(lodge, path);
				displayList();
				infoPopup.setContentText("Importing has been succesfully completed");
				infoPopup.showAndWait();
				
			} catch (FileNotFoundException e1) {

				alertPopup.setContentText(e1.getMessage());
				alertPopup.setHeaderText("Something Went Wrong");
				alertPopup.showAndWait();

			} catch (AddRoomException e1) {

				alertPopup.setContentText(e1.getMessage());
				alertPopup.setHeaderText("Something Went Wrong");
				alertPopup.showAndWait();

			} catch (DbException e1) {

				alertPopup.setContentText(e1.getMessage());
				alertPopup.setHeaderText("Something Went Wrong");
				alertPopup.showAndWait();
			} catch (Exception e1) {

				alertPopup.setContentText("ERROR ! File is not valid in format or data");
				alertPopup.setHeaderText(null);
				alertPopup.showAndWait();
			}
		}

	}

	//method to handle export data menu item
	@FXML
	public void exporttDataFromMenuListener(ActionEvent event) throws InvocationTargetException {

		Stage stage = null;

		Alert alertPopup = new Alert(Alert.AlertType.ERROR);
		Alert infoPopup = new Alert(Alert.AlertType.INFORMATION);
		alertPopup.setTitle("Error Window");

		DirectoryChooser directoryChooser = new DirectoryChooser();
		File selectedDir = directoryChooser.showDialog(stage);

		System.out.println(selectedDir.getAbsolutePath() + "/export.txt");
		if (selectedDir != null) {
			
			String path = selectedDir.getAbsolutePath() + "/export.txt";
			
			try {
				
				FileAccess.exportToFile(lodge, path);
				infoPopup.setContentText("Exporting has been succesfully completed");
				infoPopup.showAndWait();
				
			} catch (FileNotFoundException e1) {

				alertPopup.setContentText(e1.getMessage());
				alertPopup.setHeaderText("Something Went Wrong");
				alertPopup.showAndWait();

			} catch (DbException e1) {

				alertPopup.setContentText(e1.getMessage());
				alertPopup.setHeaderText("Something Went Wrong");
				alertPopup.showAndWait();
				
			} catch (NullPointerException e1) {

				alertPopup.setContentText(e1.getMessage());
				alertPopup.setHeaderText("Something Went Wrong");
				alertPopup.showAndWait();
				
			} catch (Exception e1) {

				alertPopup.setContentText("ERROR ! File is not valid in format or data");
				alertPopup.setHeaderText("Something Went Wrong");;
				alertPopup.showAndWait();

			}
		}

	}
	
	//Method to display the list of room images and details on the front page
	private void displayList() {

		listComponents.clear();
		mainListView.getItems().clear();

		getRooms();

		mainListView.setCellFactory(new Callback<ListView<HBox>, ListCell<HBox>>() {

			@Override
			public ListCell<HBox> call(ListView<HBox> param) {
				// TODO Auto-generated method stub

				ListCell<HBox> cell = new ListCell<HBox>() {
					protected void updateItem(HBox item, boolean empty) {
						super.updateItem(item, empty);
						if (item != null) {
							setGraphic(item);
						}
					}
				};
				return cell;
			}

		});

		mainListView.setItems(listComponents);
	}
	

	public void refreshDataListener() {

		try {
			mainListView.getItems().clear();
			DatabaseFunction.writefunction(lodge);
			displayList();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Alert alertPopup = new Alert(Alert.AlertType.ERROR);
			alertPopup.setTitle("Error Alert");
			alertPopup.setContentText(e.getMessage());
			alertPopup.showAndWait();
		}
	}

	
	public void loadDataListener() {

		try {
			
			mainListView.getItems().clear();
			DatabaseFunction.readFunction(lodge);
			displayList();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			Alert alertPopup = new Alert(Alert.AlertType.ERROR);
			alertPopup.setTitle("Error Alert");
			alertPopup.setContentText(e.getMessage());
			alertPopup.showAndWait();
		}

	}

	private void getRooms() {
		
		String id = "";
		String beds = "";
		String type = "";
		String features = "";
		String status = "";
		String imageName = "";
		int inputBed = 0;

		for (int i = 0; i < lodge.roomCollectionSize(); i++) {

			HotelRoom room = lodge.roomCollection().get(i);

			id = room.getRoomId();
			inputBed = room.getNoOfBed();
			beds = Integer.toString(inputBed);
			type = room.getRoomType();
			features = room.getFeature();
			status = room.getStatus();
			imageName = room.getImageName();

			listComponents.add(MainWindow.roomListView(id, beds, features, status, type, imageName));
		}
	}

	public void saveDataListener() {

		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle("Error Alert");
		try {
			
			DatabaseFunction.writefunction(lodge);
			
		} catch (Exception e) {
			
			alert.setContentText("Error: Could not Write to Database");
			alert.setHeaderText(null);
			alert.showAndWait();
		}
	}

}
