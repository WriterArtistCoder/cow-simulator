package runner;
/**
 * CowSim is a game that simulates a dairy farm.
 * 
 * @author WriterArtistCoder (Github user)
 * @version 2.1.0
*/

/** Ideas 
 * Use MouseInfo.getPointerInfo().getLocation() to get mouse X and Y position
*/

/** Bugs */

import java.awt.*;
import java.awt.datatransfer.*;
import java.awt.event.*;

import javax.imageio.ImageIO;
import javax.swing.*;

public class CowSim {

	public Timer frameRate;

	private int farmPoints;
	private int money;
	private int cows;
	private int milk;

	static String updateURL = "https://github.com/WriterArtistCoder/cow-simulator/releases/latest"; // The update page
																									// URL

	static long timeStep = 100; // Milliseconds before updating program

	static double milkProb = 0.02;

	public final static int billTimestep = 1200; // Updates before bill must be paid
	int billTime = 0; // Updates since bill was last paid
	
	public static final int moneyPerSellMilk = 3;
	public static final int moneyPerSellCows = 50;
	public static final int moneyPerBuyCow = 100;
	
	public static final int moneyPerBuyBill = 20;
	public static final int moneyPerBuyFarmer = 50;

	public static final int pointsPerLevel = 250; // Points per level

	public static final int pointsPerMilk = 2;
	public static final int pointsPerMilkSell = 5;
	public static final int pointsPerCowBuy = 20;

	public Font trackerFont;

	public boolean gameWon = false; // Has "You won" pop-up shown yet
	public boolean farmersHired = false; // Have farmers been hired

	// English text for game
	static String ENversion = "CowSim v2.0.0"; // TODO Update when version is changed

	static String ENlaunchTypeDialog = "Do you want to create a new game (NEW) or import a game (OPEN)?";
	static String ENlaunchAddressDialog = "Type in your game address.";

	static String ENsavingGame0 = "Saving game...\n Your current game address is: ";
	static String ENsavingGame1 = "\n Next time you play, import a game and type in this address!\n This address is a representation of the current state of the game.\n Copy to clipboard?";

	static String ENsellMilkDialog = "Sell how many gallons? (Type an integer, or 0 to cancel)\n Gallons are worth $" + moneyPerSellMilk + " each.";
	static String ENsellCowsDialog = "Sell how many cows? (Type an integer, or 0 to cancel)\n Cows are worth $" + moneyPerSellCows + " each.";
	static String ENbuyCowsDialog = "Buy how many cows? (Type an integer, or 0 to cancel)\n Cows are worth $" + moneyPerBuyCow + " each.";
	
	static String ENbuyFarmersDialog = "Do you wish to hire farmers to automate processes such as:\n Selling milk\n Paying bills\n Costs $" + moneyPerBuyFarmer + ".";
	static String ENbuyBillsDialog = "Pay bills? Costs $" + moneyPerBuyBill + ". If bills aren't paid your cows won't have milk.";
	static String ENbrokeDialog = "You are broke and cannot pay your bills!\n If you have some cows, sell them.";
	
	// One-time dialogs
	static String ENunlockedBuyFarmersDialog = "You've unlocked farmers for hire!";
	public boolean unlockedBuyFarmersDialogShown = false;

	// Tooltip text for splash and game
	static String ENgameNew = "New game";
	static String ENgameImport = "Import game";
	static String ENreleasesSee = "Check for updates / See CowSim's Github page";

	static String ENmilk = "Milk";
	static String ENmilkSell = "Sell milk";
	static String ENmilkSellAll = "Sell all milk";

	static String ENcow = "Cows";
	static String ENcowSell = "Sell cows";
	static String ENcowBuy = "Buy cows";

	static String ENmoney = "Money";

	static String ENfarmpoints = "Farm points to next farm level";
	static String ENfarmlevel = "Farm level (" + pointsPerLevel + " farm points)";

	static String ENbillBuy = "Pay bills";
	
	static String ENfarmerBuy = "Hire farmers";

	static String ENsave = "Save and quit to title";

	// Image variables for splash and game
	static String logoImgname = "CowSimLogo_v2.0.0.png";
	Icon logoImg;

	static String gameNewImgname = "game-new.png";
	Icon gameNewImg;
	static String gameImportImgname = "game-import.png";
	Icon gameImportImg;
	static String releasesSeeImgname = "releases-see.png";
	Icon releasesSeeImg;

	static String milkImgname = "milk.png";
	Icon milkImg;
	static String milkSellImgname = "sell-milk.png";
	Icon milkSellImg;
	static String milkSellAllImgname = "sell-milk-all.png";
	Icon milkSellAllImg;

	static String cowImgname = "cow.png";
	Icon cowImg;
	static String cowSellImgname = "cow-sell.png";
	Icon cowSellImg;
	static String cowBuyImgname = "cow-buy.png";
	Icon cowBuyImg;

	static String moneyImgname = "money.png";
	Icon moneyImg;

	static String farmpointsImgname = "farmpoints.png";
	Icon farmpointsImg;

	static String farmlevelImgname = "farmlevel.png";
	Icon farmlevelImg;

	static String billBuyImgname = "bill-buy.png";
	Icon billBuyImg;
	
	static String farmerBuyImgname = "farmer-buy.png";
	Icon farmerBuyImg;

	static String saveImgname = "export.png";
	Icon saveImg;

	/**
	 * Creates a CowSim dairy farm instance with the starting supplies of money,
	 * milk, and cows.
	 */

	public CowSim() {
		farmPoints = 0;

		money = 100;
		cows = 1;
		milk = 0;

		trackerFont = new Font("Monospace", Font.PLAIN, 20);
	}

	/**
	 * Creates a CowSim dairy farm instance from the specified amounts of money,
	 * milk, and cows.
	 * 
	 * @param farmPointsa The amount of farm points for the simulator to have
	 * @param milka       The amount of milk for the simulator to have
	 * @param cowsa       The amount of cows for the simulator to have
	 * @param moneya      The amount of money for the simulator to have
	 * @param youWona     Whether the "You won" pop-up has shown
	 */

	public CowSim(int moneya, int cowsa, int milka, int farmPointsa, boolean gameWona, boolean farmersHireda) {
		farmPoints = farmPointsa;
		money = moneya;
		cows = cowsa;
		milk = milka;
		gameWon = gameWona;
		farmersHired = farmersHireda;

		trackerFont = new Font("Monospace", Font.PLAIN, 20);
	}

	/**
	 * Gets current amount of milk.
	 * 
	 * @returns The current amount of milk
	 */
	public int getMilk() {
		return milk;
	}

	/**
	 * Gets current amount of cows.
	 * 
	 * @returns The current amount of cows
	 */
	public int getCows() {
		return cows;
	}

	/**
	 * Gets current amount of money.
	 * 
	 * @returns The current amount of money
	 */
	public int getMoney() {
		return money;
	}

	/**
	 * Gets current amount of farm points.
	 * 
	 * @returns The current amount of farm points
	 */
	public int getFarmPoints() {
		return farmPoints;
	}
	
	/**
	 * Gets current farm level.
	 * 
	 * @returns The current farm level
	 */
	public int getFarmLevel() {
		String level = ("" + (Math.floor(getFarmPoints() / pointsPerLevel) + 1));
		level = level.substring(0, level.length() - 2);
		
		return Integer.parseInt(level);
	}

	/**
	 * Sells specified amount of milk.
	 * 
	 * @param amount The amount of milk to sell
	 */
	public void sellMilk(int amount) {
		milk -= amount; // Decrease milk
		money += moneyPerSellMilk * amount; // Increase money
		farmPoints += pointsPerMilkSell * amount;
	}

	/**
	 * Sells specified amount of cows.
	 * 
	 * @param amount The amount of cows to sell
	 */
	public void sellCows(int amount) {
		cows -= amount; // Decrease cows
		money += moneyPerSellCows * amount; // Increase money
	}

	/**
	 * Purchases specified amount of cows.
	 * 
	 * @param amount The amount of cows to purchase
	 */
	public void buyCows(int amount) {
		cows += amount; // Decrease cows
		money -= moneyPerBuyCow * amount; // Increase money
		farmPoints += pointsPerCowBuy * amount;
	}
	
	/**
	 * Pays bills.
	 */
	
	public void buyBill() {
		if (getMoney() >= moneyPerBuyBill) {
			money -= moneyPerBuyBill;
			billTime = 0;
		}
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
			if (farmersHired) {
				money += Math.ceil(massMilk * 5)*moneyPerSellMilk;
			} else {
				milk += Math.ceil(massMilk * 5); // Add milk
			}
			farmPoints += pointsPerMilk;
		}

		if (cows >= 100 && !gameWon) {
			JOptionPane.showMessageDialog(null, "You won! Continue playing or quit.");
			gameWon = true;
		}
	}

	/**
	 * Imports images for game.
	 */

	public void setupImages() {
		try {
			logoImg = new ImageIcon(ImageIO.read(new CowSim().getClass().getResourceAsStream(logoImgname)));
			gameNewImg = new ImageIcon(ImageIO.read(new CowSim().getClass().getResourceAsStream(gameNewImgname)));
			gameImportImg = new ImageIcon(ImageIO.read(new CowSim().getClass().getResourceAsStream(gameImportImgname)));
			releasesSeeImg = new ImageIcon(
					ImageIO.read(new CowSim().getClass().getResourceAsStream(releasesSeeImgname)));

			milkImg = new ImageIcon(ImageIO.read(new CowSim().getClass().getResourceAsStream(milkImgname)));
			milkSellImg = new ImageIcon(ImageIO.read(new CowSim().getClass().getResourceAsStream(milkSellImgname)));
			milkSellAllImg = new ImageIcon(
					ImageIO.read(new CowSim().getClass().getResourceAsStream(milkSellAllImgname)));
			cowImg = new ImageIcon(ImageIO.read(new CowSim().getClass().getResourceAsStream(cowImgname)));
			cowSellImg = new ImageIcon(ImageIO.read(new CowSim().getClass().getResourceAsStream(cowSellImgname)));
			cowBuyImg = new ImageIcon(ImageIO.read(new CowSim().getClass().getResourceAsStream(cowBuyImgname)));
			moneyImg = new ImageIcon(ImageIO.read(new CowSim().getClass().getResourceAsStream(moneyImgname)));
			farmpointsImg = new ImageIcon(ImageIO.read(new CowSim().getClass().getResourceAsStream(farmpointsImgname)));
			farmlevelImg = new ImageIcon(ImageIO.read(new CowSim().getClass().getResourceAsStream(farmlevelImgname)));

			billBuyImg = new ImageIcon(ImageIO.read(new CowSim().getClass().getResourceAsStream(billBuyImgname)));
			farmerBuyImg = new ImageIcon(ImageIO.read(new CowSim().getClass().getResourceAsStream(farmerBuyImgname)));
			saveImg = new ImageIcon(ImageIO.read(new CowSim().getClass().getResourceAsStream(saveImgname)));
		} catch (Exception e) {

		}
	}

	/**
	 * Sets up GUI for specified GameWindow.
	 * 
	 * @param simulator The simulator to update
	 * @param ui        The GameWindow to update
	 */
	public void setupGUI(CowSim simulator, GameWindow ui) {
		setupImages();

		// UI setup (WARNING: Remove all panels when the ENsave button is pressed)
		JPanel ui0 = new JPanel(); // Tracker panel
		JPanel ui1 = new JPanel(); // Control panel

		JLabel ui00 = new JLabel(); // Milk tracker
		ui00.setIcon(milkImg);
		ui00.setFont(trackerFont);
		ui00.setToolTipText(ENmilk);

		JLabel ui01 = new JLabel(); // Cow tracker
		ui01.setIcon(cowImg);
		ui01.setFont(trackerFont);
		ui01.setToolTipText(ENcow);

		JLabel ui02 = new JLabel(); // Money tracker
		ui02.setIcon(moneyImg);
		ui02.setFont(trackerFont);
		ui02.setToolTipText(ENmoney);

		JLabel ui03 = new JLabel(); // Farm level tracker
		ui03.setIcon(farmlevelImg);
		ui03.setFont(trackerFont);
		ui03.setToolTipText(ENfarmlevel);

		JLabel ui04 = new JLabel(); // Farm points tracker
		ui04.setIcon(farmpointsImg);
		ui04.setFont(trackerFont);
		ui04.setToolTipText(ENfarmpoints);

		JButton ui10 = new JButton(milkSellImg);
		ui10.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int amount = 0;
				try {
					amount = Integer.parseInt(JOptionPane.showInputDialog(ENsellMilkDialog));
				} catch (Exception ex) {

				}
				if (simulator.getMilk() >= amount) {
					simulator.sellMilk(amount);
				}
			}
		});
		ui10.setToolTipText(ENmilkSell);

		JButton ui11 = new JButton(milkSellAllImg);
		ui11.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				simulator.sellMilk(simulator.getMilk());
			}
		});
		ui11.setToolTipText(ENmilkSellAll);

		JButton ui12 = new JButton(cowSellImg);
		ui12.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int amount = 0;
				try {
					amount = Integer.parseInt(JOptionPane.showInputDialog(ENsellCowsDialog));
				} catch (Exception ex) {

				}
				if (simulator.getCows() >= amount) {
					simulator.sellCows(amount);
				}
			}
		});
		ui12.setToolTipText(ENcowSell);

		JButton ui13 = new JButton(cowBuyImg);
		ui13.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int amount = 0;
				try {
					amount = Integer.parseInt(JOptionPane.showInputDialog(ENbuyCowsDialog));
				} catch (Exception ex) {

				}
				if (simulator.getMoney() >= (amount * moneyPerBuyCow)) {
					simulator.buyCows(amount);
				}
			}
		});

		ui13.setToolTipText(ENcowBuy);

		JButton ui14 = new JButton(billBuyImg);
		ui14.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					int yes = JOptionPane.showConfirmDialog(null, ENbuyBillsDialog, null, JOptionPane.YES_NO_OPTION);
					if (yes == 0) {
						buyBill();
					}
				} catch (Exception ex) {

				}
			}
		});

		ui14.setToolTipText(ENbillBuy);
		
		JButton ui15 = new JButton(farmerBuyImg); // Money tracker
		ui15.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					int yes = JOptionPane.showConfirmDialog(null, ENbuyFarmersDialog, null, JOptionPane.YES_NO_OPTION);
					if (yes == 0 && simulator.getMoney() >= moneyPerBuyFarmer) {
						simulator.money -= moneyPerBuyFarmer;
						simulator.farmersHired = true;
					}
				} catch (Exception ex) {

				}
			}
		});

		ui15.setToolTipText(ENfarmerBuy);

		JButton ui16 = new JButton(saveImg); // Money tracker
		ui16.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				simulator.printAddress();
				if (JOptionPane.showConfirmDialog(null, "Quit to title screen?") == 0) {
					ui.remove(ui0);
					ui.remove(ui1);
					ui.setupSplashGUI();
				}
			}
		});

		ui16.setToolTipText(ENsave);

		// Add components to panels
		ui0.add(ui00);
		ui0.add(ui01);
		ui0.add(ui02);
		ui0.add(ui03);
		ui0.add(ui04);

		ui1.add(ui10);
		ui1.add(ui11);
		ui1.add(ui12);
		ui1.add(ui13);
		ui1.add(ui14);
		ui1.add(ui15);
		ui1.add(ui16);
		// Add panels to frames
		ui.add(ui0, BorderLayout.NORTH);
		ui.add(ui1, BorderLayout.CENTER);
		// Frame setup
		ui.setTitle(ENversion);
		ui.setDefaultCloseOperation(GameWindow.DO_NOTHING_ON_CLOSE);
		
		// Frame animation
		simulator.frameRate = new Timer((int) timeStep, new ActionListener() {
			@SuppressWarnings("unused")
			public void actionPerformed(ActionEvent e) {
				simulator.update();
				ui00.setText("" + simulator.getMilk()); // Print amount of milk
				ui01.setText("" + simulator.getCows()); // Print amount of cows
				ui02.setText("" + simulator.getMoney()); // Print amount of money
				if (pointsPerLevel > 0) {
					ui03.setText("" + simulator.getFarmLevel()); // Print farm level
					ui04.setText("" + simulator.getFarmPoints() % pointsPerLevel); // Print amount of farm points
				} else {
					ui03.setText("1"); // Print farm level
					ui04.setText("0"); // Print amount of farm points
				}
				
				if (simulator.getFarmLevel() >= 2) {
					ui15.setVisible(true);
					if (!unlockedBuyFarmersDialogShown) {
						JOptionPane.showMessageDialog(null, ENunlockedBuyFarmersDialog);
						unlockedBuyFarmersDialogShown = true;
					}
				} else {
					ui15.setVisible(false);
				}
				
				if (farmersHired) {
					ui14.setVisible(false);
					if (simulator.billTime >= billTimestep) {
						if (simulator.getMoney() >= moneyPerBuyBill) {
							buyBill();
						} else {
							JOptionPane.showMessageDialog(null, ENbrokeDialog);
						}
					}
				} else {
					ui14.setVisible(simulator.billTime >= billTimestep); // Show "Pay bills" button if it's time to pay
																		// bills
				}

				ui.setMinimumSize(new Dimension(GameWindow.sizeX, GameWindow.sizeY)); // Pack frame
			}
		});

		// Show dialog when closing
		ui.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent we) {
				if (JOptionPane.showConfirmDialog(null, "Are you sure you want to close the game?", null,
						JOptionPane.YES_NO_OPTION) == 0) {
					if (JOptionPane.showConfirmDialog(null, "Save before closing?", null,
							JOptionPane.YES_NO_OPTION) == 0) {
						simulator.frameRate.stop();
						simulator.printAddress();
					}
					System.exit(0);
				}
			}
		});
	}

	/**
	 * Starts the game using the specified GameWindow. It also sets up the
	 * GameWindow.
	 * 
	 * @param frame The GameWindow to launch the game on
	 */
	public void startGame(GameWindow frame) {
		setupGUI(this, frame);
		frame.setVisible(true);
		frameRate.start();
	}

	/**
	 * Starts the game using the specified GameWindow. It does not set up the
	 * GameWindow.
	 * 
	 * @param frame The GameWindow to launch the game on
	 */
	public void startGameWithoutSetup(GameWindow frame) {
		frame.setVisible(true);

		frameRate.start();
	}

	/**
	 * Starts the game using a new GameWindow.
	 */
	public void startGameWithNewWindow() {
		GameWindow frame = new GameWindow();
		startGame(frame);
	}

	/**
	 * Gets the current state of the game as a String called the game address.
	 * 
	 * @return The game address.
	 */
	public String getAddress() {
		return gameWon + "." + getMoney() * 69 + "." + getCows() * 57 + "." + getMilk() * 60 + "."
				+ getFarmPoints() * 67 + "." + farmersHired;
	}

	/**
	 * Prints the current state of the game as a String called the game address.
	 */
	public void printAddress() {
		StringSelection stringSelection = new StringSelection(getAddress());
		if (JOptionPane.showConfirmDialog(null, ENsavingGame0 + getAddress() + ENsavingGame1, null,
				JOptionPane.YES_NO_OPTION) == 0) {
			Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
			clipboard.setContents(stringSelection, null);
		}
	}

	/**
	 * Returns a new game created from a game address.
	 * 
	 * @param address The game address to create the game from.
	 * @return The game created from the address
	 */
	public static CowSim readAddress(String address) {
		int dots = 0;
		String moneya = "";
		String cowsa = "";
		String milka = "";
		String farmPointsa = "";
		boolean gameWona = false;
		boolean farmersHireda = false;

		try {
			if (address.substring(0, 5).equals("true.")) {
				address = address.substring(5, address.length());
				gameWona = true;
			} else if (address.substring(0, 6).equals("false.")) {
				address = address.substring(6, address.length());
			}
			
			if (address.substring(address.length()-5).equals(".true")) {
				address = address.substring(0, address.length()-5);
				farmersHireda = true;
			} else if (address.substring(address.length()-6).equals(".false")) {
				address = address.substring(0, address.length()-6);
				farmersHireda = false;
			}

			for (int i = 0; i < address.length(); i++) {
				if (address.charAt(i) == '.') {
					dots++;
				} else if (dots == 0) {
					moneya += "" + address.charAt(i);
				} else if (dots == 1) {
					cowsa += "" + address.charAt(i);
				} else if (dots == 2) {
					milka += "" + address.charAt(i);
				} else if (dots == 3) {
					farmPointsa += "" + address.charAt(i);
				}
			}

			if (farmPointsa.isEmpty()) {
				farmPointsa = "" + Integer.parseInt(milka) * pointsPerMilkSell;
			}

			return new CowSim(Integer.parseInt(moneya) / 69, Integer.parseInt(cowsa) / 57, Integer.parseInt(milka) / 60,
					Integer.parseInt(farmPointsa) / 67, gameWona, farmersHireda);
		} catch (Exception e) {
			return new CowSim();
		}
	}

	/**
	 * Launches a new game created from a game address, using a new window.
	 * 
	 * @param address The game address to create the game from
	 */
	public static void readAddressAndLaunch(String address) {
		readAddress(address).startGameWithNewWindow();
	}

	/**
	 * Launches a new game created from a game address.
	 * 
	 * @param address The game address to create the game from
	 * @param frame   The GameWindow to launch the game on
	 */
	public static void readAddressAndLaunch(String address, GameWindow frame) {
		readAddress(address).startGame(frame);
	}

	/**
	 * Launches the user's choice of game, either from a game address or from a new
	 * game, using a new window.
	 */
	public static void getInputAndLaunch() {
		try {
			String launchType = JOptionPane.showInputDialog(ENlaunchTypeDialog);
			if (launchType.equalsIgnoreCase("NEW")) {
				new CowSim().startGameWithNewWindow();
			} else if (launchType.equalsIgnoreCase("OPEN")) {
				readAddressAndLaunch(JOptionPane.showInputDialog(ENlaunchAddressDialog));
			} else {
				getInputAndLaunch();
			}
		} catch (Exception e) {
			System.exit(0);
		}
	}

	/**
	 * Launches the user's choice of game, either from a game address or from a new
	 * game.
	 * 
	 * @param frame The GameWindow to launch the game on
	 */
	public static void getInputAndLaunch(GameWindow frame) {
		try {
			String launchType = JOptionPane.showInputDialog(ENlaunchTypeDialog);
			if (launchType.equalsIgnoreCase("NEW")) {
				new CowSim().startGameWithNewWindow();
			} else if (launchType.equalsIgnoreCase("OPEN")) {
				readAddressAndLaunch(JOptionPane.showInputDialog(ENlaunchAddressDialog), frame);
			} else {
				getInputAndLaunch(frame);
			}
		} catch (Exception e) {
			System.exit(0);
		}
	}

	/**
	 * Returns the user's choice of game, either from a game address or from a new
	 * game.
	 * 
	 * @return The user's choice of game
	 */
	public static CowSim getInputAndReturn() {
		try {
			String launchType = JOptionPane.showInputDialog(ENlaunchTypeDialog);
			if (launchType.equalsIgnoreCase("NEW")) {
				return new CowSim();
			} else if (launchType.equalsIgnoreCase("OPEN")) {
				return readAddress(JOptionPane.showInputDialog(ENlaunchAddressDialog));
			} else {
				return getInputAndReturn();
			}
		} catch (Exception e) {
			return null;
		}
	}

}
