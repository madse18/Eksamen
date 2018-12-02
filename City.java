public class City {
		
	private String name;
	private double x;
	private double y;

	/*
	 * Returns a new city with a given name and x and y.
	 */
	public City(String name, double horizontal, double vertical) {
		this.name = name;
		x = horizontal;
		y = vertical;

	}

	/*
	 * Returns the city's x coordinate.
	 */
	public double x() {
		return x;
	}

	/*
	 * Returns the city's y coordinate.
	 */
	public double y() {
		return y;
	}

	/*
	 * Returns the city's name.
	 */
	public String name() {
		return name;
	}

	/*
	 * Returns the euclidean distance between two cities.
	 */
	public double distanceTo(City other) {
		double euclideanDistance;

		double deltaX = (other.x() - x());
        double deltaY = (other.y() - y());

        euclideanDistance = Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaY, 2));

        return euclideanDistance;
	}
}