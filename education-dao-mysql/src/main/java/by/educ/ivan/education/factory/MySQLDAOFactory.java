package by.educ.ivan.education.factory;

import by.educ.ivan.education.dao.*;
import by.educ.ivan.education.util.PropertiesUtil;
import org.apache.commons.dbcp.BasicDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class MySQLDAOFactory extends DAOFactory {

    private static final String MYSQL_CONFIG_PROPERTIES = "mysql.properties";
    private static final String DRIVER_CLASS_NAME = "driverClassName";
    private static final String CONNECTION_URL = "connectionUrl";
    private static final String USER = "user";
    private static final String PASSWORD = "password";
    private static final BasicDataSource mDatasource;

    static {
        Properties mySqlProperties = new PropertiesUtil()
                .getProperties(MYSQL_CONFIG_PROPERTIES);
        mDatasource = new BasicDataSource();
        mDatasource.setDriverClassName(
                mySqlProperties.getProperty(DRIVER_CLASS_NAME));
        mDatasource.setUrl(mySqlProperties.getProperty(CONNECTION_URL));
        mDatasource.setUsername(mySqlProperties.getProperty(USER));
        mDatasource.setPassword(mySqlProperties.getProperty(PASSWORD));
    }

    public static Connection getConnection() throws SQLException {
        return mDatasource.getConnection();
    }

    public AcademicDisciplineDAO getAcademicDisciplineDAO() {
        return new AcademicDisciplineMySQLDAO(getUserDAO());
    }

    public AdminOperationDAO getAdminOperationDAO() {
        return new AdminOperationMySQLDAO(getUserDAO());
    }

    public EducationalMaterialDAO getEducationalMaterialDAO() {
        return new EducationalMaterialMySQLDAO(getUserDAO(), getAcademicDisciplineDAO());
    }

    public StudentDAO getStudentDAO() {
        return new StudentMySQLDAO();
    }

    public StudyAssignmentDAO getStudyAssignmentDAO() {
        return new StudyAssignmentMySQLDAO(getUserDAO(), getStudentDAO(), getEducationalMaterialDAO());
    }

    public UserDAO getUserDAO() {
        return new UserMySQLDAO();
    }
}
