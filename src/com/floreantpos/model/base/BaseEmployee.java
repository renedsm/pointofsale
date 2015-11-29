package com.floreantpos.model.base;

import java.io.Serializable;
import java.util.Date;



/**
 * @author rene
 * table = EMPLOYEE
 *
 */
public abstract class BaseEmployee implements Comparable, Serializable {
	

	public static String REF = "Employee"; //$NON-NLS-1$
	public static String PROP_ID_EMPLOYEE = "idEmployee"; //$NON-NLS-1$
	public static String PROP_NAME = "name"; //$NON-NLS-1$
	public static String PROP_DEPARTMENT = "department"; //$NON-NLS-1$
	public static String PROP_MAX_CREDIT = "maxCredit"; //$NON-NLS-1$
	public static String PROP_PICTURE = "picture"; //$NON-NLS-1$
	public static String PROP_LAST_VISIT = "lastVisit"; //$NON-NLS-1$
	public static String PROP_STATUS = "status"; //$NON-NLS-1$
	

	private int hashCode = Integer.MIN_VALUE;
	
	
	//primary key
	
	protected String idEmployee;
	
	//fields
	protected String name;
	protected String department;
	protected double maxCredit;
	protected byte[] picture;
	protected String lastVisit;
	protected String status;
	
	
	
	public BaseEmployee(){
		initialize();
	}

	public BaseEmployee(String idEmployee, String name, double maxCredit, String status) {		
		
		this.idEmployee = idEmployee;
		this.name = name;
		this.maxCredit = maxCredit;
		this.status = status;
		initialize();
	}
	
	
	public BaseEmployee(String idEmployee) {

		this.idEmployee = idEmployee;
		initialize();
	}




	protected void initialize () {}





	@Override
	public int compareTo(Object obj) {
		if (obj.hashCode() > hashCode()) return 1;
		else if (obj.hashCode() < hashCode()) return -1;
		return 0;
	
	}
	
	




	@Override
	public int hashCode() {
		if (null == this.idEmployee || this.idEmployee.equals("")) return super.hashCode();
		else{
			String hashString = this.getClass().getName() + ":" + this.idEmployee.hashCode();
			this.hashCode = hashString.hashCode();
		}
		
		return this.hashCode();
	}
	
	


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BaseEmployee other = (BaseEmployee) obj;
		if (idEmployee == null) {
			if (other.idEmployee != null)
				return false;
		} else if (!idEmployee.equals(other.idEmployee))
			return false;
		return true;
	}






	public String getIdEmployee() {
		return idEmployee;
	}




	public void setIdEmployee(String idEmployee) {
		this.idEmployee = idEmployee;
	}




	public String getName() {
		return name;
	}




	public void setName(String name) {
		this.name = name;
	}




	public String getDepartment() {
		return department;
	}




	public void setDepartment(String department) {
		this.department = department;
	}




	public double getMaxCredit() {
		return maxCredit;
	}




	public void setMaxCredit(double maxCredit) {
		this.maxCredit = maxCredit;
	}




	public byte[] getPicture() {
		return picture;
	}




	public void setPicture(byte[] picture) {
		this.picture = picture;
	}




	public String getLastVisit() {
		return lastVisit;
	}




	public void setLastVisit(String lastVisit) {
		this.lastVisit = lastVisit;
	}




	public String getStatus() {
		return status;
	}




	public void setStatus(String status) {
		this.status = status;
	}

}
