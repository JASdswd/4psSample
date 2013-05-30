package beans;

public class Municpality {
	private String municipal=null;
	private int mun_id = 0;
	
	
	public Municpality(int mun_id,String municipal) {
		super();
		this.mun_id = mun_id;
		this.municipal = municipal;
	}

	
	public Municpality(String municipal){
		super();
		this.municipal=municipal;
	}
	
	public int getMun_id() {
		return mun_id;
	}

	public void setMun_id(int mun_id) {
		this.mun_id = mun_id;
	}

	public String getMunicipal() {
		return municipal;
	}

	public void setMunicipal(String municipal) {
		this.municipal = municipal;
	}

}
