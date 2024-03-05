package eu.pinteam.rcpfos.dialogs;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.*;

import eu.pinteam.rcpfos.widgets.ClockWidget;

import org.eclipse.swt.layout.*;
import org.eclipse.swt.graphics.Point;

public class ClockDialog extends Dialog {
    
    public ClockDialog(Shell parentShell) {
        super(parentShell);
    }

    @Override
    protected Control createDialogArea(Composite parent) {
        Composite container = (Composite) super.createDialogArea(parent);
        
        FillLayout layout = new FillLayout(SWT.VERTICAL);
        container.setLayout(layout);
  
        ClockWidget clockWidget = new ClockWidget(container, SWT.NONE);
        
        return container;
    }

    @Override
    protected void createButtonsForButtonBar(Composite parent) {
        createButton(parent, IDialogConstants.OK_ID, "OK", true);
 
    }

    @Override
    protected void okPressed() {
 
        super.okPressed();
    }

    @Override
    protected Point getInitialSize() {
        return new Point(300, 200); 
    }

}
