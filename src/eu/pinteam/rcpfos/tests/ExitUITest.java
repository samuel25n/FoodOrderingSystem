package eu.pinteam.rcpfos.tests;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.eclipse.swtbot.eclipse.finder.SWTWorkbenchBot;
import org.eclipse.swtbot.swt.finder.exceptions.WidgetNotFoundException;
import org.eclipse.swtbot.swt.finder.finders.UIThreadRunnable;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotButton;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotCheckBox;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotShell;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.junit.Test;

public class ExitUITest {
	
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
			
			bot.menu("Exit").click();
            SWTBotShell exitShell = bot.activeShell();
            exitShell.activate();
            
            assertTrue("Exit shell should be active", exitShell.isOpen());
            
        } catch (WidgetNotFoundException e) {
            assertTrue("Exit shell was not found", false);
        }
		
	}

}
