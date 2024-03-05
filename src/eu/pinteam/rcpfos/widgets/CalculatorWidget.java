package eu.pinteam.rcpfos.widgets;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

public class CalculatorWidget extends Composite {

    private Label display;  
    private StringBuilder displayContent = new StringBuilder();   
    private double number1;
    private String operator = "";
    private SevenSegmentDisplay sevenSegmentDisplay = new SevenSegmentDisplay();
	private double currentDisplayValue;
	private boolean equalPressed = false;


    public CalculatorWidget(Composite parent, int style) {
        super(parent, style);

        this.setLayout(new GridLayout(4, true));
        int equalCounter =0;

        display = new Label(this, SWT.BORDER | SWT.RIGHT);
        Font customFont = new Font(parent.getDisplay(), "Consolas", 10, SWT.NONE);
        display.setFont(customFont);
        display.setText(sevenSegmentDisplay.convertToSevenSegmentString(0));
        GridData gridData = new GridData(SWT.FILL, SWT.CENTER, true, false, 4, 1);
        display.setLayoutData(gridData);

        String[] buttons = {"1", "2", "3", "+", "4", "5", "6", "-", "7", "8", "9", "*", "C", "0", "=", "/","CLOSE"};
        
        this.addDisposeListener(new DisposeListener() {
            @Override
            public void widgetDisposed(DisposeEvent e) {
                if (customFont != null && !customFont.isDisposed()) {
                    customFont.dispose();
                }
            }
        });

        for (String text : buttons) {
            Button button = new Button(this, SWT.PUSH);
            button.setText(text);
            button.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

            if ("0123456789.".contains(text)) {
                button.addListener(SWT.Selection, e -> appendToDisplay(text));
            }else if (text.equals("CLOSE")) {
            	 button.addListener(SWT.Selection, e -> parent.getShell().dispose());
				
			}
            else if (text.equals("C")) {
                button.addListener(SWT.Selection, e -> clearDisplay());
            } else if (text.equals("=")) {
                button.addListener(SWT.Selection, e -> compute());
            } else {
            	button.addListener(SWT.Selection, e -> {
            	    number1 = currentDisplayValue;
            	    operator = text;
            	    clearDisplay();
            	});
            }
        }
    }
    
    

    private void appendToDisplay(String text) {
        displayContent.append(text);
        currentDisplayValue = Double.parseDouble(displayContent.toString());
        display.setText(sevenSegmentDisplay.convertToSevenSegmentString(currentDisplayValue));
    }

    private void clearDisplay() {
        displayContent.setLength(0); 
        display.setText("");
    }

    private String getDisplayText() {
        return displayContent.toString();
    }

    private void compute() {
        if (operator == null || operator.isEmpty()) {
            return; 
        }

        double number2 = Double.parseDouble(getDisplayText());
        double result = 0;

        switch (operator) {
            case "+":
                result = number1 + number2;
                break;
            case "-":
                result = number1 - number2;
                break;
            case "*":
                result = number1 * number2;
                break;
            case "/":
                if (number2 != 0) {
                    result = number1 / number2;
                } else {
                    setDisplayText(sevenSegmentDisplay.convertErrorToSevenSegmentString());
                    MessageDialog.openInformation(getShell(), "Error", "You can't do that");
                    clearDisplay();
                    return;
                }
                break;
        }
        operator = "";

        setDisplayText(sevenSegmentDisplay.convertToSevenSegmentString(result));
        
        currentDisplayValue = result;
        setDisplayText(sevenSegmentDisplay.convertToSevenSegmentString(result));
        
    }


    private void setDisplayText(String text) {
        displayContent.setLength(0);
        displayContent.append(text);
        display.setText(displayContent.toString());
    }
}
