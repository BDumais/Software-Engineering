package cs2212.team7;

import com.google.common.primitives.Ints;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Before;
import junit.framework.Assert;
import org.junit.Test;


public class TableRowTest {

	private Course course, course2;
	private Deliverable d,d1,d2,d3,d4,d5,d6;
	private Student s;
        private TableRow table, table2;
	
	@Before
	public void before(){//this method runs and creates new course and dilverables
                course = new Course("Software Engineering","Winter 2014", "CS2212");
                course2 = new Course("Earth Resources","Winter 2014", "Ea2205");
		s = new Student("John", "Smith", "jsmith@uni.co.uk", 987654321);
		d = new Deliverable("Midterm", 35, "Exam", course);
		d1 = new Deliverable("Final", 40, "Exam", course);
                d2 = new Deliverable("Asn1", 5, "Assignment", course);
		d3 = new Deliverable("Asn2", 5, "Assignment", course);
                d4 = new Deliverable("Final Project", 15, "Assignment", course);
                d5 = new Deliverable("Scientific Report", 10, "Assignment", course2);
                d6 = new Deliverable("Midterm", 25, "Exam", course2);
		try{
			course.addDeliverable(d);
                        course.addDeliverable(d1);
                        course.addDeliverable(d2);
                        course.addDeliverable(d3);
                        course.addDeliverable(d4);
                        course2.addDeliverable(d5);
                        course2.addDeliverable(d6);
                        
		} catch (DeliverableException ex){ }
		table = new TableRow(s,course);
                table2 = new TableRow(s,course2);
                
                try {
                d.addGrade(987654321, 20);
                d1.addGrade(987654321, 65);
                d2.addGrade(987654321, 72);
                d3.addGrade(987654321, 60);
                d4.addGrade(987654321, 76);
                d6.addGrade(987654321, 89);
               
            } catch (DeliverableException ex) {  }
	}
	
	
	@Test
	public void setNewDeliverableGrade(){//test set new deliverable
            
	table.setDeliverableGrade(d,987654321, 85);
        float g=0;
            try {
                g = d.getStudentGrade(987654321);
            } catch (DeliverableException ex){}
            Assert.assertEquals(g, 85,0.01);
	}
	
        @Test
	public void getExamAverage(){//test get exam average with multiple exams
            float average = table.getExamAverage();
            Assert.assertEquals(average, 44 ,0.1);
        }
	
         @Test
	public void getAsnAverage(){//test get assignment average with multiple assignments
            float average = table.getAssnAverage();
            Assert.assertEquals(average, 72 ,0.1);
        }
	
        @Test
	public void getAsnAverageNullMark(){//get assignment average with no marks for assignments
            float average;
            //boolean thrown;
            average  = table2.getAssnAverage();
            // = false;
		Assert.assertEquals(average,0,0.1);
         }
        
        
           @Test
	public void getOverallAverage(){//tests gets overall average with multiple deliverables
            float average = table.getTotalAverage();
            Assert.assertEquals(average, 51 ,0.1);
        }
	
	 @Test
	public void getOverallAverageNullMark(){//tests gets overall average with one deliverables
            float average = table2.getTotalAverage();
            Assert.assertEquals(average,89,0.1);
         }
        

	

}
