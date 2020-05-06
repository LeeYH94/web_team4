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

	String menu_1 = "1. ����������\n2. �����ϱ�\n3. �α׾ƿ�";

	public void selectPlaneReservationView(boolean login_flag) {

		planeReservationControl.startPlaneReservation(CoreView.session_id);

		// �����
		System.out.println("[�����]");
		for (int i = 0; i < arMenu_1_2.length; i++) {
			System.out.println((i + 1) + arMenu_1_2[i]);
		}
		// �ε����� �����ϱ� ���� -1
		departure_choice = sc.nextInt() - 1;

		// ������
		System.out.println("[������]");
		for (int i = 0; i < arMenu_1_2.length; i++) {
			System.out.println((i + 1) + arMenu_1_2[i]);
		}
		// �ε����� �����ϱ� ���� -1
		arrival_choice = sc.nextInt() - 1;

		System.out.println("1. ��\n2. �պ�\n");
		trip_choice = sc.nextInt();
		// UP

		switch (trip_choice) {
		// �� ����
		case 1:
//	�ڴ޷� ��¸޼ҵ�;
			// �����, ������, ���ó�¥, ���ýð� �Ѱ���
			// airplaneList()���� �ش� ���ǿ� �´� planeList ���
			String userDate = mainPromptCalender.runPROMPT();
			System.out.print("�ð��� �Է��ϼ��� : ");
			int userTime = sc.nextInt();
			planeControl.getAirplaneList(arMenu_1_2[departure_choice], arMenu_1_2[arrival_choice], userDate, userTime);
			// ����Ʈ���� ������ȣ �Է�
			// ������� while ������ ���ξ� �ҵ�? => �ߺ��˻� ������
			System.out.println("����� ��ȣ �Է� : ");
			plane_num1 = sc.next().toUpperCase();

			if (planeReservationControl.checkPlaneNum(plane_num1)) {
				// ����� Ŭ���� ����
				System.out.println("[����� Ŭ���� ����]");
				for (int i = 0; i < ar_planeclass_choice.length; i++) {
					System.out.println((i + 1) + ar_planeclass_choice[i]);
				}
				grade = ar_planeclass_choice[sc.nextInt() - 1];

				// ��޺� ���� ���
				System.out.println("ADULT\tCHILD\tBABY");
				planeControl.showGradePrice(plane_num1, grade);

				// ����, �Ҿ�, ���� �ο��� �Է�
				seat_count = planeReservationControl.setSeatCount();
				total_price = planeReservationControl.getPlaneTotalPrice(plane_num1, null, grade);
				point = (int) (total_price * 0.1);
				// System.out.println("[���ų���] :" + selectAll());
				System.out.println("[�����ݾ�]	:" + total_price + "��");
				System.out.println("[����Ʈ ����]	:" + point + "��");
			} else {
				System.out.println("�߸��� ����� ��ȣ");
			}

			System.out.println("���� �Ͻðڽ��ϱ�?(��: Y         �ƴϿ�: N)");
			final_choice = sc.next().toUpperCase();

			if (final_choice.equals("Y")) {
				planeReservationControl.updatePlaneQuery(plane_num1, null, grade, seat_count, total_price);
				usersControl.pointUpdate(point, CoreView.session_id);
				phoneM.phoneM(CoreView.session_id, phoneM.selectPhone(CoreView.session_id));
				System.out.println("���� �Ϸ�");
				PlaneReservationControl.session_plane_reservation = "";
			} else if (final_choice.equals("N")) {
				return;
			} else {
				System.out.println("�߸��Է��ϼ̽��ϴ�.");
			}
			break;

		// �պ� ����
		case 2:
			// ���
			System.out.println("[�����]");
			userDate = mainPromptCalender.runPROMPT();
			System.out.print("�ð��� �Է��ϼ��� : ");
			userTime = sc.nextInt();
			planeControl.getAirplaneList(arMenu_1_2[departure_choice], arMenu_1_2[arrival_choice], userDate, userTime);
			// ����Ʈ���� ������ȣ �Է�
			System.out.println("����� ��ȣ �Է� : ");
			plane_num1 = sc.next().toUpperCase();

			// ����
			System.out.println("[������]");
			userDate = mainPromptCalender.runPROMPT();
			System.out.print("�ð��� �Է��ϼ��� : ");
			userTime = sc.nextInt();
			planeControl.getAirplaneList(arMenu_1_2[arrival_choice], arMenu_1_2[departure_choice], userDate, userTime);
			// ����Ʈ���� ������ȣ �Է�
			System.out.println("����� ��ȣ �Է� : ");
			plane_num2 = sc.next().toUpperCase();

			// ����� ��ȣ Ȯ��
			if (planeReservationControl.checkPlaneNum(plane_num1)
					&& planeReservationControl.checkPlaneNum(plane_num2)) {
				// ����� Ŭ���� ����
				System.out.println("[����� Ŭ���� ����]");
				for (int i = 0; i < ar_planeclass_choice.length; i++) {
					System.out.println((i + 1) + ar_planeclass_choice[i]);
				}
				grade = ar_planeclass_choice[sc.nextInt() - 1];

				// ��޺� ���� ���
				System.out.println("ADULT\tCHILD\tBABY");
				planeControl.showGradePrice(plane_num1, grade);
				planeControl.showGradePrice(plane_num2, grade);

				// ����, �Ҿ�, ���� �ο��� �Է�
				seat_count = planeReservationControl.setSeatCount();
				total_price = planeReservationControl.getPlaneTotalPrice(plane_num1, plane_num2, grade);
				point = (int) (total_price * 0.1);
				// System.out.println("[���ų���] :" + selectAll());
				System.out.println("[�����ݾ�]	:" + total_price + "��");
				System.out.println("[����Ʈ ����]	:" + point + "��");
			} else {
				System.out.println("�߸��� �Է��Դϴ�.");
			}

			System.out.println("���� �Ͻðڽ��ϱ�?(��: Y         �ƴϿ�: N)");
			final_choice = sc.next().toUpperCase();

			if (final_choice.equals("Y")) {
				planeReservationControl.updatePlaneQuery(plane_num1, null, grade, seat_count, total_price);
				usersControl.pointUpdate(point, CoreView.session_id);
				phoneM.phoneM(CoreView.session_id, phoneM.selectPhone(CoreView.session_id));
				PlaneReservationControl.session_plane_reservation = "";
				System.out.println("���� �Ϸ�");
			} else if (final_choice.equals("N")) {
				return;
			} else {
				System.out.println("�߸��Է��ϼ̽��ϴ�.");
			}
			break;
		}
	}

}
