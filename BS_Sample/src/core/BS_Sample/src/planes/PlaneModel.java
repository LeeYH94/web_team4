package planes;

public class PlaneModel {

	// FlightModel
	// 근데 개념상으로는 티켓에 가깝습니다

	/*
	 * """ Field """
	 * 
	 * FlightID (PK - 그냥 고유 번호. FK를 위한 필드일 뿐, 의미는 없습니다) DeparturePlace - 출발 지점
	 * ArrivalPlace - 도착 지점 DepartureDateTime - 출발 날짜/시간 ArrivalDateTime - 도착 날짜/시간
	 * (굳이 필요 없을 수도 있습니다만, 프로그램에서 활용할 생각이 없으시면 지우세요) Company - 항공사 SeatOption - 좌석
	 * 종류. 이코노미, 비지니스 이런 것 Price - 티켓 가격 isBooked - 예약 중인지 아닌지 체크
	 * 
	 * 
	 * """ 사용 예시 """
	 * 
	 * 한국에서 1월 1일 10시에 출발하는 미국행 비행기를 찾고 싶다? (그냥 대충 알아 보시면 됩니다... 문법 따지지 귀찮아영) SELECT
	 * FlightID FROM FLIGHT WHERE isBooked = false AND DeparturePlace = Seoul AND
	 * ArrivalPlace = US AND DepartureDateTime = 01/01 10:00;
	 * 
	 * 하면 끝나죠? 그리고 ReservationModel에서 이 FlightID를 예매하겠습니다 하면 예매가 끝나는겁니다 Flight는 1회성.
	 * 즉 한번 쓴 티켓은 다시 쓸 수 없기 때문에 isBooked 필드를 넣었습니다
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
