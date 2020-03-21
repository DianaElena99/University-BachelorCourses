package UI;

import Controller.GradeController;
import Domain.Grade;
import Domain.Homework;
import Domain.Student;

import java.util.Collection;
import java.util.Scanner;

public class UI {
    Scanner scan = new Scanner(System.in);
    private GradeController gradeController;
    public UI(GradeController gradeController){
        this.gradeController = gradeController;
    }

    public void run() throws Exception {

        boolean x = true;
        while(x){
            System.out.println(" 1. Adauga Nota\n 2. Meniu Teme (Work in progress) \n 3. Meniu Studenti (Work in progress) \n 4.Filtrare grupa \n 5. Filtrare tema \n 6. Filtrare tema prof \n 7. Teme dintr-o saptamana\n");
            System.out.println(" > > > ");
            int cmd = scan.nextInt();
            if (cmd < 1 || cmd > 8)
                x = false;
            if (cmd == 1)
                ui_addGrade();
            if (cmd == 2){
                ui_homeworkMenu();
            }
            if (cmd == 3){
                ui_studentMenu();
            }
            if (cmd == 4)
                filtruGrupa();
            if (cmd == 5)
                filtruTema();
            if (cmd==6)
                filtruTemaProf();
            if (cmd == 7)
                filtruSaptamana();
        }
    }

    private void ui_studentMenu() throws Exception {
        while (true){
            System.out.println("1. Adauga Student \n2. Sterge student\n3.Modifica student\n 4.Afiseaza studenti");
            System.out.println("> > > ");
            int cmd = scan.nextInt();
            if (cmd < 1 || cmd > 4)
               break;
            if (cmd == 1){
                System.out.println("ID student : ");
                Integer id = scan.nextInt();

                System.out.println("Nume : ");
                String nume = scan.next();

                System.out.println("Grupa");
                Integer group = scan.nextInt();

                System.out.println("Email :");
                String email = scan.next();

                Student nou = new Student(id, group, nume, email);
                gradeController.addStudent(nou);
                System.out.println("Student adaugat");
            }

            if (cmd == 2){
                System.out.println("ID : ");
                Integer id = scan.nextInt();

                Student st = gradeController.deleteStudent(id);
            }
            if (cmd == 3){
                System.out.println("ID student : ");
                Integer id = scan.nextInt();

                System.out.println("Nume : ");
                String nume = scan.next();

                System.out.println("Grupa");
                Integer group = scan.nextInt();

                System.out.println("Email :");
                String email = scan.next();

                Student nou = new Student(id, group, nume, email);
                gradeController.updateStudent(nou);
            }
            if (cmd == 4)
                gradeController.getAllStudents().forEach(System.out::println);
        }
    }

    private void ui_homeworkMenu() throws Exception {
        while (true){
            System.out.println("1. Adauga tema \n2. Sterge tema\n3.Extinde deadline\n");
            System.out.println("> > > ");
            int cmd = scan.nextInt();
            if (cmd < 1 || cmd > 3)
                break;
            if (cmd == 1){
                System.out.println("ID tema : ");
                Integer id = scan.nextInt();
                System.out.println("Titlu : ");
                String nume = scan.next();
                scan.nextLine();
                System.out.println("Deadline : ");
                Integer dl = scan.nextInt();

                Homework hw = new Homework(id, nume, Homework.getCurrentWeek(), dl);
                gradeController.addHomework(hw);
                System.out.println("Tema adaugata");
            }

            if (cmd == 2){
                System.out.println("ID : ");
                Integer id = scan.nextInt();

                Homework hw = gradeController.deleteHomework(id);
            }
            if (cmd == 3){
                System.out.println("ID tema : ");
                Integer id = scan.nextInt();
                //gradeController.ExtendDeadline(id);
            }
        }
    }

    private void ui_addGrade() throws Exception {
        System.out.println("\nID student:");
        Integer idStud = scan.nextInt();

        System.out.println("ID tema: ");
        Integer idTema = scan.nextInt();

        System.out.println("Nota :");
        Float nota = scan.nextFloat();

        System.out.println("Cadru didactic");
        String prof = scan.next();
        scan.nextLine();
        System.out.println("Feedback");
        String feedbk = scan.nextLine();

        Homework found = null;
        Integer weekCurrent = Homework.getCurrentWeek();
        for (Homework hw : gradeController.getAllHomework()) {
            if (hw.getID().equals(idTema)) {
                found = hw;
                break;
            }
        }
        if (found == null)
            return;
        System.out.println("Deadline :" + found.getDeadline() + " | Current week : " + weekCurrent);
        boolean ScutireMotivata = false;
        boolean ProfDelay = false;
        if (found.getDeadline() < weekCurrent) {
            ScutireMotivata = false;
            ProfDelay = false;
            String input = "";
            System.out.println("Studentul are scutire motivata? Y/N    ");
            input = scan.next();
            if (input.equals("Y") || input.equals("y"))
                ScutireMotivata = true;

            System.out.println("A introdus profesorul notele cu intarziere> Y/N   ");
            input = scan.next();
            if (input.equals("Y") || input.equals("y"))
                ProfDelay = true;


        }
        gradeController.addGrade(ScutireMotivata, ProfDelay, idStud, idTema, nota, prof, feedbk);
    }

    public void filtruGrupa(){
        System.out.println("Grupa:");
        Integer grupa = scan.nextInt();
        Collection<Student> lista = gradeController.studentiGrupa(grupa);
        lista.forEach(System.out::println);
    }

    public void filtruTema(){
        System.out.println("Tema:");
        Integer tema = scan.nextInt();
        Collection<Student> lista = gradeController.studentiTema(tema);
        lista.forEach(System.out::println);
    }

    public void filtruTemaProf(){
        System.out.println("Tema:");
        Integer tema = scan.nextInt();
        scan.nextLine();

        System.out.println("Prof :");
        String prof = scan.nextLine();
        Collection<Student> lista = gradeController.studentiTemaProf(tema,prof);
        lista.forEach(System.out::println);
    }

    public void filtruSaptamana(){
        System.out.println("Tema:");
        Integer tema = scan.nextInt();
        scan.nextLine();

        System.out.println("Saptamana:");
        Integer week = scan.nextInt();
        scan.nextLine();
        Collection<Grade> lista = gradeController.noteTemaSaptamana(tema,week);
        lista.forEach(System.out::println);
    }
}
