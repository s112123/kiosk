import java.util.Vector;

import javax.swing.table.DefaultTableModel;

public class CusDefaultTableModel extends DefaultTableModel {

	@Override
	public boolean isCellEditable(int row, int column) {

		return false;
	}

	public CusDefaultTableModel() {
		super();

	}

	public CusDefaultTableModel(int rowCount, int columnCount) {
		super(rowCount, columnCount);

	}

	public CusDefaultTableModel(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);

	}

	public CusDefaultTableModel(Object[][] data, Object[] columnNames) {
		super(data, columnNames);

	}

	public CusDefaultTableModel(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);

	}

	public CusDefaultTableModel(Vector data, Vector columnNames) {
		super(data, columnNames);

	}

}