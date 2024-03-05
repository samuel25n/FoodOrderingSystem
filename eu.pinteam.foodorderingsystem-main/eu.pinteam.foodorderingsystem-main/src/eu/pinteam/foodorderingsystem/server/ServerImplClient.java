package eu.pinteam.foodorderingsystem.server;

import java.io.File;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import eu.pinteam.foodorderingsystem.management.OrderManagement;
import eu.pinteam.foodorderingsystem.management.RestaurantManagement;
import eu.pinteam.foodorderingsystem.service.IClientService;
import eu.pinteam.foodorderingsystem.shared.Customer;
import eu.pinteam.foodorderingsystem.shared.Menu;
import eu.pinteam.foodorderingsystem.shared.Order;
import eu.pinteam.foodorderingsystem.shared.Restaurant;

public class ServerImplClient implements IClientService{
	
	private OrderManagement orderManagement;
	private RestaurantManagement restaurantManagement;
	private File orderFile;
	private File restaurantsFile;
	private File allOrdersFile;
	
	public ServerImplClient() throws RemoteException {
		super();
		UnicastRemoteObject.exportObject(this, 0);
		orderManagement = new OrderManagement();
		restaurantManagement = new RestaurantManagement();
		restaurantsFile = new File("files\\restaurants.csv");
		createFileForTodayOrder();
		allOrdersFile = new File("files\\allOrders.txt");
        if(!allOrdersFile.exists()) {
        	try {
				allOrdersFile.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
	}
	
	/**
	 * This method will create the name of the order file. the file will have the
	 * current date as name (dd.MM.yyy.txt)
	 * 
	 * @return the name of the order file
	 */
	private String createFileName() {
		SimpleDateFormat f = new SimpleDateFormat("dd.MM.yyyy");
		String stringDate = f.format(new Date());
		String fileName = stringDate + ".txt";
		return fileName;
	}
	
	/**
	 * This method will create the file for today's order
	 */
	private void createFileForTodayOrder() {
		try {
			String filePath = "files\\" + createFileName();
			orderFile = new File(filePath);
			if (orderFile.createNewFile()) {
				System.out.println("File created: " + orderFile.getName());
			} else {
				System.out.println("File already exists.");
			}
		} catch (IOException e) {
			System.out.println("An error occurred while creating the orders file.");
		}
	}
	
	/**
	 * This method return a list of restaurants from the file
	 */
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
	 * This method will delete the order file
	 */
	@Override
	public void cancelOrder() throws RemoteException {

		if (orderFile != null && orderFile.exists()) {
			try {
				orderManagement.cancelOrder(orderFile);
			} catch (IOException e) {
				System.out.println("The orders file cannot be deleted, it does not exist.");
			}
		}

	}

	/**
	 * This method will add a new order to the file
	 */
	@Override
	public void addOrder(Order newOrder) throws RemoteException {
		if (newOrder != null) {
			orderManagement.addOrder(newOrder, orderFile);
		} else {
			System.out.println("The command has a null value, it cannot be added to the file.");
		}

	}

	/**
	 * This method will delete one order from the file
	 * 
	 */
	@Override
	public void deleteOneOrder(Customer customer) throws RemoteException {
		if (customer != null) {
			try {
				orderManagement.deleteOneOrder(customer, orderFile);
			} catch (IOException e) {
				System.out.println("Customer's order cannot be deleted!!!");
			}
		} else {
			System.out.println("The client has a null value");
		}

	}

	/**
	 * This method return restaurant's menu
	 */
	@Override
	public List<Menu> getListOfMenus(Restaurant restaurant) throws RemoteException {
		if (restaurant != null) {
			return restaurantManagement.getMenusForRestaurant(restaurant,restaurantsFile,"files/restaurants");
		} else {
			System.out.println("The restaurant does not exist or has no menu!!!");
			return new ArrayList<>();
		}

	}
	

	/**
	 * This method set a new order file
	 * 
	 * @param orderFile
	 */

	public void setOrderFile(File orderFile) {
		if (orderFile.exists()) {
			this.orderFile = orderFile;
		} else {
			System.out.println(orderFile.getName() + " does not exist!!!");
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
	 * This method returns file of orders.
	 * 
	 * @return restaurantsFile
	 */
	public File getOrderFile() {
		return orderFile;
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
	public void addOrderToAllOrdersFile(Order order)throws RemoteException {
		if(order!= null) {
			orderManagement.addOrder(order, allOrdersFile);
		}else {
			System.out.println("The order required details are not provided");
		}
	}





@Override
	public List<Order> getClientOrders(Customer c) throws RemoteException {
		
        return orderManagement.readOrdersFromFile(c,allOrdersFile);

	}

@Override
public void ping() throws RemoteException {
	
	RunServer runServer = new RunServer();
	if(runServer.checkIfServerIsOn()) {
		throw new RemoteException();	
	}
	
}

}
