import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Product {
    private int id;
    private String name;
    private int price;
    private int stars;
    private List<Opinion> opinionList = new ArrayList<>();
    private int opinionsSize;
    private static final String WEBSITE_URL = "https://www.euro.com.pl";
    private static final String OPINIONS_URL_PREFFIX = "https://www.euro.com.pl/product-card-opinion.ltr?nodeid=telefony-komorkowe&product-id=";
    private static final String OPINIONS_URL_SUFFIX = "&group-id=698969&preview=false&sort=DEFAULT&scrollTo=false&page_nr=";

    public Product(String url) {
        System.out.println("start adding product");

        try {
            final Document document = Jsoup.connect(WEBSITE_URL + url).get();

            this.name = document.select("h1.selenium-KP-product-name").get(0).ownText();
            this.price = Integer.parseInt(document.select("div.price-normal.selenium-price-normal").get(0).ownText().replace("zł", "").replace(" ", ""));

            //TODO make it optional can be 0 opinions
            try {
                this.stars = Integer.parseInt(document.select("div.opinion-summary-total > span").get(0).ownText());
            } catch (IndexOutOfBoundsException e) {
                this.stars = 0;
            }

            //TODO check what if 0
            this.opinionsSize = Integer.parseInt(document.select("a.js-save-keyword.js-scroll-by-hash.rating-count > em").get(0).text().replaceAll("[^0-9]", ""));

            System.out.println("name: " + name);
            System.out.println("price: " + price);
            System.out.println("stars: " + stars);
            System.out.println("size of opinions " + opinionsSize);

//          /telefony-komorkowe/xiaomi-smartfon-redmi-note-8pro-64g-gra-xiaomi.bhtml -> xiaomi-smartfon-redmi-note-8pro-64g-gra-xiaomi
            System.out.println("opinions " + url.substring(url.lastIndexOf("/") + 1, url.lastIndexOf(".")));


            for (int i = 1; i <= opinionsSize / 10 + 1; i++) {
                createOpinions(i, url.substring(url.lastIndexOf("/") + 1, url.lastIndexOf(".")));
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createOpinions(int pageNumber, String url) {
        try {
            final Document document = Jsoup.connect(OPINIONS_URL_PREFFIX + url + OPINIONS_URL_SUFFIX + pageNumber).get();

            Elements webOpinionList = document.select("div.opinion-item");

            for (Element element : webOpinionList) {
                opinionList.add(new Opinion(name, element));
            }


        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
