package eu.pinteam.rcpfos.wizard.addRestaurant;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class RestaurantDetailsPage extends WizardPage {

    private Text nameText;
    private Text phoneText;
    private RestaurantWizard wizard;

    public RestaurantDetailsPage() {
        super("Restaurant Details");
        setTitle("Enter Restaurant Details");
    }

    @Override
    public void createControl(Composite parent) {
    	setPageComplete(false);
        wizard = (RestaurantWizard) getWizard();
        Composite composite = new Composite(parent, SWT.NONE);
        GridLayout layout = new GridLayout(2, false);
        composite.setLayout(layout);

        Label nameLabel = new Label(composite, SWT.NONE);
        nameLabel.setText("Name:");
        nameText = new Text(composite, SWT.BORDER);
        nameText.addModifyListener(e -> {
            String name = nameText.getText();
            if (!name.matches("[A-Za-z0-9]+") || name.length() < 1) {
                setErrorMessage("Name must contain at least 1 character and only letters and numbers.");
                setPageComplete(false);
            } else {
                setErrorMessage(null);
                if(phoneText.getText().length()>=8) {
                	setPageComplete(true);
                }
                if (wizard.getRestaurant() != null) {
                    wizard.getRestaurant().setName(name);
                    
                }
            }
        });

        Label phoneLabel = new Label(composite, SWT.NONE);
        phoneLabel.setText("Phone:");
        phoneText = new Text(composite, SWT.BORDER);
        phoneText.addModifyListener(e -> {
            String phone = phoneText.getText();
            if (!phone.matches("\\d+") || phone.length() < 8) {
                setErrorMessage("Phone number must contain at least 8 digits and only numbers.");
                setPageComplete(false);
            } else {
                setErrorMessage(null);
                if(nameText.getText().length() > 1) {
                	setPageComplete(true);
                }
                
                if (wizard.getRestaurant() != null) {
                    wizard.getRestaurant().setPhone(phone);
                }
            }
        });

        setControl(composite);
    }
}
