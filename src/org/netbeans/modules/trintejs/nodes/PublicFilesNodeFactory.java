package org.netbeans.modules.trintejs.nodes;

import org.netbeans.api.project.Project;
import org.netbeans.spi.project.ui.support.NodeFactory;
import org.netbeans.spi.project.ui.support.NodeFactorySupport;
import org.netbeans.spi.project.ui.support.NodeList;
import org.openide.loaders.DataObjectNotFoundException;
import org.openide.util.Exceptions;

/**
 * @author Aleks
 */
@NodeFactory.Registration(projectType = "org-netbeans-modules-trintejs-project")
public class PublicFilesNodeFactory implements NodeFactory {

    @Override
    public NodeList createNodes(Project project) {

        //Optionally, only return a new node
        //if some item is in the project's lookup:
        //MyCoolLookupItem item = project.getLookup().lookup(MyCoolLookupItem.class);
        if (this.ifExist(project, "public")) {
            try {
                PublicFilesNode nd = new PublicFilesNode(project);
                return NodeFactorySupport.fixedNodeList(nd);
            } catch (DataObjectNotFoundException ex) {
                Exceptions.printStackTrace(ex);
            }
        }

        //If the above try/catch fails, e.g.,
        //our item isn't in the lookup,
        //then return an empty list of nodes:
        return NodeFactorySupport.fixedNodeList();
    }

    public boolean ifExist(Project project, String Folder) {
        return project.getProjectDirectory().getFileObject(Folder) != null;
    }
}
