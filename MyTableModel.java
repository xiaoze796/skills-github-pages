import javax.swing.table.AbstractTableModel;

public class MyTableModel extends AbstractTableModel {
    private String[] columnNames; // 列名
    private Object[][] data;      // 表格数据

    public MyTableModel(String[] columnNames, Object[][] data) {
        this.columnNames = columnNames;
        this.data = data;
    }

    @Override
    public int getRowCount() {
        return data.length;
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return data[rowIndex][columnIndex];
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false; // 表格单元格不可编辑
    }

    // 更新数据并刷新表格
    public void updateDataAndRefresh(Object[][] newData) {
        this.data = newData;
        fireTableDataChanged(); // 通知表格数据变更
    }
}
