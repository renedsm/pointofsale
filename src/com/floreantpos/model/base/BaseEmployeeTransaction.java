package com.floreantpos.model.base;

import java.io.Serializable;

public class BaseEmployeeTransaction extends com.floreantpos.model.PosTransaction implements Comparable, Serializable {
	
	public static String REF = "EmployeeTransaction"; //$NON-NLS-1$
	public static String PROP_ID = "id"; //$NON-NLS-1$
	public BaseEmployeeTransaction () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseEmployeeTransaction (java.lang.Integer id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public BaseEmployeeTransaction (
		java.lang.Integer id,
		java.lang.String transactionType,
		java.lang.String paymentType) {

		super (
			id,
			transactionType,
			paymentType);
	}


	
	private int hashCode = Integer.MIN_VALUE;









	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.floreantpos.model.EmployeeTransaction)) return false;
		else {
			com.floreantpos.model.EmployeeTransaction employeeTransaction = (com.floreantpos.model.EmployeeTransaction) obj;
			if (null == this.getId() || null == employeeTransaction.getId()) return false;
			else return (this.getId().equals(employeeTransaction.getId()));
		}
	}

	public int hashCode () {
		if (Integer.MIN_VALUE == this.hashCode) {
			if (null == this.getId()) return super.hashCode();
			else {
				String hashStr = this.getClass().getName() + ":" + this.getId().hashCode(); //$NON-NLS-1$
				this.hashCode = hashStr.hashCode();
			}
		}
		return this.hashCode;
	}

	public int compareTo (Object obj) {
		if (obj.hashCode() > hashCode()) return 1;
		else if (obj.hashCode() < hashCode()) return -1;
		else return 0;
	}

	public String toString () {
		return super.toString();
	}


}
