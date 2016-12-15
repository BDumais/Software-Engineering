package cs2212.team7;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * This class represents a collection that stores grades once they have been imported.
 *
 * Created by Nick DelBen
 * Created on 2/25/14
 * Version 1.0
 * Bugs: None known
 *
 * @param <Element_Type> the type of element that is importing.
 */
public class ImportResult<Element_Type> extends LinkedList<Element_Type> {

    /* The rows that failed to match the expected pattern. */
    private LinkedList<String> failedImports;
    private ArrayList<String> delNames;

    /**
     * Creates and returns a new instance of ImportResult.
     */
    public ImportResult(){
        failedImports = new LinkedList<>();
        delNames = null;
    }

    /**
     * Adds another element to the collection of successfully imported elements.
     *
     * @param importedElement_in the successfully imported item to add.
     */
    public void addSuccess(Element_Type importedElement_in) {
        add(importedElement_in);
    }

    /**
     * Adds another element to the collection of elements that failed to import.
     * @param failedElement_in the element that could not be imported.
     */
    public void addFail(String failedElement_in) {
        failedImports.add(failedElement_in);
    }

    /**
     * Returns the number of items that were successfully imported.
     *
     * @return the number of imported items.
     */
    public int numSuccess() {
        return size();
    }

    /**
     * Returns the number of items that failed to import.
     *
     * @return the number of failed imports.
     */
    public int numFail() {
        return failedImports.size();
    }

    /**
     * Returns a list containing the items that could not be imported.
     * @return list of failed imports
     */
    public LinkedList<String> getFailedImports() {
        return failedImports;
    }

    /**
     * Method to set deliverable name list of imported grades
     * @param dels
     */
    public void setDelNames(ArrayList<String> dels){
        delNames = dels;
    }

    /**
     * Method to get deliverable name list for imported grades
     * @return
     */
    public ArrayList<String> getDelNames(){
        return delNames;
    }
}
