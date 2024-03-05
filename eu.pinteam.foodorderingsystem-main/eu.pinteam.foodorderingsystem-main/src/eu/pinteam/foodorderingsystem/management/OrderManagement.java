package eu.pinteam.foodorderingsystem.management;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import eu.pinteam.foodorderingsystem.shared.Customer;
import eu.pinteam.foodorderingsystem.shared.Menu;
import eu.pinteam.foodorderingsystem.shared.Order;
import eu.pinteam.foodorderingsystem.shared.Restaurant;

public class OrderManagement {

    public void cancelOrder(File file) throws IOException {
        if (file.exists()) {
            boolean isDeleted = file.delete();
            if (isDeleted) {
                System.out.println("Order cancelled. File deleted.");
            } else {
                System.out.println("Failed to delete the file.");
            }
        } else {
            System.out.println("File does not exist.");
        }
    }

    public void addOrder(Order order, File file) {
        try (PrintWriter printWriter = new PrintWriter(new BufferedWriter(new FileWriter(file, true)))) {
            printWriter.println(order.toString());
            System.out.println("Order successfully added to the order file.");
        } catch (IOException e) {
            System.out.println("Error occurred while adding the order to the file.");
            e.printStackTrace();
        }
    }

    public List<Order> readOrdersFromFile(Customer customer, File file) {
        List<Order> orders = new ArrayList<>();
        List<Order> ordersForCustomer = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    String customerName = parts[0].trim();
                    String menuName = parts[1].trim();
                    String restaurantDetails = parts[2].trim();

                    String[] restaurantInfo = restaurantDetails.split(" ", 2);
                    if (restaurantInfo.length == 2) {
                        String restaurantName = restaurantInfo[0];
                        String phoneNumber = restaurantInfo[1];
                        if (customerName.trim().equalsIgnoreCase(customer.getName().trim())) {
                            Customer customerX = new Customer(customerName);
                            Menu menu = new Menu(menuName, null, null);
                            Restaurant restaurant = new Restaurant(restaurantName, phoneNumber, null);

                            Order order = new Order(customerX, menu, restaurant);
                            orders.add(order);
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (Order order : orders) {
            if (customer.getName().trim().equalsIgnoreCase(order.getCustomer().getName().trim())) {
                ordersForCustomer.add(order);
            }
        }
        return ordersForCustomer;
    }

    public void deleteOneOrder(Customer customer, File file) throws IOException {
        String customerName = customer.getName();
        ArrayList<String> updatedLines = new ArrayList<>();

        try (FileReader fileReader = new FileReader(file);
             BufferedReader bufferedReader = new BufferedReader(fileReader)) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                if (!line.contains(customerName)) {
                    updatedLines.add(line);
                }
            }
        } catch (IOException e) {
            System.out.println("Error occurred while searching and updating the order list.");
            e.printStackTrace();
        }

        try (PrintWriter printWriter = new PrintWriter(new BufferedWriter(new FileWriter(file, false)))) {
            for (String updatedLine : updatedLines) {
                printWriter.println(updatedLine);
            }
            System.out.println("List updated.");
        } catch (IOException e) {
            System.out.println("Error occurred while writing the updated list to the file.");
            e.printStackTrace();
        }
    }
}