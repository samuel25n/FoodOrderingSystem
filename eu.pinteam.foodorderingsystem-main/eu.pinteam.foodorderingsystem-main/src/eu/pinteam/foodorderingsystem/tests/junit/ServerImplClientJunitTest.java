package eu.pinteam.foodorderingsystem.tests.junit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import eu.pinteam.foodorderingsystem.management.OrderManagement;
import eu.pinteam.foodorderingsystem.management.RestaurantManagement;
import eu.pinteam.foodorderingsystem.server.ServerImplClient;
import eu.pinteam.foodorderingsystem.shared.Customer;
import eu.pinteam.foodorderingsystem.shared.Menu;
import eu.pinteam.foodorderingsystem.shared.Order;
import eu.pinteam.foodorderingsystem.shared.Restaurant;

public class ServerImplClientJunitTest {

	private File restaurantsFile;
	private File orderFile;
	private ServerImplClient clientServer;

	@Before
	public void start_test() throws RemoteException {
		clientServer = new ServerImplClient();

		try {
			orderFile = createTempOrderFile();
			restaurantsFile = createTempRestaurantsFile();
		} catch (IOException e) {
			System.out.println("can't create temporary orders file");
		}

		clientServer.setOrderFile(orderFile);
		clientServer.setRestaurantsFile(restaurantsFile);
	}

	/**
	 * This method tests if getRestaurants method returns the restaurants list
	 * correctly
	 * 
	 * @throws RemoteException
	 */
	@Test
	public void test1GetRestaurants() throws RemoteException {
		// Populate file with restaurants
		try {
			writeRestaurantsToFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// Create list of expected restaurnts
		List<Restaurant> expectedRestaurants = new ArrayList<>();
		expectedRestaurants.add(new Restaurant("mere", "96874986", new ArrayList<>()));
		expectedRestaurants.add(new Restaurant("ava", "123", new ArrayList<>()));
		expectedRestaurants.add(new Restaurant("husi", "535436", new ArrayList<>()));

		// call getRestaurants() method to receive the list of restaurants from the file
		List<Restaurant> actualRestaurants = clientServer.getRestaurants();

		assertNotNull(actualRestaurants);
		assertEquals(expectedRestaurants.size(), actualRestaurants.size());

		for (int i = 0; i < expectedRestaurants.size(); i++) {
			Restaurant expectedRestaurant = expectedRestaurants.get(i);
			Restaurant actualRestaurant = actualRestaurants.get(i);
			assertEquals(expectedRestaurant.getName(), actualRestaurant.getName());
			assertEquals(expectedRestaurant.getPhone(), actualRestaurant.getPhone());

		}
	}

	/**
	 * This method checks if getListOfMenus() method returns correctly a restaurant
	 * list
	 * 
	 * @throws RemoteException
	 */
	@Test
	public void test1GetListOfMenus() throws RemoteException {

		Restaurant restaurant = createTestRestaurant();

		List<Menu> menus = clientServer.getListOfMenus(restaurant);
		assertNotNull(menus);

	}

	/**
	 * This method tests if an order is added to the file
	 * 
	 * @throws RemoteException
	 */
	@Test
	public void test1AddOrder() throws RemoteException {
		Order newOrder = createTest1Order();

		clientServer.addOrder(newOrder);

		List<String> orderLines;
		try {

			orderLines = readOrderFileLines();

			assertTrue(orderLines.contains(newOrder.toString()));
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * This method tests if a null order is not added to the file
	 * 
	 * @throws RemoteException
	 */
	@Test
	public void test2AddOrder() throws RemoteException {

		Order newOrder = null;

		clientServer.addOrder(newOrder);

		try {

			assertTrue(readOrderFileLines().isEmpty());
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * This method tests if an order is deleted when the customer name exists in the
	 * file
	 * 
	 * @throws RemoteException
	 */

	@Test
	public void test1DeleteOneOrder() throws RemoteException {
		try {
			writeOrdersToFile();
		} catch (IOException e) {
			e.printStackTrace();
		}

		List<String> orderLinesBeforeDeletion;
		try {
			orderLinesBeforeDeletion = readOrderFileLines();
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}

		Customer customer = new Customer("John Doe");
		clientServer.deleteOneOrder(customer);

		List<String> orderLinesAfterDeletion;
		try {
			orderLinesAfterDeletion = readOrderFileLines();
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}

		assertEquals(orderLinesBeforeDeletion.size() - 1, orderLinesAfterDeletion.size());

		assertFalse(
				orderLinesAfterDeletion.contains("Order [customer=John Doe, menu=Menu 1, restaurant=Restaurant 1]"));
	}

	/**
	 * This method tests if an order is not deleted when the customer name does not
	 * exists in the file or the file is empty
	 * 
	 * @throws RemoteException
	 */
	@Test
	public void test2DeleteOneOrder() throws RemoteException {
		try {
			writeOrdersToFile();
		} catch (IOException e) {
			e.printStackTrace();
		}

		List<String> orderLinesBeforeDeletion;
		try {
			orderLinesBeforeDeletion = readOrderFileLines();
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}

		Customer customer = new Customer("Ionel duta");
		clientServer.deleteOneOrder(customer);

		List<String> orderLinesAfterDeletion;
		try {
			orderLinesAfterDeletion = readOrderFileLines();
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}

		assertEquals(orderLinesBeforeDeletion.size(), orderLinesAfterDeletion.size());

	}

	/**
	 * This method tests what happens if we want to delete an order when the
	 * customer is null
	 * 
	 * @throws RemoteException
	 */
	@Test
	public void test3DeleteOneOrder() throws RemoteException {
		try {
			writeOrdersToFile();
		} catch (IOException e) {
			e.printStackTrace();
		}

		List<String> orderLinesBeforeDeletion;
		try {
			orderLinesBeforeDeletion = readOrderFileLines();
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}

		Customer customer = null;
		clientServer.deleteOneOrder(customer);

		List<String> orderLinesAfterDeletion;
		try {
			orderLinesAfterDeletion = readOrderFileLines();
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}

		assertEquals(orderLinesBeforeDeletion.size(), orderLinesAfterDeletion.size());
	}

	/**
	 * This method tests if a file is deleted when the cancel order is called
	 * 
	 * @throws RemoteException
	 */
	@Test
	public void test1CancelOrder() throws RemoteException {
		try {
			writeOrdersToFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		clientServer.cancelOrder();

		assertFalse(orderFile.exists());

	}

	private List<String> readOrderFileLines() throws IOException {
		return Files.readAllLines(Paths.get(orderFile.getAbsolutePath()));
	}

	private Order createTest1Order() {
		Customer customer = new Customer("John Doe");
		Restaurant restaurant = new Restaurant("Restaurant 1", "123456789", new ArrayList<>());
		Menu menu = new Menu("Menu 1", "path", 10.99);
		return new Order(customer, menu, restaurant);
	}

	/**
	 * This method creates a temporary restaurants file
	 * 
	 * @return
	 * @throws IOException
	 */
	private File createTempRestaurantsFile() throws IOException {
		File tempFile = File.createTempFile("restaurants", ".csv");
		tempFile.deleteOnExit();
		return tempFile;
	}

	/**
	 * This method creates a temporary order file
	 * 
	 * @return
	 * @throws IOException
	 */
	private File createTempOrderFile() throws IOException {
		File tempFile = File.createTempFile("orders", ".txt");
		tempFile.deleteOnExit();
		return tempFile;
	}

	private void writeOrdersToFile() throws IOException {
		OrderManagement orderManagement = new OrderManagement();
		Order o1 = new Order(new Customer("John Doe"), new Menu("Menu 1", "path", 12.995),
				new Restaurant("Restaurant 1", "123456789", new ArrayList<>()));
		Order o2 = new Order(new Customer("Gigi pepe"), new Menu("Menu 2", "path", 10.99),
				new Restaurant("Restaurant 2", "343123456789", new ArrayList<>()));
		orderManagement.addOrder(o1, orderFile);
		orderManagement.addOrder(o2, orderFile);

	}

	/**
	 * This method create a test restaurant
	 * 
	 * @return
	 */
	private Restaurant createTestRestaurant() {
		List<Menu> m = new ArrayList<>();
		m.add(new Menu("supa", "poza", 45.7));
		m.add(new Menu("ciorba", "poza", 45.7));
		return new Restaurant("Restaurant 1", "123456789", m);
	}

	/**
	 * This method writes restaurants in the file
	 * 
	 * @throws IOException
	 */
	private void writeRestaurantsToFile() throws IOException {
		RestaurantManagement restaurantManagement = new RestaurantManagement();
		restaurantManagement.addRestaurantToFile(new Restaurant("mere", "96874986", null), restaurantsFile, "");
		restaurantManagement.addRestaurantToFile(new Restaurant("ava", "123", null), restaurantsFile, "");
		restaurantManagement.addRestaurantToFile(new Restaurant("husi", "535436", null), restaurantsFile, "");
	}

	/**
	 * This method reads the file lines
	 * 
	 * @return
	 * @throws IOException
	 */
	private List<String> readRestaurantFileLines() throws IOException {
		return Files.readAllLines(Paths.get(restaurantsFile.getAbsolutePath()));
	}

	@After
	public void tearDown() {
		orderFile.delete();
		restaurantsFile.delete();
	}

}
