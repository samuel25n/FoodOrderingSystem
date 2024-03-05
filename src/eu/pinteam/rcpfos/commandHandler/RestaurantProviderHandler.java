package eu.pinteam.rcpfos.commandHandler;

 

import java.io.File;

 

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.commands.IHandlerListener;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;

 

import eu.pinteam.extensionpoint.definition.EvaluateContributionsHandler;
import eu.pinteam.extensionpoint.definition.IRestaurantProvider;
import eu.pinteam.rcpfos.activator.Activator;
import eu.pinteam.rcpfos.management.RestaurantManagement;
import eu.pinteam.rcpfos.path.PathHandler;
import fosemf.FosemfFactory;
import fosemf.Restaurant;

 

public class RestaurantProviderHandler extends EvaluateContributionsHandler implements IRestaurantProvider,IHandler {

 

	@Override
	public void restaurantProvider() {


		Activator activator = Activator.getDefault();
		RestaurantManagement restaurantManagement = activator.getRestaurantManagement();
		
		Restaurant restaurant3 = FosemfFactory.eINSTANCE.createRestaurant();
		Restaurant restaurant2 = FosemfFactory.eINSTANCE.createRestaurant();
		Restaurant restaurant1 = FosemfFactory.eINSTANCE.createRestaurant();

		restaurant1.setName("Jack's");
		restaurant1.setPhone("0744552234");
		restaurant1.setRating(0);

		restaurant3.setName("Test Restaurant");
		restaurant3.setPhone("0744552434");
		restaurant3.setRating(0);

		restaurant2.setName("Baby's");
		restaurant2.setPhone("0744552134");
		restaurant2.setRating(0);

		restaurantManagement.addRestaurant(restaurant1);
		restaurantManagement.addRestaurant(restaurant3);
		restaurantManagement.addRestaurant(restaurant2);


		MessageDialog.openInformation(new Shell(), "Info", "Extensionpoint done!");

 

	}

 

	@Override
	public void addHandlerListener(IHandlerListener handlerListener) {
		// TODO Auto-generated method stub

	}

 

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

 

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		restaurantProvider();
		return null;
	}

 

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

 

	@Override
	public boolean isHandled() {
		// TODO Auto-generated method stub
		return false;
	}

 

	@Override
	public void removeHandlerListener(IHandlerListener handlerListener) {
		// TODO Auto-generated method stub

	}

 

}