package com.floreantpos.bo.ui.explorer;

import java.awt.BorderLayout;
import java.awt.LayoutManager;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import com.floreantpos.bo.ui.BOMessageDialog;
import com.floreantpos.bo.ui.explorer.UserExplorer.UserTableModel;
import com.floreantpos.model.Employee;
import com.floreantpos.model.User;
import com.floreantpos.model.dao.EmployeeDAO;
import com.floreantpos.model.dao.UserDAO;
import com.floreantpos.swing.ListTableModel;
import com.floreantpos.swing.TransparentPanel;
import com.floreantpos.ui.PosTableRenderer;
import com.floreantpos.ui.dialog.BeanEditorDialog;
import com.floreantpos.ui.forms.UserForm;

public class EmployeeExplorer extends TransparentPanel {

	private JTable table;
	private EmployeeTableModel tableModel;

	public EmployeeExplorer(LayoutManager layout, boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);
		// TODO Auto-generated constructor stub
	}

	public EmployeeExplorer(LayoutManager layout) {
		super(layout);
		// TODO Auto-generated constructor stub
	}

	public EmployeeExplorer(boolean isDoubleBuffered) {
		super(isDoubleBuffered);
		// TODO Auto-generated constructor stub
	}

	public EmployeeExplorer() {

		List<Employee> users = EmployeeDAO.getInstance().findAll();

		tableModel = new EmployeeTableModel(users);
		table = new JTable(tableModel);
		table.setDefaultRenderer(Object.class, new PosTableRenderer());

		setLayout(new BorderLayout(5, 5));
		add(new JScrollPane(table));
		final TextField txtFile = new TextField(20);
		txtFile.setEditable(false);

		JButton openFileButton = new JButton(com.floreantpos.POSConstants.ADD);
		openFileButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					JFileChooser fileChooser = new JFileChooser();
					fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
					int result = fileChooser.showOpenDialog(null);

					if (result == JFileChooser.APPROVE_OPTION) {
						File selectedFile = fileChooser.getSelectedFile();
						txtFile.setText(selectedFile.getAbsolutePath());

					}
				} catch (Exception x) {
					x.printStackTrace();
					BOMessageDialog.showError(com.floreantpos.POSConstants.ERROR_MESSAGE, x);
				}
			}

		});

		JButton importButton = new JButton(com.floreantpos.POSConstants.OPEN_FILE);
		importButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					
					
					String fullFileName = "C:\\home\\pos\\logs\\";
		            String op = System.getProperty("os.name");
		            if (op.indexOf("nix") >= 0 || op.indexOf("nux") >= 0) {
		                fullFileName = "/Users/rene/Desktop/Projects/pv/logs/";
		            } else {
		                if (op.indexOf("Mac") >= 0) {
		                    fullFileName = "/Users/rene/Desktop/Projects/pv/logs/";

		                } else {
		                    fullFileName = "C:\\home\\pos\\logs\\";
		                }
		            }
					
					SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
					String fileNameLog = fullFileName +sdf.format(new Date());

					File DataFile = new File(txtFile.getText());

					CSVFile f = new CSVFile();
					ArrayList<String[]> listCSV = f.ReadCSVfile(DataFile);
					
					FileWriter fstream =  new FileWriter(fileNameLog);       
		            BufferedWriter out = new BufferedWriter(fstream);
		            boolean hasErrors = false;
					for (String[] row : listCSV) {
						Employee employee = new Employee();
						try{

						if (row[0].equalsIgnoreCase("ALTA") || row[0].equalsIgnoreCase("EDICION")
								|| row[0].equalsIgnoreCase("CANCELACION") || row[0].equalsIgnoreCase("ELIMINACION")) {
							
							
							employee.setIdEmployee(row[1]);
							employee.setName(row[2]);
							employee.setMaxCredit(Double.parseDouble(row[3]));
							
							if (row.length > 3)
								employee.setDepartment(row[4]);
							
							if (row[0].equalsIgnoreCase("ALTA") || row[0].equalsIgnoreCase("EDICION")) {
								
								employee.setStatus("ACTIVO");
								// load picture
								if (row.length > 4 && row[5] != null && !row[5].equals("")) {
									// save image into database
									File file = new File(row[5]);
									if (file.exists()) {
										byte[] bFile = new byte[(int) file.length()];

										try {
											FileInputStream fileInputStream = new FileInputStream(file);
											// convert file into array of bytes
											fileInputStream.read(bFile);
											fileInputStream.close();
										} catch (Exception ex) {
											ex.printStackTrace();
										}

										employee.setPicture(bFile);
									}
								}
							} else {
								employee.setStatus("CANCELADO");
								employee.setMaxCredit(0);
							}

							EmployeeDAO.instance.saveOrUpdate(employee);
						    out.write(employee.getIdEmployee()+" "+employee.getName()+" OK");
	                        out.newLine();
						}
						}catch (Exception ex){
							 out.write(row[1]+" "+row[2]+" ERROR:"+ex);
		                     out.newLine();
		                     hasErrors = true;
						}
					}
					out.close();
					
					if (!hasErrors){
						BOMessageDialog.showMessage("Archivo cargado");
					}else{
						BOMessageDialog.showMessage("Archivo cargado con errores, verifique el log: "+fileNameLog);
					}

				} catch (Exception x) {
					x.printStackTrace();
					BOMessageDialog.showError(com.floreantpos.POSConstants.ERROR_MESSAGE, x);
				}
			}

		});

		TransparentPanel panel = new TransparentPanel();

		panel.add(openFileButton);
		panel.add(txtFile);
		panel.add(importButton);

		add(panel, BorderLayout.SOUTH);
	}

	class EmployeeTableModel extends ListTableModel {

		EmployeeTableModel(List list) {
			super(new String[] { com.floreantpos.POSConstants.ID, com.floreantpos.POSConstants.NAME,
					com.floreantpos.POSConstants.CREDIT, com.floreantpos.POSConstants.DEPARTMENT,com.floreantpos.POSConstants.STATUS }, list);
		}

		public Object getValueAt(int rowIndex, int columnIndex) {
			Employee employee = (Employee) rows.get(rowIndex);

			switch (columnIndex) {
			case 0:

				return employee.getIdEmployee();

			case 1:
				return employee.getName();

			case 2:
				return String.valueOf(employee.getMaxCredit());

			case 3:
				return employee.getDepartment();

			case 4:
				return employee.getStatus();
			}
			return null;
		}
	}

	public class CSVFile {
		private ArrayList<String[]> Rs = new ArrayList<String[]>();
		private String[] OneRow;

		public ArrayList<String[]> ReadCSVfile(File DataFile) {
			try {
				BufferedReader brd = new BufferedReader(new FileReader(DataFile));

				while (brd.ready()) {
					String st = brd.readLine();
					OneRow = st.split(",");
					Rs.add(OneRow);
					//System.out.println(Arrays.toString(OneRow));
				} // end of while
			} // end of try
			catch (Exception e) {
				e.printStackTrace();
				String errmsg = e.getMessage();
			//	System.out.println("File not found:" + errmsg);
			} // end of Catch
			return Rs;
		}// end of ReadFile method
	}// end of CSVFile class

}
