import java.awt.Color;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class CLabel extends JLabel {
	/* variable */
	CLabel[] lbls;
	
	public CLabel(String[] layout) {
		if (layout[0].contains(".")) {
			ImageIcon lblImg = new ImageIcon(layout[0]);
			setIcon(lblImg);
		} else {
			setText(layout[0]);
		}

		setBounds(
				Integer.parseInt(layout[1]), 
				Integer.parseInt(layout[2]), 
				Integer.parseInt(layout[3]),
				Integer.parseInt(layout[4]));
		
//		setOpaque(true);
		setBackground(null);
		setBorder(null);
	}
}

