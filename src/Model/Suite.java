package Model;

import Model.util.DateTime;

//Class created to implement Suite type rooms
public class Suite extends HotelRoom {
	
	private String lastMaintenanceDate;
	
	//Constructor created for Suites
	public Suite (String roomId, String feature,String lastMaintDate){
		
		super( roomId, 6, feature, "Suite", "Available");
		this.lastMaintenanceDate = lastMaintDate;

	}

	
	//Accessor method for last maintenance Date
	public String getLastMaintenanceDate() {
		return lastMaintenanceDate;
	}

	
	//Mutator method for last maintenance Date
	public void setLastMaintenanceDate(String lastMaintnceDate) {
		this.lastMaintenanceDate = lastMaintnceDate;
	}
	
	
	//Method implemented to rent an available room. 
	//This method will check all the required condition before renting a room.
	public void rent(String customerId, DateTime rentDate, int numOfRentDay) throws RentException {
	      

	        if (this.getStatus().equalsIgnoreCase("Rented")) {
	        	
	            throw new RentException("This Standard room is already being rented by someone");
	        }
	        
	        if (this.getStatus().equalsIgnoreCase("Maintenance")) {
	            
	        	throw new RentException("This room is already under maintenance");
	        }

	        if (numOfRentDay < 1) {
	        	
	        	throw new RentException("Invalid Input. Please provide a valid rental period. ");
	        }
	        
	        DateTime estReturnDate = new DateTime (rentDate,numOfRentDay);
	        //Calculating the next estimated maintenance date
			String[] dateformatsplit = this.lastMaintenanceDate.toString().split("/");
	        DateTime lastMaintenanceDateFormat = new DateTime(Integer.parseInt(dateformatsplit[0]),
	        		Integer.parseInt(dateformatsplit[1]),Integer.parseInt(dateformatsplit[2]));
	        
	        DateTime estNextMaintenanceDate = new DateTime(lastMaintenanceDateFormat,10);

	        int checkForRentDate = DateTime.diffDays(rentDate, lastMaintenanceDateFormat);
	        
	        int checkForOverlap = DateTime.diffDays(estReturnDate, estNextMaintenanceDate);
	        //Assumption : Rent date can't be on the same day as last maintenance date
	        if(checkForRentDate < 1) {
	        	
	        	throw new RentException("Rent Date should be after last Maintenance Date");
	        }
	        
	        if(checkForOverlap > 0) {
	        	
	        	throw new RentException("Estimated Next Maintenance Date is overlapping with rental period. Please re-check the details ");

	        }
	        //Setting the status to rented
	        this.setStatus("Rented");      
	        HiringRecord record1 = new HiringRecord(this.getRoomId(), customerId, rentDate, estReturnDate,null,0,0);
	        //Adding the record to hiring records of a particular room
	        this.addRecord(record1);
		
		}
	
    
	//Method implemented to check whether a room can be returned or not
	//This method will return boolean value accordingly.
	public void returnRoom(DateTime returnDate) throws ReturnException {
		
		float rentalFee = 0,lateFee = 0;
		
    	if (this.getStatus().equals("Available")) {
    		
    		throw new ReturnException("This room is not rented");
        }
    	
        if (this.getStatus().equals("Maintenance")) {
        	
        	throw new ReturnException("This room is already under maintenance");
        }
        
        HiringRecord recentRecord = retrieveRecord();
        
        if(recentRecord == null) {
        	
        	throw new ReturnException("No record Found");

        }
		DateTime rentDate = recentRecord.getRentDate();

		if (DateTime.diffDays(returnDate, rentDate) < 0) {
			
			throw new ReturnException("Error!! Return date is prior to the rent date. Please check again");

		}
		
        //Calculating the number of days between rent day and estimated return day
        int numOfDays = DateTime.diffDays(recentRecord.getEstimatedReturnDate(), recentRecord.getRentDate());
        //Calculating the number of days between estimated return day and actual return day
        int numOfDaysDelayed = DateTime.diffDays(returnDate, recentRecord.getEstimatedReturnDate());

        	int perDayCharges = 999;
 
        	rentalFee = numOfDays * perDayCharges;
        	
        	if(numOfDaysDelayed <= 0) {
        		
        		lateFee = 0;
        	}
        	else {
        		
        		lateFee = numOfDaysDelayed * 1099; 
        	} 
        
        this.setStatus("Available");
        this.updateRecord(returnDate, rentalFee, lateFee);
        System.out.println("\n****The Bill for your stay*****\n");
        System.out.println("Rental Fee = " + rentalFee + " for " + numOfDays + " Days" );
        System.out.println("Late Fee = " + lateFee + " for extra Days" );
        System.out.println("Your Total bill = " + (rentalFee + lateFee)+ "\n");
 
	}

   
	//Method to perform the maintenance after checking the required conditions
	public void performMaintenance() throws MaintenanceException{
    
	  if (this.getStatus().equals("Rented")) {
        
		throw new MaintenanceException("The room is not available for maintenance right now. It is rented");
		
      } else if (this.getStatus().equals("Maintenance")) {
        
    	throw new MaintenanceException("The room is already under maintenance");
      }
	  
      this.setStatus("Maintenance");
      System.out.println("The room is under Maintenance now");
   }

  
	//Method to set the ongoing maintenance as complete and making the room available again for renting
	public void completeMaintenance(DateTime completionDate) throws MaintenanceException {
		
		String[] dateformatsplit = this.lastMaintenanceDate.toString().split("/");
        DateTime lastMaintenanceDateFormat = new DateTime(Integer.parseInt(dateformatsplit[0]),
        		Integer.parseInt(dateformatsplit[1]),Integer.parseInt(dateformatsplit[2]));
		
	   
	   int maintenanceInterval = DateTime.diffDays(completionDate, lastMaintenanceDateFormat);
		
	   /*
	   Assumption : Despite of strict restriction that maintenance needs to be done in an internal of 10 days,
	 		  		there can be a situation where room is returned late and crossed the next estimated maintenance date.
	 		  		In that case, system will throw a warning rather than denying the request.
	    */
	   if(maintenanceInterval > 10) {
		   
		   System.out.println("WARNING : Maintenance period has exceeded the period of 10 days");
	   }
	  
       if (this.getStatus().equals("Rented")) {
    	   
    	   throw new MaintenanceException("The Room is not available for maintenance right now. It is rented");
           
       } else if (this.getStatus().equals("Available")) {
    	   
    	   throw new MaintenanceException("The room was not under maintenance");
  
       }
       
       int checkForOverlap = DateTime.diffDays(completionDate, lastMaintenanceDateFormat);
       
       if (checkForOverlap < 1) {
    	   
    	   throw new MaintenanceException("Latest maintenance completion date can't be prior to the last maintenance date stored");
    	   
       }
       
       this.setLastMaintenanceDate(this.lastMaintenanceDate);
       this.setStatus("Available");
       System.out.println("The maintenance has been completed. Now the room is available for renting");
       System.out.println("The last maintenance date has been updated to " + completionDate);
       
   }
  
	
	//Overridden Method to return a string with the room details combined keeping ":" as separator 
	public String toString() {
		
   	
		String recordsToString = super.toString() + ":" + this.getLastMaintenanceDate() + ":" + this.getFeature() + ":" + this.getImageName() ;
		return recordsToString;
		
   }
   
   
	//Overridden Method to a return a string with the room details along with rent history.
	public String getDetails() {
   	
   	String details = "Room ID:\t\t" + this.getRoomId() + "\nNumber of Beds:\t\t" + this.getNoOfBed() + "\nType:\t\t\t" + 
   	this.getRoomType() + "\nstatus:\t\t\t" + this.getStatus() + "\nFeature Summary:\t" + this.getFeature() + 
   	"\nLast maintenance date:\t" + this.getLastMaintenanceDate()  + "\nRENTAL RECORD\t\t";
   	
   	HiringRecord recentRecord = retrieveRecord();
   	
   	if (recentRecord == null) {
   		
   		return details += "empty";
   	}
   	else {
   		
   		details+= "\n" + this.retrieveRecord().getDetails();

           for (int i = 0; i < this.recentRecords.size() - 1; i++) {
             
           	details += "-----------------------------------------------\n";
               details += this.retrieveRecordIndex(i).getDetails();
           }
   	
   	}
   	return details;
	
   }	

}

