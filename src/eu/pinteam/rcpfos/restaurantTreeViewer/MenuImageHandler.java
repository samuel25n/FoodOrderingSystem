package eu.pinteam.rcpfos.restaurantTreeViewer;

import java.io.File;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

import eu.pinteam.rcpfos.activator.Activator;
import eu.pinteam.rcpfos.commandHandler.AddImageRating;
import eu.pinteam.rcpfos.imageRegistry.FosImageRegistry;
import eu.pinteam.rcpfos.management.RestaurantManagement;
import eu.pinteam.rcpfos.ratingDecorator.CustomLabelDecorator;
import fosemf.Menu;

public class MenuImageHandler {
	private Label imageLabel;
	private Image originalImage = null;
	private Image scaledImage;
	private MenuManager imageContextMenuManager;
	private String imgString = "";
	private org.eclipse.swt.widgets.Menu contextMenu;
	private Menu imageMenu;
	private Activator activator = Activator.getDefault();
	@SuppressWarnings({ "static-access", "unused" })
	private RestaurantManagement restaurantManagement = activator.getRestaurantManagement();;
	private FosImageRegistry fos;
	private CustomLabelDecorator customLabelDecorator;
	private Composite borderComposite;

	public MenuImageHandler(Composite parent) {
		super();
		borderComposite = new Composite(parent, SWT.BORDER);
        GridLayout gridLayout = new GridLayout();
        gridLayout.marginWidth = 1;  
        gridLayout.marginHeight = 1;  
        borderComposite.setLayout(gridLayout);
        GridData layoutData = new GridData();
        layoutData.widthHint = 400; 
        layoutData.heightHint = 365;  
        borderComposite.setLayoutData(layoutData);
        
        this.imageLabel = new Label(borderComposite, SWT.CENTER);
        
        
        
        this.imageLabel.setLayoutData(layoutData);
        
        
		imageLabel.addDisposeListener(e -> {
			if (originalImage != null && !originalImage.isDisposed()) {
				originalImage.dispose();
			}
		});
		imageContextMenuManager = new MenuManager();
		createImageContextMenu();
		fos = new FosImageRegistry();
		customLabelDecorator = new CustomLabelDecorator();
	}

	public void setImage() {
		if (this.imgString != null) {
			File file = new File(this.imgString);

			if (!file.exists()) {
				Display display = Display.getDefault();
				Shell shell = new Shell(display);
				MessageDialog.openError(shell, "Image Error", "The specified image does not exist.");
				return;
			}

			int rating = imageMenu.getImageRating();

			try {
				fos.addImageDescriptorInput(imgString, imgString);
				fos.addImageDataInput("menuImage",
						fos.getFosImageRegistry().get(imgString).getImageData().scaledTo(300, 300));
				scaledImage = fos.getFosImageRegistry().get("menuImage");
				scaledImage = customLabelDecorator.decorateImage(fos.getFosImageRegistry().get("menuImage"),
						fos.getFosImageRegistry().get("restaurantRating" + rating));
				imageLabel.setImage(scaledImage);
				fos.deleteInput(imgString);
				fos.deleteInput("menuImage");
			} catch (Exception e) {
				Display display = Display.getDefault();
				Shell shell = new Shell(display);
				MessageDialog.openError(shell, "Image Error", "There was an error loading the image.");
			} finally {

			}
		}
	}

	private void createImageContextMenu() {
		Action rateImageAction = new Action("Rate Image", Action.AS_DROP_DOWN_MENU) {
			@Override
			public void run() {
				System.out.println("Rate Image Action triggered.");
				AddImageRating addImageRating = new AddImageRating();
				Object element = imgString;
				if (element instanceof String) {

					addImageRating.setMenu(imageMenu);
					String imagePath = (String) element;

					addImageRating.processSelection(imagePath);
				}
			}
		};

		MenuManager rateImageSubMenuManager = new MenuManager("Add rating");

		Action action1 = new Action("Rate Image", Action.AS_PUSH_BUTTON) {
			@Override
			public void run() {
				System.out.println("Rate Image Action triggered.");
				AddImageRating addImageRating = new AddImageRating();
				Object element = imgString;
				if (element instanceof String) {
					addImageRating.setMenu(imageMenu);
					String imagePath = (String) element;
					int selectedRating = addImageRating.processSelection(imagePath);
					// refresh image
					fos.addImageDescriptorInput(imgString, imgString);
					fos.addImageDataInput("menuImage",
							fos.getFosImageRegistry().get(imgString).getImageData().scaledTo(300, 300));
					scaledImage = fos.getFosImageRegistry().get("menuImage");
					scaledImage = customLabelDecorator.decorateImage(fos.getFosImageRegistry().get("menuImage"),
							fos.getFosImageRegistry().get("restaurantRating" + selectedRating));
					imageLabel.setImage(scaledImage);
					imageLabel.redraw();
					fos.deleteInput(imgString);
					fos.deleteInput("menuImage");

				}
			}
		};

		Action action2 = new Action("Add rating for restaurant") {
			@Override
			public void run() {
				Shell shell = null;
				MessageDialog.openInformation(shell, "Information", "You haven't selected a restaurant!");
			}
		};

		Action action3 = new Action("Add rating for menu") {
			@Override
			public void run() {
				Shell shell = null;
				MessageDialog.openInformation(shell, "Information", "You haven't selected a menu!");
			}
		};

		rateImageSubMenuManager.add(action1);
		rateImageSubMenuManager.add(action2);
		rateImageSubMenuManager.add(action3);

		imageContextMenuManager.add(rateImageSubMenuManager);

		contextMenu = imageContextMenuManager.createContextMenu(imageLabel);
		imageLabel.setMenu(contextMenu);
		imageLabel.addListener(SWT.MouseUp, event -> {
			if (event.button == 3) {
				contextMenu.setVisible(true);
			}
		});
	}

	public void setContextMenu() {
		contextMenu = imageContextMenuManager.createContextMenu(imageLabel);
	}

	public void setImagePath(String imgPath) {

		this.imgString = imgPath;
	}

	public void setImageMenu(Menu imageMenu) {

		this.imageMenu = imageMenu;
	}

}
