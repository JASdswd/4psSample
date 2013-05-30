package beans;



public class BeansAdd {

	public String household_id;
	public String month;
	public int year;
	public String day;
	public String time;
	public float amount;
	public int receive;
	public String reason=null;
	public int reason_id;
	public String comment=null;
	public int sub = 0;
	public String munLink_name = null;

	public BeansAdd(String household_id, String month,String day,String time,float amount,int receive,String comment, int sub, String munLink_name) {
		super();
		this.household_id = household_id;
		this.month = month;
		/*this.year = year;*/
		this.day=day;
		this.time=time;
		this.amount=amount;
		this.receive=receive;
		this.comment=comment;
		this.sub = sub;
		this.munLink_name = munLink_name;
	}
	
	public BeansAdd(int reason_id,String household_id,String reason,String month,int year){
		this.reason_id=reason_id;
		this.household_id = household_id;
		this.reason = reason;
		this.month=month;
		this.year=year;
	}
	public BeansAdd(String household_id,String month,int year,String day,String time,float amount,int receive,String reason){
		this.household_id = household_id;
		this.month = month;
		this.year = year;
		this.day=day;
		this.time=time;
		this.amount=amount;
		this.receive=receive;
		this.reason = reason;
	}

	public String getMunLink_name() {
		return munLink_name;
	}

	public void setMunLink_name(String munLink_name) {
		this.munLink_name = munLink_name;
	}
	public String getHousehold_id() {
		return household_id;
	}

	public void setHousehold_id(String household_id) {
		this.household_id = household_id;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public int getReceive() {
		return receive;
	}

	public void setReceive(int receive) {
		this.receive = receive;
	}
	public float getAmount() {
		return amount;
	}
	public void setAmount(float amount) {
		this.amount = amount;
	}
	public int getReason_id() {
		return reason_id;
	}
	public void setReason_id(int reason_id) {
		this.reason_id = reason_id;
	}

	public String getDay() {
		return day;
	}

	public int getSub() {
		return sub;
	}

	public void setSub(int sub) {
		this.sub = sub;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
	
	
}
