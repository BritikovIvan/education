package by.educ.ivan.education.dao;

import by.educ.ivan.education.exception.DaoException;
import by.educ.ivan.education.factory.MySQLDAOFactory;
import by.educ.ivan.education.model.AcademicDiscipline;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class AcademicDisciplineMySQLDAO implements AcademicDisciplineDAO {

    private static final String INSERT = "insert into academic_disciplines (name, abbreviation, description, author) " +
            "values (?, ?, ?, ?)";

    private static final String DELETE = "delete from academic_disciplines where id = ?";

    private static final String SELECT = "select id, name, abbreviation, description, author from academic_disciplines " +
            "where id = ?";

    private static final String UPDATE = "update academic_disciplines set name = ?, abbreviation = ?, " +
            "description = ?, author = ? where id = ?";

    private static final String SELECT_ALL = "select id, name, abbreviation, description, author from academic_disciplines";

    private final UserDAO userDAO;

    public AcademicDisciplineMySQLDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public AcademicDiscipline insertAcademicDiscipline(AcademicDiscipline academicDiscipline) {
        try (Connection connection = MySQLDAOFactory.getConnection();
             PreparedStatement ptmt = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS))
        {

            ptmt.setString(1, academicDiscipline.getName());
            ptmt.setString(2, academicDiscipline.getAbbreviation());
            ptmt.setString(3, academicDiscipline.getDescription());
            ptmt.setLong(4, academicDiscipline.getAuthor().getId());
            int affectedRows = ptmt.executeUpdate();
            if (affectedRows == 0) {
                throw new DaoException("Creating academic discipline failed, no rows affected.");
            }

            try (ResultSet generatedKeys = ptmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    academicDiscipline.setId(generatedKeys.getLong(1));
                } else {
                    throw new DaoException("Creating academic discipline failed, no ID obtained.");
                }
            }
            return academicDiscipline.getId();
        } catch (SQLException ex) {
            throw new DaoException(ex);
        }
    }

    @Override
    public boolean deleteAcademicDiscipline(String academicDisciplineId) {
        try (Connection connection = MySQLDAOFactory.getConnection();
             PreparedStatement ptmt = connection.prepareStatement(DELETE))
        {
            ptmt.setString(1, academicDisciplineId);
            int count = ptmt.executeUpdate();
            return count == 1;
        } catch (SQLException ex) {
            throw new DaoException(ex);
        }
    }

    @Override
    public AcademicDiscipline findAcademicDiscipline(String academicDisciplineId) {
        try (Connection connection = MySQLDAOFactory.getConnection();
             PreparedStatement ptmt = connection.prepareStatement(SELECT))
        {
            ptmt.setString(1, academicDisciplineId);
            ResultSet rs = ptmt.executeQuery();
            if (rs.next()) {
                return setDiscipline(rs);
            } else {
                return null;
            }
        } catch (SQLException ex) {
            throw new DaoException(ex);
        }
    }

    @Override
    public boolean updateAcademicDiscipline(AcademicDiscipline academicDiscipline) {
        try (Connection connection = MySQLDAOFactory.getConnection();
             PreparedStatement ptmt = connection.prepareStatement(UPDATE))
        {
            ptmt.setString(1, academicDiscipline.getName());
            ptmt.setString(2, academicDiscipline.getAbbreviation());
            ptmt.setString(3, academicDiscipline.getDescription());
            ptmt.setLong(4, academicDiscipline.getAuthor().getId());
            ptmt.setLong(5, academicDiscipline.getId());
            int count = ptmt.executeUpdate();
            return count == 1;
        } catch (SQLException ex) {
            throw new DaoException(ex);
        }
    }

    @Override
    public Collection<AcademicDiscipline> selectAcademicDisciplines() {
        try (Connection connection = MySQLDAOFactory.getConnection();
             PreparedStatement ptmt = connection.prepareStatement(SELECT_ALL))
        {
            List<AcademicDiscipline> academicDisciplines = new ArrayList<>();
            AcademicDiscipline academicDisciplineBean;
            ResultSet rs = ptmt.executeQuery();
            while (rs.next()) {
                academicDisciplineBean = setDiscipline(rs);
                academicDisciplines.add(academicDisciplineBean);
            }
            return academicDisciplines;
        } catch (SQLException ex) {
            throw new DaoException(ex);
        }
    }

    private AcademicDiscipline setDiscipline(ResultSet rs) throws SQLException {
        AcademicDiscipline academicDiscipline = new AcademicDiscipline();
        academicDiscipline.setId(rs.getLong(1));
        academicDiscipline.setName(rs.getString(2));
        academicDiscipline.setAbbreviation(rs.getString(3));
        academicDiscipline.setDescription(rs.getString(4));
        academicDiscipline.setAuthor(userDAO.findUser(rs.getString(5)));
        return academicDiscipline;
    }
}
