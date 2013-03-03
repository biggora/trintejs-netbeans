package org.netbeans.modules.trintejs.actions;

import java.awt.Component;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.net.URL;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JMenuItem;
import org.netbeans.api.project.Project;
import org.netbeans.modules.trintejs.ui.wizards.CreateModelWizardFields;
import org.netbeans.modules.trintejs.ui.wizards.CreateModelWizardName;
import org.openide.DialogDisplayer;
import org.openide.WizardDescriptor;
import org.openide.util.ImageUtilities;
import org.openide.util.actions.Presenter;

public final class CreateModelAction extends AbstractAction implements Presenter.Popup {

    private static final String ICON = "org/netbeans/modules/trintejs/images/document.png";
    private String TEMPLATE = "org/netbeans/modules/trintejs/templates/model.template.ejs";
    private final Project context;
    public Frame parent;
    public Boolean MODAL;

    public CreateModelAction(Project context) {
        this.context = context;
        putValue(SMALL_ICON, ImageUtilities.loadImageIcon(ICON, false));
        putValue(NAME, "Create Model");
    }

    @Override
    public void actionPerformed(ActionEvent ev) {
        List<WizardDescriptor.Panel<WizardDescriptor>> panels = new ArrayList<WizardDescriptor.Panel<WizardDescriptor>>();
        panels.add(new CreateModelWizardName());
        panels.add(new CreateModelWizardFields());
        String[] steps = new String[panels.size()];
        for (int i = 0; i < panels.size(); i++) {
            Component c = panels.get(i).getComponent();
            // Default step name to component name of panel.
            steps[i] = c.getName();
            if (c instanceof JComponent) { // assume Swing components
                JComponent jc = (JComponent) c;
                jc.putClientProperty(WizardDescriptor.PROP_CONTENT_SELECTED_INDEX, i);
                jc.putClientProperty(WizardDescriptor.PROP_CONTENT_DATA, steps);
                jc.putClientProperty(WizardDescriptor.PROP_AUTO_WIZARD_STYLE, true);
                jc.putClientProperty(WizardDescriptor.PROP_CONTENT_DISPLAYED, true);
                jc.putClientProperty(WizardDescriptor.PROP_CONTENT_NUMBERED, true);
            }
        }
        WizardDescriptor wiz = new WizardDescriptor(new WizardDescriptor.ArrayIterator<WizardDescriptor>(panels));
        // {0} will be replaced by WizardDesriptor.Panel.getComponent().getName()
        wiz.setTitleFormat(new MessageFormat("{0}"));
        wiz.setTitle("Create Model");
        // wiz.putProperty(WizardDescriptor.PROP_AUTO_WIZARD_STYLE, Boolean.FALSE);
        wiz.putProperty(WizardDescriptor.PROP_IMAGE, ImageUtilities.loadImage("org/netbeans/modules/trintejs/images/trintejs-banner.png", true));

        if (DialogDisplayer.getDefault().notify(wiz) == WizardDescriptor.FINISH_OPTION) {
            String modelName = (String) wiz.getProperty("modelName");
            String[] modelFields = (String[]) wiz.getProperty("modelFields");
            // System.out.println(Arrays.toString(modelFields));
            URL template = CreateModelAction.class.getResource(TEMPLATE);

            System.out.println("template: " + template.toString());
            // WriteFileFromTemplate.modelFile(context, template, modelName, modelFields);
            System.out.println("WizardDescriptor: FINISH_OPTION");
        }
        System.out.println("CreateModelAction: ActionPerformed");
    }

    @Override
    public JMenuItem getPopupPresenter() {
        return new JMenuItem(this);
    }
}