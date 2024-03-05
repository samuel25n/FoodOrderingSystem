package eu.pinteam.rcpfos.checkBox;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

public class Checkbox extends Composite {
	private Button yesCheckbox;
	private Button noCheckbox;

	public Checkbox(Composite parent, int style) {
		super(parent, style);
		createContents();
	}

	private void createContents() {
		GridLayout layout = new GridLayout(1, false);
		setLayout(layout);

		Label checkboxLabel = new Label(this, SWT.NONE);
		checkboxLabel.setText("Doriti tacamuri?");

		yesCheckbox = new Button(this, SWT.CHECK);
		noCheckbox = new Button(this, SWT.CHECK);
		yesCheckbox.setText("Da");
		yesCheckbox.setSelection(true);
		yesCheckbox.setLayoutData(new GridData(GridData.VERTICAL_ALIGN_BEGINNING));

		noCheckbox.setText("Nu");
		noCheckbox.setSelection(false);
		noCheckbox.setLayoutData(new GridData(GridData.VERTICAL_ALIGN_BEGINNING));

		addSelectionListeners();
	}

	private void addSelectionListeners() {
		yesCheckbox.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (yesCheckbox.getSelection()) {
					noCheckbox.setSelection(false);
				}
			}
		});

		noCheckbox.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (noCheckbox.getSelection()) {
					yesCheckbox.setSelection(false);
				}
			}
		});
	}

	public boolean isYesSelected() {
		return yesCheckbox.getSelection();
	}

	public boolean isNoSelected() {
		return noCheckbox.getSelection();
	}
}
