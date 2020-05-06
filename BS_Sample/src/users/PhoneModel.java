package users;

public class PhoneModel {

public PhoneModel() {}
   
   private String RESERVATION;
   private String ID;
   private String PLANE_NUM1;
   private String PLANE_NUM2;
   private String GRADE;
   private String SEAT_COUNT;
   private int PLANE_TOTAL_PRICE;
   
   
   public String getPLANE_RESERVATION() {
      return RESERVATION;
   }
   public void setPLANE_RESERVATION(String pLANE_RESERVATION) {
      RESERVATION = pLANE_RESERVATION;
   }
   public String getID() {
      return ID;
   }
   public void setID(String iD) {
      ID = iD;
   }
   public String getPLANE_NUM1() {
      return PLANE_NUM1;
   }
   public void setPLANE_NUM1(String pLANE_NUM1) {
      PLANE_NUM1 = pLANE_NUM1;
   }
   public String getPLANE_NUM2() {
      return PLANE_NUM2;
   }
   public void setPLANE_NUM2(String pLANE_NUM2) {
      PLANE_NUM2 = pLANE_NUM2;
   }
   public String getGRADE() {
      return GRADE;
   }
   public void setGRADE(String gRADE) {
      GRADE = gRADE;
   }
   public String getSEAT_COUNT() {
      return SEAT_COUNT;
   }
   public void setSEAT_COUNT(String sEAT_COUNT) {
      SEAT_COUNT = sEAT_COUNT;
   }
   public int getPLANE_TOTAL_PRICE() {
      return PLANE_TOTAL_PRICE;
   }
   public void setPLANE_TOTAL_PRICE(int i) {
      PLANE_TOTAL_PRICE = i;
   }
   
   
}