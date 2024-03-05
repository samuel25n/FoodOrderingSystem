package eu.pinteam.rcpfos.wizard;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.wizard.IWizardContainer;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.Wizard;

public class OrderWizard extends Wizard {


	private String selectedRestaurant;
	private String selectedMenu;

	public OrderWizard() {
		super();
		setWindowTitle("create a new order");
	}

	@Override
	public void addPages() {
		addPage(new RestaurantSelectionPage(getContainer()));
		addPage(new MenuSelectionPage(getContainer()));
		addPage(new OrderDetailsPage(getContainer()));
	}

	@Override
	public boolean canFinish() {
		IWizardContainer container = getContainer();
		IWizardPage currentPage = container.getCurrentPage();
		IWizardPage[] pages = getPages();
		return currentPage == pages[pages.length - 1];
	}

	@Override
	public boolean performFinish() {
		MessageDialog.openInformation(getShell(), "Order confirmation", "The order has been successfully saved.");

		return true; 
	}

	public String getSelectedRestaurant() {
		return selectedRestaurant;
	}

	public void setSelectedRestaurant(String selectedRestaurant) {
		this.selectedRestaurant = selectedRestaurant;
	}

	public String getSelectedMenu() {
		return selectedMenu;
	}

	public void setSelectedMenu(String selectedMenu) {
		this.selectedMenu = selectedMenu;
	}


}
