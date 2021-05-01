package by.educ.ivan.education.service;

import by.educ.ivan.education.dao.AcademicDisciplineDAO;
import by.educ.ivan.education.exception.AcademicDisciplineException;
import by.educ.ivan.education.model.AcademicDiscipline;
import by.educ.ivan.education.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Service
@Transactional
public class AcademicDisciplineServiceImpl implements AcademicDisciplineService {

    private final AcademicDisciplineDAO disciplineDAO;

    private final SessionService sessionService;

    private final UserService userService;

    @Autowired
    public AcademicDisciplineServiceImpl(AcademicDisciplineDAO disciplineDAO, SessionService sessionService, UserService userService) {
        this.disciplineDAO = disciplineDAO;
        this.sessionService = sessionService;
        this.userService = userService;
    }

    @Override
    public AcademicDiscipline createAcademicDiscipline(AcademicDiscipline academicDiscipline) {
//        if (!sessionService.isProfessor()) {
//            throw new AcademicDisciplineException("Wrong professor user role.");
//        }

//        academicDiscipline.setAuthor(sessionService.getCurrentUser());
        academicDiscipline.setAuthor(userService.getUserByEmail("qjamal.chafik.75t@gmailwe.com"));

        return  disciplineDAO.insertAcademicDiscipline(academicDiscipline);
    }

    @Override
    public AcademicDiscipline editAcademicDiscipline(AcademicDiscipline academicDiscipline, Long id) {
        if (!isAuthor(academicDiscipline.getAuthor())) {
            throw new AcademicDisciplineException("This user is not the author of this discipline.");
        }

        AcademicDiscipline discipline = disciplineDAO.findAcademicDiscipline(String.valueOf(id));
        if (academicDiscipline.getName() != null) {
            discipline.setName(academicDiscipline.getName());
        }
        if (academicDiscipline.getAbbreviation() != null) {
            discipline.setAbbreviation(academicDiscipline.getAbbreviation());
        }
        if (academicDiscipline.getDescription() != null) {
            discipline.setDescription(academicDiscipline.getDescription());
        }
        if (academicDiscipline.getAuthor() != null) {
            discipline.setAuthor(academicDiscipline.getAuthor());
        }
        disciplineDAO.updateAcademicDiscipline(discipline);
        return discipline;
    }

    private boolean isAuthor(User author) {
        return sessionService.getCurrentUser().equals(author);
    }

    @Override
    public Collection<AcademicDiscipline> getAllAcademicDisciplines() {
        return disciplineDAO.selectAcademicDisciplines();
    }

    @Override
    public AcademicDiscipline getAcademicDiscipline(Long id) {
        return disciplineDAO.findAcademicDiscipline(String.valueOf(id));
    }
}
