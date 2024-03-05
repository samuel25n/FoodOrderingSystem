package eu.pinteam.foodorderingsystem.service;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import eu.pinteam.foodorderingsystem.shared.Menu;
import eu.pinteam.foodorderingsystem.shared.Restaurant;

/**
 * This interface contains all the methods 
 * that will be called by the administrator
 */
public interface IAdminService extends Remote{
	
	/**
	 * This methos add a new restaurant to the file
	 * @param r - the new restaurant that will be added
	 * @throws RemoteException
	 */
	public boolean addNewRestaurant(Restaurant r) throws RemoteException;
	
	/**
	 * This method will delete a restaurant
	 * @param r - the restaurant that will be deleted
	 * @throws RemoteException
	 */
	public boolean deleteRestaurant(String restaurantName) throws RemoteException;
	

	public List<Restaurant> getRestaurants() throws RemoteException;
	
	public boolean deleteOrderFile(String fileName) throws RemoteException;

	List<Menu> getListOfMenus(Restaurant restaurant) throws RemoteException;
	
	public boolean addNewMenu(String restaurant, Menu menu) throws RemoteException;
	
	public boolean deleteMenu(String restaurant, String menu) throws RemoteException;
	
	public boolean checkIfRestaurantExist(String restaurantName) throws RemoteException;
}
