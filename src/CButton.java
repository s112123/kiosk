
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class CButton extends JButton{

   String pName ="";
   int pPrice = 0;
   JPanel textPnl;
   JLabel lblName, lblPrice, lblIcon;
   
   public CButton(String[] layout) {
      setMethod(layout);
   }

   public CButton(String[] layout, String name, int price) {
      setMethod(layout);
      pName = name;
      pPrice = price;
      textPnl = new JPanel();
      textPnl.setLayout(new GridLayout(2,1));
      lblName = new JLabel(name, JLabel.CENTER);
      lblPrice = new JLabel(price+"", JLabel.CENTER);
      
      this.add(textPnl,"South");
      textPnl.add(lblName);
      textPnl.add(lblPrice);
      textPnl.setBackground(Color.WHITE);
      
   }
   
   public CButton(String[] layout, String name) {
      setMethod(layout);
      pName = name;
      textPnl = new JPanel();
      textPnl.setLayout(new GridLayout(2,1));
      lblName = new JLabel(name, JLabel.CENTER);
      this.add(textPnl,"South");
      textPnl.add(lblName);
      textPnl.setBackground(new Color(0, 0, 0, 0));
   
   }

   void setMethod(String[] layout){
      if (layout[0].contains(".")) {
         ImageIcon btnImg = new ImageIcon(layout[0]);
         lblIcon = new JLabel(btnImg,JLabel.CENTER);
         this.setLayout(new BorderLayout());
         this.add(lblIcon);
      } else {
         setText(layout[0]);
      }

      setBounds(
    		Integer.parseInt(layout[1]), 
    		Integer.parseInt(layout[2]), 
    		Integer.parseInt(layout[3]),
            Integer.parseInt(layout[4]));
      
      setFont(new Font("SansSanif", Font.BOLD, 16));
      setBackground(Color.WHITE);
      setBorder(null);
   }
}