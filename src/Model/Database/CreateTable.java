package Model.Database;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;


public class CreateTable {
		
	    public static void createRoomTable() throws SQLException {
	        
	        final String DB_NAME = "CityLodgeDB";
	        final String TABLE_NAME = "room";
	        
	        //use try-with-resources Statement
	        try (Connection con = ConnectionTest.getConnection(DB_NAME);
	                Statement stmt = con.createStatement();
	        ) {
	            int result = stmt.executeUpdate("CREATE TABLE room ("
	                                        + "Room_Id Varchar(100) NOT NULL,"
	                                        + "Number_of_Beds int NOT NULL,"
	                                        + "Feature_Summary VARCHAR(200) NOT NULL,"
	                                        + "Room_Type VARCHAR(20) NOT NULL,"
	                                        + "Room_Status Varchar(20) NOT NULL,"
	                                        + "last_Maintenance_Date Varchar(30),"
	                                        + "Image_name Varchar(30),"
	                                        + "PRIMARY KEY (Room_Id))");
	            if(result == 0) {
	               
	            	return;
	            } else {
	            	
	            	throw new Exception("Table room could not be created");
	            }
	        } catch (Exception e) {
	        	
	        	throw new SQLException("Error: Could not write to Database.");
	            
	        }
	    }

		
		
		public static void createRentalRecordTable() throws SQLException, DbException {
			
			
			final String DB_NAME = "CityLodgeDB";
			final String TABLE_NAME = "record";
			
			
			//use try-with-resources Statement
			try (Connection con = ConnectionTest.getConnection(DB_NAME);
					Statement stmt = con.createStatement();
			) {
				int result = stmt.executeUpdate("CREATE TABLE record ("
											+ "record_id VARCHAR(100) NOT NULL ,"
											+ "cust_id VARCHAR(50) NOT NULL ,"
											+ "room_id VARCHAR(50) NOT NULL ,"
											+ "rent_date VARCHAR(20) NOT NULL ,"
											+ "estimated_return_date VARCHAR(20) NOT NULL ,"
											+ "actual_return_date VARCHAR(20),"
											+ "rental_fee VARCHAR(50),"
											+ "late_fee VARCHAR(50),"
											+ "PRIMARY KEY (record_id))");

				if(result==0) {
					
					return;	
				}
				else {
					
					throw new Exception("Table record could not be created");
				}
			} catch (DbException e) {
				
				throw new DbException("Error: Could not write to Database.");
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
	
			}
		}	
		
	}

