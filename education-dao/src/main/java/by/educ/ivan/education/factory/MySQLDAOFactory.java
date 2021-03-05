package by.educ.ivan.education.factory;

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
    private static BasicDataSource mDatasource;

    //Members

    //Properties

    public MySQLDAOFactory() {
        Properties mySQLproperties = new PropertiesUtil()
                .getProperties(MYSQL_CONFIG_PROPERTIES);
        mDatasource = new BasicDataSource();
        mDatasource.setDriverClassName(
                mySQLproperties.getProperty(DRIVER_CLASS_NAME));
        mDatasource.setUrl(mySQLproperties.getProperty(CONNECTION_URL));
        mDatasource.setUsername(mySQLproperties.getProperty(USER));
        mDatasource.setPassword(mySQLproperties.getProperty(PASSWORD));

//        try {
//            Class.forName(mySQLproperties.getProperty(DRIVER_CLASS_NAME));
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
    }

    //Methods

    public static Connection getConnection() throws SQLException {
        return mDatasource.getConnection();
    }

    /**
     * Returns factory instance
     */
//	public static synchronized DAOFactory getInstance() {
//		if (daoFactory == null) {
//			daoFactory = new MySQLDAOFactory();
//		}
//		return daoFactory;
//	}

}
