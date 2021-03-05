package by.educ.ivan.education.factory;

public abstract class DAOFactory {

    // List of DAO types supported by the factory
    public static final int MYSQL = 1;
    public static final int ORACLE = 2;

    // There will be a method for each DAO that can be
    // created. The concrete factories will have to
    // implement these methods.

    public static DAOFactory getDAOFactory(int whichFactory) {

        switch (whichFactory) {
            case MYSQL:
                return new MySQLDAOFactory();
            case ORACLE:
                return null;
            default:
                return null;
        }
    }
}
