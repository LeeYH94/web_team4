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
			System.out.println("����̹� �ε� ����");
		} catch (SQLException sqle) {
			System.out.println("DB ���� ����");
		} catch (Exception e) {
			System.out.println("�˼� ���� ����");
		}
		return conn;
	}

}
