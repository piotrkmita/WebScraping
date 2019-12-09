import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class WebScraping {
    private static List<Document> documentList = new ArrayList<>();
    private static List<Opinion> opinionList = new ArrayList<>();
    private static OpinionDAO b = new OpinionDAO();

    public static void main(String[] args) throws IOException {
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
                        }catch(IOException e) {
                            System.out.println("Wrong url");
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
                        }catch (IOException e){
                            System.out.println("Wrong url");
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

    private static void etl(String url) throws IOException {
        clearDB();
        documentList = Extract.getDocuments(url);
        System.out.println("Transforming...");
        opinionList = Transform.transformDocuments(documentList);
        b.closeConnection();
        load(opinionList);
    }

    private static void load(List<Opinion> opinionList) {
        System.out.println("Loading...");
        clearDB();
        b.openConnection();
        if (b.dbDropped) {
            b.createTables();
        }
        opinionList.forEach(opinion -> b.insertOpinion(opinion));
        System.out.println(opinionList.size() + " opinions loaded to database.");
        b.closeConnection();
    }

    private static void clearDB() {
        b.dropDB();
        b.closeConnection();
    }

}



