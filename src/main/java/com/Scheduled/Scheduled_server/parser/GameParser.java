package com.Scheduled.Scheduled_server.parser;

import com.Scheduled.Scheduled_server.model.Game;
import org.apache.logging.log4j.util.Strings;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.Scheduled.Scheduled_server.utils.Constants.ATTRIBUTE_DATA_IMAGE;
import static com.Scheduled.Scheduled_server.utils.Constants.ATTRIBUTE_HREF;
import static com.Scheduled.Scheduled_server.utils.Constants.HTML_SPACES;
import static com.Scheduled.Scheduled_server.utils.Constants.LINK_STORE_EPIC_GAMES;
import static com.Scheduled.Scheduled_server.utils.Constants.NO_VALID_PRICE_GAME;
import static com.Scheduled.Scheduled_server.utils.Constants.REGEX_PATTERN_DISCOUNT_PERCENT;
import static com.Scheduled.Scheduled_server.utils.Constants.REGEX_PATTERN_FREE;
import static com.Scheduled.Scheduled_server.utils.Constants.REGEX_PATTERN_ID;
import static com.Scheduled.Scheduled_server.utils.Constants.REGEX_PATTERN_PRICE;
import static com.Scheduled.Scheduled_server.utils.Constants.SELECTOR_DISCOUNT_PRICE;
import static com.Scheduled.Scheduled_server.utils.Constants.SELECTOR_FLAG_DISCOUNT;
import static com.Scheduled.Scheduled_server.utils.Constants.SELECTOR_NAME;
import static com.Scheduled.Scheduled_server.utils.Constants.SELECTOR_PRICE;
import static com.Scheduled.Scheduled_server.utils.Constants.SELECT_DISCOUNT_PERCENT;
import static com.Scheduled.Scheduled_server.utils.Constants.SEPARATOR_COMMA;
import static com.Scheduled.Scheduled_server.utils.Constants.SEPARATOR_DOT;
import static com.Scheduled.Scheduled_server.utils.Constants.TAG_IMAGE;
import static com.Scheduled.Scheduled_server.utils.Constants.twoPoints;

@Component
public class GameParser {


    private String parseId(String link) {
        return link.replaceAll(REGEX_PATTERN_ID, Strings.EMPTY);
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

    private Double parsePrices(String price) {
        if (price.equals(REGEX_PATTERN_FREE)) return 0.0;
        StringBuilder currentPrice = new StringBuilder();
        Matcher matcher = Pattern.compile(REGEX_PATTERN_PRICE).matcher(price);
        while (matcher.find()) {
            currentPrice.append(matcher.group());
        }
        if (currentPrice.toString().equals(Strings.EMPTY)) {
            return NO_VALID_PRICE_GAME;
        }
        String finishedParsePrice = currentPrice.toString()
                .replaceAll(HTML_SPACES, Strings.EMPTY).replaceAll(SEPARATOR_COMMA, SEPARATOR_DOT);
        if (isContainsPenny(finishedParsePrice)) {
            return Double.parseDouble(finishedParsePrice.replaceFirst(SEPARATOR_DOT, Strings.EMPTY));
        }
        return Double.parseDouble(finishedParsePrice);
    }

    private boolean isContainsPenny(String finishedParsePrice) {
        int countPoints = 0;
        Matcher matcher = Pattern.compile(SEPARATOR_DOT).matcher(finishedParsePrice);
        while (matcher.find()) {
            countPoints++;
        }
        return countPoints == twoPoints;
    }

    private String getLink(Element element) {
        return element.attr(ATTRIBUTE_HREF);
    }

    private String parseImage(Element element) {
        return element.selectFirst(TAG_IMAGE).attr(ATTRIBUTE_DATA_IMAGE);
    }

    private Integer parseDiscountPercent(Element element) {
        return Integer.parseInt(element
                .select(SELECT_DISCOUNT_PERCENT).text()
                .replaceAll(REGEX_PATTERN_DISCOUNT_PERCENT, Strings.EMPTY));
    }

    public Game parseGame(Element element) {
        String link = getLink(element);

        Game game = new Game();
        game.setId(parseId(link));
        game.setName(parseName(element));
        game.setPrice(parsePrice(element));
        game.setLink(LINK_STORE_EPIC_GAMES + link);
        game.setImage(parseImage(element));
        if (element.selectFirst(SELECTOR_FLAG_DISCOUNT) != null) {
            game.setDiscountPrice(parseDiscountPrice(element));
            game.setDiscountPercent(parseDiscountPercent(element));
        } else {
            game.setDiscountPercent(0);
            game.setDiscountPrice(parsePrice(element));
        }
        return game;
    }

}
