package org.netbeans.modules.trintejs.actions;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import org.netbeans.api.annotations.common.StaticResource;
import org.netbeans.api.project.Project;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.util.ImageUtilities;
import org.openide.util.Lookup;
import org.openide.util.NbBundle.Messages;
import org.openide.util.Utilities;
import org.openide.util.actions.Presenter;

@ActionID(
    category = "Project",
id = "org.netbeans.modules.trintejs.actions.CreateModelAction")
@ActionReferences({
    @ActionReference(path = "Menu/File", position = 0),
    @ActionReference(path = "Loaders/Languages/Actions", position = 0),
    @ActionReference(path = "Projects/Actions")
})
@ActionRegistration(
    displayName = "#CTL_CreateModelAction")
@Messages("CTL_CreateModelAction=Create Model")
public final class CreateModelAction extends AbstractAction implements Presenter.Popup {

    @StaticResource
    private static final String ICON = "org/netbeans/modules/trintejs/images/document.png";
    private static final long serialVersionUID = 1L;
    private final Lookup context;

    public CreateModelAction() {
        this.context = Utilities.actionsGlobalContext();
        putValue(SMALL_ICON, ImageUtilities.loadImageIcon(ICON, false));
        putValue(NAME, Bundle.CTL_CreateModelAction());
    }

    @Override
    public boolean isEnabled() {
        return null != context.lookup(Project.class);
    }

    @Override
    public void actionPerformed(ActionEvent ev) {
        JOptionPane.showMessageDialog(null, "Hello colorful project.\n" + context.lookup(Project.class).toString());
    }

    @Override
    public JMenuItem getPopupPresenter() {
        return new JMenuItem(this);
    }
}
