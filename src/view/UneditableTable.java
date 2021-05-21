package view;

import javax.swing.table.DefaultTableModel;

public class UneditableTable extends DefaultTableModel {
    public UneditableTable() {
        super();
    }

    public UneditableTable(String[][] tableData, String[] columnID) {
        super(tableData, columnID);
    }

    @Override
    public boolean isCellEditable(int row, int cols) {
        return false;
    }

}
