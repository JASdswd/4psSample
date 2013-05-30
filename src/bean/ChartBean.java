package bean;

public class ChartBean {

	private String municipality = "";
	private int registered = 0;
	private int notRegistered = 0;
	
	public ChartBean() {
		super();
	}

	
	
	public ChartBean(String municipality, int registered, int notRegistered) {
		super();
		this.municipality = municipality;
		this.registered = registered;
		this.notRegistered = notRegistered;
	}



	public String getMunicipality() {
		return municipality;
	}

	public void setMunicipality(String municipality) {
		this.municipality = municipality;
	}

	public int getRegistered() {
		return registered;
	}

	public void setRegistered(int registered) {
		this.registered = registered;
	}

	public int getNotRegistered() {
		return notRegistered;
	}

	public void setNotRegistered(int notRegistered) {
		this.notRegistered = notRegistered;
	}
	
}
