package eu.pinteam.rcpfos.tests;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.eclipse.swtbot.eclipse.finder.SWTWorkbenchBot;
import org.eclipse.swtbot.swt.finder.finders.UIThreadRunnable;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotButton;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotCheckBox;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotShell;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.junit.Test;

public class WidgetCheckBoxUITest {
	
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
		
		
		
		bot.viewByTitle("Restaurants").show();
		bot.checkBox("Nu").click();
		SWTBotCheckBox noCheckBox = bot.checkBox("Nu");
		assertTrue("Checkbox 'nu' should be checked ", noCheckBox.isChecked());
		
		bot.checkBox("Da").click();
		SWTBotCheckBox yesCheckBox = bot.checkBox("Da");
		assertTrue("Checkbox 'Da' should be checked ", yesCheckBox.isChecked());
		
		bot.toolbarButtonWithTooltip("CLOCK").click();
		SWTBotShell clockShell = bot.activeShell();
		clockShell.activate();
		assertTrue("Clock should be active", clockShell.isOpen());
		SWTBotButton okButton = clockShell.bot().button("OK");
		okButton.click();
		
		bot.toolbarButtonWithTooltip("CALENDAR").click();
		SWTBotShell calendarShell = bot.activeShell();
		calendarShell.activate();
		assertTrue("Calendar should be active", calendarShell.isOpen());
		okButton = calendarShell.bot().button("OK");
		okButton.click();
		
		bot.toolbarButtonWithTooltip("CALCULATOR").click();
		SWTBotShell calculatorShell = bot.activeShell();
		assertTrue("Calculator should be active", calculatorShell.isOpen());
		okButton = calculatorShell.bot().button("CLOSE");
		okButton.click();
		
		
		
		
		
	}

}
