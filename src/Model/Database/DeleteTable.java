package Model.Database;

import java.sql.Connection;
import java.sql.Statement;

import javafx.scene.control.Alert;

public class DeleteTable {
	
	
	public static void deleteRoomTable() throws DbException {
		
		final String DB_NAME = "CityLodgeDB";
		final String TABLE_NAME = "room";
			
		try (Connection con = ConnectionTest.getConnection(DB_NAME);
				Statement stmt = con.createStatement();) {
				
			int result = stmt.executeUpdate("DROP TABLE room");
				
		} catch (Exception e) {
			
			Alert alertPopup = new Alert(Alert.AlertType.ERROR);
			alertPopup.setTitle("Error Alert");
			alertPopup.setContentText(e.getMessage());
			alertPopup.showAndWait();
		}
	}
	
	
	public static void deleteRecordTable() throws DbException {
		
		final String DB_NAME = "CityLodgeDB";
		final String TABLE_NAME = "record";
			
		try (Connection con = ConnectionTest.getConnection(DB_NAME);
				Statement stmt = con.createStatement();) {
				
			int result = stmt.executeUpdate("DROP TABLE record");
				
		} catch (Exception e) {
			
			Alert alertPopup = new Alert(Alert.AlertType.ERROR);
			alertPopup.setTitle("Error Alert");
			alertPopup.setContentText(e.getMessage());
			alertPopup.showAndWait();
		}
	}
}