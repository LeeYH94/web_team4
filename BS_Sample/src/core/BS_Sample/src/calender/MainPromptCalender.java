package calender;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Scanner;

public class MainPromptCalender {

	String selectDay = "";

	/**
	 * 
	 * @param week=weekday
	 * @return 0~6(0=SunDAy 6=SaturDAy)
	 */
	public int ParseDay(String week) {
		if (week.equals("su"))
			return 0;
		else if (week.equals("mo"))
			return 1;
		else if (week.equals("tu"))
			return 2;
		else if (week.equals("we"))
			return 3;
		else if (week.equals("th"))
			return 4;
		else if (week.equals("fr"))
			return 5;
		else if (week.equals("sa"))
			return 6;
		else
			return 0;

	}

	public String runPROMPT() {

		Scanner scanner = new Scanner(System.in);
		Calender cal = new Calender();

		cmdCal(scanner, cal);

		return selectDay;
	}

	public void thisTime() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Calendar cal = Calendar.getInstance();
		String today = null;
		today = formatter.format(cal.getTime());
		Timestamp ts = Timestamp.valueOf(today);

		System.out.println(" Timestamp : " + ts);
	}

	private void cmdCal(Scanner sc, Calender cal) {
		int month = 1;
		int year = 2000;
		int day = 1;

		year = 2020;

		System.out.println("달 입력");
		System.out.print("month > ");
		month = sc.nextInt();

		cal.PrintCalinder(year, month);
		System.out.println("일 입력");
		System.out.print("day > ");
		day = sc.nextInt();

		// 입력불가if
		selectDay = year + "-" + month + "-" + day;
		// System.out.println(selectDay);

	}

	private void cmdSearch(Scanner sc, Calender cal) throws ParseException {
		System.out.println("날짜선택");
		String date = selectDay;
		String text = sc.nextLine();
		cal.registerPlan(date, text);
	}
//   private void cmdRegister(Scanner sc,Calender cal) throws ParseException {
//      System.out.println("등록"); 
//      System.out.println("yyyy-mm-dd");
//      String date=sc.nextLine();
//      String text=sc.nextLine();
//      cal.registerPlan(date, text);      
//   }

	public static void main(String[] args) {
		MainPromptCalender p = new MainPromptCalender();
		p.runPROMPT();
	}
}
