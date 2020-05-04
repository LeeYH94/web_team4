package planes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import core.DBConnection;

public class PlaneDBInsert {
	
   public static void main(String[] args) {
      PlaneDBInsert dbi = new PlaneDBInsert();
      dbi.planeDBInsert();
//      Random r = new Random();
   }

   Connection conn;
   PreparedStatement pstm;
   ResultSet rs;
//   int[] arPrice = new int[10];

   String temp_1 = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
   String temp_2 = "0123456789";
   ArrayList<String> ar_temp_id = new ArrayList<>();
   int[] max_day = { 0, 31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };

   public void planeDBInsert() {
      Random r = new Random();
      ArrayList<String> price = new ArrayList<>();
      try {
         conn = DBConnection.getConnection();
         for (int i = 0; i < 100; i++) {
            String temp_country = "";
            String[] arCompany = { "Korean_air", "Asinana", "Tokyo" };
            String[] arCountry = { "Korea", "Japan", "America", "China" };
           
            
            String query = "INSERT INTO PLANE"
                  + "(PLANE_NUM, COMPANY, DEPARTURE, ARRIVAL, DEPARTURE_DATE, DEPARTURE_TIME, SEAT_TOTAL)"
                  + " VALUES(?, ?, ?, ?, ?, ?, ?)";
            String temp_id = "";
            temp_id += temp_1.charAt(r.nextInt(temp_1.length()));
            for (int j = 0; j < 3; j++) {
               temp_id += temp_2.charAt(r.nextInt(temp_2.length()));
            }
            if (ar_temp_id.contains(temp_id)) {
               continue;
            }
            ar_temp_id.add(temp_id);               
            pstm = conn.prepareStatement(query);
            pstm.setString(1, temp_id);
            price.add(temp_id);
            pstm.setString(2, arCompany[r.nextInt(arCompany.length)]);
            temp_country = arCountry[r.nextInt(arCountry.length)];
            pstm.setString(3, temp_country);
            List<String> temp_arCountry = new ArrayList<String>(Arrays.asList(arCountry));
            temp_arCountry.remove(temp_country);
            pstm.setString(4, temp_arCountry.get(r.nextInt(arCountry.length - 1)));
            int month = r.nextInt(12) + 1;
            int date = r.nextInt(max_day[month] + 1);
            pstm.setString(5, 2020 + "-" + month + "-" + date);
            pstm.setString(6, Integer.toString(r.nextInt(24)));
            pstm.setString(7, r.nextInt(30) + "," + r.nextInt(30) + "," + r.nextInt(30));
            rs = pstm.executeQuery();
            
         }
      } catch (SQLException sqle) {
         System.out.println(sqle);
         System.out.println("planeDBInsert() 쿼리문 오류");
      } catch (Exception e) {
         System.out.println(e);
         System.out.println("알 수 없는 오류(planeDBInsert())");
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
      System.out.println("plane insert 완료");
      

	  try {
		conn = DBConnection.getConnection();
		String query2 = "INSERT INTO ECONOMY_C(PLANE_NUM, ADULT, CHILD, BABY)VALUES(?, ?, ?, ?)";
			for (int i = 0; i < 100; i++) {
			  pstm = conn.prepareStatement(query2);
			  pstm.setString(1, price.get(i));
			  pstm.setInt(2, (r.nextInt(10) + 20) * 10000);
			  pstm.setInt(3, (r.nextInt(10) + 10) * 10000);
			  pstm.setInt(4, (r.nextInt(10) + 5) * 10000);
			  rs = pstm.executeQuery();
			}
    	  } catch (SQLException sqle) {
	         System.out.println(sqle);
	         System.out.println("planeDBInsert2() 쿼리문 오류");
	      } catch (Exception e) {
	         System.out.println(e);
	         System.out.println("알 수 없는 오류(planeDBInsert2())");
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
      
	  	try {
		conn = DBConnection.getConnection();
		String query3 = "INSERT INTO BUSINESS_C(PLANE_NUM, ADULT, CHILD, BABY)VALUES(?, ?, ?, ?)";
				for (int i = 0; i < 100; i++) {
				  pstm = conn.prepareStatement(query3);
				  pstm.setString(1, price.get(i));
				  pstm.setInt(2, (r.nextInt(10) + 40) * 10000);
				  pstm.setInt(3, (r.nextInt(10) + 30) * 10000);
				  pstm.setInt(4, (r.nextInt(10) + 15) * 10000);
				  rs = pstm.executeQuery();
				}
			} catch (SQLException sqle) {
	        System.out.println(sqle);
	        System.out.println("planeDBInsert3() 쿼리문 오류");
			} catch (Exception e) {
	        System.out.println(e);
	        System.out.println("알 수 없는 오류(planeDBInsert3())");
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

	  	try {
    	conn = DBConnection.getConnection();
		String query4 = "INSERT INTO FIRST_C(PLANE_NUM, ADULT, CHILD, BABY)VALUES(?, ?, ?, ?)";
		  for (int i = 0; i < 100; i++) {
			  pstm = conn.prepareStatement(query4);
			  pstm.setString(1, price.get(i));
			  pstm.setInt(2, (r.nextInt(10) + 60) * 10000);
			  pstm.setInt(3, (r.nextInt(10) + 50) * 10000);
			  pstm.setInt(4, (r.nextInt(10) + 25) * 10000);
			  rs = pstm.executeQuery();
		  }
		} catch (SQLException sqle) {
	        System.out.println(sqle);
	        System.out.println("planeDBInsert4() 쿼리문 오류");
			} catch (Exception e) {
	        System.out.println(e);
	        System.out.println("알 수 없는 오류(planeDBInsert4())");
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