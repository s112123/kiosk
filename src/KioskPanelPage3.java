
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;

public class KioskPanelPage3 extends JPanel implements ActionListener{
	/* variable */
	Date date = new Date();
	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	CButton[] btns;
	CLabel[] lbls;
	CTable tblOrder;
	
	Timer timer; 
	TimerTask task = new TimerTask() {
		int timeCount = 20; //10초
		
		@Override
	    public void run() {
			if(timeCount>=0) {
	    		if(timeCount<=5) {
	    			lbls[7].setForeground(new Color(192, 0, 0));
	    		}
	    		
	    		lbls[7].setText("(" + timeCount + ")");
	    		timeCount--;
			} else {
				timer.cancel(); 
				Kiosk.insertPanel(new KioskPanelPage1()); 
			}
	    }
	};
	
	KioskPanelPage2 p2Pnl;
	
	/* constructor */
											// 카드결제 시작
	public KioskPanelPage3(KioskPanelPage2 pnl) {
		
		p2Pnl = pnl;
		
		this.setLayout(null);
		this.setBackground(Color.WHITE);
		
		/* timer */
		timer = new Timer();
        timer.schedule(task, 1000, 1000);	//(1000 = 1초), 1초후에 실행해서 1초마다 반복
        /* timer end */
		
		/* button */
		String[][] btnLayouts = {
				//btnText or imgPath, LocationX, LocationY, Width, Height, image text
				{"./images/home(50).png", 	"30", "30", "50", "50"},		//0:처음으로 
				{"돌아가기", 					"30", "710", "150", "50"},		//1:돌아가기
				{"승인요청", 					"505", "710", "150", "50"}		//2:승인요청
		};
		
		btns = new Create().createButtons(btnLayouts);

		for(int i=0; i<btns.length; i++) {
			
			btns[i].addActionListener(this);
			
			//button option change
			switch(i) {
			case 1:
				btns[i].setBackground(new Color(230, 230, 230));
				break;
			case 2:
				btns[i].setBackground(Kiosk.bgKiosk);
				btns[i].setForeground(Color.WHITE);
				break;
			}	
			
			this.add(btns[i]);
		}		
		/* button end */		
		
		/* label */
		String imgPath ;  
		if( KioskPanelPage2.payMethod == "카드를 ") {
			imgPath = "./images/incard(100).png";
		} else {
			imgPath = "./images/incash(100).png";
		}
		
		String[][] lblLayouts = {
				// lblText or imgPath, LocationX, LocationY, Width, Height
				{ "주문내역",										"30", "110", "625", "50" },		//0:주문내역 문구
				{ "주문수량:",	 									"365", "425", "145", "30" },	//1:주문수량 문구
				{ KioskPanelPage2.countOrder + "개  ",			"510", "425", "145", "30" },	//2:주문수량 
				{ "결제금액:",	 									"365", "455", "145", "30" },	//3:결제금액 문구
				{ KioskPanelPage2.totalSum + "원  ",	 			"510", "455", "145", "30" },	//4:결제금액 
				{ imgPath,	 									"290", "520", "100", "100" },	//5:결제방법 이미지
				{ KioskPanelPage2.payMethod + "투입구에 넣어주세요",	"30", "630", "625", "50" },		//6:결제방법 문구
				{ "",	 										"605", "630", "50", "50" }		//7:타이머
		};

		lbls = new Create().createLabels(lblLayouts);

		for (int i = 0; i < lbls.length; i++) {
			lbls[i].setOpaque(true);
			
			// label Option change
			switch (i) {
			case 0:
				lbls[i].setFont(new Font("SansSanif", Font.BOLD, 30));
				break;
			case 1: case 2: case 3: case 4:
				lbls[i].setFont(new Font("SansSanif", Font.BOLD, 16));
				lbls[i].setHorizontalAlignment(JLabel.RIGHT);
				break;
			case 6:
				lbls[i].setFont(new Font("SansSanif", Font.BOLD, 25));
				lbls[i].setHorizontalAlignment(JLabel.CENTER);
				lbls[i].setBackground(null);
				break;
			case 7:
				lbls[i].setFont(new Font("SansSanif", Font.BOLD, 25));
				lbls[i].setForeground(new Color(100, 100, 100));
				lbls[i].setHorizontalAlignment(JLabel.CENTER);
				break;
			}
			
			this.add(lbls[i]);
		}
		/* label end */
		
		/* table */
		String[] tblOrderHeader = {"제품명", "주문수량", "금액"};
		tblOrder = new CTable(tblOrderHeader);
//		tblOrder.setTableHeader(null);	//머리글 숨기기
		tblOrder.setShowGrid(false);	//칸 선 숨기기
		tblOrder.setRowHeight(30);		//행 높이
		tblOrder.setFont(new Font("SansSanif", Font.BOLD, 16));
		tblOrder.getTableHeader().setFont(new Font("SansSanif", Font.BOLD, 16));

		//열 너비
		tblOrder.getColumnModel().getColumn(0).setPreferredWidth(250);
		tblOrder.getColumnModel().getColumn(1).setPreferredWidth(50);
		tblOrder.getColumnModel().getColumn(2).setPreferredWidth(50);
		
		//열 정렬
		DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
		renderer.setHorizontalAlignment(JLabel.RIGHT);
		tblOrder.getColumnModel().getColumn(1).setCellRenderer(renderer);
		tblOrder.getColumnModel().getColumn(2).setCellRenderer(renderer);
		
	
		JScrollPane spTableOrder = new JScrollPane(tblOrder);
		spTableOrder.setBounds(30, 165, 625, 250);
		spTableOrder.getViewport().setBackground(Color.WHITE);
		spTableOrder.setBorder(new TitledBorder(new LineBorder(Color.LIGHT_GRAY)));
		tblOrder.getTableHeader().setPreferredSize(new Dimension(spTableOrder.getWidth(), 30));
		
		for(int i=0; i<KioskPanelPage2.orderList.size(); i++) {
			String[] tempRowData = KioskPanelPage2.orderList.get(i).split("#");
			String[] orderRowData = {"  "+ tempRowData[0], tempRowData[1] + "개", tempRowData[2] + "원"	};
			tblOrder.model.addRow(orderRowData);
		}
		
		this.add(spTableOrder);
		/* table end */
	}
	
	
	/* method */
	@Override
	public void actionPerformed(ActionEvent e) {
		/*
	 	 * btns[0]:처음으로, btns[1]:돌아가기, btns[2]:승인요청
	 	 * lbls[0]:주문내역 문구, lbls[1]:주문수량 문구, lbls[2]:주문수량, lbls[3]:결제금액 문구, lbls[4]:결제금액
	 	 * lbls[5]:결제방법 이미지, lbls[6]:결제방법 문구, lbls[7]:타이머	
		 */		
		
		if(e.getSource() == btns[0]) {
			timer.cancel();
			Kiosk.insertPanel(new KioskPanelPage1());

		} else if(e.getSource() == btns[1]){
			timer.cancel();
			Kiosk.insertPanel(p2Pnl);
			
		} else if(e.getSource() == btns[2]) {
			if ((JOptionPane.showConfirmDialog(null, "결제하시겠습니까?", "결제", JOptionPane.YES_NO_OPTION) == 0)) {
				Kiosk.tempSalesDatas[0] = Kiosk.getMaxNum(Kiosk.filePathDBSales) + ""; 	//주문번호
				Kiosk.tempSalesDatas[3] = formatter.format(date); 						//결제일자 
				
				//dbSales.txt 저장
				for(int i=0; i<tblOrder.model.getRowCount(); i++) {
						String[] salesDatas = {
							Kiosk.tempSalesDatas[0],						//주문번호
							(tblOrder.model.getValueAt(i, 0)+"").trim(),	//제품명
							tblOrder.model.getValueAt(i, 1)+"",				//주문수량
							tblOrder.model.getValueAt(i, 2)+"",				//결제금액
							Kiosk.tempSalesDatas[2],						//결제방법
							Kiosk.tempSalesDatas[3],						//결제일자
							Kiosk.tempSalesDatas[1]							//매장여부
						};
						
						String rowData = String.join("#", salesDatas);
						new ConnectFile(Kiosk.filePathDBSales).saveRowData(rowData, true);
				}
				
				timer.cancel();
				Kiosk.insertPanel(new KioskPanelPage4());
				p2Pnl.selectedItem_removeAll();
			}
		}
	} //actionPerformed end
}
