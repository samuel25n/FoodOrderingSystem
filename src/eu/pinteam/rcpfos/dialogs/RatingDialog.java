package eu.pinteam.rcpfos.dialogs;


import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;



public class RatingDialog {

	private Label label;
	private Shell shell;
	private Integer rating=0;
	private boolean userSelected = false; 
	
	public RatingDialog(Shell shell) {
		super();
		this.shell = shell;
	}

	/**
	 * @param itemName - it could be the name of a restaurant or a menu
	 * @return - rating value or 0 if no rating was selected
	 */
	public int createDialog(String itemName) {
		
		shell.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, true, 1, 1));
		shell.setMinimumSize(500, 200);
		shell.setText("Rate " + itemName);
		
		label = new Label(shell, SWT.NONE);
		label.setText("Add rating for " + itemName);
		label.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, false, 5, 1));

		for (int i = 1; i <= 5; i++) {
			final Button button = new Button(shell, SWT.PUSH);
			button.setText(String.valueOf(i));
			
			GridData gridData = new GridData(SWT.FILL, SWT.CENTER, true, false);
			gridData.widthHint = 40;
			button.setLayoutData(gridData);

			button.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					rating = Integer.parseInt(button.getText());
					userSelected = true;
					System.out.println(rating);
					System.out.println(itemName);
					MessageDialog.openInformation(shell, "Info",
							itemName + "-" + rating + " / 5\nThank you for rating!");

					shell.close();

				}
			});
		}
		IWorkbench workbench = PlatformUI.getWorkbench();

		IWorkbenchWindow activeWorkbenchWindow = workbench.getActiveWorkbenchWindow();

		if (activeWorkbenchWindow != null) {
			activeWorkbenchWindow.getShell().addDisposeListener(new DisposeListener() {
				@Override
				public void widgetDisposed(DisposeEvent e) {
					if(shell != null && !shell.isDisposed()) {
					shell.close();
					}
				}
			});
		}
		
		shell.pack();
		shell.open();
		
		while (!shell.isDisposed()) {
	        if (!shell.getDisplay().readAndDispatch()) {
	            shell.getDisplay().sleep();
	        }
	    }

	    if (userSelected) {
	        return rating;
	    } else {
	        return 0; 
	    }
	}

}
