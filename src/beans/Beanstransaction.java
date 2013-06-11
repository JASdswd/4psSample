package beans;


public class Beanstransaction {
	private String household_id;
	private int householdmember_id;
	private String name=null;
	private String head_name=null;
	private String brgy=null;
	private String municipal=null;
	
	private String date_recorded = null;
	private String time_recorded = null;
	
	private int grsidoctypes_id = 0;
	private String grsidoctypes_casetypes= "";
	private String  grsidoctypes_description= "";
	
	public Beanstransaction(String household_id, int householdmember_id,
			String name,String barangay,String municipal) { 
		super();
		this.household_id = household_id;
		this.householdmember_id = householdmember_id;
		this.name = name;
		this.brgy=barangay;
		this.municipal=municipal;
	}
	public Beanstransaction(String household_id,int householdmember_id,String name,String head_name
			,String brgy,String municipal){
		this.household_id = household_id;
		this.householdmember_id = householdmember_id;
		this.name = name;
		this.head_name=head_name;
		this.brgy=brgy;
		this.municipal=municipal;
	}
	public Beanstransaction(String head_name,String brgy,String municipal){
		this.head_name=head_name;
		this.brgy=brgy;
		this.municipal=municipal;
	}
	
	
	
	public Beanstransaction(int grsidoctypes_id, String grsidoctypes_casetypes,
			String grsidoctypes_description) {
		super();
		this.grsidoctypes_id = grsidoctypes_id;
		this.grsidoctypes_casetypes = grsidoctypes_casetypes;
		this.grsidoctypes_description = grsidoctypes_description;
	}
	public Beanstransaction(String household_id, String name,
			String date_recorded, String time_recorded) {
		super();
		this.household_id = household_id;
		this.name = name;
		this.date_recorded = date_recorded;
		this.time_recorded = time_recorded;
	}
	
	public String getHousehold_id() {
		return household_id;
	}
	public void setHousehold_id(String household_id) {
		this.household_id = household_id;
	}
	public int getHouseholdmember_id() {
		return householdmember_id;
	}
	public void setHouseholdmember_id(int householdmember_id) {
		this.householdmember_id = householdmember_id;
	}
	public String getName() {
		return name;
	}
	public void setHead_name(String name) {
		this.name = name;
	}
	public String getBrgy() {
		return brgy;
	}
	public void setBrgy(String brgy) {
		this.brgy = brgy;
	}
	public String getMunicipal() {
		return municipal;
	}
	public void setMunicipal(String municipal) {
		this.municipal = municipal;
	}
	public String getHead_name() {
		return head_name;
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
	public void setName(String name) {
		this.name = name;
	}
	public int getGrsidoctypes_id() {
		return grsidoctypes_id;
	}
	public void setGrsidoctypes_id(int grsidoctypes_id) {
		this.grsidoctypes_id = grsidoctypes_id;
	}
	public String getGrsidoctypes_casetypes() {
		return grsidoctypes_casetypes;
	}
	public void setGrsidoctypes_casetypes(String grsidoctypes_casetypes) {
		this.grsidoctypes_casetypes = grsidoctypes_casetypes;
	}
	public String getGrsidoctypes_description() {
		return grsidoctypes_description;
	}
	public void setGrsidoctypes_description(String grsidoctypes_description) {
		this.grsidoctypes_description = grsidoctypes_description;
	}
	
}
