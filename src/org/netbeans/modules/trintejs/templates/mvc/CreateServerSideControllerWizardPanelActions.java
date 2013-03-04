package org.netbeans.modules.trintejs.templates.mvc;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JTable;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;
import org.netbeans.modules.trintejs.tools.DataType;
import org.netbeans.modules.trintejs.ui.ValidationMessages;
import org.openide.WizardDescriptor;
import org.openide.WizardValidationException;
import org.openide.util.HelpCtx;

public class CreateServerSideControllerWizardPanelActions implements WizardDescriptor.ValidatingPanel<WizardDescriptor> {

    /**
     * The visual component that displays this panel. If you need to access the
     * component from this class, just use getComponent().
     */
    private CreateServerSideControllerVisualPanelActions component;
    private String REPL_TITLE = "Action Name";

    // Get the visual component for the panel. In this template, the component
    // is kept separate. This can be more efficient: if the wizard is created
    // but never displayed, or not all panels are displayed, it is better to
    // create only those which really need to be visible.
    @Override
    public CreateServerSideControllerVisualPanelActions getComponent() {
        if (component == null) {
            component = new CreateServerSideControllerVisualPanelActions();
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

    @Override
    public void readSettings(WizardDescriptor wiz) {
        // use wiz.getProperty to retrieve previous panel state
    }

    @Override
    public void storeSettings(WizardDescriptor wiz) {

        JTable table = component.getActionsTable();
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        int count = model.getRowCount();
        String[] actions = new String[count];

        for (int i = 0; i < count; i++) {
            actions[i] = (String) model.getValueAt(i, 0);
        }
        wiz.putProperty("controllerActions", actions);
    }

    @Override
    public void validate() throws WizardValidationException {

        JTable table = component.getActionsTable();
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        int count = model.getRowCount();
        Pattern pattern = Pattern.compile("[A-Za-z0-9_]+");

        if (count == 0) {
            throw new WizardValidationException(null, String.format(ValidationMessages.IS_REQUIRED.toString(), REPL_TITLE), null);
        }

        for (int i = 0; i < count; i++) {

            String fieldName = (String) model.getValueAt(i, 0);
            Matcher matcher = pattern.matcher(fieldName);

            if (fieldName.equals("")) {
                throw new WizardValidationException(null, String.format(ValidationMessages.IS_REQUIRED.toString(), REPL_TITLE + " at row " + (1 + i)), null);
            } else if (matcher.matches()) {
            } else {
                throw new WizardValidationException(null, String.format(ValidationMessages.IS_NOT_ONLY_CHARACTERS.toString(), REPL_TITLE + " at row " + (1 + i)), null);
            }
        }
    }
}
