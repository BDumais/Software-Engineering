package cs2212.team7;

import java.util.LinkedList;
import org.junit.Before;
import junit.framework.Assert;
import org.junit.Test;
import java.lang.reflect.*;

public class AppTest {

	App app;
	Course c;
	Deliverable d1;
	Deliverable d2;
	Student s1;
	Student s2;
	Student s3;
	Student s4;
	Student s5;
	Student s6;
	Student s7;
	Method checkValidCourse;
	Method checkValidStudent;
	Method checkValidDeliverable;

	@Before
	public void before(){
		app = new App();
		try{
			checkValidCourse = App.class.getDeclaredMethod("checkValidCourse", String.class, String.class, String.class);
			checkValidStudent = App.class.getDeclaredMethod("checkValidStudent", String.class, String.class, String.class, String.class);
			checkValidDeliverable = App.class.getDeclaredMethod("checkValidDeliverable", String.class, String.class);
		}
		catch(NoSuchMethodException e){
			System.err.println("No method found in AppTest.before()");
		}
		checkValidCourse.setAccessible(true);
		checkValidStudent.setAccessible(true);
		checkValidDeliverable.setAccessible(true);
		c = new Course("Name", "Term", "Code");
		d1 = new Deliverable("d1Name11", 50, "Assignment", c);
		d2 = new Deliverable("d2Name22", 50, "Exam", c);
		s1 = new Student("s1f", "s1l", "s1@mail.com", 111111111);
		s2 = new Student("s2f", "s2l", "s2@mail.com", 222222222);
		s3 = new Student("s3f", "s3l", "s3@mail.com", 333333333);
		s4 = new Student("s4f", "s4l", "s4@mail.com", 444444444);
		s5 = new Student("s5f", "s5l", "s5@mail.com", 555555555);
		s6 = new Student("s6f", "s6l", "s6@mail.com", 666666666);
		s7 = new Student("s7f", "s7l", "s7@mail.com", 777777777);
		try{
			c.addDeliverable(d1);
			c.addDeliverable(d2);
		}
		catch(DeliverableException e){
			System.err.println("Cannot add deliverable in AppTest.before()");
		}
		c.addStudent(s1);
		c.addStudent(s2);
		c.addStudent(s3);
		c.addStudent(s4);
		c.addStudent(s5);
		c.addStudent(s6);
		c.addStudent(s7);
		app.setActiveCourse(c);
	}

	@Test
	public void testRemoveStudentsFound(){
		LinkedList<Student> students = new LinkedList<Student>();
		students.add(s1);
		students.add(s2);
		students.add(s3);
		app.removeStudents(students);
		Assert.assertEquals(app.getActiveCourse().hasStudent(111111111), false);
		Assert.assertEquals(app.getActiveCourse().hasStudent(222222222), false);
		Assert.assertEquals(app.getActiveCourse().hasStudent(333333333), false);
	}

	@Test
	public void testRemoveStudentsItemInListNotFound(){
		LinkedList<Student> students = new LinkedList<Student>();
		students.add(s1);
		students.add(s2);
		students.add(new Student("s8f", "s8l", "s8@mail.com", 888888888));
		app.removeStudents(students);
		Assert.assertEquals(app.getActiveCourse().hasStudent(111111111), false);
		Assert.assertEquals(app.getActiveCourse().hasStudent(222222222), false);
		Assert.assertEquals(app.getActiveCourse().hasStudent(888888888), false);		
	}

	/**********************************************************
	 * checkValidCourse
	 **********************************************************/

	@Test
	public void testCheckValidCourseSuccess(){
		try{
			String res = (String)checkValidCourse.invoke(app, "1234567890", "ab1234", "1234567890");
                        boolean value = (res == null);  //Set value to whether or not the result is null
                        //A null string is returned only when input passes all checks
			Assert.assertEquals(value, true);
		}
		catch(Exception e){
			System.err.println("Exception in AppTest.testCheckValidCourseSuccess");
		}
	}

	@Test
	public void testCheckValidCourseTitleTooShort(){
		try{
			String res = (String)checkValidCourse.invoke(app, "1234567", "ab1234", "1234567890");
                        boolean value = (res == null);
                        //A non non string indicates that a check failed
                        //If value is not false, null was returned and checked failed
			Assert.assertEquals(value, false);
		}
		catch(Exception e){
			System.err.println("Exception in AppTest.testCheckValidCourseTitleTooShort");
		}
	}

	@Test
	public void testCheckValidCourseTitleTooLong(){
		try{
			String res = (String)checkValidCourse.invoke(app, "123456789", "ab1234", "123456789012345678901234567890123456789012345678901");
                        boolean value = (res == null);
			Assert.assertEquals(value, false);
		}
		catch(Exception e){
			System.err.println("Exception in AppTest.testCheckValidCourseTitleTooLong");
		}
	}

	@Test
	public void testCheckValidCourseCodeTooShort(){
		try{
			String res = (String)checkValidCourse.invoke(app, "12345678", "ab123", "1234567890");
			boolean value = (res == null);
			Assert.assertEquals(value, false);
		}
		catch(Exception e){
			System.err.println("Exception in AppTest.testCheckValidCourseCodeTooShort");
		}
	}

	@Test
	public void testCheckValidCourseCodeTooLong(){
		try{
			String res = (String)checkValidCourse.invoke(app, "12345678", "ab12345", "1234567890");
			boolean value = (res == null);
			Assert.assertEquals(value, false);
		}
		catch(Exception e){
			System.err.println("Exception in AppTest.testCheckValidCourseCodeTooLong");
		}
	}

	@Test
	public void testCheckValidCourseCodeIncorrectFormat(){
		try{
			String res = (String)checkValidCourse.invoke(app, "12345678", "001234", "1234567890");
			boolean value = (res == null);
			Assert.assertEquals(value, false);
		}
		catch(Exception e){
			System.err.println("Exception in AppTest.testCheckValidCourseIncorrectFormat");
		}
	}

	@Test
	public void testCheckValidCourseTermTooShort(){
		try{
			String res = (String)checkValidCourse.invoke(app, "12345678", "ab1234", "123");
			boolean value = (res == null);
			Assert.assertEquals(value, false);
		}
		catch(Exception e){
			System.err.println("Exception in AppTest.testCheckValidCourseTermTooShort");
		}
	}

	@Test
	public void testCheckValidCourseTermTooLong(){
		try{
			String res = (String)checkValidCourse.invoke(app, "12345678", "ab1234", "1234567890123456789");
			boolean value = (res == null);
			Assert.assertEquals(value, false);
		}
		catch(Exception e){
			System.err.println("Exception in AppTest.testCheckValidCourseTermTooLong");
		}
	}

	/**********************************************************
	 * checkValidStudent
	 **********************************************************/

	@Test
	public void testCheckValidStudentSuccess(){
		try{
			String res = (String)checkValidStudent.invoke(app, "12", "12", "test@mail.com", "123456789");
			boolean value = (res == null);
			Assert.assertEquals(value, true);
		}
		catch(Exception e){
			System.err.println("Exception in AppTest.testCheckValidStudentSuccess");
		}
	}

	@Test
	public void testCheckValidStudentFNameTooShort(){
		try{
			String res = (String)checkValidStudent.invoke(app, "1", "12", "test@mail.com", "123456789");
			boolean value = (res == null);
			Assert.assertEquals(value, false);
		}
		catch(Exception e){
			System.err.println("Exception in AppTest.testCheckValidStudentFNameTooShort");
		}
	}

	@Test
	public void testCheckValidStudentLNameTooShort(){
		try{
			String res = (String)checkValidStudent.invoke(app, "12", "1", "test@mail.com", "123456789");
			boolean value = (res == null);
			Assert.assertEquals(value, false);
		}
		catch(Exception e){
			System.err.println("Exception in AppTest.testCheckValidStudentLNameTooShort");
		}
	}

	@Test
	public void testCheckValidStudentEmailTooShort(){
		try{
			String res = (String)checkValidStudent.invoke(app, "12", "12", "a@b.", "123456789");
			boolean value = (res == null);
			Assert.assertEquals(value, false);
		}
		catch(Exception e){
			System.err.println("Exception in AppTest.testCheckValidStudentEmailTooShort");
		}
	}

	@Test
	public void testCheckValidStudentEmailIncorrectFormat(){
		try{
			String res = (String)checkValidStudent.invoke(app, "12", "12", "12345678", "123456789");
			boolean value = (res == null);
			Assert.assertEquals(value, false);
		}
		catch(Exception e){
			System.err.println("Exception in AppTest.testCheckValidStudentEmailTooShort");
		}
	}

	@Test
	public void testCheckValidStudentIdTooShort(){
		try{
			String res = (String)checkValidStudent.invoke(app, "12", "12", "test@mail.com", "12345678");
			boolean value = (res == null);
			Assert.assertEquals(value, false);
		}
		catch(Exception e){
			System.err.println("Exception in AppTest.testCheckValidStudentIdTooShort");
		}
	}

	@Test
	public void testCheckValidStudentIdTooLong(){
		try{
			String res = (String)checkValidStudent.invoke(app, "12", "12", "test@mail.com", "1234567890");
			boolean value = (res == null);
			Assert.assertEquals(value, false);
		}
		catch(Exception e){
			System.err.println("Exception in AppTest.testCheckValidStudentIdTooLong");
		}
	}

	/**********************************************************
	 * checkValidDeliverable
	 **********************************************************/

	@Test
	public void testCheckValidDeliverableSuccess(){
		try{
			String res = (String)checkValidDeliverable.invoke(app, "1234", "50");
			boolean value = (res == null);
			Assert.assertEquals(value, true);
		}
		catch(Exception e){
			System.err.println("Exception in AppTest.testCheckValidDeliverableSuccess");
		}
	}

	@Test
	public void testCheckValidDeliverableNameTooShort(){
		try{
			String res = (String)checkValidDeliverable.invoke(app, "123", "50");
			boolean value = (res == null);
			Assert.assertEquals(value, false);
		}
		catch(Exception e){
			System.err.println("Exception in AppTest.testCheckValidDeliverableNameTooShort");
		}
	}

	@Test
	public void testCheckValidDeliverableNameTooLong(){
		try{
			String res = (String)checkValidDeliverable.invoke(app, "1234567890123456789012345678901", "50");
			boolean value = (res == null);
			Assert.assertEquals(value, false);
		}
		catch(Exception e){
			System.err.println("Exception in AppTest.testCheckValidDeliverableNameTooLong");
		}
	}

	@Test
	public void testCheckValidDeliverableWeightTooSmall(){
		try{
			String res = (String)checkValidDeliverable.invoke(app, "1234", "-1");
			boolean value = (res == null);
			Assert.assertEquals(value, false);
		}
		catch(Exception e){
			System.err.println("Exception in AppTest.testCheckValidDeliverableWeightTooSmall");
		}
	}

	@Test
	public void testCheckValidDeliverableWeightTooLarge(){
		try{
			String res = (String)checkValidDeliverable.invoke(app, "1234", "101");
			boolean value = (res == null);
			Assert.assertEquals(value, false);
		}
		catch(Exception e){
			System.err.println("Exception in AppTest.testCheckValidDeliverableWeightTooLarge");
		}
	}
}

