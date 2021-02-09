package Model;

import Model.util.DateTime;

//Class created to implement standard type rooms
public class Standard extends HotelRoom {

	
	//Constructor created for standard rooms
	public Standard (String roomId, int noOfBed, String feature) { 
		 
		super( roomId, noOfBed, feature, "Standard", "Available");	
	}
	
	
	//Method implemented to rent an available room. 
	//This method will check all the required condition before renting a room.
	public void rent(String customerId, DateTime rentDate, int numOfRentDay) throws RentException {
		    
	      
			//Checking required conditions for standard type of rooms
	        if ((rentDate.getNameOfDay().equals("Saturday") || rentDate.getNameOfDay().equals("Sunday") && numOfRentDay < 3)) {
	            
	        	throw new RentException("Error : A standard room should be booked for minimum 3 days if booked on Saturday or Sunday");  
	        } 
	        else if (!rentDate.getNameOfDay().equals("Saturday") && !rentDate.getNameOfDay().equals("Sunday") && numOfRentDay < 1) {
	            
	        	throw new RentException("Error: A standard room should be booked for minimum 2 days if booked between Monday to Friday");
	        }

	        if (this.getStatus().equalsIgnoreCase("Rented")) {
	        	
	        	throw new RentException("Error: This Standard room is already being rented by someone");     
	        }
	        
	        if (this.getStatus().equalsIgnoreCase("Maintenance")) {
	            
	        	throw new RentException("Error: This room is already under maintenance");
	        }

	        if (numOfRentDay > 10) {
	        	
	        	throw new RentException("Oops ! You can't rent a room for more than 10 days");
	        }  
	        
	        DateTime estimatedReturnDate = new DateTime(rentDate, numOfRentDay);  
	        
	        //Updating the status to rented
	        this.setStatus("Rented");
	        HiringRecord record1 = new HiringRecord(this.getRoomId(), customerId, rentDate, estimatedReturnDate,null,0,0);
	        
	        //Adding the record to a particular room's record history
	        this.addRecord(record1);    	
		}
	
	
	//Method implemented to check whether a room can be returned or not
	//This method will return boolean value accordingly.
	public void returnRoom(DateTime returnDate) throws ReturnException {
		
		double rentalFee = 0,lateFee = 0;
		
    	if (this.getStatus().equals("Available")) {
    		
    		throw new ReturnException("Error : This room is not rented");
        }
    	
        if (this.getStatus().equals("Maintenance")) {
        	
        	throw new ReturnException("Error: This room is already under maintenance");
        }
        
        HiringRecord recentRecord = retrieveRecord();
        
        if(recentRecord == null) {
        	
        	throw new ReturnException("No record Found");
        }
        
		DateTime rentDate = recentRecord.getRentDate();

		if (DateTime.diffDays(returnDate, rentDate) < 0) {
			
			throw new ReturnException("Error!! Return date is prior to the rent date. Please check again");
		}
        
		//Getting the number of days between rent and estimated Return date given
        int numOfDays = DateTime.diffDays(recentRecord.getEstimatedReturnDate(), recentRecord.getRentDate());
        //Getting the number of days exceeded estimated return date
        int numOfDaysDelayed = DateTime.diffDays(returnDate, recentRecord.getEstimatedReturnDate());
        
        if(this.getNoOfBed() == 1) {
        	
        	int perDayCharges = 59;
 
        	rentalFee = numOfDays * perDayCharges;
        	
        	if(numOfDaysDelayed <=0) {
        		
        		lateFee = 0;
        	}
        	else {
        		
        		lateFee = (numOfDaysDelayed * perDayCharges * 1.35); 
        	}
        } else if (this.getNoOfBed() == 2) {
        	
        	int perDayCharges = 99;
        	 
        	rentalFee = numOfDays * perDayCharges;
        	
        	if(numOfDaysDelayed <=0) {
        		
        		lateFee = 0;
        	}
        	else {
        		
        		lateFee = (numOfDaysDelayed * perDayCharges * 1.35); 
        	}
	
        } else if (this.getNoOfBed() == 4) {
        	
        	int perDayCharges = 199;
        	 
        	rentalFee = numOfDays * perDayCharges;
        	
        	if(numOfDaysDelayed <=0) {
        		
        		lateFee = 0;
        	}
        	else {
        		
        		lateFee = (numOfDaysDelayed * perDayCharges * 1.35); 
        	}
        }
        
        //Setting the status back to available once it is returned
        this.setStatus("Available");
        this.updateRecord(returnDate, rentalFee, lateFee);
        System.out.println("\n****The Bill for your stay*****\n");
        System.out.println("Rental Fee = " + rentalFee + " for " + numOfDays + " Days" );
        System.out.println("Late Fee = " + lateFee + " for extra Days" );
        System.out.println("Your Total bill = " + (rentalFee + lateFee) + "\n");
	}
	
	
	//Method to perform the maintenance after checking the required conditions
    public void performMaintenance() throws MaintenanceException {
       
    	if (this.getStatus().equals("Rented")) {
            
    		throw new MaintenanceException("The room is not available for maintenance right now. It is rented");
           
        } else if (this.getStatus().equals("Maintenance")) {
            
        	throw new MaintenanceException("The room is already under maintenance");
        }

        this.setStatus("Maintenance");
        System.out.println("The room is under Maintenance now");

    }

   
    //Method to set the ongoing maintenance as complete and making the room available again for renting
    public void completeMaintenance(DateTime completionDate) throws MaintenanceException{

        if (this.getStatus().equals("Rented")) {
           
        	throw new MaintenanceException("The Room is not available for maintenance right now. It is rented");
          
        } else if (this.getStatus().equals("Available")) {
        	
        	throw new MaintenanceException("The room was not under maintenance");
        }
  
        this.setStatus("Available");
        System.out.println("The maintenance has been completed. Now the room is available for renting");

    }
    
    
    //Overridden Method to return a string with the room details combined keeping ":" as separator 
    public String toString() {
    	
    	String recordsToString = super.toString() + ":" + this.getFeature() + ":" + this.getImageName() ;
    	
    	return recordsToString;
		
    }
    
    
    //Overridden Method to a return a string with the room details along with rent history.
    public String getDetails() {
    	
    	String details = "Room ID:\t\t" + this.getRoomId() + "\nNumber of Beds:\t\t" + this.getNoOfBed() + "\nType:\t\t\t" + 
    	this.getRoomType() + "\nstatus\t\t\t" + this.getStatus() + "\nFeature Summary\t\t" + this.getFeature() + "\nRENTAL RECORD\t\t";
    	
    	HiringRecord recentRecord = retrieveRecord();
    	
    	if (recentRecord == null) {
    		
    		return details += "empty";
    	}
    	else {
    		
    		//Print the latest record first
    		details+= "\n" + this.retrieveRecord().getDetails();
    		
    		//Created a loop to print rest of the record, if any
            for (int i = 0; i < this.recentRecords.size() - 1; i++) {
              
            	details += "-----------------------------------------------\n";
                details += this.retrieveRecordIndex(i).getDetails();
            }
    	
    	}
    	return details;
 	
    }
}
