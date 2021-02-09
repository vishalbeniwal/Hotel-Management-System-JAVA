package Model.util;

//This class is implemented to validate the date input from the user
public class DateValidator {
	
		public boolean isValidDate(int day, int month, int year) {
			
			if (month == 2) {	
			
				if (isLeap(year)) { 
					
					return (day <= 29); 
				
				} else {
					
					return (day <= 28);
				}
			
			}	
			//Checks for days		
			if (day < 1 || day > 31) {
				
				System.out.println("Invalid Day. Day should be between 1 and 31");
				return false;
			}
			//Checks for months
			if (month < 1 || month > 12) {
			
				System.out.println("Invalid Month. Month should be between 1 and 12");
				return false;	
			}
			//Assumption : The application is running in present time. Year before 2019 will not be accepted
			if (year < 2019 ) {
				
				System.out.println("Invalid year. Please recheck your details");
				return false;
			}
			
			if (month == 4 || month == 6 || month == 9 || month == 11) {
				
				return (day <= 30); 		
			}
			
			return true;
			
		}
 
		
		public boolean isLeap(int year) 
		
		{  
			return (((year % 4 == 0) && 
					(year % 100 != 0)) || 
					(year % 400 == 0)); 
		} 

}
