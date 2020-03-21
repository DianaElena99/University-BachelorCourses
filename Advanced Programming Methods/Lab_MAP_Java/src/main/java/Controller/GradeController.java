package Controller;

import Domain.Grade;
import Domain.Homework;
import Domain.Student;
import Domain.Validator.IllegalArgumentException;
import Domain.Validator.ValidatorException;
import Repository.AbstractRepository;
import Utils.EntityEventChange;
import Utils.Event;
import Utils.Observable;
import Utils.Observer;
import com.sun.mail.smtp.SMTPTransport;
import javafx.util.Pair;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.WeekFields;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import static java.lang.StrictMath.abs;

public class GradeController implements Observable {
    private HashMap<Integer, Document> store = new HashMap<>();

    private List<Observer<EntityEventChange>> observers=new ArrayList<>();

    private static String EMAIL_FROM = "elenapirvu99@gmail.com";
    private static final String SMTP_SERVER = "smtp server ";
    private static final String USERNAME = "";
    private static final String PASSWORD = "";

    @Override
    public void addObserver(Observer e) {
        observers.add(e);
    }

    @Override
    public void removeObserver(Observer e) {
        //observers.remove(e);
    }

    @Override
    public void notifyObservers() {
        observers.stream().forEach(x->x.update());
    }

    private AbstractRepository<Student, Integer> studentRepo;
    private AbstractRepository<Homework,Integer> homeworkRepo;
    private AbstractRepository<Grade, String> gradeRepo;

    public GradeController(AbstractRepository<Student, Integer> studentRepo,
                           AbstractRepository<Homework, Integer> homeworkRepo,
                           AbstractRepository<Grade, String> gradeRepo) {
        this.studentRepo = studentRepo;
        this.homeworkRepo = homeworkRepo;
        this.gradeRepo = gradeRepo;
    }

    public Student addStudent(Student student) throws Exception {
        if (studentRepo.find(student.getID())!=null)
            return studentRepo.find(student.getID());
        studentRepo.save(student);
        notifyObservers();
        return null;
    }

    public Student deleteStudent(Integer id) throws IllegalArgumentException {
        Student target = studentRepo.delete(id);
        HashMap<String, Grade> mapp = new HashMap<>();
        for (Grade gr:gradeRepo.findAll()
             ) {
            if (gr.getID_Student().equals(id))
                gradeRepo.delete(gr.getID());
        }
        notifyObservers();
        return target;
    }

    public Collection<Student> getAllStudents(){
        return studentRepo.findAll();
    }

    public Homework addHomework(Homework hw) throws Exception{
        if (homeworkRepo.find(hw.getID())!=null)
            return homeworkRepo.find(hw.getID());
        homeworkRepo.save(hw);
        notifyObservers();
        return null;
    }

    public Homework deleteHomework(Integer id) throws IllegalArgumentException {
        Homework deleted = homeworkRepo.delete(id);
        for (Grade gr:gradeRepo.findAll()
        ) {
            if (gr.getID_Tema().equals(id)){
                String idd = gr.getID();
                gradeRepo.delete(idd);
            }
        }
        notifyObservers();
        return deleted;
    }

    public Collection<Homework> getAllHomework(){
        return homeworkRepo.findAll();
    }


    public Homework ExtendDeadline(Homework h) throws IllegalArgumentException, ValidatorException {
        Integer init = h.getDeadline();
        h.extendDeadline();
        if(h.getDeadline() > init){
            homeworkRepo.update(h);
        }
        notifyObservers();
        return h;
    }

    private void writeInXML(Document doc, String filePath, String id_tema, String week_prd, String dead_line, String grade, String prof, String feedback) throws Exception {

        Element root = doc.getDocumentElement();

        Element root_chd = doc.createElement("entry");

        Node tema = doc.createElement("tema");
        tema.appendChild(doc.createTextNode(id_tema));
        root_chd.appendChild(tema);

        Node sapt = doc.createElement("predat");
        sapt.appendChild(doc.createTextNode(week_prd));
        root_chd.appendChild(sapt);

        Node dl = doc.createElement("deadline");
        dl.appendChild(doc.createTextNode(dead_line));
        root_chd.appendChild(dl);

        Node gr = doc.createElement("nota");
        gr.appendChild(doc.createTextNode(grade));
        root_chd.appendChild(gr);

        Node profesor = doc.createElement("prof");
        profesor.appendChild(doc.createTextNode(prof));
        root_chd.appendChild(profesor);

        Node feedbk = doc.createElement("feedback");
        feedbk.appendChild(doc.createTextNode(feedback));
        root_chd.appendChild(feedbk);

        root.appendChild(root_chd);

        DOMSource domSource = new DOMSource(doc);
        StreamResult result = new StreamResult(filePath);

        Transformer xformer = TransformerFactory.newInstance().newTransformer();
        xformer.transform(domSource, result);
    }

    public void addGrade(Boolean ScutireMotivata, Boolean ProfDelay,Integer StudID, Integer HwID, Float nota, String prof, String feedback) throws Exception {

        Integer diferenta = abs(homeworkRepo.find(HwID).getDeadline() - Homework.getCurrentWeek()) ;

        if (!ScutireMotivata && !ProfDelay && Homework.getCurrentWeek() > homeworkRepo.find(HwID).getDeadline()){
            if (diferenta > 2){
                nota = 1f;
            }
            else
                nota = nota - diferenta;
        }

        String id_tema = "Tema " + HwID.toString();
        String week_prd = "Predat in saptamana : " + Homework.getCurrentWeek();
        String dead_line = "Deadline : "+ homeworkRepo.find(HwID).getDeadline().toString();

        String grade = "Nota : " + nota.toString();
        feedback = "Feedback : " + feedback;

        String nume_stud = studentRepo.find(StudID).getName();
        if (nume_stud.equals("") )
            return;
        gradeRepo.save(new Grade(StudID, HwID, nota, LocalDate.now(),prof));

        /*String filePath = "C:/Users/user/Desktop/map/src/main/java/StudentFiles/" + StudID + nume_stud + ".xml";
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = factory.newDocumentBuilder();

        //daca deja contine o inregistrare a studentului, face doar append, nu mai creeaza doc de la zero;
        if (store.containsKey(StudID)){
            Document doc = db.parse(filePath);
            writeInXML(doc, filePath, id_tema, week_prd, dead_line, grade, prof, feedback);
        }

        else {
            Document doc = db.newDocument();
            Element root = doc.createElement("entries");
            doc.appendChild(root);

            Element root_chd = doc.createElement("entry");
            root.appendChild(root_chd);

            Node tema = doc.createElement("tema");
            tema.appendChild(doc.createTextNode(id_tema));
            root_chd.appendChild(tema);

            Node sapt = doc.createElement("predat");
            sapt.appendChild(doc.createTextNode(week_prd));
            root_chd.appendChild(sapt);

            Node dl = doc.createElement("deadline");
            dl.appendChild(doc.createTextNode(dead_line));
            root_chd.appendChild(dl);

            Node gr = doc.createElement("nota");
            gr.appendChild(doc.createTextNode(grade));
            root_chd.appendChild(gr);

            Node profesor = doc.createElement("prof");
            profesor.appendChild(doc.createTextNode(prof));
            root_chd.appendChild(profesor);

            Node feedbk = doc.createElement("feedback");
            feedbk.appendChild(doc.createTextNode(feedback));
            root_chd.appendChild(feedbk);

            store.put(StudID, doc);

            DOMSource domSource = new DOMSource(doc);
            File XMLfile = new File(filePath);
            StreamResult result = new StreamResult(XMLfile);

            Transformer xformer = TransformerFactory.newInstance().newTransformer();
            xformer.transform(domSource, result);
        }*/
        notifyObservers();

    }

    public void addNota(Boolean scutit, Integer nrDelay, String nume,Integer idst, Integer idhw, Float nota, String prof, String feedback) throws IllegalArgumentException {
        Integer lim = 0;
        try {
            lim = homeworkRepo.find(idhw).getDeadline();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        Integer diff = Math.abs(Homework.getCurrentWeek() - lim - nrDelay);
        if (scutit)
            diff=0;
        LocalDate data;
        if (diff != 0  || nrDelay != 0) {
            nota = nota - diff;
            data = LocalDate.now().minusWeeks((long) nrDelay);
        }
        else
            data = LocalDate.now();
        try {
            gradeRepo.save(new Grade(idst, idhw, nota, data,prof ));
            Autentificare.sendMail(studentRepo.find(idst).getEmail(),"Ai primit o nota noua","Nota : " + nota + " Tema " +
                    idhw + "Feedback " + feedback );
        } catch (IllegalArgumentException | ValidatorException e) {
            e.printStackTrace();
        }



/*
        String filePath = "C:/Users/user/Desktop/map/src/main/java/StudentFiles/" + idst + nume + ".xml";
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = null;
        try {
            db = factory.newDocumentBuilder();

        Integer week = Homework.getCurrentWeek() - diff;
        //daca deja contine o inregistrare a studentului, face doar append, nu mai creeaza doc de la zero;
        if (store.containsKey(idst)){
            Document doc = db.parse(filePath);
            writeInXML(doc, filePath, idhw.toString(), week.toString(), lim.toString(),nota.toString(), prof, feedback);
        }

        else {
            Document doc = db.newDocument();
            Element root = doc.createElement("entries");
            doc.appendChild(root);

            Element root_chd = doc.createElement("entry");
            root.appendChild(root_chd);

            Node tema = doc.createElement("tema");
            tema.appendChild(doc.createTextNode(idhw.toString()));
            root_chd.appendChild(tema);

            Node sapt = doc.createElement("predat");
            sapt.appendChild(doc.createTextNode(week.toString()));
            root_chd.appendChild(sapt);

            Node dl = doc.createElement("deadline");
            dl.appendChild(doc.createTextNode(lim.toString()));
            root_chd.appendChild(dl);

            Node gr = doc.createElement("nota");
            gr.appendChild(doc.createTextNode(nota.toString()));
            root_chd.appendChild(gr);

            Node profesor = doc.createElement("prof");
            profesor.appendChild(doc.createTextNode(prof));
            root_chd.appendChild(profesor);

            Node feedbk = doc.createElement("feedback");
            feedbk.appendChild(doc.createTextNode(feedback));
            root_chd.appendChild(feedbk);

            store.put(idst, doc);

            DOMSource domSource = new DOMSource(doc);
            File XMLfile = new File(filePath);
            StreamResult result = new StreamResult(XMLfile);

            Transformer xformer = TransformerFactory.newInstance().newTransformer();
            xformer.transform(domSource, result);
        }
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        notifyObservers();
    }

    public Student updateStudent(Student nou) {
        Student result = null;
        try{
            result = studentRepo.update(nou);}
        catch (Exception e){
            e.printStackTrace();
        }
        notifyObservers();
        return result;
    }

    public HashMap<Integer, Float> mediaLabStud() throws IllegalArgumentException {
        HashMap<Integer,Float> aux = new HashMap<>();
        Integer totalWeeks = 0;
        for (Homework hw:homeworkRepo.findAll()
             ) {
            totalWeeks += hw.getDeadline()-hw.getWeekRecv()+1;
        }

        for (Student st:studentRepo.findAll()
             ) {
            Float media = 1f;
            for(Homework hw : homeworkRepo.findAll()){
                Float nota;
                Grade gr = gradeRepo.find(st.getID()+"_"+hw.getID());
                if (gr!=null)
                    nota = gr.getNota() * (hw.getDeadline()-hw.getWeekRecv()+1);
                else nota = 0f;
                media = media + nota;
            }
            media = media / totalWeeks;
            aux.put(st.getID(), media);
        }
        return aux;
    }

    public List<Pair<Homework, Float>> ceaMaiGreaTema(){
        List<Pair<Homework,Float>> lista = new ArrayList<>();

        Float minimum = 100f;
        Homework rez = null;
        Pair<Homework, Float> result = new Pair<>(null,0f);
        for (Homework hw: homeworkRepo.findAll()
             ) {
            Float medie = 0f; Integer cnt = 0;
            for(Grade gr:gradeRepo.findAll()){
                if (gr.getID_Tema().equals(hw.getID()) && hw.getDeadline() <= Homework.getCurrentWeek()){
                    medie += gr.getNota();
                    cnt ++;
                }
            }
            if (cnt!=0)
                medie = medie / cnt;
            else medie = 0f;
            lista.add(new Pair<Homework, Float>(hw, medie));
            if (medie < minimum){
                minimum  = medie;
                rez = hw;
                result = new Pair<>(hw, medie);
            }
        }
        return lista;
    }

    public HashMap<Integer, Float> studentiPromovati() throws IllegalArgumentException {
        HashMap<Integer, Float> medii = mediaLabStud();
        HashMap<Integer, Float> promovati = new HashMap<>();
        Iterator it = medii.entrySet().iterator();
        while(it.hasNext()){
            Map.Entry elem = (Map.Entry) it.next();
            if ((Float)elem.getValue() >= 4f) {
                promovati.put((Integer)elem.getKey(), (Float)elem.getValue());
            }
        }
        return promovati;
    }

    private Integer getWeek(LocalDate data){
        WeekFields weekFields = WeekFields.of(Locale.getDefault());
        LocalDate start = LocalDate.parse("2019-09-30");
        Integer nrstart = start.get(weekFields.weekOfWeekBasedYear());
        return data.get(weekFields.weekOfWeekBasedYear()) - nrstart;
    }

    public List<Student> predatLaTimp() throws IllegalArgumentException {
        List<Student> studenti = new ArrayList<>();
        List<Homework> filtered = homeworkRepo.findAll().stream().filter(x ->
                x.getDeadline() <= Homework.getCurrentWeek()).collect(Collectors.toList());
        for (Student st:studentRepo.findAll()) {
            int nrDelay = 0;
            List<Grade> note = gradeRepo.findAll().stream().filter(x ->
                    x.getID_Student().equals(st.getID())).collect(Collectors.toList());
            for (Homework hw:filtered) {
                Grade gr = gradeRepo.find(st.getID()+"_"+hw.getID());
                if (gr==null)
                    nrDelay++;
                else {
                    Integer nr = getWeek(gr.getData());
                    if (nr > hw.getDeadline())
                        nrDelay++;
                    }
                }
            if (nrDelay == 0)
                studenti.add(st);
        }

        return studenti;
    }

    public Collection<Student> studentiGrupa(Integer grupa){
        Collection<Student> result = new ArrayList<>();
        Consumer<Student> copyIf = x -> {if (x.getGroup().equals(grupa)) result.add(x);};
        studentRepo.findAll().forEach(copyIf);
        return result;
    }

    public Set<Student> studentiTema(Integer id_tema){
        Set<Student> result = new HashSet<>();
        Consumer<Grade> copyIf = x -> {if (x.getID_Tema().equals(id_tema)){
            try {
                result.add(studentRepo.find(x.getID_Student()));
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
        }};
        gradeRepo.findAll().forEach(copyIf);
        return result;
    }

    public Set<Student> studentiTemaProf(Integer id_tema, String prof){
        Set<Student> result = new HashSet<>();
        Consumer<Grade> copyIf = x -> {if (x.getID_Tema().equals(id_tema) && x.getProfesor().equals(prof)){
            try {
                result.add(studentRepo.find(x.getID_Student()));
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
        }};
        gradeRepo.findAll().forEach(copyIf);
        return result;
    }

   public Set<Grade> noteTemaSaptamana(Integer id_tema, Integer week){
        Set<Grade> result = new HashSet<>();
        Consumer<Grade> copyIf = x -> {
            LocalDate data = x.getData();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate start = LocalDate.of(2019,9,30);
            long weeks = ChronoUnit.WEEKS.between(start,data)+1;

            if(x.getID_Tema().equals(id_tema) && weeks == week)
                result.add(x);
        };
        gradeRepo.findAll().forEach(copyIf);
        return result;
    }

    public Collection<Grade> getAllGrades() {
        return gradeRepo.findAll();
    }
}
