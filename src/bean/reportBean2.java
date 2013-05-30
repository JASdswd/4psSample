package bean;

public class reportBean2 {

	private String household_id;
	private String head_name;
	private String bday;
	private String brgy;
	private String municipality;
	private String date_coverage;
	private String date_receive;
	private String time_receive;
	private String amount_receive;
	private float total_release;
	private int household_member_id;
	private String hmember_id;
	private String student_name;
	private int mun_id;
	private int brgy_id;
	private int total_mun;
	private int total_brgy;	
	private String mun_name;
	private String brgy_name;
	private String philhealth_id;
	private String name;
	private int age;
	private String gender;
	private int pregnant;
	private int attending_school;
	private String purok;
	private String street;
	private int status;
	//---------------------------------------------- constructors ---------------------------------------//
	

	public reportBean2(){
		
	}
	public reportBean2(String household_id, String head_name){
		this.household_id = household_id;
		this.head_name = head_name;
	}
	
	public reportBean2(int mun_id,String municipality, int total_mun){
		super();
		this.mun_id = mun_id;
		this.municipality = municipality;
		this.total_mun = total_mun;
	}
	

	public reportBean2(String mun_name, String brgy_name, int total){
		this.mun_name = mun_name;
		this.brgy_name = brgy_name;
		this.total_brgy = total;
	}
	
	//------------------------------------- setters ---------------------------------------------//
	
	public void setHmember_id(String hmember_id) {
		this.hmember_id = hmember_id;
	}
	
	public void setTotal_mun(int total_mun) {
		this.total_mun = total_mun;
	}

	public void setTotal_brgy(int total_brgy) {
		this.total_brgy = total_brgy;
	}
	
	public void setPhilhealth_id(String philhealth_id) {
		this.philhealth_id = philhealth_id;
	}

	public void setTotal_release(float total_release) {
		this.total_release = total_release;
	}

	public void setHousehold_id(String household_id) {
		this.household_id = household_id;
	}
	public void setHead_name(String head_name) {
		this.head_name = head_name;
	}
	public void setHousehold_member_id(int household_member_id) {
		this.household_member_id = household_member_id;
	}
	public void setStudent_name(String student_name) {
		this.student_name = student_name;
	}
	
	public void setMun_id(int mun_id) {
		this.mun_id = mun_id;
	}

	public void setBrgy_id(int brgy_id) {
		this.brgy_id = brgy_id;
	}

	public void setMun_name(String mun_name) {
		this.mun_name = mun_name;
	}

	public void setBrgy_name(String brgy_name) {
		this.brgy_name = brgy_name;
	}

	public void setMunicipality(String municipality) {
		this.municipality = municipality;
	}
	
	public void setDate_coverage(String date_coverage) {
		this.date_coverage = date_coverage;
	}
	
	public void setDate_receive(String date_receive) {
		this.date_receive = date_receive;
	}
	
	public void setTime_receive(String time_receive) {
		this.time_receive = time_receive;
	}
	
	public void setAmount_receive(String amount_receive) {
		this.amount_receive = amount_receive;
	}
	
	public void setBday(String bday) {
		this.bday = bday;
	}
	
	public void setBrgy(String brgy) {
		this.brgy = brgy;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public void setPregnant(int pregnant) {
		this.pregnant = pregnant;
	}

	public void setAttending_school(int attending_school) {
		this.attending_school = attending_school;
	}

	public void setPurok(String purok) {
		this.purok = purok;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	//--------------------------------- getters -----------------------------------------------//
	public int getStatus() {
		return status;
	}
	
	public String getHmember_id() {
		return hmember_id;
	}

	
	public String getName() {
		return name;
	}

	public int getAge() {
		return age;
	}

	public String getGender() {
		return gender;
	}

	public int getPregnant() {
		return pregnant;
	}

	public int getAttending_school() {
		return attending_school;
	}

	public String getPurok() {
		return purok;
	}

	public String getStreet() {
		return street;
	}

	public String getPhilhealth_id() {
		return philhealth_id;
	}
	
	public int getTotal_mun() {
		return total_mun;
	}
	
	public int getTotal_brgy() {
		return total_brgy;
	}
	
	public float getTotal_release() {
		return total_release;
	}
	
	public String getBday() {
		return bday;
	}

	public String getBrgy() {
		return brgy;
	}

	public String getMunicipality() {
		return municipality;
	}

	public String getDate_coverage() {
		return date_coverage;
	}

	public String getDate_receive() {
		return date_receive;
	}
	
	public String getTime_receive() {
		return time_receive;
	}

	public String getAmount_receive() {
		return amount_receive;
	}

	public int getMun_id() {
		return mun_id;
	}

	public int getBrgy_id() {
		return brgy_id;
	}

	public String getMun_name() {
		return mun_name;
	}

	public String getBrgy_name() {
		return brgy_name;
	}

	public String getStudent_name() {
		return student_name;
	}
	public String getHousehold_id() {
		return household_id;
	}
	public String getHead_name() {
		return head_name;
	}
	public int getHousehold_member_id() {
		return household_member_id;
	}
	
	
	
	
	
}