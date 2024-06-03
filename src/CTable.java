import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class CTable extends JTable {
	/* field */
	String[][] contents = {};

	CusDefaultTableModel model;

	CTable(String[] header) {
		model = new CusDefaultTableModel(contents, header);
		this.setModel(model);
	}

	void getRowData() {
		ArrayList<String> rowDatas = new ConnectFile("./database/dbProducts.txt").loadRowData();
		
		if (rowDatas.size() != 0) {
			model.setRowCount(0);
			for (int i = 0; i < rowDatas.size(); i++) {
				String[] rowData = rowDatas.get(i).split("#");
				model.addRow(rowData);
			}
		}
	}
}