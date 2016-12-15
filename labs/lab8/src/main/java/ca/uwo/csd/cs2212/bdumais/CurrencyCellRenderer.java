package ca.uwo.csd.cs2212.bdumais;
 
import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import java.text.Format;
import java.text.NumberFormat;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
 
public class CurrencyCellRenderer extends DefaultTableCellRenderer {
 
    private final Format formatter;
 
    public CurrencyCellRenderer() {
        this.formatter = NumberFormat.getCurrencyInstance();
        this.setHorizontalAlignment(SwingConstants.RIGHT);
    }
 
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, 
      boolean isSelected, boolean hasFocus, int row, int column) {
 
        double amount = (double)value;
		if(amount > 0)
			this.setForeground(Color.RED);
		else
			this.setForeground(Color.BLACK);
 
		if(isSelected){
			setForeground((Color)UIManager.get("Table.selectionForeground"));
			setBackground((Color)UIManager.get("Table.selectionBackground"));
		}
		else {
			setBackground((Color)UIManager.get("Table.background"));
		}
 
        // Format the value with a currency symbol
        String formattedValue = formatter.format(amount);
        
        // Set the value here
		super.setValue(formattedValue);
 
        return this;
    }
}