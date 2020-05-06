package users;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import org.json.simple.JSONObject;

import net.nurigo.java_sdk.api.Message;
import core.DBConnection;
import net.nurigo.java_sdk.exceptions.CoolsmsException;

public class PhoneM {

   Connection conn;
   PreparedStatement pstm;
   ResultSet rs;
   
//   public static String session_id = "";

   public void phoneM(String id, String phone_number) {
      PhoneM phoneM = new PhoneM();
      String query = "SELECT ID FROM GUEST WHERE ID = ? AND PHONE_NUMBER = ?";

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
         params.put("type", "LMS"); // Message type ( SMS, LMS, MMS, ATA )
         params.put("text", "예약완료 메세지 : \n" + phoneM.messageContents1(id) 
                  + phoneM.messageContents2(id) + " 예약이 완료되었습니다."); // 문자내용
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

   
   public String selectPhone(String id) {
         String query = "SELECT PHONE_NUMBER FROM GUEST WHERE ID= ?";
         String result = "";
         try {
            conn=DBConnection.getConnection();
            pstm=conn.prepareStatement(query);
            pstm.setString(1, id);
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
   
   public String messageContents1(String id) {
      String result = "";
      String query = "SELECT * FROM PLANE_INFORMATION WHERE ID= ?";
      PhoneModel phone = new PhoneModel();
         try {
            conn=DBConnection.getConnection();
            pstm=conn.prepareStatement(query);
            pstm.setString(1, id);
            rs = pstm.executeQuery();
            
               while(rs.next()) {
                     phone.setPLANE_RESERVATION(rs.getString(1));
                     result += "예약번호 : " + phone.getPLANE_RESERVATION() + "\n";
                     phone.setID(rs.getString(2));
                     result += "ID : " + phone.getID() + "\n";
                     phone.setPLANE_NUM1(rs.getString(3));
                     result += "항공기번호1 : " + phone.getPLANE_NUM1() + "\n";
                     phone.setPLANE_NUM2(rs.getString(4));
                     result += "항공기 번호2 : " + phone.getPLANE_NUM2() + "\n";
                     phone.setGRADE(rs.getString(5));
                     result += "좌석등급 : " + phone.getGRADE() + "\n";
                     phone.setSEAT_COUNT(rs.getString(6));
                     result += "성인, 소아, 유아 : (" + phone.getSEAT_COUNT() + ")\n";
                     phone.setPLANE_TOTAL_PRICE(rs.getInt(7));
                     result += "총 결제 금액: " + phone.getPLANE_TOTAL_PRICE()+ "\n\n";
                  }
            
         }catch(SQLException sqle) {
            System.out.println(sqle);
            System.out.println("messageContents1() 쿼리문 오류");
         }
         catch(Exception e) {
            System.out.println(e);
            System.out.println("알 수 없는 오류(messageContents1() method)");
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
   
   
   
   public String messageContents2(String id) {
      String result = "";
      String query = "SELECT * FROM HOTEL_INFORMATION WHERE ID= ?";
      PhoneModel phone = new PhoneModel();
         try {
            conn=DBConnection.getConnection();
            pstm=conn.prepareStatement(query);
            pstm.setString(1, id);
            rs = pstm.executeQuery();
            
               while(rs.next()) {
                        result = "";
                     phone.setPLANE_RESERVATION(rs.getString(1));
                     result += "ID : " + phone.getPLANE_RESERVATION() + " \n";
                     phone.setID(rs.getString(2));
                     result += "예약번호 : " + phone.getID() + " \n";
                     phone.setPLANE_NUM1(rs.getString(3));
                     result += "호텔번호 : " + phone.getPLANE_NUM1() + " \n";
                     phone.setPLANE_NUM2(rs.getString(4));
                     result += "체크인 날짜 : " + phone.getPLANE_NUM2() + " \n";
                     phone.setGRADE(rs.getString(5));
                     result += "체크아웃 날짜 : " + phone.getGRADE() + " \n";
                     phone.setSEAT_COUNT(rs.getString(6));
                     result += "1인실, 2인실, 4인실 : (" + phone.getSEAT_COUNT() + ") \n";
                     phone.setPLANE_TOTAL_PRICE(rs.getInt(7));
                     result += "총 결제 금액: " + phone.getPLANE_TOTAL_PRICE();
                  }
            
         }catch(SQLException sqle) {
            System.out.println(sqle);
            System.out.println("messageContents2() 쿼리문 오류");
         }
         catch(Exception e) {
            System.out.println(e);
            System.out.println("알 수 없는 오류(messageContents2() method)");
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
}