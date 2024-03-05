package eu.pinteam.rcpfos.management;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;

import fosemf.Customer;
import fosemf.Order;

public class OrderManagement {

    private Resource resource;
    private ResourceSet resourceSet;

    public OrderManagement(String file) {
    	
        File xmiFile = new File(file);
        resourceSet = new ResourceSetImpl();

        if (xmiFile.exists()) {
            this.resource = resourceSet.getResource(URI.createFileURI(file), true);
            try {
                resource.load(null);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            resource = resourceSet.createResource(URI.createFileURI(file));
        }
    }

    public boolean addOrder(Order order) {
        resource.getContents().add(order);
        save();
        return true;
    }

    public void deleteOneOrder(Customer customer, Order orderToDelete) {
        for (EObject obj : resource.getContents()) {
            if (obj instanceof Customer) {
                Customer c = (Customer) obj;
                if (customer.getName().trim().equalsIgnoreCase(c.getName().trim())) {
                    List<Order> orders = c.getOrders();
                    if (orders != null) {
                        orders.remove(orderToDelete);
                        save();
                    }
                }
            }
        }
    }

    private void save() {
        try {
            resource.save(null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean validateName(String name) {
        String pattern = "^[a-zA-Z\\s']+$";
        Pattern regex = Pattern.compile(pattern);
        Matcher matcher = regex.matcher(name);
        return matcher.matches();
    }
}
