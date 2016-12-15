package cs2212.team7;

import org.junit.Before;
import junit.framework.Assert;
import org.junit.Test;

public class DeliverableTest {

	Course c;
	Deliverable d;
	
	@Before
	public void before(){
		c = new Course("Name", "Term", "Code");
		d = new Deliverable ("Name", 100, "Type", c);
		try{
			d.addGrade(3, 3);
			d.addGrade(4, 4);
			d.addGrade(5, 5);
			d.addGrade(1, 1);
			d.addGrade(2, 2);
		}
		catch(DeliverableException e){
			System.err.println("DevilverableTest.before() failed.");
		}
		
	}
	
	@Test
	public void testAddGrade(){
		try{
			d.addGrade(6, 6);
			Assert.assertEquals(d.getStudentGrade(6), 6.0f, 0.001);
		}
		catch(DeliverableException e){
			System.err.println("DevilverableTest.testAddGrade() failed.");
		}
	}
	
	@Test
	public void testRemoveGradeNotFoundEmptyDeliverable(){
		boolean thrown;
		try{
			d.removeGrade(0);
			thrown = false;
		}
		catch(DeliverableException e){
			thrown = true;
		}
		Assert.assertEquals(thrown, true);
	}
	
	@Test
	public void testRemoveGradeFound(){
		try{
			d.removeGrade(1);
			Assert.assertEquals(d.containsStudent(1), false);
		}
		catch(DeliverableException e){
			System.err.println("DevilverableTest.testRemoveGradeFound() failed.");
		}
	}
	
	@Test
	public void testSetGradeFound(){
		try{
			d.setGrade(1, 2);
			Assert.assertEquals(d.getStudentGrade(1), 2.0f, 0.001);
		}
		catch(DeliverableException e){
			System.err.println("DevilverableTest.testSetGradeFound() failed.");
		}
	}
	
	@Test
	public void testSetGradeNotFound(){
		boolean thrown;
		try{
			d.setGrade(6, 1);
			thrown = false;
		}
		catch(DeliverableException e){
			thrown = true;
		}
		Assert.assertEquals(thrown, true);
	}
	
	@Test
	public void testGetAverage(){
		try{
			//float num = 3.0;
			Assert.assertEquals(d.getAverage(), 3.0f, 0.001);
		}
		catch(DeliverableException e){
			System.err.println("DevilverableTest.testGetAverage() failed.");
		}
	}
	
	@Test
	public void testGetAverageDivideByZero(){
		boolean thrown;
		Course newC = new  Course("Name", "Term", "Code");
		Deliverable newD = new Deliverable("Name", 100, "Type", newC);
		try{
			newD.getAverage();
			thrown = false;
		}
		catch(DeliverableException e){
			thrown = true;
		}
		Assert.assertEquals(thrown, true);
	}
	
	@Test
	public void testGetStudentGradeFound(){
		try{
			Assert.assertEquals(d.getStudentGrade(1), 1.0f, 0.001);		
		}
		catch(DeliverableException e){
			System.err.println("DevilverableTest.testGetStudentGradeFound() failed.");
		}
	}

	@Test
	public void testGetStudentGradeNotFound(){
		boolean thrown;
		try{
			d.getStudentGrade(100);
			thrown = false;
		}
		catch(DeliverableException e){
			thrown = true;
		}
		Assert.assertEquals(thrown, true);
	}
	
	@Test
	public void testContainsStudentFound(){
		Assert.assertEquals(d.containsStudent(1), true);
	}

	@Test
	public void testContainsStudentNotFound(){
		Assert.assertEquals(d.containsStudent(6), false);
	}

	@Test
	public void testIsEmptyTrue(){
		Deliverable del = new Deliverable("dName", 100, "Assignment", c);
		Assert.assertEquals(del.isEmpty(), true);
	}

	@Test 
	public void testIsEmptyAfterRemoval(){
		try{
			d.removeGrade(1);
			d.removeGrade(2);
			d.removeGrade(3);
			d.removeGrade(4);
			d.removeGrade(5);
		}
		catch(DeliverableException e){
			System.err.println("DeliverableException in DeliverableTest.testIsEmptyAfterRemoval");
		}
		Assert.assertEquals(d.isEmpty(), true);
	}

	@Test
	public void testIsEmptyFalse(){
		Assert.assertEquals(d.isEmpty(), false);
	}
}