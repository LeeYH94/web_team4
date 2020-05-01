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

	// HotelControl

	/*
	 * 
	 * FlightControl�� ����ϰ�,
	 * 
	 * ȣ���� Reservation�� ���� ���̱� ������ �Ұ��� ������ �ʽ��ϴ� ����ϰ� List�� Admin�� �߰��ϱ� �����ϱ� �� ������
	 * ������� ������ �մϴ�
	 * 
	 */

	Connection conn;
	PreparedStatement pstm;
	ResultSet rs;

	// ȣ�� ����Ʈ ��� 
	// TODO ȣ�ڿ��� �ð��� ���µ� ���� ���⿡�� �ð��� ����ұ��?
	public void hotelList(String country, String userDate) {
		String query = "";
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
		// time1 == user�� ������ �ð�
		// time2 == ����ð�
		Date time = new Date();
		String time1 = format1.format(time);

		int compare = userDate.compareTo(time1);
		if (compare < 0) {
			// ������ ���� ���� ������ �����϶�
			System.out.println(time1 + " ������ ���� �Է��߽��ϴ�.");
		} else {
			// ������ ���� ���� ���� ���ų� ������ �� 
			query = "SELECT * FROM HOTEL WHERE HOTEL_LOCATION = \'" + country + "\'";
			showHotelList(query);
		}
	}

	// ȣ�� ����Ʈ ���
	public void showHotelList(String query) {
		try {
			conn = DBConnection.getConnection();
			pstm = conn.prepareStatement(query);
			rs = pstm.executeQuery();
			System.out.println("-----------------------------------------------------------------------");
			// column�� ����
			System.out.println("HOTEL_NUM" + "\t" + "HOTEL_NAME" + "\t" + "HOTEL_LOCATION");
			while (rs.next()) {
				System.out.println(rs.getString(1) + "\t" + rs.getString(2) + "\t" + rs.getString(3));
				rs.getString(4);
			}
			System.out.println("-----------------------------------------------------------------------");
			pstm.close();
		} catch (SQLException sqle) {
			System.out.println(sqle);
			System.out.println("showHotelList() ������ ����");
		} catch (Exception e) {
			System.out.println(e);
			System.out.println("�� �� ���� ����(showHotelList())");
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
	
	public void hotel_price_list(String hotel_num){
        String query = "SELECT * FROM HOTEl_CLASS WHERE HOTEL_NUM = ?";
        
        int price[] = null;
        try {
           conn = DBConnection.getConnection();
           pstm = conn.prepareStatement(query);
           pstm.setString(1, hotel_num);
           rs = pstm.executeQuery();
           rs.next();
           
          
        System.out.println("ROOM_1\tROOM_2\tROOM_4\n"
        + rs.getInt(2) + "\t" + rs.getInt(3) + "\t" + rs.getInt(4));
        

        } catch(SQLException sqle) {
           System.out.println("hotel_price_list() ������ ����");
        } catch (Exception e) {
           System.out.println("�� �� ���� ����(hotel_price_list())");
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

	
	public void setSeatCount() {
		int adult = 0;
		int child = 0;
		int baby = 0;
		
		Scanner sc = new Scanner(System.in);
		adult = sc.nextInt();
		child = sc.nextInt();
		baby = sc.nextInt();
		
		String seat_count = adult + "," + child + "," + baby;
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
