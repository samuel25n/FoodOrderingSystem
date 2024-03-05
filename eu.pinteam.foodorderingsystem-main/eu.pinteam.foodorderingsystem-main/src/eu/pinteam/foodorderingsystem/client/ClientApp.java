package eu.pinteam.foodorderingsystem.client;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import eu.pinteam.foodorderingsystem.service.IClientService;
import eu.pinteam.foodorderingsystem.shared.Customer;
import eu.pinteam.foodorderingsystem.shared.Menu;
import eu.pinteam.foodorderingsystem.shared.Order;
import eu.pinteam.foodorderingsystem.shared.Restaurant;

public class ClientApp {
    private Shell shell;
    private IClientService server;
    private List<Restaurant> restaurantsList;
    private Label labelImage;
    private Display display;
    private Customer customer;
    private List<Menu> listOfMenus;
    private Restaurant restaurantForOrder;
    private ListViewer restaurantsListViewer;
    private ListViewer menusListViewer;
    private Menu selectedMenu;
    private boolean isRestaurantShell = true;
    private Button historyButton;
    private Label menuImageLabel;
    private boolean nameInputComplete = false;
    private Composite nameInputComposite;
    private Composite restaurantComposite;

    public static void main(String[] args) {
        new ClientApp().run();
    }

    public void run() {
        display = new Display(); 
        shell = new Shell(display);
        shell.setText("Food Ordering System");
        shell.setSize(670, 590);
        shell.setLayout(new GridLayout());
        shell.setMaximumSize(670, 590);
        shell.setMinimumSize(670, 590);
        shell.setImage(new Image(display, "images\\icon.png"));
        try {
            server = (IClientService) Naming.lookup("rmi://localhost:7777/clientServer");
        } catch (Exception e) {
            MessageBox dialog = new MessageBox(shell, SWT.ICON_ERROR | SWT.OK);
            dialog.setMessage("The server is down. Contact administrator");
            dialog.setText("Server error");
            dialog.open();
            shell.close();
            e.printStackTrace();
        }
        Thread serverStatusThread = new Thread(() -> {
            while (!shell.isDisposed()) {
                try {
                    Thread.sleep(1000);
                    server.ping(); 
                } catch (Exception e) {
                    display.syncExec(() -> {
                        MessageBox dialog = new MessageBox(shell, SWT.ICON_ERROR | SWT.OK);
                        dialog.setMessage("The server was shutted down by the administrator. The app will be closed in 3 seconds");
                        dialog.setText("Server error");
                        dialog.open();
                        try {
							Thread.sleep(3000);
						} catch (InterruptedException e1) {
							e1.printStackTrace();
						}
                        shell.close();
                    });
                    return;
                }
            }
        });
        serverStatusThread.setDaemon(true);
        serverStatusThread.start();

        createNameInputSection();
        if(server!=null) {
        	shell.open();
        	
        }
        
        while (!shell.isDisposed()) {
            if (!display.readAndDispatch()) {
                display.sleep();
            }
        }
        display.dispose();
    }

    private void createNameInputSection() {
        nameInputComposite = new Composite(shell, SWT.NONE);
        nameInputComposite.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, true));
        nameInputComposite.setLayout(new GridLayout(1, false));
        
        Image startImage = new Image(display, "images\\start.png");
        Label imgLabel = new Label(nameInputComposite, SWT.CENTER);
        imgLabel.setImage(new Image(display, startImage.getImageData().scaledTo(200, 200)));
        GridData imgLabelGridData = new GridData(SWT.CENTER, SWT.CENTER, true, false);
        imgLabel.setLayoutData(imgLabelGridData);
        
        
        

        Label nameLabel = new Label(nameInputComposite, SWT.CENTER);
        nameLabel.setText("Please write your first and last name:\n *Only letters allowed\n *Make sure you write the full name (first and last name)");

        Text nameText = new Text(nameInputComposite, SWT.BORDER);
        nameText.setText("");
        GridData textGridData = new GridData(SWT.FILL, SWT.CENTER, true, false);
        textGridData.widthHint = 200;
        nameText.setLayoutData(textGridData);

        Button submitButton = new Button(nameInputComposite, SWT.PUSH);
        submitButton.setText("Continue");
        submitButton.setForeground(display.getSystemColor(SWT.COLOR_WHITE));
        submitButton.setBackground(display.getSystemColor(SWT.COLOR_GRAY));
        submitButton.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, false));
        Font boldFont = new Font(display, "Arial", 14, SWT.BOLD);
        submitButton.setFont(boldFont);
        submitButton.setVisible(false);
        
        nameText.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent event) {
                if (event.character == SWT.CR || event.character == SWT.LF) {
                    if (submitButton.getVisible()) {
                        submitButton.notifyListeners(SWT.Selection, new Event());
                    }
                }
            }
        });
        
        ModifyListener textModifyListener = new ModifyListener() {
            @Override
            public void modifyText(ModifyEvent event) {
                String[] name = nameText.getText().split(" ");
                if (name.length >= 2 && !nameText.getText().isEmpty() && nameText.getText().matches("[a-zA-Z ]+")) {
                    submitButton.setVisible(true);
                    imgLabel.setImage(new Image(display, new Image(display, "images\\start2.png").getImageData().scaledTo(200, 200)));
                    
                }else {
					submitButton.setVisible(false);
					imgLabel.setImage(new Image(display, startImage.getImageData().scaledTo(200, 200)));
				}
            }
        };
        nameText.addModifyListener(textModifyListener);
		nameText.addModifyListener(textModifyListener);
        
        submitButton.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                if (!nameText.getText().isEmpty() && nameText.getText().matches("[a-zA-Z ]+")) {
                    customer = new Customer(nameText.getText());
                    nameInputComplete = true;
                    nameInputComposite.dispose();
                    createRestaurantSection();
                    populateRestaurantList();
                    shell.layout();
                } else {
                    MessageBox dialog = new MessageBox(shell, SWT.ICON_QUESTION | SWT.OK);
                    dialog.setMessage("The name should contain first and last name and ONLY LETTERS");
                    dialog.setText("Name error");
                    dialog.open();
                    nameText.setText("");
                }
            }
        });

        Rectangle bounds = display.getPrimaryMonitor().getBounds();
        Rectangle rect = shell.getBounds();
        int x = bounds.x + (bounds.width - rect.width) / 2;
        int y = bounds.y + (bounds.height - rect.height) / 2;
        shell.setLocation(x, y);
    }

    private void createRestaurantSection() {
        restaurantComposite = new Composite(shell, SWT.NONE);
        restaurantComposite.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, true));
        restaurantComposite.setLayout(new GridLayout(2, false));

        labelImage = new Label(restaurantComposite, SWT.NONE);
        labelImage.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 3));
        setDefaultImage();

        restaurantsListViewer = new ListViewer(restaurantComposite, SWT.BORDER | SWT.V_SCROLL);
        GridData restaurantListData = new GridData(SWT.FILL, SWT.FILL, true, true);
        restaurantListData.widthHint = 200;
        restaurantListData.heightHint = 400;
        restaurantsListViewer.getControl().setLayoutData(restaurantListData);
        restaurantsListViewer.setContentProvider(ArrayContentProvider.getInstance());
        restaurantsListViewer.setLabelProvider(new LabelProvider() {
            @Override
            public String getText(Object element) {
                if (element instanceof Restaurant) {
                    Restaurant restaurant = (Restaurant) element;
                    return restaurant.getName();
                }
                return super.getText(element);
            }
        });
        addRestaurantSelectionListener();

        menusListViewer = new ListViewer(restaurantComposite, SWT.BORDER | SWT.V_SCROLL);
        GridData menuListData = new GridData(SWT.FILL, SWT.FILL, true, true);
        menuListData.widthHint = 200;
        menuListData.heightHint = 400;
        menusListViewer.getControl().setLayoutData(menuListData);
        menusListViewer.setContentProvider(ArrayContentProvider.getInstance());
        menusListViewer.setLabelProvider(new LabelProvider() {
            @Override
            public String getText(Object element) {
                if (element instanceof Menu) {
                    Menu menu = (Menu) element;
                    return menu.getName();
                }
                return super.getText(element);
            }
        });
        addMenuSelectionListener();

        menuImageLabel = new Label(restaurantComposite, SWT.NONE);
        menuImageLabel.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false));

        createSendOrderButton(restaurantComposite);
        createOrderHistoryButton(restaurantComposite);
    }

    private void setDefaultImage() {
        Image image = new Image(display, "images\\defaultimage.png");
        image.isAutoScalable();
        labelImage.setImage(new Image(display, image.getImageData().scaledTo(400, 400)));
        labelImage.setImage(image);
    }

    private void addRestaurantSelectionListener() {
        restaurantsListViewer.addSelectionChangedListener(new ISelectionChangedListener() {
        
            public void selectionChanged(SelectionChangedEvent event) {
            	populateRestaurantList();
                IStructuredSelection selection = (IStructuredSelection) event.getSelection();
                Object selectedElement = selection.getFirstElement();

                if (selectedElement instanceof Restaurant) {
                    Restaurant selectedRestaurant = (Restaurant) selectedElement;
                    restaurantForOrder = selectedRestaurant;
                    populateMenuList(selectedRestaurant);
                }
            }
        });
    }

    private void addMenuSelectionListener() {
        menusListViewer.addSelectionChangedListener(new ISelectionChangedListener() {
            public void selectionChanged(SelectionChangedEvent event) {
            	populateRestaurantList();
                IStructuredSelection selection = (IStructuredSelection) event.getSelection();
                Object selectedElement = selection.getFirstElement();

                if (selectedElement instanceof Menu) {
                    Menu selectedMenu = (Menu) selectedElement;
                    setSelectedMenu(selectedMenu);
                    displayMenuImage(selectedMenu);
                } else {
                    setSelectedMenu(null); // No menu selected
                    menuImageLabel.setImage(new Image(display, "images\\defaultimage.png"));
                    menuImageLabel.setVisible(false); // Hide the menu image label
                }
            }
        });
    }

    public void populateRestaurantList() {
        try {
            restaurantsList = server.getRestaurants();
            restaurantsListViewer.setInput(restaurantsList);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private void populateMenuList(Restaurant restaurant) {
        try {
            listOfMenus = server.getListOfMenus(restaurant);
            menusListViewer.setInput(listOfMenus);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private void setSelectedMenu(Menu menu) {
        selectedMenu = menu;
    }

    private void displayMenuImage(Menu menu) {
        if (menu == null || menu.getPicture() == null) {
            return;
        }

        try {
            Image image = new Image(display, menu.getPicture());
            labelImage.setImage(new Image(display, image.getImageData().scaledTo(400, 400)));
            menuImageLabel.setImage(null); // Clear the menu image label
        } catch (Exception e) {
        	Image image = new Image(display, "images\\notAvailable.jpg");
			labelImage.setImage(new Image(display, image.getImageData().scaledTo(400, 400)));
			menuImageLabel.setImage(null);
        }
    }


    private void createSendOrderButton(Composite parent) {
        Button sendOrderButton = new Button(parent, SWT.PUSH);
        sendOrderButton.setText("Send Order");
        sendOrderButton.setBackground(display.getSystemColor(SWT.COLOR_GRAY));
        sendOrderButton.setFont(new Font(display, "Arial", 12, SWT.BOLD));
        sendOrderButton.setForeground(display.getSystemColor(SWT.COLOR_WHITE));
        sendOrderButton.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false));
        sendOrderButton.addSelectionListener(new SelectionAdapter() {
            @SuppressWarnings("unused")
            @Override
            public void widgetSelected(SelectionEvent event) {
                if (!nameInputComplete) {
                    MessageBox dialog = new MessageBox(shell, SWT.ICON_ERROR | SWT.OK);
                    dialog.setMessage("Please complete the name input first.");
                    dialog.setText("Name input required");
                    dialog.open();
                    return;
                }

                Menu menu = selectedMenu;
                if (menu == null) {
                    MessageBox dialog = new MessageBox(shell, SWT.ICON_ERROR | SWT.OK);
                    dialog.setMessage("Don't forget to select a restaurant and a menu!");
                    dialog.setText("Select a menu!");
                    dialog.open();
                } else {
                    if (customer == null) {
                        System.out.println("CustomerNull");
                    } else if (restaurantForOrder == null) {
                        System.out.println("RestaurantNull");
                    } else if (menu == null) {
                        System.out.println("MenuNull");
                    } else {
                        Order order = new Order(customer, menu, restaurantForOrder);
                        try {
                            server.addOrder(order);
                            server.addOrderToAllOrdersFile(order);
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                        MessageBox dialog = new MessageBox(shell, SWT.ICON_QUESTION | SWT.OK);
                        dialog.setMessage("Order successfully sent! You can still add another order\n\n");
                        dialog.setText("Order sent");
                        dialog.open();
                    }
                }
            }
        });
    }

    private void createOrderHistoryButton(Composite parent) {
        historyButton = new Button(parent, SWT.PUSH);
        historyButton.setBackground(display.getSystemColor(SWT.COLOR_GRAY));
        historyButton.setFont(new Font(display, "Arial", 12, SWT.BOLD));
        historyButton.setText("Istoric comenzi");
        historyButton.setForeground(display.getSystemColor(SWT.COLOR_WHITE));
        historyButton.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false));
        historyButton.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent event) {
                if (isRestaurantShell) {
                    showOrderHistory();
                } else {
                    isRestaurantShell = true;
                    historyButton.setText("Istoric comenzi");
                    menusListViewer.getControl().setEnabled(true);
                    populateMenuList(restaurantForOrder);
                }
            }
        });
    }

    private void showOrderHistory() {
        // Hide the restaurant composite
        restaurantComposite.setVisible(false);

        // Create the history composite
        Composite historyComposite = new Composite(shell, SWT.NONE);
        historyComposite.setLayoutData(new GridData(SWT.TOP, SWT.CENTER, true, false));
        historyComposite.setLayout(new GridLayout());

        Label orderHistoryLabel = new Label(historyComposite, SWT.WRAP);
        orderHistoryLabel.setText("Istoricul comenzilor dumneavoastra");
        Font font = new Font(display, "Arial", 12, SWT.BOLD);
        orderHistoryLabel.setFont(font);
        Color grayColor = display.getSystemColor(SWT.COLOR_GRAY);
        orderHistoryLabel.setForeground(grayColor);

        ListViewer orderHistoryViewer = new ListViewer(historyComposite, SWT.BORDER | SWT.V_SCROLL);
        orderHistoryViewer.getControl().setSize(600, 500);
        GridData viewerGridData = new GridData(SWT.CENTER, SWT.TOP, true, true);
        viewerGridData.widthHint = 600;
        viewerGridData.heightHint = 460;
        orderHistoryViewer.getControl().setLayoutData(viewerGridData);
        orderHistoryViewer.setContentProvider(ArrayContentProvider.getInstance());
        orderHistoryViewer.setLabelProvider(new LabelProvider() {
            @Override
            public String getText(Object element) {
                if (element instanceof Order) {
                    Order order = (Order) element;
                    return "Comanda: " + order.getRestaurant() + ", " + order.getMenu();
                }
                return super.getText(element);
            }
        });
        List<Order> orders = new ArrayList<>();
        try {
             orders = server.getClientOrders(customer);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        orderHistoryViewer.setInput(orders);

        // Create a button to go back to the restaurant composite
        Button backButton = new Button(historyComposite, SWT.PUSH);
        backButton.setText("Go Back");
        backButton.setLayoutData(new GridData(SWT.CENTER, SWT.BOTTOM, true, false));
        backButton.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                // Show the restaurant composite
                restaurantComposite.setVisible(true);

                // Dispose of the history composite
                historyComposite.dispose();

                // Update the shell layout
                shell.layout(true, true);
            }
        });

        // Update the shell layout
        shell.layout(true, true);
    }








}
