package runner;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.*;
import java.net.URI;

import javax.swing.*;

@SuppressWarnings("serial")
public class GameWindow extends JFrame {

	final static int sizeX = 600;
	final static int sizeY = 600;

	public Font titleFont;

	public JPanel splashPanel0;
	public JPanel splashPanel1;

	public CowSim imageResource;

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

		JButton importGame = new JButton(imageResource.gameImportTxtImg);
		importGame.setToolTipText(CowSim.ENgameImportTxt);
		importGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				startGame(CowSim.readAddress(JOptionPane.showInputDialog(CowSim.ENlaunchAddressDialog)));
			}
		});

		splashPanel1.add(importGame);

		JButton seeReleases = new JButton(imageResource.releasesSeeImg);
		seeReleases.setToolTipText(CowSim.ENreleasesSee);
		seeReleases.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					java.awt.Desktop.getDesktop().browse(new URI(CowSim.updateURL));
				} catch (Exception ex) {

				}
			}
		});

		splashPanel1.add(seeReleases);
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
