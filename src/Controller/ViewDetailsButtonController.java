package Controller;

import View.ViewDetailsWindow;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.stage.Stage;

//Class defined to switch between the screens
public class ViewDetailsButtonController implements EventHandler<ActionEvent> {
	
	
	public void handle(ActionEvent e) {

		String roomID = ((Button) e.getSource()).getId();		
		Node eNode = (Node) e.getSource();
		Stage window = (Stage)eNode.getScene().getWindow();
		ViewDetailsWindow view = new ViewDetailsWindow();
		view.loadWindow(window,roomID);
				

	}

}
