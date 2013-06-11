package bean;

public class LogsBean {
	
	private String date = "";
	private String time = "";
	private String log_message = "";
	
	public LogsBean() {
		super();
	}

	public LogsBean(String date, String time, String log_message) {
		super();
		this.date = date;
		this.time = time;
		this.log_message = log_message;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getLog_message() {
		return log_message;
	}

	public void setLog_message(String log_message) {
		this.log_message = log_message;
	}
	
	
	
	
}
