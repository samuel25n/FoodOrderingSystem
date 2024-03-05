package eu.pinteam.foodorderingsystem.shared;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public class Restaurant implements Serializable{
	
	private String name;
	private String phone;
	private List<Menu> menuList;
	@Override
	public String toString() {
		return name;
	}
	public Restaurant( String name, String phone, List<Menu> menuList) {
		super();
		this.name = name;
		this.phone = phone;
		this.menuList = menuList;
	}
	
	public Restaurant(String name,String phone) {
		super();
		this.name = name;
		this.phone = phone;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public List<Menu> getMenuList() {
		return menuList;
	}
	public void setMenuList(List<Menu>menuList) {
		this.menuList = menuList;
	}
	
	
	

}
