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
	   
	   public void start_plane_reservation() {
	      Random r = new Random();
	      String temp = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	      for (int i = 0; i < 6; i++) {
	         session_plane_reservation += temp.charAt(r.nextInt(temp.length()));
	      }
	   }
	
	//plane_info�� seat_count�ٲٴ� �޼ҵ�
	public int setSeatCount() {
		int adult;
		int child;
		int baby;
		String query = "";
		String seat_count = "";
		int total_count;
		
		Scanner sc = new Scanner(System.in);
		adult = sc.nextInt();
		child = sc.nextInt();
		baby = sc.nextInt();
		
		//DB plane_info ���̺� seat_count ����
		seat_count = adult + "," + child + "," + baby;
		//DB plane ���̺��� seat_total�� ���ֱ� ���� ����
		total_count = adult + child + baby;
		
		try {
			query = "UPDATE PLANE_INFORMATION SET SEAT_COUNT = ?"
					+ " WHERE PLANE_RESERVATION = ?";
			conn = DBConnection.getConnection();
			pstm = conn.prepareStatement(query);
			pstm.setString(1, seat_count);
			pstm.setString(2, session_plane_reservation);
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
		return total_count;
	}
	
	//�����ڷ� ������ String�� int �迭�� �ٲٴ� �޼ҵ�
	public int[] stringToIntAr(String str) {
		String[] arTotal = str.split(",");
		int[] arIntTotal = new int[arTotal.length];
		for (int i = 0; i < arTotal.length; i++) {
			arIntTotal[i] = Integer.parseInt(arTotal[i]);
		}
		return arIntTotal;
	}
	
	//int�迭�� �����ڷ� �������� String���� �ٲٴ� �޼ҵ�
	public String intArToString(int[] intAr) {
		String str = "";
		for (int i = 0; i < intAr.length; i++) {
			str += intAr[i];
			if(i != intAr.length - 1) {
				str += ",";
			}
		}
		return str;
	}
	
	//plane�� seat_total �ٲٴ� �޼ҵ�
	   public void setSeatTotal(String plane_num1, String plane_num2, String grade, int total_count) {
	      //plane_num_1������ ��� �� ��ŭ ���־�� �ϰ�
	      //�պ��̸� plane_num_2������ ���־�� ��
	      String query = "";
	      int[] seat_total_ar = null;
	      String seat_total = "";
	      
	      try {
//	         query = "SELECT PLANE_NUM_1 AND PLANE_NUM_2 AND "
//	               + "GRADE FROM PLANE_INFORMATION WHERE PLANE_RESERVATION = ?";
//	         conn = DBConnection.getConnection();
//	         pstm = conn.prepareStatement(query);
//	         pstm.setString(1, session_plane_reservation);
//	         rs = pstm.executeQuery();
//	         if(rs.next()) {
//	            plane_num1 = rs.getString(1);
//	            plane_num2 = rs.getString(2);
//	            grade = rs.getString(3);
//	         }
	         //plane_num�� �ٽ� ����
	         query = "SELECT SEAT_TOTAL FROM PLANE WHERE PLANE_NUM = ?";
	         pstm = conn.prepareStatement(query);
	         pstm.setString(1, plane_num1);
	         rs = pstm.executeQuery();
	         if(rs.next()) {
	            //plane�� [economy�¼���, business�¼���, first�¼���] �迭
	            seat_total_ar = stringToIntAr(rs.getString(1));
	         }
	         if(grade.equals("economy_c")) {
	            seat_total_ar[0] -= total_count;
	         } else if (grade.equals("business_c")) {
	            seat_total_ar[1] -= total_count;
	         } else {
	            seat_total_ar[2] -= total_count;
	         }
	         //���� ���� �� seat_total �ٽ� �������
	         seat_total = intArToString(seat_total_ar);
	         
	         //seat_total update
	         query = "UPDATE PLANE SET SEAT_TOTAL = ? WHERE PLANE_NUM = ?";
	         pstm = conn.prepareStatement(query);
	         pstm.setString(1, seat_total);
	         pstm.setString(2, plane_num1);
	         pstm.executeQuery();
	         
	      } catch (SQLException sqle) {
	         System.out.println(sqle);
	         System.out.println("setSeatTotal() ������ ����");
	      } catch (Exception e) {
	         System.out.println(e);
	         System.out.println("�׹��� ����(setSeatTotal())");
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
	      //�պ��� ���
	      if(plane_num2 != null) {
	         //���� try�� �ѹ���(plane_num_2)
	         try {
	            //plane_num�� �ٽ� ����
	            query = "SELECT SEAT_TOTAL FROM PLANE WHERE PLANE_NUM = ?";
	            pstm = conn.prepareStatement(query);
	            pstm.setString(1, plane_num2);
	            rs = pstm.executeQuery();
	            if(rs.next()) {
	               //plane�� [economy�¼���, business�¼���, first�¼���] �迭
	               seat_total_ar = stringToIntAr(rs.getString(1));
	            }
	            if(grade.equals("economy_c")) {
	               seat_total_ar[0] -= total_count;
	            } else if (grade.equals("business_c")) {
	               seat_total_ar[1] -= total_count;
	            } else {
	               seat_total_ar[2] -= total_count;
	            }
	            //���� ���� �� seat_total �ٽ� �������
	            seat_total = intArToString(seat_total_ar);
	            
	            //seat_total update
	            query = "UPDATE PLANE SET SEAT_TOTAL = ? WHERE PLANE_NUM = ?";
	            pstm = conn.prepareStatement(query);
	            pstm.setString(1, seat_total);
	            pstm.setString(2, plane_num2);
	            pstm.executeQuery();
	            
	         } catch (SQLException sqle) {
	            System.out.println(sqle);
	            System.out.println("setSeatTotal() ������ ����");
	         } catch (Exception e) {
	            System.out.println(e);
	            System.out.println("�׹��� ����(setSeatTotal())");
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
	
	//���
	public ArrayList<PlaneReservationModel> selectAll(){
		String query="SELECT *" + 
				"FROM PLANE_INFORMATION P_I,GUEST g,PLANE P" + 
				"WHERE P_I.ID=g.ID";
		ArrayList<PlaneReservationModel> users=new ArrayList<>();
		PlaneReservationModel user=null;
		
		try {
			conn=DBConnection.getConnection();
			pstm=conn.prepareStatement(query);
			rs=pstm.executeQuery();
			while(rs.next()) {
				user=new PlaneReservationModel();
				user.setPlane_reservation(rs.getString(1));
				user.setId(rs.getString(2));
				user.setPlane_num_1(rs.getString(3));
				user.setPlane_num_2(rs.getString(4));
				user.setGrade(rs.getString(5));
				user.setSeat_count(rs.getString(6));
				user.setPlane_total_price(rs.getInt(7));
				
				users.add(user);
			} 
		}catch(SQLException sqle) {
			System.out.println("select() ������ ����");
		} catch (Exception e) {
			System.out.println("�� �� ���� ����(select())");
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
		return users;	
	
	}
	
	
//	 �� ���� �����ϴ� �޼ҵ�
	public int total_price() {
		String query = "";
		int total_price = 0;
		String plane_num_1 = "";
		String plane_num_2 = "";
		String grade = "";
		int[] arSeat_cnt = null;
		
		try {
			query = "SELECT PLANE_NUM_1 AND PLANE_NUM_2 AND SEAT_COUNT AND GRADE "
					+ "FROM PLANE_INFORMATION WHERE PLANE_RESERVATION = ?";
			//������ ���� �����ٶ�
			conn = DBConnection.getConnection();
			pstm = conn.prepareStatement(query);
			pstm.setString(1, session_plane_reservation);
			rs = pstm.executeQuery();
		
			if(rs.next()) {
				plane_num_1 = rs.getString(1);
				plane_num_2 = rs.getString(2);
				arSeat_cnt = stringToIntAr(rs.getString(3));
				grade = rs.getString(4);
			}
			
			query = "SELECT ADULT AND CHILD AND BABY FROM ? WHERE = ?";
			pstm = conn.prepareStatement(query);
			pstm.setString(1, grade);
			pstm.setString(2, plane_num_1);
			rs = pstm.executeQuery();
			rs.next();
			
			for (int i = 0; i < arSeat_cnt.length; i++) {
				total_price += arSeat_cnt[i] * rs.getInt(i+1);
			}
			
			if(plane_num_2 != null) {
				// �պ��� ���
				query = "SELECT ADULT AND CHILD AND BABY FROM ? WHERE = ?";
				pstm = conn.prepareStatement(query);
				pstm.setString(1, grade);
				pstm.setString(2, plane_num_2);
				rs = pstm.executeQuery();
				rs.next();
				
				for (int i = 0; i < arSeat_cnt.length; i++) {
					total_price += arSeat_cnt[i] * rs.getInt(i+1);
				}
			}	
		
		}catch (SQLException sqle) {
			System.out.println(sqle);
			System.out.println("total_price() ������ ����");
		} catch (Exception e) {
			System.out.println(e);
			System.out.println("�׹��� ����(total_price())");
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
	
	
	// �װ��� ��ȣ�� �ִ��� Ȯ���ϴ� �޼ҵ�
	   public boolean check_plane_num(String plane_num) {

	      String query = "SELECT PLANE_NUM FROM PLANE WHERE PLANE_NUM = ?";
	      boolean check = false;
	      try {
	         conn = DBConnection.getConnection();
	         pstm = conn.prepareStatement(query);
	         pstm.setString(1, plane_num);
	         int result = pstm.executeUpdate();
	         
	         
	         if(result == 1) {
	            check = true; // ������ false
	         }
	         
	      } catch (SQLException sqle) {
	         System.out.println(sqle);
	         System.out.println("check_plane_num() ������ ����");
	      } catch (Exception e) {
	         System.out.println(e);
	         System.out.println("�� �� ���� ����(check_plane_num())");
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


	
	

