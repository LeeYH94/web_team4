package users;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Random;
import java.util.Scanner;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import core.DBConnection;

public class UsersControl {
	Connection conn;
	PreparedStatement pstm;
	ResultSet rs;

	private final int KEY = 7;
	
	public static String session_id;

	// ���� �߼� �޼ҵ�
	public void sendMail(String email, int code) throws AddressException, MessagingException {
		String host = "smtp.naver.com";

		// --- �ȿ� ������ ��� �̸��� �ּ�(@naver.com)����, ��й�ȣ
		final String id = "smy1550";
		final String pw = "xy5544xy";
		int port = 465;

		String recipient = email;
		String subject = "���� �߼� Ȯ��";
		int body = code;

		Properties props = System.getProperties();

		props.put("mail.smtp.host", host);
		props.put("mail.smtp.port", port);
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.ssl.enable", "true");
		props.put("mail.smtp.ssl.trust", host);

		Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
			String userName = id;
			String passWord = pw;

			protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
				return new javax.mail.PasswordAuthentication(userName, passWord);
			}
		});
		session.setDebug(true);

		Message mimeMessage = new MimeMessage(session);
		// --- �ȿ� ������ ��� �̸��� �ּ� �ֱ� (@naver.com) ����
		mimeMessage.setFrom(new InternetAddress("smy1550@naver.com"));
		mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
		mimeMessage.setSubject(subject);
		mimeMessage.setText(Integer.toString(body));
		Transport.send(mimeMessage);
	}

	// ��ȣȭ
	public String encrypt(String pw) {
		String en_pw = "";
		for (int i = 0; i < pw.length(); i++) {
			en_pw += (char) (pw.charAt(i) * KEY);
		}
		return en_pw;
	}

	// ȸ������ (���̵�, ��й�ȣ, �̸�, ��ȭ��ȣ, �̸���)
	public boolean register(String id, String pw, String name, String phone, String email, int point)
			throws AddressException, MessagingException {
		boolean result = false;
		String query = "";
		Random rand = new Random();
		Scanner sc = new Scanner(System.in);
		int code = (int) (rand.nextFloat() * 10000);
		sendMail(email, code);
		System.out.print("�ڵ带 �Է��ϼ��� : ");
		if (sc.nextInt() == code) {
			query = "INSERT INTO GUEST" + "(ID, PW, NAME, PHONE_NUMBER, EMAIL, POINT) " + "VALUES(?, ?, ?, ?, ?, ?)";
			try {
				int idx = 0;
				conn = DBConnection.getConnection();
				pstm = conn.prepareStatement(query);
				pstm.setString(++idx, id);
				pstm.setString(++idx, encrypt(pw));
				pstm.setString(++idx, name);
				pstm.setString(++idx, phone);
				pstm.setString(++idx, email);
				pstm.setInt(++idx, point);
				pstm.executeQuery();
				pstm.close();
				result = true;
			} catch (SQLException sqle) {
				System.out.println(sqle);
				System.out.println("register() ������ ����");
			} catch (Exception e) {
				System.out.println(e);
				System.out.println("�� �� ���� ����(register())");
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
		return result;
	}

	// �α��� �޼ҵ�()
	public boolean login(String id, String pw) {
		boolean check = false;
		String query = "SELECT COUNT(*) FROM GUEST" + " WHERE ID = ? AND PW = ?";
		try {
			conn = DBConnection.getConnection();
			pstm = conn.prepareStatement(query);
			pstm.setString(1, id);
			pstm.setString(2, encrypt(pw));
			rs = pstm.executeQuery();
			if (rs.next()) {
				if (rs.getInt(1) == 1) {
					check = true;
					
				}
				session_id = id;
			}
		} catch (SQLException sqle) {
			System.out.println(sqle);
			System.out.println("login() ������ ����");
		} catch (Exception e) {
			System.out.println(e);
			System.out.println("�׹��� ����(login())");
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

		return check;

	}
	
	//�α׾ƿ� �޼ҵ�
	public void logout() {
		session_id = null;
	}
	
	//ȸ���������� �޼ҵ�
	   public boolean Update_member(String pw,String new_pw,String phone,String new_phone) {
	      //DB���� ȸ�������� �������� ���� Update ����Ѵ�.
	      //�����ϰ� �ٽ� �� ���� �־���Ѵ�. 
	      //���� ���� = ��й�ȣ, �ڵ�����ȣ �ٲٱ�. >���̹� ȸ������ ���� ����
	      // �ΰ� �� �ٲ� ��� ������? �޼ҵ� �ΰ� ������ �ϴ���.. query�� �Ѱ��� �� �� �ֳ�? 
	      String query = "UPDATE GUEST SET PW= ? , PHONE_NUMBER= ? WHERE ID= ? AND PW =?";
	      boolean check = false;
	      try {
	         conn=DBConnection.getConnection();
	         pstm=conn.prepareStatement(query);
	         //���ο� �н����� new_pw�� ��ȣȭ �ؼ� �־��ֱ� 
	         pstm.setString(1, encrypt(new_pw));
	         pstm.setString(2, new_phone);
	         //session_id�� id�� �α����� �� �־��ֱ� 
	         pstm.setString(3, session_id);
	         pstm.setString(4, encrypt(pw));
	         pstm.executeQuery();
	         check= pstm.execute();
	         
	      }catch(SQLException sqle) {
	         System.out.println(sqle);
	         System.out.println("update() ������ ����");
	      }
	      catch(Exception e) {
	         System.out.println(e);
	         System.out.println("�� �� ���� ����(update() method)");
	      }
	      finally {
	         try {
	         if(pstm != null) {
	            pstm.close();
	         }
	         if(conn!=null) {
	            conn.close();
	         }
	         }
	         catch(SQLException e){
	            throw new RuntimeException(e.getMessage());
	         }
	      }
	      return check;
	   }

	// ���̵� �ߺ�Ȯ�� �޼ҵ�
	public boolean check_dup_id(String id) {
		String query = "SELECT COUNT(*) FROM GUEST WHERE ID = ?";
		boolean check = true;
		try {
			conn = DBConnection.getConnection();
			pstm = conn.prepareStatement(query);
			pstm.setString(1, id);
			rs = pstm.executeQuery();
			rs.next();
			if (rs.getInt(1) == 0) {
				check = false;
			}
			pstm.close();
		} catch (SQLException sqle) {
			System.out.println(sqle);
			System.out.println("check_dup_id() ������ ����");
		} catch (Exception e) {
			System.out.println(e);
			System.out.println("�� �� ���� ����(check_dup_id() �޼ҵ�)");
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
		return check;
	}
}
