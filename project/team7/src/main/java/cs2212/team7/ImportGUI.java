package cs2212.team7;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import javax.swing.JPanel;

/**
 * GUI Class for importing objects into gradebook
 * @author Ben
 */
public class ImportGUI extends javax.swing.JFrame {

    private final static int STUDENTS = 0;
    private final static int GRADES = 1;
    
    private final int mode;
    
    private final Course course;
    private Deliverable del;
    private File file;
    
    /**
     * Creates new form ImportGUI
     * @param c
     * @param mode dictates what we are importing
     * @param f file to be imported from
     */
    public ImportGUI(Course c, int mode, File f) {
        this.mode = mode == 0 ? STUDENTS : GRADES; //Set mode depending on parameter
        course = c;     //Set course
        file = f;       //Set File
        initComponents();
        run();
    }
    
    /**
     * Method to handle button clicks accordingly
     * @param e 
     */
    private void run() {
        
        //Clear output
        outputBox.setText("Importing...");
        
        //If we're importing students, do the following
        if(mode == STUDENTS){
            
            //Start output message
            int imported = 0;
            int added = 0;
            try { //Try to open the file and import
                ImportResult results = CSVManager.importStudents(file); 
                
                //Create iterator and iterate through results
                Iterator it = results.iterator();
                while(it.hasNext()){
                    Student s = (Student)it.next(); //Get student
                    String id = Long.toString(s.getStudentNum()); //get id
                    String email = s.getEmail();
                    
                    //Check if this student already exists in the database
                    if(GUIHelper.lookupStudent(id)){
                        boolean enrolled = GUIHelper.enroll(id, course); //If it does, enroll it from the database
                        //Display results in output
                        String str;
                        if(!enrolled)
                            str = "\nStudent " + id + " is already enrolled in this course";
                        else{
                            str = "\nStudent " + id + " already exists and was added from database";
                            added++;
                        }
                        
                        outputBox.append(str);
                    }
                    else{ //Otherwise its a new student, so create and add to database
                        if(!App.containsEmail(email)){ //If no student with the email exists, add it
                            course.addStudent(s); //enroll
                            PersistenceManager.writeStudent(s); //add to database
                            //Display results
                            String str = "\nStudent " + id + " was added to the course and database";
                            imported++;
                            outputBox.append(str);
                        }
                        else{ //Otherwise email already exists, so print an error
                            String str = "\nStudent " + id + " has an email that already exists, student not imported";
                            outputBox.append(str);
                        }
                    }
                }
                
                //Now we display any failed imports
                LinkedList<String> fails = results.getFailedImports();
                for(String s : fails){ //Loop through failures and display results
                    String str = "\nFailed to import Student: " + s;
                    outputBox.append(str);
                }
                
                outputBox.append("\n\nImported " + imported + " Students from file and added " + added + " Students from database");
                outputBox.append("\nDone");
                
            //If an error is thrown while trying to import, display an error in output
            } catch (IOException | ClassNotFoundException ex) {
                outputBox.append("\nUnable to import from \"" + file.getName() + "\"\nPlease ensure filepath and name are correct");
            }

        }//End of if import students
        
        else if(mode == GRADES){
            //Initialize variable for imported grades
            int imported = 0;
            try {
                //Create string of deliverable names for import comparison
                LinkedList<Deliverable> dels = course.getDeliverables();
                LinkedList<String> names = new LinkedList<>();
                for(Deliverable d : dels){
                    names.add(d.getName());
                }
                
                //Import file
                ImportResult results = CSVManager.importGrades(names ,file);
                
                //Get list of deliverables import contains
                ArrayList<String> inputDels = results.getDelNames();
                Iterator it = results.iterator();
                
                if(inputDels.size() == 0){
                    outputBox.append("\nNo valid deliverables found in grades");
                    outputBox.append("\nPlease either add relevant Deliverables or use different input");
                    return;
                }
                    
                
                //Loop through results
                while(it.hasNext()){
                    //Get import result
                    ImportedGrade imp = (ImportedGrade)it.next();
                    Float[] grades = imp.getGrades();
                    long id = (long)imp.getNumber(); //Get Student ID
                    int i = -1;
                    if(GUIHelper.inCourse(id, course)){ //If id exists within course
                        for(String name : inputDels){ //Loop through all deliverables to edited
                            i++;
                            name = name.substring(1);
                            Deliverable d = course.getDeliverable(name); //Get deliverable
                            try {
                                float grade = grades[i];
                                grade = (float)(Math.round(grade*10.0) / 10.0);
                                if(grade >= 0 && grade <= 100){
                                    d.setGrade(id, grade);
                                    outputBox.append("\nImported Grade for student " + id);
                                    imported++;
                                }
                                else
                                    outputBox.append("\nCould not set grade for student " + id + ": invalid grade input");
                            } catch (DeliverableException | NullPointerException ex) { //Catch errors and print message
                                outputBox.append("\nCould not set grade for student " + id);
                            }
                        }
                    }
                    else
                        outputBox.append("\nStudent " + id + " does not exist within active course");
                        
                }
                
                //Now we display any failed imports
                LinkedList<String> fails = results.getFailedImports();
                for(String s : fails){ //Loop through failures and display results
                    String str = "\nFailed to import grades from: " + s;
                    outputBox.append(str);
                }
                outputBox.append("\n\nImported " + imported + " Grades");
                outputBox.append("\nDone");
                
            } catch (IOException | InvalidCsvException ex) {
                outputBox.append("\nUnable to import from \"" + file.getName() + "\"\nPlease ensure filepath and name are correct");
            }
            
        }
    }
    
    /**
     * Method to return the main JPanel of the GUI
     * @return JPanel
     */
    public JPanel getPanel(){
        return mainPanel;
    }
    
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel = new javax.swing.JPanel();
        scrollPane = new javax.swing.JScrollPane();
        outputBox = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        outputBox.setEditable(false);
        outputBox.setColumns(20);
        outputBox.setFont(new java.awt.Font("Monospaced", 0, 10)); // NOI18N
        outputBox.setRows(5);
        outputBox.setText("No output to display");
        scrollPane.setViewportView(outputBox);

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(scrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE)
                .addContainerGap())
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(scrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 278, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel mainPanel;
    private javax.swing.JTextArea outputBox;
    private javax.swing.JScrollPane scrollPane;
    // End of variables declaration//GEN-END:variables

}
