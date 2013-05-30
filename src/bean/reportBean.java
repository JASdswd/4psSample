package bean;

public class reportBean {
	private String province;
	private String gstatus;
	private String f_position;
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
	private int sub;
	private int team_id;
	//---------------------------------------------- constructors ---------------------------------------//
	private String grscasetype;
	private String grsidoctype;
	private String remarks;
	private String grievance_officer;
	private String set_group;
	private String hh_set;
	private int server_id;

	public reportBean(){
		
	}

	public reportBean(String household_id, String philhealth_id, int household_member_id, String name, int age, String bday, String gender, int pregnant, int attending_school, String street, String purok, int barangay, int municipality, String f_position){
		super();
		this.household_id = household_id;
		this.philhealth_id = philhealth_id;
		this.household_member_id = household_member_id;
		this.name = name;
		this.age = age;
		this.bday = bday;
		this.gender = gender;
		this.pregnant = pregnant;
		this.attending_school = attending_school;
		this.street = street;
		this.purok = purok;
		this.brgy_id = barangay;
		this.mun_id = municipality;
		this.f_position = f_position;
	}
	
	public reportBean(String municipality, String brgy){
		super();
		this.municipality = municipality;
		this.brgy_name = brgy;
	}
	
	public reportBean(String municipality, int total_mun){
		super();
		this.municipality = municipality;
		this.total_mun = total_mun;
	}
	
	public reportBean(int mun_id,String municipality, int total_mun,int total_brgy){
		super();
		this.mun_id = mun_id;
		this.municipality = municipality;
		this.total_mun = total_mun;
		this.total_brgy = total_brgy;
	}
	
	public reportBean(int mun_id,String municipality, int total_mun,String brgy, int total_brgy){
		super();
		this.mun_id = mun_id;
		this.municipality = municipality;
		this.total_mun = total_mun;
		this.brgy_name = brgy;
		this.total_brgy = total_brgy;
	}
	
	public reportBean(int municipality,String mun_name, int total_mun, int brgy,String brgy_name, int total_brgy){
		
		super();
		this.mun_id = municipality;
		this.mun_name = mun_name;
		this.total_mun = total_mun;
		this.brgy_id= brgy;
		this.brgy_name = brgy_name;
		this.total_brgy = total_brgy;
		
	}
	
	public reportBean(int stat, String household_id, String philhealth_id, String household_member_id, String name, int age, String bday, String gender, int pregnant, int attending_school, String street, String purok, int barangay, int municipality){
		super();
		this.status = stat;
		this.household_id = household_id;
		this.philhealth_id = philhealth_id;
		this.hmember_id = household_member_id;
		this.name = name;
		this.age = age;
		this.bday = bday;
		this.gender = gender;
		this.pregnant = pregnant;
		this.attending_school = attending_school;
		this.street = street;
		this.purok = purok;
		this.brgy_id = barangay;
		this.mun_id = municipality;
	}
	
	public reportBean(int stat, String household_id, String philhealth_id, String household_member_id, String name, int age, String bday, int pregnant, int attending_school, String street, String purok, int barangay, int municipality){
		super();
		this.status = stat;
		this.household_id = household_id;
		this.philhealth_id = philhealth_id;
		this.hmember_id = household_member_id;
		this.name = name;
		this.age = age;
		this.bday = bday;
		this.pregnant = pregnant;
		this.attending_school = attending_school;
		this.street = street;
		this.purok = purok;
		this.brgy_id = barangay;
		this.mun_id = municipality;
	}


	public reportBean(int stat, String household_id, String philhealth_id, String household_member_id, String name, int age, String bday, int pregnant, int attending_school, String street, String purok, String barangay, String municipality){
		super();
		this.status = stat;
		this.household_id = household_id;
		this.philhealth_id = philhealth_id;
		this.hmember_id = household_member_id;
		this.name = name;
		this.age = age;
		this.bday = bday;
		this.pregnant = pregnant;
		this.attending_school = attending_school;
		this.street = street;
		this.purok = purok;
		this.brgy_name = barangay;
		this.municipality = municipality;
	}

	public reportBean(String household_id, String head_name,
			int household_member_id) {
		super();
		this.household_id = household_id;
		this.head_name = head_name;
		this.household_member_id = household_member_id;
	}
	
	public reportBean( String household_id,int household_member_id, String student_name) {
		super();
		
		this.household_id = household_id;
		this.household_member_id = household_member_id;
		this.student_name = student_name;
		
	}
	
	public reportBean(int mun_id, int brgy_id, String brgy_name) {
		super();
		this.mun_id = mun_id;
		this.brgy_id = brgy_id;
		this.brgy_name = brgy_name;
	}

	public reportBean(int mun_id, String mun_name) {
		super();
		this.mun_id = mun_id;
		this.mun_name = mun_name;
	}
	
	public reportBean(int mun_id, int brgy_id) {
		super();
		this.mun_id = mun_id;
		this.brgy_id = brgy_id;
	}

	
	public reportBean(String mun_name, String brgy_name, String household_id,
			String head_name, int team_id, int server_id, String hh_set, String set_group) {
		// TODO Auto-generated constructor stub
		
		this.mun_name = mun_name;
		this.brgy_name = brgy_name;
		this.household_id = household_id;
		this.head_name = head_name;
		this.team_id = team_id;
		this.server_id = server_id;
		this.hh_set = hh_set;
		this.set_group = set_group;
		
	}

	
	public reportBean(String household_id, String head_name, String birthday,
			String barangay, String municipality, String date_coverage, String date_receive,
			String time_receive, String amount_receive, String philhealth_id,int sub) {

		super();
		this.household_id = household_id;
		this.head_name = head_name;
		this.bday = birthday;
		this.brgy = barangay;
		this.municipality = municipality;
		this.date_coverage = date_coverage;
		this.date_receive = date_receive;
		this.time_receive = time_receive;
		this.amount_receive = amount_receive;
		this.philhealth_id = philhealth_id;
		this.sub = sub;
	}
	
	public reportBean(int stat, String household_id, String philhealth_id, String household_member_id, String name, int age, String bday, String gender, int pregnant, int attending_school, String street, String purok, int barangay, int municipality, String f_position,String hh_set,String set_group){
		super();
		this.status = stat;
		this.household_id = household_id;
		this.philhealth_id = philhealth_id;
		this.hmember_id = household_member_id;
		this.name = name;
		this.age = age;
		this.bday = bday;
		this.gender = gender;
		this.pregnant = pregnant;
		this.attending_school = attending_school;
		this.street = street;
		this.purok = purok;
		this.brgy_id = barangay;
		this.mun_id = municipality;
		this.f_position = f_position;
		this.hh_set = hh_set;
		this.set_group = set_group;
	}
	public reportBean(int stat, String household_id, String philhealth_id, String household_member_id, String name, int age, String bday, String gender, int pregnant, int attending_school, String street, String purok, int barangay, int municipality, String f_position){
		super();
		this.status = stat;
		this.household_id = household_id;
		this.philhealth_id = philhealth_id;
		this.hmember_id = household_member_id;
		this.name = name;
		this.age = age;
		this.bday = bday;
		this.gender = gender;
		this.pregnant = pregnant;
		this.attending_school = attending_school;
		this.street = street;
		this.purok = purok;
		this.brgy_id = barangay;
		this.mun_id = municipality;
		this.f_position = f_position;
		
	}
	public reportBean(int stat, String household_id, String philhealth_id, String household_member_id, String name, int age, String bday, String gender, int pregnant, int attending_school, String street, String purok, String barangay, String municipality, String f_position){
		super();
		this.status = stat;
		this.household_id = household_id;
		this.philhealth_id = philhealth_id;
		this.hmember_id = household_member_id;
		this.name = name;
		this.age = age;
		this.bday = bday;
		this.gender = gender;
		this.pregnant = pregnant;
		this.attending_school = attending_school;
		this.street = street;
		this.purok = purok;
		this.brgy_name = barangay;
		this.municipality = municipality;
		this.f_position = f_position;
	}
	
	public reportBean(String household_id, String head_name, 
			String barangay, String municipality, String hh_set, String set_group) {
		super();
		this.household_id = household_id;
		this.head_name = head_name;
		this.brgy = barangay;
		this.municipality = municipality;
		this.hh_set = hh_set;
		this.set_group = set_group;
	}
	
	public reportBean(String mun_name, String brgy_name, String date_recorded, int team_id,
			String household_id, String head_name, String grscasetype, String grsidoctype,
			String remarks, String grievance_officer) {
		// TODO Auto-generated constructor stub
		
		this.mun_name = mun_name;
		this.brgy_name = brgy_name;
		this.date_coverage = date_recorded;
		this.team_id = team_id;
		this.household_id = household_id;
		this.head_name = head_name;
		this.grscasetype = grscasetype;
		this.grsidoctype = grsidoctype;
		this.remarks = remarks;
		this.grievance_officer = grievance_officer;
		
	}
	
	//------------------------------------- setters ---------------------------------------------//

	public reportBean(int mun_id, String grscases, int count) {
		// TODO Auto-generated constructor stub
		this.mun_id =mun_id;
		this.grscasetype = grscases;
		this.total_mun = count;
	}

	public int getTeam_id() {
		return team_id;
	}

	public void setTeam_id(int team_id) {
		this.team_id = team_id;
	}

	public String getGrscasetype() {
		return grscasetype;
	}

	public void setGrscasetype(String grscasetype) {
		this.grscasetype = grscasetype;
	}

	public String getGrsidoctype() {
		return grsidoctype;
	}

	public void setGrsidoctype(String grsidoctype) {
		this.grsidoctype = grsidoctype;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getGrievance_officer() {
		return grievance_officer;
	}

	public void setGrievance_officer(String grievance_officer) {
		this.grievance_officer = grievance_officer;
	}

	//------------------//
	
	public void setF_position(String f_position) {
		this.f_position = f_position;
	}
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
	public String getF_position() {
		return f_position;
	}
	
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
	

	public int getSub() {
		return sub;
	}

	public void setSub(int sub) {
		this.sub = sub;
	}

	public String getSet_group() {
		return set_group;
	}

	public void setSet_group(String set_group) {
		this.set_group = set_group;
	}

	public String getHh_set() {
		return hh_set;
	}

	public void setHh_set(String hh_set) {
		this.hh_set = hh_set;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getGstatus() {
		return gstatus;
	}

	public void setGstatus(String gstatus) {
		this.gstatus = gstatus;
	}
	public int getServer_id() {
		return server_id;
	}

	public void setServer_id(int server_id) {
		this.server_id = server_id;
	}

}