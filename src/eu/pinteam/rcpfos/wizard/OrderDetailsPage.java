package eu.pinteam.rcpfos.wizard;

import org.eclipse.jface.wizard.IWizardContainer;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

public class OrderDetailsPage extends WizardPage {
	private String selectedRestaurant;
	private String selectedMenu;
	private Label menuLabel;
	private Label restaurantLabel;

	public OrderDetailsPage(IWizardContainer container) {
		super("Order details");
		setTitle("Order details");
		setDescription("You have selected the following:");
	}

	@Override
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		container.setLayout(new GridLayout());
		restaurantLabel = new Label(container, SWT.NONE);
		menuLabel = new Label(container, SWT.NONE);
		restaurantLabel.setText("selected restaurant");
		menuLabel.setText("selected menu");
		restaurantLabel.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		menuLabel.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

		setControl(container);
	}

	@Override
	public void setVisible(boolean visible) {
		super.setVisible(visible);
		if (visible) {
			OrderWizard wizard = (OrderWizard) getWizard();
			selectedMenu = wizard.getSelectedMenu();
			selectedRestaurant = wizard.getSelectedRestaurant();
			if (selectedMenu != null && selectedRestaurant != null) {
				displayOrderCustomer(selectedMenu, selectedRestaurant);
			} else {
				restaurantLabel.setText("There is no restaurant selected!");
				menuLabel.setText("There is no menu selected!");
			}
		}
	}

	private void displayOrderCustomer(String meniu, String rest) {
		restaurantLabel.setText(rest);
		menuLabel.setText(meniu);
	}

}
