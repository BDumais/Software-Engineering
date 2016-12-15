package cs2212.team7;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import javax.swing.JPanel;

/**
 * GUI class for database management
 * @author Ben
 */
public class DatabaseManagerGUI extends javax.swing.JFrame implements ActionListener {

    private LinkedList<String> removed;
    
    /**
     * Creates new form ImportGUI
     */
    public DatabaseManagerGUI() {
        initComponents();
        populateStudentSelector();
        populateCourseSelector();
        removed = new LinkedList<>();
    }
    
    public JPanel getPanel(){
        return mainPanel;
    }
    
    public LinkedList<String> getRemoved(){
        return removed;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        
        //Get the source of the button
        
        //Source is Search button for student
        if(e.getSource() == searchStudentButton){
            //Get text from search box
            String query = searchStudentText.getText();
            //Lookup this student
            boolean exists = GUIHelper.lookupStudent(query);
            if(exists){ //If it exists, indicate so
                Student s = GUIHelper.fetchStudent(query);
                searchStudentOutput.setText("Match found: " + s.getFName() + " " + s.getLName());
            }
            else //Otherwise indicate no student was found
                searchStudentOutput.setText("No matches found");
        }
        
        //Source is remove button for search student
        else if(e.getSource() == removeSearchStudentButton){
            //Get text from search box
            String query = searchStudentText.getText();
            if(GUIHelper.lookupStudent(query)){ //Lookup student
                //If student exists, try to enroll and indicate success
                if(GUIHelper.removeStudent(query)){
                    searchStudentOutput.setText("Student removed successfully"); //update output box
                    removed.add(query); //add to removed list
                    populateStudentSelector(); //update list
                }
                else
                    searchStudentOutput.setText("Error occured, could not remove student");
            }
            else //Otherwise indicate that the current search query does not yeild a result
                searchStudentOutput.setText("Student ID not found, no student removed");
            
        }
        
        //Source is remove selected student
        else if(e.getSource() == removeSelectedStudentButton){
            //Get selection from box
            String selection = (String)selectStudentBox.getSelectedItem();
            //Trim it to get just the student ID (last 9 digits)
            String id = selection.substring(selection.length()- 9);
            //try to remove and indicate result
            if(GUIHelper.removeStudent(id)){
                selectedStudentOutput.setText("Student removed successfully");  
                removed.add(id); // add to removed list
                populateStudentSelector(); //update list
            }
            else
                selectedStudentOutput.setText("Error occured, could not remove student");
        }
    
        //Source is search course
        else if(e.getSource() == searchCourseButton){
            //Get text from search box
            String query = searchCourseText.getText();
            //Lookup this course
            boolean exists = GUIHelper.lookupCourse(query);
            if(exists){ //If it exists, indicate so
                Course c = GUIHelper.fetchCourse(query);
                searchCourseOutput.setText("Match found: " + c.getName());
            }
            else //Otherwise indicate no course was found
                searchCourseOutput.setText("No matches found");
        }
        
        //Source is remove button for search course
        else if(e.getSource() == removeCourseSearchButton){
            //Get text from search box
            String query = searchCourseText.getText();
            if(GUIHelper.lookupCourse(query)){ //Lookup student
                //If course exists, try to enroll and indicate success
                if(GUIHelper.removeCourse(query)){
                    searchCourseOutput.setText("Course removed successfully"); //update output box
                    populateCourseSelector(); //update list
                }
                else
                    searchCourseOutput.setText("Error occured, could not remove course");
            }
            else //Otherwise indicate that the current search query does not yeild a result
                searchCourseOutput.setText("Course code not found, no course removed");
            
        }
        
        //Source is remove selected course
        else if(e.getSource() == removeSelectedCourseButton){
            //Get selection from box
            String selection = (String)selectCourseBox.getSelectedItem();
            //try to remove and indicate result
            if(GUIHelper.removeCourse(selection)){
                selectedCourseOutput.setText("Course removed successfully");  
                populateCourseSelector(); //update list
            }
            else
                selectedCourseOutput.setText("Error occured, could not remove course");
        }        
        
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
        tabPane = new javax.swing.JTabbedPane();
        studentPanel = new javax.swing.JPanel();
        searchStudentLabel = new javax.swing.JLabel();
        searchStudentText = new javax.swing.JTextField();
        searchStudentButton = new javax.swing.JButton();
        removeSearchStudentButton = new javax.swing.JButton();
        searchStudentOutput = new javax.swing.JLabel();
        orStudentLabel = new javax.swing.JLabel();
        selectStudentLabel = new javax.swing.JLabel();
        selectStudentBox = new javax.swing.JComboBox();
        removeSelectedStudentButton = new javax.swing.JButton();
        selectedStudentOutput = new javax.swing.JLabel();
        coursePanel = new javax.swing.JPanel();
        searchCourseLabel = new javax.swing.JLabel();
        searchCourseText = new javax.swing.JTextField();
        searchCourseButton = new javax.swing.JButton();
        removeCourseSearchButton = new javax.swing.JButton();
        searchCourseOutput = new javax.swing.JLabel();
        orCourseLabel = new javax.swing.JLabel();
        selectCourseLabel = new javax.swing.JLabel();
        removeSelectedCourseButton = new javax.swing.JButton();
        selectCourseBox = new javax.swing.JComboBox();
        selectedCourseOutput = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        searchStudentLabel.setText("Search by Student ID");

        searchStudentText.setText("Enter Student ID Here");

        searchStudentButton.setText("Search");
        searchStudentButton.addActionListener(this);

        removeSearchStudentButton.setText("Remove");
        removeSearchStudentButton.addActionListener(this);

        searchStudentOutput.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        searchStudentOutput.setText("No Output");

        orStudentLabel.setText("- OR -");

        selectStudentLabel.setText("Select from List");

        removeSelectedStudentButton.setText("Remove");
        removeSelectedStudentButton.addActionListener(this);

        selectedStudentOutput.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        selectedStudentOutput.setText("No Output");

        javax.swing.GroupLayout studentPanelLayout = new javax.swing.GroupLayout(studentPanel);
        studentPanel.setLayout(studentPanelLayout);
        studentPanelLayout.setHorizontalGroup(
            studentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(studentPanelLayout.createSequentialGroup()
                .addGroup(studentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(studentPanelLayout.createSequentialGroup()
                        .addGroup(studentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(studentPanelLayout.createSequentialGroup()
                                .addGap(165, 165, 165)
                                .addComponent(orStudentLabel))
                            .addGroup(studentPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(selectStudentLabel)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(studentPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(studentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(studentPanelLayout.createSequentialGroup()
                                .addComponent(searchStudentLabel)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(studentPanelLayout.createSequentialGroup()
                                .addGroup(studentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(searchStudentText, javax.swing.GroupLayout.DEFAULT_SIZE, 298, Short.MAX_VALUE)
                                    .addComponent(searchStudentOutput, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(studentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(removeSearchStudentButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(searchStudentButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, studentPanelLayout.createSequentialGroup()
                                .addGroup(studentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(selectedStudentOutput, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(selectStudentBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(removeSelectedStudentButton)))))
                .addContainerGap())
        );
        studentPanelLayout.setVerticalGroup(
            studentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(studentPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(searchStudentLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(studentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(searchStudentText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(searchStudentButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(studentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(removeSearchStudentButton)
                    .addComponent(searchStudentOutput))
                .addGap(29, 29, 29)
                .addComponent(orStudentLabel)
                .addGap(18, 18, 18)
                .addComponent(selectStudentLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(studentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(selectStudentBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(removeSelectedStudentButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(selectedStudentOutput)
                .addContainerGap(12, Short.MAX_VALUE))
        );

        tabPane.addTab("Students", studentPanel);

        searchCourseLabel.setText("Search by Course Code");

        searchCourseText.setText("Enter Course Code Here");

        searchCourseButton.setText("Search");
        searchCourseButton.addActionListener(this);

        removeCourseSearchButton.setText("Remove");
        removeCourseSearchButton.addActionListener(this);

        searchCourseOutput.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        searchCourseOutput.setText("No Output");

        orCourseLabel.setText("- OR -");

        selectCourseLabel.setText("Select from List");

        removeSelectedCourseButton.setText("Remove");
        removeSelectedCourseButton.addActionListener(this);

        selectedCourseOutput.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        selectedCourseOutput.setText("No Output");

        javax.swing.GroupLayout coursePanelLayout = new javax.swing.GroupLayout(coursePanel);
        coursePanel.setLayout(coursePanelLayout);
        coursePanelLayout.setHorizontalGroup(
            coursePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(coursePanelLayout.createSequentialGroup()
                .addGroup(coursePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(coursePanelLayout.createSequentialGroup()
                        .addGap(166, 166, 166)
                        .addComponent(orCourseLabel))
                    .addGroup(coursePanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(selectCourseLabel)))
                .addContainerGap(200, Short.MAX_VALUE))
            .addGroup(coursePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(coursePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(coursePanelLayout.createSequentialGroup()
                        .addComponent(searchCourseLabel)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(coursePanelLayout.createSequentialGroup()
                        .addGroup(coursePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(searchCourseOutput, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(searchCourseText, javax.swing.GroupLayout.DEFAULT_SIZE, 298, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(coursePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(removeCourseSearchButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(searchCourseButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, coursePanelLayout.createSequentialGroup()
                        .addGroup(coursePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(selectedCourseOutput, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(selectCourseBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(removeSelectedCourseButton))))
        );
        coursePanelLayout.setVerticalGroup(
            coursePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(coursePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(searchCourseLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(coursePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(searchCourseText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(searchCourseButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(coursePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(removeCourseSearchButton)
                    .addComponent(searchCourseOutput))
                .addGap(30, 30, 30)
                .addComponent(orCourseLabel)
                .addGap(18, 18, 18)
                .addComponent(selectCourseLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(coursePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(removeSelectedCourseButton)
                    .addComponent(selectCourseBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(selectedCourseOutput)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tabPane.addTab("Courses", coursePanel);

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabPane)
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabPane)
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
    private javax.swing.JPanel coursePanel;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JLabel orCourseLabel;
    private javax.swing.JLabel orStudentLabel;
    private javax.swing.JButton removeCourseSearchButton;
    private javax.swing.JButton removeSearchStudentButton;
    private javax.swing.JButton removeSelectedCourseButton;
    private javax.swing.JButton removeSelectedStudentButton;
    private javax.swing.JButton searchCourseButton;
    private javax.swing.JLabel searchCourseLabel;
    private javax.swing.JLabel searchCourseOutput;
    private javax.swing.JTextField searchCourseText;
    private javax.swing.JButton searchStudentButton;
    private javax.swing.JLabel searchStudentLabel;
    private javax.swing.JLabel searchStudentOutput;
    private javax.swing.JTextField searchStudentText;
    private javax.swing.JComboBox selectCourseBox;
    private javax.swing.JLabel selectCourseLabel;
    private javax.swing.JComboBox selectStudentBox;
    private javax.swing.JLabel selectStudentLabel;
    private javax.swing.JLabel selectedCourseOutput;
    private javax.swing.JLabel selectedStudentOutput;
    private javax.swing.JPanel studentPanel;
    private javax.swing.JTabbedPane tabPane;
    // End of variables declaration//GEN-END:variables

    /**
     * Method to populate student selector box with students from database
     */
    private void populateStudentSelector() {
        try {
            
            selectStudentBox.removeAllItems(); //Empty the box
            //Get list of students from database
            LinkedList students = PersistenceManager.readStudents();
            //If it has students, add all of them
            if(students.size() != 0){
                Iterator it = students.iterator(); //Get list iterator
                while(it.hasNext()){                //Loop through
                    Student s = (Student)it.next(); //Get current student
                    //Create input text for selection box
                    String input = s.getLName() + ", " + s.getFName() + " - " + s.getStudentNum();
                    selectStudentBox.addItem(input); //Add it
                }
            }
            else //If list was empty, put a message in the box
                selectStudentBox.addItem("No students found");
            
            //If any errors are thrown catch them and print a message in the box
        } catch (IOException | ClassNotFoundException ex) {
            selectStudentBox.addItem("No students found");
        }
    }
 
    /**
     * Method to populate the course selector within the GUI
     */
    private void populateCourseSelector() {
        try {
            
            selectCourseBox.removeAllItems(); //Empty the box
            //Get list of course codes from database
            LinkedList courses = PersistenceManager.readCourses();
            //If it has courses, add all of them
            if(courses.size() != 0){
                Iterator it = courses.iterator(); //Get list iterator
                while(it.hasNext()){                //Loop through
                    selectCourseBox.addItem((String)it.next()); //Add course code
                }
            }
            else //If list was empty, put a message in the box
                selectCourseBox.addItem("No courses found");
            
            //If any errors are thrown catch them and print a message in the box
        } catch (IOException | ClassNotFoundException ex) {
            selectCourseBox.addItem("No courses found");
        }
    }    
    
    
}
