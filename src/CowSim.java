/* CowSim v1.0.0
 * TODO Update when version is changed
 * */

/** Version numbers 
 * v0.0.1 Beta release
 * v1.0.0 Full release
 * */

/** Bugs 
 * v1.1.0 is coming, but the new feature, bills, isn't showing up! Try asking for the value of billTime and billTimestep at different points in the game.
 * */

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

public class CowSim {
	private long timeStep; // Milliseconds before updating
	
	private int money;
	private int cows;
	private int milk;
	
	private double milkProb; // Probability of a cow to create a gallon of milk per update
	
	private int billTimestep; // Updates before bill must be paid
	private int billTime; // Updates since bill was last paid
	
	public String ENversion = "CowSim v1.0.0"; // TODO Update when version is changed
	public String ENsellMilk = "How many gallons? (Type an integer, or 0 to cancel)\n Gallons are worth $3 each.";
	public String ENsellCows = "How many cows? (Type an integer, or 0 to cancel)\n Cows are worth $50 each.";
	public String ENbuyCows = "How many cows? (Type an integer, or 0 to cancel)\n Cows are worth $100 each.";
	public String ENbuyBills = "Pay bills? Costs $20. If bills aren't paid your cows won't have milk.";
	public CowSim() {
		money = 100;
		cows = 1;
		milk = 0;
		milkProb = 0.02;
		
		billTimestep = 60000;
		billTime = 0;
		
		timeStep = 100;
	}
	public int getMoney() {
		return money;
	}
	public int getCows() {
		return cows;
	}
	public int getMilk() {
		return milk;
	}
	public void sellMilk(int amount) {
		milk -= amount; // Decrease milk
		money += (amount * 3); // Increase money
	}
	public void sellCows(int amount) {
		cows -= amount; // Decrease cows
		money += (amount * 50); // Increase money
	}
	public void buyCows(int amount) {
		cows += amount; // Decrease cows
		money -= (amount * 100); // Increase money
	}
	public void update() {
		double seed = Math.random();
		double massNoMilkProb = Math.pow(1 - milkProb, cows); // Get probability of no milk from any cows
		double massMilk = seed - massNoMilkProb; // Get amount of milk from cows
		billTime++; // Update billTime
		if (massMilk > 0 && billTime < billTimestep) {
			milk += Math.ceil(massMilk * 5); // Add milk
		}
	}
	public void updateGraphics(CowSim simulator, JFrame ui) {
		// Import images
		String milkImgname = "carton.png";
		Icon milkImg;
		String cowImgname = "cow.png";
		Icon cowImg;
		String moneyImgname = "money.png";
		Icon moneyImg;
		try {
			milkImg = new ImageIcon(ImageIO.read(new CowSim().getClass().getResourceAsStream(milkImgname)));
		} catch (IOException e) {
			milkImg = null;
		}
		try {
			cowImg = new ImageIcon(ImageIO.read(new CowSim().getClass().getResourceAsStream(cowImgname)));
		} catch (IOException e) {
			cowImg = null;
		}
		try {
			moneyImg = new ImageIcon(ImageIO.read(new CowSim().getClass().getResourceAsStream(moneyImgname)));
		} catch (IOException e) {
			moneyImg = null;
		}
		// UI setup
		JPanel ui0 = new JPanel(); // Tracker panel
		JPanel ui1 = new JPanel(); // Control panel
		JLabel ui00 = new JLabel(); // Milk tracker
		ui00.setIcon(milkImg); // Add milk icon
		JLabel ui01 = new JLabel(); // Cow tracker
		ui01.setIcon(cowImg); // Add cow icon
		JLabel ui02 = new JLabel(); // Money tracker
		ui02.setIcon(moneyImg); // Add money icon
		ui0.add(ui00); // Add trackers to panel
		ui0.add(ui01);
		ui0.add(ui02);
		JButton ui10 = new JButton("Sell milk");
		ui10.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int amount = 0;
				try {
					amount = Integer.parseInt(JOptionPane.showInputDialog(ENsellMilk));
				} catch (Exception ex) {
				}
				if (simulator.getMilk() >= amount) {
					simulator.sellMilk(amount);
				}
			}
		});
		JButton ui11 = new JButton("Sell cows");
		ui11.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int amount = 0;
				try {
					amount = Integer.parseInt(JOptionPane.showInputDialog(ENsellCows));
				} catch (Exception ex) {
				}
				if (simulator.getCows() >= amount) {
					simulator.sellCows(amount);
				}
			}
		});
		JButton ui12 = new JButton("Buy cows");
		ui12.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int amount = 0;
				try {
					amount = Integer.parseInt(JOptionPane.showInputDialog(ENbuyCows));
				} catch (Exception ex) {
				}
				if (simulator.getMoney() >= (amount * 100)) {
					simulator.buyCows(amount);
				}
			}
		});
		
		JButton ui13 = new JButton("Pay bills");
		ui13.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					int yes = JOptionPane.showConfirmDialog(null, ENbuyBills);
					if (yes == 0 && simulator.getMoney() >= 20) {
						money -= 20;
						billTime = 0;
					}
				} catch (Exception ex) {
				}
			}
		});
		ui1.add(ui10); // Add buttons to panel
		ui1.add(ui11);
		ui1.add(ui12);
		ui1.add(ui13);
		ui.add(ui0, BorderLayout.NORTH); // Add tracker panel to frame
		ui.add(ui1, BorderLayout.CENTER); // Add control panel to frame
		// Frame setup
		ui.setTitle(ENversion);
		ui.setVisible(true);
		ui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		while (true) {
			simulator.update();
			ui00.setText("" + simulator.getMilk() + ""); // Print amount of milk
			ui01.setText("" + simulator.getCows() + ""); // Print amount of cows
			ui02.setText("" + simulator.getMoney() + ""); // Print amount of money
			
			ui13.setVisible(billTime >= billTimestep); // Show "Pay bills" button if it's time to pay bills
			
			ui.setMinimumSize(new Dimension(600, 600)); // Pack frame
			// Wait timeStep
			try {
				Thread.sleep(timeStep);
			} catch (Exception e) {
			}
		}
	}
	public static void main(String args[]) {
		CowSim player = new CowSim();
		JFrame frame = new JFrame();
		player.updateGraphics(player, frame); // Run update loop forever
	}
}