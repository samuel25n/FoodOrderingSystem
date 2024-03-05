package eu.pinteam.foodorderingsystem.service;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import eu.pinteam.foodorderingsystem.shared.Customer;
import eu.pinteam.foodorderingsystem.shared.Menu;
import eu.pinteam.foodorderingsystem.shared.Order;
import eu.pinteam.foodorderingsystem.shared.Restaurant;

/**
 * This interface contains all the methods 
 * that will be called by the client
 */

public interface IClientService extends Remote{
	
	/**
	 * @return Returns the list of restaurants you can order from
	 * @throws RemoteException
	 */
	public List<Restaurant> getRestaurants() throws RemoteException;
	
	/**
	 * If the customer calls this method the file will be deleted
	 * @throws RemoteException
	 */
	
	/*
	 * If the server is down the method will throw an exception
	 */
	public void ping() throws RemoteException;
	
	public void cancelOrder() throws RemoteException;
	
	/**
	 * @param newOrder is an order that will be added to the file
	 * @throws RemoteException
	 */
	public void addOrder(Order newOrder) throws RemoteException;
	
	/**
	 * This method will delete an order depending on the customer
	 * @param customer
	 * @throws RemoteException
	 */
	public void deleteOneOrder(Customer customer) throws RemoteException;
	
	/**
	 * This method will return a list of menus from a specific restaurant.
	 * @param restaurant
	 * @return list of menus
	 * @throws RemoteException
	 */
	public List<Menu> getListOfMenus(Restaurant restaurant) throws RemoteException;

	List<Order> getClientOrders(Customer c) throws RemoteException;

	void addOrderToAllOrdersFile(Order order) throws RemoteException;
	
	

}
