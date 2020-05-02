package planes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import core.DBConnection;

public class PlaneControl {
	Connection conn;
	PreparedStatement pstm;
	ResultSet rs;

	// �װ��� ��� �޼ҵ�
	public void getAirplaneList(String departure, String arrival, String userDate, int userTime) {
		String query = "";
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
		// time1 == user�� ������ �ð�
		// time2 == ����ð�
		Date time = new Date();
		String time1 = userDate;
		String time2 = format1.format(time);

		int compare = time1.compareTo(time2);
		if (compare > 0) {
			// ������ ���� ���� ������ ������ ��
			query = "SELECT * FROM PLANE WHERE DEPARTURE_DATE = \'" + userDate + "\'"
					+ "AND DEPARTURE = \'" + departure + "\'" + "AND ARRIVAL = \'" + arrival + "\'";
			showAirplaneList(query);
		} else if (compare < 0) {
			// ������ ���� ���� ������ �����϶�
			System.out.println(time2 + " ������ ���� �Է��߽��ϴ�.");
		} else {
			// ������ ���� ���� ���� ���� ��
			query = "SELECT * FROM PLANE WHERE DEPARTURE_DATE = \'" + userDate + "\' "
					+ "AND DEPARTURE_TIME >= \'" + userTime + "\'"
					+ "AND DEPARTURE = \'" + departure + "\'" + "AND ARRIVAL = \'" + arrival + "\'";
			showAirplaneList(query);
		}
	}

	// �װ��� ����Ʈ ���
	public void showAirplaneList(String query) {
		try {
			conn = DBConnection.getConnection();
			pstm = conn.prepareStatement(query);
			rs = pstm.executeQuery();
			System.out.println("-----------------------------------------------------------------------");
			// column�� ����
			System.out.println("PLANE_NUM" + "\t" + "COMPANY" + "\t" + "DEPARTURE" 
			+ "\t" + "ARRIVAL" + "\t" + "DEPARTURE_DATE" + "\t" + "DEPARTURE_TIME"
			+ "\t" + "SEAT_COUNT");
			while (rs.next()) {
				System.out.println(rs.getString(1) + "\t" + rs.getString(2) + "\t" + rs.getString(3) + "\t"
						+ rs.getString(4) + "\t" + rs.getString(5) + "\t" + rs.getInt(6) + "\t" + rs.getString(7));
			}
			System.out.println("-----------------------------------------------------------------------");
			pstm.close();
		} catch (SQLException sqle) {
			System.out.println(sqle);
			System.out.println("showAirplaneList() ������ ����");
		} catch (Exception e) {
			System.out.println(e);
			System.out.println("�� �� ���� ����(showAirplaneList())");
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

	//��޺� ���� ���
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
	         System.out.println("price() ������ ����");
	      } catch (Exception e) {
	         System.out.println("�� �� ���� ����(price())");
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
