package Model;


import Model.util.DateTime ;

//Creating class for each room to keep their own collection of hiring records
public class HiringRecord implements Comparable<HiringRecord> {
	
	
	private String recordId;
	private DateTime rentDate;
	private DateTime estimatedReturnDate;
	private DateTime actualReturnDate;
	private double rentalFee;
	private double lateFee;
	private String custId;
	private String roomId;
	
	//constructor to store data before returning the room
	public HiringRecord (String roomId, String custId, DateTime rentDate, DateTime estimatedRentDate) {
		
		this.setRoomId(roomId);
		this.rentDate = rentDate;
		this.estimatedReturnDate = estimatedRentDate;
		this.setCustId(custId);
		//concatenating attributes to create record id
		this.recordId = roomId + "_" + custId + "_" + rentDate.getEightDigitDate(); 
		this.setActualReturnDate(null);
		this.setRentalfee(0);
		this.setLatefee(0);
	}
	
	//constructor to store data after the returning the room
	public HiringRecord (String roomId, String custId, DateTime rentDate, DateTime estimatedReturnDate, 
			DateTime actualReturnDate, double rentalfee, double latefee) {
		
		this.setRoomId(roomId);
		this.rentDate = rentDate;
		this.setCustId(custId);
		this.estimatedReturnDate = estimatedReturnDate;
		this.recordId = roomId + "_" + custId + "_" + rentDate.getEightDigitDate();	
		this.actualReturnDate = actualReturnDate;
		this.rentalFee = rentalfee;
		this.lateFee = latefee;	
	}
	
	
	// Getters & Mutators Methods
	public String getRecordId() {
		
		return recordId;
	}
	
	
	public void setRecordId(String recordId) {
		
		this.recordId = recordId;
	}

	
	public DateTime getRentDate() {
		
		return rentDate;
	}
	
	
	public void setRentDate(DateTime rentDate) {
		
		this.rentDate = rentDate;
	}
	
	
	public DateTime getEstimatedReturnDate() {
		
		return estimatedReturnDate;
	}
	
	
	public void setEstimatedReturnDate(DateTime estimatedRentDate) {
		
		this.estimatedReturnDate = estimatedRentDate;
	}
	
	
	public DateTime getActualReturnDate() {
	
		return actualReturnDate;
	}
	
	
	public void setActualReturnDate(DateTime actualReturnDate) {
	
		this.actualReturnDate = actualReturnDate;
	}
	
	
	public double getRentalfee() {
	
		return rentalFee;
	}
	
	
	public void setRentalfee(double rentalfee) {
	
		this.rentalFee = rentalfee;
	}
	
	
	public double getLatefee() {
	
		return lateFee;
	}
	
	
	public void setLatefee(double latefee) {
	
		this.lateFee = latefee;
	}
	
    
	
	//Method to return a single string which contains record ID, rentDate, estimated Return Date, actual rental date, rental fee
	//and Late fee
	public String toString() {
		
		String recordsToString = this.recordId + ":" + this.rentDate.getFormattedDate() + ":" + this.estimatedReturnDate.getFormattedDate() +
				":" ;
		
		if(this.actualReturnDate != null) {
			
			recordsToString += this.actualReturnDate.getFormattedDate()+ ":" + this.rentalFee + ":" + this.lateFee;
		}
		else {
			recordsToString += "none:none:none";
		}
		return recordsToString;
	}
	
	
	//Method to return a String which contains all the room and rental details
	public String getDetails() {
		
		String detailsToString = "Record ID:\t\t\t" + this.recordId + "\nCust ID:\t\t\t\t" + this.custId+ "\nRent Date:\t\t\t" + this.rentDate.getFormattedDate() + 
				"\nEstimated Return Date:\t" + this.estimatedReturnDate.getFormattedDate();
		
		if(this.actualReturnDate != null) {
			
			detailsToString += "\nActual Return Date:\t" + this.actualReturnDate.getFormattedDate() + 
					"\nRental Fee:\t\t\t" + this.rentalFee + "\nLate Fee:\t\t\t\t" + this.lateFee;
		}
		return detailsToString ;
	}

	
	//Method to sort the hiring records using the rent date of the records.
	@Override
	public int compareTo(HiringRecord sort) {
		
		return DateTime.diffDays(this.rentDate, sort.rentDate);
	}

	public String getCustId() {
		return custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public String getRoomId() {
		return roomId;
	}

	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}
	
}
