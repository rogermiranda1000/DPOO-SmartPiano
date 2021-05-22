package view;

import javax.swing.table.DefaultTableModel;

/**
 * Custom default table model that can't be edited by the user
 */
public class UneditableTable extends DefaultTableModel {

    /**
     * Calls the DefaultTableModel's constructor
     */
    public UneditableTable() {
        super();
    }

    /**
     * Creates an uneditable table with the data
     * @param tableData Data to show on the table
     * @param columnID The names of the columns
     */
    public UneditableTable(String[][] tableData, String[] columnID) {
        super(tableData, columnID);
    }

    /**
     * Returns if this cell is editable, it will always return false for this class
     * @param row Row the user is trying to edit
     * @param cols Column the user is trying to edit
     * @return It wil always return false
     */
    @Override
    public boolean isCellEditable(int row, int cols) {
        return false;
    }

}
