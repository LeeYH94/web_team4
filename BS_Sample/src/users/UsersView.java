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

	String menu_1_1 = "1.예약 목록\n2.예약 수정\n3.회원 정보 수정 "; // 마이페이지 메뉴

	public String[] registerUsersView() {
		String[] usersPwAndPhone = new String[2];
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
					return null;
				}
			}
		}
		id_check = true;

		System.out.println("비밀번호를 입력하세요.");
		pw = sc.next();
		usersPwAndPhone[0] = pw;
		System.out.println("이름을 입력하세요.");
		name = sc.next();
		System.out.println("번호를 입력하세요.");
		phone = sc.next();
		usersPwAndPhone[1] = phone;
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
		return usersPwAndPhone;
	}

	public boolean loginUsersView() {
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
					CoreView.session_id = id;
				} else {
					System.out.println("비밀번호 오류");
				}
			} else {
				System.out.println("아이디가 없습니다.");
			}
		} catch (Exception e) {
			System.out.println("로그인 오류");
		}
		return login_flag;

	}

	public void selectMypageUsersView(String pw, String phone) {
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
//       System.out.println(usersControl.SelectAll(session_id,air,depart,arrive,guest));
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
	}

}
