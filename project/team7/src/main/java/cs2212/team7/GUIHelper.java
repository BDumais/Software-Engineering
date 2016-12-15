package cs2212.team7;

import java.io.IOException;

/**
 * Helper class for dealing with database functionality
 *  - Allows for adding and removing of courses and students from within GUIs
 *  - This class was created to allow unit testing and to keep back end functionality
 *      out of GUI classes while simplifying and reducing the logic in GUIs
 * @author Ben
 */
public class GUIHelper {

    /**
     * Method to fetch a student from the database
     * @param id string of id being looked up
     * @return Student object found
     */
    public static Student fetchStudent(String id){
        try { //Try to fetch this student
            Student s;
            long longId= Long.parseLong(id); //Convert id string into a long
            s = PersistenceManager.readStudent(longId); //Read student from database
            return s; //return result
        } catch (IOException | ClassNotFoundException | NumberFormatException ex) {
            return null; //If any errors are thrown, return null
        }
    }
    
    /**
     * Method to look up a student in the database
     * @param id string of id being looked up
     * @return boolean result of search
     */
    public static boolean lookupStudent(String id){
        try { //Try to look this student up
            long longId = Long.parseLong(id); //Convert id string into a long
            Student s = PersistenceManager.readStudent(longId); //Read the student in
            return s != null; //Return whether this student is null (true if it found something)
        } catch (IOException | ClassNotFoundException | NumberFormatException ex) {
            return false; //Return false if any exceptions are thrown
        }

    }
    
    /**
     * Method to add student via enroll existing
     * @param id id of student being added
     * @param c course student is being added to
     * @return boolean result of if student was added
     */
    public static boolean enroll(String id, Course c){
        long longId = Long.parseLong(id); //Parse id
        if(c.hasStudent(longId)) //If student already exists within course return false
            return false;
        else{ //Otherwise add the student and return true
            c.addStudent(fetchStudent(id));
            return true;
        }
    }
    
    /**
     * Method to remove student from database via GUI
     * @param id student id to be removed
     * @return boolean result of removal
     */
    public static boolean removeStudent(String id){
        try {
            long longId = Long.parseLong(id); //Parse id
            PersistenceManager.removeStudent(id); //Try to remove from database
            return true; //return true if no errors encountered
        } catch (IOException | ClassNotFoundException | GradebookException ex) {
            return false; //Otherwise something went wrong so return false
        }
    }
    
    /**
     * Method to remove a course via GUI
     * @param code code of course to be removed
     * @return boolean result of removal
     */
    public static boolean removeCourse(String code){
        try{
            PersistenceManager.removeCourse(code); //try to remove course
            return true; //return true if no error thrown
        } catch(IOException | ClassNotFoundException | GradebookException ex){
            return false; //return false if an error occured
        }
    }
    
    /**
     * Method to lookup a course in the database
     * @param code code to be used in search
     * @return boolean result of search
     */
    public static boolean lookupCourse(String code){
        try { //Try to look this student up
            Course c = PersistenceManager.readCourse(code); //Read the course in
            return c != null; //Return whether this course is null (true if it found something)
        } catch (IOException | ClassNotFoundException | GradebookException ex) {
            return false; //Return false if any exceptions are thrown
        }
    }    
    
    /**
     * Method to find a course and return via GUI
     * @param code code to be searched
     * @return the course found
     */
    public static Course fetchCourse(String code){
        try { //Try to fetch this course
            Course c;
            c = PersistenceManager.readCourse(code); //Read student from database
            return c; //return result
        } catch (IOException | ClassNotFoundException | GradebookException ex) {
            return null; //If any errors are thrown, return null
        }
    }
    
    /**
     * Method to determine if student exists within course
     * @param id id of student
     * @param c course student is being checked against
     * @return boolean result
     */
    public static boolean inCourse(long id, Course c){
        return c.hasStudent(id);
    }
    
    
}
