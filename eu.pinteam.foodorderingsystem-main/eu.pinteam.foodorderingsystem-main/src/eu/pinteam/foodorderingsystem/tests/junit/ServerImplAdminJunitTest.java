package eu.pinteam.foodorderingsystem.tests.junit;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import eu.pinteam.foodorderingsystem.management.RestaurantManagement;
import eu.pinteam.foodorderingsystem.server.ServerImplAdmin;
import eu.pinteam.foodorderingsystem.shared.Menu;
import eu.pinteam.foodorderingsystem.shared.Restaurant;

public class ServerImplAdminJunitTest {

	private File restaurantsFile;
	private ServerImplAdmin serverImplAdmin;

	@Before
	public void test() throws RemoteException {
		serverImplAdmin = new ServerImplAdmin();
		try {
			restaurantsFile = createTempRestaurantsFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		serverImplAdmin.setRestaurantsFile(restaurantsFile);
	}

	/**
	 * This method tests if addNewRestaurant method add the new restaurant to the
	 * file
	 * 
	 * @throws RemoteException
	 */
	@Test
	public void test1AddNewRestaurant() throws RemoteException {
		Restaurant newRestaurant = createTestRestaurant();
		serverImplAdmin.addNewRestaurant(newRestaurant);
		List<String> restaurantsList;
		try {

			restaurantsList = readRestaurantFileLines();
			String objectRepresentation = newRestaurant.getName() + "," + newRestaurant.getPhone();

			boolean isObjectAdded = restaurantsList.stream().anyMatch(line -> line.contains(objectRepresentation));
			assertTrue(isObjectAdded);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * This method tests if addNewRestaurant method does not add the new restaurant
	 * to the file
	 * 
	 * @throws RemoteException
	 */
	@Test
	public void test2AddNewRestaurant() throws RemoteException {
		Restaurant newRestaurant = null;
		serverImplAdmin.addNewRestaurant(newRestaurant);
		List<String> restaurantsList;
		try {
			// read file content
			restaurantsList = readRestaurantFileLines();
			Assert.assertFalse("Null value was added to the file.",
					restaurantsList.contains(String.valueOf(newRestaurant)));
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Test
	public void testGetRestaurantsFile() throws IOException {
		Restaurant newRestaurant = createTestRestaurant();
		serverImplAdmin.addNewRestaurant(newRestaurant);
		List<Restaurant> restaurants = serverImplAdmin.getRestaurants();
		for (Restaurant restaurant : restaurants) {
			System.out.println(restaurant);
		}
		assertFalse("The list of restaurants should not be empty.", restaurants.isEmpty());

	}

	/**
	 * valid name
	 * 
	 * @throws IOException
	 */
	@Test
	public void testDeleteValidRestaurant1() throws IOException {
		Restaurant newRestaurant = createTestRestaurant();
		serverImplAdmin.addNewRestaurant(newRestaurant);
		boolean result = serverImplAdmin.deleteRestaurant("Restaurant");

		assertTrue(result);

	}

	/**
	 * invalid name
	 * 
	 * @throws IOException
	 */
	@Test
	public void testDeleteRestaurant2() throws IOException {
		String restaurantName = null;

		boolean result = serverImplAdmin.deleteRestaurant(restaurantName);

		assertFalse(result);

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
	 * This method create a test restaurant
	 * 
	 * @return
	 */
	private Restaurant createTestRestaurant() {
		List<Menu> m = new ArrayList<>();
		m.add(new Menu("supa", "poza", 45.7));
		m.add(new Menu("ciorba", "poza", 45.7));
		return new Restaurant("Restaurant", "123456789", m);
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
		restaurantsFile.delete();
	}

}
