import java.awt.Color;
import javax.swing.JTextField;

public class CTextField extends JTextField {
	/* variable */
	CTextField[] tfs;
	
	public CTextField(String[] layout) {

		setText(layout[0]);
		setBounds(Integer.parseInt(layout[1]), Integer.parseInt(layout[2]), Integer.parseInt(layout[3]),
				Integer.parseInt(layout[4]));
//		setBackground(Color.WHITE);
		setBorder(null);
	}
}
