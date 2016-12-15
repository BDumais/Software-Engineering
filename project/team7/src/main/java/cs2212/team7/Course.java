package cs2212.team7;
import java.io.Serializable;
import java.util.*;

/**
 * The class represents a single course in the gradebook.
 * Each course has a number of deliverables and students.
 * Each course can be identified by a name, a course code, and a term.
 * 
 * @author Alex Gyori, #250643023
 */

public class Course implements Serializable {
	
	/*********************************************************************
	 * Instance variables
	 *********************************************************************/
	
	// The name of the course.
	private String name;
	
	// The term the course will take place in.
	private String term;
	
	// The course code.
	private String code;
	
	// The list of deliverables the course contains.
	private LinkedList<Deliverable> deliverables;
	
	// The list of IDs for students who are enrolled in this course
	private LinkedList<Student> students;
	
	/**
	 * Constructor for class Course
	 * @param name Name of the course
	 * @param term Term the course will be being taught in
	 * @param code Course code
	 */
	public Course(String name, String term, String code){
		this.name = name;
		this.term = term;
		this.code = code;
		deliverables = new LinkedList<Deliverable>();
		students = new LinkedList<Student>();
	}
	
	/*********************************************************************
	 * Setter Methods
	 *********************************************************************/
	
	/**
	 * Sets the name of the course
	 * @param name The new name of the course
	 */
	public void setName(String name){
		this.name = name;
	}
	
	/**
	 * Sets the term of the course
	 * @param term The new term of the course
	 */
	public void setTerm(String term){
		this.term = term;
	}
	
	/**
	 * Sets the course code
	 * @param code The new course code
	 */
	public void setCode(String code){
		this.code = code;
	}
	
	/**
	 * Adds a deliverable to the course.
	 * @param newDeliverable The new deliverable to be added
         * @throws cs2212.team7.DeliverableException if weight exceeds 100
	 */
	public void addDeliverable(Deliverable newDeliverable) throws DeliverableException {
            //Check weight
            int sum = sumWeights();
            if(sum + newDeliverable.getWeight() > 100)
                throw new DeliverableException("New deliverables weight is too large: max weight it can have is " + (100 - sum));
            //If weight doesn't exceed 100 add it
            deliverables.add(newDeliverable);
	}
	
	/**
	 * Removes a deliverable from the course
	 * @param targetDelName The name of the deliverable to be removed
	 */
	public void removeDeliverable(String targetDelName){
		ListIterator<Deliverable> iter = deliverables.listIterator();
		int counter = 0;
		boolean found = false;
		while (iter.hasNext() && found == false){
			Deliverable tempDel = iter.next();
			if (tempDel.getName().equals(targetDelName)){
				deliverables.remove(counter);
				found = true;
			}
			counter++;
		}
		
		if (found == false){
			System.err.println("Error: the specified deliverable could not be found in course " + name + ".");
		}
	}
	
	/**
	 * Adds a student to the course
	 * @param newStudent The student to be added
	 */
	public void addStudent(Student newStudent){
		students.add(newStudent);
	}
	
	/**
	 * Removes a student from the course
	 * @param targetId The ID of the student to be removed
	 */
	public void removeStudent(long targetId){
		ListIterator<Student> iter = students.listIterator();
		int counter = 0;
		boolean found = false;
		while (iter.hasNext() && found == false){
			Student temp = iter.next();
			if (temp.getStudentNum() == targetId){
				students.remove(counter);
				found = true;
			}
			counter++;
		}
		
		if (found == false){
			System.err.println("Error: the specified student ID could not be found in course " + name + ".");
		}
	}
        
        /**
         * Method to search this course for a student
         * @param targetId ID of student to be searched
         * @return boolean result of search
         */
        public boolean hasStudent(long targetId){
            for(Student s: students){
                if(s.getStudentNum() == targetId){
                    return true;
                }
            }
            return false;
        }
	
	/*********************************************************************
	 * Getter Methods
	 *********************************************************************/
	
	
	/**
	 * Returns the name of the course
	 * @return The name of the course
	 */
	public String getName(){
		return name;
	}
	
	/**
	 * Returns the term the course will be taught in
	 * @return The term of the course
	 */
	public String getTerm(){
		return term;
	}
	
	/**
	 * Returns the course code
	 * @return The course code
	 */
	public String getCode(){
		return code;
	}
	
	/**
	 * Returns the list of deliverables the course contains
	 * @return
	 */
	public LinkedList<Deliverable> getDeliverables(){
		return deliverables;
	}
        
        /**
         * Method to return a specific deliverable
         * @param name name of deliverable being looked for
         * @return the deliverable
         */
        public Deliverable getDeliverable(String name){
            for(Deliverable d : deliverables){
                if(d.getName().equals(name))
                    return d;
            }
            return null;
        }
	
	/**
	 * Returns the list of IDs for the students enrolled in the course
	 * @return list of IDs for the students enrolled in the course
	 */
	public LinkedList<Student> getStudents(){
		return students;
	}
        
        /**
         * Method to sum current weight of all deliverables
         * @return total weight
         */
        public int sumWeights(){
            //Create sum
            int sum = 0;
            //Loop through deliverables
            for(Deliverable d: deliverables){
                sum += d.getWeight(); //Add current deliverables weight to sum
            }
            return sum; //return sum
        }
}
