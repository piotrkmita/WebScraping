import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Class responsible for transforming htmls to opinion objects
 */
public class Transform {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy,");

    private Transform() {
    }

    /**
     * Takes list of documents and transform to opinions list
     * @param documentList
     * @return
     */
    public static List<Opinion> transformDocuments(List<Document> documentList) {
        List<Opinion> opinionList = new ArrayList<>();
        System.out.println("Transforming documents for objects");
        for (Document document : documentList) {
            Elements webOpinionList = document.select("div.opinion-item");
            for (Element element : webOpinionList) {
                opinionList.add(createOpinion(element));
            }
        }
        opinionList.forEach(o -> System.out.println(o.toString()));
        System.out.println("\nTransformed " + opinionList.size() + " opinions");
        return opinionList;
    }


    /**
     * Takes element and creates opinion
     * @param element
     * @return
     */
    private static Opinion createOpinion(Element element) {
        String header = element.select("div.opinion-title").text();
        String content = element.select("div.opinion-text").text();
        String nick = element.select("div.opinion-nick").text();
        double stars = Double.parseDouble(element.select("div.stars-rating.js-opinion-stars > div > span").toString().replaceAll("[^0-9]", "")) / 20;
        LocalDate date = LocalDate.parse(element.select("div.opinion-date").text(), DATE_FORMATTER);
        int votesFor = Integer.parseInt(element.select("span.opinion-helpful-yes-number").text().replaceAll("[^0-9]", ""));
        int votesAgainst = Integer.parseInt(element.select("span.opinion-helpful-no-number").text().replaceAll("[^0-9]", ""));

        return new Opinion(header, content, nick, stars, date, votesFor, votesAgainst);
    }

}

