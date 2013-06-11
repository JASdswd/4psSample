package beans;

public class Brgy {

	private String barangay=null;
	private int brgy_id = 0;
	
	
	public Brgy(int brgy_id,String barangay) {
		super();
		this.brgy_id = brgy_id;
		this.barangay = barangay;
	}
	
	public Brgy(String barangay){
		super();
		this.barangay = barangay;
	}

	public String getBarangay() {
		return barangay;
	}

	public void setBarangay(String barangay) {
		this.barangay = barangay;
	
	}
	public int getBrgy_id() {
		return brgy_id;
	}

	public void setBrgy_id(int brgy_id) {
		this.brgy_id = brgy_id;
	}

	
}
