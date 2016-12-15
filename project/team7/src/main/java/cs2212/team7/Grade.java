/**
 * Class to represent a single student's grade for a deliverable
 */
package cs2212.team7;

import java.io.Serializable;

public class Grade implements Serializable {

    /* Attributes */
	private long studentId; 
	private float mark;
	
        /**
         * Constructor for grades
         * @param studentId student ID being added
         * @param mark Mark being added
         */
	public Grade(long studentId, float mark){
		this.studentId = studentId;
		this.mark = mark;
	}
	
        /**
         * Get method for student ID
         * @return long of student number
         */
	public long getStudentId(){
		return studentId;
	}
	
        /**
         * Get for student mark
         * @return float of their grade
         */
	public float getMark(){
		return mark;
	}
	
        /**
         * Set for grade
         * @param newMark mark to be set
         */
	public void setGrade(float newMark){
		mark = newMark;
	}
	
        /**
         * Set for student ID
         * @param newId id to be set
         */
	public void setStudentId(long newId){
		studentId = newId;
	}
}
