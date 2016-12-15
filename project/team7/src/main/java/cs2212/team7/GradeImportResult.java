package cs2212.team7;

/**
 * This class represents a collection that stores grades once they have been imported.
 *
 * Created by Nick DelBen
 * Created on 2/25/14
 * Version 1.0
 * Bugs: None known
 * @param <ImportedGrade>
 */
public class GradeImportResult<ImportedGrade> extends ImportResult<ImportedGrade> {

    /* The names of the imported deliverables. */
    private String[] deliverables;

    /**
     * Constructs and returns a new instance of GradeImportResult
     *
     * @param deliverables_in the deliverables associated with this import.
     */
    public GradeImportResult(String[] deliverables_in) {
        super();
        deliverables = deliverables_in;
    }

    /**
     * Inserts an element into the list of grades.
     *
     * @param importedGrade_in the grade to be added.
     */
    public void addGrade(ImportedGrade importedGrade_in) {
        add(importedGrade_in);
    }

    /**
     * Returns an array representing
     * @return
     */
    public String[] getDeliverables() {
        return deliverables;
    }
}
