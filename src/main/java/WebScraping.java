import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Main class with menu
 */
public class WebScraping {
    private static List<Document> documentList = new ArrayList<>();
    private static List<Opinion> opinionList = new ArrayList<>();
    private static OpinionDAO opinionDAO = new OpinionDAO();

    /**
     * Main menu
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) {
        int command = 1;
        int previous = 4;
        Scanner scanner = new Scanner(System.in);
        while (command != 0) {
            System.out.println("\n1 - ETL");
            System.out.println("2 - E");
            System.out.println("3 - T");
            System.out.println("4 - L");
            System.out.println("5 - Clear database");
            System.out.println("6 - Convert to .csv");
            System.out.println("0 - Exit");
            System.out.println("Choose what to do: ");
            while (!scanner.hasNextInt()) {
                System.out.println("Choose number");
                scanner.next();
            }
            command = scanner.nextInt();
            switch (command) {
                case 1:
                    if (previous == 4) {
                        try {
                            Scanner scanner2 = new Scanner(System.in);
                            System.out.println("Enter product url");
                            String link = scanner2.next().trim();
                            etl(link);
                        }catch(Exception e) {
                            System.out.println("Wrong url");
                            System.out.println("Choose product from https://www.euro.com.pl/telefony-komorkowe.bhtml");
                            System.out.println("Example: https://www.euro.com.pl/telefony-komorkowe/xiaomi-redmi-note-7-4-64-gb-czarny.bhtml");
                        }

                    } else {
                        System.out.println("You need to finish current ETL process");
                        System.out.println("Last process step was " + previous);
                    }
                    break;
                case 2:
                    if (previous == 4) {
                        try {
                            Scanner scanner2 = new Scanner(System.in);
                            System.out.println("Enter product url");
                            String link = scanner2.next().trim();
                            documentList = Extract.getDocuments(link);
                            previous = 2;
                        }catch (Exception e){
                            System.out.println("Wrong url");
                            System.out.println("Choose product from https://www.euro.com.pl/telefony-komorkowe.bhtml");
                            System.out.println("Example: https://www.euro.com.pl/telefony-komorkowe/xiaomi-redmi-note-7-4-64-gb-czarny.bhtml");
                        }

                    } else {
                        System.out.println("You need to finish current ETL process");
                        System.out.println("Last process step was " + previous);
                    }
                    break;
                case 3:
                    if (previous == 2) {
                        opinionList = Transform.transformDocuments(documentList);
                        previous = 3;
                    } else {
                        System.out.println("You need to perform extract before transforming");
                    }
                    break;
                case 4:
                    if (previous == 3) {
                        load(opinionList);
                        previous = 4;
                    } else {
                        System.out.println("You need to do transform before loading");
                    }
                    break;
                case 5:
                    System.out.println("Clearing database...");
                    clearDB();
                    System.out.println("Database cleared");
                    break;
                case 6:
                    System.out.println("Converting to CSV file");
                    CsvConverter.convertToCsv(opinionList);
                    break;
            }
        }
    }

    /**
     * Method performing whole etl process.
     * Takes URL of product from rtveuroagd website
     * @param url
     */
    private static void etl(String url) throws Exception {
        clearDB();
        documentList = Extract.getDocuments(url);
        if(documentList.isEmpty()){
            System.out.println("No opinions found, please choose different product");
        }else {
            System.out.println("Transforming...");
            opinionList = Transform.transformDocuments(documentList);
            opinionDAO.closeConnection();
            load(opinionList);
        }
    }

    /**
     * Method loading opinions to database
     * Takes opinionList
     * @param opinionList
     */
    private static void load(List<Opinion> opinionList) {
        System.out.println("Loading...");
        clearDB();
        opinionDAO.openConnection();
        if (opinionDAO.dbDropped) {
            opinionDAO.createTables();
        }
        opinionList.forEach(opinion -> opinionDAO.insertOpinion(opinion));
        System.out.println(opinionList.size() + " opinions loaded to database.");
        opinionDAO.closeConnection();
    }

    /**
     * Method clearing database
     */
    private static void clearDB() {
        opinionDAO.dropDB();
        opinionDAO.closeConnection();
    }

}



