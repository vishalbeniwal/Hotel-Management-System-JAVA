package Model.Database;

//Importing required packages
import java.sql.Connection;
import java.sql.Statement;
import javafx.scene.control.Alert;

//Defining class to insert row(s) with room details into the database
public class InsertRow {

	public static void insertRoomRow(String Room_Id, int Number_of_Beds, String Feature_Summary, String Room_Type,
			String Room_Status, String lastMaintenanceDate, String imageName) {

		final String DB_NAME = "CityLodgeDB";
		final String TABLE_NAME = "room";
		Alert alertPopup = new Alert(Alert.AlertType.ERROR);
		alertPopup.setTitle("Error Alert");

		try (Connection con = ConnectionTest.getConnection(DB_NAME); Statement stmt = con.createStatement();) {
			String query = "INSERT INTO " + TABLE_NAME + " VALUES (" + "'" + Room_Id + "'" + "," + "'" + Number_of_Beds
					+ "'" + "," + "'" + Feature_Summary + "'" + "," + "'" + Room_Type + "'" + "," + "'" + Room_Status
					+ "'" + "," + "'" + lastMaintenanceDate + "'" + "," + "'" + imageName + "'" + ")";

			int result = stmt.executeUpdate(query);

			con.commit();

		}

		catch (Exception e) {

			alertPopup.setContentText("ROOM ID is already exist");
			alertPopup.showAndWait();
		}

	}
	
	
	//Defining class to insert row(s) with rental record details into the database
	public static void insertRentalRecordRow(String recordID, String custId, String roomId, String rentDate,
			String estimatedReturnDate, String actualReturnDate, String rentalfee, String latefee) {

		final String DB_NAME = "CityLodgeDB";
		final String TABLE_NAME = "record";
		Alert alertPopup = new Alert(Alert.AlertType.ERROR);
		alertPopup.setTitle("Error Alert");

		try (Connection con = ConnectionTest.getConnection(DB_NAME); Statement stmt = con.createStatement();) {
			String query = "INSERT INTO " + TABLE_NAME + " VALUES (" + "'" + recordID + "'" + "," + "'" + custId + "'"
					+ "," + "'" + roomId + "'" + "," + "'" + rentDate + "'" + "," + "'" + estimatedReturnDate + "'"
					+ "," + "'" + actualReturnDate + "'" + "," + "'" + rentalfee + "'" + "," + "'" + latefee + "'"
					+ ")";

			int result = stmt.executeUpdate(query);

			con.commit();

		} catch (Exception e) {
	
			alertPopup.setContentText("record ID is already exist");
			alertPopup.showAndWait();
		}
	}
}
