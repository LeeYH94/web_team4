package planes;

public class PlaneModel {

	// FlightModel
	// �ٵ� ��������δ� Ƽ�Ͽ� �������ϴ�

	/*
	 * """ Field """
	 * 
	 * FlightID (PK - �׳� ���� ��ȣ. FK�� ���� �ʵ��� ��, �ǹ̴� �����ϴ�) DeparturePlace - ��� ����
	 * ArrivalPlace - ���� ���� DepartureDateTime - ��� ��¥/�ð� ArrivalDateTime - ���� ��¥/�ð�
	 * (���� �ʿ� ���� ���� �ֽ��ϴٸ�, ���α׷����� Ȱ���� ������ �����ø� ���켼��) Company - �װ��� SeatOption - �¼�
	 * ����. ���ڳ��, �����Ͻ� �̷� �� Price - Ƽ�� ���� isBooked - ���� ������ �ƴ��� üũ
	 * 
	 * 
	 * """ ��� ���� """
	 * 
	 * �ѱ����� 1�� 1�� 10�ÿ� ����ϴ� �̱��� ����⸦ ã�� �ʹ�? (�׳� ���� �˾� ���ø� �˴ϴ�... ���� ������ �����ƿ�) SELECT
	 * FlightID FROM FLIGHT WHERE isBooked = false AND DeparturePlace = Seoul AND
	 * ArrivalPlace = US AND DepartureDateTime = 01/01 10:00;
	 * 
	 * �ϸ� ������? �׸��� ReservationModel���� �� FlightID�� �����ϰڽ��ϴ� �ϸ� ���Ű� �����°̴ϴ� Flight�� 1ȸ��.
	 * �� �ѹ� �� Ƽ���� �ٽ� �� �� ���� ������ isBooked �ʵ带 �־����ϴ�
	 * 
	 */

	private String plane_num;
	private String company;
	private String departure;
	private String arrival;
	private String departure_date;
	private int departure_time;
	private String seat_total;
	
	public String getPlane_num() {
		return plane_num;
	}
	public void setPlane_num(String plane_num) {
		this.plane_num = plane_num;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getDeparture() {
		return departure;
	}
	public void setDeparture(String departure) {
		this.departure = departure;
	}
	public String getArrival() {
		return arrival;
	}
	public void setArrival(String arrival) {
		this.arrival = arrival;
	}
	public String getDeparture_date() {
		return departure_date;
	}
	public void setDeparture_date(String departure_date) {
		this.departure_date = departure_date;
	}
	public int getDeparture_time() {
		return departure_time;
	}
	public void setDeparture_time(int departure_time) {
		this.departure_time = departure_time;
	}
	public String getSeat_total() {
		return seat_total;
	}
	public void setSeat_total(String seat_total) {
		this.seat_total = seat_total;
	}
	
	


}
