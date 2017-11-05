import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class Main extends JPanel implements Runnable {

	boolean playing = false;
	float massToSet = 5;
	private final int FPS = 60;
	private ArrayList<Ball> balls = new ArrayList<>();
	static JPanel sidePanel;
	static SidePanel sidePanelInsides = new SidePanel();
	static LowerPanel lowerPanelInsides = new LowerPanel();
	boolean addingMass = false;
	boolean justChangedBalls = false;

	public static void main(String[] args) {

		SwingUtilities.invokeLater(new Runnable() {
			@Override

			public void run() {

				JFrame frame = new JFrame();
				frame.setTitle("Collisions");
				frame.setDefaultCloseOperation(3);
				Main gamePanel = new Main();
				gamePanel.setPreferredSize(new Dimension(700, 400));

				JPanel lowerPanel = lowerPanelInsides;

				lowerPanel.setPreferredSize(new Dimension(700, 200));

				JPanel megaPanel = new JPanel(new BorderLayout());
				JPanel megaMegaPanel = new JPanel(new BorderLayout());

				megaPanel.add(gamePanel, BorderLayout.CENTER);
				megaPanel.add(lowerPanel, BorderLayout.SOUTH);
				megaMegaPanel.add(megaPanel, BorderLayout.WEST);
				sidePanel = sidePanelInsides;

				sidePanel.setPreferredSize(new Dimension(200, 600));

				megaMegaPanel.add(sidePanel, BorderLayout.EAST);

				frame.add(megaMegaPanel);

				frame.pack();
				frame.setLocationRelativeTo(null);
				frame.setVisible(true);
				frame.setResizable(false);
			}
		});
	}

	@Override
	public void run() {
		int delta = (int) Math.ceil(1000. / FPS);

		Ball b1 = new Ball();
		b1.setMass(5);

		b1.setCenter(new Vector2d(350, 200));
		b1.setVelocity(new Vector2d(1, 0));
		balls.add(b1);

		Ball b2 = new Ball();
		b2.setMass(8);

		b2.setCenter(new Vector2d(200, 299));

		b2.setVelocity(new Vector2d(-2, 1));
		b2.initialVelocity = b2.getVelocity();
		b2.initialPosition = b2.getCenter();

		balls.add(b2);
		sidePanelInsides.updateItems(balls);

		while (isVisible()) {
			try {
				Thread.sleep(delta);

				if (playing == false) {
					for (int i = 0; i < balls.size(); i++) {
						balls.get(i).setColor(Ball.getColors(i));
					}

					if (justChangedBalls) {
						justChangedBalls = false;
						sidePanelInsides.updateItems(balls);
					} else {
						sidePanelInsides.updateEveryFrame(balls);
					}
				} else {
					sidePanelInsides.updateEveryFrame(balls);
					for (int i = 0; i < balls.size(); i++) {
						balls.get(i).update();
						balls.get(i).setColor(Ball.getColors(i));
						for (Ball ball : balls) {
							if (ball == balls.get(i)) {
								ball.update();
								if (balls.get(i).detectWallCollision()) {   
									balls.get(i).performWallCollision();
								}

							} else {

								ball.update();

								if (balls.get(i).detectCollision(ball)) {
									
									balls.get(i).performCollision(ball);
									

								} else {

								}
							}
						}

					}
				}
				repaint();
			} catch (InterruptedException e) {
			}

		}
	}

	public Main() {
		super();
		ListenForButton listener = new ListenForButton();
		lowerPanelInsides.play.addActionListener(listener);
		lowerPanelInsides.addMass.addActionListener(listener);
		lowerPanelInsides.reset.addActionListener(listener);

		MouseListener l = new MousePressListener();
		this.addMouseListener(l);

		setFocusable(true);
		requestFocusInWindow();

	}

	@Override
	public void addNotify() {
		super.addNotify();
		new Thread(this).start();
	}

	public void paintComponent(Graphics g1) {
		super.paintComponent(g1);

		Graphics2D g = (Graphics2D) g1;
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		// clearing screen
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, getWidth(), getHeight());

		// draws balls.
		for (Ball ball : balls) {
			ball.draw(g);
		}

	}

	public class ListenForButton implements ActionListener {
		// This is the action listener that listens for button presses

		public void actionPerformed(ActionEvent e) {

			if (e.getSource() == lowerPanelInsides.play) {
				if (playing == false) {
					lowerPanelInsides.play.setText("PAUSE");
					playing = true;

				} else {
					lowerPanelInsides.play.setText("START");
					playing = false;
				}

			} else if (e.getSource() == lowerPanelInsides.addMass && !playing) {

				try {
					int potentialMass = Integer.parseInt(lowerPanelInsides.massField.getText());
					if (potentialMass < 1 || potentialMass > 15) {
						JOptionPane.showMessageDialog(new JFrame(),
								"Please enter the mass as an integer between 1 and 15");
					} else {
						massToSet = potentialMass;
						addingMass = true;
					}
				} catch (Exception exception) {
					JOptionPane.showMessageDialog(new JFrame(), "Please enter the mass as an integer between 1 and 15");
				}

			} else if (e.getSource() == lowerPanelInsides.reset && !playing) {
				for (int i = (balls.size() - 1); i > 0; i--) {
					balls.remove(i);
					justChangedBalls = true;
				}
			}

		}

	}

	private class MousePressListener implements MouseListener {

		boolean touchingArrow = false;
		Ball arrowMove = new Ball();

		@Override
		public void mouseClicked(MouseEvent e) {
			if (!playing && addingMass) {
				int x = e.getX();
				int y = e.getY();

				if (0 < x && x < 700 && 0 < y && y < 400) {
					Ball potential = new Ball();

					potential.setMass(massToSet);
					potential.setCenter(new Vector2d(x, y));
					ArrayList<Ball> copyBalls = new ArrayList(balls);

					boolean collisions = false;
					for (Ball b : copyBalls) {
						if (potential.detectCollision(b)) {
							collisions = true;
						}

					}
					if (potential.detectWallCollision()) {
						collisions = true;
					}
					if (!collisions) {

						balls.add(potential);
						justChangedBalls = true;
						addingMass = false;
					}
				}
			} else if (!playing && addingMass == false) {
				if (e.getModifiers() == MouseEvent.BUTTON3_MASK) {
					if (balls.size() != 1) {
						int x = e.getX();
						int y = e.getY();

						ArrayList<Ball> copyBalls = new ArrayList(balls);
						boolean clickedBall = false;
						int index = 0;
						for (int i = 0; i < copyBalls.size(); i++) {
							if (copyBalls.get(i).isInBall(x, y)) {

								index = i;
								clickedBall = true;
							}
						}

						if (clickedBall) {
							balls.remove(index);
							justChangedBalls = true;
						}

					}
				}

			}
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
	}

}
