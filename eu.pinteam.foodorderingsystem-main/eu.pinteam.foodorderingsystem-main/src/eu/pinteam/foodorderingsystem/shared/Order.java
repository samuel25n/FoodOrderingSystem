package eu.pinteam.foodorderingsystem.shared;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Order implements Serializable{
	
	
	private Customer customer;
	private Menu menu;
	private Restaurant restaurant;
	
	public Order(Customer customer, Menu menu, Restaurant restaurant) {
		super();
		this.customer = customer;
		this.menu = menu;
		this.restaurant = restaurant;
	}

	public Customer getCustomer() {
		return customer;
	}

	public Menu getMenu() {
		return menu;
	}

	public Restaurant getRestaurant() {
		return restaurant;
	}

	@Override
	public String toString() {

        return "" + customer.getName() + "," + menu.getName() + "," + restaurant.getName() +" "+ restaurant.getPhone() + "";

    }

}
