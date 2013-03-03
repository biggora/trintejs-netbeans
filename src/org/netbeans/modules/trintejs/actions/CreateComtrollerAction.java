package org.netbeans.modules.trintejs.actions;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import org.netbeans.api.project.Project;
import org.openide.util.ImageUtilities;
import org.openide.util.actions.Presenter;

public final class CreateComtrollerAction extends AbstractAction implements Presenter.Popup  {

    private static final String ICON = "org/netbeans/modules/trintejs/images/document.png";
    private final Project context;

    public CreateComtrollerAction(Project context) {
        this.context = context;
        putValue(SMALL_ICON, ImageUtilities.loadImageIcon(ICON, false));
        putValue(NAME, "Create Comtroller");
    }

    @Override
    public void actionPerformed(ActionEvent ev) {
        JOptionPane.showMessageDialog(null, "Hello CreateComtrollerAction.\n");
    }


    @Override
    public JMenuItem getPopupPresenter() {
        return new JMenuItem(this);
    }
}