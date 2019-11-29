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
        System.out.println("test");


        int command = 1;
        int previous = 4;
        Scanner scanner = new Scanner(System.in);
        while (command != 0) {
            System.out.println("Choose what to do");
            System.out.println("1 - ETL");
            System.out.println("2 - E");
            System.out.println("3 - T");
            System.out.println("4 - L");
            System.out.println("5 - Clear database");
            System.out.println("6 - Convert to .csv");
            while (!scanner.hasNextInt()) {
                System.out.println("choose number");
                scanner.next();
            }
            command = scanner.nextInt();
            switch (command) {
                case 1:
                    if (previous == 4) {
                        System.out.println("you can do etl");
                        etl("https://www.euro.com.pl/telefony-komorkowe/xiaomi-redmi-note-7-4-64-gb-czarny.bhtml");
                    } else {
                        System.out.println("u need to finish current etl process");
                        System.out.println("last process was " + previous);
                    }
                    break;
                case 2:
                    if (previous == 4) {
                        System.out.println("Extracting");
                        documentList = Extract.getDocuments("https://www.euro.com.pl/telefony-komorkowe/xiaomi-redmi-note-7-4-64-gb-czarny.bhtml");
                        previous = 2;
                    } else {
                        System.out.println("u need to finish current etl process");
                        System.out.println("last process was " + previous);
                    }
                    break;
                case 3:
                    if (previous == 2) {
                        System.out.println("transforming");
                        opinionList = Transform.transformDocuments(documentList);
                        previous = 3;
                    } else {
                        System.out.println("u need to do extract before");
                    }
                    break;
                case 4:
                    if (previous == 3) {
                        System.out.println("you can do load");
                        load(opinionList);
                        previous = 4;
                    } else {
                        System.out.println("u need to do transform before");
                    }
                    break;
                case 5:
                    clearDB();
                    System.out.println("database cleared");
                    break;
                case 6:
                    CsvConverter.convertToCsv(opinionList);
                    break;
            }
        }
    }

    private static void etl(String url) {
        documentList = Extract.getDocuments(url);
        opinionList = Transform.transformDocuments(documentList);
        load(opinionList);
    }

    private static void load(List<Opinion> opinionList) {
        opinionList.forEach(opinion -> b.insertOpinion(opinion));
        b.closeConnection();
    }

    private static void clearDB() {
        b.dropDB();
        b.closeConnection();
    }

}



