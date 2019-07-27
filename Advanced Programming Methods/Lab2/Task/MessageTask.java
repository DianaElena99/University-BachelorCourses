package Task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MessageTask extends Task{
        private String message;
        private String from;
        private String to;
        private LocalDateTime date;

        public MessageTask(String id, String descr, String message_, String from_, String to_, LocalDateTime date_){
            super(id, descr);
            this.date = date_;
            this.from = from_;
            this.to = to_;
            DateTimeFormatter FMT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getFrom(){
            return this.from;
        }

        public String getTo() {
            return to;
        }

        public LocalDateTime getDate() {
            return date;
        }

        public void setFrom(String from) {
            this.from = from;
        }

        public void setTo(String to) {
            this.to = to;
        }

        public void setDate(LocalDateTime date) {
            this.date = date;
        }

        public void execute(){
            System.out.println(this);
        }

        @Override
        public String toString() {
            return super.toString() + " | message : " + getMessage()
                    + " | to : " + getTo() + " | from : " + getFrom()
                    + " | date : " + getDate();
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (this.getClass() != obj.getClass())
                return false;
            MessageTask aux = (MessageTask)obj;
            return super.getTaskID().equals(aux.getTaskID());
        }
}

