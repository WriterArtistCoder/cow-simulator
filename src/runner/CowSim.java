package runner;
/**
 * CowSim: A Dairy Odyssey is a game that simulates a dairy farm.
 * See https://github.com/WriterArtistCoder/cow-simulator and
 * https://github.com/WriterArtistCoder/cow-simulator/wiki for more.
 * 
 * @author Github user WriterArtistCoder
 * @version 2.3.0
*/

/** Ideas 
 * Use MouseInfo.getPointerInfo().getLocation() to get mouse X and Y position
 * Use jar2app: jar2app /Users/soda/Desktop/CowSim.jar -i /Users/soda/Desktop/Icons/CowSim/Logo/favicon.icns -o -v 2.3.0 -s 2.3.0 -n "CowSim - A Dairy Odyssey" -d CowSim
 * Remember to export CowSim to /Users/soda/Desktop/CowSim.jar first!
*/

/** Bugs */

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.PrintWriter;

import javax.imageio.ImageIO;
import javax.swing.*;

public class CowSim {

	public Timer frameRate;

	private int meadows;
	private int money;
	private int cows;
	private int milk;

	// Encryption keys
	private static final int encryptionKeyMoney = 69;
	private static final int encryptionKeyCows = 57;
	private static final int encryptionKeyMilk = 60;
	private static final int encryptionKeyMeadows = 79;

	// URLs
	public static final String githubURL = "https://github.com/WriterArtistCoder/cow-simulator"; // The Github page URL
	public static final String wikiURL = "https://github.com/WriterArtistCoder/cow-simulator/wiki"; // The wiki page URL
	public static final String updateURL = "https://github.com/WriterArtistCoder/cow-simulator/releases/latest"; // The
																													// update
																													// page
	// URL

	public static final long timeStep = 100; // Milliseconds before updating program

	static double milkProb = 0.02;

	public static final int billTimestep = 1200; // Updates before bill must be paid
	public int billTime = 0; // Updates since bill was last paid

	public static final int moneyPerSellMilk = 3;
	public static final int moneyPerSellCows = 50;
	public static final int moneyPerBuyCow = 100;

	public static final int moneyPerBuyMeadow = 20;
	public static final int moneyPerBuyBill = 20;
	public static final int moneyPerBuyWater = 20;
	public static final int moneyPerBuyFertilizer = 20;

	public static final int pointsPerLevel = 250; // Points per level

	public static final int pointsPerMilk = 2;
	public static final int pointsPerMilkSell = 5;
	public static final int pointsPerCowBuy = 20;

	public static final double meadowsPerCow = 0.1;

	public Font trackerFont;

	public int cowPunNum = 0;
	public String[] cowPuns = new String[] { "Your cow-op will thrive until\n the cows come home!", "Manure great!",
			"Manure admira-bull!", "You've got some knock-cowt skills!", "Your cow-op will thrive for-heifer!" }; // List
																													// of
																													// cow
																													// puns

	public boolean showPuns = false;
	public boolean gameWon = false; // If "You won" pop-up has shown yet

	// English text for game
	static String ENversion = "CowSim 2.3.0";

	static String ENupdateDialog = "A new version of CowSim is available.\n Do you wish to download it to your\n default browser's specified downloads folder?";

	static String ENlaunchTypeDialog = "Do you want to create a new game (NEW) or import a game (OPEN)?";
	static String ENlaunchAddressDialog = "Type in your game address.";

	static String ENshowPunsDialog = "Show cow puns when cows are bought?";

	static String ENsaveGame = "Saving game...\n Save game and quit to title screen (YES) or skip\n saving, lose your progress, and only quit to title screen (NO)?";

	static String ENsellCowsDialog = "Sell how many cows? (Type an integer, or type 0 to cancel)\n Cows are worth $"
			+ moneyPerSellCows + " each.";
	static String ENbuyCowsDialog = "Buy how many cows? (Type an integer, or type 0 to cancel)\n Cows are worth $"
			+ moneyPerBuyCow + " each and each herd of 10 cows\n needs one meadow.";

	static String ENbuyMeadowsDialog = "Buy how many meadows? (Type an integer, or type 0 to cancel)\n Meadows are worth $"
			+ moneyPerBuyMeadow + " each and each herd of 10 cows\n needs one meadow.";
	static String ENbuyBillsDialog = "Pay bills? Costs $" + moneyPerBuyBill
			+ ". If bills aren't paid your cows won't have milk.";
	static String ENbuyWaterDialog = "Water field? Costs $" + moneyPerBuyWater
			+ ". If field isn't watered, it will die, and your cows won't have milk.";
	static String ENbuyFertilizerDialog = "Buy and use how much fertilizer? (Type an integer, or type 0 to cancel)\n Costs $"
			+ moneyPerBuyFertilizer + " each, and will boost your field's growth,\n therefore boosting your cows' milk.";
	static String ENfertilizerLimitDialog = "Dude. Way too much fertilizer.";
	static String ENbrokeDialog = "You are broke and cannot pay your bills!\n If you have some cows, sell them.";

	static String ENcloseGameDialog = "Are you sure you want to close the game\n without saving? (To save, click the Save button.)";

	// Tooltip text for splash and game
	static String ENgameNew = "New game";
	static String ENgameImportTxt = "Import game from game address";
	static String ENgameImportFile = "Import game from file";
	static String ENgithubSee = "See CowSim's Github page";
	static String ENwikiSee = "See CowSim Wiki, a wiki/manual for CowSim";

	static String ENmilk = "Milk";
	static String ENmilkSellAll = "Sell all milk";

	static String ENcow = "Cows";
	static String ENcowSell = "Sell cows";
	static String ENcowBuy = "Buy cows";

	static String ENmeadow = "Meadows";
	static String ENmeadowBuy = "Buy meadows";

	static String ENmoney = "Money";

	static String ENfieldQuality = "Field quality";
	static String ENfieldWater = "Water field";
	static String ENfieldFertilize = "Fertilize field";

	static String ENbillBuy = "Pay bills";

	static String ENsave = "Save as new .cowsim and quit to title";

	// Image variables for splash and game
	static String logoImgname = "CowSimLogo_v2.3.0.png";
	Icon logoImg;

	static String gameNewImgname = "game-new.png";
	Icon gameNewImg;
	static String gameImportTxtImgname = "game-importtxt.png";
	Icon gameImportTxtImg;
	static String gameImportFileImgname = "game-importfile.png";
	Icon gameImportFileImg;
	static String githubSeeImgname = "github-see.png";
	Icon githubSeeImg;
	static String wikiSeeImgname = "wiki-see.png";
	Icon wikiSeeImg;

	static String milkImgname = "milk.png";
	Icon milkImg;
	static String milkSellAllImgname = "milk-sell-all.png";
	Icon milkSellAllImg;

	static String cowImgname = "cow.png";
	Icon cowImg;
	static String cowSellImgname = "cow-sell.png";
	Icon cowSellImg;
	static String cowBuyImgname = "cow-buy.png";
	Icon cowBuyImg;

	static String meadowImgname = "meadow.png";
	Icon meadowImg;
	static String meadowBuyImgname = "meadow-buy.png";
	Icon meadowBuyImg;

	static String moneyImgname = "money.png";
	Icon moneyImg;

	static String billBuyImgname = "bill-buy.png";
	Icon billBuyImg;

	static String saveImgname = "export.png";
	Icon saveImg;

	static String fieldQualityImgname = "field-quality.png";
	Icon fieldQualityImg;
	static String fieldWaterImgname = "water-buy.png";
	Icon fieldWaterImg;
	static String fieldFertilizeImgname = "fertilizer-buy.png";
	Icon fieldFertilizeImg;

	public Field field;

	/**
	 * Creates a CowSim dairy farm instance with the starting supplies of money,
	 * milk, and cows.
	 */

	public CowSim() {
		meadows = 1;
		money = 100;
		cows = 1;
		milk = 0;
		field = new Field();

		trackerFont = new Font("Monospace", Font.PLAIN, 20);
	}

	/**
	 * Creates a CowSim dairy farm instance from the specified amounts of money,
	 * milk, and cows.
	 * 
	 * @param moneya   The amount of money for the simulator to have
	 * @param cowsa    The amount of cows for the simulator to have
	 * @param milka    The amount of milk for the simulator to have
	 * @param gameWona Whether the "Game won" pop-up has shown
	 * @param meadowsa The amount of meadows for the simulator to have
	 */

	public CowSim(int moneya, int cowsa, int milka, boolean gameWona, int meadowsa) {
		meadows = meadowsa;
		money = moneya;
		cows = cowsa;
		milk = milka;
		gameWon = gameWona;
		field = new Field();

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
	 * Gets current amount of meadows.
	 * 
	 * @returns The current amount of meadows
	 */

	public int getMeadows() {
		return meadows;
	}

	/**
	 * Sells specified amount of milk.
	 * 
	 * @param amount The amount of milk to sell
	 */

	public void sellMilk(int amount) {
		milk -= amount; // Decrease milk
		money += moneyPerSellMilk * amount; // Increase money
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
		if (money >= moneyPerBuyCow * amount
				&& ((double) meadows) >= Math.ceil((double) meadowsPerCow * (cows + amount))) {
			cows += amount; // Increase cows
			money -= moneyPerBuyCow * amount; // Decrease money
		}
	}

	/**
	 * Purchases specified amount of meadows.
	 * 
	 * @param amount The amount of meadows to purchase
	 */

	public void buyMeadows(int amount) {
		if (money >= moneyPerBuyMeadow * amount) {
			meadows += amount; // Increase meadows
			money -= moneyPerBuyMeadow * amount; // Decrease money
		}
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
			milk += Math.ceil(massMilk * 5) * (field.getQuality() / Field.defaultQuality); // Add milk
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
			gameImportTxtImg = new ImageIcon(
					ImageIO.read(new CowSim().getClass().getResourceAsStream(gameImportTxtImgname)));
			gameImportFileImg = new ImageIcon(
					ImageIO.read(new CowSim().getClass().getResourceAsStream(gameImportFileImgname)));
			githubSeeImg = new ImageIcon(ImageIO.read(new CowSim().getClass().getResourceAsStream(githubSeeImgname)));
			wikiSeeImg = new ImageIcon(ImageIO.read(new CowSim().getClass().getResourceAsStream(wikiSeeImgname)));

			milkImg = new ImageIcon(ImageIO.read(new CowSim().getClass().getResourceAsStream(milkImgname)));
			milkSellAllImg = new ImageIcon(
					ImageIO.read(new CowSim().getClass().getResourceAsStream(milkSellAllImgname)));
			cowImg = new ImageIcon(ImageIO.read(new CowSim().getClass().getResourceAsStream(cowImgname)));
			cowSellImg = new ImageIcon(ImageIO.read(new CowSim().getClass().getResourceAsStream(cowSellImgname)));
			cowBuyImg = new ImageIcon(ImageIO.read(new CowSim().getClass().getResourceAsStream(cowBuyImgname)));

			meadowImg = new ImageIcon(ImageIO.read(new CowSim().getClass().getResourceAsStream(meadowImgname)));
			meadowBuyImg = new ImageIcon(ImageIO.read(new CowSim().getClass().getResourceAsStream(meadowBuyImgname)));

			moneyImg = new ImageIcon(ImageIO.read(new CowSim().getClass().getResourceAsStream(moneyImgname)));

			billBuyImg = new ImageIcon(ImageIO.read(new CowSim().getClass().getResourceAsStream(billBuyImgname)));
			saveImg = new ImageIcon(ImageIO.read(new CowSim().getClass().getResourceAsStream(saveImgname)));

			fieldQualityImg = new ImageIcon(
					ImageIO.read(new CowSim().getClass().getResourceAsStream(fieldQualityImgname)));
			fieldWaterImg = new ImageIcon(ImageIO.read(new CowSim().getClass().getResourceAsStream(fieldWaterImgname)));
			fieldFertilizeImg = new ImageIcon(
					ImageIO.read(new CowSim().getClass().getResourceAsStream(fieldFertilizeImgname)));
		} catch (Exception e) {
			e.printStackTrace();
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

		// UI setup TODO (REMOVE ALL ADDED PANELS, ui0, ui1, etc... FROM ui WHEN SAVE
		// BUTTON IS CLICKED
		JPanel ui0 = new JPanel(); // Tracker panel
		JPanel ui1 = new JPanel(); // Control panel
		JPanel ui2 = new JPanel(); // Field panel
		ui2.setForeground(Color.YELLOW);
		ui2.setBackground(new Color(73, 219, 0));

		// Tracker panel
		JLabel ui00 = new JLabel(milkImg); // Milk tracker
		ui00.setFont(trackerFont);
		ui00.setToolTipText(ENmilk);

		JLabel ui01 = new JLabel(cowImg); // Cow tracker
		ui01.setFont(trackerFont);
		ui01.setToolTipText(ENcow);

		JLabel ui02 = new JLabel(meadowImg);
		ui02.setFont(trackerFont);
		ui02.setToolTipText(ENmeadow);

		JLabel ui03 = new JLabel(moneyImg); // Money tracker
		ui03.setFont(trackerFont);
		ui03.setToolTipText(ENmoney);

		// Control panel
		JButton ui10 = new JButton(milkSellAllImg);
		ui10.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				simulator.sellMilk(simulator.getMilk());
			}
		});
		ui10.setToolTipText(ENmilkSellAll);

		JButton ui11 = new JButton(cowSellImg);
		ui11.addActionListener(new ActionListener() {
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
		ui11.setToolTipText(ENcowSell);

		JButton ui12 = new JButton(cowBuyImg);
		ui12.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int amount = 0;
				try {
					amount = Integer.parseInt(JOptionPane.showInputDialog(ENbuyCowsDialog));

					simulator.buyCows(amount);
					if (showPuns) {
						JOptionPane.showMessageDialog(null, cowPuns[cowPunNum]);
						cowPunNum++;
						if (cowPunNum == cowPuns.length) {
							cowPunNum = 0;
						}
					}
				} catch (Exception ex) {

				}
			}
		});

		ui12.setToolTipText(ENcowBuy);

		JButton ui13 = new JButton(meadowBuyImg);
		ui13.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int amount = 0;
				try {
					amount = Integer.parseInt(JOptionPane.showInputDialog(ENbuyMeadowsDialog));

					simulator.buyMeadows(amount);
				} catch (Exception ex) {

				}
			}
		});
		ui13.setToolTipText(ENmeadowBuy);

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

		JButton ui15 = new JButton(saveImg); // Save button
		ui15.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				simulator.frameRate.stop();
				simulator.printAddress(ui);
				if (JOptionPane.showConfirmDialog(null, "Quit to title screen?") == 0) {
					ui.origFile = null;
					ui.remove(ui0);
					ui.remove(ui1);
					ui.remove(ui2);
					ui.setupSplashGUI();
					ui.isSetUp = false;

				} else {
					simulator.frameRate.start();
				}
			}
		});

		ui15.setToolTipText(ENsave);

		// Field panel
		JLabel ui20 = new JLabel(fieldQualityImg); // Quality tracker
		ui20.setFont(trackerFont);
		ui20.setToolTipText(ENfieldQuality);

		JButton ui21 = new JButton(fieldWaterImg);
		ui21.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					int yes = JOptionPane.showConfirmDialog(null, ENbuyWaterDialog, null, JOptionPane.YES_NO_OPTION);
					if (yes == 0) {
						simulator.field.water(simulator);
					}
				} catch (Exception ex) {

				}
			}
		});
		ui21.setToolTipText(ENfieldWater);

		JButton ui22 = new JButton(fieldFertilizeImg);
		ui22.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					int amount = Integer.parseInt(
							JOptionPane.showInputDialog(null, ENbuyFertilizerDialog, null, JOptionPane.YES_NO_OPTION));
					if ((simulator.field.getFertilizer()+amount) > 1000) {
						JOptionPane.showMessageDialog(null, ENfertilizerLimitDialog);
					} else {
						simulator.field.fertilize(simulator, amount);
					} 
				} catch (Exception ex) {

				}
			}
		});
		ui22.setToolTipText(ENfieldFertilize);

		// Add components to panels
		ui0.add(ui00);
		ui0.add(ui01);
		ui0.add(ui02);
		ui0.add(ui03);

		ui1.add(ui10);
		ui1.add(ui11);
		ui1.add(ui12);
		ui1.add(ui13);
		ui1.add(ui14);
		ui1.add(ui15);

		ui2.add(ui20);
		ui2.add(ui21);
		ui2.add(ui22);
		// Add panels to frames
		ui.add(ui0, BorderLayout.NORTH);
		ui.add(ui1, BorderLayout.CENTER);
		ui.add(ui2, BorderLayout.SOUTH);
		// Frame setup
		ui.setTitle(ENversion);
		ui.setDefaultCloseOperation(GameWindow.DO_NOTHING_ON_CLOSE);
		// Frame animation
		simulator.frameRate = new Timer((int) timeStep, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				simulator.update();
				ui00.setText("" + simulator.getMilk()); // Print amount of milk
				ui01.setText("" + simulator.getCows()); // Print amount of cows
				ui02.setText("" + simulator.getMeadows()); // Print amount of meadows
				ui03.setText("" + simulator.getMoney()); // Print amount of money

				ui14.setVisible(simulator.billTime >= billTimestep); // Show "Pay bills" button if it's time to pay
																		// bills
				simulator.field.update();
				ui20.setText("" + simulator.field.getQuality());

				ui21.setVisible(simulator.field.waterTime >= Field.waterTimestep); // Show "Water field" button if it's
																					// time to water

				ui.setMinimumSize(new Dimension(GameWindow.sizeX, GameWindow.sizeY)); // Pack frame
			}
		});

		// Show dialog when closing
		ui.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent we) {
				if (ui.isSetUp) {
					if (JOptionPane.showConfirmDialog(null, ENcloseGameDialog, null, JOptionPane.YES_NO_OPTION) == 0) {
						System.exit(0);
					}
				} else {
					System.exit(0);
				}
			}
		});

		ui.isSetUp = true;
	}

	/**
	 * Starts the game using the specified GameWindow. It also sets up the
	 * GameWindow for the game.
	 * 
	 * @param frame The GameWindow to launch the game on
	 */

	public void startGame(GameWindow frame) {
		setupGUI(this, frame);
		frame.setVisible(true);
		if (JOptionPane.showConfirmDialog(null, ENshowPunsDialog, null, JOptionPane.YES_NO_OPTION) == 0) {
			showPuns = true;
		}

		frameRate.start();
	}

	/**
	 * Starts the game using the specified GameWindow. It does not set up the
	 * GameWindow for the game.
	 * 
	 * @param frame The GameWindow to launch the game on
	 */

	public void startGameWithoutSetup(GameWindow frame) {
		frame.setVisible(true);
		if (JOptionPane.showConfirmDialog(null, ENshowPunsDialog, null, JOptionPane.YES_NO_OPTION) == 0) {
			showPuns = true;
		}

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
	 * @returns The game address.
	 */

	public String getAddress() {
		return gameWon + "." + getMoney() * encryptionKeyMoney + "." + getCows() * encryptionKeyCows + "."
				+ getMilk() * encryptionKeyMilk + ".0." + getMeadows() * encryptionKeyMeadows + ".false";
	}

	/**
	 * Saves the current state of the game as a .cowsim file.
	 * 
	 * @param frame The GameWindow that is currently running the game
	 */

	public void printAddress(GameWindow frame) {
		String address = getAddress();
		if (JOptionPane.showConfirmDialog(null, ENsaveGame, null, JOptionPane.YES_NO_OPTION) == 0) {
			boolean locationIsNull = false;
			try {
				JFileChooser locationChooser = new JFileChooser();
				File location = frame.origFile;

				if (location == null) {
					locationIsNull = true;
					locationChooser.showSaveDialog(null);
					location = locationChooser.getSelectedFile();
				}

				PrintWriter out = new PrintWriter(location);
				out.write(address);
				out.close();

				if (locationIsNull) {
					location.renameTo(new File(location.getParent() + "/" + location.getName() + ".cowsim"));
				}
			} catch (Exception e) {

			}
		}
	}

	/**
	 * Returns a new game created from a game address. If the game address is
	 * invalid it returns a new game.
	 * 
	 * @param address The game address to create the game from.
	 * @returns The game created from the address
	 */

	public static CowSim readAddress(String address) {
		int dots = 0;
		String moneya = "";
		String cowsa = "";
		String milka = "";
		@SuppressWarnings("unused")
		String farmPointsa = "";
		String meadowsa = "";
		boolean gameWona = false;

		try {
			if (address.substring(0, 5).equals("true.")) {
				address = address.substring(5, address.length());
				gameWona = true;
			} else if (address.substring(0, 6).equals("false.")) {
				address = address.substring(6, address.length());
			}

			int lastDot = address.lastIndexOf(".");
			int secondLastDot = address.lastIndexOf(".", lastDot - 1);

			if (address.substring(lastDot).equals(".true")) {
				address = address.substring(0, address.length() - 5);
			} else if (address.substring(lastDot).equals(".false")) {
				address = address.substring(0, address.length() - 6);
			}

			if (address.substring(secondLastDot, lastDot).equals(".true")) {
				address = address.replaceFirst(address.substring(secondLastDot, lastDot), "");
			} else if (address.substring(secondLastDot, lastDot).equals(".false")) {
				address = address.replaceFirst(address.substring(secondLastDot, lastDot), "");
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
				} else if (dots == 4) {
					meadowsa += "" + address.charAt(i);
				}
			}

			if (meadowsa.isEmpty()) {
				meadowsa = "" + Math.ceil(((double) (Integer.parseInt(cowsa) / encryptionKeyCows)) * meadowsPerCow)
						* encryptionKeyMeadows;
				meadowsa = meadowsa.substring(0, meadowsa.indexOf("."));
			}

			return new CowSim(Integer.parseInt(moneya) / encryptionKeyMoney,
					Integer.parseInt(cowsa) / encryptionKeyCows, Integer.parseInt(milka) / encryptionKeyMilk, gameWona,
					Integer.parseInt(meadowsa) / encryptionKeyMeadows);
		} catch (Exception e) {
			return new CowSim();
		}
	}

	/**
	 * Launches a new game created from a game address, using a new window. If the
	 * game address is invalid it launches a new game.
	 * 
	 * @param address The game address to create the game from
	 */

	public static void readAddressAndLaunch(String address) {
		readAddress(address).startGameWithNewWindow();
	}

	/**
	 * Launches a new game created from a game address. If the game address is
	 * invalid it launches a new game.
	 * 
	 * @param address The game address to create the game from
	 * @param frame   The GameWindow to launch the game on
	 */

	public static void readAddressAndLaunch(String address, GameWindow frame) {
		readAddress(address).startGame(frame);
	}

	/**
	 * Launches the user's choice of game, either from a game address or from a new
	 * game, using a new window. If the game address is invalid it launches a new
	 * game.
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
	 * game. If the game address is invalid it launches a new game.
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
	 * game. If the game address is invalid it returns a new game.
	 * 
	 * @returns The user's choice of game
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
