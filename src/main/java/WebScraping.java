import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.Set;
import java.util.stream.Collectors;

public class WebScraping {
    public static void main(String[] args) {
        System.out.println("test");


        final String url = "https://www.euro.com.pl/telefony-komorkowe,strona-1.bhtml";


        try{
            final Document document = Jsoup.connect(url).get();
            Element phones = document.getElementById("products");
            Set<String> phonesLinks = phones.getElementsByClass("productHref").stream().map(row -> row.attributes().get("value")).collect(Collectors.toSet());
            System.out.println(phonesLinks);

//            phonesLinks.forEach(Product::new);
            phonesLinks.stream().skip(4).limit(1).forEach(Product::new);
            //System.out.println(document.outerHtml());



        } catch (IOException e) {
            e.printStackTrace();
        }
    }




}
