package org.netbeans.modules.trintejs.project;

import java.beans.PropertyChangeListener;
import java.io.File;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import org.netbeans.api.annotations.common.StaticResource;
import org.netbeans.api.project.Project;
import org.netbeans.api.project.ProjectInformation;
import org.netbeans.spi.project.ProjectState;
import org.openide.filesystems.FileObject;
import org.openide.util.ImageUtilities;
import org.openide.util.Lookup;
import org.openide.util.lookup.Lookups;

/**
 * @author Aleks
 */
public class TrinteJSProject implements Project {

    private final FileObject projectDir;
    private final ProjectState state;
    private Lookup lkp;
    private final File nbdir;

    TrinteJSProject(FileObject dir, ProjectState state) {

        nbdir = new File(dir.getPath() + "/nbproject");
        if (!nbdir.exists()) {
           // nbdir.mkdir();
        }
        /**/
        this.projectDir = dir;
        this.state = state;
    }

    @Override
    public FileObject getProjectDirectory() {
        return projectDir;
    }

    @Override
    public Lookup getLookup() {
        if (lkp == null) {
            lkp = Lookups.fixed(new Object[]{
                        new Info(),
                        new TrinteJSProjectLogicalView(this),
                        new TrinteJSCustomizerProvider(this),});
        }
        return lkp;
    }


    private final class Info implements ProjectInformation {

        @StaticResource()
        public static final String TRINTE_ICON = "org/netbeans/modules/trintejs/images/trinte.png";

        @Override
        public Icon getIcon() {
            return new ImageIcon(ImageUtilities.loadImage(TRINTE_ICON));
        }

        @Override
        public String getName() {
            return getProjectDirectory().getName();
        }

        @Override
        public String getDisplayName() {
            return getName();
        }

        @Override
        public void addPropertyChangeListener(PropertyChangeListener pcl) {
            //do nothing, won't change
        }

        @Override
        public void removePropertyChangeListener(PropertyChangeListener pcl) {
            //do nothing, won't change
        }

        @Override
        public Project getProject() {
            return TrinteJSProject.this;
        }
    }
}
