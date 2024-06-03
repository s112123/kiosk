import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class KioskPanelAdmin4 extends JPanel implements ActionListener {
	/* field */
	CButton[] btns;
	CLabel[] lbls;
	CTextField[] tfs;
	CTableOrder oTable, oTableSearch;
	JScrollPane spTable, spTableSearch;

	/* construct */
	KioskPanelAdmin4() {
		this.setLayout(null);
		this.setBackground(Kiosk.bgAdmin);
		
		/* button */
		String[][] btnLayouts = {
				//btnText or imgPath, LocationX, LocationY, Width, Height,
				{ "./images/chevronL(30).png",	"30", "30", "30", "30"},	//0:이전
				{ "./images/logout(30).png",	"620", "30", "30", "30"},	//1:로그아웃
				{ "전체선택",					"30", "170", "80", "30"}, 	//2:전체선택
				{ "검색",						"490", "170", "80", "30"}, 	//3:검색
				{ "삭제",						"575", "170", "80", "30"} 	//4:삭제
		};

		btns = new Create().createButtons(btnLayouts);

		for (int i = 0; i < btns.length; i++) {
			btns[i].addActionListener(this);
			
			// button option change 
			switch (i) {
			case 0: case 1:
				btns[i].setBackground(null);
				break;
			case 2: case 3: case 4:
				btns[i].setFont(new Font("SansSerif", Font.BOLD, 12));
				btns[i].setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
			}
	
			this.add(btns[i]);
		}		
		/* button end */
		

		/* label */
		String[][] lblLayouts = {
				// lblText or imgPath, LocationX, LocationY, Width, Height
				{ "Order Management",	"30", "80", "400", "50" },	//0: 타이틀
				{ "주문번호",				"315", "170", "80", "30" }  //1: 주문번호
		};
		
		lbls = new Create().createLabels(lblLayouts);
		
		for (int i = 0; i < lbls.length; i++) {
			/* label Option change */
			switch (i) {
			case 0:
				lbls[i].setFont(new Font("SansSanif", Font.BOLD, 35));
				break;
			case 1:
				lbls[i].setFont(new Font("SansSanif", Font.BOLD, 14));
				break;
			}
			/* label Option change end */
			
			this.add(lbls[i]);
		}
		/* label end */
		
		
		/* textfield */
		String[][] tfLayouts = {
				// lblText or imgPath, LocationX, LocationY, Width, Height
				{ "", "385", "170", "100", "30" }, // 0:제품코드 입력
		};

		tfs = new Create().createTextFields(tfLayouts);

		for (int i = 0; i < tfs.length; i++) {
			/* textfield option change */
			switch (i) {
			case 0:
				tfs[i].setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
				tfs[i].setHorizontalAlignment(JTextField.CENTER);
			}
			this.add(tfs[i]);
		}
		/* textfield end */

		
		/* table */
		// 주문 내역 테이블 db 자동 로드
		oTable = new CTableOrder();
		spTable = new JScrollPane(oTable);
		scrPnlSet(spTable);

		// 가상 테이블
		oTableSearch = new CTableOrder();
		spTableSearch = new JScrollPane(oTableSearch);
		scrPnlSet(spTableSearch);
		spTableSearch.setVisible(false);
		
		this.add(spTable);
		this.add(spTableSearch);
		/* table end */
	}
	
	/* method */
	@Override
	public void actionPerformed(ActionEvent e) {
		/*
		 * btns[0]:이전, btns[1]:로그아웃, btns[2]:전체선택, btns[3]:검색, btns[4]:삭제
		 * lbls[0]:타이틀, lbls[1]:주문번호
		 * tfs[0]:제품코드 입력
		 */

		String[] rowDatas = new String[oTable.header.length-1];

		if (e.getSource() == btns[0]) {	//이전
			Kiosk.insertPanel(new KioskPanelAdmin2());		
		
		} else if(e.getSource() == btns[1]) { //로그아웃
			if ((JOptionPane.showConfirmDialog(null, "로그아웃 하시겠습니까?", "로그아웃", JOptionPane.YES_NO_OPTION) == 0)) {
				Kiosk.insertPanel(new KioskPanelPage1());
			}
		
		} else if (e.getSource() == btns[2]) { //전체선택
			oSelectAll();
			
		} else if (e.getSource() == btns[3]) { //검색
			oSearch();
			
		} else if (e.getSource() == btns[4]) { //삭제
			if ((JOptionPane.showConfirmDialog(null, "삭제하시겠습니까?", "삭제", JOptionPane.YES_NO_OPTION) == 0)) {
				oDel();
				oSave(rowDatas);
			}
		}
	} // actionPerformed end

	
	void oDel() {
		// 삭제 버튼 입력 후 동작
		String data = oTable.data;
		String resultDatas[];
		String resultRow[];
		if (data != null) {
			int cnt = 0;
			
			if (spTable.isVisible()) {
				removeAction(oTable);
			} else if (spTableSearch.isVisible()) {
				removeAction(oTableSearch);
			}
		}
	} //oDel end

	void removeAction(CTableOrder table){
		// 삭제 실행 메소드
		int cnt = 0;
		int idx = table.model.getRowCount() - 1;
		
		for (int i = idx; i >= 0; i--) {
			if (table.model.getValueAt(i, 0) == (Boolean) true) {
				cnt++;
			}
		}
		
		if (JOptionPane.showConfirmDialog(null, cnt + "건의 주문이 선택되었습니다. 정말 삭제하시겠습니까?", "삭제",
				JOptionPane.YES_NO_OPTION) == 0) {
			for (int i = idx; i >= 0; i--) {
				if (table.model.getValueAt(i, 0) == (Boolean) true) {
					table.model.removeRow(i);
					if (table==oTableSearch) {
						oTable.model.removeRow(oTableSearch.searchDataNum[i]);
					}
				}
			}
			JOptionPane.showConfirmDialog(null, "총 " + cnt + "개의 주문 내역이 삭제되었습니다.", "삭제", JOptionPane.CLOSED_OPTION);
		}
	} //removeAction end
	
	
	void oSave(String rowDatas[]) {
		// 저장 기능 동작
		String rowData = "";

		int idx = oTable.getRowCount();
			
		for (int i = 0; i < idx; i++) {
			for (int j = 1; j < oTable.getColumnCount(); j++) {
				rowDatas[j - 1] = oTable.getValueAt(i, j) + "";
			}
			
			if (i < oTable.getRowCount() - 1) {
				rowData += String.join("#", rowDatas) + "\n";
			} else {
				rowData += String.join("#", rowDatas);
			}
		}
		new ConnectFile(Kiosk.filePathDBSales).saveRowData(rowData, false);
		
	} //oSave end
	
	
	void oSearch() {
		// 검색 기능 동작
		String search = tfs[0].getText(); //tfs[0]: 제품번호 입력
		oTableSearch.getRowData();
		if (search != null && !search.equals("")) {
			spTable.setVisible(false);
			oTableSearch.getSearchRowData(search);
			spTableSearch.setVisible(true);

		} else {
			spTableSearch.setVisible(false);
			spTable.setVisible(true);
		}

	} //oSerach end

 //<기존코드: 제품번호 조회 후, 전체선택 오류>	
//	void oSelectAll() {
//		// 전체 선택 기능
//		int cnt = oTable.model.getRowCount();
//		if (btns[2].getText().equals("전체선택")) {
//			for (int i = 0; i < cnt; i++) {
//				oTable.model.setValueAt(true, i, 0);
//			}
//			btns[2].setText("선택취소");
//		} else {
//			for (int i = 0; i < cnt; i++) {
//				oTable.model.setValueAt(false, i, 0);
//			}
//			btns[2].setText("전체선택");
//		}
//	}

// <수정코드: 제품번호 조회 후, 전체선택 오류 처리>	
	void oSelectAll() {
		// 전체 선택 기능
		int cnt = oTable.model.getRowCount();
		if (btns[2].getText().equals("전체선택")) { 
			if(oTable.getRowCount()==oTableSearch.getRowCount()) {
				for (int i = 0; i < cnt; i++) {
					oTable.model.setValueAt(true, i, 0);
				}
			}else {
				for(int i = 0; i<oTableSearch.getRowCount(); i++) {
					oTableSearch.model.setValueAt(true, i, 0);
					for(int j = 0; j<cnt; j++) {
						if(oTable.getValueAt(j, 1).equals(tfs[0].getText())) {
							oTable.model.setValueAt(true, j, 0);
						}
					}
				}
			}
			btns[2].setText("선택취소");
		} else {
			if(oTable.getRowCount()==oTableSearch.getRowCount()) {
				for (int i = 0; i < cnt; i++) {
					oTable.model.setValueAt(false, i, 0);
				}
			}else {
				for(int i = 0; i<oTableSearch.getRowCount(); i++) {
					oTableSearch.model.setValueAt(false, i, 0);
				}for(int j = 0; j<cnt; j++) {
					if(oTable.getValueAt(j, 1).equals(tfs[0].getText())) {
						oTable.model.setValueAt(true, j, 0);
					}
				}
			}
			btns[2].setText("전체선택");
		}
	} //oSelectAll end
	
	void scrPnlSet(JScrollPane scrPnl) {
		// 스크롤패널 세팅
		scrPnl.setBounds(30, 215, 625, 600);
		scrPnl.getViewport().setBackground(Kiosk.bgAdmin);
		scrPnl.setBorder(BorderFactory.createEmptyBorder());
	} //scrPnlSet end
}
