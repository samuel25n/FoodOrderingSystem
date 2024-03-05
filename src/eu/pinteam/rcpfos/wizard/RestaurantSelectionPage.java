package eu.pinteam.rcpfos.wizard;

 

import java.io.File;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

 

import org.eclipse.jface.wizard.IWizardContainer;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import eu.pinteam.rcpfos.activator.Activator;
import eu.pinteam.rcpfos.management.RestaurantManagement;
import eu.pinteam.rcpfos.path.PathHandler;
import fosemf.Restaurant;

 

public class RestaurantSelectionPage extends WizardPage {
	private Combo combo;
	public PathHandler path = new PathHandler();
	private Activator activator = Activator.getDefault();
	private RestaurantManagement restaurantManagement = activator.getRestaurantManagement();
	private List<Restaurant> availableRestaurants;

 

	public RestaurantSelectionPage(IWizardContainer container) {
		super("Restaurant Selection");
		setTitle("Select a restaurant");
		setDescription("Choose a restaurant from the list.");
		try {
			this.availableRestaurants = getRestaurants();
		} catch (RemoteException e) {
			System.out.println("Nu s-a putut deschide fisierul cu restaurante!");
		}
	}

 

	@Override
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		container.setLayout(new GridLayout(2, false));

 

		Label label = new Label(container, SWT.NONE);
		label.setText("Select a restaurant:");

 

		combo = new Combo(container, SWT.DROP_DOWN | SWT.READ_ONLY);
		combo.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

 

		if (this.availableRestaurants.isEmpty()) {
			System.out.println("There are no restaurants available");
			combo.add("There are no restaurants available");
		} else {
			fillComboWithRestaurants();
		}

 

		setControl(container);

 

	}

 

	private void fillComboWithRestaurants() {
		for (Restaurant restaurant : availableRestaurants) {
			combo.add(restaurant.getName());
		}

 

		combo.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				OrderWizard wizard = (OrderWizard) getWizard();
				int selectedIndex = combo.getSelectionIndex();
				if (selectedIndex != -1) {
					String selectedValue = combo.getItem(selectedIndex);
					wizard.setSelectedRestaurant(selectedValue);
				}
				setPageComplete(selectedIndex != -1);
			}

 

		});

 

	}

 

	public List<Restaurant> getRestaurants() throws RemoteException {
			return restaurantManagement.getListOfRestaurants();
	}

 

	@Override
	public boolean isPageComplete() {
		return combo.getSelectionIndex() != -1;
	}
}