package runner;

import javax.swing.JOptionPane;

public class CowSimRunner {

	public GameWindow window;

	public CowSimRunner() {
		window = new GameWindow();
	}

	public static void main(String[] args) {
		CowSimRunner instance = new CowSimRunner();
		if (CowSimUpdater.needsUpdating()) {
			if (JOptionPane.showConfirmDialog(null, CowSim.ENupdateDialog, null, JOptionPane.YES_NO_OPTION) == 0) {
				CowSimUpdater.updateAsJar();
				System.exit(0);
			} else {
				instance.window.start();
			}
		} else {
			instance.window.start();
		}
	}

}
