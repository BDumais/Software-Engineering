package cs2212.team7;

/**
 * An exception raised by the grade book.
 *
 * Created by Nick on 3/3/14.
 */
public class GradebookException extends Exception {
    //Exception messages
    public static final String CLASS_NOT_FOUND = "There was an issue opening the saved data. It appears the data is corrupt.";
    public static final String FILE_IO_ERROR = "There was an issue opening the saved data. The file could not be accessed (permissions / removed).";
    public static final String COURSE_NOT_FOUND = "There was an error locating the course. It does not appear to exist in the database.";
    public static final String COURSE_CURRENTLY_ACTIVE = "There was an error opening the selected course. There is another course active.";
    public static final String STUDENT_NOT_FOUND = "There was an error locating the student. It does not appear to exist in the database";

    public GradebookException() {
        super();
    }
    public GradebookException(String message_in) {
        super(message_in);
    }
}
