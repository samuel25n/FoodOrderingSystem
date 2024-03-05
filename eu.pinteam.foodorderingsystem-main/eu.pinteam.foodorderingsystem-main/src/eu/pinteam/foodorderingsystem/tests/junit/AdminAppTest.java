package eu.pinteam.foodorderingsystem.tests.junit;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.rmi.Naming;

import org.junit.Before;
import org.junit.Test;

import eu.pinteam.foodorderingsystem.admin.AdminApp;
import eu.pinteam.foodorderingsystem.service.IAdminService;

public class AdminAppTest {
    private AdminApp adminAppTest;
    private IAdminService server;

    @Before
    public void setUpBeforeClass() throws Exception {
        adminAppTest = new AdminApp();
        server = (IAdminService) Naming.lookup("rmi://localhost:7777/adminServer");
        adminAppTest.setServer(server);
    }

    @Test
    public void testDisplayListOfRestaurants() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        PrintStream originalPrintStream = System.out;
        System.setOut(printStream);

        adminAppTest.displayListOfRestaurants();

        System.out.flush();
        System.setOut(originalPrintStream);

        String output = outputStream.toString().trim();
        String expectedOutput = "Name: Antalya Phone: 712345677\r\n"
        		+ "Menus: Donner Kebap-22.0 ron, \n"
        		+ "\r\n"
        		+ "Name: Del Corso Phone: 712345678\r\n"
        		+ "Menus: Pizza Margherita-21.0 ron, Pizza DelCorso-5.0 ron, \n"
        		+ "\r\n"
        		+ "Name: KFC Phone: 744745544\r\n"
        		+ "Menus: Zinger Menu-45.0 ron, Crispy Strips (Radu's favorite)-23.0 ron, \n"
        		+ "\r\n"
        		+ "Name: McDonald's Phone: 711122244\r\n"
        		+ "Menus: McCombo-42.0 ron, Mc Chicken-12.0 ron, BigMac-34.0 ron, \n"
        		+ "\r\n"
        		+ "Name: Naomi Phone: 744254758\r\n"
        		+ "Menus: Burger de vita-18.0 ron, Burger de pui-43.0 ron, Burger de post-23.0 ron, \n"
        		+ "\r\n"
        		+ "Name: Ruth Phone: 712412351\r\n"
        		+ "Menus: Pizza quatro formaggi-22.0 ron, Antricot de vita-66.0 ron,";

        //assertEquals(expectedOutput, output);
    }
    
    @Test
    public void testCloseApp() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        PrintStream originalPrintStream = System.out;
        System.setOut(printStream);

        adminAppTest.closeApp(true);

        System.out.flush();
        System.setOut(originalPrintStream);

        String output = outputStream.toString().trim();
        String expectedOutput = "Goodbye!";

        assertEquals(expectedOutput, output);
    }
    
    @Test
    public void testCancelOrder() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        PrintStream originalPrintStream = System.out;
        System.setOut(printStream);

        boolean ok =adminAppTest.cancelOrder("05.07.2023.txt");

        System.out.flush();
        System.setOut(originalPrintStream);

        String output = outputStream.toString().trim();
        String expectedOutput = "Order cancelled!";

        if(ok==true)
        assertEquals(expectedOutput, output);
        else assertEquals("The order cannot be cancelled, make sure you wrote the date correctly", output);
    }
      
    @Test
    public void testAddRestaurant() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        PrintStream originalPrintStream = System.out;
        System.setOut(printStream);

        boolean ok=adminAppTest.addRestaurant("Restaurant nou", "0777785");

        System.out.flush();
        System.setOut(originalPrintStream);

        String output = outputStream.toString().trim();
        String expectedOutput = "Restaurant added!";

        if(ok==true)
        assertEquals(expectedOutput, output);
        else assertEquals("Can't add the restaurant!", output);
    }
    
    @Test
    public void testAddMenu() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        PrintStream originalPrintStream = System.out;
        System.setOut(printStream);

        boolean ok=adminAppTest.addMenu("Ruth", "Meniu nou", null , (double)5);

        System.out.flush();
        System.setOut(originalPrintStream);

        String output = outputStream.toString().trim();
        String expectedOutput = "Menu added!";

        if(ok==true)
        assertEquals(expectedOutput, output);
        else assertEquals("Can't add the menu. Check if menu already exists.", output);
    }
   
    @Test
    public void testDeleteMenu() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        PrintStream originalPrintStream = System.out;
        System.setOut(printStream);

        boolean ok=adminAppTest.deleteMenu("Ruth", "Burger de post");

        System.out.flush();
        System.setOut(originalPrintStream);

        String output = outputStream.toString().trim();
        String expectedOutput = "Menu added!";

        if(ok==true)
        assertEquals(expectedOutput, output);
        else assertEquals("The menu does not exist!", output);
    }
    @Test
    public void testDeleteRestaurant() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        PrintStream originalPrintStream = System.out;
        System.setOut(printStream);

        boolean ok=adminAppTest.deleteRestaurant("Naomi");

        System.out.flush();
        System.setOut(originalPrintStream);

        String output = outputStream.toString().trim();
        String expectedOutput = "Restaurant deleted!";

        if(ok==true)
        assertEquals(expectedOutput, output);
        else assertEquals("Can't delete the restaurant!", output);
    }
 
}
