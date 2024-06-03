import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class SelectedItemPnl extends JPanel implements ActionListener {
	ImageIcon deleteImg, minusImg, plusImg;
	JButton btnDelete, btnMinus, btnPlus;
	JLabel lblProdName, lblProdCount, lblPrice;
	
	JPanel superPnl;
	KioskPanelPage2 superPagePnl;
	String name;
	int cnt, price, code;
	Color bgButton = new Color(255, 255, 255);
	Font fontLabel = new Font("SansSanif", Font.BOLD, 18);

	public SelectedItemPnl(JPanel pnl, String pName, int pPrice, int code, KioskPanelPage2 pnlPage2) {
		this.setLayout(null);
		this.setPreferredSize(new Dimension(620, 40));
		
		superPnl = pnl;
		superPagePnl = pnlPage2;
		
		name = pName;
		this.code = code;
		
		//Button: 삭제버튼
		deleteImg = new ImageIcon("./images/close(25).png");
		btnDelete = new JButton(deleteImg);
		btnDelete.setBounds(0, 0, 40, 40);
		btnDelete.setBackground(bgButton);
		btnDelete.setBorder(null);
		
		//Label: 제품명
		lblProdName = new JLabel(pName);
		lblProdName.setBounds(40, 0, 320, 40);
		lblProdName.setFont(fontLabel);
//		lblProdName.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
		
		//Button: 마이너스 버튼
		minusImg = new ImageIcon("./images/minus(25).png");
		btnMinus = new JButton(minusImg);
		btnMinus.setBounds(360, 0, 40, 40);
		btnMinus.setBackground(bgButton);
		btnMinus.setBorder(null);
		
		//Label: 수량
		lblProdCount = new JLabel();
		lblProdCount.setBounds(400, 0, 60, 40);
		lblProdCount.setFont(fontLabel);
//		lblProdCount.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
		lblProdCount.setHorizontalAlignment(JLabel.CENTER);
		
		//Button: 플러스 버튼
		plusImg = new ImageIcon("./images/plus(25).png");
		btnPlus = new JButton(plusImg);
		btnPlus.setBounds(460, 0, 40, 40);
		btnPlus.setBackground(bgButton);
		btnPlus.setBorder(null);
		
		//Label: 가격
		price = pPrice;
		lblPrice = new JLabel(pPrice+"원");
		lblPrice.setBounds(500, 0, 90, 40);
		lblPrice.setFont(fontLabel);
//		lblPrice.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
		lblPrice.setHorizontalAlignment(JLabel.RIGHT);
		
		this.setBackground(Color.WHITE);
		
		btnDelete.addActionListener(this);
		btnPlus.addActionListener(this);
		btnMinus.addActionListener(this);
		
		this.add(btnDelete);
		this.add(lblProdName);
		this.add(btnMinus);
		this.add(lblProdCount);
		this.add(btnPlus);
		this.add(lblPrice);
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==btnDelete) {
			KioskPanelPage2.selectedItemCnt--;
			superPnl.remove(this);
			superPagePnl.pnlSelectedItem[code]=null;
			superPnl.revalidate();
			superPnl.repaint();
			
		}else if(e.getSource()==btnPlus) {
			this.cnt++;
		}else if(e.getSource()==btnMinus) {
			if(cnt > 1) {
				//수량이 1일 때, 마이너스 버튼을 누르고 메뉴가 삭제되려면 조건은 cnt==1로 고치고 주석해제 
//				KioskPanelPage2.selectedItemCnt--;
//			    superPnl.remove(this);
//			    superPagePnl.pnlSelectedItem[code] = null;
//			    superPnl.revalidate();
//			    superPnl.repaint();
//			}else {
				this.cnt--;
			}
		}
		refresh();
	}
	
	void refresh() {
		lblProdCount.setText(cnt+"");
		lblPrice.setText((price*cnt)+"원");
		superPagePnl.selectedItem_SumPrice();
	}
	
}
