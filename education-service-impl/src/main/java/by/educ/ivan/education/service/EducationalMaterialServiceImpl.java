package by.educ.ivan.education.service;

import by.educ.ivan.education.dao.EducationalMaterialDAO;
import by.educ.ivan.education.exception.EducationalMaterialException;
import by.educ.ivan.education.model.AcademicDiscipline;
import by.educ.ivan.education.model.EducationalMaterial;
import by.educ.ivan.education.model.MaterialStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collection;

@Service
@Transactional
public class EducationalMaterialServiceImpl implements EducationalMaterialService {

    private final SessionService sessionService;

    private final UserService userService;

    private final EducationalMaterialDAO materialDAO;

    private final AcademicDisciplineService disciplineService;

    @Autowired
    public EducationalMaterialServiceImpl(SessionService sessionService, UserService userService, EducationalMaterialDAO materialDAO, AcademicDisciplineService disciplineService) {
        this.sessionService = sessionService;
        this.userService = userService;
        this.materialDAO = materialDAO;
        this.disciplineService = disciplineService;
    }

    @Override
    public Collection<EducationalMaterial> getAllProfessorEducationalMaterials(Long professorId) {
//        if (!sessionService.isProfessor()) {
//            throw new EducationalMaterialException("Wrong professor user role.");
//        }

        return materialDAO.selectEducationalMaterialsByReviewer(userService.getUser(professorId));
    }

    @Override
    public Collection<EducationalMaterial> getAllTeacherEducationalMaterials(Long teacherId) {
//        if (!sessionService.isTeacher()) {
//            throw new EducationalMaterialException("Wrong teacher user role.");
//        }

        return materialDAO.selectEducationalMaterialsByTeacher(userService.getUser(teacherId));
    }

    @Override
    public Collection<EducationalMaterial> getAllEducationalMaterialsByDiscipline(AcademicDiscipline discipline) {
        return materialDAO.selectEducationalMaterialsByDiscipline(discipline);
    }

    @Override
    public EducationalMaterial createEducationalMaterial(EducationalMaterial educationalMaterial) {
//        if (!sessionService.isTeacher()) {
//            throw new EducationalMaterialException("Wrong teacher user role.");
//        }

        educationalMaterial.setReviewStatus(MaterialStatus.DRAFT);
        educationalMaterial.setCreationDate(LocalDateTime.now());
//        educationalMaterial.setAuthor(sessionService.getCurrentUser());
        educationalMaterial.setAuthor(userService.getUser(5L));
        educationalMaterial.setAcademicDiscipline(disciplineService.getAcademicDiscipline(educationalMaterial.getAcademicDiscipline().getId()));
        if (educationalMaterial.getReviewer().getId() == 0) {
            educationalMaterial.setReviewer(educationalMaterial.getAcademicDiscipline().getAuthor());
        } else {
            educationalMaterial.setReviewer(userService.getUser(educationalMaterial.getReviewer().getId()));
        }
        return materialDAO.insertEducationalMaterial(educationalMaterial);
    }

    @Override
    public EducationalMaterial editEducationalMaterial(EducationalMaterial educationalMaterial, Long id) {
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
            material.setAcademicDiscipline(disciplineService.getAcademicDiscipline(educationalMaterial.getAcademicDiscipline().getId()));
        }
        if (educationalMaterial.getAuthor() != null) {
            material.setAuthor(userService.getUser(educationalMaterial.getAuthor().getId()));
        }
        if (educationalMaterial.getReviewer() != null) {
            material.setReviewer(userService.getUser(educationalMaterial.getReviewer().getId()));
        }
        if (educationalMaterial.getReviewStatus() != null) {
            changeReviewStatus(educationalMaterial, id);
        }
        return materialDAO.updateEducationalMaterial(material);
    }

//    @Override
    private EducationalMaterial makeReadyForReview(EducationalMaterial material) {
        EducationalMaterial educationalMaterial = materialDAO.findEducationalMaterial(String.valueOf(material.getId()));
        if (isReadyForReviewReached(educationalMaterial)) {
//            if (isNotAuthor(educationalMaterial)) {
//                throw new EducationalMaterialException("The user doesnt have access to this action.");
//            }
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

//    @Override
    private EducationalMaterial reviewMaterial(EducationalMaterial material) {
        EducationalMaterial educationalMaterial = materialDAO.findEducationalMaterial(String.valueOf(material.getId()));
        if (educationalMaterial.getReviewStatus() != MaterialStatus.READY_FOR_REVIEW) {
            throw new EducationalMaterialException("Material with this status cannot be reviewed.");
        }
//        if (isNotReviewer(educationalMaterial)) {
//            throw new EducationalMaterialException("The user doesnt have access to this action.");
//        }

        educationalMaterial.setReviewStatus(MaterialStatus.UNDER_REVIEW);
        materialDAO.updateEducationalMaterial(educationalMaterial);
        return educationalMaterial;
    }

    private boolean isNotReviewer(EducationalMaterial material) {
        return sessionService.getCurrentUser().equals(material.getReviewer());
    }

//    @Override
    private EducationalMaterial backToReworkMaterial(EducationalMaterial material) {
        EducationalMaterial educationalMaterial = materialDAO.findEducationalMaterial(String.valueOf(material.getId()));
        if (educationalMaterial.getReviewStatus() != MaterialStatus.UNDER_REVIEW) {
            throw new EducationalMaterialException("Material with this status cannot be back to rework.");
        }
//        if (isNotReviewer(educationalMaterial)) {
//            throw new EducationalMaterialException("The user doesnt have access to this action.");
//        }

        educationalMaterial.setReviewStatus(MaterialStatus.BACK_TO_REWORK);
        materialDAO.updateEducationalMaterial(educationalMaterial);
        return educationalMaterial;
    }

//    @Override
    private EducationalMaterial cancelMaterial(EducationalMaterial material) {
        EducationalMaterial educationalMaterial = materialDAO.findEducationalMaterial(String.valueOf(material.getId()));
        if (mayBeCancelled(educationalMaterial)) {
//            if (isNotReviewer(educationalMaterial)) {
//                throw new EducationalMaterialException("The user doesnt have access to this action.");
//            }
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

//    @Override
    private EducationalMaterial approveMaterial(EducationalMaterial material) {
        EducationalMaterial educationalMaterial = materialDAO.findEducationalMaterial(String.valueOf(material.getId()));
        if (educationalMaterial.getReviewStatus() != MaterialStatus.UNDER_REVIEW) {
            throw new EducationalMaterialException("Material with this status cannot be approved.");
        }
//        if (isNotReviewer(educationalMaterial)) {
//            throw new EducationalMaterialException("The user doesnt have access to this action.");
//        }

        educationalMaterial.setReviewStatus(MaterialStatus.REVIEWED);
        educationalMaterial.setReviewFinishDate(LocalDateTime.now());
        materialDAO.updateEducationalMaterial(educationalMaterial);
        return educationalMaterial;
    }

    @Override
    public EducationalMaterial getEducationalDiscipline(Long id) {
        return materialDAO.findEducationalMaterial(String.valueOf(id));
    }

    @Override
    public EducationalMaterial changeReviewStatus(EducationalMaterial educationalMaterial, Long id) {
        if (educationalMaterial.getReviewStatus() == materialDAO.findEducationalMaterial(String.valueOf(id)).getReviewStatus()) {
            throw new EducationalMaterialException("No status changes");
        }
        if (educationalMaterial.getReviewStatus() == MaterialStatus.REVIEWED) {
            return approveMaterial(materialDAO.findEducationalMaterial(String.valueOf(id)));
        }
        if (educationalMaterial.getReviewStatus() == MaterialStatus.CANCELLED) {
            return cancelMaterial(materialDAO.findEducationalMaterial(String.valueOf(id)));
        }
        if (educationalMaterial.getReviewStatus() == MaterialStatus.BACK_TO_REWORK) {
            return backToReworkMaterial(materialDAO.findEducationalMaterial(String.valueOf(id)));
        }
        if (educationalMaterial.getReviewStatus() == MaterialStatus.UNDER_REVIEW) {
            return reviewMaterial(materialDAO.findEducationalMaterial(String.valueOf(id)));
        }
        if (educationalMaterial.getReviewStatus() == MaterialStatus.READY_FOR_REVIEW) {
            return makeReadyForReview(materialDAO.findEducationalMaterial(String.valueOf(id)));
        }
        return null;
    }
}
