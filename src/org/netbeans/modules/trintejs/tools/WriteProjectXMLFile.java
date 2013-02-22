package org.netbeans.modules.trintejs.tools;

import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * @author Aleks
 */
public class WriteProjectXMLFile {

    public static void main(String argv[]) {

        try {

            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

            // root elements
            Document doc = docBuilder.newDocument();
            Element rootElement = doc.createElement("project");
            doc.appendChild(rootElement);

            // staff elements
            Element ptype = doc.createElement("type");
            ptype.appendChild(doc.createTextNode("org.gora.api.trintejs"));
            rootElement.appendChild(ptype);

            // firstname elements
            Element configuration = doc.createElement("configuration");

            Element data = doc.createElement("data");
            Attr attr = doc.createAttribute("xmlns");
            attr.setValue("http://www.netbeans.org/ns/trintejs-project/1");
            data.setAttributeNode(attr);
            configuration.appendChild(data);

            Element name = doc.createElement("name");
            name.appendChild(doc.createTextNode("yong"));
            data.appendChild(name);

            // write the content into xml file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File("C:\\file.xml"));

            transformer.transform(source, result);

        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        } catch (TransformerException tfe) {
            tfe.printStackTrace();
        }
    }
}
