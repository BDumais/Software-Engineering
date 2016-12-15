/**
 *
 * Version 2.0
 * Bugs: None known
 * Last Edit: Ben
 *
 *  Main application for the project. Represents the application as a whole and allows user to
 *  interact with courses (and by extension students and other objects of interest)
 *
 *  Written by Team 7 for CS2212-w2014
 *
 */

package cs2212.team7;

/* Imports */

import java.io.File;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;


public class App {

    //Represents a response in which the user closed the dialog
    public static final Integer DIALOG_CLOSED_RESPONSE = -1;
    public static final Integer OKAY = 0;

    //The options for the close file confirm dialog.
    public static final String[] DIALOG_MESSAGES_CLOSE_COURSE = {"Save & Close", "Close Without Saving","Cancel"};
    public static final Integer  DIALOG_OPTIONS_CLOSE_SAVE    = 0;
    public static final Integer  DIALOG_OPTIONS_CLOSE_FORCE   = 1;
    public static final String   DIALOG_OPTIONS_CLOSE_MESSAGE = "You have chosen to close the current course.";
    public static final String   DIALOG_OPTIONS_CLOSE_TITLE   = "Confirm Course Close";

    //The options for the delete file confirm dialog.
    public static final String[] DIALOG_MESSAGES_DELETE_COURSE = {"Confirm", "Cancel"};
    public static final Integer  DIALOG_OPTIONS_DELETE_CONFIRM = 0;
    public static final String   DIALOG_OPTIONS_DELETE_MESSAGE = "You have chosen to delete the active course. \n! WARNING !\nThis can not be undone.";
    public static final String   DIALOG_OPTIONS_DELETE_TITLE   = "Confirm Course Delete";

    //Constants for import and export type
    private static final Integer STUDENTS = 0;
    private static final Integer GRADES = 1;

    /* Attributes */
    private Course activeCourse;

    /**
     * Main class for the gradebook application
     *
     * @param args arguments passed to program
     */
    public static void main(String[] args) {
        //TODO: Somehow get the course the user would like to get open at the start. For now I will set the number.

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                MainWindow window = new MainWindow(new App());
                window.setVisible(true);
            }
        });
    }




    /* Methods */



    /**
     * Opens a course, making it the currently active course,
     *
     * @param courseCode_in the title of the course to be loaded.
     * @throws GradebookException if the course cant be opened.
     */
    public void openCourse(String courseCode_in) throws GradebookException {
        //Check if there is a course open
        if (closeActiveCourse()) {
            //If there is no course active load the specified course.
            loadCourse(courseCode_in);
        } else {
            //If there is an active course
            throw new GradebookException(GradebookException.COURSE_CURRENTLY_ACTIVE);
        }
    }

    /**
     * Loads a course from the database to the currently active course.
     *
     * @param courseCode_in the code of the course to be loaded.
     * @throws GradebookException if there is an error loading the course.
     */
    private void loadCourse(String courseCode_in) throws GradebookException {
        Course newCourse;
        try {
            newCourse = PersistenceManager.readCourse(courseCode_in);
        } catch (ClassNotFoundException e) {
            throw new GradebookException(GradebookException.CLASS_NOT_FOUND);
        } catch (IOException e) {
            throw new GradebookException(GradebookException.FILE_IO_ERROR);
        }
        setActiveCourse(newCourse);
    }

    /**
     * Closes the currently active course.
     *
     * @return true if the active course was closed, otherwise false.
     * @throws GradebookException if there is an error saving the active course.
     */
    public boolean closeActiveCourse() throws GradebookException {

        if(activeCourse != null)
            saveActiveCourse();

        setActiveCourse(null);
        return true;
    }

    /**
     * Saves the currently active course to the database.
     *
     * @throws GradebookException if there is an error saving the course.
     */
    public void saveActiveCourse() throws GradebookException {
        try {
            PersistenceManager.writeCourse(getActiveCourse());
        } catch (ClassNotFoundException e) {
            throw new GradebookException(GradebookException.CLASS_NOT_FOUND);
        } catch (IOException e) {
            throw new GradebookException(GradebookException.FILE_IO_ERROR);
        }
    }

    /**
     * Closes and removes the currently active course from the course database.
     *
     * @throws GradebookException if there is an error removing the course.
     */
    public void deleteActiveCourse() throws GradebookException {
        //Confirm the user wishes to delete the file,
        Integer response = JOptionPane.showOptionDialog(null, DIALOG_OPTIONS_DELETE_MESSAGE, DIALOG_OPTIONS_DELETE_TITLE, JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, DIALOG_MESSAGES_DELETE_COURSE, DIALOG_MESSAGES_DELETE_COURSE[0]);
        //If they wish to delete the entry remove it from the file.
        if (response == DIALOG_OPTIONS_DELETE_CONFIRM) {
            try {
                PersistenceManager.removeCourse(getActiveCourse().getCode());
            } catch (IOException e) {
                throw new GradebookException(GradebookException.FILE_IO_ERROR);
            } catch (ClassNotFoundException e) {
                throw new GradebookException(GradebookException.CLASS_NOT_FOUND);
            } catch (GradebookException e) {
                throw new GradebookException(GradebookException.COURSE_NOT_FOUND);
            }
        }
    }

    public void removeStudents(LinkedList<Student> students){
        for(Student s : students){
            activeCourse.removeStudent(s.getStudentNum());
        }
    }

    /**
     * Method to prompt user for course information and create new course in database
     */
    public void addNewCourse(){
        CourseGUI gui = new CourseGUI();
        JPanel panel = gui.getPanel();
        boolean valid = false;

        while(!valid){
            //Show popup
            int response = JOptionPane.showOptionDialog(null, panel, "Create New Course", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, new String[] {"Okay", "Cancel"}, "Okay");

            //If user selected Okay
            if(response == OKAY){
                //Get data from fields
                String title = gui.getTitleText();
                String code = gui.getCodeText();
                String term = gui.getTermText();

                String result = checkValidCourse(title,code,term);
                //Check if they're all valid
                if(result == null){
                    //If so create a new course and add to database
                    Course c = new Course(title, term, code);
                    try { PersistenceManager.writeCourse(c); }
                    catch (IOException | ClassNotFoundException ex) {
                        System.err.println("Error adding new course to database");
                        Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    //Exit the loop
                    valid = true;
                }
                else
                    gui.setErrorMessage(result);
            }
            //If user exited or canceled the popup exit loop
            else
                valid = true;
        }

    }
    /**
     * Method to create new student and add to active course
     * @return new student object
     */
    public Student addNewStudent(){
        //Create GUI, panel, return object and loop check
        StudentGUI gui = new StudentGUI();
        JPanel panel = gui.getPanel();
        Student s = null;
        boolean valid = false;

        //Loop until exited or valid input is found
        while(!valid){

            //Show GUI and get response
            int response = JOptionPane.showOptionDialog(null, panel, "Create New Student", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, new String[] {"Okay", "Cancel"}, "Okay");

            //If user hit "Okay"
            if(response == OKAY){
                //Get new data from the GUI
                String first = gui.getFNameText();
                String last = gui.getLNameText();
                String email = gui.getEmailText();
                String id = gui.getStudentNumText();

                String result = checkValidStudent(first, last, email, id);

                //Check if they're valid
                if(result == null){
                    //If they are, create new student and add to course
                    s = new Student(first, last, email, Long.parseLong(id));
                    activeCourse.addStudent(s);
                    //Try to add new student to the database
                    try {
                        PersistenceManager.writeStudent(s);
                    } catch (IOException | ClassNotFoundException ex) {
                        System.err.println("Error writing new student");
                    }
                    //Exit loop
                    valid = true;
                }
                else
                    gui.setErrorMessage(result);
            }
            //If they hit cancel or closed the popup, exit loop
            else
                valid = true;
        }
        //Return the student (may be null)
        return s;

    }

    /**
     * Method to add a student from database
     */
    public void addExistingStudent(){
        //Create GUI with the active course
        StudentSearchGUI gui = new StudentSearchGUI(activeCourse);
        JPanel panel = gui.getPanel();

        //Show GUI (GUI adds users via StudentHelper class, which contains all methods)
        JOptionPane.showOptionDialog(null, panel, "Add Existing Student", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, new String[] {"Done"}, "Done");

    }
    /**
     * Method to create new deliverable and add to active course
     */
    public void addNewDeliverable(){

        //Initialize GUI, panel and loop check
        DeliverableGUI gui = new DeliverableGUI();
        JPanel panel = gui.getPanel();
        boolean valid = false;

        while(!valid){
            //Create popup and get the response
            int response = JOptionPane.showOptionDialog(null, panel ,"Create New Deliverable", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, new String[] {"Okay", "Cancel"}, "Okay");
            //If user hit "Okay"
            if(response == OKAY){
                //Get the new fields from the gui
                String name = gui.getNameText();
                String weight = gui.getWeightText();
                String type = gui.getTypeText();

                String result = checkValidDeliverable(name, weight);

                //check if they're valid
                if(result == null){
                    try {
                        valid = true;
                        //If they are, create new deliverable and add to current course
                        activeCourse.addDeliverable(new Deliverable(name, Integer.parseInt(weight), type, activeCourse));
                    } catch (DeliverableException ex) { //Catch an invalid weight exception
                        gui.setErrorMessage(ex.getMessage());
                        valid = false;
                    }

                }
                else
                    gui.setErrorMessage(result);
            }
            //Otherwise if they hit cancel or closed the window exit loop
            else
                valid = true;
        }

    }

    /**
     * Method to edit the active course
     */
    public void editCourse(){
        //Try to remove and then re-add the course from the database
        try {

            //Remove course from database
            PersistenceManager.removeCourse(activeCourse.getCode()); //Remove course

            //Create gui for popup window and set fields
            CourseGUI gui = new CourseGUI();
            gui.setTitleText(activeCourse.getName());
            gui.setCodeText(activeCourse.getCode());
            gui.setTermText(activeCourse.getTerm());

            //Get the panel from the GUI
            JPanel panel = gui.getPanel();

            boolean valid = false;

            while(!valid){
                //Show popup
                int response = JOptionPane.showOptionDialog(null, panel, "Edit Course", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, new String[] {"Okay", "Cancel"}, "Okay");

                if(response == OKAY){
                    String title = gui.getTitleText();
                    String code = gui.getCodeText();
                    String term = gui.getTermText();

                    String result = checkValidCourse(title, code, term);

                    if(result == null){
                        activeCourse.setName(title);
                        activeCourse.setCode(code);
                        activeCourse.setTerm(term);
                        valid = true;
                    }
                    else
                        gui.setErrorMessage(result);
                }
                else
                    valid = true;
            }

            //Re-add course
            PersistenceManager.writeCourse(activeCourse); //Re-add Course

        } catch (IOException | ClassNotFoundException | GradebookException ex) {
            System.err.println("Error editing course");
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Method to edit a student object
     * @param s student being edited
     */
    public void editStudent(Student s){
        //Try to remove student from database
        try {
            PersistenceManager.removeStudent(Long.toString(s.getStudentNum()));
        } catch (IOException | ClassNotFoundException | GradebookException ex) {
            System.err.println("Error removing student from database during editing");
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }

        //Create GUI for popup
        StudentGUI gui = new StudentGUI();

        //Set the appropriate fields to current data
        gui.setFNameText(s.getFName());
        gui.setLNameText(s.getLName());
        gui.setEmailText(s.getEmail());
        gui.setStudentNumText(Long.toString(s.getStudentNum()));

        //Get the panel
        JPanel panel = gui.getPanel();

        //Create loop check
        boolean valid = false;

        //Loop until exited or valid input is found
        while(!valid){

            //Show GUI and get response
            int response = JOptionPane.showOptionDialog(null, panel, "Edit Student", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, new String[] {"Okay", "Cancel"}, "Okay");

            //If user hit "Okay"
            if(response == OKAY){
                //Get new data from the GUI
                String first = gui.getFNameText();
                String last = gui.getLNameText();
                String email = gui.getEmailText();
                String id = gui.getStudentNumText();

                String result = checkValidStudent(first, last, email, id);

                //Check if they're valid
                if(result == null){
                    //If they are, set the new data for this object
                    s.setFName(first);
                    s.setLName(last);
                    s.setEmail(email);
                    s.setStudentNum(Long.parseLong(id));
                    //Exit loop
                    valid = true;
                }
                else
                    gui.setErrorMessage(result);
            }
            //If they hit cancel or closed the popup, exit loop
            else
                valid = true;

        }
        //Try to re-add student to database
        try {
            PersistenceManager.writeStudent(s);
        } catch (IOException | ClassNotFoundException ex) {
            System.err.println("Error writing edited student");
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Method to edit a deliverable within a course
     * @param d deliverable being edited
     */
    public void editDeliverable(Deliverable d){

        //Initialize the popup
        DeliverableGUI gui = new DeliverableGUI();

        //Set fields to the current data from the deliverable
        gui.setNameText(d.getName());
        gui.setWeightText(Integer.toString(d.getWeight()));
        gui.setTypeBox(d.getType());

        //Get the panel
        JPanel panel = gui.getPanel();

        //Set loop check
        boolean valid = false;

        //Loop until exited or valid input is found
        while(!valid){
            //Create popup and get the response
            int response = JOptionPane.showOptionDialog(null, panel ,"Edit Deliverable", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, new String[] {"Okay", "Cancel"}, "Okay");
            //If user hit "Okay"
            if(response == OKAY){
                //Get the new fields from the gui
                String name = gui.getNameText();
                String weight = gui.getWeightText();
                String type = gui.getTypeText();

                String result = checkValidDeliverable(name, weight);

                //check if they're valid
                if(result == null){
                    //If they are, set the name weight and type to the new data
                    d.setName(name);
                    valid = true;
                    try { d.setWeight(Integer.parseInt(weight));}
                    catch (DeliverableException ex) {
                        gui.setErrorMessage(ex.getMessage());
                        valid = false; //Loop again
                    }
                    d.setType(type);
                }
                else
                    gui.setErrorMessage(result);
            }
            //Otherwise if they hit cancel or closed the window exit loop
            else
                valid = true;
        }
    }
     
     /**
      * Method to import students via CSV file
      */
     public String importStudents(){
         
         //Prompt user for file
         File f = CSVManager.filePathDialog(CSVManager.EXTENSION_FILTER_CSV);
         
         //If user did not choose a file, cancel the export
         if(f == null)
             return "Export students cancelled";
         
         //Initialize GUI with active course, which will set it to import students
         ImportGUI gui = new ImportGUI(activeCourse, STUDENTS, f);
         JPanel panel = gui.getPanel();
         
         //Show GUI, all functionality is handeled through CSVManager, GUI Helper and the gui
         JOptionPane.showOptionDialog(null, panel ,"Import Students from CSV", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, new String[] {"Done"}, "Done");

         return "Export students finished";
     }
     
     /**
      * Method to import list of grades via CSV file
      */
     public String importGrades(){
         
         //Get file from user
         File f = CSVManager.filePathDialog(CSVManager.EXTENSION_FILTER_CSV);
         
         if(f == null) //return if null
             return "Export Grades cancelled";
         
         //Initialize GUI
         ImportGUI gui = new ImportGUI(activeCourse, GRADES, f);
         JPanel panel = gui.getPanel();
         
         //Show import window
         JOptionPane.showOptionDialog(null, panel ,"Import Grades from CSV", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, new String[] {"Done"}, "Done");
 
         return "Export grades finished";
                 
     }
     
     /**
      * Method to open up database manager
      */
     public void databaseManager() {
         //Initialize GUI and panel
         DatabaseManagerGUI gui = new DatabaseManagerGUI();
         JPanel panel = gui.getPanel();
         
         //Show GUI, all functionality is handeled through GUI Helper and the gui itself
         JOptionPane.showOptionDialog(null, panel ,"Manage Databases", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, new String[] {"Done"}, "Done");
 
         //Unenroll any students that were removed from any courses they may be in
        LinkedList<String> removed = gui.getRemoved();
        for(String id : removed){ //Loop through all removed students
            long longId = Long.parseLong(id); //Parse the long
            removeFromAllCourses(longId);
        }
    }


    //Getters / Setters
    public  Course getActiveCourse() {return activeCourse;}
    public void setActiveCourse(Course course_in) {this.activeCourse = course_in;}

    /* Methods to display different popup messages */
    //Popup to display options
    private int getDesiredOption(String message, String title, String[] options){
        return JOptionPane.showOptionDialog(null, message, title, JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[0]);
    }
    /**
     * Method to check input for a course, ie see if all input is valid
     * @param title title to be checked
     * @param code code to be checked
     * @param term term to be checked
     * @return boolean result of validity
     */
    private String checkValidCourse(String title, String code, String term){
        //Check length of title
        if(title.length() < 8){ //Too short, display message
            return "Course Title is too short, must be at least 8 characters";
        }
        else if(title.length() > 50){//Too long, display message
            return "Course Title is too long, must be at most 50 characters";
        }

        //Check if code adheres to correct format LLDDDD
        if(code.length() != 6){
            return "Course Code is not 6 characters long";
        }
        else if(!Character.isLetter(code.charAt(0)) || !Character.isLetter(code.charAt(1))
                || !Character.isDigit(code.charAt(2)) || !Character.isDigit(code.charAt(3))
                || !Character.isDigit(code.charAt(4)) || !Character.isDigit(code.charAt(5))){
            return "Course Code format is invalid: LLDDDD Ex. CS2212";
        }

        //Check length of term
        if(term.length() < 4){
            return "Course Term must be at least 4 characters long";
        }
        else if(term.length() > 18){
            return "Course Term must be a maximum of 18 characters long";
        }

        //Now we check if there is already a course with this code (the key)
        try {
            //Get the result of reading the course in
            Course target = PersistenceManager.readCourse(code);
            //If no error is thrown, a course was returned and already exists with this code
            return "Course code already exists";
        } catch (IOException | ClassNotFoundException | GradebookException ex) {
            //Otherwise the code is valid
            return null;
        }
    }

    /**
     * Method to check the input for a student, ie if all input is valid
     * @param first first name to be checked
     * @param last last name to be checked
     * @param email email to be checked
     * @param preID string of id being checked
     * @param student student object being edited (for testing)
     * @return boolean result of validity
     */
    private String checkValidStudent(String first, String last, String email, String preID){

        //Check length of first name
        if(first.length() < 2){
            return "First name must be longer than 1 character";
        }

        //Check length of last name
        if(last.length() < 2){
            return "Last name must be longer than 1 character";
        }

        //Check length of email and if it contains an '@'
        if(email.length() < 5){
            return "Email must be at least 5 characters";
        }
        else if(containsEmail(email)){
            return "Another student has this email, please enter a unique email";
        }
        else {
            boolean containsAt = false, containsDot = false;
            for(int i=0; i < email.length(); i++){
                if(email.charAt(i) == '@')
                    containsAt = true;
                else if(email.charAt(i) == '.')
                    containsDot = true;
            }
            if(!containsAt || !containsDot){
                return "Email must contain @ character and . character";
            }
        }

        //Now we check the student ID
        try{
            //Check length of id (must be 9 digits)
            if(preID.length() != 9){
                return "Student Id must be 9 digits long";
            }
            //Attempt to parse the long
            long id = Long.parseLong(preID);
            //If no error was thrown, check if there already exists a student with this ID
            Student target = PersistenceManager.readStudent(id);
            //If null was returned there is no student with this ID and is valud
            if(target == null)
                return null;

            //Otherwise it returned a student with this ID, and its not valid
            return "Student ID already exists";

            //Catch formating exceptions and return false
        } catch(NumberFormatException ex){
            return "Invalid Student Number";
        }
        //If target is not found its a valid student ID
        catch (IOException | ClassNotFoundException ex) { return null; }

    }

    /**
     * Method to check validity of deliverable input
     * @param name name to be checked
     * @param weight weight to be checked
     * @return boolean result of validity
     */
    private String checkValidDeliverable(String name, String weight){

        //Check length of name
        if(name.length() > 30){
            return "Deliverable name is too long, max 30 characters";
        }
        else if(name.length() < 4){
            return "Deliverable name is too short, min 4 characters";
        }

        //Check the weight
        try{
            //Try to parse the integer
            int w = Integer.parseInt(weight);
            //If no error, check if its in bounds
            if(w > 100 || w < 0){
                return "Deliverable weight is invalid";
            }
            //if it passes, its valid
            return null;

            //If a formating error was thrown its invalid
        } catch(NumberFormatException ex){
            return "Deliverable weight is non-numeric or non-integer";
        }
    }

    /**
     * Method to remove all instances of a student from courses
     * Used when removing a student from database
     * @param id long of student id being removed
     */
    public void removeFromAllCourses(long id){
        try {

            //Get list of courses
            LinkedList<String> courses = PersistenceManager.readCourses();
            for(String code : courses){ //Loop through each course
                Course c = PersistenceManager.readCourse(code); //read course
                if(c.hasStudent(id)){ //If this course has the current student
                    c.removeStudent(id); //Unenroll this student\
                    PersistenceManager.writeCourse(c);
                }
            }

            //Catch any errors and print a message
        } catch (IOException | ClassNotFoundException | GradebookException ex) {
            System.err.println("Error occured attempting to read courses while removing student from all courses");
            Logger.getLogger(App.class.getName()).log(Level.WARNING, null, ex);
        }
    }

    /**
     * Method to check email against databank
     * @param email email being checked
     * @return boolean result of search
     */
    public static boolean containsEmail(String email){

        LinkedList<Student> students;

        try {
            //Read in students from database
            students = PersistenceManager.readStudents();
        } catch (IOException | ClassNotFoundException ex) { //If an error is thrown, print error and try to create file
            System.err.println("Error reading student list, datafile non-existant or corrupt\nCreating file...");
            try {   //Try to create file
                PersistenceManager.createStudentFile(); //Create student file and retry
            } catch (IOException ex1) { return false; } //If another error is thrown exit
            return false;
        }
        //Loop through student list
        for(Student s : students){
            //If we find an email that matches the input, return true
            if(s.getEmail().equals(email))
                return true;
        }
        return false; //Otherwise its non existing, so return
    }

    /**
     * Exports students to a csv file
     * @param students_in the students to be exported.
     * @throws java.io.IOException
     */
    public void exportStudents(LinkedList<Student> students_in) throws IOException {
        try{
            CSVManager.exportStudents(students_in, activeCourse.getCode()); //Export students
        } catch(AccessDeniedException ex){
            throw new IOException("Export cancelled: access to chosen path denied");
        }
    }

}//End of Class