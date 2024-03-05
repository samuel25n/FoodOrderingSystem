package eu.pinteam.foodorderingsystem.tests.junit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import eu.pinteam.foodorderingsystem.management.OrderManagement;
import eu.pinteam.foodorderingsystem.shared.Customer;
import eu.pinteam.foodorderingsystem.shared.Menu;
import eu.pinteam.foodorderingsystem.shared.Order;
import eu.pinteam.foodorderingsystem.shared.Restaurant;

public class OrderManagmentJunitTest {

	File file;
	OrderManagement orderManagement = new OrderManagement();
	Restaurant restaurant = new Restaurant("KFC", "1234", null);
	Customer customer = new Customer("nume");
	Menu menu = new Menu("Crispy", "path", (double) 25);
	Order order = new Order(customer, menu, restaurant);

	
	//this method will be called before any method testing 
	@Before
	public void Before() throws IOException {
		file = new File("testare.txt");
			file.createNewFile();
			
		assertTrue(file.exists());

	}
	// this method will be called after any method testing
	@After
	public void tearDown() {
		if (file.exists()) {
			file.delete();
		}
	}
	// this method will test if we can delete the order file if we cancel the order
	@Test
	public void cancelOrderTest() throws IOException {
		// Delete the file
		orderManagement.cancelOrder(file);
		
		assertFalse("File was not deleted",file.exists());
	}
	
	@Test
	public void readOrdersFromFile() throws IOException {
		addOrderTest();
		List<Order> orderList = orderManagement.readOrdersFromFile(customer, file);
		assertEquals(orderList.get(0).getMenu().getName(), menu.getName());
		assertEquals(orderList.get(0).getCustomer().getName(), customer.getName());
		assertEquals(orderList.get(0).getRestaurant().getName(), restaurant.getName());
		assertEquals(orderList.get(0).getRestaurant().getPhone(), restaurant.getPhone());
		
	}
	
	
	
	// this method will test if we can add an order to the file 
	@Test
	public void addOrderTest() throws IOException {
		orderManagement.addOrder(order, file);
		file.exists();
		String fileContent = Files.readString(Path.of(file.getAbsolutePath()));
		assertTrue(fileContent.contains(order.toString()));
	}
	// this method will test if we can delete one order by a costumer from a specific fil e
	@Test
	public void deleteOrderTest() throws IOException {
		assertTrue(file.exists());
		orderManagement.deleteOneOrder(customer, file);
		String fileContent = Files.readString(Path.of(file.getPath()));
		assertFalse(fileContent.contains(order.toString()));
	}

}
