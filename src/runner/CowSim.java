package runner;
/**
 * CowSim is a game that simulates a dairy farm.
 * 
 * @author WriterArtistCoder (Github user)
 * @version 1.2.1
 * @since 08/28/2018
*/

/** Ideas */

/** Bugs */

import java.awt.*;
import java.awt.datatransfer.*;
import java.awt.event.*;
import java.net.URI;

import javax.imageio.ImageIO;
import javax.swing.*;

public class CowSim {

	private int money;
	private int cows;
	private int milk;
	
	static String updateURL = "https://github.com/WriterArtistCoder/cow-simulator/releases/latest"; // The update page URL
	
	static long timeStep = 100; // Milliseconds before updating

	static double milkProb = 0.02;

	static int billTimestep = 600; // Updates before bill must be paid
	static int billTime = 0; // Updates since bill was last paid

	// English text for game
	static String ENversion = "CowSim v1.2.1"; // TODO Update when version is changed
	
	static String ENlaunchTypeDialog = "Do you want to create a new game (NEW) or import a game (OPEN)?";
	static String ENlaunchAddressDialog = "Type in your game address.";

	static String ENsavingGame0 = "Saving game...\n Your current game address is: ";
	static String ENsavingGame1 = "\n Next time you play, import a game and type in this address!\n This address is a representation of the current state of the game.\n Copy to clipboard?";
	
	static String ENsellMilkDialog = "Sell how many gallons? (Type an integer, or 0 to cancel)\n Gallons are worth $3 each.";

	static String ENsellCowsDialog = "Sell how many cows? (Type an integer, or 0 to cancel)\n Cows are worth $50 each.";

	static String ENbuyCowsDialog = "Buy how many cows? (Type an integer, or 0 to cancel)\n Cows are worth $100 each.";

	static String ENbuyBillsDialog = "Pay bills? Costs $20. If bills aren't paid your cows won't have milk.";

	/** 
	 * Creates a CowSim dairy farm instance with the starting supplies of money, milk, and cows.
	 */
	
	public CowSim() {
		money = 100;
		cows = 1;
		milk = 0;
	}
	
	/** 
	 * Creates a CowSim dairy farm instance from the specified amounts of money, milk, and cows.
	 * 
	 * @param moneya The amount of money for the simulator to have.
	 * @param cowsa The amount of cows for the simulator to have.
	 * @param milka The amount of milk for the simulator to have.
	 */
	
	public CowSim(int moneya, int cowsa, int milka) {
		money = moneya;
		cows = cowsa;
		milk = milka;
	}

	/**
	 * Gets current amount of money.
	 * 
	 * @returns The current amount of money.
	 */
	public int getMoney() {
		return money;
	}

	/**
	 * Gets current amount of cows.
	 * 
	 * @returns The current amount of cows.
	 */
	public int getCows() {
		return cows;
	}

	/**
	 * Gets current amount of milk.
	 * 
	 * @returns The current amount of milk.
	 */
	public int getMilk() {
		return milk;
	}

	/**
	 * Sells specified amount of milk.
	 * 
	 * @param amount The amount of milk to sell.
	 */
	public void sellMilk(int amount) {
		milk -= amount; // Decrease milk
		money += (amount * 3); // Increase money
	}

	/**
	 * Sells specified amount of cows.
	 * 
	 * @param amount The amount of cows to sell.
	 */
	public void sellCows(int amount) {
		cows -= amount; // Decrease cows
		money += (amount * 50); // Increase money
	}

	/**
	 * Purchases specified amount of cows.
	 * 
	 * @param amount The amount of cows to purchase.
	 */
	public void buyCows(int amount) {
		cows += amount; // Decrease cows
		money -= (amount * 100); // Increase money
	}

	/**
	 * Updates dairy farm.
	 */
	public void update() {
		double seed = Math.random();
		double massNoMilkProb = Math.pow(1 - milkProb, cows); // Get probability of no milk from any cows
		double massMilk = seed - massNoMilkProb; // Get amount of milk from cows
		billTime++; // Update billTime

		if (massMilk > 0 && billTime < billTimestep) {
			milk += Math.ceil(massMilk * 5); // Add milk
		}
	}

	/**
	 * Starts updating game until it is quit.
	 * 
	 * @param simulator The simulator to update
	 * @param ui        The JFrame to update
	 */
	public void updateGraphics(JFrame ui) {
		// Import images
		String milkImgname = "milk.png";
		Icon milkImg;
		String milkSellImgname = "sell-milk.png";
		Icon milkSellImg;
		
		String cowImgname = "cow.png";
		Icon cowImg;
		String cowSellImgname = "cow-sell.png";
		Icon cowSellImg;
		String cowBuyImgname = "cow-buy.png";
		Icon cowBuyImg;
		
		String moneyImgname = "money.png";
		Icon moneyImg;
		
		String billBuyImgname = "bill-buy.png";
		Icon billBuyImg;
		
		
		String saveImgname = "export.png";
		Icon saveImg;
		
		try {
			milkImg = new ImageIcon(ImageIO.read(new CowSim().getClass().getResourceAsStream(milkImgname)));
		} catch (Exception e) {
			milkImg = null;
		}
		
		try {
			milkSellImg = new ImageIcon(ImageIO.read(new CowSim().getClass().getResourceAsStream(milkSellImgname)));
		} catch (Exception e) {
			milkSellImg = null;
		}

		try {
			cowImg = new ImageIcon(ImageIO.read(new CowSim().getClass().getResourceAsStream(cowImgname)));
		} catch (Exception e) {
			cowImg = null;
		}
		
		try {
			cowSellImg = new ImageIcon(ImageIO.read(new CowSim().getClass().getResourceAsStream(cowSellImgname)));
		} catch (Exception e) {
			cowSellImg = null;
		}
		
		try {
			cowBuyImg = new ImageIcon(ImageIO.read(new CowSim().getClass().getResourceAsStream(cowBuyImgname)));
		} catch (Exception e) {
			cowBuyImg = null;
		}

		try {
			moneyImg = new ImageIcon(ImageIO.read(new CowSim().getClass().getResourceAsStream(moneyImgname)));
		} catch (Exception e) {
			moneyImg = null;
		}
		
		try {
			billBuyImg = new ImageIcon(ImageIO.read(new CowSim().getClass().getResourceAsStream(billBuyImgname)));
		} catch (Exception e) {
			billBuyImg = null;
		}
		
		try {
			saveImg = new ImageIcon(ImageIO.read(new CowSim().getClass().getResourceAsStream(saveImgname)));
		} catch (Exception e) {
			saveImg = null;
		}
		
		// UI setup
		JPanel ui0 = new JPanel(); // Tracker panel
		JPanel ui1 = new JPanel(); // Control panel
		
		JLabel ui00 = new JLabel(); // Milk tracker
		ui00.setIcon(milkImg);
		JLabel ui01 = new JLabel(); // Cow tracker
		ui01.setIcon(cowImg);
		JLabel ui02 = new JLabel(); // Money tracker
		ui02.setIcon(moneyImg);
		
		JButton ui10 = new JButton(milkSellImg);
		ui10.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int amount = 0;
				try {
					amount = Integer.parseInt(JOptionPane.showInputDialog(ENsellMilkDialog));
				} catch (Exception ex) {
					
				}
				if (getMilk() >= amount) {
					sellMilk(amount);
				}
			}
		});
		
		JButton ui11 = new JButton(cowSellImg);
		ui11.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int amount = 0;
				try {
					amount = Integer.parseInt(JOptionPane.showInputDialog(ENsellCowsDialog));
				} catch (Exception ex) {

				}
				if (getCows() >= amount) {
					sellCows(amount);
				}
			}
		});
		
		JButton ui12 = new JButton(cowBuyImg);
		ui12.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int amount = 0;
				try {
					amount = Integer.parseInt(JOptionPane.showInputDialog(ENbuyCowsDialog));
				} catch (Exception ex) {
					
				}
				if (getMoney() >= (amount * 100)) {
					buyCows(amount);
				}
			}
		});

		JButton ui13 = new JButton(billBuyImg);
		ui13.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					int yes = JOptionPane.showConfirmDialog(null, ENbuyBillsDialog, null, JOptionPane.YES_NO_OPTION);
					if (yes == 0 && getMoney() >= 20) {
						money -= 20;
						billTime = 0;
					}
				} catch (Exception ex) {

				}
			}
		});
		
		JButton ui14 = new JButton(saveImg); // Money tracker
		ui14.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				printAddress();
			}
		});
		
		// Add components to panels
		ui0.add(ui00);
		ui0.add(ui01);
		ui0.add(ui02);
		
		ui1.add(ui10);
		ui1.add(ui11);
		ui1.add(ui12);
		ui1.add(ui13);
		ui1.add(ui14);
		// Add panels to frames
		ui.add(ui0, BorderLayout.NORTH);
		ui.add(ui1, BorderLayout.CENTER);
		// Frame setup
		ui.setTitle(ENversion);
		ui.setVisible(true);
		ui.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		// Show dialog when closing
		ui.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent we) { 
			    if (JOptionPane.showConfirmDialog(null, "Are you sure you want to close the game?", null, JOptionPane.YES_NO_OPTION) == 0) {
			    	if (JOptionPane.showConfirmDialog(null, "Save before closing?", null, JOptionPane.YES_NO_OPTION) == 0) {
			    		printAddress();
				    }
			    	System.exit(0);
			    }
			}
		});
		
		while (true) {
			update();
			ui00.setText("" + getMilk() + ""); // Print amount of milk
			ui01.setText("" + getCows() + ""); // Print amount of cows
			ui02.setText("" + getMoney() + ""); // Print amount of money

			ui13.setVisible(billTime >= billTimestep); // Show "Pay bills" button if it's time to pay bills

			ui.setMinimumSize(new Dimension(600, 600)); // Pack frame
			// Wait timeStep
			try {
				Thread.sleep(timeStep);
			} catch (Exception e) {

			}
		}
	}

	/**
	 * Launches the game.
	 */
	public void launch() {
		JFrame frame = new JFrame();
		updateGraphics(frame);
	}
	
	/**
	 * Gets the current state of the game as a String called the game address.
	 * 
	 * @return The game address.
	 */
	public String getAddress() {
		return getMoney()*69 + "." + getCows()*57 + "." + getMilk()*60;
	}
	

	/**
	 * Prints the current state of the game as a String called the game address.
	 */
	public void printAddress() {
		StringSelection stringSelection = new StringSelection(getAddress());
		if (JOptionPane.showConfirmDialog(null, ENsavingGame0 + getAddress() + ENsavingGame1, null, JOptionPane.YES_NO_OPTION) == 0) {
			Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
			clipboard.setContents(stringSelection, null);
		}
	}
	
	/**
	 * Returns a new game created from a game address.
	 * 
	 * @param address The game address to create the game from.
	 * @return The game created from the address.
	 */
	public static CowSim readAddress(String address) {
		int dots = 0;
		String moneya = "";
		String cowsa = "";
		String milka = "";
		
		try {
			for (int i = 0; i < address.length(); i++) {
				if (address.charAt(i) == '.') {
					dots++;
				} else if (dots == 0) {
					moneya += "" + address.charAt(i);
				} else if (dots == 1) {
					cowsa += "" + address.charAt(i);
				} else if (dots == 2) {
					milka += "" + address.charAt(i);
				}
			}
			
			return new CowSim(Integer.parseInt(moneya)/69, Integer.parseInt(cowsa)/57, Integer.parseInt(milka)/60);
		} catch (Exception e) {
			return new CowSim();
		}
	}
	
	/**
	 * Launches a new game created from a game address.
	 * 
	 * @param address The game address to create the game from.
	 */
	public static void readAddressAndLaunch(String address) {
		int dots = 0;
		String moneya = "";
		String cowsa = "";
		String milka = "";
		
		try {
			for (int i = 0; i < address.length(); i++) {
				if (address.charAt(i) == '.') {
					dots++;
				} else if (dots == 0) {
					moneya += "" + address.charAt(i);
				} else if (dots == 1) {
					cowsa += "" + address.charAt(i);
				} else if (dots == 2) {
					milka += "" + address.charAt(i);
				}
			}
			
			new CowSim(Integer.parseInt(moneya)/69, Integer.parseInt(cowsa)/57, Integer.parseInt(milka)/60).launch();
		} catch (Exception e) {
			new CowSim().launch();
		}
	}
	
	/**
	 * Launches a game, either from a game address or from a new game.
	 */
	public static void getInputAndLaunch() {
		try {
			String launchType = JOptionPane.showInputDialog(ENlaunchTypeDialog);
			if (launchType.equalsIgnoreCase("NEW")) {
				new CowSim().launch();
			} else if (launchType.equalsIgnoreCase("OPEN")) {
				readAddress(JOptionPane.showInputDialog(ENlaunchAddressDialog)).launch();
			} else {
				getInputAndLaunch();
			}
		} catch (Exception e) {
			System.exit(0);
		}
	}
	
	public static void main(String[] args) {
		if (JOptionPane.showConfirmDialog(null, "Visit update page? (Your current version is " + ENversion + ")", null, JOptionPane.YES_NO_OPTION) == 0) {
			try {
		          URI uri = new URI(updateURL);
		          java.awt.Desktop.getDesktop().browse(uri);
		     } catch (Exception e) {
		          
		     }
		}
		
		getInputAndLaunch();
	}

}
