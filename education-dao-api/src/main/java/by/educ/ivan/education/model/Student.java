package by.educ.ivan.education.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "students")
@NamedQuery(name = "Student.findAll", query = "select s from Student s")
public class Student extends User {

    @Column(name = "group")
    private int group;

    public int getGroup() {
        return group;
    }

    public void setGroup(int group) {
        this.group = group;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Student)) return false;
        if (!super.equals(o)) return false;
        Student student = (Student) o;
        return getGroup() == student.getGroup();
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getGroup());
    }
}
