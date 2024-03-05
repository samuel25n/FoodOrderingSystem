package eu.pinteam.rcpfos.restaurantTreeViewer;


import java.util.List;

import org.eclipse.jface.viewers.ITreeContentProvider;

import eu.pinteam.rcpfos.activator.Activator;
import eu.pinteam.rcpfos.management.RestaurantManagement;
import fosemf.Restaurant;

public class RestaurantTreeViewerContentProvider implements ITreeContentProvider {

	private Activator activator = Activator.getDefault();
	@SuppressWarnings("static-access")
	private RestaurantManagement restaurantManagement = activator.getRestaurantManagement();

	@Override
	public Object[] getElements(Object inputElement) {
		return ((List<?>) inputElement).toArray();
	}

	/**
	 * This method returns menus for a specific restaurant
	 */
	@Override
	public Object[] getChildren(Object parentElement) {
		if (parentElement instanceof Restaurant) {
			return restaurantManagement
					.getMenusForRestaurant((Restaurant) parentElement)
					.toArray();
		}
		return new Object[0];
	}

	@Override
	public Object getParent(Object element) {
		return null;
	}

	@Override
	public boolean hasChildren(Object element) {
		return element instanceof Restaurant;
	}

}
