package core;

import java.util.ArrayList;
import java.util.Random;
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
		int total_price = 0, total_count = 0;
		
		String plane_num1 = "", plane_num2 = "", hotel_num = "";
		String check_in = "", check_out = "";
		String final_choice = "";
		String grade = "", room_count = "";
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

						switch (trip_choice) {
						
						// �� ����
						case 1:
							// �����, ������, ���ó�¥, ���ýð� �Ѱ���
							// airplaneList()���� �ش� ���ǿ� �´� planeList ���
							String userDate = mainPromptCalender.runPROMPT();
							System.out.print("�ð��� �Է��ϼ��� : ");
							int userTime = sc.nextInt();
							planeControl.getAirplaneList(arMenu_1_2[departure_choice], arMenu_1_2[arrival_choice],
									userDate, userTime);
							
							//������ȣ �Է�
							System.out.println("����� ��ȣ �Է� : ");
							plane_num1 = sc.next();
							
							//������ȣ �ߺ��˻�
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
								total_count = planeReservationControl.setSeatCount();
								total_price = planeReservationControl.getPlaneTotalPrice(plane_num1, plane_num2, grade);
								point = (int)(total_price * 0.1);
								//System.out.println("[���ų���]    :" + selectAll());
								System.out.println("[�����ݾ�]	:" + total_price + "��");
								System.out.println("[����Ʈ ����]	:" + point + "��");
							} else {
								System.out.println("�߸�.");
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
									//������Ʈ
									planeReservationControl.updatePlaneQuery(plane_num1, null, grade, total_count, total_price);
									//����Ʈ ����
									usersControl.pointUpdate(point, session_id);
									System.out.println("���� �Ϸ�");
								} 

							}else if(final_choice.equals("N")){
//								continue;
							}else {
								System.out.println("�߸��Է��ϼ̽��ϴ�.");
							}
							break;

						// �պ� ����
						case 2:
							//���
							System.out.println("[�����]");
							userDate = mainPromptCalender.runPROMPT();
							System.out.print("�ð��� �Է��ϼ��� : ");
							userTime = sc.nextInt();
							planeControl.getAirplaneList(arMenu_1_2[departure_choice], arMenu_1_2[arrival_choice],
									userDate, userTime);
							// ����Ʈ���� ������ȣ �Է�
							System.out.println("����� ��ȣ �Է� : ");
							plane_num1 = sc.next().toUpperCase();

							//����
							System.out.println("[������]");
							userDate = mainPromptCalender.runPROMPT();
							System.out.print("�ð��� �Է��ϼ��� : ");
							userTime = sc.nextInt();
							planeControl.getAirplaneList(arMenu_1_2[arrival_choice], arMenu_1_2[departure_choice],
									userDate, userTime);
							// ����Ʈ���� ������ȣ �Է�
							System.out.println("����� ��ȣ �Է� : ");
							plane_num2 = sc.next().toUpperCase();

							//����� ��ȣ Ȯ��
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
								total_count = planeReservationControl.setSeatCount();
								total_price = planeReservationControl.getPlaneTotalPrice(plane_num1, plane_num2, grade);
								point = (int)(total_price * 0.1);
								//System.out.println("[���ų���]    :" + selectAll());
								System.out.println("[�����ݾ�]	:" + total_price + "��");
								System.out.println("[����Ʈ ����]	:" + point + "��");
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
									//������Ʈ
									planeReservationControl.updatePlaneQuery(plane_num1, plane_num2, grade, total_count, total_price);
									//����Ʈ ����
									usersControl.pointUpdate(point, session_id);				
									System.out.println("���� �Ϸ�");					
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
						//�����ȣ
						hotelReservationControl.startHotelReservation(session_id);
						
						//������
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
						hotel_num = sc.next();
						
						if(hotelReservationControl.checkHotelNum(hotel_num)) {
							//���� ���� ���
							hotelControl.showHotelPriceList(hotel_num);
							
							//���� ����
							System.out.println("[1�ν�/2�ν�/4�ν�]");
							room_count = hotelReservationControl.setRoomCount();
							total_price = hotelReservationControl.getHotelTotalPrice(hotel_num, room_count);
							point = (int)(total_price * 0.1);
							//System.out.println("[���ų���]    :" + selectAll());
							System.out.println("[�����ݾ�]	:" + total_price + "��");
							System.out.println("[����Ʈ ����]	:" + point + "��");
							
						}else {
							System.out.println("����� ��ȣ �߸� �Է�");
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
								hotelReservationControl.updateHotelQuery(hotel_num, check_in, check_out, room_count, total_price);
								System.out.println("���� �Ϸ�");
//								TODO ����Ʈ ���� �޼ҵ� �ʿ�
							} 

						}else if(final_choice.equals("N")){
//							continue;
						}else {
							System.out.println("�߸��Է��ϼ̽��ϴ�.");
						}

						break;

					}
					
				// ��ȸ�� �����ϱ� ����
				case 3:
					Random ran = new Random();
					String temp_1 = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
					String temp_2 = "0123456789";
					String temp_id = "";
					
					//��ȸ�� ���̵�
				    for (int i = 0; i < 4; i++) {
				       	temp_id += temp_1.charAt(ran.nextInt(temp_1.length()));
				       	for (int j = 0; j < 2; j++) {
				       		temp_id += temp_2.charAt(ran.nextInt(temp_2.length()));		
				       		}
				        }
				    
				    //���̵� �ߺ��˻�
			        if(usersControl.checkDupId(temp_id) == true) {
			        	while (id_check) {
			        		temp_id = "";
			        		for (int i = 0; i < 4; i++) {
						       	temp_id += temp_1.charAt(ran.nextInt(temp_1.length()));
						       	for (int j = 0; j < 2; j++) {
						       		temp_id += temp_2.charAt(ran.nextInt(temp_2.length()));		
						       		}
						        }
			        		if (usersControl.checkDupId(id) == false) {
								id_check = false;
								break;
							}
			        		break;
			        	}
			        }
			        
			        id_check = true;
					
			        System.out.println("�̸��� �Է��ϼ���.");
		        	name = sc.next();
		        	System.out.println("��ȣ�� �Է��ϼ���.");
		        	phone = sc.next();
		        	System.out.println("�̸����� �Է��ϼ���.");
		        	email = sc.next();
		        	
					try {
						if (usersControl.register(id, pw, name, phone, email, point)) {
							System.out.println("��ȸ��");
						} else {
							System.out.println("����");
						}
					} catch (AddressException e) {
						e.printStackTrace();
					} catch (MessagingException e) {
						e.printStackTrace();
					}

					
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
