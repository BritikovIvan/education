package by.educ.ivan.education.model;

import java.util.Date;

public class EducationalMaterial {

    private int id;
    private String name;
    private MaterialStatus reviewStatus;
    private Date creationDate;
    private MaterialType type;
    private Date reviewFinishDate;
    private String description;
    private AcademicDiscipline academicDiscipline;
    private User author;
    private User reviewer;

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

    public MaterialStatus getReviewStatus() {
        return reviewStatus;
    }

    public void setReviewStatus(MaterialStatus reviewStatus) {
        this.reviewStatus = reviewStatus;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public MaterialType getType() {
        return type;
    }

    public void setType(MaterialType type) {
        this.type = type;
    }

    public Date getReviewFinishDate() {
        return reviewFinishDate;
    }

    public void setReviewFinishDate(Date reviewFinishDate) {
        this.reviewFinishDate = reviewFinishDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public AcademicDiscipline getAcademicDiscipline() {
        return academicDiscipline;
    }

    public void setAcademicDiscipline(AcademicDiscipline academicDiscipline) {
        this.academicDiscipline = academicDiscipline;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public User getReviewer() {
        return reviewer;
    }

    public void setReviewer(User reviewer) {
        this.reviewer = reviewer;
    }
}
