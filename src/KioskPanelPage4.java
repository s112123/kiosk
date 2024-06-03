
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Handler;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

//카드결제 마지막 페이지
public class KioskPanelPage4 extends JPanel implements ActionListener{
	CButton[] btns;
	CLabel[] lbls;
	
	Timer timer; 
	TimerTask task = new TimerTask() {
		int timeCount = 5; //5초
		
		@Override
	    public void run() {
			if(timeCount>=0) {
	    		timeCount--;
			} else {
				timer.cancel(); 
				Kiosk.insertPanel(new KioskPanelPage1()); 
			}
	    }
	};
	
	/* constructor */
	public KioskPanelPage4() {
		this.setLayout(null);
		this.setBackground(Color.WHITE);
		
		/* timer */
		timer = new Timer();
        timer.schedule(task, 1000, 1000);	//(1000 = 1초), 1초후에 실행해서 1초마다 반복
        /* timer end */
		
		/* button */
		String[][] btnLayouts = {
				///btnText or imgPath, LocationX, LocationY, Width, Height
				{"영수증 출력",	"210", "500", "300", "50"},	//0:영수증출력
				{"처음으로",	"210", "565", "300", "50"},	//1:처음으로
		};
		
		btns = new Create().createButtons(btnLayouts);
		
		for(int i=0; i<btns.length; i++) {
			//button option change
			switch(i) {
				case 0: 
					btns[i].setBorder(BorderFactory.createLineBorder(Kiosk.bgKiosk, 1));
					break;
				case 1:
					btns[i].setBackground(Kiosk.bgKiosk);
					btns[i].setForeground(Color.WHITE);
					break;
			}
			
			btns[i].addActionListener(this);
			this.add(btns[i]);
		}
		/* button end */
		
		
		/* label */
		String[][] lblLayouts = {
				// lblText or imgPath, LocationX, LocationY, Width, Height
				{ "./images/checked(50).png",	"325", "150", "50", "50" }, 	//0:완료 이미지
				{ "결제가 완료되었습니다",				"200", "200", "300", "50" }, 	//1:문구
				{ "주문번호",						"200", "300", "300", "50" }, 	//2:문구
				{ Kiosk.tempSalesDatas[0],		"200", "320", "300", "100" } 	//3:문구
		};
		
		lbls = new Create().createLabels(lblLayouts);
		
		for (int i = 0; i < lbls.length; i++) {
			lbls[i].setHorizontalAlignment(JLabel.CENTER);
			
			// label Option change
			switch (i) {
			case 1:
				lbls[i].setFont(new Font("SansSanif", Font.BOLD, 20));
				break;	
			case 2:
				lbls[i].setFont(new Font("SansSanif", Font.BOLD, 18));
				lbls[i].setForeground(Color.GRAY);
				break;
			case 3:
				lbls[i].setFont(new Font("SansSanif", Font.BOLD, 50));
				break;
			}
			
			this.add(lbls[i]);
		}		
		/* label end */
	}
	
	
	/* method */
	@Override
	public void actionPerformed(ActionEvent e) {
		/*
	 	 * btns[0]:영수증출력, btns[1]:처음으로
	 	 * lbls[0]:완료이미지, lbls[1]:결제완료 문구, lbls[2]:주문번호 문구, lbls[3]:주문번호
		 */		
	
		if(e.getSource() == btns[0]) {
			timer.cancel();
			// 여기 메서드 없어도 됨 (Kiosk.java에 있는 insertDialog 없앴어요)
			new KioskPanelPage4Dialog();
			
		} else if(e.getSource() == btns[1]) {
			timer.cancel();
			Kiosk.insertPanel(new KioskPanelPage1());
		}
	} //actionPerformed end
}
