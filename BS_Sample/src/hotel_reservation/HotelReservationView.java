package hotel_reservation;

import java.util.Scanner;

import calender.MainPromptCalender;
import core.CoreView;
import hotels.HotelControl;
import oracle.net.aso.p;
import users.PhoneM;
import users.UsersControl;

public class HotelReservationView {
	Scanner sc = new Scanner(System.in);
	HotelReservationControl hotelReservationControl = new HotelReservationControl();
	MainPromptCalender mainPromptCalender = new MainPromptCalender();
	HotelControl hotelControl = new HotelControl();
	UsersControl usersControl = new UsersControl();
	PhoneM phoneM = new PhoneM();

	String[] arMenu_1_2 = { "Korea", "Japan", "America", "China" };
	String check_in = "", check_out = "", hotel_num = "", final_choice = "", id = "", pw = "", new_phone = "",
			new_pw = "", room_count = "";

	int city_choice = 0, total_price = 0, point = 0, choice = 0;

	String menu_1 = "1. 마이페이지\n2. 예매하기\n3. 로그아웃";

	public void selectHotelReservationView(boolean login_flag) {
		hotelReservationControl.startHotelReservation(CoreView.session_id);

		// 여행지
		System.out.println("[여행지]");
		for (int i = 0; i < arMenu_1_2.length; i++) {
			System.out.println((i + 1) + arMenu_1_2[i]);
		}
		// 인덱스로 접근하기 위해서 -1
		city_choice = sc.nextInt() - 1;

		// 입실일, 퇴실일 입력
		// 달력 출력메소드
		System.out.println("[입실일]");
		check_in = mainPromptCalender.runPROMPT();
		System.out.println("[퇴실일]");
		check_out = mainPromptCalender.runPROMPT();
		// 여행지에 맞는 호텔리스트 출력(여행지,입실일 )
		hotelControl.getHotelList(arMenu_1_2[city_choice], check_in);

		System.out.println("호텔 번호 입력 : ");
		hotel_num = sc.next().toUpperCase();

		if (hotelReservationControl.checkHotelNum(hotel_num)) {
			// 객실 가격 출력
			hotelControl.showHotelPriceList(hotel_num);

			// 객실 선택
			System.out.println("[1인실/2인실/4인실]");
			room_count = hotelReservationControl.setRoomCount();
			total_price = hotelReservationControl.getHotelTotalPrice(hotel_num);
			point = (int) (total_price * 0.1);
			// System.out.println("[예매내역] :" + selectAll());
			System.out.println("[결제금액]	:" + total_price + "원");
			System.out.println("[포인트 적립]	:" + point + "원");

		} else {
			System.out.println("호텔 번호 잘못 입력");
		}
		System.out.println("결제 하시겠습니까?(네: Y         아니요: N)");
		final_choice = sc.next().toUpperCase();

		if (final_choice.equals("Y")) {
			hotelReservationControl.updateHotelQuery(hotel_num, check_in, check_out, room_count,total_price);
			phoneM.phoneM(CoreView.session_id, phoneM.selectPhone(CoreView.session_id));
			System.out.println("예매완료");
		}

		else if (final_choice.equals("N")) {
			return;
		} else {
			System.out.println("잘못입력하셨습니다.");
		}
	}

}
