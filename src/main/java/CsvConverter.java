import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CsvConverter {
    //Delimiter used in CSV file
    private static final String COMMA_DELIMITER = ";";
    private static final String NEW_LINE_SEPARATOR = "\n";

    //CSV file header
    private static final String FILE_HEADER = "header;content;nick;stars;date;votesFor;votesAgainst";


    private static void writeCsvFile(String fileName, List<Opinion> opinionList) {

        FileWriter fileWriter = null;

        try {
            fileWriter = new FileWriter(fileName);

            //Write the CSV file header
            fileWriter.append(FILE_HEADER.toString());

            //Add a new line separator after the header
            fileWriter.append(NEW_LINE_SEPARATOR);

            //Write a new opinion object list to the CSV file
            for (Opinion opinion : opinionList) {
                fileWriter.append(opinion.getHeader());
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append(opinion.getContent());
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append(opinion.getNick());
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append(opinion.getStars());
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append(String.valueOf(opinion.getDate()));
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append(String.valueOf(opinion.getVotesFor()));
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append(String.valueOf(opinion.getVotesAgainst()));
                fileWriter.append(NEW_LINE_SEPARATOR);
            }


            System.out.println("CSV file was created successfully!");
            System.out.println("Path " + fileName);

        } catch (Exception e) {
            System.out.println("Error in CsvFileWriter !!!");
            e.printStackTrace();
        } finally {

            try {
                fileWriter.flush();
                fileWriter.close();
            } catch (IOException e) {
                System.out.println("Error while flushing/closing fileWriter !!!");
                e.printStackTrace();
            }

        }
    }


    public static void convertToCsv(List<Opinion> opinionList) {
        String fileName = System.getProperty("user.home") + "\\opinions.csv";
        CsvConverter.writeCsvFile(fileName, opinionList);
        System.out.println("Csv created");
    }
}