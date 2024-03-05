package eu.pinteam.rcpfos.views;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.part.ViewPart;

import fosemf.Menu;


public class ImageView extends ViewPart {

    private Label imageLabel;

    public ImageView() {
    }

    @Override
    public void createPartControl(Composite parent) {
        imageLabel = new Label(parent, SWT.CENTER);
        
        getSite().getPage().addSelectionListener(new ISelectionListener() {
			
			@Override
			public void selectionChanged(IWorkbenchPart part, ISelection selection) {
				if(part instanceof MenuView && selection instanceof IStructuredSelection) {
					Object element = ((IStructuredSelection) selection).getFirstElement();
					if(element instanceof Menu) {
						Menu menu = (Menu)element;
						String imagePath = "C:\\Users\\SamuelNistor\\eclipse-workspace\\foss\\"+menu.getPicture();
						setImage(imagePath);
					}
				}
				
			}
		});
        
    }

    @Override
    public void setFocus() {
        imageLabel.setFocus();
    }

    private void setImage(String imagePath) {
        Image image = new Image(imageLabel.getDisplay(), imagePath);
        imageLabel.setImage(image);
    }

}
