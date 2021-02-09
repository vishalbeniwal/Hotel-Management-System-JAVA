package Model;

import java.util.ArrayList;
import java.util.Collections;

import Model.util.DateTime;

/*
The Super class HotelRoom is an abstract class with few abstract methods. These methods are implemented in the subclasses using
the concepts of inheritance and polymorphism.
*/
public abstract class HotelRoom {
	
	private String roomId;
	private int noOfBed;
	private String feature;
	private String roomType;
	private String status;
	private String imageName;
	private String lastmaintenanceDate;
	//Creating a object reference of HiringRecords to store 10 most recent records of a room
	protected ArrayList<HiringRecord> recentRecords = new ArrayList<HiringRecord>();
	
	
	//constructor of HotelRoom
	public HotelRoom (String roomId, int noOfBed, String feature, String roomType, String status) {
		
		this.roomId = roomId;
		this.noOfBed = noOfBed;
		this.feature = feature;
		this.roomType = roomType;
		this.status = status;
		this.imageName = "default.jpg";
	}

	
	//Getter and mutator methods
	public int getNoOfBed() {
		
		return noOfBed;
	}

	
	public void setNoOfBed(int noOfBed) {
		
		this.noOfBed = noOfBed;
	}

	
	public String getFeature() {
		
		return feature;
	}

	
	public void setFeature(String feature) {
	
		this.feature = feature;
	}

	
	public String getRoomType() {
	
		return roomType;
	}

	
	public void setRoomType(String roomType) {
		
		this.roomType = roomType;
	}

	
	public String getStatus() {
		
		return status;
	}

	
	public void setStatus(String status) {
	
		this.status = status;
	}


	public String getRoomId() {
	
		return roomId;
	}


	public void setRoomId(String roomId) {
	
		this.roomId = roomId;
	}
	
	public String getImageName() {
		return imageName;
	}


	public void setImageName(String imageName) {
		this.imageName = imageName;
	}
	
	public int recentRecordsSize() {
		
		return recentRecords.size();
	}
	
	public ArrayList<HiringRecord> recordCollection() {
		
		return recentRecords;
	}
	

	/*
	Method to add room records in the ArrayList of size 10 which starts to delete records once it's capacity
	becomes full. This method will be used later in the method of renting the room.
	*/
	public void addRecord(HiringRecord record) {
		
		if(recentRecords.size() < 10) {
			
			recentRecords.add(record);
			Collections.sort(recentRecords);
		}
		else {
			
			recentRecords.remove(0);
			recentRecords.add(record);
			Collections.sort(recentRecords);	
		}	
	}
	

	//Method to retrieve the records of a specific index
	public HiringRecord retrieveRecordIndex(int index){
	        
		   if(index >= 0 && index < 10) {
	            
			   return recentRecords.get(index);
	        }
		   else { 
			   
			   return null;
	        }
	    }
	
	
	  //Method to retrieve the latest record of the list associated with a particular room
	  public HiringRecord retrieveRecord() {
	        
		   if(recentRecords.size() < 1) {
	            
			   return null;
		   }
	       else {
	    	   
	            return recentRecords.get(recentRecords.size()-1);
	        }
	   }
	  
	   
	  //Method to update the rental records of the latest record from a list of a particular room
	   public void updateRecord(DateTime actualReturnDate,double rentalFee,double lateFee) {
		   
		   if(recentRecords.size() > 0) {	   
			   
			   int size = recentRecords.size();
			   recentRecords.get(size-1).setActualReturnDate(actualReturnDate);
			   recentRecords.get(size-1).setRentalfee(rentalFee);
			   recentRecords.get(size-1).setLatefee(lateFee);

		   }
		   else {
			   
			   return;
		   }  
	   }
	   
	   //Abstract methods are created. Their implementation will be done in the sub classes.
	   public abstract void rent(String customerId, DateTime rentDate, int numOfRentDay) throws RentException;
	   public abstract void returnRoom(DateTime returnDate) throws ReturnException;
	   public abstract void performMaintenance() throws MaintenanceException;
	   public abstract void completeMaintenance(DateTime completionDate) throws MaintenanceException;
	   
	   public String toString() {
		   
		   String str = this.getRoomId() + ":" + this.getNoOfBed() + ":" + this.getRoomType() 
		   				+ ":"+this.getStatus();
	    	    	
	    	    return str;   
	   }
	   
	   public abstract String getDetails();


	public String getLastmaintenanceDate() {
		return lastmaintenanceDate;
	}


	public void setLastmaintenanceDate(String lastmaintenanceDate) {
		this.lastmaintenanceDate = lastmaintenanceDate;
	}



}
