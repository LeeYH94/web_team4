package core;

import java.util.ArrayList;

import java.util.Scanner;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import calender.MainPromptCalender;
import hotel_reservation.HotelReservationControl;
import hotels.HotelControl;
import plane_reservation.PlaneReservationControl;
import planes.PlaneControl;
import users.UsersControl;

public class CoreView {
	// CoreView
	// ���� ȭ���� ����� ���� �˴ϴ�
	UsersControl usersControl = new UsersControl();
	PlaneReservationControl planeReservationControl = new PlaneReservationControl();
	PlaneControl planeControl = new PlaneControl();
	HotelReservationControl hotelReservationControl = new HotelReservationControl();
	HotelControl hotelControl = new HotelControl();
	MainPromptCalender mainPromptCalender = new MainPromptCalender();

	public static String session_id;

	public void view() {
		Scanner sc = new Scanner(System.in);
		String title = "ABO �����\n";
		String menu = "1. ȸ������\n2. �α���\n3. ��ȸ�� �����ϱ�\n4. ����";
		String menu_1 = "1. ����������\n2. �����ϱ�\n3. �α׾ƿ�";
		String menu_1_1 = "1.���� ���\n2.���� ����\n3.ȸ�� ���� ���� "; // ���������� �޴�
		String[] arMenu_1_2 = { "Korea", "Japan", "America", "China" };
		String[] arMenu_1_2_1 = { "��", "�պ�" };
		String[] arMenu_1_2_2 = { "Aȣ��", "Bȣ��" };
		String[] ar_reservation_chioce = { "�װ��ǿ���", "ȣ�ڿ���" };
		String[] ar_planeclass_choice = { "FIRST_C", "BUSINESS_C", "ECONOMY_C" };
		int choice = 0, trip_choice = 0, round_choice = 0, login_choice = 0;
		int departure_choice = 0, arrival_choice = 0, city_choice = 0;
		String plane_num1 = "", plane_num2 = "", hotal_num = "";
		// mypage ���� ���� ����
		int mypage_choice = 0;
		ArrayList<String> user_choice = new ArrayList<String>();
		boolean id_check = true;
		// login ���� Ȯ��
		boolean login_flag = false;

		String id = "";
		String pw = "";
		String name = "";
		String phone = "";
		String email = "";
		String air = "";
		String depart = "";
		String arrive = "";
		String new_phone = "";
		String new_pw = "";
		int point = 0;
		int guest = 0;

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

				break;

			// �α��� ����
			case 2:
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
							session_id = id;
							System.out.println(menu_1);
							choice = sc.nextInt();
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

				switch (choice) {
				// ���������� ����
				case 1:
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
//                   System.out.println(usersControl.SelectAll(session_id,air,depart,arrive,guest));
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

					break;
				// �����ϱ� ����
				case 2:
					// �װ���orȣ��
					for (int i = 0; i < ar_reservation_chioce.length; i++) {
						System.out.println(i + 1 + ar_reservation_chioce[i]);
					}
					choice = sc.nextInt();

					switch (choice) {
					// �װ��� ����
					case 1:
						// �����ȣ
						planeReservationControl.startPlaneReservation(session_id);

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
//					�ڴ޷� ��¸޼ҵ�;
							// �����, ������, ���ó�¥, ���ýð� �Ѱ���
							// airplaneList()���� �ش� ���ǿ� �´� planeList ���
							String userDate = mainPromptCalender.runPROMPT();
							System.out.print("�ð��� �Է��ϼ��� : ");
							int userTime = sc.nextInt();
							planeControl.getAirplaneList(arMenu_1_2[departure_choice], arMenu_1_2[arrival_choice],
									userDate, userTime);
							// ����Ʈ���� ������ȣ �Է�
							// ������� while ������ ���ξ� �ҵ�? => �ߺ��˻� ������
							System.out.println("����� ��ȣ �Է� : ");
							plane_num1 = sc.next();

							if (planeReservationControl.checkPlaneNum(plane_num1)) {
								// ����� Ŭ���� ����
								System.out.println("[����� Ŭ���� ����]");
								for (int i = 0; i < ar_planeclass_choice.length; i++) {
									System.out.println((i + 1) + ar_planeclass_choice[i]);
								}
								String grade = ar_planeclass_choice[sc.nextInt() - 1];

								// ��޺� ���� ���
								System.out.println("ADULT\tCHILD\tBABY");
								planeControl.showGradePrice(plane_num1, grade);

								// ����, �Ҿ�, ���� �ο��� �Է�
								int seat_count = planeReservationControl.setSeatCount();
								planeReservationControl.updatePlaneQuery(plane_num1, null, grade, seat_count);
								System.out.println(planeReservationControl.getTotalPrice() + "��.");
							} else {
								System.out.println("�߸�.");
							}

							System.out.println("���� �Ͻðڽ��ϱ�?(��: Y         �ƴϿ�: N)");
							String final_choice = sc.next().toUpperCase();

							if (final_choice.equals("Y")) {
								if (!login_flag) {
									System.out.println("�α��� �Ͻðڽ��ϱ�?(��: Y         �ƴϿ�: N)");
									new_phone = "";
									new_pw = "";
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
												session_id = id;
												System.out.println(menu_1);
												choice = sc.nextInt();
											} else {
												System.out.println("��й�ȣ ����");
											}
										} else {
											System.out.println("���̵� �����ϴ�.");
										}
									} catch (Exception e) {
										System.out.println("�α��� ����");
									}
								} else{
									System.out.println("���� �Ϸ�");
//									TODO ����Ʈ ���� �޼ҵ� �ʿ�
								} 

							}else if(final_choice.equals("N")){
//								continue;
							}else {
								System.out.println("�߸��Է��ϼ̽��ϴ�.");
							}
							break;

						// �պ� ����
						case 2:
//					�ڴ޷� ��¸޼ҵ�;
							// �޷� �߰��ϰ� ���� �޾ƿ��� ���� ������
							userDate = mainPromptCalender.runPROMPT();
							System.out.print("�ð��� �Է��ϼ��� : ");
							userTime = sc.nextInt();
							planeControl.getAirplaneList(arMenu_1_2[departure_choice], arMenu_1_2[arrival_choice],
									userDate, userTime);
							// ����Ʈ���� ������ȣ �Է�
							System.out.println("����� ��ȣ �Է� : ");
							plane_num1 = sc.next().toUpperCase();

//					�ڴ޷� ��¸޼ҵ�;
							// ���� ������� �������� �ٲ���
							userDate = mainPromptCalender.runPROMPT();
							System.out.print("�ð��� �Է��ϼ��� : ");
							userTime = sc.nextInt();
							planeControl.getAirplaneList(arMenu_1_2[arrival_choice], arMenu_1_2[departure_choice],
									userDate, userTime);
							// ����Ʈ���� ������ȣ �Է�
							System.out.println("����� ��ȣ �Է� : ");
							plane_num2 = sc.next().toUpperCase();

							if (planeReservationControl.checkPlaneNum(plane_num1)
									&& planeReservationControl.checkPlaneNum(plane_num2)) {
								// ����� Ŭ���� ����
								System.out.println("[����� Ŭ���� ����]");
								for (int i = 0; i < ar_planeclass_choice.length; i++) {
									System.out.println((i + 1) + ar_planeclass_choice[i]);
								}
								String grade = ar_planeclass_choice[sc.nextInt() - 1];

								// ��޺� ���� ���
								System.out.println("ADULT\tCHILD\tBABY");
								planeControl.showGradePrice(plane_num1, grade);
								planeControl.showGradePrice(plane_num2, grade);

								// ����, �Ҿ�, ���� �ο��� �Է�
								int seat_count = planeReservationControl.setSeatCount();
								planeReservationControl.updatePlaneQuery(plane_num1, plane_num2, grade, seat_count);
								System.out.println(planeReservationControl.getTotalPrice() + "��.");
							} else {
								System.out.println("�߸��� �Է��Դϴ�.");
							}

							System.out.println("���� �Ͻðڽ��ϱ�?(��: Y         �ƴϿ�: N)");
							final_choice = sc.next().toUpperCase();

							if (final_choice.equals("Y")) {
								if (!login_flag) {
									System.out.println("�α��� �Ͻðڽ��ϱ�?(��: Y         �ƴϿ�: N)");
									new_phone = "";
									new_pw = "";
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
												session_id = id;
												System.out.println(menu_1);
												choice = sc.nextInt();
											} else {
												System.out.println("��й�ȣ ����");
											}
										} else {
											System.out.println("���̵� �����ϴ�.");
										}
									} catch (Exception e) {
										System.out.println("�α��� ����");
									}
								} else{
									System.out.println("���� �Ϸ�");
//									TODO ����Ʈ ���� �޼ҵ� �ʿ�
								} 

							}else if(final_choice.equals("N")){
//								continue;
							}else {
								System.out.println("�߸��Է��ϼ̽��ϴ�.");
							}

							break;

						// �ڷΰ���(����)
						case 3:
							break;
						}

						// ȣ�ڿ���
					case 2:
						hotelReservationControl.startHotelReservation(session_id);

						System.out.println("[������]");
						for (int i = 0; i < arMenu_1_2.length; i++) {
							System.out.println((i + 1) + arMenu_1_2[i]);
						}
						// �ε����� �����ϱ� ���ؼ� -1
						city_choice = sc.nextInt() - 1;

						// �Խ���, ����� �Է�
//				�ڴ޷� ��¸޼ҵ�
						String userDate = mainPromptCalender.runPROMPT();
						// �������� �´� ȣ�ڸ���Ʈ ���(������,�Խ��� )
						hotelControl.getHotelList(arMenu_1_2[city_choice], userDate);
						System.out.println("ȣ�� ��ȣ �Է� : ");
						hotal_num = sc.next();

//				��ȣ�ڹ�ȣ �ߺ��˻� �޼ҵ�
//				if(��ȣ�ڹ�ȣ �ߺ��˻� �޼ҵ�) {

//					���ش� ȣ�ڹ�ȣ�� ������� �޼ҵ�
						// ��(1,2,4)����
						hotelReservationControl.setRoomCount();
						// room_total �ٲٴ� �޼ҵ�
						hotelReservationControl.setRoomTotal();
						// ���೻�� �����ֱ�
//					System.out.println("�����Ͻðڽ��ϱ�? (y/n)");
//					String finish = sc.next();

//					if(finish == "y") {
//						//�α��ο��� Ȯ��
						// �α���o - ���� ����
//						//�α���x - �α���â or ��ȸ�� �α���
//					}else if((finish == "n")) {
//						//�������� ���ư���
//					}else {
//						System.out.println("�߸��� �Է��Դϴ�. (y/n)�� �Է��ϼ���");
//					}
						System.out.print(hotelReservationControl.getHotelTotalPrice() + "��.");
//				}else{
//					System.out.println("�߸��� �Է��Դϴ�.");
//				}

						break;

					}
					// ��ȸ�� �����ϱ� ����
				case 3:
//					TODO �����ϱ� �޼ҵ� ��� ����, �޴��� �ߺ� �˻� �޼ҵ�
					break;

				// �α׾ƿ� ����
				case 4:
					usersControl.logout();
					System.out.println("logout �Ǿ����ϴ�. ");
					break;
				}
				break;

			}
		}
	}

}
