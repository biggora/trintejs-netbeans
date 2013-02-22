package org.netbeans.modules.trintejs.project;

import java.awt.Image;
import java.beans.PropertyChangeListener;
import java.io.File;
import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import org.netbeans.api.annotations.common.StaticResource;
import org.netbeans.api.project.Project;
import org.netbeans.api.project.ProjectInformation;
import org.netbeans.spi.project.ProjectState;
import org.netbeans.spi.project.ui.LogicalViewProvider;
import org.netbeans.spi.project.ui.support.CommonProjectActions;
import org.netbeans.spi.project.ui.support.NodeFactorySupport;
import org.openide.filesystems.FileObject;
import org.openide.loaders.DataFolder;
import org.openide.loaders.DataObjectNotFoundException;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.nodes.FilterNode;
import org.openide.nodes.Node;
import org.openide.util.Exceptions;
import org.openide.util.ImageUtilities;
import org.openide.util.Lookup;
import org.openide.util.lookup.Lookups;
import org.openide.util.lookup.ProxyLookup;

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

    /*
     *
     */
    private final class Info implements ProjectInformation {

        @StaticResource()
        public static final String CUSTOMER_ICON = "org/netbeans/modules/trintejs/images/trinte.png";

        @Override
        public Icon getIcon() {
            return new ImageIcon(ImageUtilities.loadImage(CUSTOMER_ICON));
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

    /*
     *
     */
    class TrinteJSProjectLogicalView implements LogicalViewProvider {

        @StaticResource()
        public static final String CUSTOMER_ICON = "org/netbeans/modules/trintejs/images/trinte.png";
        private final TrinteJSProject project;

        public TrinteJSProjectLogicalView(TrinteJSProject project) {
            this.project = project;
        }

        @Override
        public Node createLogicalView() {
            try {
                //Obtain the project directory's node:
                FileObject projectDirectory = project.getProjectDirectory();
                DataFolder projectFolder = DataFolder.findFolder(projectDirectory);
                Node nodeOfProjectFolder = projectFolder.getNodeDelegate();
                //Decorate the project directory's node:
                return new ProjectNode(nodeOfProjectFolder, project);
            } catch (DataObjectNotFoundException donfe) {
                Exceptions.printStackTrace(donfe);
                //Fallback-the directory couldn't be created -
                //read-only filesystem or something evil happened
                return new AbstractNode(Children.LEAF);
            }
        }

        @Override
        public Node findPath(Node root, Object target) {
            // throw new UnsupportedOperationException("Not supported yet.");
            return null;
        }

        private final class ProjectNode extends FilterNode {

            final TrinteJSProject project;

            public ProjectNode(Node node, TrinteJSProject project)
                    throws DataObjectNotFoundException {
                super(node,
                        NodeFactorySupport.createCompositeChildren(
                        project,
                        "Projects/org-netbeans-modules-trintejs-project/Nodes"),
                        // new FilterNode.Children(node),
                        new ProxyLookup(
                        new Lookup[]{
                            Lookups.singleton(project),
                            node.getLookup()
                        }));
                this.project = project;
            }

            @Override
            public Action[] getActions(boolean arg0) {
                return new Action[]{
                            CommonProjectActions.newFileAction(),
                            CommonProjectActions.renameProjectAction(),
                            CommonProjectActions.copyProjectAction(),
                            CommonProjectActions.moveProjectAction(),
                            CommonProjectActions.deleteProjectAction(),
                            CommonProjectActions.closeProjectAction(),
                            CommonProjectActions.customizeProjectAction()
                        };
            }

            @Override
            public Image getIcon(int type) {
                return ImageUtilities.loadImage(CUSTOMER_ICON);
            }

            @Override
            public Image getOpenedIcon(int type) {
                return getIcon(type);
            }

            @Override
            public String getDisplayName() {
                return project.getProjectDirectory().getName();
            }
        }
    }
}
