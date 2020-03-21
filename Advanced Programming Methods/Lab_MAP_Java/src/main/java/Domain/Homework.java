package Domain;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Homework implements Entity<Integer> {
    private Integer ID;
    private int weekRecv;
    private int deadline;
    private String title;


    public Homework(Integer ID_, String title_, int weekRecv_, int deadline_){
        this.ID = ID_;
        this.title = title_;
        this.deadline = deadline_;
        this.weekRecv = weekRecv_;
    }

    public Integer getID() {
        return ID;
    }

    public Integer getDeadline() {
        return deadline;
    }

    public int getWeekRecv() {
        return weekRecv;
    }

    public String getTitle() {
        return title;
    }

    public void setDeadline(int deadline) {
        this.deadline = deadline;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setWeekRecv(int weekRecv) {
        this.weekRecv = weekRecv;
    }

    @Override
    public void setID(Integer ID) {
        this.ID = ID;
    }

    public Homework extendDeadline(){
        if (getCurrentWeek() < this.deadline && this.deadline < 14)
            this.deadline++;
        return this;
    }

    public static int draft_getCurrentWeek(){
        StructuraAn sem1 = new StructuraAn(LocalDate.of(2019,9,30),
                LocalDate.of(2020,1,15),
                LocalDate.of(2019,12,21),
                LocalDate.of(2020,1,5));

        StructuraAn sem2 = new StructuraAn(LocalDate.of(2020,2,20),
                LocalDate.of(2020,6,15),
                LocalDate.of(2020,4,7),
                LocalDate.of(2020,4,20));

        LocalDate data = LocalDate.now();
        long current_week = 0;

        if (data.isBefore(sem2.getStart())){
            if (data.isBefore(sem1.getEndBreak())&& data.isAfter(sem1.getStartBreak()))
                current_week = 12;
            else
                current_week = ChronoUnit.WEEKS.between(sem1.getStart(),data) + 1;
        }
        return (int)current_week;
    }

    public static int getCurrentWeek(){
        SimpleDateFormat MyFormat = new SimpleDateFormat("dd MM yyyy");
        String start = "30 09 2019";
        String current = MyFormat.format(new Date());
        int days = 0;
        try{
            Date yearStart = MyFormat.parse(start);
            Date now = MyFormat.parse(current);
            long diff = now.getTime() - yearStart.getTime();
            days = Math.toIntExact(TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS));
        }
        catch(ParseException e){
            e.printStackTrace();
        }
        int weeks = 1 + days/7;
        return weeks;
    }

    @Override
    public String toString() {
        return "Homework{" +
                "ID=" + ID +
                ", weekRecv=" + weekRecv +
                ", deadline=" + deadline +
                ", title='" + title + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null){
            return false;
        }
        if(this==obj){
            return true;
        }
        if (obj.getClass()!=this.getClass()){
            return false;
        }
        Homework hw = (Homework)obj;
        return this.ID.equals(hw.getID());
    }
}
