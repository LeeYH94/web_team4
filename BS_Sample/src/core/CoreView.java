package core;

import java.util.Scanner;
import hotel_reservation.HotelReservationView;
import plane_reservation.PlaneReservationView;
import users.UsersView;

public class CoreView {
	Scanner sc;
	public CoreView() {
		sc = new Scanner(System.in);
	}
	PlaneReservationView planeReservationView = new PlaneReservationView();
	HotelReservationView hotelReservationView = new HotelReservationView();
	UsersView usersView = new UsersView();

	public static String session_id = "";

	int choice = 0;
	String[] ar_reservation_chioce = { "�װ��ǿ���", "ȣ�ڿ���" };
	String title = "ABO �����\n";
	String menu = "1. ȸ������\n2. �α���\n3. ��ȸ�� �����ϱ�\n4. ����";
	String menu_1 = "1. ����������\n2. �����ϱ�\n3. �α׾ƿ�";
	String menu_2 = "1. �����ϱ�\n2. �α׾ƿ�";

	boolean non_member_login_flag = false, login_flag = false;

	public void view() {
		while (true) {
			System.out.println(title + menu);
			choice = sc.nextInt();

			while (!login_flag) {
				switch (choice) {
				case 1:
					usersView.registerUsersView();
					System.out.println(title + menu);
					choice = sc.nextInt();
					break;
				case 2:
					login_flag = usersView.loginUsersView();
					break;
				case 3:
					non_member_login_flag = usersView.nonMember_loginUsersView();
					login_flag = true;
					break;
				case 4:
					return;
				}
			}
			
			//��ȸ�� �޴�
			if (non_member_login_flag) {
					reserveView();
					session_id = null;
					non_member_login_flag = false;
				
			} else {//ȸ���޴�
				while (true) {
					//boolean logout = false;
					System.out.println(menu_1);
					choice = sc.nextInt();

//					1. ����������\n2. �����ϱ�\n3. �α׾ƿ�					
					switch (choice) {
					case 1:
//						usersView.selectMypageUsersView(pw, phone);
						break;
					case 2:
						reserveView();
						break;
					case 3:
						session_id = null;
						login_flag = false;
						break;
					}
					if (!login_flag)
						break;
				}
			}
		}
	}

	public void reserveView() {
		for (int i = 0; i < ar_reservation_chioce.length; i++) {
			System.out.println(i + 1 + ar_reservation_chioce[i]);
		}
		choice = sc.nextInt();
		switch (choice) {
		// �װ��� ����
		case 1:
			planeReservationView.selectPlaneReservationView(non_member_login_flag);
			break;
		// ȣ�ڿ���
		case 2:
			hotelReservationView.selectHotelReservationView(non_member_login_flag);
			break;
		// �ڷΰ���
		case 3:
			return;
		}
	}
}