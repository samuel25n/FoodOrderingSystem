package eu.pinteam.foodorderingsystem.tests.junit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import eu.pinteam.foodorderingsystem.shared.Customer;

public class CustomerJunitTest {

	private static final String CLIENT1 = "CLIENT1";
	private static final String CLIENT2 = "CLIENT2";
	
	Customer customer;
	
	@Before
	public void setUpBeforeClass() throws Exception {
		customer = new Customer(CLIENT1);
				
	}
	
	@Test
	public void testGettersAndSetters() {
		
		//assert that the customer exist
		assertNotNull(customer);
		
		//assert that the name of the customer is CUSTOMER1
		assertEquals(customer.getName(), CLIENT1);
		
		//assert that the setter for name works
		customer.setName(CLIENT2);
		assertEquals(customer.getName(), CLIENT2);
			
	}
	
	@Test
	public void testToString() {
		
		//assert that the output is correct
		assertEquals(customer.toString(),"Customer [id=" + customer.getId() + ", name=" + customer.getName() + "]");
	}

}
