package cs2212.team7;

/**
 * An exception thrown to signify a CSV file in invalid
 *
 * Created by Nick DelBen
 * Created on 2/25/14
 * Version 1.0
 * Bugs: None known
 */
public class InvalidCsvException extends Exception {

    public InvalidCsvException() {
        super();
    }
    public InvalidCsvException(String errorMessage_in) {
        super(errorMessage_in);
    }
}
