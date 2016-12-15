package ca.uwo.csd.cs2212.bdumais;
 
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
 
public class CustomerTableModel extends AbstractTableModel {
 
    private final static int COLUMN_COUNT = 4;
    private final static int IDX_FIRST_NAME = 0;
    private final static int IDX_LAST_NAME = 1;
    private final static int IDX_EMAIL = 2;
    private final static int IDX_BALANCE = 3;
    
    private final List<Customer> customers;
 
    public CustomerTableModel() {
        customers = new ArrayList<>();
    }
    
    public List<Customer> getCustomers() {
        return customers;
    }
    
    @Override
    public int getRowCount() {
        return customers.size();
    }
 
    @Override
    public int getColumnCount() {
        return COLUMN_COUNT;
    }
    
    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return (columnIndex == IDX_BALANCE ? Double.class : String.class);
    }
    
    @Override
    public String getColumnName(int columnIndex) {
        switch (columnIndex) {
            case IDX_FIRST_NAME:
                return "First Name";
            case IDX_LAST_NAME:
                return "Last Name";
            case IDX_EMAIL:
                return "Email";
            case IDX_BALANCE:
                return "Balance Owing";
            default:
                return null;            
        }
    }
 
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if ((rowIndex < 0) || (rowIndex >= customers.size()))
            return null;
         
		Customer cust = customers.get(rowIndex);
		
		switch (columnIndex) {
            case IDX_FIRST_NAME:
                return cust.getFirstName();
            case IDX_LAST_NAME:
                return cust.getLastName();
            case IDX_EMAIL:
                return cust.getEmail();
            case IDX_BALANCE:
                return cust.getBalanceOwing();
            default:
                return null;            
        }
    }
    
    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        if ((rowIndex < 0) || (rowIndex >= customers.size()) || columnIndex != IDX_BALANCE)
            return;
            
        customers.get(rowIndex).setBalanceOwing((double)aValue);
		fireTableCellUpdated(rowIndex, columnIndex);
    }
    
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
      if(columnIndex == IDX_BALANCE)
		return true;
	return false;
    }
 
}