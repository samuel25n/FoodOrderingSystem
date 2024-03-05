package eu.pinteam.rcpfos.wizard;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.handlers.HandlerUtil;

public class OrderWizardHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		
		Shell parentShell = HandlerUtil.getActiveShell(event);

		OrderWizard wizard = new OrderWizard(); 
		WizardDialog dialog = new WizardDialog(parentShell, wizard);
		dialog.open();

		return null;
	}

}
