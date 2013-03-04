package org.netbeans.modules.trintejs.templates.mvc;

import java.awt.Component;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import javax.swing.JComponent;
import javax.swing.event.ChangeListener;
import org.netbeans.api.project.Project;
import org.netbeans.api.templates.TemplateRegistration;
import org.netbeans.modules.trintejs.json.JSONObject;
import org.netbeans.modules.trintejs.tools.GeneratorHelper;
import org.netbeans.modules.trintejs.tools.Inflector;
import org.netbeans.modules.trintejs.tools.ProjectData;
import org.netbeans.spi.project.ui.templates.support.Templates;
import org.openide.WizardDescriptor;
import org.openide.filesystems.FileObject;
import org.openide.loaders.DataFolder;
import org.openide.loaders.DataObject;
import org.openide.util.ImageUtilities;
import org.openide.util.NbBundle.Messages;

@TemplateRegistration(
folder = "TrinteJS",
displayName = "#CreateServerSideModelWizardIterator_displayName",
iconBase = "org/netbeans/modules/trintejs/images/mvc-template.png",
description = "createServerSideModel.html",
content = "modelTemplate.js",
scriptEngine = "freemarker")
@Messages("CreateServerSideModelWizardIterator_displayName=Server Side Model")
public final class CreateServerSideModelWizardIterator implements WizardDescriptor.InstantiatingIterator<WizardDescriptor> {

    private int index;
    private WizardDescriptor wizard;
    private List<WizardDescriptor.Panel<WizardDescriptor>> panels;
    private Project project;

    private List<WizardDescriptor.Panel<WizardDescriptor>> getPanels() {
        if (panels == null) {
            panels = new ArrayList<WizardDescriptor.Panel<WizardDescriptor>>();
            panels.add(new CreateServerSideModelWizardPanelName());
            panels.add(new CreateServerSideModelWizardPanelFields());
            String[] steps = createSteps();
            for (int i = 0; i < panels.size(); i++) {
                Component c = panels.get(i).getComponent();
                if (steps[i] == null) {
                    // Default step name to component name of panel. Mainly
                    // useful for getting the name of the target chooser to
                    // appear in the list of steps.
                    steps[i] = c.getName();
                }
                if (c instanceof JComponent) { // assume Swing components
                    JComponent jc = (JComponent) c;
                    jc.putClientProperty(WizardDescriptor.PROP_CONTENT_SELECTED_INDEX, i);
                    jc.putClientProperty(WizardDescriptor.PROP_CONTENT_DATA, steps);
                    jc.putClientProperty(WizardDescriptor.PROP_AUTO_WIZARD_STYLE, true);
                    jc.putClientProperty(WizardDescriptor.PROP_CONTENT_DISPLAYED, true);
                    jc.putClientProperty(WizardDescriptor.PROP_CONTENT_NUMBERED, true);
                }
            }
        }
        return panels;
    }

    @Override
    public Set<?> instantiate() throws IOException {

        FileObject createdFile = null;

        // Read Model Name from wizard
        String modelName = (String) wizard.getProperty(CreateServerSideModelVisualPanelName.MODEL_NAME);
        String[] modelFields = (String[]) wizard.getProperty("modelFields");
        // FreeMarker Template will get its variables from HashMap.
        // HashMap key is the variable name.
        Map<String, String> options = new HashMap<String, String>();

        //Get the template and convert it:
        FileObject template = Templates.getTemplate(wizard);
        DataObject dTemplate = DataObject.find(template);

        //Get the package:
        project = Templates.getProject(wizard);
        FileObject projectDir = project.getProjectDirectory();
        FileObject mdf = project.getProjectDirectory().getFileObject("app/models");
        DataFolder dfl = DataFolder.findFolder(mdf);

        Inflector inflector = new Inflector();
        String SingularModelName = inflector.singularize(modelName);
        String CamelModelName = inflector.camelCase(SingularModelName, true, null);
        // String PluralModelName = inflector.pluralize(CamelModelName);
        JSONObject projectData = (JSONObject) ProjectData.getJSON(projectDir.getPath());
        DateFormat dfor = new SimpleDateFormat("yyyy-MM-dd'T'HH:mmZ");
        String nowAsString = dfor.format(new Date());
        String fields = GeneratorHelper.CreateFields(modelFields);

        options.put("modelName", CamelModelName);
        options.put("projectName", projectData.getString("name"));
        options.put("description", projectData.getString("description"));
        options.put("version", projectData.getString("version"));
        options.put("created", nowAsString);
        options.put("fields", fields);

        //Define the template from the above,
        //passing the package, the file name, and the map of strings to the template:
        DataObject dobj = dTemplate.createFromTemplate(dfl, CamelModelName, options);

        //Obtain a FileObject:
        createdFile = dobj.getPrimaryFile();

        // Return the created file.
        return Collections.singleton(createdFile);
    }

    @Override
    public void initialize(WizardDescriptor wizard) {
        wizard.putProperty(WizardDescriptor.PROP_IMAGE, ImageUtilities.loadImage("org/netbeans/modules/trintejs/images/trintejs-banner.png", true));
        this.wizard = wizard;
    }

    @Override
    public void uninitialize(WizardDescriptor wizard) {
        panels = null;
    }

    @Override
    public WizardDescriptor.Panel<WizardDescriptor> current() {
        return getPanels().get(index);
    }

    @Override
    public String name() {
        return index + 1 + ". from " + getPanels().size();
    }

    @Override
    public boolean hasNext() {
        return index < getPanels().size() - 1;
    }

    @Override
    public boolean hasPrevious() {
        return index > 0;
    }

    @Override
    public void nextPanel() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        index++;
    }

    @Override
    public void previousPanel() {
        if (!hasPrevious()) {
            throw new NoSuchElementException();
        }
        index--;
    }

    // If nothing unusual changes in the middle of the wizard, simply:
    @Override
    public void addChangeListener(ChangeListener l) {
    }

    @Override
    public void removeChangeListener(ChangeListener l) {
    }
    // If something changes dynamically (besides moving between panels), e.g.
    // the number of panels changes in response to user input, then use
    // ChangeSupport to implement add/removeChangeListener and call fireChange
    // when needed

    // You could safely ignore this method. Is is here to keep steps which were
    // there before this wizard was instantiated. It should be better handled
    // by NetBeans Wizard API itself rather than needed to be implemented by a
    // client code.
    private String[] createSteps() {
        String[] beforeSteps = (String[]) wizard.getProperty("WizardPanel_contentData");
        assert beforeSteps != null : "This wizard may only be used embedded in the template wizard";
        String[] res = new String[(beforeSteps.length - 1) + panels.size()];
        for (int i = 0; i < res.length; i++) {
            if (i < (beforeSteps.length - 1)) {
                res[i] = beforeSteps[i];
            } else {
                res[i] = panels.get(i - beforeSteps.length + 1).getComponent().getName();
            }
        }
        return res;
    }
}
