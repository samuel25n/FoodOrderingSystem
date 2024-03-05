package eu.pinteam.rcpfos.widgets;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;

import eu.pinteam.rcpfos.dialogs.CalendarDialog;
import eu.pinteam.rcpfos.dialogs.ClockDialog;
import eu.pinteam.rcpfos.path.PathHandler;

public class AllWidget {

	Composite parent;
	PathHandler path = new PathHandler();
	ClockDialog dialog;
	CalendarDialog calendarDialog;
	CalculatorWidget calculatorWidget;
	
	public AllWidget(Composite parent, PathHandler path, ClockDialog dialog, CalendarDialog calendarDialog,
			CalculatorWidget calculatorWidget) {
		super();
		this.parent = parent;
		this.path = path;
		this.dialog = dialog;
		this.calendarDialog = calendarDialog;
		this.calculatorWidget = calculatorWidget;
	}

	public void createWidgets() {
			
		Composite customWidgetLabelComposite = new Composite(parent, SWT.NONE);
		GridLayout customWidgetLabelLayout = new GridLayout(1, false);
		customWidgetLabelLayout.marginRight = 10;
		customWidgetLabelLayout.marginTop = 10;
		customWidgetLabelComposite.setLayout(customWidgetLabelLayout);
		customWidgetLabelComposite.setLayoutData(new GridData(SWT.RIGHT, SWT.TOP, true, false));

		Label calendarLabell = new Label(customWidgetLabelComposite, SWT.NONE);
		calendarLabell.setCursor(parent.getDisplay().getSystemCursor(SWT.CURSOR_HAND));
		String calendarImagePath = path.getPathToCalendarIcon();
		Image imageCalendar = new Image(parent.getDisplay(), calendarImagePath);
		calendarLabell.setImage(imageCalendar);

		calendarLabell.addListener(SWT.MouseUp, new Listener() {
			@Override
			public void handleEvent(Event event) {
				if (event.button == 1) {
					if (dialog == null || dialog.getShell() == null || dialog.getShell().isDisposed()) {

						calendarDialog = new CalendarDialog(parent.getShell());
						calendarDialog.open();
					}
				}
			}
		});

		Label clockLabel = new Label(customWidgetLabelComposite, SWT.NONE);
		clockLabel.setCursor(parent.getDisplay().getSystemCursor(SWT.CURSOR_HAND));
		String clockImagePath = path.getPathToClockIcon();
		Image imageClock = new Image(parent.getDisplay(), clockImagePath);
		clockLabel.setImage(imageClock);

		clockLabel.addListener(SWT.MouseUp, new Listener() {
			@Override
			public void handleEvent(Event event) {
				if (event.button == 1) {
					if (dialog == null || dialog.getShell() == null || dialog.getShell().isDisposed()) {

						dialog = new ClockDialog(parent.getShell());
						dialog.open();
					}
				}
			}
		});
		
		
		Label calculatorLabel = new Label(customWidgetLabelComposite, SWT.NONE);
		calculatorLabel.setCursor(parent.getDisplay().getSystemCursor(SWT.CURSOR_HAND));
		String calculatorImagePath = path.getPathToCalculatorIcon();
		Image imageCalculator = new Image(parent.getDisplay(),calculatorImagePath);
		calculatorLabel.setImage(imageCalculator);
		
		calculatorWidget = new CalculatorWidget(parent, SWT.NONE);
		calculatorWidget.setLayoutData(new GridData(SWT.FILL,SWT.FILL,true,true));
		calculatorWidget.setVisible(false);
		
		calculatorLabel.addMouseListener(new MouseAdapter() {
		    @Override
		    public void mouseUp(MouseEvent e) {
		    	Display display = parent.getDisplay();
		    	Shell shell = new Shell(display);
		        shell.setLayout(new GridLayout());

		        CalculatorWidget calculator = new CalculatorWidget(shell, SWT.NONE);
		        calculator.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		        shell.setSize(400, 400);
		        
		        shell.open();

		        while (!shell.isDisposed()) {
		            if (!display.readAndDispatch()) {
		                display.sleep();
		            }
		        }

		    }
		});
	}
}