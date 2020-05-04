package hotels;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;

import core.DBConnection;

public class HotelDB_insert {
	
	public static void main(String[] args) {
		HotelDB_insert hotel = new HotelDB_insert();
		hotel.hotelDB_Insert();
	}
	
	Connection conn;
	PreparedStatement pstm;
	ResultSet rs;
	
	String temp_1 = "0123456789";
	String temp_2 = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	ArrayList<String> ar_num_temp = new ArrayList<>();
	ArrayList<String> ar_price_temp = new ArrayList<>();


	public void hotelDB_Insert() {
		Random r = new Random();
		conn = DBConnection.getConnection();
		
		try {
			String query = "INSERT INTO HOTEL " + "(HOTEL_NUM, HOTEL_NAME, HOTEL_LOCATION, ROOM_TOTAL)"
					+ "VALUES(?, ?, ?, ?)";
			String[] arHotel = { "Raffles_hotel", "sejong_hotel", "shilla_hotel" };
			String[] arLocation = { "Korea", "Japan", "America", "China" };
			
			for (int i = 0; i < 100; i++) {
				String temp_num = "";
				temp_num += temp_2.charAt(r.nextInt(temp_2.length()));
				for (int j = 0; j < 4; j++) {
					temp_num += temp_1.charAt(r.nextInt(temp_1.length()));					
				}
				if (ar_num_temp.contains(temp_num)) {
					continue;
				}

				ar_num_temp.add(temp_num);
				pstm = conn.prepareStatement(query);
				pstm.setString(1, temp_num);
				ar_price_temp.add(temp_num);
				pstm.setString(2, arHotel[r.nextInt(arHotel.length)]);
				pstm.setString(3, arLocation[r.nextInt(arLocation.length)]);
				pstm.setString(4, r.nextInt(30) + "," + r.nextInt(30) + "," + r.nextInt(30));
				pstm.executeQuery();
				
			}
			System.out.println("완료");

		} catch (SQLException sqle) {
			System.out.println(sqle);
			System.out.println("hotelDB_Insert() 쿼리문 오류");
		} catch (Exception e) {
			System.out.println(e);
			System.out.println("알 수 없는 오류(hotelDB_Insert())");
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstm != null) {
					pstm.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				throw new RuntimeException(e.getMessage());
			}
		}
	// HOTEL_CLASS INSERT 메소드 필요 (1인실, 2인실, 4인실 호텔 방번호에 맞는 값이 담기도록 할것) TODO
	
	
	try {
    	conn = DBConnection.getConnection();
		String query2 = "INSERT INTO HOTEL_CLASS(HOTEL_NUM, ROOM_1, ROOM_2, ROOM_4)VALUES(?, ?, ?, ?)";
		  for (int i = 0; i < 100; i++) {
			  pstm = conn.prepareStatement(query2);
			  pstm.setString(1, ar_price_temp.get(i));
			  pstm.setInt(2, (r.nextInt(10) + 25) * 10000);
			  pstm.setInt(3, (r.nextInt(10) + 50) * 10000);
			  pstm.setInt(4, (r.nextInt(10) + 60) * 10000);
			  rs = pstm.executeQuery();
		  }
		} catch (SQLException sqle) {
	        System.out.println(sqle);
	        System.out.println("hotelDBInsert2() 쿼리문 오류");
			} catch (Exception e) {
	        System.out.println(e);
	        System.out.println("알 수 없는 오류(hotelDBInsert2())");
			} finally {
		        try {
		           if (rs != null) {
		              rs.close();
		           }
		           if (pstm != null) {
		              pstm.close();
		           }
		           if (conn != null) {
		              conn.close();
		           }
		        } catch (SQLException e) {
		           throw new RuntimeException(e.getMessage());
	        }
		}
	
	}
}



	
	
