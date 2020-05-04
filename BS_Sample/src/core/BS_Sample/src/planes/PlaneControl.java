package planes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import calender.Calender;
import calender.MainPromptCalender;
import core.DBConnection;

public class PlaneControl {
   Connection conn;
   PreparedStatement pstm;
   ResultSet rs;

   // 항공기 출력 메소드
   public int getAirplaneList(String departure, String arrival, String userDate, int userTime) {
      String query = "";
      Scanner sc = new Scanner(System.in);
      SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
      MainPromptCalender cal = new MainPromptCalender();
      // time1 == user가 선택한 시간
      // time2 == 현재시간
      Date time = new Date();
      String time1 = userDate;
      String time2 = format1.format(time);
      int compare = time1.compareTo(time2);
      
      try {
         conn = DBConnection.getConnection();
         query = "SELECT COUNT(*) FROM PLANE WHERE DEPARTURE_DATE = ? AND DEPARTURE = ?"
               + " AND ARRIVAL = ?";
         pstm = conn.prepareStatement(query);
         pstm.setString(1, userDate);
         pstm.setString(2, departure);
         pstm.setString(3, arrival);      
         rs = pstm.executeQuery();
         rs.next();
         if(rs.getInt(1) == 0) {
            System.out.println(userDate + "에 출발하는 비행기가 없습니다.");
            System.out.println("출발일을 다시 입력해주세요");
            userDate = cal.runPROMPT();
            System.out.print("시간을 입력하세요 : ");
            userTime = sc.nextInt();
            getAirplaneList(departure, arrival, userDate, userTime);
            return 0;
         }
         
      } catch (SQLException sqle) {
         System.out.println(sqle);
         System.out.println("getAirplaneList() 쿼리문 오류");
      } catch (Exception e) {
         System.out.println(e);
         System.out.println("알 수 없는 오류(getAirplaneList())");
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

      if (compare > 0) {
         // 선택한 날이 현재 날보다 이후일 때
         query = "SELECT * FROM PLANE WHERE DEPARTURE_DATE = \'" + userDate + "\'"
               + "AND DEPARTURE = \'" + departure + "\'" + "AND ARRIVAL = \'" + arrival + "\'";
         showAirplaneList(query);
      } else if (compare < 0) {
         // 선택한 날이 현재 날보다 이전일때
         System.out.println(time2 + " 이전의 날을 입력했습니다.");
         System.out.println("출발일을 다시 입력해주세요");
         userDate = cal.runPROMPT();
         getAirplaneList(departure, arrival, userDate, userTime);
         
      } else {
         // 선택한 날이 현재 날과 같을 때
         query = "SELECT * FROM PLANE WHERE DEPARTURE_DATE = \'" + userDate + "\' "
               + "AND DEPARTURE_TIME >= \'" + userTime + "\'"
               + "AND DEPARTURE = \'" + departure + "\'" + "AND ARRIVAL = \'" + arrival + "\'";
         showAirplaneList(query);
      }
      return 0;
   }

   // 항공기 리스트 출력
   public void showAirplaneList(String query) {
      try {
         conn = DBConnection.getConnection();
         pstm = conn.prepareStatement(query);
         rs = pstm.executeQuery();
         System.out.println("-----------------------------------------------------------------------");
         // column명 쓰기
         System.out.println("PLANE_NUM" + "\t" + "COMPANY" + "\t" + "DEPARTURE" 
         + "\t" + "ARRIVAL" + "\t" + "DEPARTURE_DATE" + "\t" + "DEPARTURE_TIME"
         + "\t" + "SEAT_COUNT");
         while (rs.next()) {
            System.out.println(rs.getString(1) + "\t" + rs.getString(2) + "\t" + rs.getString(3) + "\t"
                  + rs.getString(4) + "\t" + rs.getString(5) + "\t" + rs.getInt(6) + "\t" + rs.getString(7));
         }
         System.out.println("-----------------------------------------------------------------------");
      } catch (SQLException sqle) {
         System.out.println(sqle);
         System.out.println("showAirplaneList() 쿼리문 오류");
      } catch (Exception e) {
         System.out.println(e);
         System.out.println("알 수 없는 오류(showAirplaneList())");
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

   //등급별 가격 출력
      public void showGradePrice(String plane_num, String grade){
         String query = "SELECT * FROM " + grade + " WHERE PLANE_NUM = ?";
         try {
            conn = DBConnection.getConnection();
            pstm = conn.prepareStatement(query);
            pstm.setString(1, plane_num);
            rs = pstm.executeQuery();
            rs.next();
            rs.getString(1);
         System.out.println(rs.getInt(2) + "\t" + rs.getInt(3) + "\t" + rs.getInt(4));

         } catch(SQLException sqle) {
            System.out.println(sqle);
            System.out.println("price() 쿼리문 오류");
         } catch (Exception e) {
            System.out.println("알 수 없는 오류(price())");
         } finally {
            try {
               if(rs != null) {
                  rs.close();
               }
               if(pstm != null) {
                  pstm.close();
               }
               if(conn != null) {
                  conn.close();
               }
            } catch (SQLException e) {
               throw new RuntimeException(e.getMessage());
            }
         }
         
      }
            
}