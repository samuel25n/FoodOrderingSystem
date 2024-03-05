package eu.pinteam.rcpfos.wizard.addRestaurant;

import java.io.File;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import eu.pinteam.rcpfos.activator.Activator;
import eu.pinteam.rcpfos.management.RestaurantManagement;
import eu.pinteam.rcpfos.path.PathHandler;
import eu.pinteam.rcpfos.views.RestaurantView;
import fosemf.FosemfFactory;
import fosemf.Restaurant;

public class RestaurantWizard extends Wizard {

	private Restaurant restaurant;

	public RestaurantWizard() {
		super();
		setNeedsProgressMonitor(true);
		restaurant = FosemfFactory.eINSTANCE.createRestaurant();
	}

	@Override
	public void addPages() {
		addPage(new RestaurantDetailsPage());
	}

	@Override
	public boolean performFinish() {
		//RestaurantManagement restaurantManagement = new RestaurantManagement();
		Activator activator = Activator.getDefault();
		 RestaurantManagement restaurantManagement = activator.getRestaurantManagement();
		RestaurantView restaurantView = RestaurantView.getInstance();
		if (restaurantView != null) {
			MessageDialog.openInformation(new Shell(), "Info", "The restaurant was created!");
			restaurantManagement.addRestaurant(restaurant);
			restaurantView.refreshViewer();
		} else
			return false;

		return true;

	}

	public Restaurant getRestaurant() {
		if (restaurant == null) {
			System.out.println("Restaurant Null");
		}
		return restaurant;
	}
}
