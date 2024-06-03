import javax.swing.JPanel;

public class Create {
   CButton[] btns;
   CLabel[] lbls;
   CTextField[] tfs;

   public CButton[] createButtons(String[][] btnLayouts) {
	   int arrSize = btnLayouts.length;
	   btns = new CButton[arrSize];
		  
	   if (btnLayouts[0].length == 7) {
		   for (int i = 0; i < arrSize; i++) {
			   btns[i] = new CButton(btnLayouts[i], btnLayouts[i][5], Integer.parseInt(btnLayouts[i][6]));
		   }
		
	   } else if (btnLayouts[0].length == 6) {
		   for (int i = 0; i < arrSize; i++) {
			   btns[i] = new CButton(btnLayouts[i], btnLayouts[i][5]);
		   }
		
	   } else {
		   for (int i = 0; i < arrSize; i++) {
			   btns[i] = new CButton(btnLayouts[i]);
		   }
	   }
		
	   return btns;
   }
   
   public CButton[] createIconButtons(String[][] btnLayouts, JPanel pnl) {
	   int arrSize = btnLayouts.length;
	   btns = new CButton[arrSize];
      
//	   for (int i = 0; i < btnLayouts[0].length; i++) {
//		   System.out.println(btnLayouts[0][i]);
//	   }
      
	   if (btnLayouts[0].length > 6) {
		   for (int i = 0; i < arrSize; i++) {
			   btns[i] = new CButton(btnLayouts[i], btnLayouts[i][5], Integer.parseInt(btnLayouts[i][6]));
		   }

	   } else {
		   for (int i = 0; i < arrSize; i++) {
			   btns[i] = new CButton(btnLayouts[i]);
		   }
	   }

	   return btns;
   }

   public CLabel[] createLabels(String[][] lblLayouts) {
	   int arrSize = lblLayouts.length;
	   lbls = new CLabel[arrSize];
		
	   for (int i = 0; i < arrSize; i++) {
		   lbls[i] = new CLabel(lblLayouts[i]);
	   }
		
	   return lbls;
   }

   public CTextField[] createTextFields(String[][] tfLayouts) {
	   int arrSize = tfLayouts.length;
	   tfs = new CTextField[arrSize];
	
	   for (int i = 0; i < arrSize; i++) {
		   tfs[i] = new CTextField(tfLayouts[i]);
	   }
	
	   return tfs;
   }
}