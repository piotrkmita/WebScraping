import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Class responsible for extracting all htmls with product data and opinions
 */
public class Extract {
    private static final String OPINIONS_URL_PREFFIX = "https://www.euro.com.pl/product-card-opinion.ltr?nodeid=telefony-komorkowe&product-id=";
    private static final String OPINIONS_URL_SUFFIX = "&group-id=698969&preview=false&sort=DEFAULT&scrollTo=false&page_nr=";

    private Extract() {
    }

    /**
     * Takes URL and gets all html documents with product info and opinions
     * @param url
     * @return
     */
    public static List<Document> getDocuments(String url) throws IOException {
        List<Document> documentList = new ArrayList<>();
        int opinionsSize = 0;
//        try {
            final Document document = Jsoup.connect(url).get();
            String name = document.select("h1.selenium-KP-product-name").get(0).ownText();
            System.out.println("Extracting data for " + name + "...\n");
            try {
                opinionsSize = Integer.parseInt(document.select("a.js-save-keyword.js-scroll-by-hash.rating-count > em").get(0).text().replaceAll("[^0-9]", ""));
            }catch(Exception e){
                return documentList;
            }

            for (int i = 1; i <= opinionsSize / 10 + 1; i++) {
                documentList.add(getDocument(i, url.substring(url.lastIndexOf("/") + 1, url.lastIndexOf("."))));
            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        System.out.println("Extracted: " + documentList.size() + " htmls files with " + opinionsSize + " opinions ");
        return documentList;
    }

    /**
     * Takes page number and url and gets html document
     * @param pageNumber
     * @param url
     * @return
     */
    private static Document getDocument(int pageNumber, String url) {
        Document document = null;
        try {
            document = Jsoup.connect(OPINIONS_URL_PREFFIX + url + OPINIONS_URL_SUFFIX + pageNumber).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return document;
    }


}