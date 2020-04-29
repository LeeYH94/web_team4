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
	public void hotelList(Date userDate) {
		String query = "";
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
		// time1 == user�� ������ �ð�
		// time2 == ����ð�
		Date time = new Date();
		String time1 = format1.format(userDate);
		String time2 = format1.format(time);

		int compare = time1.compareTo(time2);
		if (compare < 0) {
			// ������ ���� ���� ������ �����϶�
			System.out.println(time2 + " ������ ���� �Է��߽��ϴ�.");
		} else {
			// ������ ���� ���� ���� ���ų� ������ ��
			query = "SELECT * FROM HOTEL WHERE DATE = \'" + userDate + "\'";
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
			System.out.println("HOTEL_NUM" + "\t" + "HOTEL_NAME" + "\t" + "LOCATION");
			while (rs.next()) {
				System.out.println(rs.getString(1) + "\t" + rs.getString(2) + "\t" + rs.getString(3));
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
