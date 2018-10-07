package runner;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.*;
import java.io.File;
import java.net.URI;
import java.util.Scanner;

import javax.swing.*;

@SuppressWarnings("serial")
public class GameWindow extends JFrame {

	final static int sizeX = 600;
	final static int sizeY = 600;

	public boolean isSetUp = false;
	public Font titleFont;

	public JPanel splashPanel0;
	public JPanel splashPanel1;

	public CowSim imageResource;
	public File origFile = null;

	/**
	 * Creates a GameWindow instance.
	 */

	public GameWindow() {
		titleFont = new Font("Arial", Font.PLAIN, 50);

		splashPanel0 = new JPanel();
		splashPanel1 = new JPanel();

		imageResource = new CowSim();
		imageResource.setupImages();

		setTitle(CowSim.ENversion);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setMinimumSize(new Dimension(sizeX, sizeY));
	}

	/**
	 * Shows the GameWindow.
	 */

	public void start() {
		super.setVisible(true);

		setupSplashGUI();
		add(splashPanel0, BorderLayout.NORTH);
		add(splashPanel1, BorderLayout.CENTER);

		splashPanel0.repaint();
		splashPanel1.repaint();
		setSize(800, 800);
		setSize(sizeX, sizeY);
	}

	/**
	 * Sets up the splash page.
	 */

	public void setupSplashGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setMinimumSize(new Dimension(sizeX, sizeY));

		splashPanel0.removeAll();
		splashPanel0.setVisible(true);

		splashPanel1.removeAll();
		splashPanel1.setVisible(true);

		// Title GUI
		JLabel logo = new JLabel(imageResource.logoImg);
		logo.setToolTipText(CowSim.ENversion);

		splashPanel0.add(logo);

		// Menu GUI
		JButton newGame = new JButton(imageResource.gameNewImg);
		newGame.setToolTipText(CowSim.ENgameNew);
		newGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				startGame(new CowSim());
			}
		});

		splashPanel1.add(newGame);

		JButton importTxtGame = new JButton(imageResource.gameImportTxtImg);
		importTxtGame.setToolTipText(CowSim.ENgameImportTxt);
		importTxtGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CowSim game = CowSim.readAddress(JOptionPane.showInputDialog(CowSim.ENlaunchAddressDialog));
				if (!game.getAddress().equals("false.6900.57.0.0.79.false")) {
					startGame(game);
				}
			}
		});

		splashPanel1.add(importTxtGame);

		JButton importFileGame = new JButton(imageResource.gameImportFileImg);
		importFileGame.setToolTipText(CowSim.ENgameImportFile);
		importFileGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					JFileChooser addressChooser = new JFileChooser();
					addressChooser.showOpenDialog(null);
					File addressFile = addressChooser.getSelectedFile();
					origFile = addressFile;

					Scanner addressFileReader = new Scanner(addressFile);

					String address = "";

					while (addressFileReader.hasNextLine()) {
						String line = addressFileReader.nextLine();
						address += line;
					}

					addressFileReader.close();
					startGame(CowSim.readAddress(address));
				} catch (Exception ex) {

				}
			}
		});

		splashPanel1.add(importFileGame);

		JButton seeGithub = new JButton(imageResource.githubSeeImg);
		seeGithub.setToolTipText(CowSim.ENgithubSee);
		seeGithub.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					java.awt.Desktop.getDesktop().browse(new URI(CowSim.githubURL));
				} catch (Exception ex) {

				}
			}
		});

		splashPanel1.add(seeGithub);

		JButton seeWiki = new JButton(imageResource.wikiSeeImg);
		seeWiki.setToolTipText(CowSim.ENwikiSee);
		seeWiki.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					java.awt.Desktop.getDesktop().browse(new URI(CowSim.wikiURL));
				} catch (Exception ex) {

				}
			}
		});

		splashPanel1.add(seeWiki);
	}

	/**
	 * Starts the specified game.
	 * 
	 * @param simulator The game, or "simulator" to launch
	 */

	public void startGame(CowSim simulator) {
		simulator.startGame(this);
		splashPanel0.setVisible(false);
		splashPanel1.setVisible(false);
	}

}
