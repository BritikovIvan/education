package by.educ.ivan.education.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "admin_operations")
@NamedQuery(name = "AdminOperation.findAll", query = "select a from AdminOperation a")
public class AdminOperation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "operation")
    @Enumerated(EnumType.STRING)
    private AdminOperationType type;

    @Column(name = "date")
    private LocalDateTime date;

    @Column(name = "reason")
    private String reason;

    @ManyToOne(optional = false)
    @JoinColumn(name = "admin", referencedColumnName = "id")
    private User admin;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user", referencedColumnName = "id")
    private User user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AdminOperationType getType() {
        return type;
    }

    public void setType(AdminOperationType type) {
        this.type = type;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
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
        if (!(o instanceof AdminOperation)) return false;
        AdminOperation that = (AdminOperation) o;
        return getId().equals(that.getId()) && getType() == that.getType() && getDate().equals(that.getDate())
                && Objects.equals(getReason(), that.getReason()) && getAdmin().equals(that.getAdmin())
                && getUser().equals(that.getUser());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getType(), getDate(), getReason(), getAdmin(), getUser());
    }
}
