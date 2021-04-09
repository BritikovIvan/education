package by.educ.ivan.education.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "academic_disciplines")
@NamedQuery(name = "AcademicDiscipline.findAll", query = "select a from AcademicDiscipline a")
public class AcademicDiscipline {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", unique = true)
    private String name;

    @Column(name = "abbreviation", unique = true)
    private String abbreviation;

    @Column(name = "description")
    private String description;

    @ManyToOne(optional = false)
    @JoinColumn(name = "author", referencedColumnName = "id")
    private User author;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AcademicDiscipline)) return false;
        AcademicDiscipline that = (AcademicDiscipline) o;
        return getId().equals(that.getId()) && getName().equals(that.getName()) && getAbbreviation().equals(that.getAbbreviation())
                && Objects.equals(getDescription(), that.getDescription()) && getAuthor().equals(that.getAuthor());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getAbbreviation(), getDescription(), getAuthor());
    }
}
