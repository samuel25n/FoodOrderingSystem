package eu.pinteam.rcpfos.tests;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swtbot.eclipse.finder.SWTWorkbenchBot;
import org.eclipse.swtbot.swt.finder.finders.UIThreadRunnable;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTreeItem;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.junit.Test;

import eu.pinteam.rcpfos.path.PathHandler;
import junit.framework.Assert;

public class ImageItemsUITest {

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

		bot.tree().getTreeItem("Del Corso").expand();
		SWTBotTreeItem select = bot.tree().getTreeItem("Del Corso").getNode("Pizza Margherita").select();
		select.click();
		assertNotNull(bot.label().image());	
		bot.label().image().dispose();
		
		select = bot.tree().getTreeItem("Del Corso").getNode("Pizza DelCorso").select();
		select.click();
		assertNotNull(bot.label().image());	
		bot.label().image().dispose();
		
		bot.tree().getTreeItem("KFC").expand();
		select = bot.tree().getTreeItem("KFC").getNode("Zinger Menu").select();
		select.click();
		assertNotNull(bot.label().image());	
		bot.label().image().dispose();
		
		bot.tree().getTreeItem("McDonald's").expand();
		select = bot.tree().getTreeItem("McDonald's").getNode("McCombo").select();
		select.click();
		assertNotNull(bot.label().image());	
		bot.label().image().dispose();
		
		select = bot.tree().getTreeItem("McDonald's").getNode("Mc Chicken").select();
		select.click();
		assertNotNull(bot.label().image());	
		bot.label().image().dispose();
		
		select = bot.tree().getTreeItem("McDonald's").getNode("BigMac").select();
		select.click();
		assertNotNull(bot.label().image());	
		bot.label().image().dispose();
		
		bot.tree().getTreeItem("Naomi").expand();
		select = bot.tree().getTreeItem("Naomi").getNode("Burger de vita").select();
		select.click();
		assertNotNull(bot.label().image());	
		bot.label().image().dispose();
		
		select = bot.tree().getTreeItem("Naomi").getNode("Burger de pui").select();
		select.click();
		assertNotNull(bot.label().image());	
		bot.label().image().dispose();
		
		select = bot.tree().getTreeItem("Naomi").getNode("Burger de post").select();
		select.click();
		assertNotNull(bot.label().image());	
		bot.label().image().dispose();
		
		bot.tree().getTreeItem("Ruth").expand();
		select = bot.tree().getTreeItem("Ruth").getNode("Antricot de vita").select();
		select.click();
		assertNotNull(bot.label().image());	
		bot.label().image().dispose();
		
		select = bot.tree().getTreeItem("Ruth").getNode("Pizza quatro formaggi").select();
		select.click();
		assertNotNull(bot.label().image());	
		bot.label().image().dispose();
		
		bot.tree().getTreeItem("Antalya").expand();
		select = bot.tree().getTreeItem("Antalya").getNode("Donner Kebap").select();
		select.click();
		
	}
}


