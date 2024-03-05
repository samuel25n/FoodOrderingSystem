package eu.pinteam.rcpfos.commandHandler;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.handlers.HandlerUtil;

public class ExitHandler extends AbstractHandler {

    @Override
    public Object execute(ExecutionEvent event) throws ExecutionException {
        IWorkbench workbench = HandlerUtil.getActiveWorkbenchWindowChecked(event).getWorkbench();
        Shell shell = HandlerUtil.getActiveShellChecked(event);
        
        boolean result = MessageDialog.openConfirm(shell, "Close", "Close Application");
        if (result) {
            workbench.close();
        }
        
        return null;
    }
}
