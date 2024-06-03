import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;

public class KioskPanelPage4Dialog extends JDialog implements ActionListener{
	JLabel lblCont, lblHead; 
	JButton btnOk, btnCanc; 
	
	Timer timer; 
	TimerTask task = new TimerTask() {
		int timeCount = 5; //5초
		
		@Override
	    public void run() {
			if(timeCount>=0) {
	    		timeCount--;
			} else {
				timer.cancel(); 
				//다이얼로그 끄기 추가해야 됨
				Kiosk.insertPanel(new KioskPanelPage1()); 
			}
	    }
	};
	
								// 다이어로그 영수증 출력 
	public KioskPanelPage4Dialog() { 
		this.setLayout(null);
		this.setSize(400, 300);
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.setLocationRelativeTo(null);

		/* timer */
		timer = new Timer();
        timer.schedule(task, 1000, 1000);	//(1000 = 1초), 1초후에 실행해서 1초마다 반복
        /* timer end */
		
		/* Label1 */
		lblCont = new JLabel("영수증을 출력 하시겠습니까?");
		lblCont.setBounds(60, 60, 280, 100);
		lblCont.setFont(new Font("", Font.BOLD, 20));
		/* Label1 end*/
		
		
		/* Button1 */
		btnOk = new JButton("네");	
		btnOk.setBounds(90, 200, 90, 30);
		btnOk.addActionListener(this);
		/* Button1 end*/
		
		
		/* Button2 */
		btnCanc = new JButton("아니오");
		btnCanc.setBounds(200, 200, 90, 30);
		btnCanc.addActionListener(this);
		/* Button2 end*/
		
		this.add(lblCont);
		this.add(btnOk);
		this.add(btnCanc);
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == btnOk || e.getSource() == btnCanc) {
			this.setVisible(false);
			this.dispose();
			Kiosk.insertPanel(new KioskPanelPage1());
		}
		
		Kiosk.pnlMain.updateUI();
	}
}
