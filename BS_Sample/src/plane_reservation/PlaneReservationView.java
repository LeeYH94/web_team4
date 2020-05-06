package plane_reservation;

import java.util.Scanner;

import calender.MainPromptCalender;
import core.CoreView;
import planes.PlaneControl;
import users.PhoneM;
import users.UsersControl;

public class PlaneReservationView {

	Scanner sc = new Scanner(System.in);
	PlaneReservationControl planeReservationControl = new PlaneReservationControl();
	MainPromptCalender mainPromptCalender = new MainPromptCalender();
	PlaneControl planeControl = new PlaneControl();
	UsersControl usersControl = new UsersControl();
	PhoneM phoneM = new PhoneM();

	String[] arMenu_1_2 = { "Korea", "Japan", "America", "China" };
	String[] ar_planeclass_choice = { "FIRST_C", "BUSINESS_C", "ECONOMY_C" };
	int departure_choice = 0, arrival_choice = 0, trip_choice = 0, total_price = 0, point = 0, choice = 0, seat_count = 0;
	String plane_num1 = "", plane_num2 = "", final_choice = "", id = "", pw = "", new_phone = "", new_pw = "", grade = "", room_count = "";

	String menu_1 = "1. 마이페이지\n2. 예매하기\n3. 로그아웃";

	public void selectPlaneReservationView(boolean login_flag) {

		planeReservationControl.startPlaneReservation(CoreView.session_id);

		// 출발지
		System.out.println("[출발지]");
		for (int i = 0; i < arMenu_1_2.length; i++) {
			System.out.println((i + 1) + arMenu_1_2[i]);
		}
		// 인덱스로 접근하기 위해 -1
		departure_choice = sc.nextInt() - 1;

		// 도착지
		System.out.println("[도착지]");
		for (int i = 0; i < arMenu_1_2.length; i++) {
			System.out.println((i + 1) + arMenu_1_2[i]);
		}
		// 인덱스로 접근하기 위해 -1
		arrival_choice = sc.nextInt() - 1;

		System.out.println("1. 편도\n2. 왕복\n");
		trip_choice = sc.nextInt();
		// UP

		switch (trip_choice) {
		// 편도 영역
		case 1:
//	★달력 출력메소드;
			// 출발지, 도착지, 선택날짜, 선택시간 넘겨줌
			// airplaneList()에서 해당 조건에 맞는 planeList 출력
			String userDate = mainPromptCalender.runPROMPT();
			System.out.print("시간을 입력하세요 : ");
			int userTime = sc.nextInt();
			planeControl.getAirplaneList(arMenu_1_2[departure_choice], arMenu_1_2[arrival_choice], userDate, userTime);
			// 리스트에서 비행기번호 입력
			// 여기부터 while 문으로 감싸야 할듯? => 중복검사 때문에
			System.out.println("비행기 번호 입력 : ");
			plane_num1 = sc.next().toUpperCase();

			if (planeReservationControl.checkPlaneNum(plane_num1)) {
				// 비행기 클래스 선택
				System.out.println("[비행기 클래스 선택]");
				for (int i = 0; i < ar_planeclass_choice.length; i++) {
					System.out.println((i + 1) + ar_planeclass_choice[i]);
				}
				grade = ar_planeclass_choice[sc.nextInt() - 1];

				// 등급별 가격 출력
				System.out.println("ADULT\tCHILD\tBABY");
				planeControl.showGradePrice(plane_num1, grade);

				// 성인, 소아, 유아 인원수 입력
				seat_count = planeReservationControl.setSeatCount();
				total_price = planeReservationControl.getPlaneTotalPrice(plane_num1, null, grade);
				point = (int) (total_price * 0.1);
				// System.out.println("[예매내역] :" + selectAll());
				System.out.println("[결제금액]	:" + total_price + "원");
				System.out.println("[포인트 적립]	:" + point + "원");
			} else {
				System.out.println("잘못된 비행기 번호");
			}

			System.out.println("결제 하시겠습니까?(네: Y         아니요: N)");
			final_choice = sc.next().toUpperCase();

			if (final_choice.equals("Y")) {
				planeReservationControl.updatePlaneQuery(plane_num1, null, grade, seat_count, total_price);
				usersControl.pointUpdate(point, CoreView.session_id);
				phoneM.phoneM(CoreView.session_id, phoneM.selectPhone(CoreView.session_id));
				System.out.println("예매 완료");
				PlaneReservationControl.session_plane_reservation = "";
			} else if (final_choice.equals("N")) {
				return;
			} else {
				System.out.println("잘못입력하셨습니다.");
			}
			break;

		// 왕복 영역
		case 2:
			// 출발
			System.out.println("[출발일]");
			userDate = mainPromptCalender.runPROMPT();
			System.out.print("시간을 입력하세요 : ");
			userTime = sc.nextInt();
			planeControl.getAirplaneList(arMenu_1_2[departure_choice], arMenu_1_2[arrival_choice], userDate, userTime);
			// 리스트에서 비행기번호 입력
			System.out.println("비행기 번호 입력 : ");
			plane_num1 = sc.next().toUpperCase();

			// 도착
			System.out.println("[도착일]");
			userDate = mainPromptCalender.runPROMPT();
			System.out.print("시간을 입력하세요 : ");
			userTime = sc.nextInt();
			planeControl.getAirplaneList(arMenu_1_2[arrival_choice], arMenu_1_2[departure_choice], userDate, userTime);
			// 리스트에서 비행기번호 입력
			System.out.println("비행기 번호 입력 : ");
			plane_num2 = sc.next().toUpperCase();

			// 비행기 번호 확인
			if (planeReservationControl.checkPlaneNum(plane_num1)
					&& planeReservationControl.checkPlaneNum(plane_num2)) {
				// 비행기 클래스 선택
				System.out.println("[비행기 클래스 선택]");
				for (int i = 0; i < ar_planeclass_choice.length; i++) {
					System.out.println((i + 1) + ar_planeclass_choice[i]);
				}
				grade = ar_planeclass_choice[sc.nextInt() - 1];

				// 등급별 가격 출력
				System.out.println("ADULT\tCHILD\tBABY");
				planeControl.showGradePrice(plane_num1, grade);
				planeControl.showGradePrice(plane_num2, grade);

				// 성인, 소아, 유아 인원수 입력
				seat_count = planeReservationControl.setSeatCount();
				total_price = planeReservationControl.getPlaneTotalPrice(plane_num1, plane_num2, grade);
				point = (int) (total_price * 0.1);
				// System.out.println("[예매내역] :" + selectAll());
				System.out.println("[결제금액]	:" + total_price + "원");
				System.out.println("[포인트 적립]	:" + point + "원");
			} else {
				System.out.println("잘못된 입력입니다.");
			}

			System.out.println("결제 하시겠습니까?(네: Y         아니요: N)");
			final_choice = sc.next().toUpperCase();

			if (final_choice.equals("Y")) {
				planeReservationControl.updatePlaneQuery(plane_num1, null, grade, seat_count, total_price);
				usersControl.pointUpdate(point, CoreView.session_id);
				phoneM.phoneM(CoreView.session_id, phoneM.selectPhone(CoreView.session_id));
				PlaneReservationControl.session_plane_reservation = "";
				System.out.println("예매 완료");
			} else if (final_choice.equals("N")) {
				return;
			} else {
				System.out.println("잘못입력하셨습니다.");
			}
			break;
		}
	}

}
