package tsp.ui;

import tsp.ui.drawers.CitiesDrawer;
import tsp.ui.drawers.CitiesPathDrawer;

public class DrawerFactory {
	public enum DrawerType {
		Cities {
			@Override
			public String toString() {
				return "Cities";
			}
		}, 
		CitiesPath {
			@Override
			public String toString() {
				return "CitiesPath";
			}
		}
	}

	//use getShape method to get object of type shape 
	public static DrawerStrategy getDrawerStrategy(DrawerType drawerType){
		switch (drawerType) {
		case Cities:
			return new CitiesDrawer();
		case CitiesPath:
			return new CitiesPathDrawer();
		}
		return null;
	}
}
