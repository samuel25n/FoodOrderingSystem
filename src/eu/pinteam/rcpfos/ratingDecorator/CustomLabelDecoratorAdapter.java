package eu.pinteam.rcpfos.ratingDecorator;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.swt.graphics.Image;

import eu.pinteam.rcpfos.imageRegistry.FosImageRegistry;
import eu.pinteam.rcpfos.management.RestaurantManagement;
import eu.pinteam.rcpfos.path.PathHandler;
import fosemf.Menu;
import fosemf.Restaurant;

public class CustomLabelDecoratorAdapter implements ILabelProvider {
	private CustomLabelDecorator decorator;

	public CustomLabelDecoratorAdapter(CustomLabelDecorator decorator) {
		this.decorator = decorator;
	}

	@Override
	public Image getImage(Object element) {
		FosImageRegistry fos = new FosImageRegistry();
		if (element instanceof Restaurant) {
			Restaurant restaurant = (Restaurant) element;
			int rating = restaurant.getRating();
			return decorator.decorateImage(fos.getFosImageRegistry().get("restaurantIcon"),
					fos.getFosImageRegistry().get("restaurantRating" + rating));
		} else if (element instanceof Menu) {
			Menu menu = (Menu) element;
			int rating = menu.getRating();
			return decorator.decorateImage(fos.getFosImageRegistry().get("menuIcon"),
					fos.getFosImageRegistry().get("restaurantRating" + rating));
		}
		return null;
	}

	@Override
	public String getText(Object element) {
		return null;
	}

	@Override
	public void addListener(ILabelProviderListener listener) {
	}

	@Override
	public void dispose() {
	}

	@Override
	public boolean isLabelProperty(Object element, String property) {

		return false;
	}

	@Override
	public void removeListener(ILabelProviderListener listener) {

	}
}
