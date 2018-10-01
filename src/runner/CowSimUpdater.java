package runner;

import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;

public class CowSimUpdater {

	/**
	 * Creates a new CowSimUpdater.
	 */

	public CowSimUpdater() {

	}

	/**
	 * Gets the latest CowSim's version number.
	 * 
	 * @return The latest CowSim's version number, or an empty string if it fails.
	 */

	public static String getCloudVersion() {
		try {
			URLConnection con = new URL(CowSim.updateURL).openConnection();
			InputStream x = con.getInputStream();
			con.connect();

			String url = con.getURL().toString();
			x.close();

			return url.substring(65);
		} catch (Exception e) {
			return "";
		}
	}

	/**
	 * Gets this CowSim's version number.
	 * 
	 * @return This CowSim's version number, or or an empty string if it fails.
	 */

	public static String getLocalVersion() {
		try {
			return CowSim.ENversion.substring(7);
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/**
	 * Checks if this CowSim needs to be updated.
	 * 
	 * @return Returns a boolean, whether the latest CowSim is ahead of this CowSim.
	 */

	public static boolean needsUpdating() {
		boolean needsUpdating = false;

		if (!getCloudVersion().equals(getLocalVersion())) {
			needsUpdating = true;
		}

		return needsUpdating;
	}

	/**
	 * Downloads the latest CowSim as a JAR file.
	 **/

	public static void updateAsJar() {
		try {
			URLConnection con = new URL(CowSim.updateURL).openConnection();
			InputStream x = con.getInputStream();
			con.connect();

			String url = con.getURL().toString();
			url = url.substring(0, 60) + "download/" + url.substring(64) + "/CowSim.jar";
			x.close();

			java.awt.Desktop.getDesktop().browse(new URI(url));
		} catch (Exception e) {

		}
	}

	/**
	 * Downloads the latest CowSim as a JAR file if the current one is out of date.
	 */

	public static void updateAsJarIfDated() {
		if (needsUpdating()) {
			updateAsJar();
		}
	}

}
