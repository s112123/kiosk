import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class KioskPanelAdmin2 extends JPanel implements ActionListener {
	/* variable */
	CButton[] btns;
	
	/* constructor */
	KioskPanelAdmin2() {
		this.setLayout(null);
		this.setBackground(Kiosk.bgAdmin);
		
		/* button */
		String[][] btnLayouts = {
				///btnText or imgPath, LocationX, LocationY, Width, Height, image text
				{"./images/logout(50).png",		"600", "30",  "50", "50", ""},			//0: 로그아웃
				{"./images/productmanagement(100).png",	"120", "280", "220", "300", "제품 관리"},		//1: 제품관리
				{"./images/ordermanagement(100).png",	"360", "280", "220", "300", "주문 관리"}		//2: 주문관리
		};

		btns = new Create().createButtons(btnLayouts);
		
		for(int i=0; i<btns.length; i++) {
			btns[i].addActionListener(this);
			
			//button opation change
			switch(i) {
				case 0: 
					btns[i].setBackground(null); 
					break;
				case 1: case 2:
					btns[i].setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
					btns[i].lblName.setFont(new Font("SansSerif", Font.BOLD, 25));
//					btns[i].remove(btns[i].lblIcon);
//					btns[i].textPnl.setLayout(new BorderLayout());
//					btns[i].textPnl.add(btns[i].lblIcon,"East");
//					btns[i].textPnl.add(btns[i].lblName, "Center");
//					btns[i].textPnl.setPreferredSize(new Dimension(300,60));
//					btns[i].textPnl.setBorder(BorderFactory.createEmptyBorder(0,20,0,20));
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
		 * btns[0]:로그아웃, btns[1]:제품관리, btns[2]:주문관리
		 */
		
		if(e.getSource() == btns[0]) {
			if ((JOptionPane.showConfirmDialog(null, "로그아웃 하시겠습니까?", "로그아웃", JOptionPane.YES_NO_OPTION) == 0)) {
				Kiosk.insertPanel(new KioskPanelPage1());
			}
			
		} else if(e.getSource() == btns[1]) {
			Kiosk.insertPanel(new KioskPanelAdmin3());

		} else if(e.getSource() == btns[2]) {
			Kiosk.insertPanel(new KioskPanelAdmin4());
		}
	} //actionPerformed end
}
