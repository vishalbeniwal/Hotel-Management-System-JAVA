package View;

import java.io.File;
import java.io.IOException;


import Controller.ViewDetailsButtonController;
import Model.CityLodge;

import javafx.application.Application;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainWindow extends Application {

	public static CityLodge lodge = CityLodge.getRooms();

	public static void main(String[] args) {
		launch(args);

	}

	@Override
	public void start(Stage primaryStage)  {
		// TODO Auto-generated method stub
		
		Alert alertPop = new Alert(Alert.AlertType.ERROR);
		alertPop.setTitle("Error Alert");
		
		try {
			
			//Method to generate dummy data as per the assignment requirement
//			RandomDataGeneration.generateRandomRoomData(lodge);
//			RandomDataGeneration.generateRandomRentalRecords(lodge);

			
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/View/MainWindow.fxml"));
			Parent root = loader.load();
			Scene scene = new Scene(root);				
			
			primaryStage.setTitle("Dashboard");
			primaryStage.setScene(scene);
			primaryStage.show();		
		}
		catch (IOException e) {
			
			alertPop.setContentText("Something went wrong. There is a problem while loading the applicaton");
			alertPop.showAndWait();

			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			alertPop.setContentText("Something went wrong. There is a problem while loading the applicaton");
			alertPop.showAndWait();
		}
		
	}

	public static HBox roomListView(String roomID, String noOfBeds, String Features, String status, String type,
			String imageName) {

		HBox horizontalBox = new HBox();

		VBox verticalComponentOne = imageContainer(imageName);
		VBox verticalComponentSecond = detailsContainer(roomID, noOfBeds, Features, status, type);
		VBox verticalComponentThird = buttonContainer(roomID, Features);
		verticalComponentThird.setAlignment(Pos.CENTER_RIGHT);

		horizontalBox.getChildren().addAll(verticalComponentOne, verticalComponentSecond, verticalComponentThird);
		horizontalBox.setSpacing(40);

		return horizontalBox;

	}

	public static VBox imageContainer(String imageName) {

		VBox imageBox = new VBox();
		String currentDirectory = System.getProperty("user.dir");
		String path = currentDirectory + "/Images/" + imageName;

		Image image = new Image(new File(path).toURI().toString());
		ImageView imageview = new ImageView(image);
		imageview.setFitHeight(135);
		imageview.setFitWidth(150);
		imageBox.getChildren().add(imageview);
		return imageBox;

	}

	public static VBox detailsContainer(String roomID, String noOfBeds, String Features, String status, String type) {

		VBox detailsBox = new VBox();

		GridPane grid = new GridPane();

		grid.add(new Label("\tID:\t"), 0, 0);
		grid.add(new Label(roomID), 1, 0);
		grid.add(new Label("\tType:\t"), 0, 1);
		grid.add(new Label(type), 1, 1);
		grid.add(new Label("\tNo. of Beds:\t"), 0, 2);
		grid.add(new Label(noOfBeds), 1, 2);
		grid.add(new Label("\tStatus:"), 0, 3);
		grid.add(new Label(status), 1, 3);

		detailsBox.setAlignment(Pos.CENTER);
		detailsBox.getChildren().add(grid);

		return detailsBox;

	}

	private static VBox buttonContainer(String id, String features) {

		VBox buttonContainer = new VBox();

		GridPane grid = new GridPane();

		grid.setHgap(10);
		grid.setVgap(7);

		grid.add(new Label("Features:"), 0, 0);
		grid.add(new Label(features), 0, 1);

		Button getDetailsButton = new Button("View Details");
		getDetailsButton.setAlignment(Pos.BOTTOM_CENTER);
		getDetailsButton.setId(id);
		getDetailsButton.setOnAction(new ViewDetailsButtonController());
		grid.add(getDetailsButton, 0, 3);

		buttonContainer.getChildren().add(grid);
		return buttonContainer;
	}

}
