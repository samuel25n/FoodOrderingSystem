package eu.pinteam.rcpfos.views;

import java.rmi.RemoteException;
import java.util.List;

import javax.inject.Inject;

import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.part.ViewPart;

import eu.pinteam.rcpfos.activator.Activator;
import eu.pinteam.rcpfos.checkBox.Checkbox;
import eu.pinteam.rcpfos.coolbar.CreateCoolBar;
import eu.pinteam.rcpfos.dialogs.CalendarDialog;
import eu.pinteam.rcpfos.dialogs.ClockDialog;
import eu.pinteam.rcpfos.management.RestaurantManagement;
import eu.pinteam.rcpfos.path.PathHandler;
import eu.pinteam.rcpfos.restaurantTreeViewer.RestaurantTreeViewerContentProvider;
import eu.pinteam.rcpfos.restaurantTreeViewer.RestaurantTreeViewerHandler;
import eu.pinteam.rcpfos.restaurantTreeViewer.RestaurantTreeViewerLabelProvider;
import eu.pinteam.rcpfos.widgets.CalculatorWidget;
import fosemf.Customer;
import fosemf.FosemfFactory;
import fosemf.Menu;
import fosemf.Restaurant;


public class RestaurantView extends ViewPart {
	public static final String ID = "foss.view";

	@Inject
	IWorkbench workbench;
	private Shell shell;
	private TreeViewer treeViewer;
	private ClockDialog dialog;
	private CalendarDialog calendarDialog;
	private Activator activator = Activator.getDefault();
	@SuppressWarnings("static-access")
	private RestaurantManagement restaurantManagement = activator.getRestaurantManagement();

	private static RestaurantView instance;

	private CalculatorWidget calculatorWidget;

	private RestaurantTreeViewerHandler restaurantTreeViewer;
	@Override
	public void createPartControl(Composite parent) {
		
	
		createMenus();
		
		createCustomers();
		
		setupLayout(parent);
		createCoolBar(parent);
		restaurantTreeViewer = new RestaurantTreeViewerHandler(parent);
		treeViewer = restaurantTreeViewer.getTreeViewer();
		treeViewer.setContentProvider(new RestaurantTreeViewerContentProvider());
		treeViewer.setLabelProvider(new RestaurantTreeViewerLabelProvider());
		try {
			List<Restaurant> restaurantsInput = getRestaurants();
			restaurantTreeViewer.setTreeViewerInput(restaurantsInput);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		restaurantTreeViewer.addSelectionChangedListener();
		restaurantTreeViewer.createTreeViewerContextMenu(getSite());
		createCheckBox(parent);
		parent.layout();
	}
	
	public void createMenus(){
		
		Menu menu1 = FosemfFactory.eINSTANCE.createMenu();
        menu1.setName("Donner Kebap");
        menu1.setPrice(22);
        menu1.setRating(1);
        menu1.setImageRating(3);
        
		Menu menu2 = FosemfFactory.eINSTANCE.createMenu();
        menu2.setName("Pizza Margherita");
        menu2.setPicture(PathHandler.getAbsolutePath()+"images\\delcorso.pizza.jpg");
        menu2.setPrice(21);
        menu2.setRating(2);
        menu2.setImageRating(2);
        
		Menu menu3 = FosemfFactory.eINSTANCE.createMenu();
        menu3.setName("Pizza DelCorso");
        menu3.setPicture(PathHandler.getAbsolutePath()+"images\\delcorso.pizza2.jpg");
        menu3.setPrice(5);
        menu3.setRating(3);
        menu3.setImageRating(3);
        
		Menu menu4 = FosemfFactory.eINSTANCE.createMenu();
        menu4.setName("Zinger Menu");
        menu4.setPicture(PathHandler.getAbsolutePath()+"images\\kfc.meniu.jpg");
        menu4.setPrice(45);
        menu4.setRating(5);
        menu4.setImageRating(3);
        
		Menu menu5 = FosemfFactory.eINSTANCE.createMenu();
        menu5.setName("Crispy Strips (Radu's favorite)");
        menu5.setPicture(PathHandler.getAbsolutePath()+"images\\mac.meniu.jpg");
        menu5.setPrice(23);
        menu5.setRating(5);
        menu5.setImageRating(5);
        
		Menu menu6 = FosemfFactory.eINSTANCE.createMenu();
        menu6.setName("McCombo");
        menu6.setPicture(PathHandler.getAbsolutePath()+"images\\mac.meniu.jpg");
        menu6.setPrice(42);
        menu6.setRating(2);
        menu6.setImageRating(5);
        
		Menu menu7 = FosemfFactory.eINSTANCE.createMenu();
        menu7.setName("Mc Chicken");
        menu7.setPicture(PathHandler.getAbsolutePath()+"images\\mac.meniu2.jpg");
        menu7.setPrice(12);
        menu7.setRating(4);
        menu7.setImageRating(1);
        
		Menu menu8 = FosemfFactory.eINSTANCE.createMenu();
        menu8.setName("BigMac");
        menu8.setPicture(PathHandler.getAbsolutePath()+"images\\mac.meniu3.jpg");
        menu8.setPrice(34);
        menu8.setRating(3);
        menu8.setImageRating(2);
        
		Menu menu9 = FosemfFactory.eINSTANCE.createMenu();
        menu9.setName("Burger de vita");
        menu9.setPicture(PathHandler.getAbsolutePath()+"images\\naomi.burger.jpg");
        menu9.setPrice(18);
        menu9.setRating(3);
        menu9.setImageRating(2);
        
		Menu menu10 = FosemfFactory.eINSTANCE.createMenu();
        menu10.setName("Burger de pui");
        menu10.setPicture(PathHandler.getAbsolutePath()+"images\\naomi.burger2.jpg");
        menu10.setPrice(43);
        menu10.setRating(4);
        menu10.setImageRating(3);
        
		Menu menu11 = FosemfFactory.eINSTANCE.createMenu();
        menu11.setName("Burger de post");
        menu11.setPicture(PathHandler.getAbsolutePath()+"images\\naomi.burger3.jpg");
        menu11.setPrice(21);
        menu11.setRating(4);
        menu11.setImageRating(5);
        
		Menu menu12 = FosemfFactory.eINSTANCE.createMenu();
        menu12.setName("Antricot de vita");
        menu12.setPicture(PathHandler.getAbsolutePath()+"images\\ruth.antricot.jpg");
        menu12.setPrice(66);
        menu12.setRating(2);
        menu12.setImageRating(3);
        
		Menu menu13= FosemfFactory.eINSTANCE.createMenu();
        menu13.setName("Pizza quatro formaggi");
        menu13.setPicture(PathHandler.getAbsolutePath()+"images\\ruth.pizza.jpg");
        menu13.setPrice(22);
        menu13.setRating(3);
        menu13.setImageRating(1);
        
        createRestaurants(menu1, menu2, menu3, menu4, menu5, menu6, menu7, menu8, menu9, menu10, menu11, menu12, menu13);
        
	}
	
	public void createRestaurants(Menu menu1, Menu menu2, Menu menu3, Menu menu4, Menu menu5, Menu menu6, Menu menu7, Menu menu8, Menu menu9, Menu menu10, Menu menu11, Menu menu12, Menu menu13) {
		
		Restaurant restaurant1 = FosemfFactory.eINSTANCE.createRestaurant();
		Restaurant restaurant2 = FosemfFactory.eINSTANCE.createRestaurant();
		Restaurant restaurant3 = FosemfFactory.eINSTANCE.createRestaurant();
		Restaurant restaurant4 = FosemfFactory.eINSTANCE.createRestaurant();
		Restaurant restaurant5 = FosemfFactory.eINSTANCE.createRestaurant();
		Restaurant restaurant6 = FosemfFactory.eINSTANCE.createRestaurant();
		
		int id=1;

		restaurant1.setName("Antalya");
		restaurant1.setPhone("712345677");
		restaurant1.setRating(1);
		restaurant1.setId(id);
		id++;

		restaurant2.setName("Del Corso");
		restaurant2.setPhone("712345678");
		restaurant2.setRating(2);
		restaurant2.setId(id);
		id++;

		restaurant3.setName("KFC");
		restaurant3.setPhone("744745544");
		restaurant3.setRating(5);
		restaurant3.setId(id);
		id++;
		
		restaurant4.setName("McDonald's");
		restaurant4.setPhone("711122244");
		restaurant4.setRating(4);
		restaurant4.setId(id);
		id++;

		restaurant5.setName("Naomi");
		restaurant5.setPhone("744254758");
		restaurant5.setRating(5);
		restaurant5.setId(id);
		id++;

		restaurant6.setName("Ruth");
		restaurant6.setPhone("712412351");
		restaurant6.setRating(3);
		restaurant6.setId(id);
		id++;

		restaurantManagement.addRestaurant(restaurant1);
		restaurantManagement.addRestaurant(restaurant2);
		restaurantManagement.addRestaurant(restaurant3);
		restaurantManagement.addRestaurant(restaurant4);
		restaurantManagement.addRestaurant(restaurant5);
		restaurantManagement.addRestaurant(restaurant6);
		restaurantManagement.addRestaurantMenu(restaurant1,menu1);
		restaurantManagement.addRestaurantMenu(restaurant2,menu2);
		restaurantManagement.addRestaurantMenu(restaurant2,menu3);
		restaurantManagement.addRestaurantMenu(restaurant3,menu4);
		restaurantManagement.addRestaurantMenu(restaurant3,menu5);
		restaurantManagement.addRestaurantMenu(restaurant4,menu6);
		restaurantManagement.addRestaurantMenu(restaurant4,menu7);
		restaurantManagement.addRestaurantMenu(restaurant4,menu8);
		restaurantManagement.addRestaurantMenu(restaurant5,menu9);
		restaurantManagement.addRestaurantMenu(restaurant5,menu10);
		restaurantManagement.addRestaurantMenu(restaurant5,menu11);
		restaurantManagement.addRestaurantMenu(restaurant6,menu12);
		restaurantManagement.addRestaurantMenu(restaurant6,menu13);
		
		
	}
	
	public void createCustomers() {
		
		Customer customer1 = FosemfFactory.eINSTANCE.createCustomer();
		Customer customer2 = FosemfFactory.eINSTANCE.createCustomer();
		Customer customer3 = FosemfFactory.eINSTANCE.createCustomer();
		
		customer1.setId(1);
		customer1.setName("Andra");
		
		customer2.setId(2);
		customer2.setName("Sami");
		
		customer3.setId(3);
		customer3.setName("Nadia");
		
	}
	

	private void setupLayout(Composite parent) {
		GridLayout layout = new GridLayout(3, false);
		parent.setLayout(layout);
	}

	private void createCoolBar(Composite parent) {
		
		CreateCoolBar createCoolBar = new CreateCoolBar(parent, shell, new PathHandler(), dialog, calendarDialog, calculatorWidget);
		createCoolBar.createCoolBar();
	}

	private void createCheckBox(Composite parent) {
		Canvas canvas = new Canvas(parent, SWT.NO_REDRAW_RESIZE);
		GridData layoutData = new GridData();
        layoutData.widthHint = 100; 
        layoutData.heightHint = 60;  
		canvas.setLayoutData(layoutData);
		canvas.setLayout(new FillLayout());

		@SuppressWarnings("unused")
		Checkbox checkboxComposite = new Checkbox(canvas, SWT.CENTER);

	}

	@Override
	public void setFocus() {
		treeViewer.getControl().setFocus();
	}

	public List<Restaurant> getRestaurants() throws RemoteException {
		 List<Restaurant> r=restaurantManagement.getListOfRestaurants();
		
		 return r;
	}

	public void refreshViewer() {
		treeViewer.setContentProvider(new RestaurantTreeViewerContentProvider());
		treeViewer.setLabelProvider(new RestaurantTreeViewerLabelProvider());
		try {
			List<Restaurant> restaurantsInput = getRestaurants();
			restaurantTreeViewer.setTreeViewerInput(restaurantsInput);
			treeViewer.refresh();
			treeViewer.refresh(true);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public RestaurantView() {
		instance = this;
	}

	public static RestaurantView getInstance() {
		return instance;
	}

	

}

