package odogwudozilla.inecIrev;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class JSoupInec {

    static void generateSoup() throws IOException {
        Document doc = Jsoup.connect("https://inecelectionresults.ng/pres/elections/63f8f25b594e164f8146a213?type=pres").get();
        Elements newsHeadlines = doc.select("#mp-itn b a");

    }

    public static void main(String[] args) throws IOException {
        generateSoup();
    }
}
