package sample;

import Controller.GradeController;
import Domain.Grade;
import Domain.Homework;
import Domain.Student;
import Domain.Validator.GradeValidator;
import Domain.Validator.HomeworkValidator;
import Domain.Validator.StudentValidator;
import Domain.Validator.Validator;
import Repository.*;
import Utils.ApplicationContext;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;

public class Main extends Application {
    GradeController service;
    PostrgeSQLStudentRepository studentRepo;
    @Override
    public void start(Stage primaryStage) throws Exception{
        Validator<Student> vs = new StudentValidator();
        Validator<Grade> gv = new GradeValidator();
        Validator<Homework> hv = new HomeworkValidator();

        studentRepo = new PostrgeSQLStudentRepository(vs);
        PostgreSQLHomeworkRepository homeworkRepo = new PostgreSQLHomeworkRepository(hv);
        PostgreSQLGradeRepository gradeRepo = new PostgreSQLGradeRepository(gv);
        service = new GradeController(studentRepo, homeworkRepo, gradeRepo);

        init1(primaryStage);
    }

    private void init1(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/students.fxml"));
        TabPane layout = loader.load();

        MainController mainController = loader.getController();
        mainController.setService(service);

        Scene scene = new Scene(layout);
        primaryStage.setTitle("Student Management App");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
