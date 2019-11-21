import org.jsoup.nodes.Element;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Opinion {

    private int id;
    private String productName;
    private String header;
    private String content;
    private LocalDate date;
    private String nick;
    private String stars;
    private int votesFor;
    private int votesAgainst;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy,");

    public Opinion(String productName, Element element) {  //Element element
        this.productName = productName;
        this.header = element.select("div.opinion-title").text();
        this.content = element.select("div.opinion-text").text();
        this.nick = element.select("div.opinion-nick").text();
        this.date = LocalDate.parse(element.select("div.opinion-date").text(), DATE_FORMATTER);
        this.votesFor = Integer.parseInt(element.select("span.opinion-helpful-yes-number").text().replaceAll("[^0-9]", ""));
        this.votesAgainst = Integer.parseInt(element.select("span.opinion-helpful-no-number").text().replaceAll("[^0-9]", ""));
//        this.stars =
        System.out.println("ele "+element.select("div.stars-rating.js-opinion-stars title").toString());

        System.out.println("product: " + productName);
        System.out.println("header: " + header);
        System.out.println("content: " + content);
        System.out.println("nick: " + nick);
        System.out.println("date: " + date);
        System.out.println("votesFor: " + votesFor);
        System.out.println("votesAgainst: " + votesAgainst);
        System.out.println("stars "+stars);

    }
}
