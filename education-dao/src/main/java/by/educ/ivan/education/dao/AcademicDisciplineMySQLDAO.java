package by.educ.ivan.education.dao;

import by.educ.ivan.education.factory.MySQLDAOFactory;
import by.educ.ivan.education.model.AcademicDiscipline;
import by.educ.ivan.education.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class AcademicDisciplineMySQLDAO implements AcademicDisciplineDAO {

    public AcademicDisciplineMySQLDAO() {
    }

    @Override
    public int insertAcademicDiscipline(AcademicDiscipline academicDiscipline) {
        String sql = "insert into academic_disciplines (name, abbreviation, description, author) values (?, ?, ?, ?);";

        try(Connection connection = MySQLDAOFactory.getConnection()) {
            PreparedStatement ptmt = connection.prepareStatement(sql);
            ptmt.setString(1, academicDiscipline.getName());
            ptmt.setString(2, academicDiscipline.getAbbreviation());
            ptmt.setString(3, academicDiscipline.getDescription());
            ptmt.setInt(4, academicDiscipline.getAuthor().getId());
            return ptmt.executeUpdate();
        } catch (SQLException ex) {
            return -1;
        }
    }

    @Override
    public boolean deleteAcademicDiscipline(String academicDisciplineId) {

        String sql = "delete from academic_disciplines where id = ?;";

        try(Connection connection = MySQLDAOFactory.getConnection()) {
            PreparedStatement ptmt = connection.prepareStatement(sql);
            ptmt.setString(1, academicDisciplineId);
            int count = ptmt.executeUpdate();
            return count == 1;
        } catch (SQLException ex) {
            return false;
        }
    }

    @Override
    public AcademicDiscipline findAcademicDiscipline(String academicDisciplineId) {

        String sql = "select id, name, abbreviation, description, author from academic_disciplines where id = ?;";

        try(Connection connection = MySQLDAOFactory.getConnection()) {
            PreparedStatement ptmt = connection.prepareStatement(sql);
            ptmt.setString(1, academicDisciplineId);
            ResultSet rs = ptmt.executeQuery();
            rs.next();
            return setDiscipline(rs);
        } catch (SQLException ex) {
            return null;
        }
    }

    @Override
    public boolean updateAcademicDiscipline(String academicDisciplineId, String academicDisciplineName) {

        String sql = "update academic_disciplines set name = ? where id = ?;";

        try(Connection connection = MySQLDAOFactory.getConnection()) {
            PreparedStatement ptmt = connection.prepareStatement(sql);
            ptmt.setString(1, academicDisciplineName);
            ptmt.setString(2, academicDisciplineId);
            int count = ptmt.executeUpdate();
            return count == 1;
        } catch (SQLException ex) {
            return false;
        }
    }

    @Override
    public Collection<AcademicDiscipline> selectAcademicDisciplines() {

        String SQL = "select id, name, abbreviation, description, author from academic_disciplines;";
        try(Connection connection = MySQLDAOFactory.getConnection()) {
            List<AcademicDiscipline> academicDisciplines = new ArrayList<AcademicDiscipline>();
            AcademicDiscipline academicDisciplineBean;
            PreparedStatement ptmt = connection.prepareStatement(SQL);
            ResultSet rs = ptmt.executeQuery();
            while (rs.next()) {
                academicDisciplineBean = setDiscipline(rs);
                academicDisciplines.add(academicDisciplineBean);
            }
            return academicDisciplines;
        } catch (SQLException ex) {
            return Collections.emptyList();
        }
    }

    private AcademicDiscipline setDiscipline(ResultSet rs) throws SQLException {
        UserDAO userDAO = new UserMySQLDAO();
        AcademicDiscipline academicDiscipline = new AcademicDiscipline();
        academicDiscipline.setId(rs.getInt(1));
        academicDiscipline.setName(rs.getString(2));
        academicDiscipline.setAbbreviation(rs.getString(3));
        academicDiscipline.setDescription(rs.getString(4));
        User author = userDAO.findUser(rs.getString(5));
        academicDiscipline.setAuthor(author);
        return academicDiscipline;
    }
}
