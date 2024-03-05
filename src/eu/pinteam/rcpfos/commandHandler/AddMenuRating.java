package eu.pinteam.rcpfos.commandHandler;
import java.io.File;
import java.util.Iterator;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.handlers.HandlerUtil;

import eu.pinteam.rcpfos.activator.Activator;
import eu.pinteam.rcpfos.dialogs.RatingDialog;
import eu.pinteam.rcpfos.management.RestaurantManagement;
import eu.pinteam.rcpfos.path.PathHandler;
import eu.pinteam.rcpfos.views.RestaurantView;
import fosemf.Menu;

public class AddMenuRating extends AbstractHandler {
	private RatingDialog ratingDialog;
	public PathHandler path = new PathHandler();
	private Activator activator = Activator.getDefault();
	private RestaurantManagement restaurantManagement = activator.getRestaurantManagement();
	private String nameOfMenu;
	private int ratingValue;

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		Display display = Display.getCurrent();
		Shell shell = new Shell(display);
		shell.setLayout(new GridLayout(5, false));
		ratingDialog = new RatingDialog(shell);
		ISelection selection = HandlerUtil.getActiveWorkbenchWindow(event).getActivePage().getSelection();

		if (selection != null && selection instanceof IStructuredSelection) {
			IStructuredSelection strucSelection = (IStructuredSelection) selection;
			for (Iterator<Object> iterator = strucSelection.iterator(); iterator.hasNext();) {
				Object element = iterator.next();
				if (element instanceof Menu) {
					Menu emfObject = (Menu) element;
					nameOfMenu = emfObject.getName();
					ratingValue = ratingDialog.createDialog(nameOfMenu);
					if(ratingValue!=0) {
					Menu menu = (Menu) element;
					menu.setRating(ratingValue);
					
					restaurantManagement.updateMenuRating(restaurantManagement.getRestaurantForMenuRestaurant(menu), menu, ratingValue);
					 RestaurantView restaurantView = RestaurantView.getInstance();
				        if (restaurantView != null) {
				            restaurantView.refreshViewer();
				        }
					}
					
				} else {

					MessageDialog.openInformation(shell, "Information", "You haven't selected a menu!");
				}
			}

			while (!shell.isDisposed()) {
				if (!display.readAndDispatch()) {
					display.sleep();
				}
			}
		} else {
			System.out.println("Your selection is null!");
		}
		return null;
	}

}
