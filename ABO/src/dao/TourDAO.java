package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.ArrayList;
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
	// �޷���� �޼ҵ�

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

	// �ߺ�Ȯ�� �޼ҵ�
	public boolean check_dup_id(String id) {
		String str = "";
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			// localaddress �ڸ��� ip �ֱ�
			Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521", "hr", "hr");
			Statement state = conn.createStatement();
			state.execute("SELECT COUNT(*) FROM REGISTER WHERE ID IN ('" + id + "')");
			ResultSet resultSet = state.executeQuery("SELECT COUNT(*) FROM REGISTER WHERE ID IN ('" + id + "')");
			if (resultSet.next()) {
				str = resultSet.getString(1);
			}
			if (str.equals("0")) {
				return false;
			}
			state.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return true;
	}

	// ȸ������ TourDTO(���̵�, ��й�ȣ, �̸�, ��ȭ��ȣ, �̸���)
	public int register(TourDTO dto) throws AddressException, MessagingException {
		int result = 0;
		Random rand = new Random();
		Scanner sc = new Scanner(System.in);
		int code = (int) (rand.nextFloat() * 10000);
		sendMail(dto, code);
		System.out.print("�ڵ带 �Է��ϼ��� : ");
		if (sc.nextInt() == code) {
			try {
				Class.forName("oracle.jdbc.driver.OracleDriver");
				// localaddress �ڸ��� ip �ֱ�
				Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521", "hr", "hr");
				Statement state = conn.createStatement();
				result = state.executeUpdate("INSERT INTO REGISTER" 
						+ "(id, pw, name, phone, email) VALUES" + " ('"
						+ dto.getId() + "', '" + dto.getPw() + "', '" 
						+ dto.getName() + "', '" + dto.getPhone() + "', '"
						+ dto.getEmail() + "')");
				state.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	// ���� �߼� �޼ҵ�
	public void sendMail(TourDTO dto, int code) throws AddressException, MessagingException {
		String host = "smtp.naver.com";

		// ������ ��� �̸��� �ּ�(@naver.com)����, ��й�ȣ
		final String id = "yhya0904";
		final String pw = "leeheader7679!";
		int port = 465;

		String recipient = dto.getEmail();
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
		// internetAddress �ȿ� ������ ��� �̸��� �ּ� �ֱ� (@naver.com) ����
		mimeMessage.setFrom(new InternetAddress("yhya0904@naver.com"));
		mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));

		mimeMessage.setSubject(subject);
		mimeMessage.setText(Integer.toString(body));
		Transport.send(mimeMessage);
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
				TourDTO dto = new TourDTO();

				//ID�Է�
				System.out.println("���̵� �Է��ϼ���.");
				dto.setId(sc.next());
				if(check_dup_id(dto.getId()) == true) {
					System.out.println("�ߺ��� ���̵� �Դϴ�.");
					while (id_check) {
						System.out.print("1. ���\n2. ������\n");
						login_choice = sc.nextInt();
						switch (login_choice) {
							case 1:
								System.out.println("���̵� �Է��ϼ���.");
								dto.setId(sc.next());
								if(check_dup_id(dto.getId()) == false) {
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
				dto.setPw(sc.next());
				System.out.println("�̸��� �Է��ϼ���.");
				dto.setName(sc.next());
				System.out.println("��ȣ�� �Է��ϼ���.");
				dto.setPhone(sc.next());
				System.out.println("�̸����� �Է��ϼ���.");
				dto.setEmail(sc.next());

				try {
					if (new TourDAO().register(dto) == 1) {
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