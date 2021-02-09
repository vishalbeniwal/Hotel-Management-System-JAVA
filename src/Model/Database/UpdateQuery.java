package Model.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.scene.control.Alert;



public class UpdateQuery {
	
	public static void updateRoomDetails(String status,String inputid) throws DbException, SQLException {
		
		final String DB_NAME = "CityLodgeDB";
		
	     Alert alertPopup = new Alert(Alert.AlertType.ERROR);
	     alertPopup.setTitle("Error Alert");
	     Alert infoPopup = new Alert(Alert.AlertType.INFORMATION);
	     infoPopup.setTitle("Message Window");

//		try (Connection con = ConnectionTest.getConnection(DB_NAME);
//				PreparedStatement stmt = con.prepareStatement("UPDATE TEST SET status = ? "
//						+ "WHERE id = ?");) {
			
		try (Connection con = ConnectionTest.getConnection(DB_NAME); Statement stmt = con.createStatement();) {
			
			String query = "UPDATE room SET Room_Status ="+"'"+status+"'"+" WHERE Room_Id ="+"'"+inputid+"'"+";";
			
			int result = stmt.executeUpdate(query);
			
			con.commit();
			
			infoPopup.setContentText("Status has been updated to " + status);
			infoPopup.showAndWait();
		
		
		} catch (SQLException e) {
			
			alertPopup.setContentText(e.getMessage());
			alertPopup.showAndWait();
			
		} catch (Exception e) {
	         
			alertPopup.setContentText("Problem encountered");
	        alertPopup.showAndWait();
			e.printStackTrace();
		}
	}
}