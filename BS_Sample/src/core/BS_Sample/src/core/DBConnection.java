package core;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

	//jdbc : Java Database Connectivity
	public static Connection getConnection() {
		Connection conn = null;
		//url, id, pw
		try {
			String url = "jdbc:oracle:thin:@localhost:1521:XE";
			String user = "hr";
			String pw = "hr";
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection(url, user, pw);
		} catch (ClassNotFoundException cnfe) {
			System.out.println(cnfe);
			System.out.println("드라이버 로딩 실패");
		} catch (SQLException sqle) {
			System.out.println("DB 접속 실패");
		} catch (Exception e) {
			System.out.println("알수 없는 오류");
		}
		return conn;
	}

}
