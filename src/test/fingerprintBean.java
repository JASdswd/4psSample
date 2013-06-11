package test;

public class fingerprintBean {

	private String houseold_id;
	private byte[] fingerprint;
	private String date_recorded;
	private String time_recorded;
	
	///////--------------------------------- GETTERS AND SETTERS ---------------------------------------------//
	
	public String getHouseold_id() {
		return houseold_id;
	}
	public void setHouseold_id(String houseold_id) {
		this.houseold_id = houseold_id;
	}
	public byte[] getFingerprint() {
		return fingerprint;
	}
	public void setFingerprint(byte[] fingerprint) {
		this.fingerprint = fingerprint;
	}
	public String getDate_recorded() {
		return date_recorded;
	}
	public void setDate_recorded(String date_recorded) {
		this.date_recorded = date_recorded;
	}
	public String getTime_recorded() {
		return time_recorded;
	}
	public void setTime_recorded(String time_recorded) {
		this.time_recorded = time_recorded;
	}
	
	
	////------------------------------------- Constructors ----------------------------------------------//
	
	public fingerprintBean(String household_id, byte[] fingerprint, String date_recorded, String time_recorded){
		
		this.houseold_id = household_id;
		this.fingerprint = fingerprint;
		this.date_recorded = date_recorded;
		this.time_recorded = time_recorded;
		
	}
	
	
	
}
