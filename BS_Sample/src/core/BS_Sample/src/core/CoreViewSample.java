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

public class CoreViewSample {

	//객체 만들기
	UsersControl usersControl = new UsersControl();
	PlaneReservationControl planeReservationControl = new PlaneReservationControl();
	PlaneControl planeControl = new PlaneControl();
	HotelReservationControl hotelReservationControl = new HotelReservationControl();
	HotelControl hotelControl = new HotelControl();
	MainPromptCalender mainPromptCalender = new MainPromptCalender();
	
	//
	public static String session_id;
	public static String session_hotel_reservation;
	public static String session_plane_reservation;
	
	public void view() {
		Scanner sc = new Scanner(System.in);
		String title = "ABO 여행사\n";
	    String main_menu = "1. 회원가입\n2. 로그인\n3. 비회원 예매하기\n0. 종료";
	    
	    int choice = 0;
	    
	    
	    while (true) {
	    	System.out.println(title + main_menu);
	    	choice = sc.nextInt();
	    	
	    	if (choice == 0) {
	    		break;
	    	}
	    	
	    	switch (choice) {
	    	//회원가입
	    	case 1:
	    		break;
	    	
	    	//로그인
	    	case 2:
	    		
	    		break;
	    		
	    	//예매하기
	    	case 3:
	    		break;
	    	
	    	
	    		
	    		
	    		
	    		
	    	}	    	
	    }
	}
}













