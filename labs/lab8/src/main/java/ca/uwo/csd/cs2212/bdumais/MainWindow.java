package ca.uwo.csd.cs2212.bdumais;
 
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;
 
public class MainWindow extends JFrame {
 
    private JButton btnGetSelection;
    private JTable tblCustomers;
    private JTextArea txtOutput;
 
    public MainWindow() {
        initComponents();
		initTable();
    }
	
	private void initTable(){
		initModel();
		setColumnWidths();
		tblCustomers.setAutoCreateRowSorter(true);
		this.btnGetSelection.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
			   StringBuilder sb = new StringBuilder();
			   int[] selectedRows = tblCustomers.getSelectedRows();
			   
			   sb.append("Selected customers:\n");
			   
			   for(int row : selectedRows) {
				String firstName = tblCustomers.getModel().getValueAt(row, 0).toString();
				String lastName = tblCustomers.getModel().getValueAt(row, 1).toString();
				sb.append(firstName).append(" ").append(lastName).append("\n");
			   }
			   
			   txtOutput.setText(txtOutput.getText() + "\n" + sb.toString());
			}
		});
		
		tblCustomers.setDefaultRenderer(Double.class, new CurrencyCellRenderer());
		tblCustomers.setCellSelectionEnabled(true);
		tblCustomers.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER,0), "startEditing");

	}
     
	private void setColumnWidths(){
		tblCustomers.getColumnModel().getColumn(0).setPreferredWidth(35);
		tblCustomers.getColumnModel().getColumn(1).setPreferredWidth(35);
		tblCustomers.getColumnModel().getColumn(2).setPreferredWidth(100);
		tblCustomers.getColumnModel().getColumn(3).setPreferredWidth(50);
	}
	 
	private void initModel(){
		CustomerTableModel custTable = new CustomerTableModel();
		custTable.getCustomers().add(new Customer("John", "Doe", "jdoe@example.come", 32.50));
		custTable.getCustomers().add(new Customer("Jane", "Doe", "jane.doe@example.net", 0));
		custTable.getCustomers().add(new Customer("James T.", "Kirk", "jtkirk@enterprise.uss", 0));
		custTable.getCustomers().add(new Customer("Albert", "Einstein", "e_mc_stein@physics.woa", 12.67));
		custTable.getCustomers().add(new Customer("Barack", "Obama", "b_bama@healthcare.gov", 1000));
		custTable.getCustomers().add(new Customer("Usain", "Bolt", "2_fast_4_u@olympics.jm", 0.32));
		tblCustomers.setModel(custTable);
	}
	
    private void initComponents() {
 
        JPanel pnlOutput = new JPanel();
        JScrollPane scrOutput = new JScrollPane();
        JPanel pnlTable = new JPanel();
        JScrollPane scrTable = new JScrollPane();
        JToolBar toolBar = new JToolBar();
 
        btnGetSelection = new JButton();
        tblCustomers = new JTable();
        txtOutput = new JTextArea();
        
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
 
        pnlOutput.setBorder(BorderFactory.createTitledBorder("Output"));
        pnlOutput.setLayout(new BorderLayout());
 
        txtOutput.setColumns(20);
        txtOutput.setRows(5);
        scrOutput.setViewportView(txtOutput);
 
        pnlOutput.add(scrOutput, BorderLayout.CENTER);
 
        getContentPane().add(pnlOutput, BorderLayout.SOUTH);
        pnlOutput.getAccessibleContext().setAccessibleDescription("");
 
        pnlTable.setBorder(BorderFactory.createTitledBorder("Customer Data"));
        pnlTable.setLayout(new BorderLayout());
 
        scrTable.setViewportView(tblCustomers);
 
        pnlTable.add(scrTable, BorderLayout.CENTER);
 
        getContentPane().add(pnlTable, BorderLayout.CENTER);
 
        toolBar.setRollover(true);
 
        btnGetSelection.setText("Get Selection");
        btnGetSelection.setFocusable(false);
        btnGetSelection.setHorizontalTextPosition(SwingConstants.CENTER);
        btnGetSelection.setVerticalTextPosition(SwingConstants.BOTTOM);
 
        toolBar.add(btnGetSelection);
 
        getContentPane().add(toolBar, BorderLayout.NORTH);
 
        pack();
    }                
}