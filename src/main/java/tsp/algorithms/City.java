package tsp.algorithms;

public class City {
	int id;
	int x;
	int y;
	boolean isVisited = false;

	// Constructs a randomly placed city
	public City() {
		this.x = (int) (Math.random() * 200);
		this.y = (int) (Math.random() * 200);
	}

	// Constructs a city at chosen x, y location
	public City(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public City(int x, int y, int id) {
		this.x = x;
		this.y = y;
		this.id = id;
	}


	// Gets city's x coordinate
	public int getX() {
		return this.x;
	}

	// Gets city's y coordinate
	public int getY() {
		return this.y;
	}

	// Gets the distance to given city
	public double distanceTo(City city) {
		int xDistance = Math.abs(getX() - city.getX());
		int yDistance = Math.abs(getY() - city.getY());
		double distance = Math.sqrt((xDistance * xDistance)
				+ (yDistance * yDistance));

		return distance;
	}

	@Override
	public String toString() {
		return getX() + ", " + getY();
	}

	public boolean isVisited() {
		return isVisited;
	}

	public void setVisited(boolean isVisited) {
		this.isVisited = isVisited;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		City other = (City) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	
}
