/*
 * 
 */

package cs2212.team7;

import static java.lang.Float.NaN;
import java.util.List;

/**
 * TableRow class, represents a row in the table that displays and allows editing
 * for a student, that student's deliverables and that student's averages
 * @author Ben
 */
public class TableRow {
    private final Student student;        //Student this row represents
    private final Course course;          //Reference to active course
    private final List<Deliverable> dels; //List of deliverables in the course
    private final String name;            //Full name of the student
    private final long id;                //Student ID
    private float examAvg, assnAvg, totalAvg; //Averages for course
    
    public TableRow(Student s, Course c){
        student = s;    //Set student to parameter
        course = c;     //Set course to parameter
        dels = course.getDeliverables(); //Get the list of deliverables and store it
        name = student.getFName() + " " + student.getLName(); //Append first and last name
        id = student.getStudentNum();   //Get student number
    }
    
    /**
     * Method to return the number of deliverables in this course (for use with columns)
     * @return integer of number of deliverables
     */
    public int getNumDeliverables(){
        return course.getDeliverables().size();
    }
 
    /* Get Methods */
    public Student getStudent(){
        return student;
    }
    public Course getCourse(){
        return course;
    }
    public List<Deliverable> getDeliverables(){
        return dels;
    }
    public Deliverable getADeliverable(int i){
        return dels.get(i);
    }
    public String getName(){
        return name;
    }
    public long getID(){
        return id;
    }
    
    /**
     * Method to calculate and return the student's overall average
     * @return float of course average
     */
    public float getTotalAverage(){
        float res = 0, weight, totalWeight = 0, curGrade;   //Declare needed variables
        Deliverable current;                                //Variable for current deliverable
        for(int i = 0; i < getNumDeliverables(); i++){      //Loop through all deliverables in course
         current = dels.get(i);                  //Get the current deliverable
         weight = (float)current.getWeight()/10; //Calculate weight

         try{ //try to get current grade
           curGrade = current.getStudentGrade(id); //Get the student's grade
           totalWeight += weight;              //Add weight to total weight
           res += weight*curGrade;             //Multiply weight by grade, add to total
         } catch(DeliverableException ex){} //If no grade for this deliverable, skip it

        }  
        res = (float)res/totalWeight;              //Divide total by total weight, return it
        if(res == NaN)
           return (float) 0.0;     
        return (float)(Math.round(res*100.0) / 100.0); //Round and return value       
    }
    
    /**
     * Method to calculate the exam average of a student
     * @return float of student's exam average
     */
    public float getExamAverage(){
        float res = 0, weight, totalWeight = 0, curGrade;   //Declare needed variables
        Deliverable current;                                //Variable for current deliverable
        for(int i = 0; i < getNumDeliverables(); i++){
            if(dels.get(i).getType().equals("Exam")){
                current = dels.get(i);                  //Get the current deliverable
                weight = (float)current.getWeight()/10; //Calculate weight
                    
                try{
                    curGrade = current.getStudentGrade(id); //Get the student's grade
                    totalWeight += weight;              //Add weight to toal weight
                    res += weight*curGrade;             //Multiply weight by grade, add to total
                } catch(DeliverableException ex) {}
             }
       }
       res = (float)res/totalWeight;              //Divide total by total weight, return it
       if(res == NaN)
            return (float) 0.0;
       return (float)(Math.round(res*100.0) / 100.0); //Round and return value
    }
    
    /**
     * Method to return assignment average
     * @return float of assignment average
     */
    public float getAssnAverage(){     
        float res = 0, weight, totalWeight = 0, curGrade;   //Declare needed variables
        Deliverable current;                                //Variable for current deliverable
        for(int i = 0; i < getNumDeliverables(); i++){
            if(dels.get(i).getType().equals("Assignment")){
                current = dels.get(i);                  //Get the current deliverable
                weight = (float)current.getWeight()/10; //Calculate weight
                try{
                    curGrade = current.getStudentGrade(id); //Get the student's grade
                    if(curGrade >= 0){                      //If student has a grade for this deliverable
                        totalWeight += weight;              //Add weight to toal weight
                        res += weight*curGrade;             //Multiply weight by grade, add to total
                    }
                } catch(DeliverableException ex) {}
            }
           }   
        res = (float)res/totalWeight;              //Divide total by total weight, return it
        if(res == NaN)
            return (float) 0.0;
        return (float)(Math.round(res*100.0) / 100.0); //Round and return value
}
    
    
    /* Set Methods */
    
    /**
     * Method to set a grade for a deliverable
     * @param d deliverable being set
     * @param s student Id of student being edited
     * @param g the grade to be set
     */
    public void setDeliverableGrade(Deliverable d, long s, float g){
        try{
         int i = dels.indexOf(d);   //Get the index of the desired deliverable
            if(i >= 0){              //If its greater than 0 (it exists)
                dels.get(i).setGrade(s, g); //set the grade
            }
        }catch(DeliverableException e){}
    }
   
}
