package by.educ.ivan.education.dao;

import by.educ.ivan.education.exception.DaoException;
import by.educ.ivan.education.factory.MySQLDAOFactory;
import by.educ.ivan.education.model.AcademicDiscipline;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class AcademicDisciplineMySQLDAO implements AcademicDisciplineDAO {

    private static final String INSERT = "insert into academic_disciplines (name, abbreviation, description, author) " +
            "values (?, ?, ?, ?)";

    private static final String DELETE = "delete from academic_disciplines where id = ?";

    private static final String SELECT = "select id, name, abbreviation, description, author from academic_disciplines " +
            "where id = ?";

    private static final String UPDATE = "update academic_disciplines set name = ? where id = ?";

    private static final String SELECT_ALL = "select id, name, abbreviation, description, author from academic_disciplines";

    private final UserDAO userDAO;

    public AcademicDisciplineMySQLDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public int insertAcademicDiscipline(AcademicDiscipline academicDiscipline) {
        try (Connection connection = MySQLDAOFactory.getConnection()) {
            PreparedStatement ptmt = connection.prepareStatement(INSERT);
            ptmt.setString(1, academicDiscipline.getName());
            ptmt.setString(2, academicDiscipline.getAbbreviation());
            ptmt.setString(3, academicDiscipline.getDescription());
            ptmt.setInt(4, academicDiscipline.getAuthor().getId());
            return ptmt.executeUpdate();
        } catch (SQLException ex) {
            throw new DaoException(ex);
        }
    }

    @Override
    public boolean deleteAcademicDiscipline(String academicDisciplineId) {
        try (Connection connection = MySQLDAOFactory.getConnection()) {
            PreparedStatement ptmt = connection.prepareStatement(DELETE);
            ptmt.setString(1, academicDisciplineId);
            int count = ptmt.executeUpdate();
            return count == 1;
        } catch (SQLException ex) {
            throw new DaoException(ex);
        }
    }

    @Override
    public AcademicDiscipline findAcademicDiscipline(String academicDisciplineId) {
        try (Connection connection = MySQLDAOFactory.getConnection()) {
            PreparedStatement ptmt = connection.prepareStatement(SELECT);
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
    public boolean updateAcademicDiscipline(String academicDisciplineId, String academicDisciplineName) {
        try (Connection connection = MySQLDAOFactory.getConnection()) {
            PreparedStatement ptmt = connection.prepareStatement(UPDATE);
            ptmt.setString(1, academicDisciplineName);
            ptmt.setString(2, academicDisciplineId);
            int count = ptmt.executeUpdate();
            return count == 1;
        } catch (SQLException ex) {
            throw new DaoException(ex);
        }
    }

    @Override
    public Collection<AcademicDiscipline> selectAcademicDisciplines() {
        try (Connection connection = MySQLDAOFactory.getConnection()) {
            List<AcademicDiscipline> academicDisciplines = new ArrayList<>();
            AcademicDiscipline academicDisciplineBean;
            PreparedStatement ptmt = connection.prepareStatement(SELECT_ALL);
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
        academicDiscipline.setId(rs.getInt(1));
        academicDiscipline.setName(rs.getString(2));
        academicDiscipline.setAbbreviation(rs.getString(3));
        academicDiscipline.setDescription(rs.getString(4));
        academicDiscipline.setAuthor(userDAO.findUser(rs.getString(5)));
        return academicDiscipline;
    }
}
