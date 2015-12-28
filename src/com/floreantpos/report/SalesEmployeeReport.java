package com.floreantpos.report;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.jdesktop.swingx.calendar.DateUtils;

import com.floreantpos.Messages;
import com.floreantpos.model.dao.EmployeeDAO;
import com.floreantpos.report.service.ReportService;
import com.floreantpos.util.HibernateQueryResultDataSource;

import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JRViewer;

public class SalesEmployeeReport extends Report {
	
	private SalesEmployeeReportModel itemReportModel;
	private SalesEmployeeReportModel modifierReportModel;


	public SalesEmployeeReport() {
		super();
	}

	@Override
	public void refresh() throws Exception {
		Date date1 = DateUtils.startOfDay(getStartDate());
		Date date2 = DateUtils.endOfDay(getEndDate());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		EmployeeDAO employeeDAO = new EmployeeDAO();
		HibernateQueryResultDataSource ds;
		String cadenaSql =" SELECT E.id_employee, E.name, E.department, sum(T.amount) as Total "+
	                      " FROM posdb.TRANSACTIONS T "+
                          " natural join EMPLOYEE E "+
	                      " where date(T.transaction_time)>='"+sdf.format(date1)+"' "+
	                      " and date(T.transaction_time)<='"+sdf.format(date2)+"' "+
                          
                          " group by T.id_employee ";
		
	//	System.out.println("cadena "+cadenaSql);
		  List listaCargosObject = employeeDAO.getVentaPorEmpleado(cadenaSql);
	      String[] fields = {"id_employee", "name", "department", "Total"};
	      ds = new HibernateQueryResultDataSource(listaCargosObject, fields);
	      
	      HashMap map = new HashMap();
		  ReportUtil.populateRestaurantProperties(map);
		  map.put("reportTitle", "Reporte de Venta por Empleado"); //$NON-NLS-1$ //$NON-NLS-2$
		  map.put("reportTime", ReportService.formatFullDate(new Date())); //$NON-NLS-1$
	      map.put("dateRange", ReportService.formatShortDate(getStartDate()) + " - " + ReportService.formatShortDate(getEndDate())); //$NON-NLS-1$ //$NON-NLS-2$
			
	        JasperReport masterReport = ReportUtil.getReport("sales_employee"); //$NON-NLS-1$

			JasperPrint print = JasperFillManager.fillReport(masterReport, map, ds);
			viewer = new JRViewer(print);

	      
	  		
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isDateRangeSupported() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isTypeSupported() {
		// TODO Auto-generated method stub
		return false;
	}
	
	
	public void createModels(){
		Date date1 = DateUtils.startOfDay(getStartDate());
		Date date2 = DateUtils.endOfDay(getEndDate());
		
		
	}

}
