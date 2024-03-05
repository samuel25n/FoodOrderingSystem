package eu.pinteam.foodorderingsystem.tests.junit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import eu.pinteam.foodorderingsystem.shared.Customer;
import eu.pinteam.foodorderingsystem.shared.Menu;
import eu.pinteam.foodorderingsystem.shared.Order;
import eu.pinteam.foodorderingsystem.shared.Restaurant;

public class OrderJunitTest {

	Order order;
	Customer customer;
	Menu menu;
	Restaurant restaurant;
	List<Menu> menuList = new ArrayList<>();
	
	@Before
	public void setUpBeforeClass() throws Exception {
		
		customer = new Customer("Client1");
		menu = new Menu("Crispy Strips","src\\\\eu\\\\pinteam\\\\foodorderingsystem\\\\images\\\\ruth.piza.jpg",(double)5);
		menuList.add(menu);
		restaurant = new Restaurant("KFC", "744745544", menuList);	
		order = new Order(customer, menu, restaurant);
	}
	
	@Test
	public void testOrderGettersWork() {
		
		//assert that the order exist
		assertNotNull(order);
		
		//assert that the customer of the order is correct
		assertEquals(order.getCustomer(),customer);
		
		//assert that the menu of the order is correct
		assertEquals(order.getMenu(),menu);
		
		//assert that the restaurant of the order is correct
		assertEquals(order.getRestaurant(),restaurant);
	}
	
	@Test
	public void testOrderToStringWorks() {
		
		//assert that the output is correct
		assertEquals(order.toString(),customer.getName() + "," + menu.getName() + "," + restaurant.getName() +" "+ restaurant.getPhone());
	}
	
	@Test
	public void testMenuBelongsRestaurant() {
		
		boolean ok=false;
		for(Menu m : restaurant.getMenuList()) {
			if(m.getName().equals(menu.getName())) ok=true;
		}
		
		//assert that the menu belongs to the restaurant
		assertEquals(ok, true);
	}

}
