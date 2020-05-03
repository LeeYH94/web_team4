package hotel_reservation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import core.DBConnection;

public class HotelReservationControl {

	Connection conn;
	PreparedStatement pstm;
	ResultSet rs;
//	String session_hotel_reservation = "";
	public static String session_hotel_reservation = "";

	public void startHotelReservation(String session_id) {
		Random r = new Random();
		String temp = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		for (int i = 0; i < 6; i++) {
			session_hotel_reservation += temp.charAt(r.nextInt(temp.length()));
		}
		try {
			String query = "SELECT COUNT(*) FROM HOTEL_INFORMATION " + "WHERE HOTEL_RESERVATION = ?";
			conn = DBConnection.getConnection();
			pstm = conn.prepareStatement(query);
			pstm.setString(1, session_hotel_reservation);
			rs = pstm.executeQuery();
			rs.next();
			if (rs.getInt(1) == 0) {
				query = "INSERT INTO HOTEL_INFORMATION"
						+ " (ID, HOTEL_RESERVATION, HOTEL_NUM, CHECK_IN, CHECK_OUT, ROOM_COUNT, HOTEL_TOTAL_PRICE)"
						+ "VALUES (?,?,?,?,?,?,?)";
				pstm = conn.prepareStatement(query);
				pstm.setString(1, session_id);
				pstm.setString(2, session_hotel_reservation);
				pstm.setString(3, null);
				pstm.setString(4, null);
				pstm.setString(5, null);
				pstm.setString(6, null);
				pstm.setInt(7, 0);
				rs = pstm.executeQuery();
			} else {
				// session_hotel_reservation �ڵ尡 �̹� DB�� ���� ���
				startHotelReservation(session_id);
			}
		} catch (SQLException sqle) {
			System.out.println(sqle);
			System.out.println("start_hotel_reservation() ������ ����");
		} catch (Exception e) {
			System.out.println(e);
			System.out.println("�׹��� ����(setSeatCount())");
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

	// hotel_info�� seat_count�ٲٴ� �޼ҵ�
	public String setRoomCount() {
		int room_1;
		int room_2;
		int room_4;
		String query = "";
		String room_count = "";

		Scanner sc = new Scanner(System.in);
		System.out.print("1�ν� : ");
		room_1 = sc.nextInt();
		System.out.print("2�ν� : ");
		room_2 = sc.nextInt();
		System.out.print("4�ν� : ");
		room_4 = sc.nextInt();

		// DB hotel_info ���̺� seat_count ����
		room_count = room_1 + "," + room_2 + "," + room_4;

		try {
			query = "UPDATE HOTEL_INFORMATION SET ROOM_COUNT = ?" + " WHERE ROOM_RESERVATION = ?";
			conn = DBConnection.getConnection();
			pstm = conn.prepareStatement(query);
			pstm.setString(1, room_count);
			pstm.setString(2, session_hotel_reservation);
			rs = pstm.executeQuery();
		} catch (SQLException sqle) {
			System.out.println(sqle);
			System.out.println("setSeatCount() ������ ����");
		} catch (Exception e) {
			System.out.println(e);
			System.out.println("�׹��� ����(setSeatCount())");
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
		return room_count;
	}

	// room_total���� room_count ���ִ� �޼ҵ�
	public String calRoomTotal(String room_total, String room_count) {
		String[] room_total_ar = room_total.split(",");
		String[] room_count_ar = room_count.split(",");

		int[] arTotal = new int[room_total_ar.length];
		int[] arCount = new int[room_total_ar.length];

		String result = "";

		for (int i = 0; i < room_count_ar.length; i++) {
			arTotal[i] = Integer.parseInt(room_total_ar[i]);
			arCount[i] = Integer.parseInt(room_count_ar[i]);
		}

		for (int i = 0; i < arCount.length; i++) {
			arTotal[i] -= arCount[i];
		}
		result = arTotal[0] + "," + arTotal[1] + "," + arTotal[2];
		return result;
	}

	// hotel�� room_total �ٲٴ� �޼ҵ�
	public void updateHotelQuery(String hotel_num, String check_in,String check_out, String room_count) {
		String query = "";
		String room_total = "";
		
		try {
//			query = "SELECT HOTEL_NUM AND ROOM_COUNT FROM HOTEL_INFORMATION " + "WHERE HOTEL_RESERVATION = ?";
//			conn = DBConnection.getConnection();
//			pstm = conn.prepareStatement(query);
//			pstm.setString(1, session_hotel_reservation);
//			rs = pstm.executeQuery();
//			if (rs.next()) {
//				hotel_num = rs.getString(1);
//				room_count = rs.getString(2);
//			}
			
			// hotel_num�� �ٽ� ����
			query = "SELECT ROOM_TOTAL FROM HOTEL WHERE PLANE_NUM = ?";
			pstm = conn.prepareStatement(query);
			pstm.setString(1, hotel_num);
			rs = pstm.executeQuery();
			if (rs.next()) {
				// hotel�� �����ڷ� �������� room_total ������
				room_total = rs.getString(1);
			}
			// ����� �� �ٽ� room_total�� �����
			room_total = calRoomTotal(room_total, room_count);

			// room_total update
			query = "UPDATE HOTEL SET ROOM_TOTAL = ? WHERE HOTEL_NUM = ?";
			pstm = conn.prepareStatement(query);
			pstm.setString(1, room_total);
			pstm.setString(2, hotel_num);
			pstm.executeQuery();
			
			// hotel_num update
			query = "UPDATE HOTEL_INFORMATION SET HOTEL_NUM = ? WHERE PLANE_RESERVATION = ?";
			pstm = conn.prepareStatement(query);
			pstm.setString(1, hotel_num);	
			pstm.setString(2, session_hotel_reservation);
			pstm.executeQuery();
			
			// check_in update
			query = "UPDATE HOTEL_INFORMATION SET CHECK_IN = ? WHERE PLANE_RESERVATION = ?";
			pstm = conn.prepareStatement(query);
			pstm.setString(1, check_in);	
			pstm.setString(2, session_hotel_reservation);
			pstm.executeQuery();
			
			// check_out update
			query = "UPDATE HOTEL_INFORMATION SET CHECK_OUT = ? WHERE PLANE_RESERVATION = ?";
			pstm = conn.prepareStatement(query);		
			pstm.setString(1, check_out);	
			pstm.setString(2, session_hotel_reservation);
			pstm.executeQuery();
			

		} catch (SQLException sqle) {
			System.out.println(sqle);
			System.out.println("updateHotelQuery() ������ ����");
		} catch (Exception e) {
			System.out.println(e);
			System.out.println("�׹��� ����(updateHotelQuery())");
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

//public ArrayList<HotelReservationModel> selectAll(){
//	String query="SELECT * FROM HOTEL_INFORMATION AND GUEST AND HOTEL" + 
//			"WHERE HOTEL_INFORMATION.ID =? AND" + 
//			"HOTEL.HOTEL_NUM=?";
//	
//	//���� ����� DB�� �־��ִ� �۾� �ʿ�
//	//���� ����.
//	
//	
//	ArrayList<HotelReservationModel> users=new ArrayList<>();
//	HotelReservationModel user=null;
//	
//	
//	
//	try {
//		conn=DBConnection.getConnection();
//		pstm=conn.prepareStatement(query);
//		rs=pstm.executeQuery();
//		while(rs.next()) {
//			user=new HotelReservationModel();
//			user.setId(rs.getString(1));
//			user.setHotel_reservation(rs.getString(2));
//			user.setHotel_num(rs.getString(3));
//			user.setCheck_in(rs.getString(4));
//			user.setCheck_out(rs.getString(5));
//			user.setRoom_count(rs.getString(6));
//			user.setHotel_total_price(rs.getInt(7));
//			
//			users.add(user);
//		}
//	} catch(SQLException sqle) {
//		System.out.println("select() ������ ����");
//	} catch (Exception e) {
//		System.out.println("�� �� ���� ����(select())");
//	} finally {
//		try {
//			if(rs != null) {
//				rs.close();
//			}
//			if(pstm != null) {
//				pstm.close();
//			}
//			if(conn != null) {
//				conn.close();
//			}
//		} catch (SQLException e) {
//			throw new RuntimeException(e.getMessage());
//		}
//	}
//	return users;	
//}
	// ȣ�ڹ�ȣ �˻� �޼ҵ�
	public boolean checkHotelNum(String hotel_num) {

		String query = "SELECT HOTEL_NUM FROM HOTEL WHERE HOTEL_NUM = ?";
		boolean check = false;
		try {
			conn = DBConnection.getConnection();
			pstm = conn.prepareStatement(query);
			pstm.setString(1, hotel_num);
			int result = pstm.executeUpdate();

			if (result == 1) {
				check = true; // ������ true
			}

		} catch (SQLException sqle) {
			System.out.println(sqle);
			System.out.println("check_hotel_num() ������ ����");
		} catch (Exception e) {
			System.out.println(e);
			System.out.println("�� �� ���� ����(check_hotel_num())");
		} finally {
			try {
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

		return check;
	}

	// �����ڷ� ������ String�� int �迭�� �ٲٴ� �޼ҵ�
	public int[] stringToIntAr(String str) {
		String[] arTotal = str.split(",");
		int[] arIntTotal = new int[arTotal.length];
		for (int i = 0; i < arTotal.length; i++) {
			arIntTotal[i] = Integer.parseInt(arTotal[i]);
		}
		return arIntTotal;
	}

	// ȣ�� �� ���� �����ϴ� �޼ҵ�
	public int getHotelTotalPrice() {
		String query = "";
		int total_price = 0;
		int[] room_total_ar = null;
		String hotal_num = "";

		try {
			query = "SELECT HOTEL_NUM, ROOM_COUNT FROM HOTEL_INFORMATION WHERE HOTEL_RESERVATION = ?";
			conn = DBConnection.getConnection();
			pstm = conn.prepareStatement(query);
			pstm.setString(1, session_hotel_reservation);
			rs = pstm.executeQuery();

			if (rs.next()) {
				// hotel�� �����ڷ� �������� room_total ������
				hotal_num = rs.getString(1);
				room_total_ar = stringToIntAr(rs.getString(2));
			}

			query = "SELECT ROOM_1, ROOM_2, ROOM_4 FROM HOTEL_CLASS FROM WHERE HOTEL_NUM = ?";
			pstm = conn.prepareStatement(query);
			pstm.setString(1, hotal_num);
			rs = pstm.executeQuery();

			for (int i = 0; i < room_total_ar.length; i++) {
				//rs.getInt(2, 3, 4)����� room1 room2 room4�� ����� ��
				//1��° column���� hotel_num
				total_price += room_total_ar[i] * rs.getInt(i + 2);
			}

		} catch (SQLException sqle) {
			System.out.println(sqle);
			System.out.println("hotel_total_price() ������ ����");
		} catch (Exception e) {
			System.out.println(e);
			System.out.println("�׹��� ����(hotel_total_price())");
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
		return total_price;
	}
}
