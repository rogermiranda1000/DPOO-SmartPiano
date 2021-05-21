package view;

import javax.swing.table.DefaultTableModel;

public class UneditableTable extends DefaultTableModel {

    public UneditableTable() {
        super();
    }

    @Override
    public boolean isCellEditable(int row, int cols) {
        return false;
    }

}
