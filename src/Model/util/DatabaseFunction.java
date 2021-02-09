package Model.util;

//Importing required packages
import Model.CityLodge;
import Model.HiringRecord;
import Model.HotelRoom;
import Model.Database.CheckTableExist;
import Model.Database.CreateTable;
import Model.Database.DeleteTable;
import Model.Database.InsertRow;
import Model.Database.SelectQuery;
import javafx.scene.control.Alert;

//Defining a utility to read and write into the created database using citylodge arraylist
public class DatabaseFunction {

	//Method to write into the database
	public static void writefunction(CityLodge lodge) throws Exception {
		
		if (CheckTableExist.checkRoomTableExist()) {
			DeleteTable.deleteRoomTable();
		}
		if (CheckTableExist.checkRentalRecordsTableExist()) {
			DeleteTable.deleteRecordTable();
		}
		CreateTable.createRoomTable();
		CreateTable.createRentalRecordTable();
		
		//Searching the list of room in CityLodge
		for (int i = 0; i < lodge.roomCollectionSize(); i++) {

			HotelRoom room = lodge.roomCollection().get(i);

			if (room.getRoomType().equalsIgnoreCase("Standard")) {

				InsertRow.insertRoomRow(room.getRoomId(), room.getNoOfBed(), room.getFeature(), room.getRoomType(),
						room.getStatus(), "", room.getImageName());
		
			} else if (room.getRoomType().equalsIgnoreCase("Suite")) {

				InsertRow.insertRoomRow(room.getRoomId(), room.getNoOfBed(), room.getFeature(), room.getRoomType(),
						room.getStatus(), room.getLastmaintenanceDate(), room.getImageName());

			}
			//Searching the list of records in Hiring records
			for (int j = 0; j < room.recentRecordsSize(); j++) {

				HiringRecord record = room.recordCollection().get(j);

				if (record.getActualReturnDate() == null) {

					InsertRow.insertRentalRecordRow(record.getRecordId(), record.getCustId(), record.getRoomId(),
							record.getRentDate().toString(), record.getEstimatedReturnDate().toString(), "none", "none",
							"none");
				} else {

					String rentDate = record.getRentDate().toString();
					String actualRentDate = record.getActualReturnDate().toString();
					String estimatedReturnDate = record.getEstimatedReturnDate().toString();

					InsertRow.insertRentalRecordRow(record.getRecordId(), record.getCustId(), record.getRoomId(),
							rentDate.toString(), estimatedReturnDate.toString(), actualRentDate.toString(),
							Double.toString(record.getRentalfee()), Double.toString(record.getLatefee()));
					
				}
			}
		}
		
		Alert infoPopup = new Alert(Alert.AlertType.INFORMATION);
		infoPopup.setTitle("Message");
		infoPopup.setContentText("Action has been successfully Done");
		infoPopup.showAndWait();
	}
	
	//Method to read from the stored data in the Database
	public static void readFunction(CityLodge lodge) throws Exception {

		if (CheckTableExist.checkRoomTableExist()) {
			
			SelectQuery.selectRoomData(lodge);
		}

		if (CheckTableExist.checkRentalRecordsTableExist()) {
			
			SelectQuery.selectRecordsData(lodge);
		}
		
		Alert infoPopup = new Alert(Alert.AlertType.INFORMATION);
		infoPopup.setTitle("Message");
		infoPopup.setContentText("Action has been successfully Done");
		infoPopup.showAndWait();
	}
	

}
