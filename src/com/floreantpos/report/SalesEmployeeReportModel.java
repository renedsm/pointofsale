package com.floreantpos.report;

import java.text.DecimalFormat;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import com.floreantpos.Messages;
import com.floreantpos.main.Application;

public class SalesEmployeeReportModel extends AbstractTableModel {
	private static DecimalFormat formatter = new DecimalFormat("#,##0.00"); //$NON-NLS-1$
	private String currencySymbol;
	
	private String[] columnNames = {Messages.getString("SalesEmployeeReportModel.1"), Messages.getString("SalesEmployeeReportModel.2"), Messages.getString("SalesEmployeeReportModel.3")}; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
	private List<ReportEmployeeItem> items;
	private double grandTotal;
	

	public SalesEmployeeReportModel() {
		super();
		currencySymbol = Application.getCurrencySymbol();
	}

	@Override
	public int getRowCount() {
		if(items == null) {
			return 0;
		}
		
		return items.size();
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	
	@Override
	public String getColumnName(int column) {
		return columnNames[column];
	}
	
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		ReportEmployeeItem item = items.get(rowIndex);
		
		switch(columnIndex) {
			case 0:
				return item.getIdEmpleado();
				
			case 1:
				return item.getNombre();
				
				
			case 2:
				return currencySymbol + " " + formatter.format(item.getMontoVenta()); //$NON-NLS-1$
				
		
		}
		
		
		return null;
	}
	
	

	public List<ReportEmployeeItem> getItems() {
		return items;
	}

	public void setItems(List<ReportEmployeeItem> items) {
		this.items = items;
	}

	public static DecimalFormat getFormatter() {
		return formatter;
	}

	public static void setFormatter(DecimalFormat formatter) {
		SalesEmployeeReportModel.formatter = formatter;
	}

	public String getCurrencySymbol() {
		return currencySymbol;
	}

	public void setCurrencySymbol(String currencySymbol) {
		this.currencySymbol = currencySymbol;
	}

	public String[] getColumnNames() {
		return columnNames;
	}

	public void setColumnNames(String[] columnNames) {
		this.columnNames = columnNames;
	}

	public double getGrandTotal() {
		return grandTotal;
	}

	public void setGrandTotal(double grandTotal) {
		this.grandTotal = grandTotal;
	}
	
	

}
