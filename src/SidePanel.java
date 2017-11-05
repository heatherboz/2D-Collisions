import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class SidePanel extends JPanel{
	String[] numbers = {"                                  "};
	public JComboBox dropdown = new JComboBox(numbers);
	public JLabel objectNumber = new JLabel();
	
	public JLabel massLabel = new JLabel("                                 ");
	public JLabel velocityXLabel = new JLabel("                                 ");
	public JLabel velocityYLabel = new JLabel("                                 ");
	public JLabel speedLabel = new JLabel("                                 ");
	public JLabel keLabel = new JLabel("                                 ");
	public JLabel momentumLabel = new JLabel("                                 ");
	
	public SidePanel(){
		super();
		this.setLayout(new GridBagLayout());
		
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		this.add(dropdown, c);
		
		c.gridx = 0;
		c.gridy = 1;
	
		this.add(objectNumber, c);
		
		c.gridx = 0;
		c.gridy = 2;
		massLabel.setHorizontalAlignment(SwingConstants.LEFT);
		this.add(massLabel, c);
		
		c.gridx = 0;
		c.gridy = 3;
		velocityXLabel.setHorizontalAlignment(SwingConstants.LEFT);
		this.add(velocityXLabel, c);
		
		c.gridx = 0;
		c.gridy = 4;
		velocityYLabel.setHorizontalAlignment(SwingConstants.LEFT);
		this.add(velocityYLabel, c);
		
		c.gridx = 0;
		c.gridy = 5;
		speedLabel.setHorizontalAlignment(SwingConstants.LEFT);
		this.add(speedLabel, c);
		
		c.gridx = 0;
		c.gridy = 6;
		keLabel.setHorizontalAlignment(SwingConstants.LEFT);
		this.add(keLabel, c);
		
		c.gridx = 0;
		c.gridy = 7;
		momentumLabel.setHorizontalAlignment(SwingConstants.LEFT);
		this.add(momentumLabel, c);
		
		
		dropdown.setSelectedIndex(0);
	}
	
	public SidePanel(ArrayList<Ball> balls){
		super();
		
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		this.add(dropdown, c);
		
		c.gridx = 0;
		c.gridy = 1;
		this.add(objectNumber, c);
		
		c.gridx = 0;
		c.gridy = 2;
		this.add(massLabel, c);
		
		c.gridx = 0;
		c.gridy = 3;
		this.add(velocityXLabel, c);
		
		c.gridx = 0;
		c.gridy = 4;
		this.add(velocityYLabel, c);
		
		c.gridx = 0;
		c.gridy = 5;
		this.add(speedLabel, c);
		
		c.gridx = 0;
		c.gridy = 6;
		this.add(keLabel, c);
		
		c.gridx = 0;
		c.gridy = 7;
		this.add(momentumLabel, c);
		
		
		dropdown.setSelectedIndex(0);
	}

	@SuppressWarnings("unchecked")
	public void updateItems(ArrayList<Ball> balls){
		@SuppressWarnings("rawtypes")
		DefaultComboBoxModel model = (DefaultComboBoxModel) dropdown.getModel();
		
		model.removeAllElements();
		
		String[] numbers = new String[balls.size()];
		for(int i = 0; i < numbers.length; i++){
			numbers[i] = "Ball " + (i + 1);
			model.addElement(numbers[i]);
		}
		dropdown.setModel(model);
		
		
		
	}
	
	
	public void updateEveryFrame(ArrayList<Ball> balls){
		Ball ball =  balls.get(dropdown.getSelectedIndex());
		

		
		massLabel.setText("Mass: " + String.format("%.2f", ball.getMass())+ " kg");
		velocityXLabel.setText("Velocity in X: " + String.format("%.2f",ball.getVelocity().getX()) + " m/s");
		velocityYLabel.setText("Velocity in Y: " + String.format("%.2f", ball.getVelocity().getY()) + " m/s");
		speedLabel.setText("Speed :" + ball.getVelocity().getLength() + " m/s");
		
		keLabel.setText("Kinetic Energy: " + String.format("%.2f", ball.getKineticEnergy()) + " J");
		
		momentumLabel.setText("Momentum: " + String.format("%.2f", ball.getMomentum()) + " kg * m/s");
	}
	
	
}
