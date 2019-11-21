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
    private int opinionsNumber;
    final String prefixUrl = "https://www.euro.com.pl";

    public Product(String url) {
        System.out.println("start adding product");

        try{
            final Document document = Jsoup.connect(prefixUrl+url).get();
//            Elements phone = document.head().getElementsByAttribute("name");
//            String test = phone.get(1).attributes().get("content");
//                    //.getElementsByAttribute("name").get(1).attributes().get("content");
//            this.name = phone.get(1).attributes().get("content");
//            this.name = document.head().getElementsByAttribute("name").get(1).attributes().get("content");


//            final Connection.Response document1 = Jsoup.connect(prefixUrl+url)
//                    .userAgent("Mozilla/5.0")
//                    .data("nodeid","telefony-komorkowe")
//                    .data("product-id","huawei-smartfon-p30-pro-6-128-twilight")
//                    .data("group-id","698969")
//                    .data("sort","DEFAULT")
//                    .data("scrollTo","false")
//                    .data("page_nr","3")
//                    .method(Connection.Method.GET)
//                    .execute();
////                    .post();
//
////            ?System.out.println("response "+document1);
//            Document document = document1.parse();

//            System.out.println("codument = "+document);

//            Elements asd = document.getElementsByClass("div.paging-numbers");


            document.select("div.paging-numbers > a")
                    .forEach(s-> System.out.println(s.select("div.opinion-item").size()));

            this.name =  document.select("h1.selenium-KP-product-name").get(0).ownText();
            this.price = Integer.parseInt(document.select("div.price-normal.selenium-price-normal").get(0).ownText().replace("zł","").replace(" ",""));

            //TODO make it optional can be 0 opinions
            try {
                this.stars = Integer.parseInt(document.select("div.opinion-summary-total > span").get(0).ownText());
            }catch(IndexOutOfBoundsException e){
                this.stars=0;
            }


            System.out.println("name: "+name);
            System.out.println("price: " +price);
            System.out.println("stars: " + stars);


            System.out.println("SIZE "+document.select("div.paging-number"));



            System.out.println("size of opinions "+document.select("div.opinion-item").size());
//            Element asd = document.select("div.opinion-item").get(0);
            Elements webOpinionList = document.select("div.opinion-item");



            //Adding opinions
            for (Element element: webOpinionList){
                opinionList.add(new Opinion(name, element));
            }



//            for(Element e: asd){
//                System.out.println("test "+e.toString());
//            }

//            Elements phone = document.getElementsByClass("has-basic-tech-details");
//            //System.out.println("HERE: "+phone.toString().substring());
//            String test = phone.select("h3").toString();
//            test = test.replace(" smartfon ","");
//            test =test.substring(test.indexOf(">")+1,test.lastIndexOf("<"));
//            this.name=test;
//            System.out.println(test);



//            Element resultLinks = document.select("description-tech-content").first();
                //System.out.println(phone);
//            Element phones = document.getElementById("products");
//            Set<String> phonesLinks = phones.getElementsByClass("productHref").stream().map(row -> row.attributes().get("value")).collect(Collectors.toSet());
//            System.out.println(phonesLinks);
//
////            phonesLinks.forEach(Product::new);
//            phonesLinks.stream().limit(1).forEach(Product::new);
//            System.out.println(document.outerHtml());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
