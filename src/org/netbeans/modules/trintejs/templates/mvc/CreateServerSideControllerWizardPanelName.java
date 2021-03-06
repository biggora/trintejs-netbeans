package org.netbeans.modules.trintejs.templates.mvc;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.event.ChangeListener;
import org.netbeans.modules.trintejs.ui.ValidationMessages;
import org.openide.WizardDescriptor;
import org.openide.WizardValidationException;
import org.openide.util.HelpCtx;

public class CreateServerSideControllerWizardPanelName implements WizardDescriptor.ValidatingPanel<WizardDescriptor> {

    /**
     * The visual component that displays this panel. If you need to access the
     * component from this class, just use getComponent().
     */
    private CreateServerSideControllerVisualPanelName component;
    private String REPL_TITLE = "Controller Name";

    // Get the visual component for the panel. In this template, the component
    // is kept separate. This can be more efficient: if the wizard is created
    // but never displayed, or not all panels are displayed, it is better to
    // create only those which really need to be visible.
    @Override
    public CreateServerSideControllerVisualPanelName getComponent() {
        if (component == null) {
            component = new CreateServerSideControllerVisualPanelName();
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
        return true;
        // If it depends on some condition (form filled out...) and
        // this condition changes (last form field filled in...) then
        // use ChangeSupport to implement add/removeChangeListener below.
        // WizardDescriptor.ERROR/WARNING/INFORMATION_MESSAGE will also be useful.
    }

    @Override
    public void addChangeListener(ChangeListener l) {
    }

    @Override
    public void removeChangeListener(ChangeListener l) {
    }

    private String getModelNameFromVisualPanel() {
        return getComponent().getControllerName();
    }

    private String getNameSpaceFromVisualPanel() {
        return getComponent().getNameSpace();
    }

    @Override
    public void validate() throws WizardValidationException {

        String modelNameValue = getModelNameFromVisualPanel();
        Pattern pattern = Pattern.compile("[A-Za-z]+");
        Matcher matcher = pattern.matcher(modelNameValue);

        if (modelNameValue.equals("")) {
            throw new WizardValidationException(null, String.format(ValidationMessages.IS_REQUIRED.toString(), REPL_TITLE), null);
        } else if (modelNameValue.length() < 3) {
            throw new WizardValidationException(null, String.format(ValidationMessages.IS_TOO_SHORT.toString(), REPL_TITLE, 3), null);
        } else if (matcher.matches()) {

        } else {
            throw new WizardValidationException(null, String.format(ValidationMessages.IS_NOT_ONLY_CHARACTERS.toString(), REPL_TITLE), null);
        }
    }

    @Override
    public void readSettings(WizardDescriptor wiz) {
        // throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void storeSettings(WizardDescriptor wiz) {
        wiz.putProperty(CreateServerSideControllerVisualPanelName.CONTROLLER_NAME, getModelNameFromVisualPanel());
        wiz.putProperty(CreateServerSideControllerVisualPanelName.NAMESPACE, getNameSpaceFromVisualPanel());
    }
}
