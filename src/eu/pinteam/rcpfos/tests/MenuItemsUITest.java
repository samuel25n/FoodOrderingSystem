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

public class MenuItemsUITest {

	private final SWTWorkbenchBot bot = new SWTWorkbenchBot();
	public static final String ID = "foss.admin";

	@Test
	public void testingMenuItems() {

		UIThreadRunnable.syncExec(bot.getDisplay(), () -> {
			IWorkbenchWindow activeWindow = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
			assertNotNull(activeWindow);
			try {
				activeWindow.getActivePage().showView(ID);
			} catch (PartInitException ex) {
				System.out.println(ex);
			}
		});

		bot.tree().getTreeItem("Del Corso").expand();
		SWTBotTreeItem select = bot.tree().getTreeItem("Del Corso").getNode("Pizza Margherita").select();
		assertEquals(select.getText(), "Pizza Margherita");
		select = bot.tree().getTreeItem("Del Corso").getNode("Pizza DelCorso").select();
		assertEquals(select.getText(), "Pizza DelCorso");
		
		bot.tree().getTreeItem("Antalya").expand();
		select = bot.tree().getTreeItem("Antalya").getNode("Donner Kebap").select();
		assertEquals(select.getText(), "Donner Kebap");
		
		bot.tree().getTreeItem("KFC").expand();
		select = bot.tree().getTreeItem("KFC").getNode("Zinger Menu").select();
		assertEquals(select.getText(), "Zinger Menu");
		
		bot.tree().getTreeItem("McDonald's").expand();
		select = bot.tree().getTreeItem("McDonald's").getNode("McCombo").select();
		assertEquals(select.getText(), "McCombo");
		select = bot.tree().getTreeItem("McDonald's").getNode("Mc Chicken").select();
		assertEquals(select.getText(), "Mc Chicken");
		select = bot.tree().getTreeItem("McDonald's").getNode("BigMac").select();
		assertEquals(select.getText(), "BigMac");
		
		bot.tree().getTreeItem("Naomi").expand();
		select = bot.tree().getTreeItem("Naomi").getNode("Burger de vita").select();
		assertEquals(select.getText(), "Burger de vita");
		select = bot.tree().getTreeItem("Naomi").getNode("Burger de pui").select();
		assertEquals(select.getText(), "Burger de pui");
		select = bot.tree().getTreeItem("Naomi").getNode("Burger de post").select();
		assertEquals(select.getText(), "Burger de post");
		
		bot.tree().getTreeItem("Ruth").expand();
		select = bot.tree().getTreeItem("Ruth").getNode("Antricot de vita").select();
		assertEquals(select.getText(), "Antricot de vita");
		select = bot.tree().getTreeItem("Ruth").getNode("Pizza quatro formaggi").select();
		assertEquals(select.getText(), "Pizza quatro formaggi");
		
	}
}


