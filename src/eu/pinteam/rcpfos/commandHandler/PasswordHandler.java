package eu.pinteam.rcpfos.commandHandler;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.handlers.HandlerUtil;

import eu.pinteam.rcpfos.dialogs.PasswordDialog;

public class PasswordHandler extends AbstractHandler {

    @Override
    public Object execute(ExecutionEvent event) throws ExecutionException {
    	
        Shell shell = HandlerUtil.getActiveShellChecked(event);     
        PasswordDialog dialog = new PasswordDialog(shell);

        if (dialog.open() == Window.OK) {
            String user = dialog.getUser();
            String pw = dialog.getPassword();
        }
        
        return null;
    }
}
