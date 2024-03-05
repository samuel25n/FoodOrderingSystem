package eu.pinteam.foodorderingsystem.admin;

import java.rmi.AccessException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import eu.pinteam.foodorderingsystem.server.RunServer;
import eu.pinteam.foodorderingsystem.service.IAdminService;
import eu.pinteam.foodorderingsystem.shared.Menu;
import eu.pinteam.foodorderingsystem.shared.Restaurant;

public class AdminApp {

    private IAdminService server;
    private Scanner scanner;
    private RunServer runServer = new RunServer();

    public void setServer(IAdminService server) {
        this.server = server;
    }

    public static void main(String[] args) throws AccessException, RemoteException, NotBoundException {
        new AdminApp().run();
    }

    public void run() throws AccessException, RemoteException, NotBoundException {
    	int ok=0;
        try {
            server = (IAdminService) Naming.lookup("rmi://localhost:7777/adminServer");
            ok=1;
        } catch (Exception e) {
            System.out.println("The server is down. Contact administrator");
        }
        if(ok==1) {
        	scanner = new Scanner(System.in);
        	adminMenu();
        	scanner.close();
        }
        
    }

    public void adminMenu() throws AccessException, RemoteException, NotBoundException {
        boolean on = true;
        int option;

        while (on) {
        	System.out.println("_____________________________________");
        	System.out.println("   FOOD ORDERING SYSTEM");
        	System.out.println("          _____");
        	System.out.println("         |     |");
        	System.out.println("         |  S A N");
        	System.out.println("         |__ __|");
        	System.out.println();
        	System.out.println("     ¯\\_( ͡~ ͜ʖ ͡°)_/¯");
        	System.out.println();
            System.out.println("1. See the list of restaurants");
            System.out.println("2. Add a restaurant");
            System.out.println("3. Delete a restaurant");
            System.out.println("4. Add a menu for a restaurant");
            System.out.println("5. Delete a menu for a restaurant");
            System.out.println("6. Cancel the order");
            System.out.println("7. Close the application ");
            System.out.println("Choose your option: ");

            if (scanner.hasNextInt()) {
                option = scanner.nextInt();
                scanner.nextLine(); // Consume the newline character after reading the option

                switch (option) {
                    case 1:
                    	for(int i=0;i<100;i++) {
                    		System.out.println();
                    	}
                        displayListOfRestaurants();
                        break;
                    case 2:
                    	for(int i=0;i<100;i++) {
                    		System.out.println();
                    	}
                    	System.out.println("ADD A RESTAURANT\n");
                        System.out.println("Restaurant name: ");
                        String name = scanner.nextLine();
                        System.out.println("Phone number: ");
                        String phone = scanner.nextLine();
                        if(phone.matches("[0-9]+")) {
                        	addRestaurant(name, phone);
                        }else {
                        	System.out.println("Make sure the restaurant contains only digits");
						}
                        
                        break;
                    case 3:
                    	for(int i=0;i<100;i++) {
                    		System.out.println();
                    	}
                    	System.out.println("DELETE A RESTAURANT \n");
                        displayListOfRestaurants();
                        System.out.println("Restaurant name: ");
                        name = scanner.nextLine();
                        try {
                            deleteRestaurant(name);
                        } catch (Exception e) {
                            System.out.println("There was a problem deleting the restaurant. Please make sure you wrote the name correctly");
                        }
                        break;
                    case 4:
                    	for(int i=0;i<100;i++) {
                    		System.out.println();
                    	}
                    	System.out.println("ADD A MENU FOR RESTAURANT\n");
                    	displayListOfRestaurants();
                        System.out.println("Restaurant name: ");
                        name = scanner.nextLine();
                        try {
                            if (server.checkIfRestaurantExist(name)) {
                                System.out.println("Menu name: ");
                                String menuName = scanner.nextLine();
                                System.out.println("Menu picture path: ");
                                String picture = scanner.nextLine();
                                System.out.println("Menu price: ");
                                Double price = scanner.nextDouble();
                                scanner.nextLine(); // Consume the newline character after reading the menu price
                                addMenu(name, menuName, picture, price);
                            } else {
                                System.out.println("There is no restaurant with such name");
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            System.out.println("ERROR adding a menu");
                        }
                        break;
                    case 5:
                    	for(int i=0;i<100;i++) {
                    		System.out.println();
                    	}
                    	System.out.println("DELETE A MENU FOR RESTAURANT \n");
                    	System.out.println("\n");
                    	displayListOfRestaurants();
                        System.out.println("Restaurant name: ");
                        String restaurantName = scanner.nextLine();
                        try {
                            if (server.checkIfRestaurantExist(restaurantName)) {
                                System.out.println("Menu name: ");
                                String menuName = scanner.nextLine();
                                deleteMenu(restaurantName, menuName);
                            } else {
                                System.out.println("The name of the restaurant does not exist! Try again!");
                            }
                        } catch (RemoteException e) {
                            System.out.println("The name of the restaurant does not exist! Try again!");
                            e.printStackTrace();
                        }
                        break;
                    case 6:
                    	
                    	for(int i=0;i<100;i++) {
                    		System.out.println();
                    	}
                    	System.out.println("CANCEL AN ORDER BY DATE\n");
                    	LocalDate date = LocalDate.now();
                    	String month = "0";
                    	if(date.getMonthValue()<10) {
                    		month+=date.getMonthValue();
                    	}
                        System.out.println("Write the order date: DD.MM.YYYY. For example for today write: "+
                        														date.getDayOfMonth()+"."+month+"."+date.getYear());
                        String fileName = scanner.next() + ".txt";
                        cancelOrder(fileName);
                        break;
                    case 7:
                    	
                    	for(int i=0;i<100;i++) {
                    		System.out.println();
                    	}
                    	System.out.println("EXIT");
                        on = closeApp(on);
                        runServer.stopServer();
                        break;
                    default:
                    	for(int i=0;i<100;i++) {
                    		System.out.println();
                    	}
                        System.out.println("This option is not available. Try again!");
                        break;
                }
            } else {
                String invalidInput = scanner.next();
                System.out.println("Invalid input: " + invalidInput + ". Please enter a valid option.");
            }
        }
    }

    public void displayListOfRestaurants() {
        List<Restaurant> listOfRestaurants = new ArrayList<>();
        try {
            listOfRestaurants = server.getRestaurants();
            for (Restaurant r : listOfRestaurants) {
                System.out.println("Name: " + r.getName() + " Phone: " + r.getPhone());
                System.out.print("Menus: ");
                for (Menu m : server.getListOfMenus(r)) {
                    System.out.print(m.getName() + "-" + m.getPrice() + " ron, ");
                }
                System.out.println("\n");
            }
            System.out.println();
        } catch (RemoteException e) {
            System.out.println("0 restaurants available!");
            e.printStackTrace();
        }
    }

    public boolean closeApp(boolean on) {
        System.out.print("Goodbye!");
        on = false;
        return on;
    }

    public boolean cancelOrder(String fileName) {
        try {
            boolean ok = server.deleteOrderFile("files//" + fileName);
            if (ok) {
                System.out.println("Order cancelled!");
                return ok;}
            else {
                System.out.println("The order cannot be cancelled, make sure you wrote the date correctly");
                return ok;
            }
        } catch (RemoteException e) {
            System.out.println("Delete order error");
            e.printStackTrace();
        }
        return false;
    }

    public boolean addRestaurant(String name, String phone) {
        Restaurant newRestaurant = new Restaurant(name, phone, new ArrayList<>());
        try {
            boolean ok = server.addNewRestaurant(newRestaurant);
            if (ok == true) {
                System.out.println("Restaurant added!");
                return ok;}
            else {
                System.out.println("Can't add the restaurant!");
                return ok;
            }
        } catch (RemoteException e) {
            System.out.println("Add new restaurant rejection");
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteRestaurant(String name) {
        try {
            boolean ok = server.deleteRestaurant(name);
            if (ok) {
                System.out.println("Restaurant deleted!");
                return ok;
            }
            else {
                System.out.println("Can't delete the restaurant!");
                return ok;
            }
        } catch (RemoteException e) {
            System.out.println("Restaurant deleted fail!");
            e.printStackTrace();
        }
        return false;
    }

    public boolean addMenu(String name, String menuName, String picture, Double price) {
        Menu newMenu = new Menu(menuName, picture, price);
        try {
            boolean ok = server.addNewMenu(name, newMenu);
            if (ok) {
                System.out.println("Menu added!");
                return ok;
            }
            else {
                System.out.println("Can't add the menu. Check if menu already exists.");
                return ok;
            }
        } catch (RemoteException e) {
            System.out.println("Menu added fail!");
        }
        return false;
    }

    public boolean deleteMenu(String restaurantName, String menuName) {
        try {
            boolean deleted = server.deleteMenu(restaurantName, menuName);
            if (deleted) {
                System.out.println("Menu deleted!");
                return deleted;
            } else {
                System.out.println("The menu does not exist!");
                return deleted;}
        } catch (RemoteException e) {
            System.out.println("Error deleting the menu");
            e.printStackTrace();
        }
        return false;
    }
}