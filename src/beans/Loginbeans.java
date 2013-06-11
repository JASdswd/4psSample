package beans;

public class Loginbeans {
	private String username=null;
	private int id = 0;
	
	
	
	public Loginbeans() {
		super();
	}
	public Loginbeans(String username,int id) {
		super();
		this.username = username;
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
}
