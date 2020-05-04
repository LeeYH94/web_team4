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
		String title = "ABO 여행사\n";
		String menu = "1. 회원가입\n2. 로그인\n3. 비회원 예매하기\n4. 종료";
		String menu_1 = "1. 마이페이지\n2. 예매하기\n3. 로그아웃";
		String menu_1_1 = "1.예약 목록\n2.예약 수정\n3.회원 정보 수정 "; // 마이페이지 메뉴
		String[] arMenu_1_2 = { "Korea", "Japan", "America", "China" };
		String[] arMenu_1_2_1 = { "편도", "왕복" };
		String[] arMenu_1_2_2 = { "A호텔", "B호텔" };
		String[] ar_reservation_chioce = { "항공권예약", "호텔예약" };
		String[] ar_planeclass_choice = { "FIRST_C", "BUSINESS_C", "ECONOMY_C" };
		int choice = 0, trip_choice = 0, round_choice = 0, login_choice = 0;
		int departure_choice = 0, arrival_choice = 0, city_choice = 0;
		int total_price = 0, total_count = 0;
		
		String plane_num1 = "", plane_num2 = "", hotel_num = "";
		String check_in = "", check_out = "";
		String final_choice = "";
		String grade = "", room_count = "";
		// mypage 선택 사항 변수
		int mypage_choice = 0;
		ArrayList<String> user_choice = new ArrayList<String>();
		boolean id_check = true;
		// login 여부 확인
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

			// 종료
			if (choice == 4) {
				break;
			}

			switch (choice) {
			// 회원가입 영역
			case 1:
				// ID입력
				System.out.println("아이디를 입력하세요.");
				id = sc.next();
				if (usersControl.checkDupId(id) == true) {
					System.out.println("중복된 아이디 입니다.");
					while (id_check) {
						System.out.print("1. 아이디 다시 입력하기\n2. 나가기\n");
						login_choice = sc.nextInt();
						switch (login_choice) {
						case 1:
							System.out.println("아이디를 입력하세요.");
							id = sc.next();
							if (usersControl.checkDupId(id) == false) {
								id_check = false;
								break;
							}
							System.out.println("중복된 아이디 입니다.");
							break;
						case 2:
							view();
						}
					}
				}
				id_check = true;

				System.out.println("비밀번호를 입력하세요.");
				pw = sc.next();
				System.out.println("이름을 입력하세요.");
				name = sc.next();
				System.out.println("번호를 입력하세요.");
				phone = sc.next();
				System.out.println("이메일을 입력하세요.");
				email = sc.next();

				try {
					if (usersControl.register(id, pw, name, phone, email, point)) {
						System.out.println("회원가입 성공");
					} else {
						System.out.println("회원가입 실패");
					}
				} catch (AddressException e) {
					e.printStackTrace();
				} catch (MessagingException e) {
					e.printStackTrace();
				}

				break;

			// 로그인 영역
			case 2:
				System.out.println("========로그인=======");
				System.out.print("아이디를 입력하세요 : ");
				id = sc.next();
				System.out.print("비밀번호를 입력하세요 : ");
				pw = sc.next();

				try {
					if (usersControl.checkDupId(id)) {
						if (usersControl.login(id, pw)) {
							System.out.println("로그인 성공");
							// 로그인 성공시 마이페이지 접근 가능!!
							login_flag = true;
							session_id = id;
							System.out.println(menu_1);
							choice = sc.nextInt();
						} else {
							System.out.println("비밀번호 오류");
						}
					} else {
						System.out.println("아이디가 없습니다.");
					}
				} catch (Exception e) {
					System.out.println("로그인 오류");
				}

				// "1. 마이페이지\n2. 예매하기\n3. 로그아웃"

				switch (choice) {
				// 마이페이지 영역
				case 1:
					String new_PW = "", new_Phone = "";
					System.out.println(menu_1_1);
					// 사용자에게 마이페이지 이용 선택지보여주고 선택하게 하는 것.
					mypage_choice = sc.nextInt();
					int updatemember_choice = 0;
					int upre_choice = 0;
					// flag = Update_member가 boolean 값이니 넣어주기
					boolean flag = false;
					switch (mypage_choice) {
					case 1:
						System.out.println("SelectAll() 예약 목록 확인");
//                   System.out.println(usersControl.SelectAll(session_id,air,depart,arrive,guest));
						// 오류부분 수정바람
						break;
						
					case 2:
						System.out.println("예약 수정");
						// String pw,String air,String depart, String arrive,String guest
						System.out.println("1.출발지 수정\n2.도착지 수정\n3.인원수 수정");

						System.out.println("Update_reservation() 예약 수정");
						break;
						
					case 3:
						System.out.println("==========회원정보 수정==========");
						System.out.println("1.비밀번호 수정\n2.핸드폰번호 수정");
						updatemember_choice = sc.nextInt();
						if (updatemember_choice == 1) {
							System.out.println("변경하실 비밀번호를 입력하세요 : ");
							new_PW = sc.next();
							// String pw,String new_pw,String Phone,String new_phone
							// new_phone자리에 그냥 원래 phone 변수 넣어주기 > 비밀번호만 바꾸니까. 아무것도 안넣어주면 null값이 된다.
							flag = usersControl.updateMember(pw, new_PW, phone, phone);
							System.out.println("비밀번호 수정 완료");
						} else if (updatemember_choice == 2) {
							System.out.println("변경하실 핸드폰 번호를 입력하세요 : ");
							new_Phone = sc.next();
							flag = usersControl.updateMember(pw, pw, phone, new_Phone);
							System.out.println("핸드폰 번호 수정 완료");
						}
						break;
					}

					break;
				// 예매하기 영역
				case 2:
					// 항공권or호텔
					for (int i = 0; i < ar_reservation_chioce.length; i++) {
						System.out.println(i + 1 + ar_reservation_chioce[i]);
					}
					choice = sc.nextInt();

					switch (choice) {
					// 항공권 예약
					case 1:
						// 예약번호
						planeReservationControl.startPlaneReservation(session_id);

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

						switch (trip_choice) {
						
						// 편도 영역
						case 1:
							// 출발지, 도착지, 선택날짜, 선택시간 넘겨줌
							// airplaneList()에서 해당 조건에 맞는 planeList 출력
							String userDate = mainPromptCalender.runPROMPT();
							System.out.print("시간을 입력하세요 : ");
							int userTime = sc.nextInt();
							planeControl.getAirplaneList(arMenu_1_2[departure_choice], arMenu_1_2[arrival_choice],
									userDate, userTime);
							
							//비행기번호 입력
							System.out.println("비행기 번호 입력 : ");
							plane_num1 = sc.next();
							
							//비행기번호 중복검사
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
								total_count = planeReservationControl.setSeatCount();
								total_price = planeReservationControl.getPlaneTotalPrice(plane_num1, plane_num2, grade);
								point = (int)(total_price * 0.1);
								//System.out.println("[예매내역]    :" + selectAll());
								System.out.println("[결제금액]	:" + total_price + "원");
								System.out.println("[포인트 적립]	:" + point + "원");
							} else {
								System.out.println("잘못.");
							}

							System.out.println("결제 하시겠습니까?(네: Y         아니요: N)");
							final_choice = sc.next().toUpperCase();

							if (final_choice.equals("Y")) {
								if (!login_flag) {
									System.out.println("로그인 하시겠습니까?(네: Y         아니요: N)");
									new_phone = "";
									new_pw = "";
									System.out.println("========로그인=======");
									System.out.print("아이디를 입력하세요 : ");
									id = sc.next();
									System.out.print("비밀번호를 입력하세요 : ");
									pw = sc.next();

									try {
										if (usersControl.checkDupId(id)) {
											if (usersControl.login(id, pw)) {
												System.out.println("로그인 성공");
												// 로그인 성공시 마이페이지 접근 가능!!
												login_flag = true;
												session_id = id;
												System.out.println(menu_1);
												choice = sc.nextInt();
											} else {
												System.out.println("비밀번호 오류");
											}
										} else {
											System.out.println("아이디가 없습니다.");
										}
									} catch (Exception e) {
										System.out.println("로그인 오류");
									}
								} else{
									//업데이트
									planeReservationControl.updatePlaneQuery(plane_num1, null, grade, total_count, total_price);
									//포인트 적립
									usersControl.pointUpdate(point, session_id);
									System.out.println("예매 완료");
								} 

							}else if(final_choice.equals("N")){
//								continue;
							}else {
								System.out.println("잘못입력하셨습니다.");
							}
							break;

						// 왕복 영역
						case 2:
							//출발
							System.out.println("[출발일]");
							userDate = mainPromptCalender.runPROMPT();
							System.out.print("시간을 입력하세요 : ");
							userTime = sc.nextInt();
							planeControl.getAirplaneList(arMenu_1_2[departure_choice], arMenu_1_2[arrival_choice],
									userDate, userTime);
							// 리스트에서 비행기번호 입력
							System.out.println("비행기 번호 입력 : ");
							plane_num1 = sc.next().toUpperCase();

							//도착
							System.out.println("[도착일]");
							userDate = mainPromptCalender.runPROMPT();
							System.out.print("시간을 입력하세요 : ");
							userTime = sc.nextInt();
							planeControl.getAirplaneList(arMenu_1_2[arrival_choice], arMenu_1_2[departure_choice],
									userDate, userTime);
							// 리스트에서 비행기번호 입력
							System.out.println("비행기 번호 입력 : ");
							plane_num2 = sc.next().toUpperCase();

							//비행기 번호 확인
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
								total_count = planeReservationControl.setSeatCount();
								total_price = planeReservationControl.getPlaneTotalPrice(plane_num1, plane_num2, grade);
								point = (int)(total_price * 0.1);
								//System.out.println("[예매내역]    :" + selectAll());
								System.out.println("[결제금액]	:" + total_price + "원");
								System.out.println("[포인트 적립]	:" + point + "원");
							} else {
								System.out.println("잘못된 입력입니다.");
							}

							System.out.println("결제 하시겠습니까?(네: Y         아니요: N)");
							final_choice = sc.next().toUpperCase();

							if (final_choice.equals("Y")) {
								if (!login_flag) {
									System.out.println("로그인 하시겠습니까?(네: Y         아니요: N)");
									new_phone = "";
									new_pw = "";
									System.out.println("========로그인=======");
									System.out.print("아이디를 입력하세요 : ");
									id = sc.next();
									System.out.print("비밀번호를 입력하세요 : ");
									pw = sc.next();

									try {
										if (usersControl.checkDupId(id)) {
											if (usersControl.login(id, pw)) {
												System.out.println("로그인 성공");
												// 로그인 성공시 마이페이지 접근 가능!!
												login_flag = true;
												session_id = id;
												System.out.println(menu_1);
												choice = sc.nextInt();
											} else {
												System.out.println("비밀번호 오류");
											}
										} else {
											System.out.println("아이디가 없습니다.");
										}
									} catch (Exception e) {
										System.out.println("로그인 오류");
									}
								} else{
									//업데이트
									planeReservationControl.updatePlaneQuery(plane_num1, plane_num2, grade, total_count, total_price);
									//포인트 적립
									usersControl.pointUpdate(point, session_id);				
									System.out.println("예매 완료");					
								} 

							}else if(final_choice.equals("N")){
//								continue;
							}else {
								System.out.println("잘못입력하셨습니다.");
							}

							break;

						// 뒤로가기(보류)
						case 3:
							break;
						}

					// 호텔영역
					case 2:
						//예약번호
						hotelReservationControl.startHotelReservation(session_id);
						
						//여행지
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
						hotel_num = sc.next();
						
						if(hotelReservationControl.checkHotelNum(hotel_num)) {
							//객실 가격 출력
							hotelControl.showHotelPriceList(hotel_num);
							
							//객실 선택
							System.out.println("[1인실/2인실/4인실]");
							room_count = hotelReservationControl.setRoomCount();
							total_price = hotelReservationControl.getHotelTotalPrice(hotel_num, room_count);
							point = (int)(total_price * 0.1);
							//System.out.println("[예매내역]    :" + selectAll());
							System.out.println("[결제금액]	:" + total_price + "원");
							System.out.println("[포인트 적립]	:" + point + "원");
							
						}else {
							System.out.println("비행기 번호 잘못 입력");
						}
						System.out.println("결제 하시겠습니까?(네: Y         아니요: N)");
						final_choice = sc.next().toUpperCase();

						if (final_choice.equals("Y")) {
							if (!login_flag) {
								System.out.println("로그인 하시겠습니까?(네: Y         아니요: N)");
								new_phone = "";
								new_pw = "";
								System.out.println("========로그인=======");
								System.out.print("아이디를 입력하세요 : ");
								id = sc.next();
								System.out.print("비밀번호를 입력하세요 : ");
								pw = sc.next();

								try {
									if (usersControl.checkDupId(id)) {
										if (usersControl.login(id, pw)) {
											System.out.println("로그인 성공");
											// 로그인 성공시 마이페이지 접근 가능!!
											login_flag = true;
											session_id = id;
											System.out.println(menu_1);
											choice = sc.nextInt();
										} else {
											System.out.println("비밀번호 오류");
										}
									} else {
										System.out.println("아이디가 없습니다.");
									}
								} catch (Exception e) {
									System.out.println("로그인 오류");
								}
							} else{
								hotelReservationControl.updateHotelQuery(hotel_num, check_in, check_out, room_count, total_price);
								System.out.println("예매 완료");
//								TODO 포인트 적립 메소드 필요
							} 

						}else if(final_choice.equals("N")){
//							continue;
						}else {
							System.out.println("잘못입력하셨습니다.");
						}

						break;

					}
					
				// 비회원 예매하기 영역
				case 3:
					Random ran = new Random();
					String temp_1 = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
					String temp_2 = "0123456789";
					String temp_id = "";
					
					//비회원 아이디
				    for (int i = 0; i < 4; i++) {
				       	temp_id += temp_1.charAt(ran.nextInt(temp_1.length()));
				       	for (int j = 0; j < 2; j++) {
				       		temp_id += temp_2.charAt(ran.nextInt(temp_2.length()));		
				       		}
				        }
				    
				    //아이디 중복검사
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
					
			        System.out.println("이름을 입력하세요.");
		        	name = sc.next();
		        	System.out.println("번호를 입력하세요.");
		        	phone = sc.next();
		        	System.out.println("이메일을 입력하세요.");
		        	email = sc.next();
		        	
					try {
						if (usersControl.register(id, pw, name, phone, email, point)) {
							System.out.println("비회원");
						} else {
							System.out.println("실패");
						}
					} catch (AddressException e) {
						e.printStackTrace();
					} catch (MessagingException e) {
						e.printStackTrace();
					}

					
					break;

				// 로그아웃 영역
				case 4:
					usersControl.logout();
					System.out.println("logout 되었습니다. ");
					break;
				}
				break;

			}
		}
	}

}
