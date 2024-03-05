package eu.pinteam.rcpfos.views;

import java.io.File;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.part.ViewPart;

import eu.pinteam.rcpfos.activator.Activator;
import eu.pinteam.rcpfos.management.RestaurantManagement;
import fosemf.Menu;
import fosemf.Restaurant;

public class MenuView extends ViewPart {

    @Inject IWorkbench workbench;
    private TreeViewer treeViewer;
    private Label imageLabel;
    private Image scaledImage;
    //private File restaurantsFile = new File("C:\\Users\\SamuelNistor\\eclipse-workspace\\foss\\files\\restaurants.csv");
	private Activator activator = Activator.getDefault();
	private RestaurantManagement restaurantManagement = activator.getRestaurantManagement();

    @Override
    public void createPartControl(Composite parent) {
        GridLayout layout = new GridLayout(2, false);
        parent.setLayout(layout);
        treeViewer = new TreeViewer(parent, SWT.SINGLE | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER | SWT.FULL_SELECTION);
        treeViewer.getTree().setHeaderVisible(true);
        treeViewer.getTree().setLinesVisible(true);
        treeViewer.getControl().setLayoutData(new GridData(GridData.FILL_BOTH));
        TreeColumn column1 = new TreeColumn(treeViewer.getTree(), SWT.LEFT);
        column1.setText("Restaurant & menu");
        column1.setWidth(400);
        
        treeViewer.setContentProvider(new MenuContentProvider());
        treeViewer.setLabelProvider(new MenuTableLabelProvider());
        try {
            treeViewer.setInput(getRestaurants());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        treeViewer.addSelectionChangedListener(event -> {
            Object selected = ((IStructuredSelection) event.getSelection()).getFirstElement();
            if (selected instanceof Menu) {
                String imagePath = "C:\\Users\\SamuelNistor\\eclipse-workspace\\foss\\" + ((Menu) selected).getPicture();
                setImage(imagePath);
            }
        });
        imageLabel = new Label(parent, SWT.CENTER);
        imageLabel.setLayoutData(new GridData(GridData.FILL_BOTH));
        
    }

    @Override
    public void setFocus() {
        treeViewer.getControl().setFocus();
    }

    public List<Restaurant> getRestaurants() throws RemoteException {
       return null;
    }

    @Override
    public void dispose() {
        if (scaledImage != null && !scaledImage.isDisposed()) {
            scaledImage.dispose();
        }
        super.dispose();
    }

    private void setImage(String imagePath) {
        File file = new File(imagePath);
        if (!file.exists()) {
            MessageDialog.openError(getSite().getShell(),
                    "Image Error",
                    "The specified image does not exist.");
            return;
        }
        if (scaledImage != null && !scaledImage.isDisposed()) {
            scaledImage.dispose();
        }
        Image originalImage = null;
        try {
            originalImage = new Image(imageLabel.getDisplay(), imagePath);
            scaledImage = new Image(imageLabel.getDisplay(), originalImage.getImageData().scaledTo(300, 300));
            imageLabel.setImage(scaledImage);
        } catch (Exception e) {
            MessageDialog.openError(getSite().getShell(),
                    "Image Error",
                    "There was an error loading the image.");
        } finally {
            if (originalImage != null) {
                originalImage.dispose();
            }
        }
    }

    class MenuContentProvider implements ITreeContentProvider {
        public Object[] getChildren(Object parentElement) {
            if (parentElement instanceof Restaurant) {
                return restaurantManagement.getMenusForRestaurant((Restaurant) parentElement).toArray();
            }
            return new Object[0];
        }

        public Object getParent(Object element) {
            return null;
        }

        public boolean hasChildren(Object element) {
            return element instanceof Restaurant;
        }

        public Object[] getElements(Object inputElement) {
            return ((List<?>) inputElement).toArray();
        }

        public void dispose() {
        }

        public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
        }
    }

    class MenuTableLabelProvider implements ITableLabelProvider {
        public Image getColumnImage(Object element, int columnIndex) {
            return null;
        }

        public String getColumnText(Object element, int columnIndex) {
            if (element instanceof Restaurant) {
                return ((Restaurant) element).getName();
            } else if (element instanceof Menu) {
                Menu menu = (Menu) element;
                return menu.getName();
            }
            return null;
        }

        public void addListener(ILabelProviderListener listener) {
        }

        public void dispose() {
        }

        public boolean isLabelProperty(Object element, String property) {
            return false;
        }

        public void removeListener(ILabelProviderListener listener) {
        }
    }
}

