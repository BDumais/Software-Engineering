/**
 * Custom Table model to handle grades, averages, and students
 */
package cs2212.team7;

/* Imports */
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.AbstractTableModel;
 
public class GradesTableModel extends AbstractTableModel {
 
/* Attributes */
    private final static int FIRST = 0;                 //Constant for first row
    private final static int NAME = 0;			//Name column
    private final static int STU_ID = 1;		//Student ID column
    private final static int AVG_T = 2;			//Course average column
    private final static int AVG_E = 3;                 //Exam average column
    private final static int AVG_A= 4;                  //Assignment average column
    private static int COLUMN_COUNT = 5;
    
    
    private final List<TableRow> rowData;		//List to store loaded courses
 
    /**
     * Constructor for the model
     */
    public GradesTableModel() {
        rowData = new ArrayList<>();
    }
    public GradesTableModel(List<TableRow> in_data){
        rowData = in_data;
    }
    
	/* Public Methods */

    /**
     * Get for the courses in the table
     * @return Array list of courses in the table
     */
    public List<TableRow> getRows() {
        return rowData;
    }
    /**
     * Get for the current row count
     * @return int of rows in table
     */
    @Override
    public int getRowCount() {
        return rowData.size();
    }
 
    /**
     * Get for the current column count
     * @return int of columns in table
     */
    @Override
    public int getColumnCount() {
        return COLUMN_COUNT;
    }
    
    public void setColumnCount(int count){
        COLUMN_COUNT = 5 + count;
    }
    
    /**
     * Get for a columns name
     * @param columnIndex the column being looked at
     * @return String of the name of that column
     */
    @Override
    public String getColumnName(int columnIndex) {
        switch (columnIndex) {  //Compare the parameter column to the columns in table
            case NAME:         //If its the TITLE, return title and etc
                return "Student Name";
            case STU_ID:
                return "Student ID";
            case AVG_T:
                return "Course Average";
            case AVG_E:
                return "Exam Average";
            case AVG_A:
                return "Assignment Average";
            default:
                if(columnIndex < COLUMN_COUNT){
                    //We subtract 5 as there are 5 columns before the first deliverable
                    //Ie the 6th column is the first deliverable
                    Deliverable d = rowData.get(FIRST).getADeliverable(columnIndex - 5);
                    return d.getName() + " (" + d.getWeight() + "%)";
                }
                else
                    return null;
        }
    }
 
    /**
     * Get method for the value of a cell
     * @param rowIndex the row being accessed
     * @param columnIndex the column being accessed
     * @return Object whatever is in that cell
     */
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        try {
            if ((rowIndex < 0) || (rowIndex >= rowData.size())) //Check index
                return null;    //If invalid return null
            
            TableRow tr = rowData.get(rowIndex); //Get course object representing that row
            
            switch (columnIndex) {  //Look at the column being accessed
                case NAME:
                    return tr.getName();   //return name if its the title etc
                case STU_ID:
                    return tr.getID();
                case AVG_T:
                    return tr.getTotalAverage();
                case AVG_E:
                    return tr.getExamAverage();
                case AVG_A:
                    return tr.getAssnAverage();
                default:            
                    if(columnIndex < COLUMN_COUNT)
                        return tr.getADeliverable(columnIndex - 5).getStudentGrade(tr.getStudent().getStudentNum());
                    else
                        return null;
            }
        } catch (DeliverableException ex) {}
        return null;
    }
    
    /**
     * Set method for a cell
     * @param aValue object of what is to be set
     * @param rowIndex row to be changed
     * @param columnIndex cell to be changed
     */
    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        try {
            if ((rowIndex < 0) || (rowIndex >= rowData.size())) //Check if the row is valid
                return; //If invalid return
            
            //Convert Average
            float grade = Float.parseFloat((String)aValue);
            grade = (float)(Math.round(grade*10.0) / 10.0);
            
            //return if input is invalid
            if(grade < 0 || grade > 100)
                return;
            
            TableRow tr = rowData.get(rowIndex); //Get data for the corresponding row
            //Set the average for the deliverable
            tr.getADeliverable(columnIndex - 5).setGrade(tr.getStudent().getStudentNum(), grade);
            
            fireTableRowsUpdated(rowIndex, rowIndex); //Update
           
            //If an error was thrown trying to find the grade for the student, print a message
        } catch (DeliverableException ex) { System.err.println("Error setting Grade - could not find this student");
                                            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NumberFormatException ex) {}
        
      
    }
    
    /**
     * Method to remove an object from the table
     * @param rows rows being removed
     */
    public void removeRow(int[] rows)
    {
        for(int row : rows){
            if(row >= 0){   //If row is valid, remove it from the table
                rowData.remove(row);

               /* Now we need to remove the student from the course */
               long sNum = rowData.get(row).getStudent().getStudentNum();
               rowData.get(row).getCourse().removeStudent(sNum);

               fireTableRowsDeleted(row, row); //Update          
            }
        }
    }
    /**
     * Method to check if a cell is editable
     * @param rowIndex row to be checked
     * @param columnIndex column to be checked
     * @return boolean of whether it can be edited
     */
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex){
        return columnIndex > 4;
    }
 
}