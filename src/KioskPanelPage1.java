import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

public class KioskPanelPage1 extends JPanel implements ActionListener {
	/* variable */
	CButton[] btns;
	
	/* constructor */
	KioskPanelPage1() {
		this.setLayout(null);
		this.setBackground(Kiosk.bgKiosk);
		
		/* button */
		String[][] btnLayouts = {
				//btnText or imgPath, LocationX, LocationY, Width, Height, image text1
				{"./images/admin(50).png",		"600", "30", "50", "50", null},				//0: 관리자 
				{"./images/takeout(100).png",	"120", "280", "220", "300", "Take Out"},	//1: 포장 
				{"./images/shop(100).png",		"360", "280", "220", "300", "Store"}		//2: 매장 
		};
		
		btns = new Create().createButtons(btnLayouts);
		
		//button options
		for(int i=0; i<btns.length; i++) {
			btns[i].addActionListener(this);
			
			switch(i) {
				case 0: 
					btns[i].setBackground(null);
					break;
				case 1: case 2: 
					btns[i].lblName.setFont(new Font("SansSerif", Font.BOLD, 25));
					break;
			}	
			
			this.add(btns[i]);
		}
		/* button end */
	}
	
	/* method */
	@Override
	public void actionPerformed(ActionEvent e) {
		/*
		 * btns[0]:관리자, btns[1]:포장, btns[2]:매장
		 */
		
		if(e.getSource() == btns[0]) {	
			Kiosk.insertPanel(new KioskPanelAdmin1());

		} else if(e.getSource() == btns[1]) {
			Kiosk.tempSalesDatas[1] = "Take Out"; //매장여부
			Kiosk.insertPanel(new KioskPanelPage2());
		
		} else if(e.getSource() == btns[2]) {
			Kiosk.tempSalesDatas[1] = "Store";	//매장여부
			Kiosk.insertPanel(new KioskPanelPage2());
		}
	} 
}
