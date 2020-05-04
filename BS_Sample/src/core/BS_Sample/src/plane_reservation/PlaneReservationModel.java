package plane_reservation;

public class PlaneReservationModel {
	private String plane_reservation;
	private String id;
	private String plane_num_1;
	private String plane_num_2;
	private String grade;
	private String seat_count;
	private int plane_total_price;
	
	public String getPlane_reservation() {
		return plane_reservation;
	}
	public void setPlane_reservation(String plane_reservation) {
		this.plane_reservation = plane_reservation;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPlane_num_1() {
		return plane_num_1;
	}
	public void setPlane_num_1(String plane_num_1) {
		this.plane_num_1 = plane_num_1;
	}
	public String getPlane_num_2() {
		return plane_num_2;
	}
	public void setPlane_num_2(String plane_num_2) {
		this.plane_num_2 = plane_num_2;
	}
	public String getGrade() {
		return grade;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}
	public String getSeat_count() {
		return seat_count;
	}
	public void setSeat_count(String seat_count) {
		this.seat_count = seat_count;
	}
	public int getPlane_total_price() {
		return plane_total_price;
	}
	public void setPlane_total_price(int plane_total_price) {
		this.plane_total_price = plane_total_price;
	}
	
}
