package eu.pinteam.rcpfos.activator;

import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import eu.pinteam.rcpfos.management.RestaurantManagement;

public class Activator extends AbstractUIPlugin {

	private static Activator plugin;
	private static RestaurantManagement restaurantManagement;

	public Activator() {

	}

	@Override
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
		restaurantManagement = new RestaurantManagement();
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		plugin = null;

		super.stop(context);
	}

	public static Activator getDefault() {
		return plugin;
	}

	public static RestaurantManagement getRestaurantManagement() {
		return restaurantManagement;
	}
}
