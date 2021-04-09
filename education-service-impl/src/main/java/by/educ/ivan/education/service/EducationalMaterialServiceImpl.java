package by.educ.ivan.education.service;

import by.educ.ivan.education.dao.EducationalMaterialDAO;
import by.educ.ivan.education.exception.EducationalMaterialException;
import by.educ.ivan.education.model.*;

import java.time.LocalDateTime;
import java.util.Collection;

public class    EducationalMaterialServiceImpl implements EducationalMaterialService {

    private final SessionService sessionService;

    private final UserService userService;

    private final EducationalMaterialDAO materialDAO;

    public EducationalMaterialServiceImpl(SessionService sessionService, UserService userService, EducationalMaterialDAO materialDAO) {
        this.sessionService = sessionService;
        this.userService = userService;
        this.materialDAO = materialDAO;
    }

    @Override
    public Collection<EducationalMaterial> getAllProfessorEducationalMaterials(User professor) {
        if (!userService.isProfessor()) {
            throw new EducationalMaterialException("Wrong professor user role.");
        }

        return materialDAO.selectEducationalMaterialsByReviewer(professor);
    }

    @Override
    public Collection<EducationalMaterial> getAllTeacherEducationalMaterials(User teacher) {
        if (!userService.isTeacher()) {
            throw new EducationalMaterialException("Wrong teacher user role.");
        }

        return materialDAO.selectEducationalMaterialsByTeacher(teacher);
    }

    @Override
    public Collection<EducationalMaterial> getAllEducationalMaterialsByDiscipline(AcademicDiscipline discipline) {
        return materialDAO.selectEducationalMaterialsByDiscipline(discipline);
    }

    @Override
    public EducationalMaterial createEducationalMaterial(EducationalMaterial educationalMaterial) {
        if (!userService.isTeacher()) {
            throw new EducationalMaterialException("Wrong teacher user role.");
        }

        educationalMaterial.setReviewStatus(MaterialStatus.DRAFT);
        educationalMaterial.setCreationDate(LocalDateTime.now());
        educationalMaterial.setAuthor(sessionService.getCurrentUser());
        if (educationalMaterial.getAuthor() == null) { //
            educationalMaterial.setReviewer(educationalMaterial.getAcademicDiscipline().getAuthor());
        }
        educationalMaterial.setId(materialDAO.insertEducationalMaterial(educationalMaterial));
        return educationalMaterial;
    }

    @Override
    public EducationalMaterial editEducationalMaterial(int id, EducationalMaterial educationalMaterial) {
        EducationalMaterial material = materialDAO.findEducationalMaterial(String.valueOf(id));
        if (educationalMaterial.getName() != null) {
            material.setName(educationalMaterial.getName());
        }
        if (educationalMaterial.getType() != null) {
            material.setType(educationalMaterial.getType());
        }
        if (educationalMaterial.getDescription() != null) {
            material.setDescription(educationalMaterial.getDescription());
        }
        if (educationalMaterial.getAcademicDiscipline() != null) {
            material.setAcademicDiscipline(educationalMaterial.getAcademicDiscipline());
        }
        if (educationalMaterial.getAuthor() != null) {
            material.setAuthor(educationalMaterial.getAuthor());
        }
        if (educationalMaterial.getReviewer() != null) {
            material.setReviewer(educationalMaterial.getReviewer());
        }
        materialDAO.updateEducationalMaterial(material);
        return material;
    }

    @Override
    public EducationalMaterial makeReadyForReview(EducationalMaterial material) {
        EducationalMaterial educationalMaterial = materialDAO.findEducationalMaterial(String.valueOf(material.getId()));
        if (isReadyForReviewReached(educationalMaterial)) {
            if (isNotAuthor(educationalMaterial)) {
                throw new EducationalMaterialException("The user doesnt have access to this action.");
            }
            educationalMaterial.setReviewStatus(MaterialStatus.READY_FOR_REVIEW);
            materialDAO.updateEducationalMaterial(educationalMaterial);
            return educationalMaterial;
        } else {
            throw new EducationalMaterialException("Material with this status cannot be ready for review.");
        }
    }

    private boolean isReadyForReviewReached(EducationalMaterial material) {
        return material.getReviewStatus() == MaterialStatus.DRAFT || material.getReviewStatus() == MaterialStatus.BACK_TO_REWORK;
    }

    private boolean isNotAuthor(EducationalMaterial material) {
        return sessionService.getCurrentUser().equals(material.getAuthor());
    }

    @Override
    public EducationalMaterial reviewMaterial(EducationalMaterial material) {
        EducationalMaterial educationalMaterial = materialDAO.findEducationalMaterial(String.valueOf(material.getId()));
        if (educationalMaterial.getReviewStatus() != MaterialStatus.READY_FOR_REVIEW) {
            throw new EducationalMaterialException("Material with this status cannot be reviewed.");
        }
        if (isNotReviewer(educationalMaterial)) {
            throw new EducationalMaterialException("The user doesnt have access to this action.");
        }

        educationalMaterial.setReviewStatus(MaterialStatus.UNDER_REVIEW);
        materialDAO.updateEducationalMaterial(educationalMaterial);
        return educationalMaterial;
    }

    private boolean isNotReviewer(EducationalMaterial material) {
        return sessionService.getCurrentUser().equals(material.getReviewer());
    }

    @Override
    public EducationalMaterial backToReworkMaterial(EducationalMaterial material) {
        EducationalMaterial educationalMaterial = materialDAO.findEducationalMaterial(String.valueOf(material.getId()));
        if (educationalMaterial.getReviewStatus() != MaterialStatus.UNDER_REVIEW) {
            throw new EducationalMaterialException("Material with this status cannot be back to rework.");
        }
        if (isNotReviewer(educationalMaterial)) {
            throw new EducationalMaterialException("The user doesnt have access to this action.");
        }

        educationalMaterial.setReviewStatus(MaterialStatus.BACK_TO_REWORK);
        materialDAO.updateEducationalMaterial(educationalMaterial);
        return educationalMaterial;
    }

    @Override
    public EducationalMaterial cancelMaterial(EducationalMaterial material) {
        EducationalMaterial educationalMaterial = materialDAO.findEducationalMaterial(String.valueOf(material.getId()));
        if (mayBeCancelled(educationalMaterial)) {
            if (isNotReviewer(educationalMaterial)) {
                throw new EducationalMaterialException("The user doesnt have access to this action.");
            }
            educationalMaterial.setReviewStatus(MaterialStatus.CANCELLED);
            materialDAO.updateEducationalMaterial(educationalMaterial);
            return educationalMaterial;
        } else {
            throw new EducationalMaterialException("Material with this status cannot be cancelled.");
        }
    }

    private boolean mayBeCancelled(EducationalMaterial material) {
        return material.getReviewStatus() == MaterialStatus.UNDER_REVIEW || material.getReviewStatus() == MaterialStatus.READY_FOR_REVIEW
                || material.getReviewStatus() == MaterialStatus.DRAFT;
    }

    @Override
    public EducationalMaterial approveMaterial(EducationalMaterial material) {
        EducationalMaterial educationalMaterial = materialDAO.findEducationalMaterial(String.valueOf(material.getId()));
        if (educationalMaterial.getReviewStatus() != MaterialStatus.UNDER_REVIEW) {
            throw new EducationalMaterialException("Material with this status cannot be approved.");
        }
        if (isNotReviewer(educationalMaterial)) {
            throw new EducationalMaterialException("The user doesnt have access to this action.");
        }

        educationalMaterial.setReviewStatus(MaterialStatus.REVIEWED);
        educationalMaterial.setReviewFinishDate(LocalDateTime.now());
        materialDAO.updateEducationalMaterial(educationalMaterial);
        return educationalMaterial;
    }
}
