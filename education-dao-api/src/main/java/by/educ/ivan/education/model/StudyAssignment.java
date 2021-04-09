package by.educ.ivan.education.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "study_assignments")
@NamedQuery(name = "StudyAssignment.findAll", query = "select s from StudyAssignment s")
public class StudyAssignment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "review_status")
    @Enumerated(EnumType.STRING)
    private AssignmentStatus reviewStatus;

    @Column(name = "creation_date")
    private LocalDateTime creationDate;

    @Column(name = "due_date")
    private LocalDateTime dueDate;

    @Column(name = "grade")
    private int grade;

    @Column(name = "description")
    private String description;

    @Column(name = "date_of_change")
    private LocalDateTime dateOfChange;

    @ManyToOne(optional = false)
    @JoinColumn(name = "educational_material", referencedColumnName = "id")
    private EducationalMaterial educationalMaterial;

    @ManyToOne(optional = false)
    @JoinColumn(name = "student", referencedColumnName = "id")
    private Student student;

    @ManyToOne(optional = false)
    @JoinColumn(name = "teacher", referencedColumnName = "id")
    private User teacher;

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

    public AssignmentStatus getReviewStatus() {
        return reviewStatus;
    }

    public void setReviewStatus(AssignmentStatus reviewStatus) {
        this.reviewStatus = reviewStatus;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDateTime dueDate) {
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

    public LocalDateTime getDateOfChange() {
        return dateOfChange;
    }

    public void setDateOfChange(LocalDateTime dateOfChange) {
        this.dateOfChange = dateOfChange;
    }

    public EducationalMaterial getEducationalMaterial() {
        return educationalMaterial;
    }

    public void setEducationalMaterial(EducationalMaterial educationalMaterial) {
        this.educationalMaterial = educationalMaterial;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public User getTeacher() {
        return teacher;
    }

    public void setTeacher(User teacher) {
        this.teacher = teacher;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StudyAssignment)) return false;
        StudyAssignment that = (StudyAssignment) o;
        return getId().equals(that.getId()) && getGrade() == that.getGrade() && getName().equals(that.getName())
                && getReviewStatus() == that.getReviewStatus() && getCreationDate().equals(that.getCreationDate())
                && getDueDate().equals(that.getDueDate()) && Objects.equals(getDescription(), that.getDescription())
                && Objects.equals(getDateOfChange(), that.getDateOfChange())
                && getEducationalMaterial().equals(that.getEducationalMaterial())
                && getStudent().equals(that.getStudent()) && getTeacher().equals(that.getTeacher());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getReviewStatus(), getCreationDate(), getDueDate(), getGrade(), getDescription(),
                getDateOfChange(), getEducationalMaterial(), getStudent(), getTeacher());
    }
}
