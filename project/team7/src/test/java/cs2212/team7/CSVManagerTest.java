package cs2212.team7;

import com.google.common.primitives.Ints;
import org.junit.Before;
import junit.framework.Assert;
import org.junit.Test; 

/**
 * Created by silent on 3/12/14.
 */
public class CSVManagerTest {

    @Test
    public void testValidStudentInvalidColumns() {
        String input = "\"09\",\"A\",\"1119\",\"2\",\"UGRD\",\"COMPSCI\",\"2212B\",\"001\",\"250000002\",\"Doe\",\"Jane\",\"\",\"SSBA3\"";
        boolean result = CSVManager.isValidStudent(input);
        Assert.assertEquals(result, false);
    }

    @Test
    public void testValidStudentNonNumericNumber() {
        String input = "\"09\",\"A\",\"1119\",\"2\",\"UGRD\",\"COMPSCI\",\"2212B\",\"001\",\"250000002Z\",\"Doe\",\"Jane\",\"\",\"SSBA3\",\"fake-jdoe@uwo.ca\"";
        boolean result = CSVManager.isValidStudent(input);
        Assert.assertEquals(result, false);
    }

    @Test
    public void testValidStudentInvalidNumber() {
        String input = "\"09\",\"A\",\"1119\",\"2\",\"UGRD\",\"COMPSCI\",\"2212B\",\"001\",\"-250000002\",\"Doe\",\"Jane\",\"\",\"SSBA3\",\"fake-jdoe@uwo.ca\"";
        boolean result = CSVManager.isValidStudent(input);
        Assert.assertEquals(result, false);
    }

    @Test
    public void testValidStudentNoFirstName() {
        String input = "\"09\",\"A\",\"1119\",\"2\",\"UGRD\",\"COMPSCI\",\"2212B\",\"001\",\"250000002\",\"Doe\",\"\",\"\",\"SSBA3\",\"fake-jdoe@uwo.ca\"";
        boolean result = CSVManager.isValidStudent(input);
        Assert.assertEquals(result, false);
    }


    @Test
    public void testValidStudentEmailMissingAt() {
        String input = "\"09\",\"A\",\"1119\",\"2\",\"UGRD\",\"COMPSCI\",\"2212B\",\"001\",\"250000002\",\"Doe\",\"Jane\",\"\",\"SSBA3\",\"fake-jdoeuwo.ca\"";
        boolean result = CSVManager.isValidStudent(input);
        Assert.assertEquals(result, false);
    }

    @Test
    public void testValidStudentEmailMissingUser() {
        String input = "\"09\",\"A\",\"1119\",\"2\",\"UGRD\",\"COMPSCI\",\"2212B\",\"001\",\"250000002\",\"Doe\",\"Jane\",\"\",\"SSBA3\",\"@uwo.ca\"";
        boolean result = CSVManager.isValidStudent(input);
        Assert.assertEquals(result, false);
    }

    @Test
    public void testValidStudentEmailMissingDomain() {
        String input = "\"09\",\"A\",\"1119\",\"2\",\"UGRD\",\"COMPSCI\",\"2212B\",\"001\",\"250000002\",\"Doe\",\"Jane\",\"\",\"SSBA3\",\"fake-jdoe@\"";
        boolean result = CSVManager.isValidStudent(input);
        Assert.assertEquals(result, false);
    }

    @Test
    public void testValidStudentValidInput() {
        String input = "\"09\",\"A\",\"1119\",\"2\",\"UGRD\",\"COMPSCI\",\"2212B\",\"001\",\"250000002\",\"Doe\",\"Jane\",\"\",\"SSBA3\",\"fake-jdoe@uwo.ca\"";
        boolean result = CSVManager.isValidStudent(input);
        Assert.assertEquals(result, true);
    }
}
