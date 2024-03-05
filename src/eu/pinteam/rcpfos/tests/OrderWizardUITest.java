package eu.pinteam.rcpfos.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.eclipse.swtbot.eclipse.finder.SWTWorkbenchBot;
import org.eclipse.swtbot.eclipse.finder.waits.Conditions;
import org.eclipse.swtbot.swt.finder.SWTBotWidget;
import org.eclipse.swtbot.swt.finder.finders.UIThreadRunnable;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotButton;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotLabel;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.junit.Before;
import org.junit.Test;

public class OrderWizardUITest {
	private final SWTWorkbenchBot bot = new SWTWorkbenchBot();
	public static String ID = "foss.admin";

	@Test
	/*
	 * This test checks if the Order via wizard is working as
	 * expected.
	 */
	public void testOrderWidget1() {
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
		bot.menu("Order via wizard").menu("OrderWizard").click();

		//check if a restaurant can be selected
		assertTrue(bot.label("Select a restaurant").isVisible());

		SWTBotButton nextButton = bot.button("Next >");
		
		//check if the "Next" button is not enabled(we didn't select any restauarant)
		assertFalse(nextButton.isEnabled());

		//select a restaurant
		bot.comboBox().setSelection("KFC");

		//check if the "Next" button is enabled(we selected a restaurant)
		assertTrue(nextButton.isEnabled());

		//click next button
		bot.button("Next >").click();

		//check if a menu can be selected
		assertTrue(bot.label("Select a Menu").isVisible());
		
		nextButton = bot.button("Next >");
		
		//check if the "Next" button is not enabled(we didn't select any menu)
		assertFalse(nextButton.isEnabled());

		//select a menu
		bot.comboBox().setSelection("Zinger Menu");

		//check if the "Next" button is enabled(we selected a menu)
		assertTrue(nextButton.isEnabled());

		//click next button
		bot.button("Next >").click();

		//check if the order details are visible
		assertTrue(bot.label("Order details").isVisible());

		SWTBotLabel restaurantLabel = bot.label("KFC");
		SWTBotLabel menuLabel = bot.label("Zinger Menu");

		//get the text of label
		String restaurantText = restaurantLabel.getText();
		String menuText = menuLabel.getText();
		
		//check if selected items are correct
		assertEquals("Nu s-a selectat restaurantul corespunzator.", "KFC", restaurantText);
		assertEquals("Nu s-a selectat meniul corespunzator.", "Zinger Menu", menuText);

		//check if the "Finish" button is enabled
		assertTrue(bot.button("Finish").isEnabled());
		
		assertEquals("create a new order", bot.activeShell().getText());
		
		//click finish
		bot.button("Finish").click();	
		
		//check if the confirmation window is opened
		assertEquals("Order confirmation", bot.activeShell().getText());
		
		//click confirmation button
		bot.button("OK").click();
		
		
	}

}
