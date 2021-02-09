package Model.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

import Model.AddRoomException;
import Model.CityLodge;
import Model.HiringRecord;
import Model.HotelRoom;
import Model.Standard;
import Model.Suite;
import Model.Database.DbException;
import Model.Database.InsertRow;
import Model.util.DateTime;
import javafx.scene.control.Alert;

public class FileAccess {

	private static Scanner input;

	public static void exportToFile(CityLodge lodge, String path) throws Exception {

		PrintWriter writer = null;

		writer = new PrintWriter(path);

		for (int i = 0; i < lodge.roomCollectionSize(); i++) {

			HotelRoom room = lodge.roomCollection().get(i);

			writer.println(room.toString());

			for (int j = (room.recentRecordsSize() - 1); j >= 0; j--) {

				HiringRecord record = room.recordCollection().get(j);

				writer.println(record.toString());
			}

		}
		writer.close();
	}

	public static void importFromFile(CityLodge lodge, String path)
			throws FileNotFoundException, AddRoomException, DbException {

		Alert infoPopup = new Alert(Alert.AlertType.INFORMATION);
		String line = null;
		String beds[] = { "1", "2", "4", "6" };
		input = new Scanner(new FileInputStream(path));
		lodge.roomCollection().clear();

		while (input.hasNextLine()) {
			line = input.nextLine();
			String[] items = line.split(":");

			if ((items[1].equals(beds[0])) || (items[1].equals(beds[1])) || (items[1].equals(beds[2]))
					|| (items[1].equals(beds[3]))) {

				insertRoomDetailsFromFile(lodge, items);

			} else {

				insertRecordsDetailsFromFile(lodge, items);
			}
		}
		infoPopup.setTitle("Message");
		infoPopup.setContentText("Room(s) has been added");
		infoPopup.showAndWait();

		input.close();

	}

	public static void insertRoomDetailsFromFile(CityLodge lodge, String[] item) throws AddRoomException, DbException {

		if (item[0].startsWith("R_")) {

			Standard room = new Standard(item[0], Integer.parseInt(item[1]), item[4]);
			lodge.roomCollection().add(room);
			room.setStatus(item[3]);
			room.setImageName(item[5]);
			InsertRow.insertRoomRow(item[0], Integer.parseInt(item[1]), item[4], item[2], item[3], "", item[5]);
		} else if (item[0].startsWith("S_")) {

			Suite suite = new Suite(item[0], item[5], item[4]);
			suite.setStatus(item[3]);
			suite.setImageName(item[6]);
			lodge.roomCollection().add(suite);
			InsertRow.insertRoomRow(item[0], Integer.parseInt(item[1]), item[5], item[2], item[3], item[4], item[6]);
		} else {

			throw new AddRoomException("Invalid format for Room details.");
		}

	}

	public static void insertRecordsDetailsFromFile(CityLodge lodge, String[] items) throws DbException {

		String ID = items[0];
		String parsedRentDate = items[1];
		String parsedEstimatedReturnDate = items[2];
		String parsedActualReturnDate = items[3];
		String rental_Fee = items[4];
		String late_Fee = items[5];
		double rentalFee = Double.parseDouble(items[4]);
		double lateFee = Double.parseDouble(items[5]);
		String[] recordIDElements = ID.split("_");
		String roomID = recordIDElements[0] + "_" + recordIDElements[1];
		String custID = recordIDElements[2];

		String[] dateformatsplit = parsedRentDate.toString().split("/");
		DateTime rentDate = new DateTime(Integer.parseInt(dateformatsplit[0]), Integer.parseInt(dateformatsplit[1]),
				Integer.parseInt(dateformatsplit[2]));

		dateformatsplit = parsedEstimatedReturnDate.toString().split("/");
		DateTime estimatedReturnDate = new DateTime(Integer.parseInt(dateformatsplit[0]),
				Integer.parseInt(dateformatsplit[1]), Integer.parseInt(dateformatsplit[2]));

		dateformatsplit = parsedActualReturnDate.toString().split("/");
		DateTime actualReturnDate = new DateTime(Integer.parseInt(dateformatsplit[0]),
				Integer.parseInt(dateformatsplit[1]), Integer.parseInt(dateformatsplit[2]));

		HiringRecord record = new HiringRecord(roomID, custID, rentDate, estimatedReturnDate, actualReturnDate,
				rentalFee, lateFee);
		int index = lodge.availRoomIndex(roomID);
		lodge.roomCollection().get(index).addRecord(record);
		record.setRecordId(ID);
		InsertRow.insertRentalRecordRow(ID, custID, roomID, parsedRentDate, parsedEstimatedReturnDate,
				parsedActualReturnDate, rental_Fee, late_Fee);

	}
}
