package runner;

public class CowSimRunner {

	public GameWindow window;

	public CowSimRunner() {
		window = new GameWindow();
	}

	public static void main(String[] args) {
		CowSimRunner instance = new CowSimRunner();
		instance.window.start();
	}

}
