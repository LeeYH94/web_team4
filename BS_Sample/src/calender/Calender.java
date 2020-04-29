package calender;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class Calender {
	private static final int[] MAX_DAYS = { 0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
	private static final int[] LEEP_MAX_DAYS = { 0, 31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
	
	
	private HashMap <Date,String>planMap;
	
	public Calender() {
		planMap=new HashMap<Date,String>();
	}
	/**
	 * 
	 * @param date 2020-01-01
	 * @param plan
	 * @throws ParseException 
	 */
	
	public void registerPlan(String strDate, String plan) throws ParseException  {
		Date data=new SimpleDateFormat("yyyy-mm-dd").parse(strDate);
		System.out.println(data);		
		planMap.put(data, plan);
	}
	public String searchPlan(String strDate) throws ParseException {
		Date date=new SimpleDateFormat("yyyy-mm-dd").parse(strDate);
		String plan= planMap.get(date);
		return plan;
	}

	public boolean isLeepYear(int year) {
		if (year % 4 == 0 && (year % 100 != 0 || year % 400 == 0))
			return true;
		else
			return false;
	}

//�ش� ���� �ִ� ���� �����´�.
	public int GetMaxDay(int year, int month) {
		if (isLeepYear(year)) {
			return LEEP_MAX_DAYS[month];
		} else
			return MAX_DAYS[month];
	}

	// �޷� ���
	public void PrintCalinder(int year, int month) {
		System.out.printf("      <<%d�� %d��>> \n", year, month);
		System.out.println(" SU   MO   TU   WE    TH   FR   SA");
		System.out.println("-------------------------------------");

		// get WeekDay automatically
		int WeekDay = GetWeekDay(year, month, 1);

		// print blink
		for (int i = 0; i < WeekDay; i++) {
			System.out.print("     "); // ��¥�� ����
		}
		int Maxday = GetMaxDay(year, month);
		int count = 7 - WeekDay;
		int delim = (count < 7) ? count : 0;
		// F_LINE 1��
		for (int i = 1; i <= count; i++) {
			System.out.printf("%3d  ", i);
		}
		System.out.println();
		// print 2~L_LINE
		for (int i = count + 1; i <= Maxday; i++) {
			System.out.printf("%3d  ", i);

			if (i % 7 == delim)
				System.out.println();

		}
		System.out.println();
		System.out.println();

	}

	private int GetWeekDay(int year, int month, int day) {
		int syear = 1970;

		int standard_WeekDay = 4;// 1970_Jenuwarry_1st=Thrthday

		int count = 0;

		for (int i = syear; i < year; i++) {
			int delta = (isLeepYear(i)) ? 366 : 365;// 1���� �� ��
			count += delta;
		}

		for (int i = 1; i < month; i++) {
			int delta = GetMaxDay(year, i);
			count += delta;
		}
		count += day - 1;

		int weekday = (count + standard_WeekDay) % 7;
		return weekday;
	}
}
	


