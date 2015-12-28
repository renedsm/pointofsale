package com.floreantpos.bo.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Icon;
import javax.swing.JTabbedPane;

import com.floreantpos.bo.ui.BackOfficeWindow;
import com.floreantpos.report.ReportViewer;
import com.floreantpos.report.SalesEmployeeReport;
import com.floreantpos.report.SalesReport;

public class SalesExployeeAction extends AbstractAction {

	public SalesExployeeAction() {
		super(com.floreantpos.POSConstants.SALES_EMPLOYEE);
	}

	public SalesExployeeAction(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	public SalesExployeeAction(String name, Icon icon) {
		super(name, icon);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		BackOfficeWindow window = com.floreantpos.util.POSUtil.getBackOfficeWindow();
		JTabbedPane tabbedPane = window.getTabbedPane();
		
		ReportViewer viewer = null;
		int index = tabbedPane.indexOfTab(com.floreantpos.POSConstants.SALES_EMPLOYEE);
		if (index == -1) {
			viewer = new ReportViewer(new SalesEmployeeReport());
			tabbedPane.addTab(com.floreantpos.POSConstants.SALES_EMPLOYEE, viewer);
		}
		else {
			viewer = (ReportViewer) tabbedPane.getComponentAt(index);
		}
		tabbedPane.setSelectedComponent(viewer);

	}

}
