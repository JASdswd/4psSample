package beans;

public class Beanstransaction_record {
	
	public String household_id=null;
	public String head_name=null;
	public float amount;
	public int receive;
	public int sub;
	
	public Beanstransaction_record(){
		super();
	}

	public Beanstransaction_record(String household_id, String head_name,
			float amount, int receive, int sub) {
		super();
		this.household_id = household_id;
		this.head_name = head_name;
		this.amount = amount;
		this.receive = receive;
		this.sub = sub;
	}
	
	public int getSub() {
		return sub;
	}

	public void setSub(int sub) {
		this.sub = sub;
	}


	public String getHousehold_id() {
		return household_id;
	}

	public void setHousehold_id(String household_id) {
		this.household_id = household_id;
	}

	public String getHead_name() {
		return head_name;
	}

	public void setHead_name(String head_name) {
		this.head_name = head_name;
	}

	public float getAmount() {
		return amount;
	}

	public void setAmount(float amount) {
		this.amount = amount;
	}

	public int getReceive() {
		return receive;
	}

	public void setReceive(int receive) {
		this.receive = receive;
	}
	

}
