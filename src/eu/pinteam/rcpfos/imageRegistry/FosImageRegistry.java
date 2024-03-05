package eu.pinteam.rcpfos.imageRegistry;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageDataProvider;

import eu.pinteam.rcpfos.path.PathHandler;

public class FosImageRegistry {
	private ImageRegistry registry;
	
	public FosImageRegistry() {
		registry = new ImageRegistry();

		//the icons of widgets
		registry.put("calendar", ImageDescriptor.createFromFile(null, PathHandler.getPathToCalendarIcon()));
		registry.put("clock", ImageDescriptor.createFromFile(null, PathHandler.getPathToClockIcon()));
		registry.put("calculator", ImageDescriptor.createFromFile(null, PathHandler.getPathToCalculatorIcon()));
		
		//restaurant's icon
		registry.put("restaurantIcon",
				ImageDescriptor.createFromFile(null, PathHandler.getPathToRatingRestaurantIcon()));
		
		//menu's icon
		registry.put("menuIcon", ImageDescriptor.createFromFile(null, PathHandler.getPathToRatingMenuIcon()));
		
		//rating
		registry.put("restaurantRating0",
				ImageDescriptor.createFromFile(null, PathHandler.getRatingValuePath() + "0.png"));
		registry.put("restaurantRating1",
				ImageDescriptor.createFromFile(null, PathHandler.getRatingValuePath() + "1.png"));
		registry.put("restaurantRating2",
				ImageDescriptor.createFromFile(null, PathHandler.getRatingValuePath() + "2.png"));
		registry.put("restaurantRating3",
				ImageDescriptor.createFromFile(null, PathHandler.getRatingValuePath() + "3.png"));
		registry.put("restaurantRating4",
				ImageDescriptor.createFromFile(null, PathHandler.getRatingValuePath() + "4.png"));
		registry.put("restaurantRating5",
				ImageDescriptor.createFromFile(null, PathHandler.getRatingValuePath() + "5.png"));

	}

	
	public ImageRegistry getFosImageRegistry() {
		return registry;
	}

	/**
	 * This method adds an image descriptor to registry using the image path
	 * @param key - Registry image key
	 * @param path - image's path
	 */
	public void addImageDescriptorInput(String key, String path) {
		registry.put(key, ImageDescriptor.createFromFile(null, path));

	}

	/**
	 * This method adds an image descriptor to registry using the image
	 * @param key - Registry image key
	 * @param path - image's path
	 */
	public void addImageInput(String key, Image image) {
		registry.put(key, ImageDescriptor.createFromImage(image));

	}

	/**
	 * This method adds an image descriptor to registry using image data
	 * @param key - Registry image key
	 * @param path - image's path
	 */
	public void addImageDataInput(String key, ImageData imageData) {
		ImageDataProvider dataProvider = new ImageDataProvider() {
			@Override
			public ImageData getImageData(int zoom) {
				return imageData;
			}
		};
		registry.put(key, ImageDescriptor.createFromImageDataProvider(dataProvider));
	}

	/**
	 * This method delete an item in registry by key
	 * @param key -> Registry image key
	 */
	public void deleteInput(String key) {
		registry.remove(key);
	}

}
