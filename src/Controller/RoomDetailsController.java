package Controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.ResourceBundle;
import Model.AddRoomException;
import Model.CityLodge;
import Model.HotelRoom;
import Model.InvalidIdException;
import Model.Database.DbException;
import Model.util.FileAccess;
import View.AddStandardRoom;
import View.AddSuiteRoom;
import View.CompleteMaintenanceWindow;
import View.ReturnRoomDialog;
import View.ViewDetailsWindow;
import View.rentRoomDialog;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class RoomDetailsController implements Initializable {

	@FXML
	private GridPane gridForActions;

	@FXML
	private HBox hBoxForDetails;

	@FXML
	private ImageView bigImageView;

	@FXML
	private Button backButton;

	@FXML
	private Button rentButton;

	@FXML
	private Button returnButton;

	@FXML
	private Button performMaintButton;

	@FXML
	private Button completeMaintButton;

	@FXML
	private Button goBackButton;

	@FXML
	private ListView<String> listViewForRecords;

	private static CityLodge lodge;

	private String roomID;

	public RoomDetailsController(String roomID) {

		this.roomID = roomID;
	}

	public void initialize(URL arg0, ResourceBundle arg1) {

		lodge = CityLodge.getRooms();
		goBackButton.setOnAction(new GoBackButtonController());
		showImage();
		showRoomDetails();
		displayRentalRecords();

	}

	public void handleAddRoomMenuItem(ActionEvent event) {

		AddStandardRoom view = new AddStandardRoom();
		view.loadWindow();

	}

	public void handleAddSuiteMenuItem(ActionEvent event) {

		AddSuiteRoom view = new AddSuiteRoom();
		view.loadWindow();
	}

	@FXML
	public void importDataFromMenuListener() throws InvocationTargetException {

		Stage stage = null;
		FileChooser fileChooser = new FileChooser();

		Alert alertPopup = new Alert(Alert.AlertType.ERROR);
		Alert infoPopup = new Alert(Alert.AlertType.INFORMATION);
		alertPopup.setTitle("Error Window");

		File selectedFile = fileChooser.showOpenDialog(stage);
		if (selectedFile != null) {
			String path = selectedFile.getAbsolutePath();

			try {
				FileAccess.importFromFile(lodge, path);
				infoPopup.setContentText("Importing has been succesfully completed");
				infoPopup.showAndWait();
			} catch (FileNotFoundException e1) {

				alertPopup.setContentText(e1.getMessage());
				alertPopup.setHeaderText(null);
				alertPopup.showAndWait();

			} catch (AddRoomException e1) {

				alertPopup.setContentText(e1.getMessage());
				alertPopup.setHeaderText(null);
				alertPopup.showAndWait();

			} catch (DbException e1) {

				alertPopup.setContentText(e1.getMessage());
				alertPopup.setHeaderText(null);
				alertPopup.showAndWait();
			} catch (Exception e1) {

				alertPopup.setContentText("ERROR ! File is not valid in format or data");
				alertPopup.setHeaderText(null);
				alertPopup.showAndWait();
			}
		}

	}

	@FXML
	public void exporttDataFromMenuListener(ActionEvent event) throws InvocationTargetException {

		Stage stage = null;

		Alert alertPopup = new Alert(Alert.AlertType.ERROR);
		Alert infoPopup = new Alert(Alert.AlertType.INFORMATION);
		alertPopup.setTitle("Error Window");

		DirectoryChooser directoryChooser = new DirectoryChooser();
		File selectedDir = directoryChooser.showDialog(stage);
		if (selectedDir != null) {
			String path = selectedDir.getAbsolutePath() + "/export.txt";
			try {
				FileAccess.exportToFile(lodge, path);
				infoPopup.setContentText("Exporting has been succesfully completed");
				infoPopup.showAndWait();
			} catch (FileNotFoundException e1) {

				alertPopup.setContentText(e1.getMessage());
				alertPopup.setHeaderText(null);
				alertPopup.showAndWait();

			} catch (DbException e1) {

				alertPopup.setContentText(e1.getMessage());
				alertPopup.setHeaderText(null);
				alertPopup.showAndWait();
			} catch (NullPointerException e1) {

				alertPopup.setContentText(e1.getMessage());
				alertPopup.setHeaderText(null);
				alertPopup.showAndWait();
			} catch (Exception e1) {

				alertPopup.setContentText("ERROR ! File is not valid in format or data");
				alertPopup.setHeaderText(null);
				alertPopup.showAndWait();
				e1.printStackTrace();
			}
		}

	}

	public void showImage() {

		int index = lodge.availRoomIndex(roomID);
		HotelRoom room = lodge.roomCollection().get(index);
		String imageName = room.getImageName();

		String currentDirectory = System.getProperty("user.dir");

		String path = currentDirectory + "/Images/" + imageName;
		Image image = new Image(new File(path).toURI().toString());
		bigImageView.setImage(image);
		bigImageView.setFitHeight(400);
		bigImageView.setFitWidth(280);

	}

	private void showRoomDetails() {

		int index = lodge.availRoomIndex(roomID);
		HotelRoom room = lodge.roomCollection().get(index);

		String roomID = room.getRoomId();
		int inputBeds = room.getNoOfBed();
		String noOfBeds = Integer.toString(inputBeds);
		String type = room.getRoomType();
		String features = room.getFeature();
		String status = room.getStatus();

		hBoxForDetails.getChildren().clear();
		hBoxForDetails.getChildren().add(ViewDetailsWindow.roomDetailsView(roomID, noOfBeds, type, features, status));

	}

	public void rentRoomListener() {

		rentRoomDialog rentRoomView = new rentRoomDialog();
		rentRoomView.loadWindow(roomID);

	}

	public void returnRoomListener() {

		ReturnRoomDialog returnRoomView = new ReturnRoomDialog();
		returnRoomView.loadWindow(roomID);

	}

	public void performMaintenanceListener() {

		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle("Error Alert");
		try {

			lodge.roomMaintenance(roomID);
		} catch (InvalidIdException e) {
			alert.setContentText(e.getMessage());
			alert.setHeaderText(null);
			alert.showAndWait();
		} catch (Model.MaintenanceException e) {
			alert.setContentText(e.getMessage());
			alert.setHeaderText(null);
			alert.showAndWait();
		} catch (Exception e) {
			alert.setContentText(e.getMessage());
			alert.setHeaderText(null);
			alert.showAndWait();
		}
	}

	public void completeMaintenanceListener() {

		CompleteMaintenanceWindow maintenanceView = new CompleteMaintenanceWindow();
		maintenanceView.loadWindow(roomID);

	}

	public void quitProgramListener() {

		Platform.exit();
		System.exit(0);

	}

	public void displayRentalRecords() {

		listViewForRecords.getItems().clear();

		int index = lodge.availRoomIndex(roomID);
		HotelRoom room = lodge.roomCollection().get(index);

		for (int i = 0; i < room.recentRecordsSize(); i++) {

			listViewForRecords.getItems().add(room.retrieveRecordIndex(i).getDetails());
		}

	}

}
