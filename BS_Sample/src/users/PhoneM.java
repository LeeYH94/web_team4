package users;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.simple.JSONObject;

import net.nurigo.java_sdk.api.Message;
import core.DBConnection;
import hotels.HotelModel;
import net.nurigo.java_sdk.exceptions.CoolsmsException;
import planes.PlaneModel;

public class PhoneM {

	Connection conn;
	PreparedStatement pstm;
	ResultSet rs;
	
	public static String session_id = "abcd";

	public void phoneM(String id, String phone_number) {
		String query = "SELECT ID FROM GUEST WHERE ID = ? AND PHONE_NUMBER = ?";
		String temp = "";

		try {
			conn = DBConnection.getConnection();
			pstm = conn.prepareStatement(query);
			pstm.setString(1, id);
			pstm.setString(2, phone_number);
			rs = pstm.executeQuery();

			String api_key = "NCSNJ3LEIWDV3UJQ";
			String api_secret = "LLGCVA6K9Y7DAXKEGHRC7V3OOPFYW6DJ";
			Message coolsms = new Message(api_key, api_secret);

			HashMap<String, String> params = new HashMap<String, String>();
			params.put("to", phone_number); // 수신번호
			params.put("from", "01099194461"); // 발신번호
			params.put("type", "SMS"); // Message type ( SMS, LMS, MMS, ATA )
			params.put("text", "[테스트]\n임시 비밀번호 : " + id + "님의 예약이 완료되었습니다."); // 문자내용
			params.put("app_version", "JAVA SDK v2.2");
			

			try {
				JSONObject obj = coolsms.send(params);
				System.out.println("문자메세지 전송완료");
			} catch (CoolsmsException e) {
				System.out.println(e.getMessage());
				System.out.println(e.getCode());

			}
		} catch (SQLException sqle) {
			System.out.println("phoneM() 쿼리문 오류");
		} catch (Exception e) {
			System.out.println("알 수 없는 오류(phoneM())");
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

	
	public String selectPhone() {
	      String query = "SELECT PHONE_NUMBER FROM GUEST WHERE ID= ?";
	      String result = "";
	      try {
	         conn=DBConnection.getConnection();
	         pstm=conn.prepareStatement(query);
	         pstm.setString(1, session_id);
	         rs = pstm.executeQuery();
	         
	         UsersModel user = new UsersModel();
	         if (rs.next()) { 
	        	 user.setPhone(rs.getString(1));
	        	 result = user.getPhone();
	         }
	         
	      }catch(SQLException sqle) {
	         System.out.println(sqle);
	         System.out.println("selectPhone() 쿼리문 오류");
	      }
	      catch(Exception e) {
	         System.out.println(e);
	         System.out.println("알 수 없는 오류(selectPhone() method)");
	      }
	      finally {
	         try {
      	 if (rs != null) {
					rs.close();
				}
	         if(pstm != null) {
	            pstm.close();
	         }
	         if(conn!=null) {
	            conn.close();
	         }
	         }
	         catch(SQLException e){
	            throw new RuntimeException(e.getMessage());
	         }
	      }
	      return result;
	}
	
	public String messageContents() {
		String result = "";
		String query = "SELECT * FROM PLANE_INFORMATION, HOTEL_INFORMATION WHERE ID= ?";
	      try {
	         conn=DBConnection.getConnection();
	         pstm=conn.prepareStatement(query);
	         pstm.setString(1, session_id);
	         rs = pstm.executeQuery();
	         
	         PlaneModel plane = new PlaneModel();
	         HotelModel hotel = new HotelModel();
	         ArrayList<PlaneModel> arPlane = new ArrayList<>();
	         arPlane.clear();
	         ArrayList<HotelModel> arHotel = new ArrayList<>();
	         arHotel.clear();
	         if(plane.getPlane_num() != null) {
		         while(rs.next()) {
						try {
							plane.setPlane_num(rs.getString(1));
							plane.setCompany(rs.getString(2));
							plane.setDeparture(rs.getString(3));
							plane.setArrival(rs.getString(4));
							plane.setDeparture_date(rs.getString(5));
							plane.setDeparture_time(rs.getInt(6));
							plane.setSeat_total(rs.getString(7));
						} catch (Exception e) {
							throw new RuntimeException(e.getMessage());
						}
						arPlane.add(plane);
		         	}
	         } else if(hotel.getHotel_name() != null){
	        	 while(rs.next()) {
	        		 try {
						hotel.setHotel_name(rs.getString(1));
						 hotel.setHotel_num(rs.getString(2));
						 hotel.setLocation(rs.getString(3));
						 hotel.setRoom_total(rs.getString(4));
					} catch (Exception e) {
						throw new RuntimeException(e.getMessage());
					}
	        		arHotel.add(hotel);
	        	 }
	         } else {
	        	 System.out.println("예약정보 없음");
	         }
	         
	      }catch(SQLException sqle) {
	         System.out.println(sqle);
	         System.out.println("messageContents() 쿼리문 오류");
	      }
	      catch(Exception e) {
	         System.out.println(e);
	         System.out.println("알 수 없는 오류(messageContents() method)");
	      }
	      finally {
	         try {
    	 if (rs != null) {
					rs.close();
				}
	         if(pstm != null) {
	            pstm.close();
	         }
	         if(conn!=null) {
	            conn.close();
	         }
	         }
	         catch(SQLException e){
	            throw new RuntimeException(e.getMessage());
	         }
	      }
	      return result;
	}
	
	
	public static void main(String[] args) {
		PhoneM test = new PhoneM();
//		System.out.println(test.selectPhone());
//		System.out.println(test.messageContents());
//		test.phoneM(session_id, test.selectPhone());
	}
}
