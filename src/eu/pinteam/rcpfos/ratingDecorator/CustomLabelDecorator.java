package eu.pinteam.rcpfos.ratingDecorator;

import javax.swing.text.View;

import org.eclipse.jface.viewers.ILabelDecorator;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.PaletteData;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PlatformUI;

import eu.pinteam.rcpfos.imageRegistry.FosImageRegistry;
import eu.pinteam.rcpfos.views.RestaurantView;

public class CustomLabelDecorator implements ILabelDecorator {

	@Override
	public void addListener(ILabelProviderListener listener) {

	}

	@Override
	public void dispose() {

	}

	@Override
	public boolean isLabelProperty(Object element, String property) {

		return false;
	}

	@Override
	public void removeListener(ILabelProviderListener listener) {

	}

	@Override
	public Image decorateImage(Image baseImage, Object element) {
		FosImageRegistry fos = new FosImageRegistry();

		Image decorationImage = (Image) element;

		Rectangle baseBounds = baseImage.getBounds();
		Rectangle decorationBounds = decorationImage.getBounds();

		int combinedWidth = baseBounds.width + Math.abs(decorationBounds.width) - 10;
		int combinedHeight = Math.max(baseBounds.height, decorationBounds.height);

		int depth = 24;

		PaletteData palette = new PaletteData(0xFF0000, 0x00FF00, 0x0000FF);

		ImageData combinedImageData = new ImageData(combinedWidth, combinedHeight, depth, palette);
		for (int y = 0; y < combinedHeight; y++) {
			for (int x = 0; x < combinedWidth; x++) {
				combinedImageData.setPixel(x, y, 0xFFFFFF);
			}
		}

		fos.addImageDataInput("background", combinedImageData);
		Image decoratedImage = fos.getFosImageRegistry().get("background");

		GC gc = new GC(decoratedImage);

		gc.drawImage(baseImage, 0, 0);
		int x = baseBounds.width - 12;
		int y = 4;
		gc.drawImage(decorationImage, x, y);

		if (gc != null && !gc.isDisposed()) {
			gc.dispose();
		}

		return decoratedImage;
	}

	@Override
	public String decorateText(String text, Object element) {
		return null;
	}

}
