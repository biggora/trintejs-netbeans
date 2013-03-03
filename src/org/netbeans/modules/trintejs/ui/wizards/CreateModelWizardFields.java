package org.netbeans.modules.trintejs.ui.wizards;

import java.awt.Color;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JTable;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;
import org.netbeans.modules.trintejs.ui.ValidationMessages;
import org.openide.WizardDescriptor;
import org.openide.WizardValidationException;
import org.openide.util.HelpCtx;

public class CreateModelWizardFields implements WizardDescriptor.ValidatingPanel {

    /**
     * The visual component that displays this panel. If you need to access the
     * component from this class, just use getComponent().
     */
    private CreateModelFieldsVisual component;
    private String rTitle = "Field name";

    // Get the visual component for the panel. In this template, the component
    // is kept separate. This can be more efficient: if the wizard is created
    // but never displayed, or not all panels are displayed, it is better to
    // create only those which really need to be visible.
    @Override
    public CreateModelFieldsVisual getComponent() {
        if (component == null) {
            component = new CreateModelFieldsVisual();
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
    public void validate() throws WizardValidationException {

        JTable table = component.getFieldsTable();
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        int count = model.getRowCount();
        Pattern pattern = Pattern.compile("[A-Za-z]+");

        for (int i = 0; i < count; i++) {

            String fieldName = (String) model.getValueAt(i, 0);
            Matcher matcher = pattern.matcher(fieldName);

            if (fieldName.equals("")) {
                throw new WizardValidationException(null, String.format(ValidationMessages.IS_REQUIRED.toString(), rTitle + " at row " + (1 + i)), null);
            } else if (matcher.matches()) {
            } else {
                throw new WizardValidationException(null, String.format(ValidationMessages.IS_NOT_ONLY_CHARACTERS.toString(), rTitle + " at row " + (1 + i)), null);
            }
        }
    }

    public void setCellColor(JTable table, int row, int col, Color color) {
        table.getCellEditor(row, col).getTableCellEditorComponent(table, table.getValueAt(row, col), false, row, col).setBackground(color);
    }

    @Override
    public void readSettings(Object settings) {
        // throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void storeSettings(Object settings) {

        JTable table = component.getFieldsTable();
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        int count = model.getRowCount();
        String[] fields = new String[count];

        for (int i = 0; i < count; i++) {
            String fieldName = (String) model.getValueAt(i, 0);
            String fieldType = (String) model.getValueAt(i, 1);
            String defValue = (String) model.getValueAt(i, 0);
            fields[i] = fieldName + ":" + fieldType;
            if (!defValue.equals("")) {
                fields[i] += ":" + defValue;
            }
        }

        ((WizardDescriptor) settings).putProperty("modelFields",fields);
    }
}