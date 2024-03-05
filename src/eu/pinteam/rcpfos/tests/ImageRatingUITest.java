package eu.pinteam.rcpfos.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swtbot.eclipse.finder.SWTWorkbenchBot;
import org.eclipse.swtbot.swt.finder.finders.UIThreadRunnable;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotButton;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotLabel;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotShell;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.junit.Test;

import eu.pinteam.rcpfos.management.RestaurantManagement;
import fosemf.Menu;
import fosemf.Restaurant;

public class ImageRatingUITest {
	
	private final SWTWorkbenchBot bot = new SWTWorkbenchBot();
	public static final String ID = "foss.admin";

	@Test
	public void testingImageItems() {

		UIThreadRunnable.syncExec(bot.getDisplay(), () -> {
			IWorkbenchWindow activeWindow = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
			assertNotNull(activeWindow);
			try {
				activeWindow.getActivePage().showView(ID);
			} catch (PartInitException ex) {
				System.out.println(ex);
			}
		});
	    bot.viewByTitle("Restaurants").show();
	    bot.tree().getTreeItem("KFC").expand();
	    bot.tree().getTreeItem("KFC").getNode("Zinger Menu").select();
	    bot.label(0).contextMenu("Add rating").menu("Rate Image").click();
	    
	    SWTBotShell ratingShell = bot.activeShell();
	    ratingShell.activate();
		bot.button("5").click();
		SWTBotShell infoShell = bot.activeShell();
		infoShell.activate();
		bot.button("OK").click();

			
		List<Restaurant> restaurants = new RestaurantManagement().getListOfRestaurants();
		for (Restaurant restaurant : restaurants) {
			if(restaurant.getName().equals("KFC")) {
				List<Menu> menus = new RestaurantManagement().getMenusForRestaurant(restaurant);
				for (Menu menu : menus) {
					if(menu.getName().equals("Zinger Menu")) {
						assertEquals(menu.getImageRating(),5);
					}
				}
			}
		}
		
	}

}
