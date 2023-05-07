package Package;

public class log {
	String date;
	String duration;
	String cycle;
	String category;
	String deliverable;
	String number;
	
	public log(String f, String a, String b, String c, String d, String e) {
		number = f;
		date = a;
		duration = b;
		cycle = c;
		category = d;
		deliverable = e;
	}
	
	public String getDate() {
		return date;
	}
	
	public String getDuration() {
		return duration;
	}
	
	public String getCycle() {
		return cycle;
	}
	
	public String getCategory() {
		return category;
	}
	
	public String getDeliverable() {
		return deliverable;
	}
	
	public String getNumber() {
		return number;
	}
}
