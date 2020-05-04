package hotels;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import core.DBConnection;

public class HotelControl {
	
	Connection conn;
	PreparedStatement pstm;
	ResultSet rs;

	// 호텔 리스트 출력 
	// TODO 호텔에는 시간이 없는데 과연 여기에도 시간을 써야할까요?
	public void getHotelList(String country, String userDate) {
		String query = "";
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
		// time1 == user가 선택한 시간
		// time2 == 현재시간
		Date time = new Date();
		String time1 = format1.format(time);

		int compare = userDate.compareTo(time1);
		if (compare < 0) {
			// 선택한 날이 현재 날보다 이전일때
			System.out.println(time1 + " 이전의 날을 입력했습니다.");
		} else {
			// 선택한 날이 현재 날과 같거나 이후일 때 
			query = "SELECT * FROM HOTEL WHERE HOTEL_LOCATION = \'" + country + "\'";
			showHotelList(query);
		}
	}

	// 호텔 리스트 출력
	public void showHotelList(String query) {
		try {
			conn = DBConnection.getConnection();
			pstm = conn.prepareStatement(query);
			rs = pstm.executeQuery();
			System.out.println("-----------------------------------------------------------------------");
			// column명 쓰기
			System.out.println("HOTEL_NUM" + "\t" + "HOTEL_NAME" + "\t" + "HOTEL_LOCATION");
			while (rs.next()) {
				System.out.println(rs.getString(1) + "\t" + rs.getString(2) + "\t" + rs.getString(3));
				rs.getString(4);
			}
			System.out.println("-----------------------------------------------------------------------");
			pstm.close();
		} catch (SQLException sqle) {
			System.out.println(sqle);
			System.out.println("showHotelList() 쿼리문 오류");
		} catch (Exception e) {
			System.out.println(e);
			System.out.println("알 수 없는 오류(showHotelList())");
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
	
	public void showHotelPriceList(String hotel_num){
        String query = "SELECT * FROM HOTEl_CLASS WHERE HOTEL_NUM = ?";
        try {
           conn = DBConnection.getConnection();
           pstm = conn.prepareStatement(query);
           pstm.setString(1, hotel_num);
           rs = pstm.executeQuery();
           rs.next();
           
          
        System.out.println("ROOM_1\tROOM_2\tROOM_4\n"
        + rs.getInt(2) + "\t" + rs.getInt(3) + "\t" + rs.getInt(4));
        

        } catch(SQLException sqle) {
           System.out.println("hotel_price_list() 쿼리문 오류");
        } catch (Exception e) {
           System.out.println("알 수 없는 오류(hotel_price_list())");
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
