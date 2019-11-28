import java.sql.*;

public class OpinionDAO {
    public static final String DRIVER = "org.sqlite.JDBC";
    public static final String DB_URL = "jdbc:sqlite:opinionDatabase.db";

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
        String createOpinions = "CREATE TABLE IF NOT EXISTS opinions (header varchar(255) PRIMARY KEY, opinionContent varchar(255), nick varchar(255), stars varchar(255), opinionDate date, votesFor int, votesAgainst int)";
//        String createKsiazki = "CREATE TABLE IF NOT EXISTS ksiazki (id_ksiazki INTEGER PRIMARY KEY AUTOINCREMENT, tytul varchar(255), autor varchar(255))";
//        String createWypozyczenia = "CREATE TABLE IF NOT EXISTS wypozyczenia (id_wypozycz INTEGER PRIMARY KEY AUTOINCREMENT, id_czytelnika int, id_ksiazki int)";
        try {
            stat.execute(createOpinions);
//            stat.execute(createKsiazki);
//            stat.execute(createWypozyczenia);
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
                    "insert into opinions values (?, ?, ?, ?, ?, ?, ?);");
            prepStmt.setString(1, opinion.getHeader());
            prepStmt.setString(2, opinion.getContent());
            prepStmt.setString(3, opinion.getNick());
            prepStmt.setString(4, opinion.getStars());
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

//    public boolean insertKsiazka(String tytul, String autor) {
//        try {
//            PreparedStatement prepStmt = conn.prepareStatement(
//                    "insert into ksiazki values (NULL, ?, ?);");
//            prepStmt.setString(1, tytul);
//            prepStmt.setString(2, autor);
//            prepStmt.execute();
//        } catch (SQLException e) {
//            System.err.println("Blad przy wypozyczaniu");
//            return false;
//        }
//        return true;
//    }
//
//    public boolean insertWypozycz(int idCzytelnik, int idKsiazka) {
//        try {
//            PreparedStatement prepStmt = conn.prepareStatement(
//                    "insert into wypozyczenia values (NULL, ?, ?);");
//            prepStmt.setInt(1, idCzytelnik);
//            prepStmt.setInt(2, idKsiazka);
//            prepStmt.execute();
//        } catch (SQLException e) {
//            System.err.println("Blad przy wypozyczaniu");
//            return false;
//        }
//        return true;
//    }

//    public List<Czytelnik> selectCzytelnicy() {
//        List<Czytelnik> czytelnicy = new LinkedList<Czytelnik>();
//        try {
//            ResultSet result = stat.executeQuery("SELECT * FROM czytelnicy");
//            int id;
//            String imie, nazwisko, pesel;
//            while (result.next()) {
//                id = result.getInt("id_czytelnika");
//                imie = result.getString("imie");
//                nazwisko = result.getString("nazwisko");
//                pesel = result.getString("pesel");
//                czytelnicy.add(new Czytelnik(id, imie, nazwisko, pesel));
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return null;
//        }
//        return czytelnicy;
//    }
//
//    public List<Ksiazka> selectKsiazki() {
//        List<Ksiazka> ksiazki = new LinkedList<Ksiazka>();
//        try {
//            ResultSet result = stat.executeQuery("SELECT * FROM ksiazki");
//            int id;
//            String tytul, autor;
//            while (result.next()) {
//                id = result.getInt("id_ksiazki");
//                tytul = result.getString("tytul");
//                autor = result.getString("autor");
//                ksiazki.add(new Ksiazka(id, tytul, autor));
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return null;
//        }
//        return ksiazki;
//    }

    public void closeConnection() {
        try {
            conn.close();
        } catch (SQLException e) {
            System.err.println("Problem z zamknieciem polaczenia");
            e.printStackTrace();
        }
    }


    public void dropDB() {
        try {
            stat.execute("DROP TABLE IF EXISTS opinions");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}