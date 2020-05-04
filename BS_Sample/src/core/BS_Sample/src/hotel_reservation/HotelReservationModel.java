package hotel_reservation;

public class HotelReservationModel {
	private String id;
	private String hotel_reservation;
	private String hotel_num;
	private String check_in;
	private String check_out;
	private String room_count;
	private int hotel_total_price;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getHotel_reservation() {
		return hotel_reservation;
	}
	public void setHotel_reservation(String hotel_reservation) {
		this.hotel_reservation = hotel_reservation;
	}
	public String getHotel_num() {
		return hotel_num;
	}
	public void setHotel_num(String hotel_num) {
		this.hotel_num = hotel_num;
	}
	public String getCheck_in() {
		return check_in;
	}
	public void setCheck_in(String check_in) {
		this.check_in = check_in;
	}
	public String getCheck_out() {
		return check_out;
	}
	public void setCheck_out(String check_out) {
		this.check_out = check_out;
	}
	public String getRoom_count() {
		return room_count;
	}
	public void setRoom_count(String room_count) {
		this.room_count = room_count;
	}
	public int getHotel_total_price() {
		return hotel_total_price;
	}
	public void setHotel_total_price(int hotel_total_price) {
		this.hotel_total_price = hotel_total_price;
	}
	
}
