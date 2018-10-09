package runner;

public class Field {

	static long timeStep = 100; // Milliseconds before updating program

	public static final int defaultQuality = 10; // Quality of the field without compost

	public static final int waterTimestep = 600; // Updates before field must be watered
	public int waterTime = waterTimestep; // Updates since field was last watered

	private int quality;
	
	public static final int fertilizerTimestep = 300; // Updates that fertilizer lasts
	private double fertilizer;

	/**
	 * Creates a new Field instance.
	 */
	public Field() {

	}

	/**
	 * Gets the current amount of fertilizer.
	 * 
	 * @returns amount The amount of fertilizer
	 */
	public double getFertilizer() {
		return fertilizer;
	}

	/**
	 * Fertilizes the field with the specified amount of fertilizer.
	 * 
	 * @param simulator The simulator to fund the purchase
	 * @param amount    The amount of
	 */
	public void fertilize(CowSim simulator, int amount) {
		if (simulator.getMoney() >= CowSim.moneyPerBuyFertilizer) {
			fertilizer += amount;
		}
	}

	/**
	 * Returns the field's grass quality.
	 * 
	 * @returns The grass quality
	 */
	public int getQuality() {
		if (waterTime >= waterTimestep) {
			quality = 0;
		} else {
			quality = (int) Math.floor(fertilizer * 2) + defaultQuality;
		}

		return quality;
	}

	/**
	 * Waters the field if there is enough money in the simulator.
	 * 
	 * @param simulator The simulator to fund the purchase
	 */
	public void water(CowSim simulator) {
		if (simulator.getMoney() >= CowSim.moneyPerBuyWater) {
			waterTime = 0;
		}
	}

	/**
	 * Updates the field.
	 */

	public void update() {
		waterTime++;
		fertilizer -= (fertilizer/fertilizerTimestep);
	}

}
