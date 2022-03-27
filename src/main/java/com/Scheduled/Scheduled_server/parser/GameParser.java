package com.Scheduled.Scheduled_server.parser;

import com.Scheduled.Scheduled_server.model.Game;
import com.Scheduled.Scheduled_server.model.GameBase;
import org.apache.logging.log4j.util.Strings;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.Scheduled.Scheduled_server.utils.Constants.ATTRIBUTE_DATA_IMAGE;
import static com.Scheduled.Scheduled_server.utils.Constants.ATTRIBUTE_HREF;
import static com.Scheduled.Scheduled_server.utils.Constants.HTML_SPACES;
import static com.Scheduled.Scheduled_server.utils.Constants.LINK_STORE_EPIC_GAMES;
import static com.Scheduled.Scheduled_server.utils.Constants.REGEX_PATTERN_DISCOUNT_PERCENT;
import static com.Scheduled.Scheduled_server.utils.Constants.REGEX_PATTERN_FREE;
import static com.Scheduled.Scheduled_server.utils.Constants.REGEX_PATTERN_PRICE;
import static com.Scheduled.Scheduled_server.utils.Constants.SELECTOR_DISCOUNT_PRICE;
import static com.Scheduled.Scheduled_server.utils.Constants.SELECTOR_FLAG_DISCOUNT;
import static com.Scheduled.Scheduled_server.utils.Constants.SELECTOR_NAME;
import static com.Scheduled.Scheduled_server.utils.Constants.SELECTOR_PRICE;
import static com.Scheduled.Scheduled_server.utils.Constants.SELECT_DISCOUNT_PERCENT;
import static com.Scheduled.Scheduled_server.utils.Constants.SEPARATOR_COMMA;
import static com.Scheduled.Scheduled_server.utils.Constants.SEPARATOR_DOT;
import static com.Scheduled.Scheduled_server.utils.Constants.SEPARATOR_SLASH;
import static com.Scheduled.Scheduled_server.utils.Constants.TAG_IMAGE;

@Component
public class GameParser {

    private String parseId(Element element) {
        return element.attr(ATTRIBUTE_HREF)
                .replaceFirst(SEPARATOR_SLASH, Strings.EMPTY);
    }

    private String parseName(Element element) {
        return element.selectFirst(SELECTOR_NAME).text();
    }

    private double parsePrice(Element element) {
        return parsePrices(element.attr(SELECTOR_PRICE));
    }

    private double parseDiscountPrice(Element element) {
        return parsePrices(element.select(SELECTOR_DISCOUNT_PRICE).text());
    }

    private double parsePrices(String price) {
        if (price.equals(REGEX_PATTERN_FREE)) return 0.0;
        StringBuilder currentPrice = new StringBuilder();
        Matcher matcher = Pattern.compile(REGEX_PATTERN_PRICE).matcher(price);
        while (matcher.find()) {
            currentPrice.append(matcher.group());
        }
        if (currentPrice.toString().equals(Strings.EMPTY)) {
            return -1;
        }
        return Double.parseDouble(currentPrice.toString()
                .replaceAll(HTML_SPACES, Strings.EMPTY).replaceAll(SEPARATOR_COMMA, SEPARATOR_DOT));
    }

    private String parseLink(String link) {
        return LINK_STORE_EPIC_GAMES + link;
    }

    private String parseImage(Element element) {
        return element
                .selectFirst(TAG_IMAGE).attr(ATTRIBUTE_DATA_IMAGE);
    }

    private Integer parseDiscountPercent(Element element) {
        return Integer.parseInt(element
                .select(SELECT_DISCOUNT_PERCENT).text()
                .replaceAll(REGEX_PATTERN_DISCOUNT_PERCENT, Strings.EMPTY));
    }

    public Game parseGame(Element element) {
        String link = parseId(element);
        GameBase gameBase = new GameBase(parseName(element), parsePrice(element), parseLink(link), parseImage(element));
        setDiscountIsNotEmpty(gameBase, element);
        return new Game(link, gameBase);
    }

    private void setDiscountIsNotEmpty(GameBase gameBase, Element element) {
        if (element.selectFirst(SELECTOR_FLAG_DISCOUNT) != null) {
            gameBase.setDiscountPrice(parseDiscountPrice(element));
            gameBase.setDiscountPercent(parseDiscountPercent(element));
        } else {
            gameBase.setDiscountPercent(0);
            gameBase.setDiscountPrice(parsePrice(element));
        }
    }
}
