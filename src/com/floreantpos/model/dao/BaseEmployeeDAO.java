package com.floreantpos.model.dao;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.type.DoubleType;
import org.hibernate.type.StringType;

public class BaseEmployeeDAO extends _RootDAO {
	
	

	public BaseEmployeeDAO() {
		// TODO Auto-generated constructor stub
	}

	
	
	
	public static EmployeeDAO instance;

	/**
	 * Return a singleton of the DAO
	 */
	public static EmployeeDAO getInstance () {
		if (null == instance) instance = new EmployeeDAO();
		return instance;
	}
   @Override
	public Class getReferenceClass () {
		return com.floreantpos.model.Employee.class;
	}

    public Order getDefaultOrder () {
		return Order.asc("name"); //$NON-NLS-1$
    }
    
    public com.floreantpos.model.Employee cast (Object object) {
		return (com.floreantpos.model.Employee) object;
	}

	public com.floreantpos.model.Employee get(java.lang.String key)
		throws org.hibernate.HibernateException {
		return (com.floreantpos.model.Employee) get(getReferenceClass(), key);
	}
	
	public List<Object[]> getVentaPorEmpleado(String cadena){
		Session session = getSession();
		 List<Object[]> lista = new ArrayList<Object[]>();
		 lista = session.createSQLQuery(cadena).list();
		 return lista;
	}
	
	public double getVentaMes(String idEmployee, Date today){
		Session session = getSession();
        Calendar calendar = Calendar.getInstance();  
        calendar.setTime(today);  
        calendar.set(Calendar.DAY_OF_MONTH, 1);  
        Date firstDayOfMonth = calendar.getTime(); 
        
        calendar.add(Calendar.MONTH, 1);          
        calendar.add(Calendar.DATE, -1);  
        Date lastDayOfMonth = calendar.getTime();  

        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
      
        String sql =" select ID_EMPLOYEE, sum(amount) as total from TRANSACTIONS "+
					" where PAYMENT_TYPE='EMPLOYEE' and ID_EMPLOYEE='"+idEmployee+"' "+
					" and date(TRANSACTION_TIME)>='"+sdf.format(firstDayOfMonth)+"' "+
					" and date(TRANSACTION_TIME)<='"+sdf.format(lastDayOfMonth)+"' "
				;

		Object[]  obj = (Object[]) session.createSQLQuery(sql).addScalar("ID_EMPLOYEE", new StringType())
        .addScalar("total", new DoubleType()).uniqueResult();
		double total =0;
		if (obj!=null && obj[1]!=null){
			total = Double.parseDouble(obj[1].toString());
		}
		return total;
		
	}
	
	/**
	 * Persist the given transient instance, first assigning a generated identifier. (Or using the current value
	 * of the identifier property if the assigned generator is used.) 
	 * @param employee a transient instance of a persistent class 
	 * @return the class identifier
	 */
	public java.lang.String save(com.floreantpos.model.Employee employee)
		throws org.hibernate.HibernateException {
		return (java.lang.String) super.save(employee);
	}

	/**
	 * Persist the given transient instance, first assigning a generated identifier. (Or using the current value
	 * of the identifier property if the assigned generator is used.) 
	 * Use the Session given.
	 * @param employee a transient instance of a persistent class
	 * @param s the Session
	 * @return the class identifier
	 */
	public java.lang.String save(com.floreantpos.model.Employee employee, Session s)
		throws org.hibernate.HibernateException {
		return (java.lang.String) save((Object) employee, s);
	}

	/**
	 * Either save() or update() the given instance, depending upon the value of its identifier property. By default
	 * the instance is always saved. This behaviour may be adjusted by specifying an unsaved-value attribute of the
	 * identifier property mapping. 
	 * @param employee a transient instance containing new or updated state 
	 */
	public void saveOrUpdate(com.floreantpos.model.Employee employee)
		throws org.hibernate.HibernateException {
		saveOrUpdate((Object) employee);
	}

	/**
	 * Either save() or update() the given instance, depending upon the value of its identifier property. By default the
	 * instance is always saved. This behaviour may be adjusted by specifying an unsaved-value attribute of the identifier
	 * property mapping. 
	 * Use the Session given.
	 * @param employee a transient instance containing new or updated state.
	 * @param s the Session.
	 */
	public void saveOrUpdate(com.floreantpos.model.Employee employee, Session s)
		throws org.hibernate.HibernateException {
		saveOrUpdate((Object) employee, s);
	}

	/**
	 * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent
	 * instance with the same identifier in the current session.
	 * @param employee a transient instance containing updated state
	 */
	public void update(com.floreantpos.model.Employee employee) 
		throws org.hibernate.HibernateException {
		update((Object) employee);
	}

	/**
	 * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent
	 * instance with the same identifier in the current session.
	 * Use the Session given.
	 * @param employee a transient instance containing updated state
	 * @param the Session
	 */
	public void update(com.floreantpos.model.Employee employee, Session s)
		throws org.hibernate.HibernateException {
		update((Object) employee, s);
	}

	
	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state. 
	 * @param employee the instance to be removed
	 */
	public void delete(com.floreantpos.model.Employee employee)
		throws org.hibernate.HibernateException {
		delete((Object) employee);
	}

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state. 
	 * Use the Session given.
	 * @param employee the instance to be removed
	 * @param s the Session
	 */
	public void delete(com.floreantpos.model.Employee employee, Session s)
		throws org.hibernate.HibernateException {
		delete((Object) employee, s);
	}
	
	/**
	 * Re-read the state of the given instance from the underlying database. It is inadvisable to use this to implement
	 * long-running sessions that span many business tasks. This method is, however, useful in certain special circumstances.
	 * For example 
	 * <ul> 
	 * <li>where a database trigger alters the object state upon insert or update</li>
	 * <li>after executing direct SQL (eg. a mass update) in the same session</li>
	 * <li>after inserting a Blob or Clob</li>
	 * </ul>
	 */
	public void refresh (com.floreantpos.model.Employee employee, Session s)
		throws org.hibernate.HibernateException {
		refresh((Object) employee, s);
	}




}
