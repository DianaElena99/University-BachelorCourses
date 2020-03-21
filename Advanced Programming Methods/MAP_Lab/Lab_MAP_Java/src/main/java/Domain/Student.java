package Domain;

public class Student implements Entity<Integer> {
    private Integer ID;
    private Integer group;
    private String name;
    private String email;

    public Student(Integer ID_, Integer group_, String name_, String email_){
        this.ID = ID_;
        this.group = group_;
        this.name = name_;
        this.email = email_;
    }

    public Integer getGroup() {
        return group;
    }

    public void setGroup(Integer group) {
        this.group = group;
    }

    public Integer getID() {
        return ID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void setID(Integer ID) {
        this.ID = ID;
    }

    @Override
    public String toString() {
        return "Student{" +
                "ID=" + ID +
                ", group=" + group +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (this == obj)
            return true;
        if (obj.getClass()!=this.getClass())
            return false;
        Student st;
        st = (Student)obj;
        return st.getID().equals(this.getID());

    }
}
