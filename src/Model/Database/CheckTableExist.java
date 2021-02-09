package Model.Database;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.scene.control.Alert;

public class CheckTableExist {

	public static boolean checkRoomTableExist() throws SQLException {

		final String DB_NAME = "CityLodgeDB";

		String TABLE_NAME = "ROOM";

		Alert alertPopup = new Alert(Alert.AlertType.ERROR);
		alertPopup.setTitle("Error Alert");

		try (Connection con = ConnectionTest.getConnection(DB_NAME)) {

			DatabaseMetaData dbm = con.getMetaData();
			ResultSet tables = dbm.getTables(null, null, TABLE_NAME.toUpperCase(), null);

			if (tables != null) {
				if (tables.next()) {
		
					return true;
				} else {
				
					return false;
				}
			}
		} catch (Exception e) {

			alertPopup.setContentText(e.getMessage());
			alertPopup.showAndWait();
		}

		return false;
	}

	public static boolean checkRentalRecordsTableExist() throws SQLException {

		final String DB_NAME = "CityLodgeDB";

		final String TABLE_NAME = "record";

		Alert alertPopup = new Alert(Alert.AlertType.ERROR);
		alertPopup.setTitle("Error Alert");

		try (Connection con = ConnectionTest.getConnection(DB_NAME)) {

			DatabaseMetaData dbm = con.getMetaData();
			ResultSet tables = dbm.getTables(null, null, TABLE_NAME.toUpperCase(), null);

			if (tables != null) {
				if (tables.next()) {

					return true;
				} else {
					return false;
				}
			} else {
		
				return false;
			}
		} catch (Exception e) {
			
			alertPopup.setContentText(e.getMessage());
			alertPopup.showAndWait();
		}

		return false;
	}
}
