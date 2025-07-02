package src;

import java.io.File;
import java.util.*;
import javax.xml.parsers.*;
import org.w3c.dom.*;

public class OSMParser {
    public Map<Long, Node> nodes = new HashMap<>();
    public List<Way> ways = new ArrayList<>();
    private String filename;

    public OSMParser(String filename) { this.filename = filename; }

    public void parse() throws Exception {
        DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document doc = db.parse(new File(filename));
        doc.getDocumentElement().normalize();

        NodeList nodeList = doc.getElementsByTagName("node");
        for (int i = 0; i < nodeList.getLength(); i++) {
            Element elem = (Element) nodeList.item(i);
            long id = Long.parseLong(elem.getAttribute("id"));
            double lat = Double.parseDouble(elem.getAttribute("lat"));
            double lon = Double.parseDouble(elem.getAttribute("lon"));
            nodes.put(id, new Node(id, lat, lon));
        }

        NodeList wayList = doc.getElementsByTagName("way");
        for (int i = 0; i < wayList.getLength(); i++) {
            Element wayElem = (Element) wayList.item(i);
            long id = Long.parseLong(wayElem.getAttribute("id"));
            List<Long> nodeIds = new ArrayList<>();
            NodeList nds = wayElem.getElementsByTagName("nd");
            for (int j = 0; j < nds.getLength(); j++) {
                Element nd = (Element) nds.item(j);
                nodeIds.add(Long.parseLong(nd.getAttribute("ref")));
            }
            ways.add(new Way(id, nodeIds));
        }
    }
}
