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

	// 메일 발송 메소드
	public void sendMail(String email, int code) throws AddressException, MessagingException {
		String host = "smtp.naver.com";

		// --- 안에 보내는 사람 이메일 주소(@naver.com)제외, 비밀번호
		final String id = "---";
		final String pw = "---";
		int port = 465;

		String recipient = email;
		String subject = "메일 발송 확인";
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
		// --- 안에 보내는 사람 이메일 주소 넣기 (@naver.com) 포함
		mimeMessage.setFrom(new InternetAddress("---@naver.com"));
		mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
		mimeMessage.setSubject(subject);
		mimeMessage.setText(Integer.toString(body));
		Transport.send(mimeMessage);
	}

	// 암호화
	public String encrypt(String pw) {
		String en_pw = "";
		for (int i = 0; i < pw.length(); i++) {
			en_pw += (char) (pw.charAt(i) * KEY);
		}
		return en_pw;
	}

	// 회원가입 (아이디, 비밀번호, 이름, 전화번호, 이메일)
	public boolean register(String id, String pw, String name, String phone, String email, int point)
			throws AddressException, MessagingException {
		boolean result = false;
		String query = "";
		Random rand = new Random();
		Scanner sc = new Scanner(System.in);
		int code = (int) (rand.nextFloat() * 10000);
		sendMail(email, code);
		System.out.print("코드를 입력하세요 : ");
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
				System.out.println("register() 쿼리문 오류");
			} catch (Exception e) {
				System.out.println(e);
				System.out.println("알 수 없는 오류(register())");
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

	// 로그인 메소드()
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
			System.out.println("login() 쿼리문 오류");
		} catch (Exception e) {
			System.out.println(e);
			System.out.println("그밖의 문제(login())");
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
	
	//로그아웃 메소드
	public void logout() {
		session_id = null;
	}
	
	//회원정보수정 메소드
	   public boolean updateMember(String pw,String new_pw,String phone,String new_phone) {
	      //DB에서 회원정보를 가져오기 위해 Update 사용한다.
	      //수정하고 다시 그 값을 넣어야한다. 
	      //수정 내용 = 비밀번호, 핸드폰번호 바꾸기. >네이버 회원정보 수정 참고
	      // 두개 다 바꿀 방법 있을까? 메소드 두개 만들어야 하는지.. query문 한개만 쓸 수 있나? 
	      String query = "UPDATE GUEST SET PW= ? , PHONE_NUMBER= ? WHERE ID= ? AND PW =?";
	      boolean check = false;
	      try {
	         conn=DBConnection.getConnection();
	         pstm=conn.prepareStatement(query);
	         //새로운 패스워드 new_pw도 암호화 해서 넣어주기 
	         pstm.setString(1, encrypt(new_pw));
	         pstm.setString(2, new_phone);
	         //session_id는 id가 로그인일 때 넣어주기 
	         pstm.setString(3, session_id);
	         pstm.setString(4, encrypt(pw));
	         pstm.executeQuery();
	         check= pstm.execute();
	         
	      }catch(SQLException sqle) {
	         System.out.println(sqle);
	         System.out.println("update() 쿼리문 오류");
	      }
	      catch(Exception e) {
	         System.out.println(e);
	         System.out.println("알 수 없는 오류(update() method)");
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

	// 아이디 중복확인 메소드
	public boolean checkDupId(String id) {
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
			System.out.println("check_dup_id() 쿼리문 오류");
		} catch (Exception e) {
			System.out.println(e);
			System.out.println("알 수 없는 오류(check_dup_id() 메소드)");
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
	//포인트 적립 메소드
	public void pointUpdate(int point, String id) {
		String query = "";
		try {
			query = "UPDATE GUEST SET POINT = ?" + " WHERE ID = ?";
			conn = DBConnection.getConnection();
			pstm = conn.prepareStatement(query);
			pstm.setInt(1, point);
			pstm.setString(2, id);
			rs = pstm.executeQuery();
		} catch (SQLException sqle) {
			System.out.println(sqle);
			System.out.println("pointUpdate() 쿼리문 오류");
		} catch (Exception e) {
			System.out.println(e);
			System.out.println("그밖의 문제(pointUpdate())");
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
