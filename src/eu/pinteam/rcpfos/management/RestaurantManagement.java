package eu.pinteam.rcpfos.management;

import java.io.File;

import java.io.IOException;

import java.util.ArrayList;

import java.util.List;

import java.util.regex.Matcher;

import java.util.regex.Pattern;

import org.eclipse.emf.common.util.URI;

import org.eclipse.emf.ecore.EObject;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;

import eu.pinteam.rcpfos.path.PathHandler;
import fosemf.Menu;

import fosemf.Restaurant;

public class RestaurantManagement {

	private Resource resource;
	private ResourceSet resourceSet;

	/**
	 * if the XMI file exists, the resource is loaded from this file if the XMI file
	 * does not exist, a new file is created for the resource
	 */
	public RestaurantManagement() {

		super();

		File file = new File(PathHandler.getAbsolutePathXmiFile());
		resourceSet = new ResourceSetImpl();

		if (file.exists()) {
			this.resource = resourceSet.getResource(URI.createFileURI(PathHandler.getAbsolutePathXmiFile()), true);
			try {
				resource.load(null);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			resource = resourceSet.createResource(URI.createFileURI(PathHandler.getAbsolutePathXmiFile()));
		}

	}

	/**
	 * this method adds a new restaurant
	 * 
	 * @param restaurant
	 * @return
	 */
	public boolean addRestaurant(Restaurant restaurant) {
		if (validateName(restaurant.getName()) && checkIfRestaurantExists(restaurant.getName())) {
			resource.getContents().add(restaurant);
			save();
			return true;
		} else
			return false;
	}

	/**
	 * this method adds a new menu to the restaurant
	 * 
	 * @param restaurant
	 * @param menu
	 */
	public void addRestaurantMenu(Restaurant restaurant, Menu menu) {

		if (validateName(menu.getName()) && checkIfMenuExists(restaurant, menu.getName())) {
			restaurant.getMenus().add(menu);
			save();
		}

	}

	/**
	 * this method returns a list of existing restaurants
	 * 
	 * @return
	 */
	public List<Restaurant> getListOfRestaurants() {
//		resource.unload();
//		try {
//		resource.load(null);
//		} catch (IOException e) {
//		e.printStackTrace();
//		}

		List<Restaurant> restaurants = new ArrayList<>();
		for (EObject obj : resource.getContents()) {
			if (obj instanceof Restaurant) {
				restaurants.add((Restaurant) obj);
			}
		}
		return restaurants;

	}

	/**
	 * this method returns a list of restaurant menus
	 * 
	 * @param restaurant
	 * @return
	 */
	public List<Menu> getMenusForRestaurant(Restaurant restaurant) {

		return restaurant.getMenus();

	}

	/**
	 * this method removes a menu from the restaurant
	 * 
	 * @param restaurant
	 * @param menuToDelete
	 * @return
	 */
	public boolean deleteOneMenuInRestaurant(Restaurant restaurant, Menu menuToDelete) {

		if (restaurant.getMenus().contains(menuToDelete)) {
			restaurant.getMenus().remove(menuToDelete);
			save();
		}

		return true;

	}

	/**
	 * this method deletes a restauarant
	 * 
	 * @param restaurantToDelete
	 * @return
	 */
	public boolean deleteRestaurant(Restaurant restaurantToDelete) {
		resource.getContents().remove(restaurantToDelete);
		save();
		return true;

	}

	/**
	 * this method deletes all menus of a restaurant
	 * 
	 * @param restaurant
	 */
	public void deleteRestaurantMenus(Restaurant restaurant) {

		restaurant.getMenus().clear();

		save();
	}

	/**
	 * this method updates the rating of a restaurant
	 * 
	 * @param restaurantToUpdate
	 * @param newRating
	 * @return
	 */
	public boolean updateRestaurantRating(Restaurant restaurantToUpdate, int newRating) {
		restaurantToUpdate.setRating(newRating);
		save();

		return true;

	}

	/**
	 * this method updates the rating of a menu
	 * 
	 * @param restaurant
	 * @param menuToUpdate
	 * @param newRating
	 * @return
	 */
	public boolean updateMenuRating(Restaurant restaurant, Menu menuToUpdate, int newRating) {

		for (Menu menu : restaurant.getMenus()) {
			if (menu == menuToUpdate) {
				menu.setRating(newRating);
				save();
				return true;
			}
		}
		return false;

	}

	/**
	 * this method updates the rating of an image
	 * 
	 * @param restaurant
	 * @param menuToUpdate
	 * @param newRating
	 * @return
	 */
	public boolean updateImageRating(Restaurant restaurant, Menu menuUpdate, int newRating) {

		for (Menu menu : restaurant.getMenus()) {
			if (menu == menuUpdate) {
				menu.setImageRating(newRating);
				save();
				return true;
			}
		}
		return false;

	}

	/**
	 * this method returns the restaurant of a menu
	 * 
	 * @param menu
	 * @return
	 */
	public Restaurant getRestaurantForMenuRestaurant(Menu menu) {

		for (EObject obj : resource.getContents()) {
			if (obj instanceof Restaurant) {
				Restaurant restaurant = (Restaurant) obj;
				for (Menu restaurantMenu : restaurant.getMenus()) {

					if (restaurantMenu.getName().equals(menu.getName())
							&& restaurantMenu.getPicture().equals(menu.getPicture())) {
						return restaurant;
					}
				}
			}
		}
		return null;
	}

	/**
	 * this method saves the changes from the resource in the xmi file
	 */
	private void save() {
		try {
			resource.save(null);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * this method checks if the name of a restaurant already exists in the resource
	 * 
	 * @param name
	 * @return
	 */
	private boolean checkIfRestaurantExists(String name) {
		for (EObject obj : resource.getContents()) {
			if (obj instanceof Restaurant) {
				Restaurant existingRestaurant = (Restaurant) obj;
				if (existingRestaurant.getName().equals(name)) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * this method validates a name
	 * 
	 * @param name
	 * @return
	 */
	private boolean validateName(String name) {
		String pattern = "^[a-zA-Z\\s']+$";

		Pattern regex = Pattern.compile(pattern);
		Matcher matcher = regex.matcher(name);

		return matcher.matches();
	}

	/**
	 * this method checks if the name of a menu already exists in the restaurant
	 * 
	 * @param restaurant
	 * @param name
	 * @return
	 */
	private boolean checkIfMenuExists(Restaurant restaurant, String name) {
		for (Menu existingMenu : restaurant.getMenus()) {
			if (existingMenu.getName().equals(name)) {
				return false;
			}
		}
		return true;
	}
}
