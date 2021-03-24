package by.educ.ivan.education.model;

import java.util.Objects;

public class Student {

    private int group;
    private User user;

    public int getGroup() {
        return group;
    }

    public void setGroup(int group) {
        this.group = group;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return group == student.group && Objects.equals(user, student.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(group, user);
    }
}
