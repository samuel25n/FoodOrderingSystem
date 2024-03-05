package eu.pinteam.rcpfos.restaurantTreeViewer;

import java.util.List;

import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IWorkbenchPartSite;

import fosemf.Menu;
import fosemf.Restaurant;


public class RestaurantTreeViewerHandler {
	private TreeViewer treeViewer;
	private MenuImageHandler menuImageHandler;
	

	public RestaurantTreeViewerHandler(Composite parent) {
		treeViewer = new TreeViewer(parent, SWT.SINGLE | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
        GridData treeViewerGridData = new GridData();
        treeViewerGridData.widthHint = 200; 
        treeViewerGridData.heightHint = 350; 
        treeViewer.getControl().setLayoutData(treeViewerGridData);
		menuImageHandler = new MenuImageHandler(parent);
	}

	public TreeViewer getTreeViewer() {
		return this.treeViewer;
	}

	public void setTreeViewerInput(List<Restaurant> input) {
		treeViewer.setInput(input);
	}

	public void addSelectionChangedListener() {
		treeViewer.addSelectionChangedListener(event -> {
			Object selected = ((IStructuredSelection) event.getSelection()).getFirstElement();
			if (selected instanceof Menu) {
				menuImageHandler.setImageMenu((Menu) selected);
				menuImageHandler.setImagePath(((Menu) selected).getPicture());
				menuImageHandler.setImage();
				menuImageHandler.setContextMenu();
			}
		});
	}
	public void createTreeViewerContextMenu(IWorkbenchPartSite iWorkbenchPartSite) {

		MenuManager menuManager = new MenuManager();
		org.eclipse.swt.widgets.Menu menu = menuManager.createContextMenu(treeViewer.getTree());

		treeViewer.getTree().setMenu(menu);
		treeViewer.getTree().setMenu(menu);

		iWorkbenchPartSite.registerContextMenu(menuManager, treeViewer);
		iWorkbenchPartSite.setSelectionProvider(treeViewer);
	}

}
