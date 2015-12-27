package rge.engine;

import org.apache.commons.lang3.StringEscapeUtils;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;

public class GrandExchange {
    private static final String BASE_URL = "http://runescape.wikia.com/wiki/";

    public static final String NOT_FOUND_TEXT = "Not found";

    private GrandExchange() {
    }

    public static Item get(String itemName) throws IOException {
        String requestUrl = createRequestUrl(itemName);

        Document document = downloadWebDocument(requestUrl);
        if (document == null) {
            return new Item(NOT_FOUND_TEXT, null, null);
        }

        Element imgElement = parseImageElement(document);
        if (imgElement == null) {
            return new Item(NOT_FOUND_TEXT, parseItemPrice(document), null);
        }

        return new Item(parseItemName(imgElement), parseItemPrice(document), parseImageUrl(imgElement));
    }

    private static String createRequestUrl(String itemName) {
        return BASE_URL + Parser.parse(itemName);
    }

    private static Document downloadWebDocument(String requestUrl) throws IOException {
        try {
            return Jsoup.connect(requestUrl).get();
        } catch (HttpStatusException e) {
            return null;
        }
    }

    private static Element parseImageElement(Document document) {
        Element imgElement = document.select(".wikitable.infobox .infobox-image a.image img").first();
        if (imgElement != null && imgElement.attr("src").startsWith("data:")) {
            imgElement = document.select(".wikitable.infobox .infobox-image a.image noscript img").first();
        }
        return imgElement;
    }

    private static String parseItemPrice(Document document) {
        Element priceElement = document.select("#GEPrice .GEItem > span").first();
        if (priceElement == null) {
            return null;
        }
        return priceElement.text();
    }

    private static String parseImageUrl(Element imgElement) {
        return imgElement.attr("src");
    }

    private static String parseItemName(Element imgElement) {
        return StringEscapeUtils.unescapeHtml3(imgElement.attr("alt"));
    }
}
