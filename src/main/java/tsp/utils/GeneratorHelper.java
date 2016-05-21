package tsp.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import tsp.algorithms.City;
import tsp.algorithms.TSAlgorithm.Tour;

public class GeneratorHelper {
	public static List<City> generateCities(int count, int xMax, int yMax, int border) {
		List<City> cities = new ArrayList<City>();
		int x, y;
		for (int i = 0; i < count; i++) {
			Random random = new Random();
			x = random.nextInt(xMax - border);
			y = random.nextInt(yMax - border);
			if (x <= border) {
				x = border;
			}
			if (y <= border) {
				y = border;
			}
			City city = new City(x, y, i);
			cities.add(city);
		}
		return cities;
	}

//	public static List<Tour> generateTestData(int citiesCount, int tourCount) {
//		List<Tour> tours = new ArrayList<Tour>();
//		for (int i = 0; i < tourCount; i++) {
//			Tour tour = new Tour(GeneratorHelper.generateCities(citiesCount, 500, 500));
//			tours.add(tour);
//		}
//		return tours;
//	}
}
