package eu.pinteam.foodorderingsystem.management;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import eu.pinteam.foodorderingsystem.shared.Menu;
import eu.pinteam.foodorderingsystem.shared.Restaurant;

public class RestaurantManagement {
	/**
	 * This method will add a restaurant to a file
	 * 
	 * @param restaurant      - New restaurant added to restaurant file
	 * @param restaurantsFile - restaurants file
	 * @return 
	 */
	public boolean addRestaurantToFile(Restaurant restaurant, File restaurantsFile,String filepath ) {
		
		if(validateName(restaurant.getName())==false) {
			return false;
		}
		
		if (checkIfRestaurantExist(restaurant.getName(),restaurantsFile) == true) {
			System.out.println("The restaurant already exists!!!");
			return false;
		} else {
			addRestaurant(restaurant, restaurantsFile);
			addRestaurantsMenusToFile(restaurant,filepath);
			
			return true;
		}
	}

	private boolean validateName(String name) {
	     String pattern = "^[a-zA-Z\\s]+$";
	        Pattern regex = Pattern.compile(pattern);
	        Matcher matcher = regex.matcher(name);
	        return matcher.matches();
	}

	/**
	 * This method will create a file called "rastaurantName.csv" which will contain
	 * the menus of the restaurant
	 * 
	 * @param restaurant - New restaurant added to restaurant file
	 */
	private void addRestaurantsMenusToFile(Restaurant restaurant,String path) {

		String filePath = path + restaurant.getName() + ".csv";
		File newRestaurantFile = new File(filePath);

		try (FileWriter fileWriter = new FileWriter(newRestaurantFile, true)) {
			StringBuilder menuData = new StringBuilder();
			menuData.append("Menu").append(",").append("Image path").append(",").append("Price").append("\n");
			List<Menu> menuList = restaurant.getMenuList();
			if (menuList != null && !menuList.isEmpty()) {
				for (Menu menu : menuList) {
					menuData.append(menu.getName()).append(",").append(menu.getPicture()).append(",")
							.append(menu.getPrice()).append("\n");
				}
			}

			fileWriter.write(menuData.toString());

			System.out.println("Restaurant added to file.");
		} catch (IOException e) {
			System.out.println("Error adding restaurant to file: " + e.getMessage());
		}

	}

	/**
	 * This method will add a new restaurant in restaurants file
	 * 
	 * @param restaurant      - New restaurant added to restaurant file
	 * @param restaursntsFile - The file of restaurants
	 */
	private void addRestaurant(Restaurant restaurant, File restaursntsFile) {
	
		try (FileWriter fileWriter = new FileWriter(restaursntsFile, true)) {
			StringBuilder restaurantData = new StringBuilder();
			restaurantData.append(restaurant.getName()).append(",").append(restaurant.getPhone()).append("\n");
			fileWriter.write(restaurantData.toString());
			System.out.println("Restaurant added to file of restaurants.");
		} catch (IOException e) {
			System.out.println("Error adding restaurant to file: " + e.getMessage());
		}

		 

	}

	/**
	 * This method will return a list of menus of a restaurant
	 * 
	 * @param restaurant
	 * @return
	 */
	public List<Menu> getMenusForRestaurant(Restaurant restaurant,File restaurantsFile,String folderPath) {
	
		if (checkIfRestaurantExist(restaurant.getName(),restaurantsFile)) {
			List<Menu> menuList = null;
	
			File folder = new File(folderPath);

			if (folder.exists() && folder.isDirectory()) {
				File[] files = folder.listFiles();
				for (File file : files) {
					if (file.isFile() && file.getName().endsWith(".csv")) {
						String fileName = file.getName();
						if (restaurant.getName().equalsIgnoreCase(fileName.substring(0, fileName.lastIndexOf('.')))) {
							
							
							menuList = createRestaurantList(file);
							return menuList;
						}
					}
				}
			} else {
				System.out.println("Folderul specificat nu există sau nu este un director.");

			}
		} else {
			System.out.println("The restaurant " + restaurant.getName() + " does not exist!");
		}
		return new ArrayList<>();
	}

	/**
	 * This method creates a list of menus in the specified restaurant file
	 * 
	 * @param file - specified restaurant menu file
	 * @return
	 */
	private List<Menu> createRestaurantList(File file) {
	    List<Menu> restaurantMenus = new ArrayList<>();

	    try (Scanner scanner = new Scanner(file)) {
	        scanner.nextLine();
	        while (scanner.hasNextLine()) {
	            String line = scanner.nextLine();
	            String[] data = line.split(",");

	            if (data.length == 3) {
	                String name = data[0];
	                String path = data[1];

	                try {
	                    double price = Double.parseDouble(data[2]);
	                    Menu menu = new Menu(name, path, price);
	                    restaurantMenus.add(menu);
	                } catch (NumberFormatException e) {
	                    System.out.println("Invalid price format for restaurant: " + name);
	                }
	            }
	        }
	        return restaurantMenus;
	    } catch (FileNotFoundException e) {
	        System.out.println("Error reading restaurants from file: " + e.getMessage());
	        return restaurantMenus;
	    }
	}

	/**
	 * this method will get a file as input and will return a list of restaurants
	 * 
	 * @param file
	 * @return
	 */
	public List<Restaurant> getRestaurantsFromFile(File restaurantsFile) {
		
		List<Restaurant> restaurants = new ArrayList<>();
		try (Scanner scanner = new Scanner(restaurantsFile)) {
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				String[] data = line.split(",");
					String name = data[0];
					String phone = data[1];
					Restaurant restaurant = new Restaurant(name, phone);
					restaurants.add(restaurant);		
			}
		} catch (FileNotFoundException e) {
			System.out.println("Error reading restaurants from file: " + e.getMessage());
		}
		return restaurants;
	}

	/**
	 * This method will check if a name of restaurant exist in restaurants file.
	 * 
	 * @param restaurantName
	 * @return
	 */
	public boolean checkIfRestaurantExist(String restaurantName,File restaurantsFile) {

		try (Scanner scanner = new Scanner(restaurantsFile)) {
		
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				
				String[] data = line.split(",");
				if (restaurantName.equalsIgnoreCase(data[0])) {
					return true;
				}

			}
		} catch (FileNotFoundException e) {
			System.out.println("Error reading restaurants from file: " + e.getMessage());
		}
		return false;
	}

	/**
	 * This method will check if a name of menu exist in restaurant file.
	 * 
	 * @param restaurantName
	 * @return
	 */
	private boolean checkIfMenuExist(String restaurantName, String menuName) {
		
		String filePath = "files/restaurants/" + restaurantName + ".csv";
		File file = new File(filePath);
		try (Scanner scanner = new Scanner(file)) {
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				String[] data = line.split(",");
				if (menuName.equalsIgnoreCase(data[0])) {
				
					return true;
				}

			}
		} catch (FileNotFoundException e) {
			System.out.println("Error reading menus from file: " + e.getMessage());
		}
		return false;
	}

	/**
	 * This method will delete a menu from the menu list of the restaurant
	 * 
	 * @param restaurantName
	 * @param menuName
	 * @return
	 */
	public boolean deleteOneMenuInRestaurant(String restaurantName, String menuName,String filePath) {
		try {
			int ok = 0;
			
			File file = new File(filePath + restaurantName + ".csv");
			File tempFile = new File("temp.csv");

			BufferedReader reader = new BufferedReader(new FileReader(file));
			BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

			String line;
			while ((line = reader.readLine()) != null) {
				String[] values = line.split(",");
				if (values[0].equalsIgnoreCase(menuName)) {
					ok = 1;
				}
				if (!values[0].equalsIgnoreCase(menuName)) {
					writer.write(line);
					writer.newLine();
				}
			}
			reader.close();
			writer.close();
			if (!file.delete()) {
				System.out.println("Nu s-a putut șterge fișierul original.");
				return false;

			}
			if (!tempFile.renameTo(file)) {
				System.out.println("Nu s-a putut redenumi fișierul temporar.");
				return false;
			}
			if (ok == 1)
				return true;
		} catch (IOException e) {
			System.out.println("S-a produs o eroare in timpul stergerii meniului!!!");
			return false;
		}
		return false;
	}

	/**
	 * This method will add a new menu to restaurant.
	 * 
	 * @param restaurant
	 * @param newMenu
	 */
	public boolean addNewMenuToRestaurant(String restaurant, Menu newMenu,File restaurantsFile,String filePath) {
		if(validateName(newMenu.getName())==false) {
			return false;
		}
		
		if (checkIfRestaurantExist(restaurant, restaurantsFile) == true && checkIfMenuExist(restaurant, newMenu.getName()) == false) {
			
			File newRestaurantFile = new File(filePath + restaurant + ".csv");
			try (FileWriter fileWriter = new FileWriter(newRestaurantFile, true)) {
				StringBuilder menuData = new StringBuilder();
				menuData.append(newMenu.getName()).append(",").append(newMenu.getPicture()).append(",")
						.append(newMenu.getPrice()).append("\n");
				fileWriter.write(menuData.toString());
				System.out.println("Menu added to restaurant.");
				return true;
			} catch (IOException e) {
				
				System.out.println("Error adding menu: " + e.getMessage());
				return false;
			}
		} else {
			System.out.println(
					"Can't add menu to restaurant. The restaurant does not exist or" + " already contains the menu!!!");
			return false;
		}
		
	}

	/**
	 * This method will delete a restaurant
	 * 
	 * @param restaurant      - the restaurant that will be deleted
	 * @param restaurantsFile
	 * @return 
	 */
	public boolean deleteRestaurant(String restaurant, File restaurantsFile) {

		if (checkIfRestaurantExist(restaurant,restaurantsFile) == true) {
			deleteRestaurantInFile(restaurant, restaurantsFile);
			deleteRestaurantMenus(restaurant,restaurantsFile,"files/restaurants");
			return true;
		} else {
			System.out.println("The restaurant does not exist!!!");
			return false;
		}
	}

	/**
	 * This method will delete the restaurant's menu file
	 * 
	 * @param restaurant
	 */
	private void deleteRestaurantMenus(String restaurant,File restaurantsFile,String folderPath) {
		if (checkIfRestaurantExist(restaurant,restaurantsFile) == true) {
			
			File folder = new File(folderPath);

			if (folder.exists() && folder.isDirectory()) {
				File[] files = folder.listFiles();
				for (File file : files) {
					if (file.isFile() && file.getName().endsWith(".csv")) {
						String fileName = file.getName();
						if (restaurant.equalsIgnoreCase(fileName.substring(0, fileName.lastIndexOf('.')))) {
							if (file.delete()) {
								System.out.println("Fișierul " + file.getName() + " a fost șters.");
							} else {
								System.out.println("Nu s-a putut șterge fișierul " + file.getName());
							}
						}
					}
				}
			} else {
				System.out.println("Folderul specificat nu există sau nu este un director.");
			}
		} else {
			System.out.println("The restaurant does not exist");
		}

	}

	/**
	 * This method will delete the restaurant from restaurants file 
	 * @param restaurant
	 * @param resFile
	 */
	private void deleteRestaurantInFile(String restaurant, File resFile) {
		try {

			List<String> fileLines = new ArrayList<>();

			FileReader fileReader = new FileReader(resFile);
			BufferedReader reader = new BufferedReader(fileReader);

			String line;
			while ((line = reader.readLine()) != null) {
				String[] lineContent = line.split(",");
				if (!lineContent[0].equalsIgnoreCase(restaurant)) {
					fileLines.add(line);
				}
			}
			reader.close();
			FileWriter writer = new FileWriter(resFile);
			for (String newLine : fileLines) {
				writer.write(newLine);
				writer.write(System.lineSeparator());
			}
			writer.close();

			System.out.println("The restaurnt was deleted!");
		} catch (IOException e) {
			System.out.println("An error occurred while deleting the restaurant from the restaurants file!");
		}
	}


}
