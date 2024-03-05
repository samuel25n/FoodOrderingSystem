package eu.pinteam.rcpfos.decorator;

 

import org.eclipse.jface.viewers.ILabelDecorator;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.swt.graphics.Image;

import fosemf.Restaurant;


 

public class RestaurantRatingDecorator implements ILabelDecorator {

 //

    @Override
    public Image decorateImage(Image image, Object element) {
        return null;
    }

 

    @Override
    public String decorateText(String text, Object element) {
        if (element instanceof Restaurant) {
            Restaurant restaurant = (Restaurant) element;
            String ratingText;
			if (restaurant.getRating()!=0)
				ratingText = " Rating: " + restaurant.getRating();
			else
				ratingText = "";
            return text + ratingText;
        }
        return text;
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
