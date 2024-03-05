package eu.pinteam.foodorderingsystem.shared;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Menu implements Serializable{
	
	private String name;
	private String picture;
	private Double price;
	
	public Menu(String name, String picture, Double price) {
		super();
		this.name = name;
		this.picture = picture;
		this.price = price;
	}

	@Override
	public String toString() {
		return name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}
	
	
	
	
	

}
