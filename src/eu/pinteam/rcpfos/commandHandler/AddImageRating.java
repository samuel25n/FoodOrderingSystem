package eu.pinteam.rcpfos.commandHandler;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import eu.pinteam.rcpfos.activator.Activator;
import eu.pinteam.rcpfos.dialogs.RatingDialog;
import eu.pinteam.rcpfos.management.RestaurantManagement;
import eu.pinteam.rcpfos.path.PathHandler;
import fosemf.Menu;

public class AddImageRating extends AbstractHandler {

	private RatingDialog ratingDialog;
	private String imagePathString;
	private int ratingValue;
	private Activator activator = Activator.getDefault();
	private RestaurantManagement restaurantManagement = activator.getRestaurantManagement();
	private Menu menu;
	

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		return null;
	}

	public int processSelection(String imagePath) {
		Display display = Display.getCurrent();
		Shell shell = new Shell(display);
		shell.setLayout(new GridLayout(5, false));
		ratingDialog = new RatingDialog(shell);

		if (imagePath != null) {
			imagePathString = imagePath;
			ratingValue = ratingDialog.createDialog(imagePathString);	
			restaurantManagement.updateImageRating(restaurantManagement.getRestaurantForMenuRestaurant(menu), menu, ratingValue);		
			this.menu.setImageRating(ratingValue);
		}

		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		return ratingValue;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
	}

}
