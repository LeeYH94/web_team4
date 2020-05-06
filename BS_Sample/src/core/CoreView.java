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
	String[] ar_reservation_chioce = { "항공권예약", "호텔예약" };
	String title = "ABO 여행사\n";
	String menu = "1. 회원가입\n2. 로그인\n3. 비회원 예매하기\n4. 종료";
	String menu_1 = "1. 마이페이지\n2. 예매하기\n3. 로그아웃";
	String menu_2 = "1. 예매하기\n2. 로그아웃";

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
			
			//비회원 메뉴
			if (non_member_login_flag) {
					reserveView();
					session_id = null;
					non_member_login_flag = false;
				
			} else {//회원메뉴
				while (true) {
					//boolean logout = false;
					System.out.println(menu_1);
					choice = sc.nextInt();

//					1. 마이페이지\n2. 예매하기\n3. 로그아웃					
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
		// 항공기 예약
		case 1:
			planeReservationView.selectPlaneReservationView(non_member_login_flag);
			break;
		// 호텔예약
		case 2:
			hotelReservationView.selectHotelReservationView(non_member_login_flag);
			break;
		// 뒤로가기
		case 3:
			return;
		}
	}
}