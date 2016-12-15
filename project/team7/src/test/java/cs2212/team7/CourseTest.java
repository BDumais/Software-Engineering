package cs2212.team7;

import com.google.common.primitives.Ints;
import org.junit.Before;
import junit.framework.Assert;
import org.junit.Test;


public class CourseTest {

	private Course course;
	private Deliverable d,d1,d3;
	private Student s;
	
	@Before
	public void before(){//this method runs and creates new course and dilverables
		course = new Course("Software Engineering","Winter 2014", "CS2212");
		s = new Student("John", "Smith", "jsmith@uni.co.uk", 987654321);
		d = new Deliverable("Midterm", 35, "Exam", course);
		try{
			course.addDeliverable(d);
		} catch (DeliverableException ex){ }
	}
	
	@Test
	public void addDeliverable(){//tests adding a deliverable works
		Deliverable d;
		d3 = new Deliverable("Project", 15, "Assignment", course);
		try{
			course.addDeliverable(d3);
		} catch (DeliverableException ex){ }
		d = course.getDeliverable("Midterm");
		boolean exists = (d == null ? false : true);
		Assert.assertEquals(exists,true);
	}
	

	@Test 
	public void addDeliverableException(){//tests if exception is thrown if the weight exceeds 100
		Deliverable d;
		boolean thrown;
		d3 = new Deliverable("Project", 99, "Assignment", course);
		try{
			course.addDeliverable(d3);
			thrown = false;
		} catch (DeliverableException ex){
			thrown = true;
		}
		Assert.assertEquals(thrown,true);
	}
	
	
	
	@Test
	public void testRemoveDeliverable(){//tests if removing deliverable works 
		course.removeDeliverable("Midterm");
		Deliverable d = course.getDeliverable("Midterm");
		boolean exists = (d == null ? false : true);
		Assert.assertEquals(exists, false);
	}
	

	@Test 
	public void testSumWeights(){ //tests that it sums the weight if there is one deliverable
		int sumResult;
		sumResult = course.sumWeights();
		Assert.assertEquals(sumResult, 35);
	}
	
	@Test //this is tested with multiple deliverables (checking the sum function)
	public void testSumWeights2(){
		d1 = new Deliverable("Report", 40, "Assignment", course);
		try{
			course.addDeliverable(d1);
		} catch (DeliverableException ex){ }
		int sumResult;
		sumResult = course.sumWeights();
		Assert.assertEquals(sumResult, 75);
	}
	

	@Test
	public void testHasStudentFalse(){ //tesy has student with a fake student
		boolean result = course.hasStudent(123456789);
		Assert.assertEquals(result,false);
	}
	
	
	
	@Test
	public void testHasStudentTrue(){//has student with a real student 
		course.addStudent(s);
		boolean result = course.hasStudent(987654321);
		Assert.assertEquals(result,true);
	}
	
	@Test
	public void testGetDilverableFound(){//test get deliverable with a deliverable that does exist 
		Deliverable d;
		d = course.getDeliverable("Midterm");
		boolean exists = (d == null ? false : true);
		Assert.assertEquals(exists,true);
	}
	
	@Test
	public void testGetDilverableNotFound(){//test get deliverable with a deliverable that does not exist 
		Deliverable d;
		d = course.getDeliverable("Final Exam");
		boolean exists = (d == null ? false : true);
		Assert.assertEquals(exists,false);
	}
	
	@Test
	public void addStudent(){//test add student to a course
		course.addStudent(s);
		boolean result = course.hasStudent(987654321);
		Assert.assertEquals(result,true);
	}
	
	@Test
	public void removeStudent(){//test remove student from a course
		course.removeStudent(987654321);
		boolean result = course.hasStudent(987654321);
		Assert.assertEquals(result,false);
	}

}
