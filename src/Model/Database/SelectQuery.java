package Model.Database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import Model.util.DateTime;
import javafx.scene.control.Alert;

import org.hsqldb.result.Result;

import Model.Standard;
import Model.CityLodge;
import Model.HiringRecord;
import Model.Suite;

public class SelectQuery {
	
	public static void selectRoomData(CityLodge lodge) throws Exception, SQLException {
		
		final String DB_NAME = "CityLodgeDB";
		final String TABLE_NAME = "room";
		
		try (Connection con = ConnectionTest.getConnection(DB_NAME);
				Statement stmt = con.createStatement();) {
			
			String query = "SELECT * FROM " + TABLE_NAME;
			
			try (ResultSet resultSet = stmt.executeQuery(query)) {
				
				while(resultSet.next()) {
				
					String id = resultSet.getString("Room_Id");
					int noOfBeds = resultSet.getInt("Number_of_Beds");
					String features = resultSet.getString("Feature_Summary");
					String type = resultSet.getString("Room_Type");
					String status = resultSet.getString("Room_Status");
					String lastMaintDate = resultSet.getString("last_Maintenance_Date");
					String imageName = resultSet.getString("Image_name");
					
					
					if(type.equalsIgnoreCase("Suite")) {
						
				        
				        Suite suite = new Suite(id,features,lastMaintDate);
				        suite.setImageName(imageName);
				        lodge.roomCollection().add(suite);
					}
					else if (type.equalsIgnoreCase("Standard")) {
						
						Standard std = new Standard(id,noOfBeds,features);
				        std.setImageName(imageName);
				        lodge.roomCollection().add(std);
					}
				}
			} catch (SQLException e) {
				
				throw new SQLException("Something went wrong. Problem in reading the data from DB");
			}

		} catch (Exception e) {
			
			throw new Exception(e.getMessage());
		}
	}
	
	
	public static void selectRecordsData(CityLodge lodge) throws Exception, SQLException {
		
		final String DB_NAME = "CityLodgeDB";
		final String TABLE_NAME = "RECORD";
		
		try (Connection con = ConnectionTest.getConnection(DB_NAME);
				Statement stmt = con.createStatement();) {
			
			String query = "SELECT * FROM " + TABLE_NAME;
			
			try (ResultSet resultSet = stmt.executeQuery(query)) {
				
				while(resultSet.next()) {
				
					String record_id = resultSet.getString("record_id");
					String cust_id = resultSet.getString("cust_id");
					String room_id = resultSet.getString("room_id");
					String read_rent_date = resultSet.getString("rent_date");
					String read_return_date = resultSet.getString("estimated_return_date");
					String read_actual_return_date = resultSet.getString("actual_return_date");	
					String rental_fee = resultSet.getString("rental_fee");
					String late_fee = resultSet.getString("late_fee");
					
					Double lateFee;
					Double rentalFee;
					DateTime actualReturnDate;
					
					String[] dateformatsplit = read_rent_date.toString().split("/");
			        DateTime rent_date = new DateTime(Integer.parseInt(dateformatsplit[0]),
			        		Integer.parseInt(dateformatsplit[1]),Integer.parseInt(dateformatsplit[2]));
			        
			        String[] dateformatsplit2 = read_return_date.toString().split("/");
			        DateTime estimated_return_date = new DateTime(Integer.parseInt(dateformatsplit2[0]),
			        		Integer.parseInt(dateformatsplit2[1]),Integer.parseInt(dateformatsplit2[2]));
			    		        
			        
					if(read_actual_return_date.equals("none")) {
						
						actualReturnDate = null;
						rentalFee = 0.0;
						lateFee = 0.0;
		
					}
					else {
							String[] dateformatsplit3 = read_actual_return_date.toString().split("/");
					        actualReturnDate = new DateTime(Integer.parseInt(dateformatsplit3[0]),
					        		Integer.parseInt(dateformatsplit3[1]),Integer.parseInt(dateformatsplit3[2]));
					        
					        lateFee = Double.parseDouble(late_fee);
					        rentalFee = Double.parseDouble(rental_fee);
			
					}
					      
					    int index = lodge.availRoomIndex(room_id);
					    HiringRecord record = new HiringRecord(room_id,cust_id,rent_date,estimated_return_date,actualReturnDate,
					    		rentalFee,lateFee);
					    lodge.roomCollection().get(index).addRecord(record);			      
					        
					}
				}
			       catch (SQLException e) {
				
						Alert alertPopup = new Alert(Alert.AlertType.ERROR);
						alertPopup.setTitle("Error Alert");
						alertPopup.setContentText(e.getMessage());
						alertPopup.showAndWait();
			} catch (Exception e) {
			
				Alert alertPopup = new Alert(Alert.AlertType.ERROR);
				alertPopup.setTitle("Error Alert");
				alertPopup.setContentText(e.getMessage());
				alertPopup.showAndWait();
		}
	}
	}
}
	
