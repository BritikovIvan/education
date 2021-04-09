package by.educ.ivan.education.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "educational_materials")
@NamedQuery(name = "EducationalMaterial.findAll", query = "select e from EducationalMaterial e")
public class EducationalMaterial {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "review_status")
    @Enumerated(EnumType.STRING)
    private MaterialStatus reviewStatus;

    @Column(name = "creation_date")
    private LocalDateTime creationDate;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private MaterialType type;

    @Column(name = "review_finish_date")
    private LocalDateTime reviewFinishDate;

    @Column(name = "description")
    private String description;

    @ManyToOne(optional = false)
    @JoinColumn(name = "academic_discipline", referencedColumnName = "id")
    private AcademicDiscipline academicDiscipline;

    @ManyToOne(optional = false)
    @JoinColumn(name = "author", referencedColumnName = "id")
    private User author;

    @ManyToOne(optional = false)
    @JoinColumn(name = "reviewer", referencedColumnName = "id")
    private User reviewer;

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

    public MaterialStatus getReviewStatus() {
        return reviewStatus;
    }

    public void setReviewStatus(MaterialStatus reviewStatus) {
        this.reviewStatus = reviewStatus;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public MaterialType getType() {
        return type;
    }

    public void setType(MaterialType type) {
        this.type = type;
    }

    public LocalDateTime getReviewFinishDate() {
        return reviewFinishDate;
    }

    public void setReviewFinishDate(LocalDateTime reviewFinishDate) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EducationalMaterial)) return false;
        EducationalMaterial that = (EducationalMaterial) o;
        return getId().equals(that.getId()) && getName().equals(that.getName()) && getReviewStatus() == that.getReviewStatus()
                && getCreationDate().equals(that.getCreationDate()) && getType() == that.getType()
                && Objects.equals(getReviewFinishDate(), that.getReviewFinishDate())
                && Objects.equals(getDescription(), that.getDescription())
                && getAcademicDiscipline().equals(that.getAcademicDiscipline())
                && getAuthor().equals(that.getAuthor()) && getReviewer().equals(that.getReviewer());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getReviewStatus(), getCreationDate(), getType(), getReviewFinishDate(),
                getDescription(), getAcademicDiscipline(), getAuthor(), getReviewer());
    }
}
