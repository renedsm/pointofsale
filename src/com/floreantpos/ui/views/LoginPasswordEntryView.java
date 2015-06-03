/*
 * PasswordScreen.java
 *
 * Created on August 14, 2006, 11:01 PM
 */

package com.floreantpos.ui.views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;

import net.miginfocom.swing.MigLayout;

import org.apache.commons.logging.LogFactory;

import com.floreantpos.actions.ClockInOutAction;
import com.floreantpos.config.TerminalConfig;
import com.floreantpos.config.ui.DatabaseConfigurationDialog;
import com.floreantpos.main.Application;
import com.floreantpos.model.User;
import com.floreantpos.swing.MessageDialog;
import com.floreantpos.swing.PosButton;
import com.floreantpos.ui.dialog.POSMessageDialog;
import com.floreantpos.ui.dialog.PasswordEntryDialog;
import com.floreantpos.util.ShiftException;
import com.floreantpos.util.UserNotFoundException;

/**
 * 
 * @author MShahriar
 */
class LoginPasswordEntryView extends JPanel {

	/** Creates new form PasswordScreen */
	LoginPasswordEntryView() {
		//setMinimumSize(new Dimension(320, 10));
		initComponents();

		applyComponentOrientation(ComponentOrientation.getOrientation(Locale.getDefault()));
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	// <editor-fold defaultstate="collapsed"
	// desc="Generated Code">//GEN-BEGIN:initComponents
	private void initComponents() {
		buttonPanel = new javax.swing.JPanel();
		bottomPanel = new javax.swing.JPanel();

		btnShutdown = new com.floreantpos.swing.PosButton();

		setPreferredSize(new Dimension(320, 593));
		setLayout(new BorderLayout());

		buttonPanel.setOpaque(false);
		buttonPanel.setPreferredSize(new java.awt.Dimension(200, 180));
		buttonPanel.setLayout(new MigLayout("", "[111px][111px][111px,grow]", "[60px][60px][60px][60px]"));

		lblTerminalId = new JLabel("TERMINAL ID:");
		lblTerminalId.setForeground(Color.BLACK);
		lblTerminalId.setFont(new Font("Dialog", Font.BOLD, 18));
		lblTerminalId.setHorizontalAlignment(SwingConstants.CENTER);
		add(lblTerminalId, BorderLayout.NORTH);

		bottomPanel.setLayout(new MigLayout("hidemode 3, fill"));
		bottomPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
		
		ButtonGroup buttonGroup = new ButtonGroup();
		buttonGroup.add(btnRegularMode);
		buttonGroup.add(btnCashierMode);
		buttonGroup.add(btnKitchenMode);
		
		btnRegularMode.setFocusable(false);
		btnCashierMode.setFocusable(false);
		btnKitchenMode.setFocusable(false);
		
		ModeSelectionListener l = new ModeSelectionListener();
		btnRegularMode.addActionListener(l);
		btnCashierMode.addActionListener(l);
		btnKitchenMode.addActionListener(l);
		
		btnRegularMode.setSelected(TerminalConfig.isRegularMode());
		btnCashierMode.setSelected(TerminalConfig.isCashierMode());
		btnKitchenMode.setSelected(TerminalConfig.isKitchenMode());
		
		if(!btnRegularMode.isSelected() && !btnCashierMode.isSelected() && !btnKitchenMode.isSelected()) {
			btnRegularMode.setSelected(true);
		}
		
		JPanel modePanel = new JPanel(new GridLayout(1, 0, 2, 2));
		modePanel.add(btnRegularMode);
		modePanel.add(btnCashierMode);
		modePanel.add(btnKitchenMode);
		
		bottomPanel.add(modePanel, "h 60!, grow, wrap");

		psbtnLogin = new PosButton();
		psbtnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				doLogin();
			}
		});
		psbtnLogin.setText("LOGIN");
		bottomPanel.add(psbtnLogin, "grow, wrap, gapbottom 20px");
		
		PosButton btnClockOUt = new PosButton(new ClockInOutAction(false, true));
		bottomPanel.add(btnClockOUt, "grow, wrap, h 60!"); //$NON-NLS-1$
		
		if (TerminalConfig.isShowDbConfigureButton()) {
			btnConfigureDatabase = new com.floreantpos.swing.PosButton();
			btnConfigureDatabase.setAction(goAction);
			btnConfigureDatabase.setText(com.floreantpos.POSConstants.CONFIGURE_DATABASE);
			btnConfigureDatabase.setFocusable(false);
			btnConfigureDatabase.setActionCommand("DBCONFIG");
			bottomPanel.add(btnConfigureDatabase, "grow, wrap, h 60!");
		}

		btnShutdown.setAction(goAction);
		btnShutdown.setText(com.floreantpos.POSConstants.SHUTDOWN);
		btnShutdown.setFocusable(false);

		if (TerminalConfig.isFullscreenMode()) {
			btnConfigureDatabase.setVisible(false);
			btnShutdown.setVisible(false);
		}
		
		bottomPanel.add(btnShutdown, "grow, wrap, h 60!");
		add(bottomPanel, BorderLayout.SOUTH);

		lblTerminalId.setText("");
	}// </editor-fold>//GEN-END:initComponents

	public synchronized void doLogin() {
		try {
			final User user = PasswordEntryDialog.getUser(Application.getPosWindow(), "LOGIN", "ENTER SECRET KEY");
			if (user == null) {
				return;
			}
			Application application = Application.getInstance();
			application.doLogin(user);

		} catch (UserNotFoundException e) {
			LogFactory.getLog(Application.class).error(e);
			POSMessageDialog.showError("User not found");
		} catch (ShiftException e) {
			LogFactory.getLog(Application.class).error(e);
			MessageDialog.showError(e.getMessage());
		} catch (Exception e1) {
			LogFactory.getLog(Application.class).error(e1);
			String message = e1.getMessage();

			if (message != null && message.contains("Cannot open connection")) {
				MessageDialog.showError("Cannot open database connection, please check database configuration.");
				DatabaseConfigurationDialog.show(Application.getPosWindow());
			}
			else {
				MessageDialog.showError("We are sorry, and unexpected error has occuered");
			}
		} finally {
		}
	}

	

	public void setTerminalId(int terminalId) {
		lblTerminalId.setText("TERMINAL ID: " + terminalId);
	}

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private com.floreantpos.swing.PosButton btnConfigureDatabase;
	private com.floreantpos.swing.PosButton btnShutdown;
	private javax.swing.JPanel buttonPanel;
	private javax.swing.JPanel bottomPanel;
	
	// End of variables declaration//GEN-END:variables

	Action goAction = new AbstractAction() {
		public void actionPerformed(ActionEvent e) {

			String command = e.getActionCommand();
			if (com.floreantpos.POSConstants.LOGIN.equals(command)) {
				doLogin();
			}
			else if (com.floreantpos.POSConstants.SHUTDOWN.equals(command)) {
				Application.getInstance().shutdownPOS();
			}
			else if ("DBCONFIG".equalsIgnoreCase(command)) {
				DatabaseConfigurationDialog.show(Application.getPosWindow());
			}
			
		}
	};
	private PosButton psbtnLogin;
	private JLabel lblTerminalId;
	
	private JToggleButton btnRegularMode = new JToggleButton("<html><center>REGULAR<br/>MODE</center></html>");
	private JToggleButton btnCashierMode = new JToggleButton("<html><center>CASHIER<br/>MODE</center></html>");
	private JToggleButton btnKitchenMode = new JToggleButton("<html><center>KITCHEN<br/>MODE</center></html>");

	
	class ModeSelectionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			TerminalConfig.setRegularMode(btnRegularMode.isSelected());
			TerminalConfig.setCashierMode(btnCashierMode.isSelected());
			TerminalConfig.setKitchenMode(btnKitchenMode.isSelected());
		}
		
	}
}
