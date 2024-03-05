package eu.pinteam.foodorderingsystem.tests.junit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import eu.pinteam.foodorderingsystem.management.RestaurantManagement;
import eu.pinteam.foodorderingsystem.shared.Menu;
import eu.pinteam.foodorderingsystem.shared.Restaurant;

public class RestaurantManagementTest {

	private File restaurantsFile;
	private RestaurantManagement restaurantManagement;

	@Before
	public void start_test() throws RemoteException {
		restaurantManagement = new RestaurantManagement();

		createTempRestaurantsFile();

	}

	/**
	 * empty restaurants file
	 */
	@Test
	public void testGetRestaurantsFromFile1() {

		List<Restaurant> restaurants = restaurantManagement.getRestaurantsFromFile(restaurantsFile);
		assertTrue(restaurants.isEmpty());

	}

	/**
	 * Non empty restaurants file
	 */
	@Test
	public void testGetRestaurantsFromFile2() {
		addRestaurantsToFile();
		List<Restaurant> restaurants = restaurantManagement.getRestaurantsFromFile(restaurantsFile);

		assertFalse(restaurants.isEmpty());
		assertEquals(2, restaurants.size());

		Restaurant restaurant1 = restaurants.get(0);
		assertEquals("KFC", restaurant1.getName());
		assertEquals("098", restaurant1.getPhone());
		Restaurant restaurant2 = restaurants.get(1);
		assertEquals("Corso dio", restaurant2.getName());
		assertEquals("134", restaurant2.getPhone());
	}

	/**
	 * case1: add a valid restaurant
	 */
	@Test
	public void testAddRestaurantToFile1() {
		List<Menu> menus = new ArrayList<>();
		menus.add(new Menu("MENU", "picture", (double) 42));
		menus.add(new Menu("MENUU", "picture2", (double) 12));
		Restaurant r1 = new Restaurant("R", "5424", menus);

		restaurantManagement.addRestaurantToFile(r1, restaurantsFile, "");
		List<String> restaurantsList;
		try {
			restaurantsList = readRestaurantsFileLines();
			String objectRepresentation = r1.getName() + "," + r1.getPhone();
			boolean isObjectAdded = restaurantsList.stream().anyMatch(line -> line.contains(objectRepresentation));
			assertTrue("Restaurantul a fost adaugat in lista de restaurante!.", isObjectAdded);
		} catch (IOException e) {
			e.printStackTrace();
		}

		String filePath = "files/restaurants/" + r1.getName() + ".csv";
		File newRestaurantFile = new File(filePath);
		newRestaurantFile.delete();
	}

	/**
	 * case2:Add non valid restaurant
	 */
	@Test
	public void testAddRestaurantToFile2() {
		List<Menu> menus = new ArrayList<>();
		menus.add(new Menu("MENU", "picture", (double) 42));
		menus.add(new Menu("MENUU", "picture2", (double) 12));
		Restaurant r1 = new Restaurant("MC1", "5424", menus);

		boolean ok = restaurantManagement.addRestaurantToFile(r1, restaurantsFile, "");

		List<String> restaurantsList;
		try {

			restaurantsList = readRestaurantsFileLines();
			
			String objectRepresentation = r1.getName() + "," + r1.getPhone() + "\n";
			boolean isObjectAdded = restaurantsList.stream().anyMatch(line -> line.contains(objectRepresentation));

			assertFalse("Restaurantul nu a fost adăugat în fișierul CSV.", isObjectAdded);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * menus for existing restaurant
	 */
	@Test
	public void testGetMenusFromRestaurant1() {
		List<Menu> menus = new ArrayList<>();
		menus.add(new Menu("MENU", "picture", (double) 42));
		menus.add(new Menu("MENUU", "picture2", (double) 12));
		Restaurant r1 = new Restaurant("MC", "5424", menus);

		boolean ok = restaurantManagement.addRestaurantToFile(r1, restaurantsFile, "");
		List<Menu> menusEx = restaurantManagement.getMenusForRestaurant(r1, restaurantsFile, ".");
		
		assertTrue(menusEx.size() > 0);
	}

	/**
	 * delete a valid restaurant
	 * 
	 * @throws IOException
	 */
	@Test
	public void testDeleteRestaurantInFile1() throws IOException {
		List<Menu> menus = new ArrayList<>();
		menus.add(new Menu("MENU", "picture", (double) 42));
		menus.add(new Menu("MENUU", "picture2", (double) 12));
		Restaurant r1 = new Restaurant("s", "5424", menus);

		restaurantManagement.addRestaurantToFile(r1, restaurantsFile, "");
		boolean ok = restaurantManagement.deleteRestaurant("s", restaurantsFile);
		assertTrue(ok);

		List<String> restaurantsList;
		try {

			restaurantsList = readRestaurantsFileLines();
		
			String restaurantLine = r1.getName() + "," + r1.getPhone() + "\n";
			boolean deleted = restaurantsList.stream().anyMatch(line -> line.contains(restaurantLine));

			assertFalse("Restaurantul nu mai exista in fiserul de restaurante!", deleted);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * delete a non valid restaurant
	 * 
	 * @throws IOException
	 */
	@Test
	public void testDeleteRestaurantInFile2() throws IOException {
		List<Menu> menus = new ArrayList<>();
		menus.add(new Menu("MENU", "picture", (double) 42));
		menus.add(new Menu("MENUU", "picture2", (double) 12));
		Restaurant r1 = new Restaurant("pu", "5424", menus);

		boolean ok = restaurantManagement.deleteRestaurant("pu", restaurantsFile);
		assertFalse(ok);

	}

	/**
	 * menu exist
	 */
	@Test
	public void testDeleteOneMenuInRestaurant1() {
		List<Menu> menus = new ArrayList<>();
		menus.add(new Menu("MENU", "picture", (double) 42));
		menus.add(new Menu("MENUU", "picture2", (double) 12));
		Restaurant r1 = new Restaurant("s", "5424", menus);

		restaurantManagement.addRestaurantToFile(r1, restaurantsFile, "");
		boolean ok = restaurantManagement.deleteOneMenuInRestaurant("s", "menu", "");
		assertTrue(ok);
	}

	/**
	 * menu does not exist
	 */
	@Test
	public void testDeleteOneMenuInRestaurant2() {

		boolean ok = restaurantManagement.deleteOneMenuInRestaurant("o", "menu", "");
		assertFalse(ok);
	}

	/**
	 * add new menu to existing restaurant
	 * 
	 * @throws IOException
	 */
	@Test
	public void testAddNewMenuToRestaurant1() throws IOException {
		List<Menu> menus = new ArrayList<>();
		menus.add(new Menu("MENU", "picture", (double) 42));
		menus.add(new Menu("MENUU", "picture2", (double) 12));
		Restaurant r1 = new Restaurant("s", "5424", menus);

		restaurantManagement.addRestaurantToFile(r1, restaurantsFile, "");

		Menu newMenu = new Menu("Mar", "Picture2", 10.99);

		boolean result = restaurantManagement.addNewMenuToRestaurant("s", newMenu, restaurantsFile, "");

		assertTrue(result);
		File sfile = new File("s.csv");
		List<String> fileLines = Files.readAllLines(sfile.toPath());
		assertTrue(fileLines.contains("Mar,Picture2,10.99"));

	}

	/**
	 * add invalid menu name to existing restaurant
	 * 
	 * @throws IOException
	 */
	@Test
	public void testAddNewMenuToRestaurant2() throws IOException {
		List<Menu> menus = new ArrayList<>();
		menus.add(new Menu("MENU", "picture", (double) 42));
		menus.add(new Menu("MENUU", "picture2", (double) 12));
		Restaurant r1 = new Restaurant("s", "5424", menus);

		restaurantManagement.addRestaurantToFile(r1, restaurantsFile, "");

		Menu newMenu = new Menu("MENU3", "Picture2", 10.99);

		boolean result = restaurantManagement.addNewMenuToRestaurant("s", newMenu, restaurantsFile, "");

		assertFalse(result);

	}

	/**
	 * restaurant does not exist
	 * 
	 * @throws IOException
	 */
	@Test
	public void testAddNewMenuToRestaurant3() throws IOException {

		Menu newMenu = new Menu("MENU3", "Picture2", 10.99);

		boolean result = restaurantManagement.addNewMenuToRestaurant("l", newMenu, restaurantsFile, "");

		assertFalse(result);

	}

	/**
	 * This method creates a temporary restaurants file
	 * 
	 * @return
	 * @throws IOException
	 */
	private void createTempRestaurantsFile() {
		try {
			restaurantsFile = File.createTempFile("restaurantsFile", ".csv");
		} catch (IOException e) {
			e.printStackTrace();
		}
		restaurantsFile.deleteOnExit();
	}

	private void addRestaurantsToFile() {
		try (FileWriter writer = new FileWriter(restaurantsFile)) {
			writer.write("KFC,098\n");
			writer.write("Corso dio,134\n");
		} catch (IOException e) {
			System.out.println("Failed to write data to file: " + e.getMessage());
		}

	}

	private List<String> readRestaurantsFileLines() throws IOException {
		return Files.readAllLines(Paths.get(restaurantsFile.getAbsolutePath()));
	}

	@After
	public void tearDown() {
		restaurantsFile.delete();
		String folderPath = ".";

		File folder = new File(folderPath);

		if (folder.isDirectory()) {
			File[] files = folder.listFiles();

			for (File file : files) {
				if (file.isFile() && file.getName().endsWith(".csv")) {
					if (file.delete()) {
						System.out.println("Fișierul " + file.getName() + " a fost șters.");
					} else {
						System.out.println("Nu s-a putut șterge fișierul " + file.getName());
					}
				}
			}
		} else {
			System.out.println("Calea specificată nu corespunde unui director valid.");
		}
	}
}
