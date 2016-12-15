package cs2212.team7;

import java.io.*;
import java.util.*;

/**
 * This class contains tools for managing the data persistence of the gradebook app.
 *
 * Created by Nick DelBen
 * Created on 3/2/14
 * Version 1.0
 * Bugs: None known
 */
public class PersistenceManager {

    //Streams for file access
    private static ObjectOutputStream out;
    private static ObjectInputStream in;

    //The file that stores the database of courses.
    public static final String FILE_COURSES_DATABASE = "courses_data.dat";

    //The file that stores the database of students.
    public static final String FILE_STUDENTS_DATABASE = "students_data.dat";

    /**
     * Gets the specified course from the database.
     *
     * @param courseName_in the name of the course to be grabbed.
     * @return the course if it exists.
     * @throws IOException if there is an issue opening the file the database is stored in.
     * @throws ClassNotFoundException if there is no dictionary to hold the data.
     * @throws cs2212.team7.GradebookException
     */
    public static Course readCourse(String courseName_in) throws IOException, ClassNotFoundException, GradebookException {
        //Create an input stream for the student course data file.
        in = new ObjectInputStream(new FileInputStream(FILE_COURSES_DATABASE));
        //Read the data and cast it to a Dictionary of courses.
        HashMap<String,Course> courseBank = (HashMap<String,Course>) in.readObject();
        //Close the file input stream
        in.close();
        //Search for the course in the dictionary.
        Course result = courseBank.get(courseName_in);
        if (result == null ) {
            throw new GradebookException(GradebookException.COURSE_NOT_FOUND);
        }
        return result;
    }

    /**
     * Gets a list of students corresponding to the collection of student numbers specified.
     * The students that were not found in the dictionary are replaced by null.
     *
     * @param studentNumbers_in the students numbers to retrieve.
     * @return a list of students attached to the specified numbers.
     * @throws IOException if there is an issue accessing the student database file.
     * @throws ClassNotFoundException if there is an issue casting to the dictionary.
     */
    public static LinkedList<Student> readStudents(Collection<Long> studentNumbers_in) throws IOException, ClassNotFoundException {
        //Create an input stream for the student data file
        in = new ObjectInputStream(new FileInputStream(FILE_STUDENTS_DATABASE));
        //Read the data and cast it to a Dictionary of students.
        HashMap<Long,Student> studentBank = (HashMap<Long,Student>) in.readObject();
        //Close the input stream
        in.close();
        //Populate the collection of students to be returned.
        LinkedList<Student> result = new LinkedList<>();
        for (long currentStudentNumber : studentNumbers_in) {
            result.add(studentBank.get(currentStudentNumber));
        }
        return result;
    }


    /**
     * Method to return list of ALL students in database
     * @return list containing all students in the database
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static LinkedList<Student> readStudents() throws IOException, ClassNotFoundException {
        try{
            //Create an input stream for the student data file
            in = new ObjectInputStream(new FileInputStream(FILE_STUDENTS_DATABASE));
            //Read the data and cast it to a Dictionary of students.
            HashMap<Long,Student> studentBank = (HashMap<Long,Student>) in.readObject();
            //Close the input stream
            in.close();
            //Populate the collection of students to be returned.
            LinkedList<Student> result = new LinkedList(studentBank.values());
            return result;
        } catch(FileNotFoundException ex){  //If file does not exist, create it and retry
            createStudentFile();
            return readStudents();
        }
    }



    /**
     * Gets a student from the database with the specified student number.
     *
     * @param studentNumber_in the student number of the target student.
     * @return the student matching the specified student if it exists, otherwise null.
     * @throws IOException if there is an issue opening the student database file.
     * @throws ClassNotFoundException if there is an issue casting to a dictionary of students.
     */
    public static Student readStudent(Long studentNumber_in) throws IOException, ClassNotFoundException {
        //Create an input stream for the student data file
        in = new ObjectInputStream(new FileInputStream(FILE_STUDENTS_DATABASE));
        //Read the data and cast it to a Dictionary of students.
        HashMap<Long,Student> studentBank = (HashMap<Long,Student>) in.readObject();
        //Close the input file stream
        in.close();
        //Return the requested student.
        return studentBank.get(studentNumber_in);
    }

    /**
     * Writes a course to the course database file.
     *
     * @param course_in the course to be written to the file.
     * @throws IOException if there is an issue accessing the course database file.
     * @throws ClassNotFoundException if there is an issue casting to a dictionary.
     */
    public static void writeCourse(Course course_in) throws IOException, ClassNotFoundException {
        try{ //Create an input stream for the student course data file.
            in = new ObjectInputStream(new FileInputStream(FILE_COURSES_DATABASE));
            //Read the data and cast it to a Dictionary of courses.
            HashMap<String,Course> courseBank = (HashMap<String,Course>) in.readObject();
            //Close the input stream
            in.close();
            //Add the current course to the course database.
            courseBank.put(course_in.getCode(), course_in);
            //Create an output stream for the course bank file database file.
            FileOutputStream FOS = new FileOutputStream(FILE_COURSES_DATABASE); //Create file stream
            FOS.write(new String().getBytes()); //clear file
            out = new ObjectOutputStream(FOS); //Initialize object stream
            //Write the course bank to the proper output file.
            out.writeObject(courseBank);
            out.flush();
            out.close();
        }
        catch(EOFException e){   //If writing to a blank file, ie no courses exist, create a new course list and write that
            out = new ObjectOutputStream(new FileOutputStream(FILE_COURSES_DATABASE));
            HashMap<String, Course> courseBank = new HashMap<>();
            courseBank.put(course_in.getCode(), course_in);
            out.writeObject(courseBank);
            out.flush();
            out.close();
        }
        //If an error is thrown because the data file does not exist, create the file
        catch(FileNotFoundException e){
            createCourseFile();
            writeCourse(course_in); //Retry method
        }
    }

    /**
     * Method to return a list of all course code in the database
     * @return list of string containing course codes
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static LinkedList<String> readCourses() throws IOException, ClassNotFoundException {
        //Create an input stream for the student data file
        in = new ObjectInputStream(new FileInputStream(FILE_COURSES_DATABASE));
        //Read the data and cast it to a Dictionary of students.
        HashMap<String, Course> courseBank = (HashMap<String, Course>) in.readObject();
        //Close the input stream
        in.close();
        //Populate the collection of students to be returned.
        LinkedList<String> result = new LinkedList<>();
        for (String s : courseBank.keySet()) {
            result.add(s);
        }
        return result;
    }

    /**
     * Removes the specified course from the database.
     *
     * @param courseCode_in the code corresponding to the course.
     * @throws IOException if there is an issue accessing the course database file
     * @throws ClassNotFoundException if there is corrupt data in the file.
     * @throws GradebookException if that course is not in the database.
     */
    public static void removeCourse(String courseCode_in) throws IOException, ClassNotFoundException, GradebookException {
        //Create an input stream for the student course data file.
        in = new ObjectInputStream(new FileInputStream(FILE_COURSES_DATABASE));
        //Read the data and cast it to a Dictionary of courses.
        HashMap<String,Course> courseBank = (HashMap<String,Course>) in.readObject();
        //Close the input file stream.
        in.close();
        //Remove the input item from the database.
        if (courseBank.remove(courseCode_in) == null) {
            //If there was no item removed throw exception.
            throw new GradebookException(GradebookException.COURSE_NOT_FOUND);
        }
        //Create an output stream for the course bank file database file.
        FileOutputStream FOS = new FileOutputStream(FILE_COURSES_DATABASE); //Create file stream
        FOS.write(new String().getBytes()); //clear file
        out = new ObjectOutputStream(FOS); //Initialize object stream
        out.writeObject(courseBank);         //Write the course bank to the proper output file.
        out.flush();
        out.close();
    }

    /**
     * Removes the specified student from the database
     *
     * @param studentID_in student number of student being removed
     * @throws IOException if there is an issue accessing the student database file
     * @throws ClassNotFoundException if there is corrupt data in the file
     * @throws GradebookException if the student is not in the database
     */
    public static void removeStudent(String studentID_in) throws IOException, ClassNotFoundException, GradebookException {
        //Create an input stream for the student course data file.
        in = new ObjectInputStream(new FileInputStream(FILE_STUDENTS_DATABASE));
        //Read the data and cast it to a Dictionary of students.
        HashMap<Long,Student> studentBank = (HashMap<Long,Student>) in.readObject();
        //Close the input file stream.
        in.close();
        //Remove the input item from the database.
        long id = Long.parseLong(studentID_in);
        if (studentBank.remove(id) == null) {
            //If there was no item removed throw exception.
            throw new GradebookException(GradebookException.STUDENT_NOT_FOUND);
        }
        //Create an output stream for the student bank file database file.
        FileOutputStream FOS = new FileOutputStream(FILE_STUDENTS_DATABASE); //Create file stream
        FOS.write(new String().getBytes()); //clear file
        out = new ObjectOutputStream(FOS); //Initialize object stream
        out.writeObject(studentBank); //Write the student bank to the proper output file.
        out.flush();
        out.close();
    }

    /**
     * Method to write a student to the database
     * @param student_in student object being written
     * @throws IOException if there is an issue accessing the student database file
     * @throws ClassNotFoundException if there is corrupt data in the file
     */
    public static void writeStudent(Student student_in) throws IOException, ClassNotFoundException {
        try{
            //Create an input stream for the student course data file.
            in = new ObjectInputStream(new FileInputStream(FILE_STUDENTS_DATABASE));
            //Read the data and cast it to a Dictionary of courses.
            HashMap<Long,Student> studentBank = (HashMap<Long,Student>) in.readObject();
            //Close the input stream
            in.close();
            //Add the current course to the course database.
            studentBank.put(student_in.getStudentNum(), student_in);
            //Create an output stream for the course bank file database file.
            FileOutputStream FOS = new FileOutputStream(FILE_STUDENTS_DATABASE); //Create file stream
            FOS.write(new String().getBytes()); //clear file
            out = new ObjectOutputStream(FOS); //Initialize object stream
            //Write the course bank to the proper output file.
            out.writeObject(studentBank);
            out.flush();
            //Close the output file.
            out.close();
        } catch(EOFException e){ //If writing to a blank file, ie no student base exists, create a new one
            FileOutputStream FOS = new FileOutputStream(FILE_STUDENTS_DATABASE); //Create file stream
            FOS.write(new String().getBytes()); //clear file
            out = new ObjectOutputStream(FOS); //Initialize object stream
            HashMap<Long,Student> stuBank = new HashMap<>();
            stuBank.put(student_in.getStudentNum(), student_in);
            out.writeObject(stuBank);
            out.flush();
            out.close();
        }
        catch(FileNotFoundException e){ //If file does not exist, create it and retry the method
            createStudentFile();
            writeStudent(student_in); //Retry method
        }
    }

    /**
     * Method to create course data file
     * @throws IOException
     */
    public static void createCourseFile() throws IOException{
        File f = new File(FILE_COURSES_DATABASE);
        f.createNewFile();
    }

    /**
     * Method to create student data file
     * @throws IOException
     */
    public static void createStudentFile() throws IOException{
        File f = new File(FILE_STUDENTS_DATABASE);
        f.createNewFile();
    }
}
