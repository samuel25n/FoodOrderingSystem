package eu.pinteam.foodorderingsystem.tests.junit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import eu.pinteam.foodorderingsystem.shared.Menu;
import eu.pinteam.foodorderingsystem.shared.Restaurant;

public class RestaurantJunitTest {

	private static final String KFC = "KFC";
	private static final String MAC = "MAC";
	private static final String PHONE = "744745544";
	private static final String NPHONE = "744745540";
	
	Restaurant restaurant;
	List<Menu>menuList = new ArrayList<>();
	List<Menu>newMenuList = new ArrayList<>();
	
	@Before
	public void setUpBeforeClass() throws Exception {
		menuList.add(new Menu("Zinger Menu","src\\\\eu\\\\pinteam\\\\foodorderingsystem\\\\images\\\\kfc.jpg",(double)27));
		menuList.add(new Menu("Crispy Strips (Radu's favorite)","src\\\\eu\\\\pinteam\\\\foodorderingsystem\\\\images\\\\ruth.piza.jpg",(double)5));
		restaurant = new Restaurant(KFC,PHONE,menuList);
		newMenuList.add(new Menu("name","path",(double)10));
				
	}

	@Test
	public void testRestaurantSettersGettersWork() {

		//assert that the restaurant exist
		assertNotNull(restaurant);
		
		//assert that the name of the restaurant is KFC
		assertEquals(restaurant.getName(), KFC);
		
		//assert that the phone of the restaurant is PHONE
		assertEquals(restaurant.getPhone(), PHONE);
		
		//assert that the menu list of the restaurant is menuList
		assertEquals(restaurant.getMenuList(),menuList);
		
		//assert that the setter for name works
        restaurant.setName(MAC);
        assertEquals(restaurant.getName(), MAC);
        
        //assert that the setter for phone works
        restaurant.setPhone(NPHONE);
        assertEquals(restaurant.getPhone(), NPHONE);
        
       //assert that the setter for list works 
       restaurant.setMenuList(newMenuList);
       assertEquals(restaurant.getMenuList(), newMenuList);
	}
	
	@Test
	public void testRestaurantToStringWorks() {
		//assert that the output is correct
		assertEquals(restaurant.toString(), restaurant.getName());
	}

}
