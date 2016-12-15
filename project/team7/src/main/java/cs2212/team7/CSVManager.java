
package cs2212.team7;

import com.google.common.base.CharMatcher;
import com.google.common.primitives.Ints;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;


/**
 * This class contains all the method for importing and exporting
 * the gradebook data to CSV files.
 * <p/>
 * Created by Nick DelBen
 * Created on 2/27/14
 * Version 1.0
 * Bugs: None known
 */
public class CSVManager {

    //The character delimiting columns in a CSV file.
    public static final String CSV_FILE_DELIMITER = ",";

    //The number of terms in each line of the students csv file.
    public static final int STUDENT_CSV_COLUMNS = 14;

    //The min and max value a student number can have.
    public static final int MINIMUM_STUDENT_NUMBER = 0;
    public static final int MAXIMUM_STUDENT_NUMBER = 999999999;

    //The character delimiting users from domains in email addresses.
    public static final char EMAIL_ADDRESS_DELIMITER = '@';

    //The name of the Student Number column in the grade CSV. */
    public static final String CSV_GRADE_NUMBERHEADING = "Student Number";

    //The indices of relevant information in a csv file.
    public static final int CSV_STUDENT_INDEX_NUMBER = 8;
    public static final int CSV_STUDENT_INDEX_LNAME = 9;
    public static final int CSV_STUDENT_INDEX_FNAME = 10;
    public static final int CSV_STUDENT_INDEX_EMAIL = 13;

    //The default name for the exported students.
    public static final String STUDENT_CSV_EXPORT_FILENAME = "exported_students";

    //A filter for only files that are comma separated values.
    public static final FileNameExtensionFilter EXTENSION_FILTER_CSV = new FileNameExtensionFilter("Comma Separated Values", "CSV", "csv");

    /**
     * Imports the grades to an imported object.
     *
     * @param deliverables_in the deliverables to extract from the file.
     * @param file_in         the file to import grades from.
     * @return the results of importing a grade.
     * @throws IOException         if there is an error accessing the file.
     * @throws InvalidCsvException if the scv file does not contain a 'Student Number' column.
     */
    public static GradeImportResult<ImportedGrade> importGrades(Collection<String> deliverables_in, File file_in) throws IOException, InvalidCsvException {
        BufferedReader reader = new BufferedReader(new FileReader(file_in));

        //The column titles from the CSV file
        String[] columnTitles = reader.readLine().split(CSV_FILE_DELIMITER);

        //A list of indecies that have valid deliverable titles
        ArrayList<Integer> validIndex = new ArrayList<Integer>();

        //A list of the titles of the valid deliverables
        ArrayList<String> validDeliverables = new ArrayList<String>();

        Integer studentNumberColumn = null;

        for (int titleIterator = 0; titleIterator < columnTitles.length; titleIterator++) {
            String str = columnTitles[titleIterator].replace("\"", ""); //Trim quotes from input
            if (str.equals(CSV_GRADE_NUMBERHEADING)) {
                studentNumberColumn = titleIterator;
            }
            str = str.substring(1); //Trim substring down (eliminates starting space)
            if (deliverables_in.contains(str)) {
                validIndex.add(titleIterator); //add index as valid
                validDeliverables.add(columnTitles[titleIterator].replace("\"", "")); //again, trim quotes
            }
        }
        //Ensure the student number column was found in the document
        if (studentNumberColumn == null) {
            throw new InvalidCsvException();
        }

        //Create String array from object array
        Object[] delivObjs = validDeliverables.toArray();
        String[] delivStrings = new String[delivObjs.length];
        for (int index = 0; index < delivObjs.length; index++) {
            delivStrings[index] = delivObjs[index].toString().substring(1);
        }

        //Create result object
        GradeImportResult result = new GradeImportResult(delivStrings);
        result.setDelNames(validDeliverables);

        String currentLine;
        while ((currentLine = reader.readLine()) != null) {
            String[] splitLine = currentLine.split(CSV_FILE_DELIMITER);
            Integer studentNumber;
            //Ensure there are the right amount of columns in the line
            if (splitLine.length != columnTitles.length || (studentNumber = Ints.tryParse(splitLine[studentNumberColumn].replace("\"", ""))) == null) {
                result.addFail(currentLine);
                continue;
            }
            Float[] gradeList = new Float[result.getDeliverables().length];
            for (int i = 0; i < gradeList.length; i++) { //Loop through gradelist
                try {
                    int column = validIndex.get(i); //Get the associated column number containing valid input
                    gradeList[i] = Float.parseFloat(splitLine[column].replace("\"", "")) * 100; //Parse and convert to proper format
                } catch (NumberFormatException ex) {
                    gradeList[i] = (float) -1;
                } //If input is invalid, set it to -1 to be dealt with later
            }
            //Create a new student and add it to the result collection.
            result.addSuccess(new ImportedGrade(studentNumber, gradeList));
        }
        return result;
    }


    /**
     * Parses a list of students contains in a CSV file and extracts the valid students from the file.
     *
     * @param file_in the file to extract students from.
     * @return the students that were imported incorrectly.
     * @throws java.io.IOException if there is an issue opening the file.
     */
    public static ImportResult<Student> importStudents(File file_in) throws IOException {
        //Create a reader to parse the specified file with
        BufferedReader reader = new BufferedReader(new FileReader(file_in));
        ImportResult<Student> result = new ImportResult();
        String currentLine;
        while ((currentLine = reader.readLine()) != null) {
            if (isValidStudent(currentLine)) {
                //Isolate the columns
                String[] columns = currentLine.split(CSV_FILE_DELIMITER);
                //Extract the data needed to create a student
                String firstName = columns[CSV_STUDENT_INDEX_FNAME].trim().replace("\"", "");
                String lastName = columns[CSV_STUDENT_INDEX_LNAME].trim().replace("\"", "");
                int studentNumber = Integer.parseInt(columns[CSV_STUDENT_INDEX_NUMBER].trim().replace("\"", ""));
                String email = columns[CSV_STUDENT_INDEX_EMAIL].trim().replace("\"", "");
                //Add the student to the results
                result.addSuccess(new Student(firstName, lastName, email, studentNumber));
            } else {
                result.addFail(currentLine);
            }
        }
        return result;
    }

    /**
     * Checks if a specified line of comma separated values
     * fits the format to be import as a student.
     *
     * @param line_in the line to check the validity of.
     * @return true if the student can be imported, otherwise false.
     */
    public static boolean isValidStudent(String line_in) {
        //Separate the input line into strings representing each column
        String[] splitLine = line_in.split(CSV_FILE_DELIMITER);
        //Ensure there are the correct amount of elements on the line.
        if (splitLine.length != STUDENT_CSV_COLUMNS
                //Ensure the student number is an integer.
                || Ints.tryParse(splitLine[CSV_STUDENT_INDEX_NUMBER].trim().replace("\"", "")) == null
                //Ensure the student number is a positive integer
                || Ints.tryParse(splitLine[CSV_STUDENT_INDEX_NUMBER].trim().replace("\"", "")) < MINIMUM_STUDENT_NUMBER
                //Ensure the student number is a below max number
                || Ints.tryParse(splitLine[CSV_STUDENT_INDEX_NUMBER].trim().replace("\"", "")) > MAXIMUM_STUDENT_NUMBER
                //Check if the student number is 9 characters long
                || splitLine[CSV_STUDENT_INDEX_NUMBER].trim().replace("\"", "").length() != 9
                //Ensure the student has a first name
                || splitLine[CSV_STUDENT_INDEX_FNAME].trim().replace("\"", "").length() <= 0
                //Ensure the student's email address contains 1 '@'
                || CharMatcher.is(EMAIL_ADDRESS_DELIMITER).countIn(splitLine[CSV_STUDENT_INDEX_EMAIL]) != 1
                //Ensure there are characters on the left side of the '@'
                || splitLine[CSV_STUDENT_INDEX_EMAIL].trim().replace("\"", "").indexOf(EMAIL_ADDRESS_DELIMITER) == 0
                //Ensure there are characters on the right side of the '@'
                || splitLine[CSV_STUDENT_INDEX_EMAIL].trim().replace("\"", "").lastIndexOf(EMAIL_ADDRESS_DELIMITER) == splitLine[CSV_STUDENT_INDEX_EMAIL].replace("\"", "").length() - 1) {
            return false;
        } else {
            return true;
        }
    }

    //Writes the specified students to the file.
    public static void exportStudents(LinkedList<Student> students_in, String course) throws IOException {
        //Ensure you are not overwriting existing file.
        String outputFilePath = directoryDialog(); //Select folder

        if (outputFilePath == null) //If directory dialog returned null user cancelled
            throw new IOException("Export cancelled"); //Signal cancellation by throwing an exception

        String outputFileName = course + "-Students"; //Create file name
        File outputFile = new File(outputFilePath + File.separator + outputFileName + ".csv"); //create file
        int fileName_iterator = 1;
        while (outputFile.exists()) { //If the specified outfile file exists, append a number until it is a new file
            outputFile = new File(outputFilePath + File.separator + outputFileName + (fileName_iterator++) + ".csv");
        }
        outputFile.createNewFile(); //Create the file
        PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(outputFile))); //Create writer
        //Concatinate massive string of exported data.
        for (Student currentStudent : students_in) {
            writer.println("\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"" + ("" + currentStudent.getStudentNum()) + "\",\"" + currentStudent.getLName() + "\",\"" + currentStudent.getFName() + "\",\"\",\"\",\"" + currentStudent.getEmail() + "\"");
            writer.flush();
        }
        writer.close(); //close writer
    }

    /**
     * Exports the grades to a csv file.
     *
     * Note: would be way better if deliverable was not linked list.
     *
     * @param studentList_in the students that need grades exported.
     * @param deliverableList_in the deliverables associated with the course.
     * @return the number of grades exported
     */
    public static Integer exportGrades(LinkedList<Student> studentList_in, LinkedList<Deliverable> deliverableList_in, String courseName_in) throws NullPointerException, IOException{
        if (studentList_in == null) {
            throw new NullPointerException("invalid Student List");
        }
        if (deliverableList_in == null) {
            throw new NullPointerException("invalid Student List");
        }
        //Create a two dimensional array of grades.
        String[][] deliverableChart = new String[studentList_in.size()][deliverableList_in.size() + 1];
        //Create the list of deliverable titles.
        String[] deliverableTitles = new String[deliverableList_in.size() + 1];
        deliverableTitles[0] = "\"" + CSV_GRADE_NUMBERHEADING + "\"";
        //Create the list of student numbers
        Long[] studentNumberList = new Long[studentList_in.size()];
        Integer currentStudentIndex = 0;
        for (Student studentIterator : studentList_in) {
            deliverableChart[currentStudentIndex][0] = "\"" + Long.toString(studentIterator.getStudentNum()) + "\"";
            studentNumberList[currentStudentIndex++] = studentIterator.getStudentNum();

        }
        //Populate grade matrix
        Integer col_cur = 1;
        for (Deliverable deliverableIterator : deliverableList_in) {
            deliverableTitles[col_cur] = "\"" + deliverableIterator.getName() + "\"";
            for (Grade currentGrade : deliverableIterator.geGrades()) {
                for (int studentNumberListIndex = 0; studentNumberListIndex < studentNumberList.length; studentNumberListIndex++){
                    if (studentNumberList[studentNumberListIndex] == currentGrade.getStudentId()) {
                        deliverableChart[studentNumberListIndex][col_cur] = "\"" + Float.toString(currentGrade.getMark()) + "\"";
                    }
                }

            }
            col_cur++;
        }
        //Ensure you are not overwriting existing file.
        String outputFilePath = directoryDialog(); //Select folder

        if (outputFilePath == null) { //If directory dialog returned null user cancelled
            throw new IOException("Export cancelled"); //Signal cancellation by throwing an exception
        }
        String outputFileName = courseName_in + "-Grades"; //Create file name
        File outputFile = new File(outputFilePath + File.separator + outputFileName + ".csv"); //create file
        int fileName_iterator = 1;
        while (outputFile.exists()) { //If the specified outfile file exists, append a number until it is a new file
            outputFile = new File(outputFilePath + File.separator + outputFileName + (fileName_iterator++) + ".csv");
        }
        outputFile.createNewFile(); //Create the file
        PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(outputFile))); //Create writer


        String arrayString = Arrays.toString(deliverableTitles);
        writer.println(arrayString.substring(1,arrayString.length() - 1));
        for (String[] currentRow : deliverableChart) {
            arrayString = Arrays.toString(currentRow);
            writer.println(arrayString.substring(1,arrayString.length() - 1));
            writer.flush();
        }
        return studentList_in.size();
    }

    /**
     * Opens a dialog returning the directory chosen.
     *
     * @return a string to the folder if chosen otherwise null
     */
    public static String directoryDialog() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Choose a folder to export to");
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int returnVal = fileChooser.showOpenDialog(null);
        return returnVal == JFileChooser.APPROVE_OPTION ? fileChooser.getSelectedFile().getPath() : null;
    }

    /**
     * Opens a file dialog for the user to select a file of the
     * type specified in order to retrieve the file.
     *
     * @param fileFilter_in a filter matching the desired file type.
     * @return the selected file if opened, otherwise null.
     */
    public static File filePathDialog(FileNameExtensionFilter fileFilter_in) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(fileFilter_in);
        int returnVal = fileChooser.showOpenDialog(null);
        return returnVal == JFileChooser.APPROVE_OPTION ? fileChooser.getSelectedFile() : null;
    }


}
