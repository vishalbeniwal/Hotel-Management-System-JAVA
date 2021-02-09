package View;

import Controller.RoomDetailsController;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ViewDetailsWindow {
	

		public void loadWindow(Stage primaryStage,String roomID) {
			
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Error Alert");
			
			try {
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(getClass().getResource("/View/RoomDetailsView.fxml"));
				loader.setController(new RoomDetailsController(roomID));
				Parent root = loader.load();
				Scene roomDetailsScene = new Scene(root);
				primaryStage.setTitle("View Details Window");
				primaryStage.setScene(roomDetailsScene);
				primaryStage.setResizable(false);
				primaryStage.show();
			}
			catch (Exception e) {
				
				alert.setContentText("Error: Could not load View Details Window");
				alert.setHeaderText(null);
				alert.showAndWait();
				e.printStackTrace();
			}
		}
		
		
		public static HBox roomDetailsView(String roomID,String noOfBeds, String type, String features,String status) {
			
			HBox detailedView = new HBox();
			
			VBox gridView = roomDetailsGridView(roomID,noOfBeds,type, features,status);
			
			gridView.setAlignment(Pos.CENTER_LEFT);
			
			detailedView.getChildren().add(gridView);			
						
			return detailedView;
		
		}
		
		public static VBox roomDetailsGridView(String roomID,String noOfBeds, String type, String features,String status) {
			
			
			VBox detailsBox = new VBox();
			
			GridPane grid = new GridPane();
			
			grid.add(new Label(" ID:"), 0, 0);
			grid.add(new Label(roomID),1,0);
			grid.add(new Label(" Type:"), 0, 1);
			grid.add(new Label(type),1,1);
			grid.add(new Label(" Beds:"), 0, 2);
			grid.add(new Label(noOfBeds),1,2);
			grid.add(new Label(" Status:"), 0, 3);
			grid.add(new Label(status),1,3);
			grid.add(new Label(""), 0, 4);
			grid.add(new Label(" Features:"), 0, 5);
			grid.add(new Label(features),1,5);
			
			detailsBox.setAlignment(Pos.CENTER_LEFT);
			detailsBox.getChildren().add(grid);
			
			return detailsBox;	
			
		}
		
	
		
		
}
