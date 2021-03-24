package by.educ.ivan.education.model;

import java.util.Objects;

public class AcademicDiscipline {

    private int id;
    private String name;
    private String abbreviation;
    private String description;
    private User author;

    public int getId() {
        return id;
    }

    public void setId(int id) {
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
        if (o == null || getClass() != o.getClass()) return false;
        AcademicDiscipline that = (AcademicDiscipline) o;
        return id == that.id && Objects.equals(name, that.name) && Objects.equals(abbreviation, that.abbreviation)
                && Objects.equals(description, that.description) && Objects.equals(author, that.author);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, abbreviation, description, author);
    }
}
