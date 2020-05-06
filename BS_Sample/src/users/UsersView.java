package users;

import java.util.Scanner;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import core.CoreView;
import oracle.net.aso.s;

public class UsersView {
	Scanner sc = new Scanner(System.in);
	UsersControl usersControl = new UsersControl();
	
	String id = "", pw = "", name = "", phone = "", email = "";
	int point = 0, login_choice = 0, mypage_choice = 0, choice = 0;
	boolean id_check = true, login_flag = false;

	String menu_1_1 = "1.���� ���\n2.���� ����\n3.ȸ�� ���� ���� "; // ���������� �޴�

	public String[] registerUsersView() {
		String[] usersPwAndPhone = new String[2];
		// ID�Է�
		System.out.println("���̵� �Է��ϼ���.");
		id = sc.next();
		if (usersControl.checkDupId(id) == true) {
			System.out.println("�ߺ��� ���̵� �Դϴ�.");
			while (id_check) {
				System.out.print("1. ���̵� �ٽ� �Է��ϱ�\n2. ������\n");
				login_choice = sc.nextInt();
				switch (login_choice) {
				case 1:
					System.out.println("���̵� �Է��ϼ���.");
					id = sc.next();
					if (usersControl.checkDupId(id) == false) {
						id_check = false;
						break;
					}
					System.out.println("�ߺ��� ���̵� �Դϴ�.");
					break;
				case 2:
					return null;
				}
			}
		}
		id_check = true;

		System.out.println("��й�ȣ�� �Է��ϼ���.");
		pw = sc.next();
		usersPwAndPhone[0] = pw;
		System.out.println("�̸��� �Է��ϼ���.");
		name = sc.next();
		System.out.println("��ȣ�� �Է��ϼ���.");
		phone = sc.next();
		usersPwAndPhone[1] = phone;
		System.out.println("�̸����� �Է��ϼ���.");
		email = sc.next();

		try {
			if (usersControl.register(id, pw, name, phone, email, point)) {
				System.out.println("ȸ������ ����");
			} else {
				System.out.println("ȸ������ ����");
			}
		} catch (AddressException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		return usersPwAndPhone;
	}

	public boolean loginUsersView() {
		System.out.println("========�α���=======");
		System.out.print("���̵� �Է��ϼ��� : ");
		id = sc.next();
		System.out.print("��й�ȣ�� �Է��ϼ��� : ");
		pw = sc.next();

		try {
			if (usersControl.checkDupId(id)) {
				if (usersControl.login(id, pw)) {
					System.out.println("�α��� ����");
					// �α��� ������ ���������� ���� ����!!
					login_flag = true;
					CoreView.session_id = id;
				} else {
					System.out.println("��й�ȣ ����");
				}
			} else {
				System.out.println("���̵� �����ϴ�.");
			}
		} catch (Exception e) {
			System.out.println("�α��� ����");
		}
		return login_flag;

	}

	public void selectMypageUsersView(String pw, String phone) {
		String new_PW = "", new_Phone = "";
		System.out.println(menu_1_1);
		// ����ڿ��� ���������� �̿� �����������ְ� �����ϰ� �ϴ� ��.
		mypage_choice = sc.nextInt();
		int updatemember_choice = 0;
		int upre_choice = 0;
		// flag = Update_member�� boolean ���̴� �־��ֱ�
		boolean flag = false;
		switch (mypage_choice) {
		case 1:
			System.out.println("SelectAll() ���� ��� Ȯ��");
//       System.out.println(usersControl.SelectAll(session_id,air,depart,arrive,guest));
			// �����κ� �����ٶ�
			break;
		case 2:
			System.out.println("���� ����");
			// String pw,String air,String depart, String arrive,String guest
			System.out.println("1.����� ����\n2.������ ����\n3.�ο��� ����");

			System.out.println("Update_reservation() ���� ����");
			break;
		case 3:
			System.out.println("==========ȸ������ ����==========");
			System.out.println("1.��й�ȣ ����\n2.�ڵ�����ȣ ����");
			updatemember_choice = sc.nextInt();
			if (updatemember_choice == 1) {
				System.out.println("�����Ͻ� ��й�ȣ�� �Է��ϼ��� : ");
				new_PW = sc.next();
				// String pw,String new_pw,String Phone,String new_phone
				// new_phone�ڸ��� �׳� ���� phone ���� �־��ֱ� > ��й�ȣ�� �ٲٴϱ�. �ƹ��͵� �ȳ־��ָ� null���� �ȴ�.
				flag = usersControl.updateMember(pw, new_PW, phone, phone);
				System.out.println("��й�ȣ ���� �Ϸ�");
			} else if (updatemember_choice == 2) {
				System.out.println("�����Ͻ� �ڵ��� ��ȣ�� �Է��ϼ��� : ");
				new_Phone = sc.next();
				flag = usersControl.updateMember(pw, pw, phone, new_Phone);
				System.out.println("�ڵ��� ��ȣ ���� �Ϸ�");
			}
			break;

		}
	}

}
