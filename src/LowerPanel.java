import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class LowerPanel extends JPanel {
	public JTextField massField = new JTextField();
	public JLabel numberLabel = new JLabel();
	public JButton addMass = new JButton();
	public JButton play = new JButton();
	public JButton reset = new JButton();
	String number;
	
	GridBagConstraints c = new GridBagConstraints();
	
	//CONSTRUCTS JPANEL!!!!
	public LowerPanel(){
			super();

			this.setLayout(new GridBagLayout());
			
			c.gridx = 0;
			c.gridy = 0; 
			number = "Object #1 mass:";
			numberLabel = new JLabel(number);
			numberLabel.setBackground(null);
			numberLabel.setFont(new Font("Sans Serif", Font.BOLD, 12));
			this.add(numberLabel, c);
			
			c.gridx = 1;
			c.gridy = 0;
			massField = new JTextField(10);
			this.add(massField, c);
			
			
			c.gridx = 2; 
			c.gridy = 0;
			addMass = new JButton("Add Mass");
			this.add(addMass,c);
			
			
			c.gridx = 3;
			c.gridy = 0;
			play = new JButton("START");
			this.add(play, c);	
			
			c.gridx = 4;
			c.gridy = 0;
			reset = new JButton("CLEAR");
			this.add(reset, c);
			
			

	}
	
	public void mass(int massNumber){
		number = "Object #" + massNumber + " mass:";
		numberLabel = new JLabel(number);
		
	}
		
		

			
}

	
