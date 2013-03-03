package org.netbeans.modules.trintejs.ui.wizards;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.event.ChangeListener;
import org.netbeans.modules.trintejs.tools.*;
import org.netbeans.modules.trintejs.ui.ValidationMessages;
import org.openide.WizardDescriptor;
import org.openide.WizardValidationException;
import org.openide.util.HelpCtx;

public class CreateModelWizardName implements WizardDescriptor.ValidatingPanel<WizardDescriptor> {

    /**
     * The visual component that displays this panel. If you need to access the
     * component from this class, just use getComponent().
     */
    private CreateModelNameVisual component;
    private String rTitle = "Model Name";
    private String modelNameValue;
    private boolean isValid = true;
    // Get the visual component for the panel. In this template, the component
    // is kept separate. This can be more efficient: if the wizard is created
    // but never displayed, or not all panels are displayed, it is better to
    // create only those which really need to be visible.

    @Override
    public CreateModelNameVisual getComponent() {
        if (component == null) {
            component = new CreateModelNameVisual();
        }
        return component;
    }

    @Override
    public HelpCtx getHelp() {
        // Show no Help button for this panel:
        return HelpCtx.DEFAULT_HELP;
        // If you have context help:
        // return new HelpCtx("help.key.here");
    }

    @Override
    public boolean isValid() {
        // If it is always OK to press Next or Finish, then:
        return isValid;
    }

    @Override
    public void addChangeListener(ChangeListener l) {
    }

    @Override
    public void removeChangeListener(ChangeListener l) {
    }

    @Override
    public void validate() throws WizardValidationException {

        modelNameValue = component.getModelNameField().getText();
        Pattern pattern = Pattern.compile("[A-Za-z]+");
        Matcher matcher = pattern.matcher(modelNameValue);

        if (modelNameValue.equals("")) {
            throw new WizardValidationException(null, String.format(ValidationMessages.IS_REQUIRED.toString(), rTitle), null);
        } else if (modelNameValue.length() < 3) {
            throw new WizardValidationException(null, String.format(ValidationMessages.IS_TOO_SHORT.toString(), rTitle, 3), null);
        } else if (matcher.matches()) {

            Inflector inflector = new Inflector();
            String SingularModelNameValue = inflector.singularize(modelNameValue);

            if (!modelNameValue.equals(SingularModelNameValue)) {
                throw new WizardValidationException(null, String.format(ValidationMessages.IS_NOT_SINGULAR.toString(), rTitle), null);
            } else {
                modelNameValue = Character.toUpperCase(modelNameValue.charAt(0)) + modelNameValue.substring(1);
            }
        } else {
            throw new WizardValidationException(null, String.format(ValidationMessages.IS_NOT_ONLY_CHARACTERS.toString(), rTitle), null);
        }
    }

    @Override
    public void readSettings(WizardDescriptor wiz) {
        // throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void storeSettings(WizardDescriptor wiz) {
        wiz.putProperty("modelName", modelNameValue);
    }
}