package by.educ.ivan.education.model;

import java.util.Date;
import java.util.Objects;

public class AdminOperation {

    private int id;
    private AdminOperationType type;
    private Date date;
    private String reason;
    private User admin;
    private User user;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public AdminOperationType getType() {
        return type;
    }

    public void setType(AdminOperationType type) {
        this.type = type;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public User getAdmin() {
        return admin;
    }

    public void setAdmin(User admin) {
        this.admin = admin;
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
        AdminOperation that = (AdminOperation) o;
        return id == that.id && type == that.type && Objects.equals(date, that.date) && Objects.equals(reason, that.reason)
                && Objects.equals(admin, that.admin) && Objects.equals(user, that.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type, date, reason, admin, user);
    }
}
