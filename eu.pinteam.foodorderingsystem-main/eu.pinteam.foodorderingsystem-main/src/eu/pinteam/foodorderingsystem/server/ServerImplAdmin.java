package eu.pinteam.foodorderingsystem.server;

import java.io.File;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import eu.pinteam.foodorderingsystem.management.RestaurantManagement;
import eu.pinteam.foodorderingsystem.service.IAdminService;
import eu.pinteam.foodorderingsystem.shared.Menu;
import eu.pinteam.foodorderingsystem.shared.Restaurant;

public class ServerImplAdmin implements IAdminService {

	private RestaurantManagement restaurantManagement;
	private File restaurantsFile;

	public ServerImplAdmin() throws RemoteException {
		super();
		UnicastRemoteObject.exportObject(this, 0);
		restaurantManagement = new RestaurantManagement();
		restaurantsFile = new File("files\\restaurants.csv");
	}

	@Override
	public List<Restaurant> getRestaurants() throws RemoteException {

		List<Restaurant> restaurantsList;
		if (restaurantsFile.exists()) {
			restaurantsList = restaurantManagement.getRestaurantsFromFile(restaurantsFile);
			return restaurantsList;
		} else {
			System.out.println("Fișierul nu există.");
			return new ArrayList<>();
		}

	}

	/**
	 * This method add a new restaurant to the file
	 */
	@Override
	public boolean addNewRestaurant(Restaurant r) throws RemoteException {
		if (r != null && r.getName()!="") {
			return restaurantManagement.addRestaurantToFile(r, restaurantsFile,"files/restaurants/");
			
		} else {
			System.out.println("Restaurant cannot be added, received parameter is invalid!!!");
			return false;
		}

	}

	/**
	 * This method delete a restaurant in the file
	 */
	@Override
	public boolean deleteRestaurant(String restaurantName) throws RemoteException {
		if (restaurantName != null && restaurantName != "") {
			return restaurantManagement.deleteRestaurant(restaurantName, restaurantsFile);
		} else {
			System.out.println("Restaurant cannot be deleted, received parameter is invalid!!!");
			return false;
		}

	}

	/**
	 * This method set a new restaurants file
	 * 
	 * @param restaurantsFile
	 */
	public void setRestaurantsFile(File restaurantsFile) {

		if (restaurantsFile.exists()) {
			this.restaurantsFile = restaurantsFile;
		} else {
			System.err.println(restaurantsFile.getName() + " does not exist!!!");
		}

	}

	/**
	 * This method returns file of restaurants.
	 * 
	 * @return restaurantsFile
	 */
	public File getRestaurantsFile() {
		return restaurantsFile;
	}

	@Override
	public List<Menu> getListOfMenus(Restaurant restaurant) throws RemoteException {
		if (restaurant != null) {
			return restaurantManagement.getMenusForRestaurant(restaurant,restaurantsFile,"files/restaurants");
		} else {
			System.out.println("The restaurant does not exist or has no menu!!!");
			return new ArrayList<>();
		}

	}

	@Override
	public boolean deleteOrderFile(String fileName) {
		File orderFile = new File(fileName);
		if (orderFile.exists() && isValidFileName(fileName))  {
			orderFile.delete();
			return true;
		} else {
			return false;
		}
		
	}

	public boolean isValidFileName(String fileName) {
		String pattern = "^files//\\d{2}\\.\\d{2}\\.\\d{4}\\.txt$";
		Pattern regex = Pattern.compile(pattern);
		Matcher matcher = regex.matcher(fileName);
		return matcher.matches();
	}

	@Override
	public boolean  addNewMenu(String restaurant, Menu menu) throws RemoteException {

		return restaurantManagement.addNewMenuToRestaurant(restaurant, menu,restaurantsFile,"files/restaurants/");

	}

	@Override
	public boolean deleteMenu(String restaurant, String menu) throws RemoteException {
		return restaurantManagement.deleteOneMenuInRestaurant(restaurant, menu,"files/restaurants/");

	}

	@Override
	public boolean checkIfRestaurantExist(String restaurantName) throws RemoteException {
		if (restaurantName != null && restaurantName != "")
			return restaurantManagement.checkIfRestaurantExist(restaurantName,restaurantsFile);
		else {
			return false;
		}
	}
}
