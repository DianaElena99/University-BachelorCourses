package Repository;

import Domain.Homework;
import Domain.Validator.IllegalArgumentException;
import Domain.Validator.Validator;
import Domain.Validator.ValidatorException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.util.Collection;


public class HomeworkXMLRepository extends Repository.AbstractRepository<Homework, Integer> {
    private String fileName;
    private DocumentBuilderFactory factory;

    public HomeworkXMLRepository(Validator<Homework> valid, String fileName){
        super(valid);
        this.fileName = fileName;
        this.factory = DocumentBuilderFactory.newInstance();
        try{
            loadFromXML();}
        catch (ValidatorException | IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    private void loadFromXML() throws  ValidatorException, IllegalArgumentException{
        try {
            DocumentBuilder db = this.factory.newDocumentBuilder();
            Document doc = db.parse(new File(this.fileName));
            Element el = (Element) doc.getDocumentElement();

            NodeList nodeList = el.getElementsByTagName("tema");

            for(int i = 0; i<nodeList.getLength(); i++){
                Integer id, recvWeek, deadlineWeek;
                String description;
                Element item = (Element) nodeList.item(i);

                id = Integer.parseInt(item.getAttribute("id"));

                NodeList nodeList1 = item.getElementsByTagName("titlu");
                description = nodeList1.item(0).getChildNodes().item(0).getNodeValue();

                nodeList1 = item.getElementsByTagName("weekRecv");
                recvWeek = Integer.parseInt(nodeList1.item(0).getChildNodes().item(0).getNodeValue());

                nodeList1 = item.getElementsByTagName("deadline");
                deadlineWeek = Integer.parseInt(nodeList1.item(0).getChildNodes().item(0).getNodeValue());

                Homework hw = new Homework(id, description, recvWeek, deadlineWeek);
                super.save(hw);
            }

        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }

    }

    private void writeToXML(){
        try{
            DocumentBuilder db = this.factory.newDocumentBuilder();
            Document doc = db.newDocument();
            Element el = doc.createElement("teme");
            doc.appendChild(el);

            for (Homework h:super.findAll()){
                el.appendChild(getHomework(doc, h.getID(), h.getWeekRecv(), h.getDeadline(), h.getTitle()));
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            DOMSource ds = new DOMSource(doc);
            StreamResult streamResult = new StreamResult(new File(this.fileName));
            Transformer transformer = transformerFactory.newTransformer();
            transformer.transform(ds, streamResult);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    private static Node getHomework(Document doc, Integer id, Integer weekRecv,
                                    Integer deadline, String titlu){
        Element el = doc.createElement("tema");
        el.setAttribute("id", id.toString());
        el.appendChild(getHomeworkElements(doc, "titlu", titlu));
        el.appendChild(getHomeworkElements(doc, "weekRecv", weekRecv.toString()));
        el.appendChild(getHomeworkElements(doc, "deadline", deadline.toString()));
        return el;
    }

    private static Node getHomeworkElements(Document doc, String name, String value){
        Element node = doc.createElement(name);
        node.appendChild(doc.createTextNode(value));
        return node;
    }

    @Override
    public Homework save(Homework obj) throws IllegalArgumentException, ValidatorException {
        Homework h1 = super.save(obj);
        if (h1 == null)
            writeToXML();
        return h1;
    }

    @Override
    public Homework update(Homework obj) throws ValidatorException, IllegalArgumentException {
        Homework h1 =  super.update(obj);
        writeToXML();
        return h1;
    }

    @Override
    public Homework delete(Integer integer) throws IllegalArgumentException {
        Homework target =  super.delete(integer);
        if (target != null){
            writeToXML();
        }
        return null;
    }

    @Override
    public Homework find(Integer integer) throws IllegalArgumentException {
        return super.find(integer);
    }

    @Override
    public Collection<Homework> findAll() {
        return super.findAll();
    }
}
