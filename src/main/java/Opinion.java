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
    private int stars;
    private int votesFor;
    private int votesAgainst;

    public Opinion(String productName,Element element) {
        this.productName=productName;
        this.header = element.select("div.opinion-title").text();
        this.content = element.select("div.opinion-text").text();
        this.nick = element.select("div.opinion-nick").text();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy,");
        this.date = LocalDate.parse(element.select("div.opinion-date").text(),formatter);
//                    new LocalDate("dd-MM-yyyy").parse(element.select("div.opinion-date").text().replace(",",""));
        this.votesFor= Integer.parseInt(element.select("span.opinion-helpful-yes-number").text().replaceAll("[^0-9]",""));
        this.votesAgainst= Integer.parseInt(element.select("span.opinion-helpful-no-number").text().replaceAll("[^0-9]",""));

        String test = element.select("div.paging-numbers").text();

//        for (Element el : test){
//            System.out.println("test "+ el.select("paging-active paging-number"));
//        }

        System.out.println("product: "+productName);
        System.out.println("header: "+header);
        System.out.println("content: "+content);
        System.out.println("nick: "+nick);
        System.out.println("date: "+date);
        System.out.println("votesFor: "+votesFor);
        System.out.println("votesAgainst: "+votesAgainst);

    }
}
