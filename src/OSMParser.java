package src;

import java.io.File;
import java.util.*;
import javax.xml.parsers.*;
import org.w3c.dom.*;

//osm 파일을 파싱하기 위한 클래스
public class OSMParser {
    public Map<Long, Node> nodes = new HashMap<>();
    public List<Way> ways = new ArrayList<>();
    private String filename;

    public OSMParser(String filename) { this.filename = filename; }

    //파싱 함수
    public void parse() throws Exception {
        //html, xml 형태를 파싱하기 위한 documentBuilder 사용
        DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document doc = db.parse(new File(filename));

        doc.getDocumentElement().normalize();

        //node의 엘리먼트값들을 파싱하여 NodeList로 저장
        NodeList nodeList = doc.getElementsByTagName("node");

        for (int i = 0; i < nodeList.getLength(); i++) {
            Element elem = (Element) nodeList.item(i);
            long id = Long.parseLong(elem.getAttribute("id"));
            double lat = Double.parseDouble(elem.getAttribute("lat"));
            double lon = Double.parseDouble(elem.getAttribute("lon"));

            nodes.put(id, new Node(id, lat, lon));
        }

        //way 엘리먼트값들을 파싱하여 NodeList로 저장
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
