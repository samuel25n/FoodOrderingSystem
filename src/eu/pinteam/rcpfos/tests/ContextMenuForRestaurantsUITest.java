package eu.pinteam.rcpfos.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.eclipse.swtbot.eclipse.finder.SWTWorkbenchBot;
import org.eclipse.swtbot.swt.finder.exceptions.WidgetNotFoundException;
import org.eclipse.swtbot.swt.finder.finders.UIThreadRunnable;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotShell;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTreeItem;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.junit.Test;

public class ContextMenuForRestaurantsUITest {

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

		try {

			bot.tree().getTreeItem("KFC").select().contextMenu("Add rating").menu("Add rating for restaurant").click();
			SWTBotShell ratingShell = bot.activeShell();
			ratingShell.activate();
			assertTrue("Rating shell should be active", ratingShell.isOpen());
			bot.button("5").click();
			bot.button("OK").click();

			bot.tree().getTreeItem("KFC").select().contextMenu("Add rating").menu("Add rating for menu").click();
			SWTBotShell infoShell = bot.activeShell();
			infoShell.activate();
			assertTrue("Info shell should be active", infoShell.isOpen());
			bot.button("OK").click();

		} catch (WidgetNotFoundException e) {
			assertTrue("Rating shell was not found", false);
		}

	}
}
