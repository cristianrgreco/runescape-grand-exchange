package rge.engine;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

public class GrandExchange {
    private static final String BASE_URL = "http://runescape.wikia.com/wiki/";

    private GrandExchange() {
    }

    public static Item get(String itemName) throws IOException {
        String requestUrl = createRequestUrl(itemName);
        Document document = downloadWebDocument(requestUrl);
        Elements imgElement = document.select(".wikitable.infobox td noscript img");

        String price = parseItemPrice(document);
        String imageUrl = parseImageUrl(imgElement);
        String name = parseImageName(imgElement);

        return new Item(name, price, imageUrl);
    }

    private static String createRequestUrl(String itemName) {
        return BASE_URL + Parser.parse(itemName);
    }

    private static Document downloadWebDocument(String requestUrl) throws IOException {
        return Jsoup.connect(requestUrl).get();
    }

    private static String parseItemPrice(Document document) {
        return document.select("#GEPrice .GEItem > span").first().text();
    }

    private static String parseImageUrl(Elements imgElement) {
        return imgElement.attr("src");
    }

    private static String parseImageName(Elements imgElement) {
        return imgElement.attr("alt");
    }
}
