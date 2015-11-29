package com.floreantpos.bo.actions;

import java.awt.BorderLayout;
import java.awt.TextField;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Icon;
import javax.swing.JTabbedPane;

import com.floreantpos.bo.ui.BackOfficeWindow;
import com.floreantpos.bo.ui.explorer.EmployeeExplorer;

public class EmployeeExplorerAction extends AbstractAction {

	public EmployeeExplorerAction() {
		super(com.floreantpos.POSConstants.EMPLOYEE);
	}

	public EmployeeExplorerAction(String name) {
		super(name);
	}

	public EmployeeExplorerAction(String name, Icon icon) {
		super(name, icon);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
BackOfficeWindow backOfficeWindow = com.floreantpos.util.POSUtil.getBackOfficeWindow();
		
		EmployeeExplorer explorer = null;
		JTabbedPane tabbedPane = backOfficeWindow.getTabbedPane();
		int index = tabbedPane.indexOfTab(com.floreantpos.POSConstants.EMPLOYEE);
		if (index == -1) {
			explorer = new EmployeeExplorer();
			tabbedPane.addTab(com.floreantpos.POSConstants.EMPLOYEE, explorer);
		}
		else {
			explorer = (EmployeeExplorer) tabbedPane.getComponentAt(index);
		}
		tabbedPane.setSelectedComponent(explorer);

	}

}
