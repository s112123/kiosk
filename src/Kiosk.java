import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class Kiosk extends JFrame {
	/* variable */
	static JPanel pnlMain;
	static String filePathDBSales = "./database/dbSales.txt";
	static String filePathDBProduct = "./database/dbProducts.txt";
	static Color bgKiosk = new Color(0, 51, 102);
	static Color bgAdmin = new Color(254, 194, 204);
	static String passwd = "1234";
	static String[] categories = { "Main", "Drink", "Side" };
	static String[] tempSalesDatas = new String[4];	//{"주문번호", "매장여부", "결제방법", "결제일자"}

	/* constructor */
	Kiosk() {
		this.setSize(700, 900);
		this.setDefaultCloseOperation(3);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		
		pnlMain = new JPanel();
		pnlMain.setLayout(new BorderLayout());
		pnlMain.add(new KioskPanelPage1());
		
		this.add(pnlMain);
		this.setVisible(true);
	}
	
	/* method */
	static void insertPanel(JPanel pnl) {
		/* 메인패널 패널 부착 */
		
		Kiosk.pnlMain.removeAll();
		Kiosk.pnlMain.add(pnl);
		Kiosk.pnlMain.updateUI();
	} //insertPanel end
	
	static int getMaxNum(String dbFilePath) {
		/* 최대값 가져오기 */

		int maxNum = 0;
		int tmpNum = 0;

		ArrayList<String> rowDatas = new ConnectFile(dbFilePath).loadRowData();

		if (rowDatas.size() == 0) {
			return 1;
		} else {
			for (int i = 0; i < rowDatas.size(); i++) {
				tmpNum = Integer.parseInt(rowDatas.get(i).split("#")[0]);
			
				if (tmpNum >= maxNum) {
					maxNum = tmpNum + 1;
				}
			}
			
			return maxNum;
		}
	} //getMaxNumber end
}
