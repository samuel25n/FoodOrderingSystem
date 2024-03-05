package eu.pinteam.rcpfos.foss;

import org.eclipse.ui.IFolderLayout;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

public class Perspective implements IPerspectiveFactory {

	@Override
	public void createInitialLayout(IPageLayout layout) {
		layout.setEditorAreaVisible(false);
		String editorArea = layout.getEditorArea();
		IFolderLayout tabFolder = layout.createFolder("myFolder", IPageLayout.BOTTOM, 0.75F, editorArea);
		layout.setFixed(true);
		tabFolder.addView("foss.admin");
		tabFolder.addView("foss.client");
//		tabFolder.addView("foss.imageView");
		
	}

}
