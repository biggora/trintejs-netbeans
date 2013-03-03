package org.netbeans.modules.trintejs.tools;

import org.netbeans.modules.trintejs.json.JSONArray;
import org.netbeans.modules.trintejs.json.JSONObject;
import org.netbeans.modules.trintejs.json.JSONTokener;
import org.netbeans.modules.trintejs.json.XML;

/**
 * @author Aleks
 */
public class ProjectData {

    private static String projectDataFile = "package.json";
    private static String projectDir = "";
    private static JSONObject projectData;

    public static String getFileName() {
        return ProjectData.projectDir + "/" + ProjectData.projectDataFile;
    }

    public static Object getJSON(String projectDir) {
        ProjectData.projectDir = projectDir;
        String fileSource = TrinteJSFileReader.read(ProjectData.getFileName());
        ProjectData.projectData = new JSONObject(fileSource);
        return ProjectData.projectData;
    }

    public static String getXML(String projectDir) {
        ProjectData.projectDir = projectDir;
        ProjectData.getJSON(ProjectData.getFileName());
        String projectXml = XML.toString(ProjectData.projectData, "root");
        return projectXml;
    }

}
