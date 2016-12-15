package cs2212.team7;

import java.lang.ArithmeticException;
import java.util.LinkedList;

import org.junit.Before;
import junit.framework.Assert;
import org.junit.Test;

/**
 * Created by silent on 3/17/14.
 */
public class PersistenceManagerTest {

    private LinkedList<Student> students;

    @Before
    public void createStudents() {
        LinkedList<Student> students = new LinkedList<Student>();
        students.add(new Student("fname1", "lname1", "fname1@uwo.ca", 250600001));
        students.add(new Student("fname2", "lname2", "fname2@uwo.ca", 250600002));
        students.add(new Student("fname3", "lname3", "fname3@uwo.ca", 250600003));
    }

    public void testwriteStudentNullStudent() {

    }



}
