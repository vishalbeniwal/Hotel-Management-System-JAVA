package Model;


import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import Model.Database.DbException;
import Model.Database.InsertRow;
import Model.Database.UpdateQuery;
import Model.util.DateTime;
import javafx.scene.control.Alert;

/*
CityLodge class is implemented to store 50 objects of both types of rooms. 
It was also has a menu by which employees of city lodge can manage the application and take certain actions on room objects
*/
public class CityLodge {
	
	private ArrayList<HotelRoom> hotel;
	
	private final int MAXROOMS = 50;
	private final int[] BEDS = {1,2,4,6};
    final static String DATE_FORMAT = "dd/MM/yyyy";
    
    private static CityLodge lodge;
	
	
	public CityLodge() {
		
		hotel = new ArrayList<HotelRoom>();	
	}
	
	public static CityLodge getRooms() {
		
		if(lodge == null) {
			
			lodge = new CityLodge();
		}
		return lodge;
	}
	
	public ArrayList<HotelRoom> roomCollection() {
		
		return hotel;
	}
	
	public int roomCollectionSize() {
		
		return hotel.size();
	}
    
	//This method is implemented to allow the employees of CityLodge to add a new room in the collection
	public void addRoom(String id,String noOfBeds,String roomType, String features,String status,String inputDate) throws 
	AddRoomException, InvalidIdException, InvalidInputException {
		
		Alert infoPopup = new Alert(Alert.AlertType.INFORMATION);
		
		if(hotel.size() > MAXROOMS) {
			
			throw new AddRoomException("Error : Capacity Exhausted. Hotel can't have more than 50 rooms");	
		}
		else if(id.length() < 3 ) {
			
			throw new InvalidIdException("Error : Length of Room ID can't be less than to 3");
		}

		else if(!(availRoomIndex(id) == -1)) {
			
			throw new InvalidIdException("The room ID is already exist in the system");
		}
		
		int numberOfBeds = Integer.parseInt(noOfBeds);
		
		if(roomType.equalsIgnoreCase("Standard") && numberOfBeds != BEDS[0] && numberOfBeds != BEDS[1] && numberOfBeds != BEDS[2]) {
				
			throw new AddRoomException("Invalid Input for beds. Number of beds can be 1,2 or 4 only for standard rooms");
		}
		else if(roomType.equalsIgnoreCase("Suite") && numberOfBeds != BEDS[3]) {
			
			throw new AddRoomException("Invalid Input for beds. Number of beds can be 6 only for suites");
		}		
		
		if(roomType.equalsIgnoreCase("Standard")) {
			
			if(!id.startsWith("R_")) {
				
				throw new InvalidIdException("Invalid Input for Id. Id should start with R_ as prefix" );
			}
			else {
				
				Standard std = new Standard(id,numberOfBeds,features);
				std.setImageName("default.jpg");
	            hotel.add(std);
	            InsertRow.insertRoomRow(id, numberOfBeds,features, "Standard", status,"","default.jpg");
				
			}
		}
		else if(roomType.equalsIgnoreCase("Suite")) {
					
			if(!id.startsWith("S_")) {
				
				throw new InvalidIdException("Invalid Input for Id. Id should start with S_ as prefix" );
			}			
			if(!inputDateValid(inputDate)) {
			
				throw new InvalidInputException("Invalid input for date. Please input a valid date ");
			}
		
	        	
	        Suite suite = new Suite(id,features,inputDate);
	        suite.setImageName("default.jpg");
            hotel.add(suite);
            InsertRow.insertRoomRow(id, numberOfBeds,features, "Suite", status,inputDate,"default.jpg");
  
		}
	
	}

	
	//This method is implemented to allow the employees of CityLodge to rent an existing room
	public void rentRoom(String id,String custID,int noOfRentDays,String inputRentDate) throws InvalidIdException, 
	InvalidInputException, RentException, DbException, SQLException {
		
		if(availRoomIndex(id) == -1) {
			
			throw new InvalidIdException("The room ID is not present in the hotel database");
		}
		else if((noOfRentDays < 1)) {
			
			throw new InvalidInputException("The number of days to rent the room can't be less than 1");	
		}
		else if(!inputDateValid(inputRentDate)) {
			
			throw new InvalidInputException("Invalid input for date. Please input a valid date ");
		}
		
		String[] dateformatsplit = inputRentDate.toString().split("/");
        DateTime rentDate = new DateTime(Integer.parseInt(dateformatsplit[0]),
        		Integer.parseInt(dateformatsplit[1]),Integer.parseInt(dateformatsplit[2]));
        
		if(!checksForRent(rentDate,id)) {
			
			throw new RentException("Error : The rent date can't overlap with past rental bookings");
		}
		
		hotel.get(availRoomIndex(id)).rent(custID, rentDate, noOfRentDays);
		String updatedStatus = hotel.get(availRoomIndex(id)).getStatus();
		UpdateQuery.updateRoomDetails(updatedStatus, id);
	}
	
	//This method is implemented to allow the employees of CityLodge to return a rented room
	public void returnRoom(String id,String inputReturnDate) throws InvalidIdException, 
	InvalidInputException, ReturnException, DbException, SQLException{
		
		if(availRoomIndex(id) == -1) {
			
			throw new InvalidIdException("The room ID is not present in the hotel database");
		}
		else if(!inputDateValid(inputReturnDate)) {
			
			throw new InvalidInputException("Invalid input for date. Please input a valid date ");
		}
		
		String[] dateformatsplit = inputReturnDate.toString().split("/");
        DateTime returnDate = new DateTime(Integer.parseInt(dateformatsplit[0]),
        		Integer.parseInt(dateformatsplit[1]),Integer.parseInt(dateformatsplit[2]));
		
        hotel.get(availRoomIndex(id)).returnRoom(returnDate);

        String updatedStatus = hotel.get(availRoomIndex(id)).getStatus();
		UpdateQuery.updateRoomDetails(updatedStatus, id);
	}
			
	
	
	//This method is implemented to allow the employees of CityLodge to perform the maintenance
	public void roomMaintenance(String id) throws InvalidIdException,MaintenanceException, DbException, SQLException {
	
		
		if(availRoomIndex(id) == -1) {
			
			throw new InvalidIdException("The room ID is not present in the hotel database");
		}
		else {
			
			hotel.get(availRoomIndex(id)).performMaintenance();
			String updatedStatus = hotel.get(availRoomIndex(id)).getStatus();
			UpdateQuery.updateRoomDetails(updatedStatus, id);
		}

	}
	
	
	//This method is implemented to allow the employees of CityLodge to complete an ongoing maintenance
	public void completeMaintenance(String id,String inputCompleteDate) throws InvalidIdException , InvalidInputException,
	MaintenanceException, DbException, SQLException {
		
		DateTime completeMaintDate = null;	
		
		if(availRoomIndex(id) == -1) {
			
			throw new InvalidIdException("The room ID is not present in the hotel database");
		}
		
		if(hotel.get(availRoomIndex(id)).getRoomType().equalsIgnoreCase("suite")) {
			
			String[] dateformatsplit = inputCompleteDate.toString().split("/");
	        completeMaintDate = new DateTime(Integer.parseInt(dateformatsplit[0]),
	        		Integer.parseInt(dateformatsplit[1]),Integer.parseInt(dateformatsplit[2]));
	        
	        if(!checkForMaintenance(completeMaintDate,id)) {
	        	
	        	throw new InvalidInputException("Error : The Maintenance date can't prior to past rental bookings. "
	        			+ "Please re-check your details");
	        }	        
		}
		
		hotel.get(availRoomIndex(id)).completeMaintenance(completeMaintDate);
		String updatedStatus = hotel.get(availRoomIndex(id)).getStatus();
		UpdateQuery.updateRoomDetails(updatedStatus, id);
	}
	        	
	
	
	
	//This method is implemented to get the index of a specific room in the array using the ID of the room which is unique.
	public int availRoomIndex(String id) {
		
		int index = -1;
		
		for (int i = 0 ; i < hotel.size() ; i++) {
			
			if(hotel.get(i).getRoomId().equals(id)) {
				
				index = i;
				break;
			}
		}
		return index;
	}
    
    
    //This method is implemented to check conditions of overlapping while renting a room
    public boolean checksForRent(DateTime rentDate, String id) {
    	
    	if(hotel.get(availRoomIndex(id)).retrieveRecord() != null) {
    		
    		DateTime latestReturnDate = hotel.get(availRoomIndex(id)).retrieveRecord().getActualReturnDate();
			int checkForOverlap = DateTime.diffDays(rentDate, latestReturnDate);
			
			if(checkForOverlap > 0) {
				
				return true;
			}
			else {
				
				return false;
			}
    	}
    	return true;
    }
    
 
    //This method is implemented to check conditions of overlapping during the completing of maintenance
    public boolean checkForMaintenance(DateTime completeMaintDate, String id) {
    	
    	if(hotel.get(availRoomIndex(id)).retrieveRecord() != null) {
    		
    		DateTime latestReturnDate = hotel.get(availRoomIndex(id)).retrieveRecord().getActualReturnDate();
    		int checkForOverlap = DateTime.diffDays(completeMaintDate, latestReturnDate);
    		
			if(checkForOverlap > 0) {
				
				return true;
			}
			else {
				
				return false;
			}	
    	}
    	return true;
    }

    
    public static boolean inputDateValid(String date) {
    	
        try {
            DateFormat df = new SimpleDateFormat(DATE_FORMAT);
            df.setLenient(false);
            df.parse(date);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }
    
    public static void printRooms(CityLodge lodge) {
    	
    	for(int i = 0 ; i < lodge.roomCollectionSize() ; i++ ) {
    		

    		
    	}
    	
    }
    
    public static void printRecords(CityLodge lodge) {
    	
    	for(int i = 0 ; i < lodge.roomCollectionSize() ; i++ ) {
    		
    		if(lodge.roomCollection().get(i).recordCollection().size() == 0) {
    			
    			continue;
    		}
    	
    }
    }

        	
    
}
