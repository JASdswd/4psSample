package beans;

public class Beanslistson {

	public String reason_name=null;
	public int month;
	public int year;
	public String household_id=null;
	
	public Beanslistson(){
		super();
	}
	public Beanslistson(String household_id,int month,int year,String reason_name){
		this.household_id=household_id;
		this.month=month;
		this.year=year;
		this.reason_name=reason_name;
	}
	public String getReason_name() {
		return reason_name;
	}
	public void setReason_name(String reason_name) {
		this.reason_name = reason_name;
	}
	public int getMonth() {
		return month;
	}
	public void setMonth(int month) {
		this.month = month;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public String getHousehold_id() {
		return household_id;
	}
	public void setHousehold_id(String household_id) {
		this.household_id = household_id;
	}
	
}
