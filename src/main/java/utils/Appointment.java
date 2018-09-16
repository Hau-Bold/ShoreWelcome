package utils;

/**
 * the class Appointment
 *
 */
public class Appointment {

	private time.Time startTime;
	private time.Time endTime;
	private time.Date date;
	private Customer customer;
	private String employee;
	private String service;
	private String subject;

	// get and set follows here
	public time.Time getStartTime() {
		return startTime;

	}

	public void setStartTime(time.Time startTime) {
		this.startTime = startTime;
	}

	public time.Time getEndTime() {
		return endTime;
	}

	public void setEndTime(time.Time endTime) {
		this.endTime = endTime;
	}

	public time.Date getDate() {
		return date;
	}

	public void setDate(time.Date date) {
		this.date = date;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public String getEmployee() {
		return employee;
	}

	public void setEmployee(String employee) {
		this.employee = employee;
	}

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}
}
