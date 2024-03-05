package eu.pinteam.rcpfos.management;

import java.util.List;

import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.jface.viewers.TreeViewer;

import fosemf.Restaurant;


public class ListPopulator {

    public void populateRestaurantList(TreeViewer restaurantsListViewer, List<Restaurant> restaurantsList) {
        if (restaurantsListViewer != null && restaurantsList != null) {
            restaurantsListViewer.setInput(restaurantsList);
        }
    }
}
