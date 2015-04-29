package com.iwinnerapps.xml.service;

public class EmployeeInfoForm implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String fName;
	private String lName;
	private String nName;
	private String sal;

	public String getfName() {
		return fName;
	}

	public void setfName(String fName) {
		this.fName = fName;
	}

	public String getlName() {
		return lName;
	}

	public void setlName(String lName) {
		this.lName = lName;
	}

	public String getnName() {
		return nName;
	}

	public void setnName(String nName) {
		this.nName = nName;
	}

	public String getSal() {
		return sal;
	}

	public void setSal(String sal) {
		this.sal = sal;
	}

	@Override
	public String toString() {
		return "EmployeeInfoForm [fName=" + fName + ", lName=" + lName
				+ ", nName=" + nName + ", sal=" + sal + "]";
	}

}
