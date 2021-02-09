package Controller;

import View.MainWindow;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.stage.Stage;


//Class defined to switch between the screens
public class GoBackButtonController implements EventHandler<ActionEvent> {
		
	
		public void handle(ActionEvent e) {
			
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Error Alert");
		
			Node eNode = (Node) e.getSource();
			Stage window = (Stage)eNode.getScene().getWindow();
			MainWindow view = new MainWindow();
			view.start(window);
			}
					

		}


