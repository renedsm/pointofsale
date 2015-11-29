package com.floreantpos.model;

import javax.xml.bind.annotation.XmlRootElement;

import com.floreantpos.model.base.BaseEmployee;
@XmlRootElement(name = "employee")
public class Employee extends BaseEmployee {

	
	/*[CONSTRUCTOR MARKER BEGIN]*/
	public Employee() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Employee(String idEmployee, String name, double maxCredit, String status) {
		super(idEmployee, name, maxCredit, status);
		// TODO Auto-generated constructor stub
	}

	public Employee(String idEmployee) {
		super(idEmployee);
		// TODO Auto-generated constructor stub
	}

	

	/*[CONSTRUCTOR MARKER END]*/
	
	@Override
	public String toString() {
		return "Employee [idEmployee=" + idEmployee + ", name=" + name + "]";
	}


}
