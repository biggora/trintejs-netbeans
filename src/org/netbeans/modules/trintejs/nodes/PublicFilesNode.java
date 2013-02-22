package org.netbeans.modules.trintejs.nodes;

import java.awt.Image;
import org.netbeans.api.annotations.common.StaticResource;
import org.netbeans.api.project.Project;
import org.openide.filesystems.FileUtil;
import org.openide.loaders.DataFolder;
import org.openide.loaders.DataObject;
import org.openide.loaders.DataObjectNotFoundException;
import org.openide.nodes.FilterNode;
import org.openide.util.ImageUtilities;
/**
 * @author Aleks
 */
public class PublicFilesNode extends FilterNode {

    @StaticResource
    private static final String IMAGE = "org/netbeans/modules/trintejs/images/shortcut.png";

    public PublicFilesNode(Project proj) throws DataObjectNotFoundException {
        super(DataObject.find(proj.getProjectDirectory().
                getFileObject("public")).getNodeDelegate());
    }

    @Override
    public String getDisplayName() {
        return "Public Files";
    }

    //Next, we add icons, for the default state, which is
    //closed, and the opened state; we will make them the same.
    //
    //Icons in project logical views are
    //based on combinations--you can combine the node's own icon
    //with a distinguishing badge that is merged with it. Here we
    //first obtain the icon from a data folder, then we add our
    //badge to it by merging it via a NetBeans API utility method:
    @Override
    public Image getIcon(int type) {
        DataFolder root = DataFolder.findFolder(FileUtil.getConfigRoot());
        Image original = root.getNodeDelegate().getIcon(type);
        return ImageUtilities.mergeImages(original,
                ImageUtilities.loadImage(IMAGE), 7, 7);
    }
    @Override
    public Image getOpenedIcon(int type) {
        DataFolder root = DataFolder.findFolder(FileUtil.getConfigRoot());
        Image original = root.getNodeDelegate().getIcon(type);
        return ImageUtilities.mergeImages(original,
                ImageUtilities.loadImage(IMAGE), 7, 7);
    }
}
