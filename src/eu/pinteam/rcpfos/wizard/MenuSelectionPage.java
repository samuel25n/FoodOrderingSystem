

package eu.pinteam.rcpfos.wizard;

 

import java.io.File;

import java.util.ArrayList;

import java.util.List;

 

import org.eclipse.emf.common.util.EList;

import org.eclipse.jface.wizard.IWizardContainer;

import org.eclipse.jface.wizard.WizardPage;

import org.eclipse.swt.SWT;

import org.eclipse.swt.custom.CTabFolder;

import org.eclipse.swt.custom.CTabItem;

import org.eclipse.swt.events.SelectionAdapter;

import org.eclipse.swt.events.SelectionEvent;

import org.eclipse.swt.graphics.Point;

import org.eclipse.swt.layout.GridData;

import org.eclipse.swt.layout.GridLayout;

import org.eclipse.swt.widgets.Combo;

import org.eclipse.swt.widgets.Composite;

import org.eclipse.swt.widgets.Label;

import eu.pinteam.rcpfos.activator.Activator;
import eu.pinteam.rcpfos.management.RestaurantManagement;

import eu.pinteam.rcpfos.path.PathHandler;

import fosemf.Menu;

import fosemf.Restaurant;

 

public class MenuSelectionPage extends WizardPage {

	private Combo combo;

	private String selectedRestaurant;

	private Activator activator = Activator.getDefault();
	private RestaurantManagement restaurantManagement = activator.getRestaurantManagement();
	
	private Combo label;

 

	public MenuSelectionPage(IWizardContainer container) {

		super("Menu Selection");

		setTitle("Select a Menu");

		setDescription("Choose a menu from the list.");

	}

 

	@Override

	public void createControl(Composite parent) {

		Composite container = new Composite(parent, SWT.NONE);

		container.setLayout(new GridLayout(2, false));

 

		CTabFolder tabFolder = new CTabFolder(container, SWT.BORDER);

		tabFolder.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		tabFolder.setSimple(false);

 

		CTabItem tab1 = new CTabItem(tabFolder, SWT.NONE);

		tab1.setText("Choose a menu");

 

		Composite tab1Composite = new Composite(tabFolder, SWT.NONE);

		tab1Composite.setLayout(new GridLayout(2, false));

		tab1.setControl(tab1Composite);

 

		Label label = new Label(tab1Composite, SWT.NONE);

		label.setText("Select a Menu:");

 

		combo = new Combo(tab1Composite, SWT.DROP_DOWN | SWT.READ_ONLY);

		combo.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

 

		combo.setSelection(new Point(0, combo.getText().length()));

 

		CTabItem tab2 = new CTabItem(tabFolder, SWT.NONE);

		tab2.setText("Extra services");

 

		Composite tab2Composite = new Composite(tabFolder, SWT.NONE);

		tab2Composite.setLayout(new GridLayout());

		tab2.setControl(tab2Composite);

 

		Label extraServcesLabel = new Label(tab2Composite, SWT.NONE);

		extraServcesLabel.setText("servicii extra");

 

		tabFolder.setSelection(tab1);

 

		combo.addSelectionListener(new SelectionAdapter() {

			@Override

			public void widgetSelected(SelectionEvent e) {

				OrderWizard wizard = (OrderWizard) getWizard();

				int selectedIndex = combo.getSelectionIndex();

				if (selectedIndex != -1) {

					String selectedValue = combo.getItem(selectedIndex);

 

					wizard.setSelectedMenu(selectedValue);

 

				}

 

				setPageComplete(selectedIndex != -1);

			}

 

		});

 

		setControl(container);

 

	}

 

	@Override

	public void setVisible(boolean visible) {

		super.setVisible(visible);

		if (visible) {

			OrderWizard wizard = (OrderWizard) getWizard();

			selectedRestaurant = wizard.getSelectedRestaurant();

			if (selectedRestaurant != null) {

				combo.removeAll();

				updateCombo(selectedRestaurant);

			} else {

				combo.removeAll();

				label.setText("There is no selected restaurant");

			}

		}

	}

 

	private void updateCombo(String restaurantName) {

		if (restaurantName != null) {

			for (Menu menu : getRestaurantMenus(restaurantName)) {

				combo.add(menu.getName());

			}

		}

	}

 

	private List<Menu> getRestaurantMenus(String restaurantName) {


			List <Restaurant> restaurants = restaurantManagement.getListOfRestaurants();

			for (Restaurant restaurant : restaurants) {

				if(restaurant.getName().equals(restaurantName)) {

					return restaurant.getMenus();

				}

			}

			return null;


	}

 

	@Override

	public boolean isPageComplete() {

		return combo.getSelectionIndex() != -1;

	}

}
