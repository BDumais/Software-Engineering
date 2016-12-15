package cs2212.team7;

/**
 * This class represents the imported deliverable grades for a single student.
 *
 * Created by Nick DelBen
 * Created on February 27, 2014
 * Version 1.0
 * Bugs: None known
 */
public class ImportedGrade {

    /* The student number associated with this imported item. */
    private Integer number;

    /* An array of the deliverable grades. */
    private Float[] grades;

    /**
     * Constructs and returns a new instance of Imported Grade.
     *
     * @param studentNumber_in the student number these grades belong to.
     * @param grades_in imported deliverable grades.
     */
    public ImportedGrade(Integer studentNumber_in, Float[] grades_in) {
        number = studentNumber_in;
        grades = grades_in;
    }

    /**
     * Returns the student number associated with this item.
     * @return the student number.
     */
    public Integer getNumber() {
        return number;
    }

    /**
     * Returns the marks imported for this student.
     * @return the imported marks.
     */
    public Float[] getGrades() {
        return grades;
    }
}
