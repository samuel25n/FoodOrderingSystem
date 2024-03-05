package eu.pinteam.rcpfos.restaurantTreeViewer;

import org.eclipse.jface.viewers.ColumnLabelProvider;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;


import eu.pinteam.rcpfos.ratingDecorator.CustomLabelDecorator;
import eu.pinteam.rcpfos.ratingDecorator.CustomLabelDecoratorAdapter;
import fosemf.Menu;
import fosemf.Restaurant;

import org.eclipse.jface.viewers.ILabelProvider;

public class RestaurantTreeViewerLabelProvider extends ColumnLabelProvider {
	private Color redColor;
	private CustomLabelDecorator customDecorator;
	ILabelProvider labelProvider;

	public RestaurantTreeViewerLabelProvider() {
		redColor = Display.getCurrent().getSystemColor(SWT.COLOR_RED);
		customDecorator = new CustomLabelDecorator();
		labelProvider = new CustomLabelDecoratorAdapter(customDecorator);
	}

	@Override
	public Image getImage(Object element) {
		if (element instanceof Restaurant) {
			Restaurant restaurant = (Restaurant) element;
			return labelProvider.getImage(restaurant);
		}
		if (element instanceof Menu) {
			Menu menu = (Menu) element;
			return labelProvider.getImage(menu);
		}
		return null;
	}

	@Override
	public String getText(Object element) {
		if (element instanceof Restaurant) {
			return ((Restaurant) element).getName();
		} else if (element instanceof Menu) {
			return ((Menu) element).getName();
		}
		return super.getText(element);
	}

	@Override
	public void dispose() {

	}

	@Override
	public Color getForeground(Object element) {
		return redColor;
	}
}
