import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

public class KioskPanelAdmin1 extends JPanel implements ActionListener {
	/* variable */
	CButton[] btns;
	JLabel[] lbls;
	JPasswordField pfPasswd;
	String strPasswd = "";
	JLabel lblPwCheck;

	/* constructor */
	KioskPanelAdmin1() {
		this.setLayout(null);
		this.setBackground(Kiosk.bgAdmin);
		/* button */
		String[][] btnLayouts = {
				///btnText or imgPath, LocationX, LocationY, Width, Height
				{ "0",						"415", "420", "50", "50"}, 	// 0: 숫자 0
				{ "1",						"175", "360", "50", "50"}, 	// 1: 숫자 1
				{ "2",						"235", "360", "50", "50"}, 	// 2: 숫자 2
				{ "3",						"295", "360", "50", "50"}, 	// 3: 숫자 3
				{ "4",						"355", "360", "50", "50"}, 	// 4: 숫자 4
				{ "5",						"415", "360", "50", "50"}, 	// 5: 숫자 5
				{ "6",						"175", "420", "50", "50"}, 	// 6: 숫자 6
				{ "7",						"235", "420", "50", "50"}, 	// 7: 숫자 7
				{ "8",						"295", "420", "50", "50"}, 	// 8: 숫자 8
				{ "9",						"355", "420", "50", "50"}, 	// 9: 숫자 9
				{ "←",						"475", "360", "50", "50"}, 	// 10: 백스페이스
				{ "Clear",					"475", "420", "50", "50"}, 	// 11: Clear
				{ "./images/home(50).png",	"30", "30", "50", "50"}, 	// 12: 처음으로
				{ "Login",					"175", "500", "350", "45"}  // 13: 로그인
		};

		btns = new Create().createButtons(btnLayouts);

		
		for (int i = 0; i < btns.length; i++) {
			btns[i].addActionListener(this);
			
			//button option change
			switch (i) {
			case 0: case 1: case 2: case 3: case 4: case 5: 
			case 6:	case 7: case 8: case 9: case 10: case 11:
				btns[i].setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1)); 
				break;
			case 12:	
				btns[i].setBackground(null); 
				break;
			case 13:
				btns[i].setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1)); 
				btns[i].setFont(new Font("SansSerif", Font.BOLD, 18));
				break;
			}
			
			this.add(btns[i]);
		}
		/* button end */
		
		
		/* label */
		String[][] lblLayouts = {
				// lblText or imgPath, LocationX, LocationY, Width, Height
				{ "Admin Login", 		"175", "220", "350", "50" },	// 0: 타이틀
				{ "비밀번호가 틀렸습니다", 	"175", "315", "350", "50" } 	// 1: 비밀번호 오류
		};

		lbls = new Create().createLabels(lblLayouts);

		for (int i = 0; i < lbls.length; i++) {
			//label option change
			switch (i) {
			case 0:
				lbls[i].setFont(new Font("Serif", Font.PLAIN, 38));
				break;
			case 1:
				lbls[i].setFont(new Font("SansSerif", Font.PLAIN, 12));
				lbls[i].setForeground(Color.RED);
				lbls[i].setVisible(false);
				break;
			}
			
			this.add(lbls[i]);
		}
		/* label end */
		
		
		/* JPasswordField */
		pfPasswd = new JPasswordField();
		pfPasswd.setBounds(175, 285, 350, 45);
		pfPasswd.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
		this.add(pfPasswd);
		/* JPasswordField end */
	}

	/* method */
	@Override
	public void actionPerformed(ActionEvent e) {
		/*
		 * btns[0]:숫자0, btns[1]:숫자1, btns[2]:숫자2, btns[3]:숫자3, btns[4]:숫자4, btns[5]:숫자5
		 * btns[6]:숫자6, btns[7]:숫자7, btns[8]:숫자8, btns[9]:숫자9, btns[10]:백스페이스, btns[11]:Clear
		 * btns[12]:처음으로, btns[13]:로그인
		 * lbls[0]:타이틀, lbls[1]:비밀번호 오류
		 */
		
		lbls[1].setVisible(false);
		
		if (e.getSource() == btns[11]){ //clear
			pfPasswd.setText("");
			strPasswd = "";
		
		} else if (e.getSource() == btns[12]) { //처음으로
			Kiosk.insertPanel(new KioskPanelPage1());
			
		} else if (e.getSource() == btns[13]) { //로그인
			if (pfPasswd.getText().equals(Kiosk.passwd)) { Kiosk.insertPanel(new KioskPanelAdmin2()); } 
			else { lbls[1].setVisible(true); }
			
		} else {
			for (int i = 0; i < 9; i++) {
				if (e.getSource() == btns[i]) {
					strPasswd += i + "";
					break;
				}
			}
			
			if (e.getSource() == btns[0]) { //숫자 0
				strPasswd += 0 + "";
			} else if (e.getSource() == btns[10]) { //백스페이스
				try {
					strPasswd = strPasswd.substring(0, strPasswd.length() - 1);
				} catch (Exception e2) {
					// TODO: handle exception
				}
			}
			
			pfPasswd.setText(strPasswd);
		}
	} //actionPerformed end
}
