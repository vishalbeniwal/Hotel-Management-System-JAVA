package Model.util;

import Model.CityLodge;
import Model.HiringRecord;
import Model.Standard;
import Model.Suite;
import Model.util.DateTime;

public class RandomDataGeneration {

	public static void generateRandomRoomData(CityLodge lodge) {

		Standard std = new Standard("R_001", 4, "Heater, Internet access, Television");
		std.setImageName("R_001.jpg");
		lodge.roomCollection().add(std);

		Standard std2 = new Standard("R_002", 2, "Heater, Television");
		std2.setImageName("R_002.jpg");
		lodge.roomCollection().add(std2);

		Standard std3 = new Standard("R_003", 1, "Television");
		std3.setImageName("R_003.jpg");
		lodge.roomCollection().add(std3);

		Suite suite = new Suite("S_001", "Balcony, Heater, Internet access, Television", "20/10/2019");
		suite.setImageName("S_001.jpg");
		lodge.roomCollection().add(suite);

		Suite suite2 = new Suite("S_002", "Balcony, Heater, Internet access, Television", "21/10/2019");
		suite2.setImageName("S_002.jpg");
		lodge.roomCollection().add(suite2);

		Suite suite3 = new Suite("S_003", "Balcony, Heater, Internet access, Television", "22/10/2019");
		suite3.setImageName("S_003.jpg");
		lodge.roomCollection().add(suite3);

	}

	public static void generateRandomRentalRecords(CityLodge lodge) {

		DateTime rentDate = new DateTime(18, 10, 2019);
		DateTime estimatedReturnDate = new DateTime(21, 10, 2019);
		DateTime actualReturnDate = new DateTime(21, 10, 2019);
		double rentalFee = 199 * 3;
		double lateFee = 0.0;
		HiringRecord record = new HiringRecord("R_001", "Vishal", rentDate, estimatedReturnDate, actualReturnDate,
				rentalFee, lateFee);
		int index = lodge.availRoomIndex("R_001");
		lodge.roomCollection().get(index).addRecord(record);

		rentDate = new DateTime(22, 10, 2019);
		estimatedReturnDate = new DateTime(26, 10, 2019);
		actualReturnDate = new DateTime(28, 10, 2019);
		rentalFee = 199 * 4;
		lateFee = (2 * (199 * 1.35));
		record = new HiringRecord("R_001", "Akshay", rentDate, estimatedReturnDate, actualReturnDate, rentalFee,
				lateFee);
		index = lodge.availRoomIndex("R_001");
		lodge.roomCollection().get(index).addRecord(record);

		rentDate = new DateTime(21, 10, 2019);
		estimatedReturnDate = new DateTime(25, 10, 2019);
		actualReturnDate = new DateTime(25, 10, 2019);
		rentalFee = 999 * 4;
		lateFee = 0.00;
		record = new HiringRecord("S_001", "Sam", rentDate, estimatedReturnDate, actualReturnDate, rentalFee, lateFee);
		index = lodge.availRoomIndex("S_001");
		lodge.roomCollection().get(index).addRecord(record);

		rentDate = new DateTime(26, 10, 2019);
		estimatedReturnDate = new DateTime(28, 10, 2019);
		actualReturnDate = new DateTime(29, 10, 2019);
		rentalFee = 999 * 2;
		lateFee = 1 * 1099;
		record = new HiringRecord("S_001", "Ben", rentDate, estimatedReturnDate, actualReturnDate, rentalFee, lateFee);
		index = lodge.availRoomIndex("S_001");
		lodge.roomCollection().get(index).addRecord(record);

	}

}
