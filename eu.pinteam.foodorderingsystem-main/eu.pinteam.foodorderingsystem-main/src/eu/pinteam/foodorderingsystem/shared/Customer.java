package eu.pinteam.foodorderingsystem.shared;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Customer implements Serializable{
	
	private Long id;
	private String name;
	
	public Customer (String name) {
		this.name = name;
	}

	public Long getId() {
		return id;
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
	@Override
	public String toString() {
		return "Customer [id=" + id + ", name=" + name + "]";
	}

}
