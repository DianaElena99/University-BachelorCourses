import Model.Artist;
import Model.Bilet;
import Model.ConcertDTO;
import Model.User;
import Repository.hibernate_repos.*;

public class TestHibernateRepos {

    public static void main(String[] args) {
        ArtistHibernateRepo ahr = new ArtistHibernateRepo();
        for (Artist a : ahr.getAll()
        ) {
            System.out.println(a);
        }

        /*
        ahr.save(new Artist("judas priest"));
        for (Artist a : ahr.getAll()
        ) {
            System.out.println(a);
        }

        ahr.delete(9);
        for (Artist a : ahr.getAll()
        ) {
            System.out.println(a);
        }

         BiletHibernateRepo bhr = new BiletHibernateRepo();
        bhr.save(new Bilet("gicu", 2,1));
        */

        UserHibernateRepo uhr = new UserHibernateRepo();
        System.out.println(uhr.checkCredentials(new User("admin", "admin")));
        System.out.println(uhr.checkCredentials(new User("puffy", "puffy")));
        uhr.getAll().forEach(System.out::println);

        ConcertHibernateRepo chr = new ConcertHibernateRepo();
        for (int i=1;i<6;i++){
            System.out.println(chr.findOne(i));
        }

        ConcertDTORepo dtos = new ConcertDTORepo();
        for (ConcertDTO c : dtos.findConcertsDetails()){
            System.out.println(c);
        }

        System.out.println(ahr.find("vancea sandu"));
        System.out.println(ahr.find("istvan"));
        System.out.println(ahr.findById(5));
        System.out.println(ahr.findById(4));
        System.out.println(ahr.findById(8));
    }

}
