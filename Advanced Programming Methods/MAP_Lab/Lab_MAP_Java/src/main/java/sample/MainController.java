package sample;
import Controller.GradeController;

import Domain.Grade;
import Domain.Homework;
import Domain.Student;
import Domain.Validator.IllegalArgumentException;
import Domain.Validator.ValidatorException;
import Utils.ApplicationContext;
import Utils.Observable;
import Utils.Observer;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Pair;
import jdk.vm.ci.code.site.Call;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1CFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import javax.print.Doc;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class MainController implements Observer{

    @FXML public Pagination pagination;
    @FXML public Pagination paginationStud;
    @FXML public Pagination paginationTeme;

    HashMap<Integer, Float> medii;
    HashMap<Integer, Float> promovati;

    @FXML public CheckBox scutire;
    @FXML public CheckBox delay;
    @FXML public BorderPane container;
    @FXML public Label valid;
    @FXML public Button raport3;
    @FXML public Button raport2;
    @FXML public Button raport1;
    @FXML TextField nrDelay;
    /// ------------ Note --------------------------
    ObservableList<Grade> modelgr = FXCollections.observableArrayList();

    @FXML public Button addNota;

    @FXML public TextField cautaDupaNume;
    @FXML public TextField txtNota;
    @FXML public TextField txtProf;
    @FXML public TextArea feedback;
    @FXML public ComboBox<String> tema_box;

    public TableView tabel_gr;

    public TableColumn idstud;
    public TableColumn idtema;
    public TableColumn nota;
    public TableColumn data;
    public TableColumn prof;


    /// ------ Tema ------------------
    @FXML TableView<Homework> tabel_h;
    @FXML TableView<Student> tabel_s;

    @FXML public Button delTema;
    @FXML public Button upTema;
    @FXML public Button addTema;
    @FXML public TableColumn idTema;
    @FXML public TableColumn titlu;
    @FXML public TableColumn start;
    @FXML public TableColumn deadline;
    ObservableList<Homework> modelh = FXCollections.observableArrayList();

    public TextField txtDeadline;
    public Label lblDeadline;

    public TextField txtTitlu;
    public Label lblTitlu;
    public TextField txtIDTema;
    public Label lblIDTema;

    public Label mostDiff;

    @FXML Button addT;
    @FXML Button delT;
    @FXML Button upT;

    /// -------------------------------------
    GradeController service;

    /// --------- Student -----------------------------
    ObservableList<Student> models = FXCollections.observableArrayList();

    @FXML TableColumn<Student, Integer> id;
    @FXML TableColumn<Student, Integer> group;
    @FXML TableColumn<Student, String> name;
    @FXML TableColumn<Student, String> email;

    @FXML Button add;
    @FXML Button del;
    @FXML Button up;

    @FXML TextField txtNume;
    @FXML TextField txtID;
    @FXML TextField txtEmail;
    @FXML TextField txtGr;

    @FXML Label lblNume;
    @FXML Label lblEmail;
    @FXML Label lblGr;
    @FXML Label lblID;

    @FXML Label currentWeek;
    ///-------------------------------------

    int itemsPerPage = 12;

    private Node createPage(int pageIdx){
        int lastIdx = 0;
        int displace = modelgr.size() % itemsPerPage;
        if (displace > 0){
            lastIdx = modelgr.size() / itemsPerPage;
        }
        else{
            lastIdx = modelgr.size()/ itemsPerPage - 1;
        }
        int page = pageIdx;
        Collections.sort(models, (x,y)->x.getID()<y.getID()?0:1);
        Collections.sort(modelgr, (x,y)-> x.getID_Student() < y.getID_Student() ? -1 : x.getID_Student() == y.getID_Student()?0:1 );
        VBox paginationContent = new VBox(5);

        for (int i=page; i<page + 1; i++) {
            TableView<Grade> tabel_gr = new TableView<>();
            idstud = new TableColumn("ID_stud");
            idtema = new TableColumn("ID_tema");
            data = new TableColumn("Data");
            nota = new TableColumn("Nota");
            prof = new TableColumn("Prof");

            idstud.setCellValueFactory(new PropertyValueFactory<Grade, Integer>("iD_Student"));
            idtema.setCellValueFactory(new PropertyValueFactory<Grade, Integer>("iD_Tema"));
            data.setCellValueFactory(new PropertyValueFactory<Grade, String>("data"));
            nota.setCellValueFactory(new PropertyValueFactory<Grade, Float>("nota"));
            prof.setCellValueFactory(new PropertyValueFactory<Grade, String>("profesor"));

            tabel_gr.getColumns().addAll(idstud, idtema, nota, prof, data);

            if (lastIdx == pageIdx)
                tabel_gr.setItems(FXCollections.observableList(modelgr.subList(
                        pageIdx*itemsPerPage, pageIdx*itemsPerPage + displace
                )));
            else
                tabel_gr.setItems(FXCollections.observableList(modelgr.subList(
                        pageIdx * itemsPerPage , pageIdx * itemsPerPage + itemsPerPage
                )));
            paginationContent.getChildren().add(tabel_gr);
        }
        return paginationContent;
    }

    @FXML
    public void initialize(){
        currentWeek.setText("Saptamana Curenta : " + Homework.getCurrentWeek());
        feedback.setText("E ok / Atentie la cod duplicat / Nota 1 pt intarzieri mai mari de 2 saptamani / Ne vedem in restanta :)");

        paginationStud.setStyle("-fx-border-color: indigo");
        paginationStud.setPageFactory(new Callback<Integer, Node>() {
            @Override
            public Node call(Integer pageIdx) {
                if (pageIdx > models.size()/itemsPerPage+1)
                    return null;
                return createPageStud(pageIdx);
            }
        });

        paginationTeme.setPageFactory(new Callback<Integer, Node>() {
            @Override
            public Node call(Integer pageIdx) {
                if (pageIdx > modelh.size()/itemsPerPage+1)
                    return null;
                return createPageTeme(pageIdx);
            }
        });

        pagination.setStyle("-fx-border-color:green;");
        pagination.setPageFactory(new Callback<Integer, Node>() {
            @Override
            public Node call(Integer pageIdx) {
                if (pageIdx > modelgr.size()/itemsPerPage+1)
                    return null;
                return createPage(pageIdx);
            }
        });
    }

    private Node createPageTeme(Integer pageIdx) {
        int lastIdx = 0;
        int displace = modelh.size() % itemsPerPage;
        if (displace > 0){
            lastIdx = modelh.size() / itemsPerPage;
        }
        else{
            lastIdx = modelh.size()/ itemsPerPage - 1;
        }
        VBox contentTeme = new VBox(5);

        tabel_h = new TableView<>();
        idTema = new TableColumn("Id Tema");
        titlu = new TableColumn("Titlu");
        start = new TableColumn("Start");
        deadline = new TableColumn("Deadline");

        idTema.setCellValueFactory(new PropertyValueFactory<Homework,Integer>("iD"));
        titlu.setCellValueFactory(new PropertyValueFactory<Homework,Integer>("title"));
        start.setCellValueFactory(new PropertyValueFactory<Homework,Integer>("weekRecv"));
        deadline.setCellValueFactory(new PropertyValueFactory<Homework,Integer>("deadline"));
        tabel_h.getColumns().addAll(idTema, titlu, start, deadline);
        if (lastIdx == pageIdx)
            tabel_h.setItems(FXCollections.observableArrayList(modelh.subList(
                    pageIdx*itemsPerPage, pageIdx*itemsPerPage + displace
            )));
        else
            tabel_h.setItems(FXCollections.observableArrayList(modelh.subList(
                    pageIdx * itemsPerPage , pageIdx * itemsPerPage + itemsPerPage
            )));
        contentTeme.getChildren().add(tabel_h);
        return contentTeme;
    }

    private Node createPageStud(Integer pageIdx) {
        int lastIdx = 0;
        int displace = models.size() % itemsPerPage;
        if (displace > 0){
            lastIdx = models.size() / itemsPerPage;
        }
        else{
            lastIdx = models.size()/ itemsPerPage - 1;
        }
        int page = pageIdx;
        VBox content = new VBox(5);
        Collections.sort(models, (x,y)->x.getID()<y.getID()?0:1);
            tabel_s = new TableView<>();
            id.setCellValueFactory(new PropertyValueFactory<Student, Integer>("iD"));
            group.setCellValueFactory(new PropertyValueFactory<Student, Integer>("group"));
            name.setCellValueFactory(new PropertyValueFactory<Student, String>("name"));
            email.setCellValueFactory(new PropertyValueFactory<Student, String>("email"));
            tabel_s.getColumns().addAll(id, group, name, email);
        if (lastIdx == pageIdx)
            tabel_s.setItems(FXCollections.observableList(models.subList(
                    pageIdx*itemsPerPage, pageIdx*itemsPerPage + displace
            )));
        else
            tabel_s.setItems(FXCollections.observableList(models.subList(
                    pageIdx * itemsPerPage , pageIdx * itemsPerPage + itemsPerPage
            )));

            content.getChildren().add(tabel_s);

        return content;
    }

    public void handleAddMessage(ActionEvent actionEvent) {
        try{
            Integer ID = Integer.parseInt(txtID.getText());
            Integer grupa = Integer.parseInt(txtGr.getText());
            String nume = txtNume.getText();
            String mail = txtEmail.getText();
            Student st = new Student(ID, grupa, nume, mail);
            Student result = service.addStudent(st);
            if (result == null) {
                MessageAlert.showMessage(null, Alert.AlertType.INFORMATION, "", "Student adaugat cu succes!! ^^");
            }
            else{
                MessageAlert.showMessage(null, Alert.AlertType.INFORMATION, "", "ID deja existent ");
            }
        }
        catch (Exception e){
            e.printStackTrace();
            MessageAlert.showMessage(null, Alert.AlertType.ERROR, "Eroare :(", e.getMessage());
        }
    }

    public void handleDeleteMessage(ActionEvent actionEvent) throws IllegalArgumentException {
        Student target = tabel_s.getSelectionModel().getSelectedItem();
        Integer identity = 0;
        try {
            identity = Integer.parseInt(txtID.getText());
        }
        catch(Exception e){
            MessageAlert.showErrorMessage(null, "Introduceti un numar");
        }
        if (target!=null){
            service.deleteStudent(target.getID());
            MessageAlert.showMessage(null, Alert.AlertType.CONFIRMATION, "","S-a sters studentul ^^");
        }
        else if (identity > 0){
            try {
                Student st = service.deleteStudent(identity);
                MessageAlert.showMessage(null, Alert.AlertType.INFORMATION, "", "S-a sters studentul " + st.getName());
            }catch (Exception e){
                MessageAlert.showErrorMessage(null, e.getMessage());
            }
        }
        else MessageAlert.showErrorMessage(null, "Nu ai selectat student :/ ");
    }


    public void handleUpdateMessage(ActionEvent actionEvent) {
        Student target = tabel_s.getSelectionModel().getSelectedItem();

        if (target != null){
            String numeNou = txtNume.getText();
            String mailNou= txtEmail.getText();
            Integer grupaNoua = Integer.parseInt(txtGr.getText());
            Student nou = new Student(target.getID(), grupaNoua, numeNou, mailNou);
            Student result = service.updateStudent(nou);
            if (result!=null){
                MessageAlert.showMessage(null, Alert.AlertType.CONFIRMATION, "","S-a modificat studentul! ^^");
            }
            else
                MessageAlert.showMessage(null, Alert.AlertType.CONFIRMATION,"","Student adaugat");
        }
        else
            MessageAlert.showErrorMessage(null, "Nu ai selectat student :/ ");
    }

    public void setService(GradeController service){
        this.service = service;
        service.addObserver(this);
        initModel();
        initGUI();
    }

    private void initModel() {
        Iterable<Student> studenti = service.getAllStudents();
        List<Student> listaStud = StreamSupport.stream(studenti.spliterator(),false).collect(Collectors.toList());
        models.setAll(listaStud);

        Iterable<Homework> teme = service.getAllHomework();
        List<Homework> listaTeme = StreamSupport.stream(teme.spliterator(), false).collect(Collectors.toList());
        modelh.setAll(listaTeme);

        Iterable<Grade> note = service.getAllGrades();
        List<Grade> listaNote = StreamSupport.stream(note.spliterator(), false).collect(Collectors.toList());
        modelgr.setAll(listaNote);

    }

    private void initGUI(){
        paginationTeme.setPageCount(service.getAllHomework().size()/itemsPerPage+1);
        paginationStud.setPageCount(service.getAllStudents().size()/itemsPerPage+1);
        pagination.setPageCount(service.getAllGrades().size()/itemsPerPage+1);
        try {
            medii = service.mediaLabStud();
            promovati = service.studentiPromovati();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        service.getAllHomework().forEach(x->tema_box.getItems().add(x.getTitle()));
        String[] tema_curenta = new String[2];
        service.getAllHomework().forEach(x -> {if (x.getDeadline().equals(Homework.getCurrentWeek()))
            tema_curenta[0] = x.getTitle();
        });

        String t = tema_curenta[0];
        tema_box.getSelectionModel().select(t);
        List<String> options = new ArrayList<>();
        service.getAllStudents().stream().forEach(x -> options.add(x.getName()));

        tema_box.setOnAction(event->{
            String target = tema_box.getSelectionModel().getSelectedItem();
            Integer[] d = new Integer[2];
            Integer penalty = 0;
            service.getAllHomework().forEach(x -> {if (x.getTitle().equals(target)) d[0] = x.getDeadline();});
            if (d[0] < Homework.getCurrentWeek()){
                penalty = Homework.getCurrentWeek() - d[0];
                feedback.setText("Depunctat cu " + penalty + "puncte din cauza intarzierilor");}
        });

        txtNota.textProperty().addListener(event ->
            {if (!txtNota.getText().equals("") &&
                    txtNota.getText().matches("^[0-9|.]]+$"))
                valid.setText("Introduceti un numar!!");
            else
                valid.setText("");
            });

        tabel_s.setOnMouseClicked((event -> {
            Student st = tabel_s.getSelectionModel().getSelectedItem();
            txtNume.setText(st.getName());
            cautaDupaNume.setText(st.getName());
            txtGr.setText(st.getGroup().toString());
            txtID.setText(st.getID().toString());
            txtEmail.setText(st.getEmail());
        }));

        tabel_h.setOnMouseClicked(event -> {
            Homework hw = tabel_h.getSelectionModel().getSelectedItem();
            txtIDTema.setText(hw.getID().toString());
            txtDeadline.setText(hw.getDeadline().toString());
            txtTitlu.setText(hw.getTitle());
        });
    }

    @Override
    public void update() {
        initModel();
        pagination.setPageCount(15);
        paginationTeme.setPageCount(15);
        paginationStud.setPageCount(15);
        paginationTeme.setPageCount(service.getAllHomework().size()/itemsPerPage+1);
        paginationStud.setPageCount(service.getAllStudents().size()/itemsPerPage+1);
        pagination.setPageCount(service.getAllGrades().size()/itemsPerPage+1);
    }

    public void handleUpdateTemaMessage(ActionEvent actionEvent) {
        Homework target = tabel_h.getSelectionModel().getSelectedItem();
        try {
            Homework nou = service.ExtendDeadline(target);
            MessageAlert.showMessage(null, Alert.AlertType.INFORMATION, "S-a modificat deadline-ul temei: " + target.getTitle(), "Noul deadline este saptamana : " + nou.getDeadline());
        } catch (IllegalArgumentException | ValidatorException e) {
            e.printStackTrace();
            MessageAlert.showErrorMessage(null, "Nu se poate modifica deadline-ul temei");
        }
    }

    public void handleDeleteTemaMessage(ActionEvent actionEvent) throws IllegalArgumentException {
        Homework target = tabel_h.getSelectionModel().getSelectedItem();
        if (target!=null){
            Homework hw = service.deleteHomework(target.getID());
   //         modelh.remove(target);
            if (hw!=null)
                MessageAlert.showMessage(null, Alert.AlertType.INFORMATION,"","Tema stearsa!");
        }
        else
            MessageAlert.showErrorMessage(null,"Nu ati selectat tema");
    }



    public void handleAddTemaMessage(ActionEvent actionEvent) {
        try{
            Integer ID_tema = Integer.parseInt(txtIDTema.getText());
            Integer start = Homework.getCurrentWeek();
            Integer end = Integer.parseInt(txtDeadline.getText());
            String titlu = txtTitlu.getText();
            Homework hw = new Homework(ID_tema,titlu, start, end);
            service.addHomework(hw);
     //       modelh.add(hw);
            MessageAlert.showMessage(null, Alert.AlertType.INFORMATION, "", "Tema adaugata! Spor la lucru ^^");
        }catch(Exception e){
            e.printStackTrace();
            MessageAlert.showErrorMessage(null, "Ceva n-o mers bine :/");
        }
    }

    public void handleAddNotaMessage(ActionEvent actionEvent) throws Exception {
        boolean s = scutire.isSelected();
        boolean d = delay.isSelected();
        Float nota = 1f;
        try {
            nota = Float.parseFloat(txtNota.getText());
        } catch (Exception e){
            MessageAlert.showErrorMessage(null,"introduceti un nr!!!");
            return;
        }
        String prof = txtProf.getText();
        String numeSt = cautaDupaNume.getText();
        if (cautaDupaNume.getText().equals(""))
            numeSt = tabel_s.getSelectionModel().getSelectedItem().getName();

        Integer id = 0;
        for (Student st:service.getAllStudents()
             ) {
            if (st.getName().equals(numeSt)){
                id = st.getID();
                break;
            }
        }
        Integer id_tema = 0;
        String tema = tema_box.getSelectionModel().getSelectedItem();
        for (Homework hw : service.getAllHomework()
             ) {
            if (hw.getTitle().equals(tema)){
                id_tema = hw.getID();
                break;
            }
        }
        String feed = feedback.getText();

        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
        alerta.setTitle("Confirmati adaugarea notei");
        alerta.setHeaderText("Student : " + numeSt + "  Nota :" + nota);
        alerta.setContentText("Student ID : " + id + " Tema: " + tema) ;

        Optional<ButtonType> butoane = alerta.showAndWait();
        Integer nr;
        if (nrDelay.getText().isEmpty() || s)
            nr = 0;
        else
            nr = Integer.parseInt(nrDelay.getText());

        if (butoane.get()==ButtonType.OK)
            service.addNota(s,nr,numeSt,id,id_tema,nota,prof,feed);
         else
            return;
    }

    public void temeLaTimp(ActionEvent actionEvent) {
        try {
            CategoryAxis xaxis = new CategoryAxis();
            xaxis.setLabel("T");

            NumberAxis yaxis = new NumberAxis();
            yaxis.setLabel("NR ");

            BarChart barchart = new BarChart(xaxis, yaxis);

            List<Student> lista = service.predatLaTimp();
            XYChart.Series dataseries = new XYChart.Series();

            dataseries.getData().add(new XYChart.Data("Predat tot la timp", lista.size()));
            dataseries.getData().add(new XYChart.Data("Nu au prdat tot la timp", service.getAllStudents().size()-lista.size()));
            barchart.getData().add(dataseries);

            VBox vbox = new VBox(barchart);
            Scene scena = new Scene(vbox);

            Stage stage = new Stage();
            stage.setScene(scena);
            stage.setHeight(600);
            stage.setWidth(600);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void studentiPromovati(ActionEvent actionEvent)  {
        try{

        CategoryAxis xaxis = new CategoryAxis();
        xaxis.setLabel("Stud");

        NumberAxis yaxis = new NumberAxis();
        yaxis.setLabel("Media ");

            ListView<String> lista = new ListView<>();
            ObservableSet<String> prom = FXCollections.<String>observableSet(String.valueOf(promovati.entrySet()));
            lista.setOrientation(Orientation.VERTICAL);
            lista.getItems().add("ID Student : Media ");
            lista.getItems().add("--------------------------------------------");

        BarChart barchart = new BarChart(xaxis, yaxis);
        XYChart.Series dataseries = new XYChart.Series();
        Iterator it = promovati.entrySet().iterator();
        while (it.hasNext()){
            Map.Entry elem = (Map.Entry)it.next();
            dataseries.getData().add(new XYChart.Data(elem.getKey().toString(), (Float)elem.getValue()));
            lista.getItems().add(elem.getKey().toString() + " : " + elem.getValue().toString() + " : PROMOVAT");
        }

        barchart.getData().add(dataseries);

            VBox vbox1 = new VBox(lista);
            Scene scena1 = new Scene(vbox1);
            Stage stage1 = new Stage();
            stage1.setTitle("Medii finale");
            stage1.setScene(scena1);
            stage1.show();

        VBox vbox = new VBox(barchart);
        Scene scena = new Scene(vbox);

        Stage stage = new Stage();
        stage.setScene(scena);
        stage.setHeight(600);
        stage.setWidth(600);
        stage.show();
        }
        catch(Exception e){e.printStackTrace();
        }

    }

    public void mediiFinale(ActionEvent actionEvent) throws IllegalArgumentException {
        Axis xaxis = new CategoryAxis();
        xaxis.setLabel("id Stud");
        Axis yaxis = new NumberAxis();
        yaxis.setLabel("Media finala");

        BarChart barchart = new BarChart(xaxis, yaxis);
        XYChart.Series dataseries = new XYChart.Series();

        ListView<String> lista = new ListView<>();
        ObservableSet<String> prom = FXCollections.<String>observableSet(String.valueOf(medii.entrySet()));
        lista.setOrientation(Orientation.VERTICAL);
        lista.getItems().add("ID Student : Media : Promovat/Restantier");
        lista.getItems().add("--------------------------------------------");

        Iterator it = medii.entrySet().iterator();
        while (it.hasNext()){
            Map.Entry elem = (Map.Entry)it.next();
            if ((Float)elem.getValue() >= 4f) {
                lista.getItems().add(elem.getKey().toString() + " : " + elem.getValue().toString() + " : PROMOVAT");
                dataseries.getData().add(new XYChart.Data(elem.getKey().toString() + "     PROMOVAT", (Float)elem.getValue()));
            }
            else{
                lista.getItems().add(elem.getKey().toString() + " : " + elem.getValue().toString() + " : RESTANTIER");
                dataseries.getData().add(new XYChart.Data(elem.getKey().toString() + "      RESTANTIER", (Float)elem.getValue()));
        }}

        VBox vbox1 = new VBox(lista);
        Scene scena1 = new Scene(vbox1);
        Stage stage1 = new Stage();
        stage1.setTitle("Medii finale");
        stage1.setScene(scena1);
        stage1.show();

        barchart.getData().add(dataseries);

        VBox vbox = new VBox(barchart);
        Scene scena = new Scene(vbox);
        Stage stage = new Stage();
        stage.setScene(scena);
        stage.setHeight(600);
        stage.setWidth(600);
        stage.show();
    }

    public void generarePDF() throws IllegalArgumentException {
        List<Pair<Homework, Float>> lista = service.ceaMaiGreaTema();
        List<Student> lista2 = service.predatLaTimp();
        try {
            String s = "rapoarte.pdf";
            PDDocument document = new PDDocument();
            PDPage p1= new PDPage();
            document.addPage(p1);
            PDPageContentStream stream = new PDPageContentStream(document, p1);
            stream.setFont(PDType1Font.TIMES_ROMAN,12);
            int i=20; Float minim = 100f; Homework HW = null;

            stream.beginText();
            stream.newLineAtOffset(25,700);
            stream.showText("Media notelor la fiecare tema");
            stream.endText();

            for(Pair<Homework, Float> pair:lista){
                if (pair.getValue() < minim && pair.getValue() > 0f){
                    minim = pair.getValue();
                    HW = pair.getKey();
                }
                stream.beginText();
                stream.newLineAtOffset(25,700-i);
                stream.showText(pair.getKey().getTitle() + "  " + pair.getValue().toString());
                stream.endText();

                i += 20;
            }

            if (HW!=null && minim > 0f) {
                stream.beginText();
                stream.newLineAtOffset(25+i,700-i);
                stream.showText("Cea mai dificila tema: " + HW.getTitle() + " Media :" + minim.toString());
                stream.endText();
            }
            stream.close();

            PDPage pag2 = new PDPage();
            document.addPage(pag2);
            stream = new PDPageContentStream(document, pag2);
            stream.setFont(PDType1Font.TIMES_ROMAN, 12);
            stream.beginText();
            stream.newLineAtOffset(25,700);
            stream.showText("Medii finale la laborator");
            stream.endText();

            stream.beginText();
            stream.newLineAtOffset(25,700-20);
            stream.showText("ID Student              Medie              Promovat ");
            stream.endText();

            Iterator it = medii.entrySet().iterator();
            i=20;
            while (it.hasNext()){
                stream.beginText();
                stream.newLineAtOffset(25,680-i);
                Map.Entry elem = (Map.Entry) it.next();
                if ((Float)elem.getValue() >= 4f)
                    stream.showText(elem.getKey().toString() + "           ....               " + elem.getValue()
                            + "        ...         " + "PROMOVAT");
                else
                    stream.showText(elem.getKey().toString()+ "             ...              " + elem.getValue()
                            + "         ...       " + "RESTANTIER");
                stream.endText();
                i += 20;
            }

            stream.close();

            PDPage pag3 = new PDPage();
            document.addPage(pag3);
            stream = new PDPageContentStream(document, pag3);
            stream.setFont(PDType1Font.TIMES_ROMAN, 12);
            stream.beginText();
            stream.newLineAtOffset(25,700);  i=20;
            stream.showText("Studenti care au predat toate temele la timp");
            stream.endText();

            for(Student st : lista2){
                stream.beginText();
                stream.newLineAtOffset(25,700-i);
                stream.showText("ID : " + st.getID() + "    Nume: " + st.getName() + "    Grupa: " + st.getGroup());
                stream.endText();
                i += 20;
            }

            stream.close();

            document.save(s);
            document.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }

        MessageAlert.showMessage(null, Alert.AlertType.INFORMATION, "Succes", "Rapoarte generate cu succes");
    }

    public void ceaMaiDificila(ActionEvent actionEvent) throws IOException, IllegalArgumentException {
        List<Pair<Homework, Float>> lista = service.ceaMaiGreaTema();
        Float minim = 100f; Homework HW = null;

        for(Pair<Homework, Float> pair:lista){
            if (pair.getValue() < minim && pair.getValue() > 0f){
                minim = pair.getValue();
                HW = pair.getKey();
            }
        }
        CategoryAxis xaxis = new CategoryAxis();
        xaxis.setLabel("Tema");

        NumberAxis yaxis = new NumberAxis();
        yaxis.setLabel("Media ");

        BarChart barchart = new BarChart(xaxis, yaxis);
        XYChart.Series dataseries = new XYChart.Series();

        for (Pair<Homework,Float> pereche: lista
             ) {
            dataseries.getData().add(new XYChart.Data( pereche.getKey().getTitle(), (Float) pereche.getValue()));
        }

        barchart.getData().add(dataseries);
        //generarePDF();

        VBox vbox = new VBox(barchart);
        Scene scena = new Scene(vbox);
        Stage stage = new Stage();
        stage.setScene(scena);
        stage.setHeight(600);
        stage.setWidth(600);
        stage.show();

        MessageAlert.showMessage(null, Alert.AlertType.INFORMATION, "Cea mai dificila tema",
                "Tema : " + HW.getTitle() + ", Media :" + minim);
    }
}
