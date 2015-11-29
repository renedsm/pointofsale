package com.floreantpos.model;

import com.floreantpos.model.base.BaseEmployeeTransaction;

public class EmployeeTransaction extends BaseEmployeeTransaction {
	private static final long serialVersionUID = 1L;

	/*[CONSTRUCTOR MARKER BEGIN]*/
	public EmployeeTransaction() {
		super();
	}

	public EmployeeTransaction (java.lang.Integer id) {
		super(id);
	}

	public EmployeeTransaction (
			java.lang.Integer id,
			java.lang.String transactionType,
			java.lang.String paymentType) {

			super (
				id,
				transactionType,
				paymentType);
		}
	
	/*[CONSTRUCTOR MARKER END]*/

}
