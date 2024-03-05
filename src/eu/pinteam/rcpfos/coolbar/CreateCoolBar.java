package eu.pinteam.rcpfos.coolbar;

import java.net.MalformedURLException;
import java.net.URL;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.CoolBar;
import org.eclipse.swt.widgets.CoolItem;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import eu.pinteam.rcpfos.dialogs.CalendarDialog;
import eu.pinteam.rcpfos.dialogs.ClockDialog;
import eu.pinteam.rcpfos.imageRegistry.FosImageRegistry;
import eu.pinteam.rcpfos.path.PathHandler;
import eu.pinteam.rcpfos.widgets.AllWidget;
import eu.pinteam.rcpfos.widgets.CalculatorWidget;

public class CreateCoolBar {

	private Composite parent;
	private Shell shell;
	PathHandler path = new PathHandler();
	ClockDialog dialog;
	CalendarDialog calendarDialog;
	CalculatorWidget calculatorWidget;
	

	public CreateCoolBar(Composite parent, Shell shell, PathHandler path, ClockDialog dialog,
			CalendarDialog calendarDialog, CalculatorWidget calculatorWidget) {
		super();
		this.parent = parent;
		this.shell = shell;
		this.path = path;
		this.dialog = dialog;
		this.calendarDialog = calendarDialog;
		this.calculatorWidget = calculatorWidget;
	}

	public CreateCoolBar(Composite parent, Shell shell) {
		super();
		this.parent = parent;
		this.shell = shell;
	}
	
	public void createCoolBar() {
		
		CoolBar coolBar = new CoolBar(parent, SWT.NONE);

		GridData coolBarGridData = new GridData(SWT.FILL, SWT.BEGINNING, true, false);
		coolBarGridData.horizontalSpan = 3;
		coolBar.setLayoutData(coolBarGridData);

		CoolItem coolItem = new CoolItem(coolBar, SWT.NONE);
		ToolBarManager toolBarManager = new ToolBarManager(SWT.FLAT);

        ImageDescriptor calendarImageDescriptor = ImageDescriptor.createFromFile(null, path.getPathToCalendarIcon());
        ImageDescriptor clockImageDescriptor = ImageDescriptor.createFromFile(null, path.getPathToClockIcon());
        ImageDescriptor calculatorImageDescriptor = ImageDescriptor.createFromFile(null, path.getPathToCalculatorIcon());
        
		Action sayHelloAction = new Action("Hello", Action.AS_PUSH_BUTTON) {
		    @Override
		    public void run() {
		        MessageDialog.openInformation(shell, "Greetings", "Hello to you too");
		    }
		};

		Action exitAction = new Action("EXIT", Action.AS_PUSH_BUTTON) {
		    @Override
		    public void run() {
		       
					shell.dispose();
			
		    }
		};
		
		Action calendarAction = new Action("CALENDAR", Action.AS_PUSH_BUTTON) {
		    @Override
		    public void run() {
		       
		    	calendarDialog = new CalendarDialog(parent.getShell());
				calendarDialog.open();
					
			
		    }
		};		

		FosImageRegistry fos=new FosImageRegistry();
		calendarAction.setImageDescriptor(fos.getFosImageRegistry().getDescriptor("calendar"));
		
		Action clockAction = new Action("CLOCK", Action.AS_PUSH_BUTTON) {
		    @Override
		    public void run() {
		       
		    	dialog = new ClockDialog(parent.getShell());
				dialog.open();
			
		    }
		};
		
		clockAction.setImageDescriptor(fos.getFosImageRegistry().getDescriptor("clock"));
		
		Action calculatorAction = new Action("CALCULATOR", Action.AS_PUSH_BUTTON) {
		    @Override
		    public void run() {
		       
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
		};

		calculatorAction.setImageDescriptor(fos.getFosImageRegistry().getDescriptor("calculator"));
		
		toolBarManager.add(calendarAction);
		toolBarManager.add(clockAction);
		toolBarManager.add(calculatorAction);
		toolBarManager.createControl(coolItem.getParent());
		coolItem.setControl(toolBarManager.getControl());

		Point toolBarSize = toolBarManager.getControl().computeSize(SWT.DEFAULT, SWT.DEFAULT);
		coolItem.setSize(toolBarSize.x, toolBarSize.y);
	}

}
