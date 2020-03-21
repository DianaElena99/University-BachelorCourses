package Repository;

import Domain.Grade;
import Domain.Validator.IllegalArgumentException;
import Domain.Validator.Validator;
import Domain.Validator.ValidatorException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.time.LocalDate;
import java.util.Collection;

public class GradeXMLRepository extends AbstractRepository<Grade,String> {
    private String fileName;
    private DocumentBuilderFactory factory;

    public GradeXMLRepository(Validator<Grade> valid, String fileName){
        super(valid);
        this.fileName = fileName;
        this.factory = DocumentBuilderFactory.newInstance();
        readFromXML();
    }


    private void readFromXML() {
        try{
            DocumentBuilder db = factory.newDocumentBuilder();
            Document doc = db.parse(new File(fileName));
            Element el = (Element) doc.getDocumentElement();
            NodeList nodes = el.getElementsByTagName("nota");

            for(int i=0; i<nodes.getLength(); i++){
                Float nota; Integer id_stud, id_tema; String nume_prof;
                LocalDate data;
                //Node item = nodes.item(i);
                org.w3c.dom.Element gradeElem =  (Element) nodes.item(i);

                id_stud = Integer.parseInt(gradeElem.getElementsByTagName("stud").item(0).
                        getTextContent());
                id_tema = Integer.parseInt(gradeElem.getElementsByTagName("tema").item(0).
                        getTextContent());

                nota = Float.parseFloat(gradeElem.getElementsByTagName("grade").item(0).getTextContent());

                nume_prof = gradeElem.getElementsByTagName("prof").item(0).getTextContent();

                String data_aux = gradeElem.getElementsByTagName("data").item(0).getTextContent();
                data = LocalDate.parse(data_aux);

                Grade g = new Grade(id_stud, id_tema, nota, data, nume_prof);
                super.save(g);
            }

        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public Grade save(Grade obj) throws IllegalArgumentException, ValidatorException {
        Grade g = super.save(obj);
        writeToXML();
        return g;
    }

    private void writeToXML() {
        try{
            DocumentBuilder db = this.factory.newDocumentBuilder();
            Document doc = db.newDocument();
            Element el = doc.createElement("note");

            doc.appendChild(el);
            for (Grade gr: super.findAll()){
                el.appendChild(getGrade(doc, gr.getID_Student().toString(),
                        gr.getID_Tema().toString(), gr.getNota(),
                        gr.getProfesor(), gr.getData()));
            }
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            DOMSource domSource = new DOMSource(doc);
            StreamResult streamResult = new StreamResult(this.fileName);
            Transformer transformer = transformerFactory.newTransformer();
            transformer.transform(domSource,streamResult);
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    private static Node getGrade(Document doc, String stud, String tema, Float nota, String prof, LocalDate data){
        Element grade = doc.createElement("nota");

        grade.appendChild(getGradeElements(doc, "stud", stud));
        grade.appendChild(getGradeElements(doc, "tema", tema));
        grade.appendChild(getGradeElements(doc, "grade", nota.toString()));
        grade.appendChild(getGradeElements(doc, "prof", prof));
        grade.appendChild(getGradeElements(doc,"data", data.toString()));

        return grade;
    }

    private static Node getGradeElements(Document doc, String name, String value){
        Element node = doc.createElement(name);
        node.appendChild(doc.createTextNode(value));
        return node;
    }

    @Override
    public Grade update(Grade obj) throws ValidatorException, IllegalArgumentException {
        Grade g = super.update(obj);
        writeToXML();
        return g;
    }

    @Override
    public Grade delete(String s) throws IllegalArgumentException {
        Grade g = super.delete(s);
        writeToXML();
        return g;
    }

    @Override
    public Grade find(String s) throws IllegalArgumentException {
        return super.find(s);
    }

    @Override
    public Collection<Grade> findAll() {
        return super.findAll();
    }
}
