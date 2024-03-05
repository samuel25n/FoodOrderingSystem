package eu.pinteam.rcpfos.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.eclipse.swtbot.eclipse.finder.SWTWorkbenchBot;
import org.eclipse.swtbot.swt.finder.finders.UIThreadRunnable;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTreeItem;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.junit.Test;

public class RestaurantItemsUITest {

	private final SWTWorkbenchBot bot = new SWTWorkbenchBot();
	public static final String ID = "foss.admin";

	@Test
	public void testingRestaurantItems() {

		UIThreadRunnable.syncExec(bot.getDisplay(), () -> {
			IWorkbenchWindow activeWindow = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
			assertNotNull(activeWindow);
			try {
				activeWindow.getActivePage().showView(ID);
			} catch (PartInitException ex) {
				System.out.println(ex);
			}
		});

		SWTBotTreeItem select = bot.tree().getTreeItem("Del Corso");
		assertEquals(select.getText(), "Del Corso");
		
		select = bot.tree().getTreeItem("Antalya");
		assertEquals(select.getText(), "Antalya");
		
		select = bot.tree().getTreeItem("KFC");
		assertEquals(select.getText(), "KFC");
		
		select = bot.tree().getTreeItem("McDonald's");
		assertEquals(select.getText(), "McDonald's");
		
		select = bot.tree().getTreeItem("Naomi");
		assertEquals(select.getText(), "Naomi");
		
		select = bot.tree().getTreeItem("Ruth");
		assertEquals(select.getText(), "Ruth");
		
	}
}


