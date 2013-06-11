package bean;

public class transactionBean {

	



	private String pl_fname = "";
	private String pl_lname = "";
	private String household_id = "";
	private String philhealth_id = "";
	private int householdMemberID = 0;
	private byte[] photo_head;
	private byte[] fingerprint;
	
	private String head_name = "";
	private int h_age = 0;
	private String h_birthday = "";
	private String h_gender = "";
	private boolean h_pregnant = false;
	private boolean h_attendingSchool = false;
	private String f_position = "";
	
	private String ws_name = "";
	private int ws_age = 0;
	private int ws_householdMemberID = 0;
	private String ws_birthday = "";
	private boolean ws_pregnant = false;
	private boolean ws_attendingSchool = false;
	
	private String sd_name = "";
	private int sd_age = 0;
	private int sd_householdMemberID = 0;
	private String sd_birthday = "";
	private boolean sd_pregnant = false;
	private boolean sd_attendingSchool = false;
	
	private String gg_name = "";
	private int gg_age = 0;
	private int gg_householdMemberID = 0;
	private String gg_birthday = "";
	private boolean gg_pregnant = false;
	private boolean gg_attendingSchool = false;
	
	private String street = "";
	private String purok = "";
	private String barangay ="";
	private String municipality = "";
	private String dateoftransaction = "";
	private int sub = 0;

	private int mun_id = 0;
	private int brgy_id = 0;
	private int munlinkUser_id = 0;
	private String brgy_name = "";
	private String username = "";
	private String gender = "";
	private String contact = "";
	private String user_email = "";
	
	private int serverId = 0;
	private String serverName = "";
	private String serverDescription = "";
	private int count = 0;
	private String date_recorded = "";
	private int id = 0;
	private int user_role = 0;
	//------- updating the household set --------------------
	private int hh_set;
	private String set_group;
	private int status;

	
	

	public transactionBean(){
	
	}
	
	
	
	public transactionBean(String pl_fname, String pl_lname) {
		super();
		this.pl_fname = pl_fname;
		this.pl_lname = pl_lname;
	}
	public transactionBean(String pl_fname, String pl_lname,int user_id) {
		this.id = user_id;
		this.pl_fname = pl_fname;
		this.pl_lname = pl_lname;
	}



	public transactionBean(String municipality, int mun_id) {
		super();
		this.municipality = municipality;
		this.mun_id = mun_id;
	}
	
	public transactionBean(String barangay) {
		super();
		this.barangay = barangay;
	}

	public transactionBean(String fname, String lname,byte[] photo_head,byte[] fingerprint) {
		
		this.pl_fname = fname;
		this.pl_lname = lname;
		this.photo_head = photo_head;
		this.fingerprint = fingerprint;
		
	}
	public transactionBean(String fname,String lname,String u_name,String gender,String contact,String email,byte[] photo_head,byte[] fingerprint){
		this.pl_fname = fname;
		this.pl_lname = lname;
		this.username = u_name;
		this.gender = gender;
		this.contact = contact;
		this.user_email = email;
		this.photo_head = photo_head;
		this.fingerprint = fingerprint;
	}
	
	public transactionBean(int sd_householdMemberID) {
		super();
		this.sd_householdMemberID = sd_householdMemberID;
	}




	public transactionBean(
			String household_id, String philhealth_id, int householdMemberID, String head_name, int h_age, String h_birthday, String h_gender,
			boolean h_pregnant, boolean h_attendingSchool, String street,
			String purok, int barangay, int municipality, String f_position) {
		super();
	
		//this.fingerprint = fingerprint;
		this.household_id = household_id;
		this.philhealth_id = philhealth_id;
		this.householdMemberID = householdMemberID;
		this.head_name = head_name;
		this.h_age = h_age;
		this.h_birthday = h_birthday;
		this.h_gender = h_gender;
		this.h_pregnant = h_pregnant;
		this.h_attendingSchool = h_attendingSchool;
		this.street = street;
		this.purok = purok;
		this.brgy_id = barangay;
		this.mun_id = municipality;
		this.f_position = f_position;
	}
	public transactionBean(
			String household_id, String philhealth_id, int householdMemberID, String head_name, int h_age, String h_birthday, String h_gender,
			boolean h_pregnant, boolean h_attendingSchool, String street,
			String purok, String barangay, String municipality, int status) {
		super();
	
		//this.fingerprint = fingerprint;
		this.household_id = household_id;
		this.philhealth_id = philhealth_id;
		this.householdMemberID = householdMemberID;
		this.head_name = head_name;
		this.h_age = h_age;
		this.h_birthday = h_birthday;
		this.h_gender = h_gender;
		this.h_pregnant = h_pregnant;
		this.h_attendingSchool = h_attendingSchool;
		this.street = street;
		this.purok = purok;
		this.barangay = barangay;
		this.municipality = municipality;
		this.status = status;
	}
	
	public transactionBean(int sub, String date_of_transaction) {
		super();
		this.sub = sub;
		this.dateoftransaction = date_of_transaction;
	}


	public transactionBean(String household_id,String ws_name, int ws_householdMemberID,
			 int ws_age, String ws_birthday,
			boolean ws_pregnant, boolean ws_attendingSchool, String f_position, int status, String gender) {
		super();
		this.household_id = household_id;
		this.ws_name = ws_name;
		this.ws_householdMemberID = ws_householdMemberID;
		this.ws_age = ws_age;
		this.ws_birthday = ws_birthday;
		this.ws_pregnant = ws_pregnant;
		this.ws_attendingSchool = ws_attendingSchool;
		this.f_position = f_position;
		this.status = status;
		this.gender = gender;
	}

	
	

	public transactionBean(String household_id, int sd_householdMemberID,
			String sd_name, int sd_age, String sd_birthday,
			boolean sd_pregnant, boolean sd_attendingSchool) {
		super();
		this.household_id = household_id;
		this.sd_householdMemberID = sd_householdMemberID;
		this.sd_name = sd_name;
		this.sd_age = sd_age;
		this.sd_birthday = sd_birthday;
		this.sd_pregnant = sd_pregnant;
		this.sd_attendingSchool = sd_attendingSchool;
	}
	
	
	



	public transactionBean(byte[] photo_head) {
		super();
		this.photo_head = photo_head;
	}
	



	public transactionBean(byte[] fingerprint, String household_id) {
		super();
		this.fingerprint = fingerprint;
		this.household_id = household_id;
	}


	public transactionBean(String household_id, int gg_householdMemberID,int gg_age,
			String gg_name,  String gg_birthday,
			boolean gg_pregnant, boolean gg_attendingSchool) {
		super();
		this.household_id = household_id;
		this.gg_householdMemberID = gg_householdMemberID;
		this.gg_age = gg_age;
		this.gg_name = gg_name;
		this.gg_birthday = gg_birthday;
		this.gg_pregnant = gg_pregnant;
		this.gg_attendingSchool = gg_attendingSchool;
	}
	/*New bean for municipal accounts*/
	public transactionBean(String fname, String lname,String username,String gender,String email,String contact,byte[] photo_head,byte[] fingerprint, int mun, String mun_name) {
		
		
		this.pl_fname = fname;
		this.pl_lname = lname;
		this.username = username;
		this.gender = gender;
		this.user_email = email;
		this.contact = contact;
		this.photo_head = photo_head;
		this.fingerprint = fingerprint;
		this.mun_id = mun;
		this.municipality = mun_name;
		
	}
	public transactionBean(int id,String fname, String lname,String username,String gender,String email,String contact,byte[] photo_head,byte[] fingerprint, int mun, String mun_name) {
		
		this.id = id;
		this.pl_fname = fname;
		this.pl_lname = lname;
		this.username = username;
		this.gender = gender;
		this.user_email = email;
		this.contact = contact;
		this.photo_head = photo_head;
		this.fingerprint = fingerprint;
		this.mun_id = mun;
		this.municipality = mun_name;
		
	}
	public transactionBean(int id,String fname, String lname,byte[] photo_head,byte[] fingerprint, int mun, String mun_name) {
			
			this.id = id;
			this.pl_fname = fname;
			this.pl_lname = lname;
			this.photo_head = photo_head;
			this.fingerprint = fingerprint;
			this.mun_id = mun;
			this.municipality = mun_name;
	}
	public transactionBean(int user_role,int id,String fname, String lname,byte[] photo_head,byte[] fingerprint, int mun, String mun_name) {
		
		this.user_role = user_role;
		this.id = id;
		this.pl_fname = fname;
		this.pl_lname = lname;
		this.photo_head = photo_head;
		this.fingerprint = fingerprint;
		this.mun_id = mun;
		this.municipality = mun_name;
}
	public transactionBean(String fname, String lname,byte[] photo_head,byte[] fingerprint, int mun, String mun_name) {
		
		this.pl_fname = fname;
		this.pl_lname = lname;
		this.photo_head = photo_head;
		this.fingerprint = fingerprint;
		this.mun_id = mun;
		this.municipality = mun_name;
}

	public transactionBean(int serverId, String serverName,
			String serverDescription) {
		super();
		this.serverId = serverId;
		this.serverName = serverName;
		this.serverDescription = serverDescription;
	}


	public transactionBean(String household_id, int sd_householdMemberID,
			String sd_name, int sd_age, String sd_birthday,
			boolean sd_pregnant, boolean sd_attendingSchool, String f_position, int status, String gender) {
		super();
		this.household_id = household_id;
		this.sd_householdMemberID = sd_householdMemberID;
		this.sd_name = sd_name;
		this.sd_age = sd_age;
		this.sd_birthday = sd_birthday;
		this.sd_pregnant = sd_pregnant;
		this.sd_attendingSchool = sd_attendingSchool;
		this.f_position = f_position;
		this.status = status;
		this.gender = gender;
	}
	public transactionBean(String household_id, int gg_householdMemberID,int gg_age,
			String gg_name,  String gg_birthday,
			boolean gg_pregnant, boolean gg_attendingSchool, int status, String gender) {
		super();
		this.household_id = household_id;
		this.gg_householdMemberID = gg_householdMemberID;
		this.gg_age = gg_age;
		this.gg_name = gg_name;
		this.gg_birthday = gg_birthday;
		this.gg_pregnant = gg_pregnant;
		this.gg_attendingSchool = gg_attendingSchool;
		this.status = status;
		this.gender = gender;
	}
	public transactionBean(String fname,String lname,String date,String mun_name,int ctr){
		this.pl_fname = fname;
		this.pl_lname= lname;
		this.date_recorded = date;
		this.municipality = mun_name;
		this.count = ctr;
	}
	/*public transactionBean(String fname, String lname,byte[] photo_head,byte[] fingerprint, int mun, String mun_name, int brgy_id, String brgy_name) {
		
		this.pl_fname = fname;
		this.pl_lname = lname;
		this.photo_head = photo_head;
		this.fingerprint = fingerprint;
		this.mun_id = mun;
		this.municipality = mun_name;
		this.brgy_id = brgy_id;
		this.brgy_name = brgy_name;
	}
*/
	public String getBrgy_name() {
		return brgy_name;
	}


	public void setBrgy_name(String brgy_name) {
		this.brgy_name = brgy_name;
	}

	

	public String getHousehold_id() {
		return household_id;
	}


	public transactionBean(byte[] fingerprint, int munlinkUser_id) {
		super();
		this.fingerprint = fingerprint;
		this.munlinkUser_id = munlinkUser_id;
	}

	






	public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}



	public int getUser_role() {
		return user_role;
	}



	public void setUser_role(int user_role) {
		this.user_role = user_role;
	}



	public int getMunlinkUser_id() {
		return munlinkUser_id;
	}


	public void setMunlinkUser_id(int munlinkUser_id) {
		this.munlinkUser_id = munlinkUser_id;
	}


	public void setHousehold_id(String household_id) {
		this.household_id = household_id;
	}


	public int getHouseholdMemberID() {
		return householdMemberID;
	}


	public void setHouseholdMemberID(int householdMemberID) {
		this.householdMemberID = householdMemberID;
	}


	public byte[] getPhoto_head() {
		return photo_head;
	}


	public void setPhoto_head(byte[] photo_head) {
		this.photo_head = photo_head;
	}


	public byte[] getFingerprint() {
		return fingerprint;
	}


	public void setFingerprint(byte[] fingerprint) {
		this.fingerprint = fingerprint;
	}


	public String getHead_name() {
		return head_name;
	}


	public void setHead_name(String head_name) {
		this.head_name = head_name;
	}

	public int getBrgy_id() {
		return brgy_id;
	}


	public void setBrgy_id(int brgy_id) {
		this.brgy_id = brgy_id;
	}

	public int getH_age() {
		return h_age;
	}


	public void setH_age(int h_age) {
		this.h_age = h_age;
	}


	public String getH_birthday() {
		return h_birthday;
	}


	public void setH_birthday(String h_birthday) {
		this.h_birthday = h_birthday;
	}


	public boolean isH_pregnant() {
		return h_pregnant;
	}


	public void setH_pregnant(boolean h_pregnant) {
		this.h_pregnant = h_pregnant;
	}


	public boolean isH_attendingSchool() {
		return h_attendingSchool;
	}


	public void setH_attendingSchool(boolean h_attendingSchool) {
		this.h_attendingSchool = h_attendingSchool;
	}


	public String getWs_name() {
		return ws_name;
	}


	public void setWs_name(String ws_name) {
		this.ws_name = ws_name;
	}


	public int getWs_age() {
		return ws_age;
	}


	public void setWs_age(int ws_age) {
		this.ws_age = ws_age;
	}


	public String getWs_birthday() {
		return ws_birthday;
	}


	public void setWs_birthday(String ws_birthday) {
		this.ws_birthday = ws_birthday;
	}


	public boolean isWs_pregnant() {
		return ws_pregnant;
	}


	public void setWs_pregnant(boolean ws_pregnant) {
		this.ws_pregnant = ws_pregnant;
	}


	public boolean isWs_attendingSchool() {
		return ws_attendingSchool;
	}


	public void setWs_attendingSchool(boolean ws_attendingSchool) {
		this.ws_attendingSchool = ws_attendingSchool;
	}


	public String getSd_name() {
		return sd_name;
	}


	public void setSd_name(String sd_name) {
		this.sd_name = sd_name;
	}


	public int getSd_age() {
		return sd_age;
	}


	public void setSd_age(int sd_age) {
		this.sd_age = sd_age;
	}


	public String getSd_birthday() {
		return sd_birthday;
	}


	public int getServerId() {
		return serverId;
	}



	public void setServerId(int serverId) {
		this.serverId = serverId;
	}



	public String getServerName() {
		return serverName;
	}



	public void setServerName(String serverName) {
		this.serverName = serverName;
	}



	public String getServerDescription() {
		return serverDescription;
	}



	public void setServerDescription(String serverDescription) {
		this.serverDescription = serverDescription;
	}



	public void setSd_birthday(String sd_birthday) {
		this.sd_birthday = sd_birthday;
	}


	public boolean isSd_pregnant() {
		return sd_pregnant;
	}


	public void setSd_pregnant(boolean sd_pregnant) {
		this.sd_pregnant = sd_pregnant;
	}


	public boolean isSd_attendingSchool() {
		return sd_attendingSchool;
	}


	public void setSd_attendingSchool(boolean sd_attendingSchool) {
		this.sd_attendingSchool = sd_attendingSchool;
	}


	public String getGg_name() {
		return gg_name;
	}


	public void setGg_name(String gg_name) {
		this.gg_name = gg_name;
	}


	public int getGg_age() {
		return gg_age;
	}


	public void setGg_age(int gg_age) {
		this.gg_age = gg_age;
	}


	public String getGg_birthday() {
		return gg_birthday;
	}


	public void setGg_birthday(String gg_birthday) {
		this.gg_birthday = gg_birthday;
	}


	public boolean isGg_pregnant() {
		return gg_pregnant;
	}


	public void setGg_pregnant(boolean gg_pregnant) {
		this.gg_pregnant = gg_pregnant;
	}


	public boolean isGg_attendingSchool() {
		return gg_attendingSchool;
	}


	public void setGg_attendingSchool(boolean gg_attendingSchool) {
		this.gg_attendingSchool = gg_attendingSchool;
	}


	public String getStreet() {
		return street;
	}


	public void setStreet(String street) {
		this.street = street;
	}


	public String getPurok() {
		return purok;
	}


	public void setPurok(String purok) {
		this.purok = purok;
	}


	public String getBarangay() {
		return barangay;
	}


	public void setBarangay(String barangay) {
		this.barangay = barangay;
	}


	public String getMunicipality() {
		return municipality;
	}


	public void setMunicipality(String municipality) {
		this.municipality = municipality;
	}




	public int getWs_householdMemberID() {
		return ws_householdMemberID;
	}




	public void setWs_householdMemberID(int ws_householdMemberID) {
		this.ws_householdMemberID = ws_householdMemberID;
	}




	public int getSd_householdMemberID() {
		return sd_householdMemberID;
	}




	public void setSd_householdMemberID(int sd_householdMemberID) {
		this.sd_householdMemberID = sd_householdMemberID;
	}




	public int getGg_householdMemberID() {
		return gg_householdMemberID;
	}
	public String getDateoftransaction() {
		return dateoftransaction;
	}


	public void setDateoftransaction(String dateoftransaction) {
		this.dateoftransaction = dateoftransaction;
	}


	public int getSub() {
		return sub;
	}


	public void setSub(int sub) {
		this.sub = sub;
	}





	public void setGg_householdMemberID(int gg_householdMemberID) {
		this.gg_householdMemberID = gg_householdMemberID;
	}




	public int getMun_id() {
		return mun_id;
	}




	public void setMun_id(int mun_id) {
		this.mun_id = mun_id;
	}


	public String getPhilhealth_id() {
		return philhealth_id;
	}


	public void setPhilhealth_id(String philhealth_id) {
		this.philhealth_id = philhealth_id;
	}


	public String getH_gender() {
		return h_gender;
	}


	public void setH_gender(String h_gender) {
		this.h_gender = h_gender;
	}
	

	public String getF_position() {
		return f_position;
	}


	public void setF_position(String f_position) {
		this.f_position = f_position;
	}


	public String getPl_fname() {
		return pl_fname;
	}


	public void setPl_fname(String pl_fname) {
		this.pl_fname = pl_fname;
	}


	public String getPl_lname() {
		return pl_lname;
	}


	public void setPl_lname(String pl_lname) {
		this.pl_lname = pl_lname;
	}


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public String getGender() {
		return gender;
	}


	public void setGender(String gender) {
		this.gender = gender;
	}


	public String getContact() {
		return contact;
	}


	public void setContact(String contact) {
		this.contact = contact;
	}


	public String getUser_email() {
		return user_email;
	}


	public void setUser_email(String user_email) {
		this.user_email = user_email;
	}

	

	//--for updating set group and status
	
	public int getHh_set() {
		return hh_set;
	}



	public void setHh_set(int hh_set) {
		this.hh_set = hh_set;
	}



	public String getSet_group() {
		return set_group;
	}



	public void setSet_group(String set_group) {
		this.set_group = set_group;
	}



	public int getStatus() {
		return status;
	}



	public void setStatus(int status) {
		this.status = status;
	}



	public int getCount() {
		return count;
	}



	public void setCount(int count) {
		this.count = count;
	}



	public String getDate_recorded() {
		return date_recorded;
	}



	public void setDate_recorded(String date_recorded) {
		this.date_recorded = date_recorded;
	}


	
	
}
