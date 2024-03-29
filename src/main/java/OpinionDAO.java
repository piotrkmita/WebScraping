import java.sql.*;

/**
 * Class responsible for database access
 */
public class OpinionDAO {
    private static final String DRIVER = "org.sqlite.JDBC";
    private static final String DB_URL = "jdbc:sqlite:opinionDatabase.db";
    public boolean dbDropped = false;

    private Connection conn;
    private Statement stat;

    /**
     * Constructor
     */
    public OpinionDAO() {
        try {
            Class.forName(OpinionDAO.DRIVER);
        } catch (ClassNotFoundException e) {
            System.err.println("Add JDBC driver");
            e.printStackTrace();
        }

        try {
            conn = DriverManager.getConnection(DB_URL);
            stat = conn.createStatement();
        } catch (SQLException e) {
            System.err.println("Cannot open connection");
            e.printStackTrace();
        }

        createTables();
    }

    /**
     * Method creating database table opinions
     * @return boolean true if done otherwise false
     */
    public boolean createTables() {
        String createOpinions = "CREATE TABLE IF NOT EXISTS opinions (id_opinion INTEGER PRIMARY KEY AUTOINCREMENT, header varchar(255) , opinionContent varchar(255), nick varchar(255), stars double, opinionDate date, votesFor int, votesAgainst int)";
        try {
            stat.execute(createOpinions);
        } catch (SQLException e) {
            System.err.println("Error during creating table");
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * Takes opinion and insert it to database
     * @param opinion
     * @return
     */
    public boolean insertOpinion(Opinion opinion) {
        try {

            PreparedStatement prepStmt = conn.prepareStatement(
                    "insert into opinions values (NULL,?, ?, ?, ?, ?, ?, ?);");
            prepStmt.setString(1, opinion.getHeader());
            prepStmt.setString(2, opinion.getContent());
            prepStmt.setString(3, opinion.getNick());
            prepStmt.setDouble(4, opinion.getStars());
            prepStmt.setString(5, opinion.getDate().toString());
            prepStmt.setInt(6, opinion.getVotesFor());
            prepStmt.setInt(7, opinion.getVotesAgainst());
            prepStmt.execute();
        } catch (SQLException e) {
            System.err.println("Error during inserting opinion");
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * Close connection to database
     */
    public void closeConnection() {
        try {
            conn.close();
        } catch (SQLException e) {
            System.err.println("Could not close connection");
            e.printStackTrace();
        }
    }

    /**
     * Open connection to database
     */
    public void openConnection() {
        try {
            conn = DriverManager.getConnection(DB_URL);
            stat = conn.createStatement();
        } catch (SQLException e) {
            System.err.println("Could not open connection");
            e.printStackTrace();
        }
    }

    /**
     * Drop database
     */
    public void dropDB() {
        try {
            conn = DriverManager.getConnection(DB_URL);
            stat = conn.createStatement();
            stat.execute("DROP TABLE IF EXISTS opinions");
            this.dbDropped = true;
        } catch (SQLException e) {
            System.err.println("Could not drop database");
            e.printStackTrace();
        }
    }
}