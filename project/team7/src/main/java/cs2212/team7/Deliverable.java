/**
 * Class to represent a deliverable within a course. Stores data associated with this assignment
 * such as type, name and the grades for each student in the course
 *
 * Written by Team 7 for CS2212-w2014
 */

package cs2212.team7;
import java.io.Serializable;
import java.util.*;

public class Deliverable implements Serializable {

	/* Attributes */

        
	private String name, type;	//String for the name of the deliverable and type
	private int weight;			//Weight of this deliverable (out of 100)
	private LinkedList<Grade> grades;   //List of grades for this deliverable
        private  Course course;

	/* Constructor */

        /**
         * Constructor, accepts passed parameters and builds new deliverable
         * @param dName
         * @param dWeight
         * @param dType 
         * @param c 
         */
	public Deliverable(String dName, int dWeight, String dType, Course c){
		name=dName;			//Set name, weight and type to passed parameters
                if(dWeight >= 0 && dWeight <= 100)
                    weight=dWeight; //If parameter weight is in accepted range set it
                else
                    weight = 0; //If invalid set weight to 0
		type=dType;
		grades = new LinkedList<>();
                course = c;
	}

	/* Public Methods */

    public LinkedList<Grade> geGrades() {return grades;}
        /**
         * Get for name
         * @return String the name of this deliverable
         */
	public String getName(){
		return name;
	}

	/**
         * Get for weight
         * @return int weight of this deliverable
         */
	public int getWeight(){
		return weight;
	}

	/**
         * Get for type
         * @return String of deliverable name
         */
	public String getType(){
		return type;
	}

	/**
         * Set for name
         * @param dName String to be set as the name
         */
	public void setName(String dName){
		this.name=dName;
	}

	/**
         * Set for weight
         * @param dWeight int of weight to be set
         * @throws DeliverableException if weight is not valid
         */
	public void setWeight (int dWeight) throws DeliverableException{
		int total = course.sumWeights() - this.weight; //Calculate course weight without this deliverable
                if(total +dWeight > 100) //If the fixed total plus the new weight is over 100, throw an error
                    throw new DeliverableException("New weight exceeds total weight: weight left is " + (100 - total + this.weight));
		else	//Otherwise set the weight of this to the parameter
			this.weight=dWeight;
	}

	/**
         * Sets the type of deliverable
         * @param t String to be set as type
         */
	public void setType(String t){
		this.type=t;
	}

	/**
	 * Adds a grade to the deliverable
	 * @param targetId The ID number of the student whose grade is being added
	 * @param newGrade The mark of the student to be added
         * @throws cs2212.team7.DeliverableException
	 */
	public void addGrade(long targetId, float newGrade) throws DeliverableException{
		// Check if the student is in the course. TODO implement this

		// Check if a grade for the student already exists in this deliverable.
		ListIterator<Grade> iter = grades.listIterator();
		while (iter.hasNext()){
			Grade tempGrade = iter.next();
			if (tempGrade.getStudentId() == targetId){
				throw new DeliverableException("Grade could not be added to Deliverable " + name + ". \nThe student with ID " + targetId + " already has a grade for this deliverable.");
			}
		}

		// Add the grade.
		grades.add(new Grade(targetId, newGrade));
	}

	/**
	 * Removes a grade from the deliverable.
	 * @param targetId The ID number of the student whose grade is to be removed
	 * @throws DeliverableException The student could not be found in this deliverable
	 */
	public void removeGrade(long targetId) throws DeliverableException{
		ListIterator<Grade> iter = grades.listIterator();
		int counter = 0;
		boolean found = false;
		while (iter.hasNext() && found == false){
			Grade tempGrade = iter.next();
			if (tempGrade.getStudentId() == targetId){
				grades.remove(counter);
				found = true;
			}
			counter++;
		}

		if (found == false){
			throw new DeliverableException("Grade could not be removed for Deliverable " + name + ".  \nGrade for student ID " + targetId + " does not exist in this deliverable.");
		}
	}

	/**
	 * Changes the mark of an existing grade in the deliverable.
	 * @param targetId The ID of student whose mark is to be changed
	 * @param newMark The new mark of the student
         * @throws cs2212.team7.DeliverableException
	 */
	public void setGrade(long targetId, float newMark) throws DeliverableException{
		ListIterator<Grade> iter = grades.listIterator();
		int counter = 0;
		boolean found = false;
		while (iter.hasNext() && found == false){
			Grade tempGrade = iter.next();
			if (tempGrade.getStudentId() == targetId){
				grades.set(counter, new Grade(targetId, newMark));
				found = true;
			}
			counter++;
		}
		if (found == false){
                    if(course.hasStudent(targetId)){
                        addGrade(targetId, newMark);
                    }
                    else
			throw new DeliverableException("Grade could not be set for Deliverable " + name + ".  \nGrade for student ID " + targetId + " does not exist in this deliverable.");
		}
	}

	/**
	 * Calculates and returns the average of the deliverable across all students
	 * @return The average mark scored on the deliverable
         * @throws cs2212.team7.DeliverableException
	 */
	public float getAverage() throws DeliverableException{
		if (grades.size() == 0) throw new DeliverableException("Calculating Deliverable average: Cannot divide by zero.");
		ListIterator<Grade> iter = grades.listIterator();
		float average = 0;
		int counter = 0;
		while (iter.hasNext()){
			Grade tempGrade = iter.next();
			average += tempGrade.getMark();
			counter++;
		}
		average = average / counter;
		return average;	
	}
	
	/**
	 * Returns the grade of a particular student for this deliverable.
	 * @param targetId The ID of the student whose mark we seek
	 * @throws DeliverableException The student could not be found
	 * @return The mark of the student for this deliverable
	 */
	public float getStudentGrade(long targetId) throws DeliverableException{
		ListIterator<Grade> iter = grades.listIterator();
		while (iter.hasNext()){
			Grade tempGrade = iter.next();
			if (tempGrade.getStudentId() == targetId){
				return tempGrade.getMark();
			}
		}
		
		// At this point, the student could not be found.
		throw new DeliverableException("Could not find student.");
	}
	
	/**
	 * Checks to see whether there are grades in this deliverable
	 * @return true if there are no grades in this deliverable
	 */
	public boolean isEmpty(){
            return grades.size() == 0;
	}
	
	/**
	 * Checks to see if the student has a mark in this deliverable.
	 * @param targetId The ID of the student we are seeking
	 * @return result of if the student exists in the deliverable
	 */
	public boolean containsStudent(long targetId){
		ListIterator<Grade> iter = grades.listIterator();
		while (iter.hasNext()){
			Grade tempGrade = iter.next();
			if (tempGrade.getStudentId() == targetId){
				return true;
			}
		}
		return false;
	}
}
