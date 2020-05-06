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

	String menu_1 = "1. ����������\n2. �����ϱ�\n3. �α׾ƿ�";

	public void selectHotelReservationView(boolean login_flag) {
		hotelReservationControl.startHotelReservation(CoreView.session_id);

		// ������
		System.out.println("[������]");
		for (int i = 0; i < arMenu_1_2.length; i++) {
			System.out.println((i + 1) + arMenu_1_2[i]);
		}
		// �ε����� �����ϱ� ���ؼ� -1
		city_choice = sc.nextInt() - 1;

		// �Խ���, ����� �Է�
		// �޷� ��¸޼ҵ�
		System.out.println("[�Խ���]");
		check_in = mainPromptCalender.runPROMPT();
		System.out.println("[�����]");
		check_out = mainPromptCalender.runPROMPT();
		// �������� �´� ȣ�ڸ���Ʈ ���(������,�Խ��� )
		hotelControl.getHotelList(arMenu_1_2[city_choice], check_in);

		System.out.println("ȣ�� ��ȣ �Է� : ");
		hotel_num = sc.next().toUpperCase();

		if (hotelReservationControl.checkHotelNum(hotel_num)) {
			// ���� ���� ���
			hotelControl.showHotelPriceList(hotel_num);

			// ���� ����
			System.out.println("[1�ν�/2�ν�/4�ν�]");
			room_count = hotelReservationControl.setRoomCount();
			total_price = hotelReservationControl.getHotelTotalPrice(hotel_num);
			point = (int) (total_price * 0.1);
			// System.out.println("[���ų���] :" + selectAll());
			System.out.println("[�����ݾ�]	:" + total_price + "��");
			System.out.println("[����Ʈ ����]	:" + point + "��");

		} else {
			System.out.println("ȣ�� ��ȣ �߸� �Է�");
		}
		System.out.println("���� �Ͻðڽ��ϱ�?(��: Y         �ƴϿ�: N)");
		final_choice = sc.next().toUpperCase();

		if (final_choice.equals("Y")) {
			hotelReservationControl.updateHotelQuery(hotel_num, check_in, check_out, room_count,total_price);
			phoneM.phoneM(CoreView.session_id, phoneM.selectPhone(CoreView.session_id));
			System.out.println("���ſϷ�");
		}

		else if (final_choice.equals("N")) {
			return;
		} else {
			System.out.println("�߸��Է��ϼ̽��ϴ�.");
		}
	}

}
