package tsp.algorithms.greedy;

import java.util.ArrayList;
import java.util.List;

import tsp.algorithms.City;
import tsp.algorithms.TSAlgorithm;
import tsp.ui.GAMainWindow;

public class GreedyAlgorithm extends TSAlgorithm{
	public GreedyAlgorithm(GAMainWindow mainWindow) {
		super(mainWindow);
	}

//	@Override
//	public Tour calculateTour() {
//		processCity(cities.get(0));
//		System.out.println(distance);
//		return new Tour(optCities);
//
//	}
	
	@Override
	public void run() {
		processCity(cities.get(0));
		setResult(new Tour(optCities));
		drawFinalResult();
	}
	
	private int visitedSum = 0;
	private int distance = 0;
	private List<City> optCities = new ArrayList<City>();
	private void processCity(City currCity)
	{
		int min=9999;
		City nextCity = null;
		visitedSum++; //количество посещенных городов
		currCity.setVisited(true);  //отметить город как посещенный
		optCities.add(currCity);
		if(visitedSum > cities.size() - 1) //если посетили все города
		{
			distance += distances[currCity.getId()][cities.get(0).getId()]; //прибавить путь к начальному городу, а потом выйти с функции
		}
		else //если не все города посетили
		{
			for(short i = 0; i < cities.size(); i++) {
				City cityAtCurrPos = cities.get(i);
				if(distances[currCity.getId()][cityAtCurrPos.getId()] < min && !cityAtCurrPos.isVisited() && distances[currCity.getId()][cityAtCurrPos.getId()] != 0) //если путь к и-тому городу ближе чем минимум и этот город не посещен
				{
					nextCity = cityAtCurrPos; //назначить следующий город для посещения
					min = (int) distances[currCity.getId()][nextCity.getId()]; //назначить новый минимум
				}
			}
			//			cout<<" "<< gorod+1<<"-"<<next+1<<" "; // пройденный маршрут
			distance += min; //когда нашли ближайший город, то добавляем к сумме путь до него
			processCity(nextCity); //идем в след город
		}
		currCity.setVisited(false);
	}

}
