import javax.swing.JTable;

public class CTableOrder extends JTable {
	/* field */
	String[][] contents = {};
	CusDefaultTableModel model;
	String data;
	String[] resultDatas;
	String[] resultRow;
	String[] header = { "선택", "주문번호", "제품명", "주문수량", "결제금액", "결제방법", "결제일자", "포장여부" };
	int[] searchDataNum;
	Object obj[] = new Object[header.length + 1];

	CTableOrder() {
		// 첫번째 열만 수정 가능한 모델 생성
		model = new CusDefaultTableModel() {
			public Class<?> getColumnClass(int column) {
				switch (column) {
				case 0:
					return Boolean.class;
				default:
					return String.class;
				}
			}

			public boolean isCellEditable(int row, int column) {
				if (column == 0)
					return true;
				else
					return false;

			}

		};

		// 테이블 헤더 추가
		for (int i = 0; i < header.length; i++) {
			model.addColumn(header[i]);
		}

		// 모델에 오브젝트 삽입
		for (int i = 0; i < this.getRowCount(); i++) {
			model.addRow(new Object[0]);

		}

		this.setModel(model);
		getRowData();
		searchDataNum = new int[getRowData()];
	}

	// 데이터 불러오기
	int getRowData() {
		String resultData = new ConnectFile(Kiosk.filePathDBSales).getResultData();
		data = resultData;
		int cnt = 0;
		
		if (resultData != "") {
			resultDatas = resultData.split("/n");

			clearData();
			for (int i = 0; i < resultDatas.length; i++) {
				resultRow = resultDatas[i].split("#");
				for (int j = 0; j < this.getColumnCount(); j++) {
					if (j == 0) {
						obj[j] = false;
					} else {
						obj[j] = resultRow[j - 1];
					}
				}
				model.addRow(obj);
				cnt++;
			}
		}
		return cnt;
	}
	
	// 데이터 검색 (검색용 가상 테이블에서만 사용됨)
	void getSearchRowData(String search) {
		if (data != null) {
			resultDatas = data.split("/n");
			clearData();
			int cnt = 0;
			for (int i = 0; i < resultDatas.length; i++) {
				resultRow = resultDatas[i].split("#");
				if (resultRow[0].equals(search)) {
					searchDataNum[cnt++] = i;
					System.out.println("searchNum: " + searchDataNum[cnt - 1]);
					for (int j = 0; j < this.getColumnCount(); j++) {
						if (j == 0) {
							obj[j] = false;
						} else {
							obj[j] = resultRow[j - 1];
						}
					}
					model.addRow(obj);
				}

			}
		}
	}
	
	// 테이블에있는 모든 데이터 제거
	void clearData() {
		int rowMax = model.getRowCount();
		for (int i = 0; i < rowMax; i++) {
			model.removeRow(0);
		}
	}
}
