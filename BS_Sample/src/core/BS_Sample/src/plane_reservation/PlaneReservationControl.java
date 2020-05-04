package plane_reservation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import core.DBConnection;

public class PlaneReservationControl {
	Connection conn;
	PreparedStatement pstm;
	ResultSet rs;
//	String session_plane_reservation = "";
	public static String session_plane_reservation = "";

	public void startPlaneReservation(String session_id) {
		Random r = new Random();
		String temp = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		for (int i = 0; i < 6; i++) {
			session_plane_reservation += temp.charAt(r.nextInt(temp.length()));
		}
		try {
			String query = "SELECT COUNT(*) FROM PLANE_INFORMATION WHERE PLANE_RESERVATION = ?";
			conn = DBConnection.getConnection();
			pstm = conn.prepareStatement(query);
			pstm.setString(1, session_plane_reservation);
			rs = pstm.executeQuery();
			rs.next();
			if (rs.getInt(1) == 0) {
				query = "INSERT INTO PLANE_INFORMATION"
						+ " (PLANE_RESERVATION, ID, PLANE_NUM_1, PLANE_NUM_2, GRADE, SEAT_COUNT, PLANE_TOTAL_PRICE)"
						+ "VALUES (?,?,?,?,?,?,?)";
				pstm = conn.prepareStatement(query);
				pstm.setString(1, session_plane_reservation);
				pstm.setString(2, session_id);
				pstm.setString(3, null);
				pstm.setString(4, null);
				pstm.setString(5, null);
				pstm.setString(6, null);
				pstm.setInt(7, 0);
				pstm.executeQuery();
			} else {
				// session_plane_reservation 코드가 이미 DB에 있을 경우
				startPlaneReservation(session_id);
			}
		} catch (SQLException sqle) {
			System.out.println(sqle);
			System.out.println("start_plane_reservation() 쿼리문 오류");
		} catch (Exception e) {
			System.out.println(e);
			System.out.println("그밖의 문제(start_plane_reservation())");
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

	// plane_info의 seat_count바꾸는 메소드
	public int setSeatCount() {
		int adult;
		int child;
		int baby;
		String query = "";
		String seat_count = "";
		int total_count;

		Scanner sc = new Scanner(System.in);
		System.out.print("성인의 인원을 입력해주세요 : ");
		adult = sc.nextInt();
		System.out.print("아동의 인원을 입력해주세요 : ");
		child = sc.nextInt();
		System.out.print("유아의 인원을 입력해주세요 : ");
		baby = sc.nextInt();

		// DB plane_info 테이블에 seat_count 전송
		seat_count = adult + "," + child + "," + baby;
		// DB plane 테이블의 seat_total을 빼주기 위한 변수
		total_count = adult + child + baby;

		try {
			query = "UPDATE PLANE_INFORMATION SET SEAT_COUNT = ?" + " WHERE PLANE_RESERVATION = ?";
			conn = DBConnection.getConnection();
			pstm = conn.prepareStatement(query);
			pstm.setString(1, seat_count);
			pstm.setString(2, session_plane_reservation);
			rs = pstm.executeQuery();
		} catch (SQLException sqle) {
			System.out.println(sqle);
			System.out.println("setSeatCount() 쿼리문 오류");
		} catch (Exception e) {
			System.out.println(e);
			System.out.println("그밖의 문제(setSeatCount())");
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
		return total_count;
	}

	// 구분자로 나눠진 String을 int 배열로 바꾸는 메소드
	public int[] stringToIntAr(String str) {
		String[] arTotal = str.split(",");
		int[] arIntTotal = new int[arTotal.length];
		for (int i = 0; i < arTotal.length; i++) {
			arIntTotal[i] = Integer.parseInt(arTotal[i]);
		}
		return arIntTotal;
	}

	// int배열을 구분자로 나뉘어진 String으로 바꾸는 메소드
	public String intArToString(int[] intAr) {
		String str = "";
		for (int i = 0; i < intAr.length; i++) {
			str += intAr[i];
			if (i != intAr.length - 1) {
				str += ",";
			}
		}
		return str;
	}

	// plane의 seat_total 바꾸는 메소드
	public void updatePlaneQuery(String plane_num1, String plane_num2, String grade, int total_count, int total_price) {
		// plane_num_1에서도 사람 수 만큼 빼주어야 하고
		// 왕복이면 plane_num_2에서도 빼주어야 함
		String query = "";
		int[] seat_total_ar = null;
		String seat_total = "";

		try {
			// plane_num에 다시 연결
			conn = DBConnection.getConnection();
			query = "SELECT SEAT_TOTAL FROM PLANE WHERE PLANE_NUM = ?";
			pstm = conn.prepareStatement(query);
			pstm.setString(1, plane_num1);
			rs = pstm.executeQuery();
			if (rs.next()) {
				// plane의 [economy좌석수, business좌석수, first좌석수] 배열
				seat_total_ar = stringToIntAr(rs.getString(1));
			}
			if (grade.equals("economy_c")) {
				seat_total_ar[0] -= total_count;
			} else if (grade.equals("business_c")) {
				seat_total_ar[1] -= total_count;
			} else {
				seat_total_ar[2] -= total_count;
			}
			// 가격 조정 후 seat_total 다시 만들어줌
			seat_total = intArToString(seat_total_ar);

			// seat_total update
			query = "UPDATE PLANE SET SEAT_TOTAL = ? WHERE PLANE_NUM = ?";
			pstm = conn.prepareStatement(query);
			pstm.setString(1, seat_total);
			pstm.setString(2, plane_num1);
			pstm.executeQuery();

			// plane_num1,grade update
			query = "UPDATE PLANE_INFORMATION SET PLANE_NUM_1 = ?, GRADE = ?, PLANE_TOTAL_PRICE = ? WHERE PLANE_RESERVATION = ?";
			pstm = conn.prepareStatement(query);
			pstm.setString(1, plane_num1);	
			pstm.setString(2, grade);	
			pstm.setInt(3, total_price);	
			pstm.setString(4, session_plane_reservation);
			pstm.executeQuery();
			
		} catch (SQLException sqle) {
			System.out.println(sqle);
			System.out.println("setSeatTotal() 쿼리문 오류");
		} catch (Exception e) {
			System.out.println(e);
			System.out.println("그밖의 문제(setSeatTotal())");
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
		
		// 왕복일 경우
		if (plane_num2 != null) {
			// 위의 try문 한번더(plane_num_2)
			try {
				// plane_num에 다시 연결
				query = "SELECT SEAT_TOTAL FROM PLANE WHERE PLANE_NUM = ?";
				pstm = conn.prepareStatement(query);
				pstm.setString(1, plane_num2);
				rs = pstm.executeQuery();
				if (rs.next()) {
					// plane의 [economy좌석수, business좌석수, first좌석수] 배열
					seat_total_ar = stringToIntAr(rs.getString(1));
				}
				if (grade.equals("economy_c")) {
					seat_total_ar[0] -= total_count;
				} else if (grade.equals("business_c")) {
					seat_total_ar[1] -= total_count;
				} else {
					seat_total_ar[2] -= total_count;
				}
				// 가격 조정 후 seat_total 다시 만들어줌
				seat_total = intArToString(seat_total_ar);

				// seat_total update
				query = "UPDATE PLANE SET SEAT_TOTAL = ? WHERE PLANE_NUM = ?";
				pstm = conn.prepareStatement(query);
				pstm.setString(1, seat_total);
				pstm.setString(2, plane_num2);
				pstm.executeQuery();

				// plane_num_2 update
				query = "UPDATE PLANE_INFORMATION SET PLANE_NUM_2 = ? WHERE PLANE_RESERVATION = ?";
				pstm = conn.prepareStatement(query);
				pstm.setString(1, plane_num2);
				pstm.setString(2, session_plane_reservation);
				pstm.executeQuery();

			} catch (SQLException sqle) {
				System.out.println(sqle);
				System.out.println("setSeatTotal() 쿼리문 오류");
			} catch (Exception e) {
				System.out.println(e);
				System.out.println("그밖의 문제(setSeatTotal())");
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

	// 목록
//	public ArrayList<PlaneReservationModel> selectAll(){
//		String query="SELECT *" + 
//				"FROM PLANE_INFORMATION P_I,GUEST g,PLANE P" + 
//				"WHERE P_I.ID=g.ID";
//		ArrayList<PlaneReservationModel> users=new ArrayList<>();
//		PlaneReservationModel user=null;
//		
//		try {
//			conn=DBConnection.getConnection();
//			pstm=conn.prepareStatement(query);
//			rs=pstm.executeQuery();
//			while(rs.next()) {
//				user=new PlaneReservationModel();
//				user.setPlane_reservation(rs.getString(1));
//				user.setId(rs.getString(2));
//				user.setPlane_num_1(rs.getString(3));
//				user.setPlane_num_2(rs.getString(4));
//				user.setGrade(rs.getString(5));
//				user.setSeat_count(rs.getString(6));
//				user.setPlane_total_price(rs.getInt(7));
//				
//				users.add(user);
//			} 
//		}catch(SQLException sqle) {
//			System.out.println("select() 쿼리문 오류");
//		} catch (Exception e) {
//			System.out.println("알 수 없는 오류(select())");
//		} finally {
//			try {
//				if(rs != null) {
//					rs.close();
//				}
//				if(pstm != null) {
//					pstm.close();
//				}
//				if(conn != null) {
//					conn.close();
//				}
//			} catch (SQLException e) {
//				throw new RuntimeException(e.getMessage());
//			}
//		}
//		return users;	
//	
//	}

//	 총 가격 리턴하는 메소드
	public int getPlaneTotalPrice(String plane_num1, String plane_num2, String grade) {
		String query = "";
		int total_price = 0;
		int[] arSeat_cnt = null;

		try {
			query = "SELECT SEAT_COUNT, ADULT, CHILD, BABY FROM " + grade + " WHERE PLANE_NUM = ?";				
			conn = DBConnection.getConnection();
			pstm = conn.prepareStatement(query);
			pstm.setString(1, plane_num1);
			rs = pstm.executeQuery();
			rs.next();
			
			for (int i = 0; i < arSeat_cnt.length; i++) {
				total_price += arSeat_cnt[i] * rs.getInt(i + 1);
			}

			if (plane_num2 != null) {
				// 왕복일 경우
				query = "SELECT ADULT, CHILD, BABY FROM " + grade + " WHERE PLANE_NUM = ?";	
				pstm = conn.prepareStatement(query);
				pstm.setString(1, plane_num2);
				rs = pstm.executeQuery();
				rs.next();
				for (int i = 0; i < arSeat_cnt.length; i++) {
					total_price += arSeat_cnt[i] * rs.getInt(i + 1);
				}
			}

		} catch (SQLException sqle) {
			System.out.println(sqle);
			System.out.println("total_price() 쿼리문 오류");
		} catch (Exception e) {
			System.out.println(e);
			System.out.println("그밖의 문제(total_price())");
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

	// 항공기 번호가 있는지 확인하는 메소드
	public boolean checkPlaneNum(String plane_num) {

		String query = "SELECT PLANE_NUM FROM PLANE WHERE PLANE_NUM = ?";
		boolean check = false;
		try {
			conn = DBConnection.getConnection();
			pstm = conn.prepareStatement(query);
			pstm.setString(1, plane_num);
			int result = pstm.executeUpdate();

			if (result == 1) {
				check = true; // 있으면 false
			}

		} catch (SQLException sqle) {
			System.out.println(sqle);
			System.out.println("check_plane_num() 쿼리문 오류");
		} catch (Exception e) {
			System.out.println(e);
			System.out.println("알 수 없는 오류(check_plane_num())");
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

}
