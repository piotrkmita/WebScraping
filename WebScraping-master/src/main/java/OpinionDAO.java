import java.sql.*;

public class OpinionDAO {
    public static final String DRIVER = "org.sqlite.JDBC";
    public static final String DB_URL = "jdbc:sqlite:opinionDatabase.db";
    public boolean dbDropped = false;

    private Connection conn;
    private Statement stat;

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

    public boolean insertOpinion(Opinion opinion) { //String header, String opinionContent, String nick, String stars, LocalDate opinionDate, int votesFor, int votesAgainst
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

    public void closeConnection() {
        try {
            conn.close();
        } catch (SQLException e) {
            System.err.println("Could not close connection");
            e.printStackTrace();
        }
    }

    public void openConnection() {
        try {
            conn = DriverManager.getConnection(DB_URL);
            stat = conn.createStatement();
        } catch (SQLException e) {
            System.err.println("Could not open connection");
            e.printStackTrace();
        }
    }


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