package eu.pinteam.rcpfos.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swtbot.eclipse.finder.SWTWorkbenchBot;
import org.eclipse.swtbot.swt.finder.finders.UIThreadRunnable;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotButton;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotLabel;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotShell;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.junit.Test;

import eu.pinteam.rcpfos.wizard.addRestaurant.RestaurantDetailsPage;

public class AddRestaurantWidgetUITest {
	private final SWTWorkbenchBot bot = new SWTWorkbenchBot();
	public static final String ID = "foss.admin";
	@Test
	/**
	 * This test checks if the Add New Restaurant wizard is working as
	 * expected.
	 * 
	 */
	public void testAddRestaurantFinishWidget() {
		UIThreadRunnable.syncExec(bot.getDisplay(), () -> {
			IWorkbenchWindow activeWindow = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
			assertNotNull(activeWindow);
			try {
				activeWindow.getActivePage().showView(ID);
			} catch (PartInitException ex) {
				System.out.println(ex);
			}
		});
		//open the wizard
		bot.menu("Add new Restaurant").menu("Add Restaurant").click();

		//check if the wizard is visible
		assertTrue(bot.label("Enter Restaurant Details").isVisible());

		//check if the "Finish" button is not enabled(we didn't enter any restauarant or phone number)
		assertFalse(bot.button("Finish").isEnabled());
		
		//check if the "Cancel" button is enabled
		assertTrue(bot.button("Cancel").isEnabled());
		
		//add restaurant and phone
		bot.textWithLabel("Name:").setText("Vasile");
		bot.textWithLabel("Phone:").setText("0754323673");
		bot.sleep(1000);		//click "Finish" button
		bot.button("Finish").click();
		bot.sleep(1000);
		//click confirmation button
		bot.button("OK").click();

	}

	@Test
	/**
	 * this test checks if the wizard is closed after the "close" button is clicked
	 */
	public void testAddRestaurantCancelWidget() {
		//open the wizard
		bot.menu("Add new Restaurant").menu("Add Restaurant").click();

		//check if the wizard is visible
		assertTrue(bot.label("Enter Restaurant Details").isVisible());

		//click the cancel button
		bot.button("Cancel").click();

		//check if the Eclipse IDE is active and the add new restaurant wizard is closed
		assertEquals("junit-workspace - Eclipse IDE", bot.activeShell().getText());

	}
	
	@Test
	/**
	 * This test checks if test phone validation is working as
	 * expected.
	 * 
	 */
	public void testPhoneValidation() {

	    //open wizard
	    bot.menu("Add new Restaurant").menu("Add Restaurant").click();

	    // Set an invalid phone number
	    bot.textWithLabel("Phone:").setText("12345");

	    //check if the "Finish" button is not enabled
	    assertFalse(bot.button("Finish").isEnabled());

	    //check if the "Cancel" button is enabled
	    assertTrue(bot.button("Cancel").isEnabled());
	    
	  //click the cancel button
	    bot.button("Cancel").click();
	    
	  //check if the Eclipse IDE is active and the add new restaurant wizard is closed
	  	assertEquals("junit-workspace - Eclipse IDE", bot.activeShell().getText());
	}
	
	@Test
	/**
	 * This test checks if test name validation wizard is working as
	 * expected.
	 * 
	 */
	public void testNameValidation() {

	    //open wizard
	    bot.menu("Add new Restaurant").menu("Add Restaurant").click();

	    // Set an invalid name
	    bot.textWithLabel("Name:").setText("!");

	    //check the "Finish" button is not enabled
	    assertFalse(bot.button("Finish").isEnabled());

	    //check the "Cancel" button is enabled
	    assertTrue(bot.button("Cancel").isEnabled());
	    
	  //click the cancel button
	  	bot.button("Cancel").click();
	  	
	    //check if the Eclipse IDE is active and the add new restaurant wizard is closed
	  	assertEquals("junit-workspace - Eclipse IDE", bot.activeShell().getText());
	}



}
