package by.educ.ivan.education.model;

import java.util.Date;

public class StudyAssignment {

    private int id;
    private String name;
    private AssignmentStatus reviewStatus;
    private Date creationDate;
    private Date dueDate;
    private int grade;
    private String description;
    private Date dateOfChange;
    private EducationalMaterial educationalMaterial;
    private User student;
    private User teacher;

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

    public AssignmentStatus getReviewStatus() {
        return reviewStatus;
    }

    public void setReviewStatus(AssignmentStatus reviewStatus) {
        this.reviewStatus = reviewStatus;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDateOfChange() {
        return dateOfChange;
    }

    public void setDateOfChange(Date dateOfChange) {
        this.dateOfChange = dateOfChange;
    }

    public EducationalMaterial getEducationalMaterial() {
        return educationalMaterial;
    }

    public void setEducationalMaterial(EducationalMaterial educationalMaterial) {
        this.educationalMaterial = educationalMaterial;
    }

    public User getStudent() {
        return student;
    }

    public void setStudent(User student) {
        this.student = student;
    }

    public User getTeacher() {
        return teacher;
    }

    public void setTeacher(User teacher) {
        this.teacher = teacher;
    }
}
