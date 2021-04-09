package by.educ.ivan.education.model;

import java.util.Objects;

public class Professor {
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Professor)) return false;
        Professor professor = (Professor) o;
        return getUser().equals(professor.getUser());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUser());
    }
}
