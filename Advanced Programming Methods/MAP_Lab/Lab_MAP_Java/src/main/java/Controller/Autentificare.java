package Controller;

public class Autentificare {
    public static void sendMail(String email, String header, String content){
        Thread t = new Thread(new MailSender(email, header, content));
        t.start();
    }


    public static String getPass() {
        return "";
    }
}

