import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

public class KioskPanelAdmin3 extends JPanel implements ActionListener {
	/* variable */
	Date date = new Date();
	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

	CButton[] btns;
	CLabel[] lbls;
	CTextField[] tfs;
	String[] categories;
	JComboBox cbCategory;
	String[] tblHeader = { "제품코드", "제품분류", "제품이름", "제품가격", "제품재고", "등록일자", "이미지" };
	boolean isEdit;

	ImageIcon pImg;
	String imgFilePath;
	String[] imgFilePath_split;
	JPanel pInfoForm;
	CTable pTable;
	Component[] inputComponents;

	/* construct */
	KioskPanelAdmin3() {
		this.setLayout(null);
		this.setBackground(Kiosk.bgAdmin);

		/* button */
		String[][] btnLayouts = {
				// btnText or imgPath, LocationX, LocationY, Width, Height
				{ "./images/chevronL(30).png",	"30", "30", "30", "30" },		//0:이전 
				{ "./images/logout(30).png", 	"620", "30", "30", "30" },		//1:로그아웃 
				{ "./images/no_image.png", 		"30", "160", "150", "150" }, 	//2:제품이미지
				{ "등록", 						"405", "280", "80", "30" }, 	//3:등록
				{ "수정", 						"490", "280", "80", "30" }, 	//4:수정
				{ "삭제", 						"575", "280", "80", "30" },		//5:삭제
				{ "초기화", 						"320", "280", "80", "30" } 	//6:초기화
		};

		btns = new Create().createButtons(btnLayouts);
		
		for (int i = 0; i < btns.length; i++) {
			btns[i].addActionListener(this);
			
			// button Option change
			switch (i) {
			case 0:
			case 1:
				btns[i].setBackground(null);
				break;
			case 2: case 3: case 4: case 5: case 6:
				btns[i].setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
				btns[i].setFont(new Font("SansSerif", Font.BOLD, 12));
			}
			
			this.add(btns[i]);
		}
		/* button end */

		/* label */
		String[][] lblLayouts = {
				// lblText or imgPath, LocationX, LocationY, Width, Height
				{ "Product Management",		"30", "80", "400", "50" },		//0:타이틀
				{ "Code", 					"225", "155", "100", "30" }, 	//1:Code
				{ "Category", 				"425", "155", "100", "30" }, 	//2:Category
				{ "Name", 					"225", "190", "100", "30" }, 	//3:Name
				{ "Unit Price", 			"425", "190", "100", "30" }, 	//4:Unit Price
				{ "Stock", 					"225", "225", "100", "30" }, 	//5:Stock
				{ "Register Date", 			"425", "225", "100", "30" } 	//6:Register Date
		};

		lbls = new Create().createLabels(lblLayouts);

		
		for (int i = 0; i < lbls.length; i++) {
			// label Option change
			switch (i) {
			case 0:
				lbls[i].setFont(new Font("SansSerif", Font.BOLD, 35));
				break;
			case 1:	case 2: case 3:	case 4:	case 5:	case 6:
				lbls[i].setFont(new Font("SansSerif", Font.BOLD, 15));
			}
			
			this.add(lbls[i]);
		}
		/* label end */

		/* textfield */
		String[][] tfLayouts = {
				// lblText or imgPath, LocationX, LocationY, Width, Height
				{ "", "275", "160", "120", "28" }, //0:Code
				{ "", "275", "195", "120", "28" }, //1:Name
				{ "", "535", "195", "120", "28" }, //2:Unit Price
				{ "", "275", "230", "120", "28" }, //3:Stock
				{ "", "535", "230", "120", "28" }, //4:Register Date
		};

		tfs = new Create().createTextFields(tfLayouts);

		
		for (int i = 0; i < tfs.length; i++) {
			tfs[i].setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
			tfs[i].setHorizontalAlignment(JTextField.CENTER);
			
			// textfield Option change
			switch (i) {
			case 0:
				tfs[i].setEditable(false);
				tfs[i].setText(Kiosk.getMaxNum(Kiosk.filePathDBProduct)+"");
				break;
			case 4:
				tfs[i].setText(formatter.format(date));
				tfs[i].setEditable(false);
			}
		
			this.add(tfs[i]);
		}
		/* textfield end */

		
		/* combobox */
		categories = Kiosk.categories;
		cbCategory = new JComboBox(categories);
		cbCategory.setBounds(535, 160, 120, 28);
		DefaultListCellRenderer listRenderer = new DefaultListCellRenderer();
	    listRenderer.setHorizontalAlignment(DefaultListCellRenderer.CENTER); // center-aligned items
	    cbCategory.setRenderer(listRenderer);
		this.add(cbCategory);
		/* combobox end */

		
		/* table */
		pTable = new CTable(tblHeader);
		JScrollPane spTable = new JScrollPane(pTable);
		spTable.setBounds(30, 335, 625, 420);
		spTable.getViewport().setBackground(Kiosk.bgAdmin);
//		spTable.setBorder(BorderFactory.createEmptyBorder());
		pTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				JTable t = (JTable) e.getSource();
				if (e.getClickCount() == 2) {
					isEdit = isEdit();
				}
			}
		});
		this.add(spTable);
		/* table end */

		// db로드: productTable.txt
		pTable.getRowData();
	}

	/* method */
	@Override
	public void actionPerformed(ActionEvent e) {
		/*
		 * btns[0]:이전, btns[1]:로그아웃, btns[2]:제품이미지, btns[3]:등록, btns[4]:수정, btns[5]:삭제, btns[6]:초기화
		 * lbls[0]:타이틀, lbls[1]:Code, lbls[2]:Category, lbls[3]:Name
		 * lbls[4]:Unit Price, lbls[5]:Stock, lbls[6]:Register Date
		 * tfs[0]:Code, tfs[1]:Name, tfs[2]:Unit Price, tfs[3]:Stock, tfs[4]:Register Date
		 */

		if (e.getSource() == btns[0]) {
			Kiosk.insertPanel(new KioskPanelAdmin2());

		} else if (e.getSource() == btns[1]) {
			if ((JOptionPane.showConfirmDialog(null, "로그아웃 하시겠습니까?", "로그아웃", JOptionPane.YES_NO_OPTION) == 0)) {
				Kiosk.insertPanel(new KioskPanelPage1());
			}

		} else if (e.getSource() == btns[2]) {
			openFileDialog();

		} else if (e.getSource() == btns[3]) {
			if ((JOptionPane.showConfirmDialog(null, "등록하시겠습니까?", "등록", JOptionPane.YES_NO_OPTION) == 0)) {
				// 입력여부 체크
				if (checkBlank()) {
					addProduct(false);
					resetTextField();
				}
			}

		} else if (e.getSource() == btns[4]) {
			if ((JOptionPane.showConfirmDialog(null, "수정하시겠습니까?", "수정", JOptionPane.YES_NO_OPTION) == 0)) {
				// 입력여부 체크
				if (checkBlank()) {
					updateProduct();
					resetTextField();
				}
			}
			
		} else if (e.getSource() == btns[5]) {
				if ((JOptionPane.showConfirmDialog(null, "삭제하시겠습니까?", "삭제", JOptionPane.YES_NO_OPTION) == 0)) {
					deleteProduct();
					resetTextField();
				}
				
		} else if (e.getSource() == btns[6]) {
			changeImg("./images/no_image.png");
			cbCategory.setSelectedIndex(0);
			tfs[0].setText(Kiosk.getMaxNum(Kiosk.filePathDBProduct)+""); //Code
			tfs[1].setText(""); //Name
			tfs[2].setText(""); //Unit Price
			tfs[3].setText(""); //Stock
			tfs[4].setText(formatter.format(date)); //Register Date
		}
	} //actionPerformed end

	void openFileDialog() {
		/* 파일선택 대화상자 열기 */

		JFrame fileDialogFrame = new JFrame();
		FileDialog imgFileOpen = new FileDialog(fileDialogFrame, "이미지 선택", FileDialog.LOAD);
		fileDialogFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		imgFileOpen.setVisible(true);
		imgFilePath = imgFileOpen.getDirectory() + imgFileOpen.getFile(); // 선택된 파일 경로
		
		System.out.println(imgFilePath);
		
		if (imgFilePath.equals("nullnull"))
			return;
		imgPathSplit("\\\\");
		changeImg(imgFilePath);
	} //openFileDialog end

	
	void imgPathSplit(String splitStr) {
		if (imgFilePath != null) {
			imgFilePath_split = imgFilePath.split(splitStr);
		}
	} //imgPathSplit end

	void changeImg(String path) {
		// 이미지 변경
		pImg = new ImageIcon(path);
		btns[2].lblIcon.setIcon(pImg);
		btns[2].setPreferredSize(new Dimension(pImg.getIconWidth(), pImg.getIconHeight()));
	} //changeImg end

	Boolean checkBlank() {
		/* 입력 여부 확인 */

		for (int i = 1; i <= 3; i++) {
			if (tfs[i].getText().trim().equals("")) {
				// 메세지 처리
				JOptionPane.showConfirmDialog(null, lbls[i + 2].getText() + "를(을) 입력하세요.", "입력",
						JOptionPane.CLOSED_OPTION);
				tfs[i].requestFocus();
				return false;
			}
		}
		return true;
	} //checkBlank end

	void resetTextField() {
		/* TextField 초기화 */

		for (int i = 0; i < tfs.length; i++) {
			if (i == 0) {
				tfs[i].setText(Kiosk.getMaxNum(Kiosk.filePathDBProduct)+"");
			} else if (i == 4) {
				tfs[i].setText(formatter.format(date));
			} else {
				tfs[i].setText("");
			}
		}
	} // resetTextField end

	void addProduct(boolean isEdit) {
		/* Product 등록 */

		// 테이블 rowData 생성
		String[] rowDatas = new String[tblHeader.length];
		for (int i = 0; i < pTable.getColumnCount(); i++) {
			// pTable Columns: 제품코드(0), 제품분류(1), 제품이름(2), 제품가격(3), 제품재고(4), 등록일자(5), 이미지(6)
			if (i == 0) {
				rowDatas[i] = Kiosk.getMaxNum(Kiosk.filePathDBProduct)+"";

			} else if (i == 1) {
				rowDatas[i] = cbCategory.getSelectedItem() + "";

			} else if (i <= 5) {
				rowDatas[i] = tfs[i - 1].getText();

			} else if (i == 6) {
				try {
					rowDatas[i] = imgFilePath_split[imgFilePath_split.length - 1];
				} catch (Exception e) {
					rowDatas[i] = "no_image.png";
				}
			}
		} // for i end

		if (isEdit) {
			rowDatas[0] = tfs[0].getText();
			rowDatas[5] = formatter.format(date);
			isEdit = false;
		}

		// 테이블에 rowDatas 추가
		pTable.model.addRow(rowDatas);

		// dbProducts 파일에 저장
		String rowData = String.join("#", rowDatas);
		new ConnectFile(Kiosk.filePathDBProduct).saveRowData(rowData, true);
		
		changeImg("./images/no_image.png");
	}

	void updateProduct() {
		deleteProduct();
		addProduct(true);
	}

	void deleteProduct() {
		/* Product 삭제 */
		int selectedRowIndex = pTable.getSelectedRow();

		if (selectedRowIndex == -1) {
			System.out.println("삭제할 행을 선택해주세요");
		} else {

			pTable.model.removeRow(selectedRowIndex);
			pTable.changeSelection(selectedRowIndex, 0, false, false);

			// dbProducts 파일에 저장
			String[] rowDatas = new String[pTable.getColumnCount()];
			String rowData = "";

			for (int i = 0; i < pTable.getRowCount(); i++) {
				for (int j = 0; j < rowDatas.length; j++) {
					rowDatas[j] = pTable.getValueAt(i, j) + "";
				}

				if (rowData != null) {
					if (i < pTable.getRowCount() - 1) {
						rowData += String.join("#", rowDatas) + "\n";
					} else {
						rowData += String.join("#", rowDatas);
					}
				}
			}
			new ConnectFile(Kiosk.filePathDBProduct).saveRowData(rowData, false);
			
			changeImg("./images/no_image.png");
		} // if end
	} // deleteProduct end

	boolean isEdit() {
		/* 테이블 행 더블클릭시, 해당 데이터를 컨트롤양식에 반환 후, Edit 여부 */

		int selectRow = pTable.getSelectedRow();

		// TextField
		tfs[0].setText(pTable.getValueAt(selectRow, 0).toString()); // Code
		tfs[1].setText(pTable.getValueAt(selectRow, 2).toString()); // Name
		tfs[2].setText(pTable.getValueAt(selectRow, 3).toString()); // Unit Price
		tfs[3].setText(pTable.getValueAt(selectRow, 4).toString()); // Stock
		tfs[4].setText(pTable.getValueAt(selectRow, 5).toString()); // Register Date

		// ComboBox
		for (int i = 0; i < categories.length; i++) {
			if (categories[i].equals(pTable.getValueAt(selectRow, 1).toString())) {
				cbCategory.setSelectedIndex(i);
				break;
			}
		}

		// Image
		String path = "./images./" + pTable.getValueAt(selectRow, 6).toString();
		imgFilePath = path;
		imgPathSplit("/");
		changeImg(path);

		return true;
	} // editProduct end
}
