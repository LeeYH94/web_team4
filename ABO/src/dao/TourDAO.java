package dao;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

import vo.TourDTO;

public class TourDAO {
	Connection conn;
	PreparedStatement pstm;
	ResultSet rs;
	TourDTO dto = null;

	private final int KEY = 7;

	// �޷���� �޼ҵ�

	// �װ��� ��� �޼ҵ�
	public void airplaneList(String userDate) {
		String query = "";
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd HH");
		// time1 == user�� ������ �ð�
		// time2 == ����ð�
		Date time = new Date();
		String time1 = format1.format(userDate);
		String time2 = format1.format(time);

		int compare = time1.compareTo(time2);
		if (compare > 0) {
			// ������ ���� ���� ������ ������ ��
			query = "SELECT * FROM AIRPLANE WHERE DATE = \'" + userDate + "\'";
			showAirplaneList(query);
		} else if (compare < 0) {
			// ������ ���� ���� ������ �����϶�
			System.out.println(time2 + " ������ ���� �Է��߽��ϴ�.");
		} else {
			// ������ ���� ���� ���� ���� ��
			query = "";
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
			System.out.println();
			while (rs.next()) {
				System.out.println(rs.getString(1) + "\t" + rs.getString(2) + "\t" + rs.getString(3) + "\t"
						+ rs.getString(4) + "\t" + rs.getString(5) + "\t" + rs.getInt(6) + "\t" + rs.getString(7) + "\t"
						+ rs.getInt(8));
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
			showAirplaneList(query);
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
			System.out.println();
			while (rs.next()) {
				System.out.println(rs.getInt(1) + "\t" + rs.getInt(2) + "\t" + rs.getInt(3) + "\t" + rs.getString(4));
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

	// ���� �޼ҵ�(ȸ��, ��ȸ��)
	// ����, �� �պ�, ȣ��
	public void pay(ArrayList<Object> user_choice, TourDTO user, int idx) {
		if (user == null) {
			// �α��� �޼ҵ�

		}

		if (user_choice.get(1) == null) {

		}

	}

	// �α��� �޼ҵ�()
	public boolean login(String id, String pw) {
		boolean check = false;
		String query = "SELECT COUNT(*) FROM REGISTER" + " WHERE ID = ? AND PW = ?";
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
			}
		} catch (SQLException sqle) {
			System.out.println(sqle);
			System.out.println("login() ������ ����");
		} catch (Exception e) {
			System.out.println(e);
			System.out.println("�׹��� ����(login())");
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

	// ���̵� �ߺ�Ȯ�� �޼ҵ�
	public boolean check_dup_id(String id) {
		String query = "SELECT COUNT(*) FROM REGISTER WHERE ID = ?";
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

	// ȸ������ TourDTO(���̵�, ��й�ȣ, �̸�, ��ȭ��ȣ, �̸���)
	public boolean register(String id, String pw, String name, String phone, String email) throws AddressException, MessagingException {
		boolean result = false;
		String query = "";
		Random rand = new Random();
		Scanner sc = new Scanner(System.in);
		int code = (int) (rand.nextFloat() * 10000);
		sendMail(email, code);
		System.out.print("�ڵ带 �Է��ϼ��� : ");
		if (sc.nextInt() == code) {
			query = "INSERT INTO REGISTER" + "(id, pw, name, phone, email) " + "VALUES(?, ?, ?, ?, ?)";
			try {
				int idx = 0;
				conn = DBConnection.getConnection();
				pstm = conn.prepareStatement(query);
				pstm.setString(++idx, id);
				pstm.setString(++idx, encrypt(pw));
				pstm.setString(++idx, name);
				pstm.setString(++idx, phone);
				pstm.setString(++idx, email);
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

	// ���� �߼� �޼ҵ�
	public void sendMail(String email, int code) throws AddressException, MessagingException {
		String host = "smtp.naver.com";

		// --- �ȿ� ������ ��� �̸��� �ּ�(@naver.com)����, ��й�ȣ
		final String id = "yhya0904";
		final String pw = "leeheader7679!";
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
		mimeMessage.setFrom(new InternetAddress("yhya0904@naver.com"));
		mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
		mimeMessage.setSubject(subject);
		mimeMessage.setText(Integer.toString(body));
		Transport.send(mimeMessage);
	}

	public String encrypt(String pw) {
		String en_pw = "";
		for (int i = 0; i < pw.length(); i++) {
			en_pw += (char) (pw.charAt(i) * KEY);
		}
		return en_pw;
	}

	public void view() {

		Scanner sc = new Scanner(System.in);
		String title = "ABO �����\n";
		String menu = "1. ȸ������\n2. �α���\n3. ��ȸ�� �����ϱ�\n4. ����";
		String menu_1 = "1. ����������\n2. �����ϱ�\n3. �α׾ƿ�";
		String menu_1_1 = ""; // ���������� �޴�
		String menu_1_2 = "1. �Ϻ�\n2. �̱�\n3. �߱�";
		String[] arMenu_1_2 = { "�Ϻ�", "�̱�", "�߱�" };
		String menu_1_2_1 = "1. ��\n2. �պ�";
		String[] arMenu_1_2_1 = { "��", "�պ�" };
		String menu_1_2_2 = "1. Aȣ��\n2. Bȣ��";
		String[] arMenu_1_2_2 = { "Aȣ��" };
		int choice = 0, country_choice = 0, trip_choice = 0, hotel_choice = 0, round_choice = 0, login_choice = 0;
		ArrayList<String> user_choice = new ArrayList<String>();
		boolean id_check = true;
		// login ���� Ȯ��
		boolean login_flag = false;
		
		String id = "";
		String pw = "";
		String name = "";
		String phone = "";
		String email = "";

		while (true) {
			System.out.println(title + menu);
			choice = sc.nextInt();

			// ����
			if (choice == 4) {
				break;
			}

			switch (choice) {
			// ȸ������ ����
			case 1:
				// ID�Է�
				System.out.println("���̵� �Է��ϼ���.");
				id = sc.next();
				if (check_dup_id(id) == true) {
					System.out.println("�ߺ��� ���̵� �Դϴ�.");
					while (id_check) {
						System.out.print("1. ���̵� �ٽ� �Է��ϱ�\n2. ������\n");
						login_choice = sc.nextInt();
						switch (login_choice) {
						case 1:
							System.out.println("���̵� �Է��ϼ���.");
							id = sc.next();
							if (check_dup_id(id) == false) {
								id_check = false;
								break;
							}
							System.out.println("�ߺ��� ���̵� �Դϴ�.");
							break;
						case 2:
							view();
						}
					}
				}
				id_check = true;

				System.out.println("��й�ȣ�� �Է��ϼ���.");
				pw = sc.next();
				System.out.println("�̸��� �Է��ϼ���.");
				name = sc.next();
				System.out.println("��ȣ�� �Է��ϼ���.");
				phone = sc.next();
				System.out.println("�̸����� �Է��ϼ���.");
				email = sc.next();

				try {
					if (register(id, pw, name, phone, email)) {
						System.out.println("ȸ������ ����");
					} else {
						System.out.println("ȸ������ ����");
					}
				} catch (AddressException e) {
					e.printStackTrace();
				} catch (MessagingException e) {
					e.printStackTrace();
				}

				break;

			// �α��� ����
			case 2:
				System.out.print("���̵� �Է��ϼ��� : ");
				id = sc.next();
				System.out.print("��й�ȣ�� �Է��ϼ��� : ");
				pw = sc.next();

				try {
					if (check_dup_id(id)) {
						if (login(id, pw)) {
							System.out.println("�α��� ����");
							login_flag = true;
						} else {
							System.out.println("��й�ȣ ����");
						}
					} else {
						System.out.println("���̵� �����ϴ�.");
					}
				} catch (Exception e) {
					System.out.println("�α��� ����");
				}

				// "1. ����������\n2. �����ϱ�\n3. �α׾ƿ�"
				System.out.println(menu_1);
				choice = sc.nextInt();

				switch (choice) {
				// ���������� ����
				case 1:
					System.out.println("����");
					break;
				// �����ϱ� ����
				case 2:
//					"1. �Ϻ�\n2. �̱�\n3. �߱�"
					System.out.println(menu_1_2);
					country_choice = sc.nextInt();
					user_choice.add(arMenu_1_2[country_choice - 1]);
					System.out.println("1.�װ��� ����\n2.ȣ�� ����");
					trip_choice = sc.nextInt();

					switch (trip_choice) {
					// �װ��� ���� ����
					case 1:
//						"1. ��\n2. �պ�\n"
						System.out.println(menu_1_2_1);
						trip_choice = sc.nextInt(); // ����
						user_choice.add(arMenu_1_2_1[trip_choice - 1]);

						switch (trip_choice) {
						// �� ����
						case 1:
//							�޷� ���(�޼ҵ�)
							break;
						// �պ� ����
						case 2:
//							�޷� ���(�޼ҵ�)
							break;
						// �ڷΰ���(����)
						case 3:
							break;
						}

						break;
					// ȣ�ڿ��� ����
					case 2:
						System.out.println(menu_1_2_2);
						hotel_choice = sc.nextInt();
						user_choice.add(arMenu_1_2_2[hotel_choice - 1]);
						switch (hotel_choice) {
						// A ȣ��
						case 1:
//						�޷� ���(�޼ҵ�)
							break;
						// Bȣ��
						case 2:
//						�޷� ���(�޼ҵ�)
							break;
						// �ڷΰ���
						case 3:
							break;
						}
						break;
					// �ڷΰ��� ����(����)
					case 3:
						break;
					}
					break;
				// �α׾ƿ� ����
				case 3:
					break;
				}
				break;
			// ��ȸ�� �����ϱ� ����
			case 3:
//				"1. �Ϻ�\n2. �̱�\n3. �߱�"
				System.out.println(menu_1_2);
				country_choice = sc.nextInt();

				System.out.println("1.�װ��� ����\n2.ȣ�� ����");
				trip_choice = sc.nextInt();
				switch (trip_choice) {
				// �װ��� ���� ����
				case 1:
//					"1. ��\n2. �պ�\n"
					System.out.println(menu_1_2_1);
					round_choice = sc.nextInt(); // ����

					switch (round_choice) {
					// �� ����
					case 1:
//						�޷� ���(�޼ҵ�)
						break;
					// �պ� ����
					case 2:
//						�޷� ���(�޼ҵ�)
						break;
					// �ڷΰ���(����)
					case 3:
						break;
					}

					break;
				// ȣ�ڿ��� ����
				case 2:
					System.out.println(menu_1_2_2);
					hotel_choice = sc.nextInt();
					switch (hotel_choice) {
					// A ȣ��
					case 1:
//					�޷� ���(�޼ҵ�)
						break;
					// Bȣ��
					case 2:
//					�޷� ���(�޼ҵ�)
						break;
					// �ڷΰ���
					case 3:
						break;
					}
					break;
				default:

				}
			}
		}

	}

}