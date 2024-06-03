import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

public class KioskPanelPage2 extends JPanel implements ActionListener {
	/* variable */
//	static KioskPanelPage2 kioskPanelPage2; 
	static String payMethod;
	static ArrayList<String> orderList;
	SelectedItemPnl[] pnlSelectedItem;
	static int totalSum, countOrder;
	static int menuLength;
	static int selectedItemCnt = 0;
	
	CButton[] btns, btnMenus;
	CLabel[] lbls;
	String[][] btnMenuLayouts;
	JPanel pnlMenu, pnlSelectedMenu;
	int cntPressArrowBtn, maxCntPressArrowBtn, codeUp;
	JScrollPane spSelectedMenu;
	
	Timer timer;
	TimerTask task = new TimerTask() {
		int timeCount = 240; //240초
		
		@Override
	    public void run() {
			if(timeCount>=0) {
	    		if(timeCount<=5) {
	    			lbls[4].setForeground(new Color(192, 0, 0));
	    		}
	    		
	    		lbls[4].setText("(" + timeCount + ")");
	    		timeCount--;
			} else {
				timer.cancel(); 
				Kiosk.insertPanel(new KioskPanelPage1()); 
			}
	    }
	};
	
	/* constructor */
	KioskPanelPage2() {
		
		ArrayList<String> rowDatasAll = new ConnectFile(Kiosk.filePathDBProduct).loadRowData();
		menuLength = rowDatasAll.size();
		
		this.setLayout(null);
		this.setBackground(Kiosk.bgKiosk);

		/* timer */
		timer = new Timer();
        timer.schedule(task, 1000, 1000);	//(1000 = 1초), 1초후에 실행해서 1초마다 반복
        /* timer end */
		
		/* button */
		String[][] btnLayouts = {
				// btnText or imgPath, LocationX, LocationY, Width, Height
				{ "./images/home(50).png",		"30", "30", "50", "50"}, 		//0:처음으로
				{ Kiosk.categories[0],			"30", "115", "155", "40"}, 		//1:메인메뉴
				{ Kiosk.categories[1],			"185", "115", "155", "40"}, 	//2:음료
				{ Kiosk.categories[2],			"340", "115", "155", "40"}, 	//3:사이드메뉴
				{ "", 							"495", "115", "155", "40"}, 	//4:카테고리 추가분(현재 공백)
				{ "./images/chevronL(50).png",	"40", "285", "30", "30"}, 		//5:left arrow
				{ "./images/chevronR(50).png",	"610", "285", "30", "30"}, 		//6:right arrow
				{ "전체삭제",					"330", "685", "100", "50"}, 	//7:전체취소
				{ "./images/credit(50).png",	"440", "685", "100", "100"}, 	//8:카드결제
				{ "./images/dollar(50).png", 	"550", "685", "100", "100"}  	//9:현금결제
		};
		
		btns = new Create().createButtons(btnLayouts);

		for (int i = 0; i < btns.length; i++) {
			if(i != 4)btns[i].addActionListener(this);
			
			//button option change
			switch (i) {
			case 0:
				btns[i].setBackground(null);
				break;
			case 1:	case 2:	case 3:	case 4:
				btns[i].setBackground(new Color(230, 230, 230));
				break;
			case 7:
				btns[i].setBackground(new Color(230, 230, 230));
				break;				
			case 8: case 9:
				btns[i].setBackground(Kiosk.bgAdmin);
				break;
			}

			this.add(btns[i]);
		}
		/* button 끝 */
		
		/* label */
		String[][] lblLayouts = {
				// lblText or imgPath, LocationX, LocationY, Width, Height
				{ "주문수량:",	"30", "685", "145", "50" },		//0:주문수량
				{ "0개  ",	"175", "685", "145", "50" },	//1:주문수량 단위
				{ "주문금액:",	"30", "735", "145", "50" },		//2:주문금액
				{ "0원  ",	"175", "735", "145", "50" }, 	//3:주문금액 단위
				{ "",       "330", "735", "100", "50"} 		//4:타이머
		};

		lbls = new Create().createLabels(lblLayouts);

		for (int i = 0; i < lbls.length; i++) {
			lbls[i].setOpaque(true);
			lbls[i].setFont(new Font("SansSanif", Font.BOLD, 18));	
			lbls[i].setBackground(Color.WHITE);
			lbls[i].setHorizontalAlignment(JLabel.RIGHT);
			
			// label Option change
			switch (i) {
			case 4:
				lbls[i].setFont(new Font("SansSanif", Font.BOLD, 25));
				lbls[i].setForeground(new Color(230, 230, 230));
				lbls[i].setBackground(null);
				lbls[i].setHorizontalAlignment(JLabel.CENTER);
				break;
			}
			
			this.add(lbls[i]);
		}
		/* label end */
		
		
		/* panel: menu */
		pnlMenu = new JPanel();
		pnlMenu.setLayout(null);
		pnlMenu.setBackground(Color.WHITE);
		pnlMenu.setBounds(30, 155, 620, 290);
		this.add(pnlMenu);
		/* panel: menu 끝 */

		
		/* panel: selected menu */
		pnlSelectedMenu = new JPanel();
		pnlSelectedMenu.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
		pnlSelectedMenu.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		pnlSelectedMenu.setPreferredSize(new Dimension(620, 200));
		pnlSelectedMenu.setBackground(Color.WHITE);

		spSelectedMenu = new JScrollPane(
				pnlSelectedMenu, 
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		spSelectedMenu.setBounds(30, 465, 620, 200);
		spSelectedMenu.getVerticalScrollBar().setUnitIncrement(15);
		
		// 선택 아이템 패널 배열 생성;
		pnlSelectedItem = new SelectedItemPnl[menuLength];
		this.add(spSelectedMenu);
		/* panel: selected menu end */
		
		
		/* 첫 번째 카테고리 메뉴 출력 */
		btns[1].setBackground(Color.WHITE);
		btnMenuLayouts = getBtnMenuLayouts(btns[1]);
		maxCntPressArrowBtn = (btnMenuLayouts.length % 8 == 0) ? (btnMenuLayouts.length / 8) - 1 : (btnMenuLayouts.length / 8);
		cntPressArrowBtn = 0;
		
		pnlMenu.removeAll();
		createMenuButtons(btnMenuLayouts, cntPressArrowBtn);
		Kiosk.pnlMain.updateUI();
		/* 첫 번째 카테고리 메뉴 출력 end */
	}

	/* method */
	@Override
	public void actionPerformed(ActionEvent e) {	
		/*
		 * btns[0]:처음으로, btns[1]:메인메뉴, btns[2]:음료, btns[3]:사이드메뉴, btns[4]:카테고리 추가분(현재 공백)
		 * btns[5]:left arrow, btns[6]:right arrow, btns[7]:전체취소, btns[8]:카드결제, btns[9]:현금결제
		 * lbls[0]:주문수량, lbls[1]:주문수량 단위, lbls[2]:주문금액, lbls[3]:주문금액 단위
		 */
		
		if (e.getSource() == btns[0]) { // 이전페이지
//			/* 변수 초기화 */
//			countOrder = 0;
//			totalSum = 0;
//			if(orderList != null) orderList = null;
//			/* 변수 초기화 */
			selectedItem_removeAll();
			
			timer.cancel();
			Kiosk.insertPanel(new KioskPanelPage1());
		
		} else if (e.getSource() == btns[1]) { // 메인메뉴
			// 버튼색 변경
			btns[1].setBackground(Color.WHITE);
			btns[2].setBackground(new Color(230, 230, 230));
			btns[3].setBackground(new Color(230, 230, 230));
			btns[4].setBackground(new Color(230, 230, 230));

			// 메뉴 가져오기
			btnMenuLayouts = getBtnMenuLayouts(btns[1]);
			maxCntPressArrowBtn = (btnMenuLayouts.length % 8 == 0) ? (btnMenuLayouts.length / 8) - 1 : (btnMenuLayouts.length / 8);
			cntPressArrowBtn = 0;

			pnlMenu.removeAll();
			createMenuButtons(btnMenuLayouts, cntPressArrowBtn);
			Kiosk.pnlMain.updateUI();
			
			codeUp = 0;

		} else if (e.getSource() == btns[2]) { // 음료(Drink)
			// 버튼색 변경
			btns[1].setBackground(new Color(230, 230, 230));
			btns[2].setBackground(Color.WHITE);
			btns[3].setBackground(new Color(230, 230, 230));
			btns[4].setBackground(new Color(230, 230, 230));

			// 메뉴 가져오기
			btnMenuLayouts = getBtnMenuLayouts(btns[2]);
			maxCntPressArrowBtn = (btnMenuLayouts.length % 8 == 0) ? (btnMenuLayouts.length / 8) - 1 : (btnMenuLayouts.length / 8);
			cntPressArrowBtn = 0;

			pnlMenu.removeAll();
			createMenuButtons(btnMenuLayouts, cntPressArrowBtn);
			Kiosk.pnlMain.updateUI();
			
			//전체메뉴개수 - 현재 카테고리의 메뉴개수
			codeUp = menuLength - btnMenus.length;

		} else if (e.getSource() == btns[3]) { // 사이드메뉴
			// 버튼색 변경
			btns[1].setBackground(new Color(230, 230, 230));
			btns[2].setBackground(new Color(230, 230, 230));
			btns[3].setBackground(Color.WHITE);
			btns[4].setBackground(new Color(230, 230, 230));

			// 메뉴 가져오기
			btnMenuLayouts = getBtnMenuLayouts(btns[3]);
			maxCntPressArrowBtn = (btnMenuLayouts.length % 8 == 0) ? (btnMenuLayouts.length / 8) - 1 : (btnMenuLayouts.length / 8);
			cntPressArrowBtn = 0;

			pnlMenu.removeAll();
			createMenuButtons(btnMenuLayouts, cntPressArrowBtn);
			Kiosk.pnlMain.updateUI();
			
			//전체메뉴개수 - 현재 카테고리의 메뉴개수
			codeUp = menuLength - btnMenus.length; 
			
		} else if (e.getSource() == btns[4]) { // 카테고리 추가여분(현재공백)
			//pass
			
		} else if (e.getSource() == btns[5]) { // 왼쪽방향 버튼
			if (cntPressArrowBtn > 0) {	cntPressArrowBtn--;	} 
			else { return; }

			pnlMenu.removeAll();
			createMenuButtons(btnMenuLayouts, cntPressArrowBtn);
			Kiosk.pnlMain.updateUI();

		} else if (e.getSource() == btns[6]) { // 오른쪽방향 버튼
			if (cntPressArrowBtn < maxCntPressArrowBtn) { cntPressArrowBtn++; } 
			else { return; }

			pnlMenu.removeAll();
			createMenuButtons(btnMenuLayouts, cntPressArrowBtn);
			Kiosk.pnlMain.updateUI();
			
		} else if (e.getSource() == btns[7]) { // 전체취소
			pnlSelectedMenu.removeAll();
			pnlSelectedMenu.revalidate();
			pnlSelectedMenu.repaint();
			selectedItem_removeAll();
			
		} else if (e.getSource() == btns[8]) { 
			if(countOrder > 0) {
				payMethod = "카드를 "; 
				Kiosk.tempSalesDatas[2] = payMethod.substring(0, payMethod.length()-2);	//결제방법, "카드를 "에서 "을 " 제외한 문자
				getOrderList();
				
				timer.cancel();
				Kiosk.insertPanel(new KioskPanelPage3(this));
			} else {
				System.out.println("메뉴를 선택하세요");
				JOptionPane.showConfirmDialog(null, "메뉴를 선택하세요", null , JOptionPane.PLAIN_MESSAGE);
			}
		
		} else if (e.getSource() == btns[9]) {
			if(countOrder > 0) {
				payMethod = "현금을 "; 
				Kiosk.tempSalesDatas[2] = payMethod.substring(0, payMethod.length()-2);	//결제방법, "현금을 "에서 "을 " 제외한 문자
				getOrderList();
				
				timer.cancel();
				Kiosk.insertPanel(new KioskPanelPage3(this));
			} else {
				JOptionPane.showConfirmDialog(null, "메뉴를 선택하세요", null , JOptionPane.PLAIN_MESSAGE);
			}
			
		} else {
			for(int i=0;i<menuLength;i++) {
				if(e.getSource() == btnMenus[i]) {
					CButton btn = btnMenus[i];
					addSelectedItem(pnlSelectedMenu, btn.lblName.getText(), Integer.parseInt(btn.lblPrice.getText()), i);
					break;
				}
			}
		}
	} // actionPerformed end

	
	// ********** pnlSelectedItem[i] 를 for문으로 돌렸을 때 null 값이 아닌 i 가 해당 아이템의 임의 코드로 설정되어있습니다.
	// code는 삭제 처리를 위해 해당 클래스에도 저장되며 pnlSelectedItem[i].code 로 불러올 수 있습니다.
	// 추가된 메뉴 리스트에 등록되어있는 모든 아이템은 아래 코드(for문)를 이용해 받아올 수 있습니다.
	
	// menuLength는 db의 총 row 갯수 입니다. (즉 저장된 총 아이템 갯수)
	
	/* for(int i=0;i<menuLength; i++){
	 * 	if (pnlSelectedItem[i]!=null){
	 * 		pnlSelectedItem[i].code; // 임의로 부여된 코드
	 * 		pnlSelectedItem[i].cnt; // 갯수
	 * 		pnlSelectedItem[i].price; // 단가
	 * 	}
	 * }
	 * 
	 * 합계(총 비용) => totalSum;
	 */
	
	void addSelectedItem(JPanel pnl, String name, int price, int i) {
		/* 선택된 메뉴 아이템 추가 */
		
		// pnlMenus가 카테고리의 변화에 따라 바뀌므로 codeUp을 통해 카테고리를 구분해줌
		int code = i + codeUp;
		
		if(pnlSelectedItem[code]==null) {
			pnlSelectedItem[code] = new SelectedItemPnl(pnl, name, price, code, this);
			selectedItemCnt++;
			pnl.setPreferredSize(new Dimension(600, selectedItemCnt * 40));
			pnl.add(pnlSelectedItem[code]);
		}
		pnlSelectedItem[code].cnt++;
		pnlSelectedItem[code].lblProdCount.setText(pnlSelectedItem[code].cnt+"");
		
		pnlSelectedItem[code].refresh();
		validate();
		repaint();
	}
	
	void selectedItem_SumPrice() {
		totalSum = 0;
		countOrder = 0;
		
		for (int i=0;i<menuLength; i++) {
			if(pnlSelectedItem[i] != null) {
				countOrder += pnlSelectedItem[i].cnt;
				totalSum += pnlSelectedItem[i].cnt * pnlSelectedItem[i].price;
			}
		}
		
		lbls[1].setText(countOrder + "개  ");
		lbls[3].setText(totalSum + "원  ");
	}
	
	void selectedItem_removeAll() {
		totalSum = 0;
		countOrder = 0;
		
		for (int i=0;i<menuLength; i++) {
			if(pnlSelectedItem[i] != null) {
				pnlSelectedItem[i].cnt = 0;
				pnlSelectedItem[i] = null;
			}
		}
		
		lbls[1].setText(countOrder + "개  ");
		lbls[3].setText(totalSum + "원  ");
	}

	
	void getOrderList() {
		/* 3Page에서 주문내역으로 반환할 선택된 메뉴 데이터를 orderList에 저장 */
		orderList = new ArrayList<>();
		String orderRowData = null;
	
		//orderList에 선택된 메뉴 저장
		for(int i=0; i<pnlSelectedItem.length; i++) {
			if(pnlSelectedItem[i] != null) {
				orderRowData = 
						pnlSelectedItem[i].name + "#" +
						pnlSelectedItem[i].cnt + "#" +
						pnlSelectedItem[i].cnt * pnlSelectedItem[i].price + "";
				
				orderList.add(orderRowData);
			}
		}		
	} //getOrderList end
	
	
	void createMenuButtons(String[][] btnMenuLayouts, int cntPressArrowBtn) {
		/* 카테고리별 메뉴 버튼 생성 */
		if (btnMenuLayouts.length > 0) {
			btnMenus = new Create().createButtons(btnMenuLayouts);
			for (int i = 0; i < btnMenus.length; i++) {
				if ((i / 8) == cntPressArrowBtn) {
					btnMenus[i].addActionListener(this);
					btnMenus[i].setBorder(new TitledBorder(new LineBorder(Color.LIGHT_GRAY)));
//					switch(i){} //버튼별 옵션 처리시, 사용
					pnlMenu.add(btnMenus[i]);
				}
			}
		}
	}

	
	String[][] getBtnMenuLayouts(CButton btn) {
		/* 카테고리별 메뉴 버튼 레이아웃 생성 */
		ArrayList<String> rowDatas = new ConnectFile(Kiosk.filePathDBProduct)
				.loadFindCategoryRowData(btn.getText() + "");
		
		String[][] btnMenuLayouts = new String[rowDatas.size()][7];
		for (int i = 0; i < rowDatas.size(); i++) {
			String[] tempRowData = rowDatas.get(i).split("#");

			// 0: btnText or imgPath, 1: LocationX, 2: LocationY, 3: Width, 4: Height, 5:
			// image text
			switch (i % 8) {
			case 0:
				btnMenuLayouts[i][0] = "./images/" + tempRowData[6];
				btnMenuLayouts[i][1] = "55";
				btnMenuLayouts[i][2] = "20";
				btnMenuLayouts[i][3] = "120";
				btnMenuLayouts[i][4] = "120";
				btnMenuLayouts[i][5] = tempRowData[2];
				btnMenuLayouts[i][6] = tempRowData[3];
				break;
			case 1:
				btnMenuLayouts[i][0] = "./images/" + tempRowData[6];
				btnMenuLayouts[i][1] = "185";
				btnMenuLayouts[i][2] = "20";
				btnMenuLayouts[i][3] = "120";
				btnMenuLayouts[i][4] = "120";
				btnMenuLayouts[i][5] = tempRowData[2];
				btnMenuLayouts[i][6] = tempRowData[3];
				break;
			case 2:
				btnMenuLayouts[i][0] = "./images/" + tempRowData[6];
				btnMenuLayouts[i][1] = "315";
				btnMenuLayouts[i][2] = "20";
				btnMenuLayouts[i][3] = "120";
				btnMenuLayouts[i][4] = "120";
				btnMenuLayouts[i][5] = tempRowData[2];
				btnMenuLayouts[i][6] = tempRowData[3];
				break;
			case 3:
				btnMenuLayouts[i][0] = "./images/" + tempRowData[6];
				btnMenuLayouts[i][1] = "445";
				btnMenuLayouts[i][2] = "20";
				btnMenuLayouts[i][3] = "120";
				btnMenuLayouts[i][4] = "120";
				btnMenuLayouts[i][5] = tempRowData[2];
				btnMenuLayouts[i][6] = tempRowData[3];
				break;
			case 4:
				btnMenuLayouts[i][0] = "./images/" + tempRowData[6];
				btnMenuLayouts[i][1] = "55";
				btnMenuLayouts[i][2] = "150";
				btnMenuLayouts[i][3] = "120";
				btnMenuLayouts[i][4] = "120";
				btnMenuLayouts[i][5] = tempRowData[2];
				btnMenuLayouts[i][6] = tempRowData[3];
				break;
			case 5:
				btnMenuLayouts[i][0] = "./images/" + tempRowData[6];
				btnMenuLayouts[i][1] = "185";
				btnMenuLayouts[i][2] = "150";
				btnMenuLayouts[i][3] = "120";
				btnMenuLayouts[i][4] = "120";
				btnMenuLayouts[i][5] = tempRowData[2];
				btnMenuLayouts[i][6] = tempRowData[3];
				break;
			case 6:
				btnMenuLayouts[i][0] = "./images/" + tempRowData[6];
				btnMenuLayouts[i][1] = "315";
				btnMenuLayouts[i][2] = "150";
				btnMenuLayouts[i][3] = "120";
				btnMenuLayouts[i][4] = "120";
				btnMenuLayouts[i][5] = tempRowData[2];
				btnMenuLayouts[i][6] = tempRowData[3];
				break;
			case 7:
				btnMenuLayouts[i][0] = "./images/" + tempRowData[6];
				btnMenuLayouts[i][1] = "445";
				btnMenuLayouts[i][2] = "150";
				btnMenuLayouts[i][3] = "120";
				btnMenuLayouts[i][4] = "120";
				btnMenuLayouts[i][5] = tempRowData[2];
				btnMenuLayouts[i][6] = tempRowData[3];
				break;
			} // switch end
		} // for i end

		return btnMenuLayouts;
	} //getBtnMenuLayouts end
}
