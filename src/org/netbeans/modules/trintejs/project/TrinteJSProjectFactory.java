package org.netbeans.modules.trintejs.project;

import java.io.IOException;
import org.netbeans.api.project.Project;
import org.netbeans.spi.project.ProjectFactory;
import org.netbeans.spi.project.ProjectState;
import org.openide.filesystems.FileObject;
import org.openide.util.lookup.ServiceProvider;

/**
 * @author Aleks
 */
@ServiceProvider(service=ProjectFactory.class)
public class TrinteJSProjectFactory implements ProjectFactory {

    public static final String PROJECT_FILE = ".trinte-status";

    //Specifies when a project is a project, i.e.,
    //if ".trinte-status" is present in a folder:
    @Override
    public boolean isProject(FileObject projectDirectory) {
        return projectDirectory.getFileObject(PROJECT_FILE) != null;
    }

    //Specifies when the project will be opened, i.e., if the project exists:
    @Override
    public Project loadProject(FileObject dir, ProjectState state) throws IOException {
        return isProject(dir) ? new TrinteJSProject(dir, state) : null;
    }

    @Override
    public void saveProject(final Project project) throws IOException, ClassCastException {
        // leave unimplemented for the moment
    }

}
