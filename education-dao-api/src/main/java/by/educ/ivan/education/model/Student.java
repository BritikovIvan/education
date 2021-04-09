package by.educ.ivan.education.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "students")
@NamedQuery(name = "Student.findAll", query = "select s from Student s")
public class Student extends User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "group")
    private int group;

    public int getGroup() {
        return group;
    }

    public void setGroup(int group) {
        this.group = group;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Student)) return false;
        if (!super.equals(o)) return false;
        Student student = (Student) o;
        return getId().equals(student.getId()) && getGroup() == student.getGroup();
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getId(), getGroup());
    }
}
