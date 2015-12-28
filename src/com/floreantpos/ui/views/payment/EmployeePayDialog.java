package com.floreantpos.ui.views.payment;

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.apache.commons.lang.StringUtils;

import com.floreantpos.Messages;
import com.floreantpos.model.Employee;
import com.floreantpos.model.dao.EmployeeDAO;
import com.floreantpos.swing.PosButton;
import com.floreantpos.ui.TitlePanel;
import com.floreantpos.ui.dialog.POSDialog;
import com.floreantpos.ui.dialog.POSMessageDialog;

import net.miginfocom.swing.MigLayout;

public class EmployeePayDialog extends POSDialog {
	private JTextField tfIdEmployee;
	private JTextField tfNameEmployee;
	private JTextField tfCredit;
	private JTextField tfSale;
	private JTextField tfBalance;
	private JLabel lPhoto ;
	private boolean proceedSale = false;
	private double amountTicket ;
	private Employee employee ;

	public EmployeePayDialog(JDialog parent){
		super();
		setTitle(Messages.getString("EmployeePayDialog.0")); //$NON-NLS-1$
		
		TitlePanel titlePanel = new TitlePanel();
		titlePanel.setTitle(Messages.getString("EmployeePayDialog.2")); //$NON-NLS-1$
		
		getContentPane().add(titlePanel, BorderLayout.NORTH);
		
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.CENTER);
		
		//"", "[][grow]", "[][]"
		panel.setLayout(new MigLayout("", "[][grow]", "[][]")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		
		
		JLabel lblGiftCertificateNumber = new JLabel(Messages.getString("EmployeePayDialog.1")); //$NON-NLS-1$
		panel.add(lblGiftCertificateNumber, "cell 0 0,alignx trailing"); //$NON-NLS-1$
	
		
	//	tfIdEmployee = new FixedLengthTextField(64);
		tfIdEmployee = new JTextField(20);
		
		  
		
		tfIdEmployee.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
            	if(e.getKeyCode() == KeyEvent.VK_ENTER) {
            		if(StringUtils.isEmpty(tfIdEmployee.getText())) {
    					POSMessageDialog.showMessage(Messages.getString("EmployeePayDialog.5")); //$NON-NLS-1$
    					return;
    				}else{
    					tfNameEmployee.setText("");
    					tfCredit.setText("");
    					tfSale.setText("");
    					tfBalance.setText("");
    					lPhoto.setIcon(null);
    					EmployeeDAO employeeDAO = new EmployeeDAO();
    				    employee = employeeDAO.get(tfIdEmployee.getText());
    					if (employee!=null){
    						
    						if (employee.getStatus().equalsIgnoreCase("ACTIVO")){
    						tfNameEmployee.setText(employee.getName());
    						
    						double monthSales = employeeDAO.getVentaMes(employee.getIdEmployee(), new Date());
    						if (employee.getMaxCredit()==0){
    							tfCredit.setText("ILIMITADO");
    							tfBalance.setText("ILIMITADO");
    						}else{
    							tfCredit.setText(String.valueOf(employee.getMaxCredit()-monthSales));
    							tfBalance.setText(String.valueOf(employee.getMaxCredit()-monthSales));
    						}
    						
    						
    						
    						tfSale.setText(String.valueOf(amountTicket));
    						monthSales+=amountTicket;
    						
    				
    						if (employee.getMaxCredit()>0 && monthSales>employee.getMaxCredit()){
    							POSMessageDialog.showMessage(Messages.getString("EmployeePayDialog.7"));
    							proceedSale = false;
    						}else{
    							
    							proceedSale = true;
    						}
    						
    						if (employee.getPicture()!=null){
    							 byte[] image = employee.getPicture();
    						       
    							 Image img = Toolkit.getDefaultToolkit().createImage(image);
    						        ImageIcon icon = new ImageIcon(img.getScaledInstance(100, 150, Image.SCALE_SMOOTH));
    						        
    						        lPhoto.setIcon(icon);
    						        //add(lPhoto);
    						}
    						
    						}else{
        						POSMessageDialog.showMessage(Messages.getString("EmployeePayDialog.12"));
        						proceedSale = false;
        					}
    					}else{
    						POSMessageDialog.showMessage(Messages.getString("EmployeePayDialog.6"));
    						proceedSale = false;
    					}
    				}
            	   }
               
            }
 
            public void keyTyped(KeyEvent e) {
                // TODO: Do something for the keyTyped event
            }
 
            public void keyPressed(KeyEvent e) {
                // TODO: Do something for the keyPressed event
            }
        });
		
		
		//	panel.add(tfIdEmployee, "cell 1 0,growx"); //$NON-NLS-1$
		
		panel.add(tfIdEmployee, "cell 1 0"); //$NON-NLS-1$
	
		
		JLabel lblFaceValue = new JLabel(Messages.getString("EmployeePayDialog.4")); //$NON-NLS-1$
		panel.add(lblFaceValue, "cell 0 1"); //$NON-NLS-1$
		
		tfNameEmployee = new JTextField(30);
		tfNameEmployee.setEditable(false);;
		
		panel.add(tfNameEmployee); //$NON-NLS-1$
		
		JLabel lblCredit = new JLabel(Messages.getString("EmployeePayDialog.9")); //$NON-NLS-1$
		panel.add(lblCredit, "cell 0 2"); //$NON-NLS-1$
		
		tfCredit = new JTextField(10);
		tfCredit.setEditable(false);
		
		panel.add(tfCredit); //$NON-NLS-1$
		
		
		
		JLabel lblSale = new JLabel(Messages.getString("EmployeePayDialog.10")); //$NON-NLS-1$
		panel.add(lblSale, "cell 0 3"); //$NON-NLS-1$
		
		tfSale = new JTextField(10);
		tfSale.setEditable(false);
		
		panel.add(tfSale); //$NON-NLS-1$
		
		
		JLabel lblBalance = new JLabel(Messages.getString("EmployeePayDialog.11")); //$NON-NLS-1$
		panel.add(lblBalance, "cell 0 4"); //$NON-NLS-1$
		
		tfBalance = new JTextField(10);
		tfBalance.setEditable(false);
		
		panel.add(tfBalance); //$NON-NLS-1$
		
		lPhoto = new JLabel();
		panel.add(lPhoto,"cell 2 0, span 1 5");
		
		
	
		
		
		
		JPanel buttonPanel = new JPanel(new MigLayout("align 50%")); //$NON-NLS-1$
		getContentPane().add(buttonPanel, BorderLayout.SOUTH);
		
		PosButton psbtnOk = new PosButton();
		psbtnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if (employee == null){
					POSMessageDialog.showMessage(Messages.getString("EmployeePayDialog.6")); //$NON-NLS-1$
					return;
				}
				if(StringUtils.isEmpty(tfIdEmployee.getText())) {
					POSMessageDialog.showMessage(Messages.getString("EmployeePayDialog.5")); //$NON-NLS-1$
					return;
				}
				
				if(!proceedSale) {
					POSMessageDialog.showMessage(Messages.getString("EmployeePayDialog.7")); //$NON-NLS-1$
					return;
				}
				
				setCanceled(false);
				dispose();
			}
		});
		psbtnOk.setText(Messages.getString("GiftCertDialog.16")); //$NON-NLS-1$
		buttonPanel.add(psbtnOk, "w 100!, h 60!"); //$NON-NLS-1$
		
		PosButton psbtnCancel = new PosButton();
		psbtnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setCanceled(true);
				dispose();
			}
		});
		psbtnCancel.setText(Messages.getString("GiftCertDialog.18")); //$NON-NLS-1$
		buttonPanel.add(psbtnCancel, "w 100!, h 60!"); //$NON-NLS-1$
		
		
		psbtnOk.setText(Messages.getString("GiftCertDialog.16")); //$NON-NLS-1$
		buttonPanel.add(psbtnOk, "w 100!, h 60!"); //$NON-NLS-1$
		
		PosButton psbtnClear = new PosButton();
		psbtnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tfIdEmployee.setText("");
				tfNameEmployee.setText("");
				tfCredit.setText("");
				tfSale.setText("");
				tfBalance.setText("");
				lPhoto.setIcon(null);
			}
		});
		psbtnClear.setText(Messages.getString("EmployeePayDialog.13")); //$NON-NLS-1$
		buttonPanel.add(psbtnClear, "w 100!, h 60!"); //$NON-NLS-1$
		
	}

	public JTextField getTfIdEmployee() {
		return tfIdEmployee;
	}

	public boolean isProceedSale() {
		return proceedSale;
	}

	public void setProceedSale(boolean proceedSale) {
		this.proceedSale = proceedSale;
	}

	public double getAmountTicket() {
		return amountTicket;
	}

	public void setAmountTicket(double amountTicket) {
		this.amountTicket = amountTicket;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public JTextField getTfCredit() {
		return tfCredit;
	}

	public void setTfCredit(JTextField tfCredit) {
		this.tfCredit = tfCredit;
	}

	
	
	
}
