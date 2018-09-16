package utils;

/**
 * the class Customer
 *
 */
public class Customer {

	private String preName;
	private String surName;
	private String salutation;

	/**
	 * Constructor.
	 * 
	 * @param preName
	 *            - the preName
	 * @param surName
	 *            - the surName
	 * @param salutation
	 *            - the salutation
	 */
	public Customer(String preName, String surName, String salutation) {
		this.preName = preName;
		this.surName = surName;
		this.salutation = salutation;
	}

	/**
	 * Constructor.
	 * 
	 * @param preName
	 *            - the preName
	 * @param surName
	 *            - the surName
	 */
	public Customer(String preName, String surName) {
		this.preName = preName;
		this.surName = surName;
	}

	/**
	 * Default Constructor.
	 */
	public Customer() {
		preName = "";
		surName = "";
		salutation = "";
	}

	// get and set follows below here
	public String getPreName() {
		return preName;
	}

	public void setPreName(String preName) {
		this.preName = preName;
	}

	public String getSurName() {
		return surName;
	}

	public void setSurName(String surName) {
		this.surName = surName;
	}

	public String getSalutation() {
		return salutation;
	}

	public void setSalutation(String salutation) {
		this.salutation = salutation;
	}

}
