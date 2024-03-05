package eu.pinteam.foodorderingsystem.tests.junit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import eu.pinteam.foodorderingsystem.shared.Menu;

public class MenuJunitTest {

	private static final String MENU1 = "CRISPY STRIPS";
	private static final String MENU2 = "ZINGER MENU";
	private static final String PIC1 = "src\\\\eu\\\\pinteam\\\\foodorderingsystem\\\\images\\\\ruth.piza.jpg";
	private static final String PIC2 = "src\\\\eu\\\\pinteam\\\\foodorderingsystem\\\\images\\\\kfc.jpg";
	private static final Double PRICE1 = (double)5;
	private static final Double PRICE2 = (double)27;
	
	Menu menu;
	
	@Before
	public void setUpBeforeClass() throws Exception {
		
		menu=new Menu(MENU1,PIC1,PRICE1);
				
	}

	@Test
	public void testMenuSettersGettersWork() {

		//assert that the menu exist
		assertNotNull(menu);
		
		//assert that the name of the menu is CRISPY STRIPS
		assertEquals(menu.getName(), MENU1);
		
		//assert that the image path of the menu is correct
		assertEquals(menu.getPicture(), PIC1);
		
		//assert that the price of the menu is 5
		assertEquals(menu.getPrice(),PRICE1);
		
		//assert that the setter for name works
        menu.setName(MENU2);
        assertEquals(menu.getName(), MENU2);
        
        //assert that the setter for path works
        menu.setPicture(PIC2);
        assertEquals(menu.getPicture(), PIC2);
        
       //assert that the setter for price works 
       menu.setPrice(PRICE2);
       assertEquals(menu.getPrice(), PRICE2);
	}
	
	@Test
	public void testMenuToStringWorks() {
		//assert that the output is correct
		assertEquals(menu.toString(), menu.getName());
	}


}
