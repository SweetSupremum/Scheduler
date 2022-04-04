package com.Scheduled.Scheduled_server.parser;

import com.Scheduled.Scheduled_server.model.Game;
import org.apache.logging.log4j.util.Strings;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.Scheduled.Scheduled_server.utils.Constants.ATTRIBUTE_DATA_IMAGE;
import static com.Scheduled.Scheduled_server.utils.Constants.ATTRIBUTE_HREF;
import static com.Scheduled.Scheduled_server.utils.Constants.HTML_SPACES;
import static com.Scheduled.Scheduled_server.utils.Constants.LINK_STORE_EPIC_GAMES;
import static com.Scheduled.Scheduled_server.utils.Constants.REGEX_PATTERN_DISCOUNT_PERCENT;
import static com.Scheduled.Scheduled_server.utils.Constants.REGEX_PATTERN_FREE;
import static com.Scheduled.Scheduled_server.utils.Constants.REGEX_PATTERN_ID;
import static com.Scheduled.Scheduled_server.utils.Constants.REGEX_PATTERN_IS_RELEASED;
import static com.Scheduled.Scheduled_server.utils.Constants.REGEX_PATTERN_PRICE;
import static com.Scheduled.Scheduled_server.utils.Constants.REGEX_PATTERN_RELEASED_DATE;
import static com.Scheduled.Scheduled_server.utils.Constants.SELECTOR_DISCOUNT_PRICE;
import static com.Scheduled.Scheduled_server.utils.Constants.SELECTOR_FLAG_DISCOUNT;
import static com.Scheduled.Scheduled_server.utils.Constants.SELECTOR_RELEASED;
import static com.Scheduled.Scheduled_server.utils.Constants.SELECTOR_NAME;
import static com.Scheduled.Scheduled_server.utils.Constants.SELECTOR_PRICE;
import static com.Scheduled.Scheduled_server.utils.Constants.SELECT_DISCOUNT_PERCENT;
import static com.Scheduled.Scheduled_server.utils.Constants.SEPARATOR_COMMA;
import static com.Scheduled.Scheduled_server.utils.Constants.SEPARATOR_DOT;
import static com.Scheduled.Scheduled_server.utils.Constants.TAG_IMAGE;
import static com.Scheduled.Scheduled_server.utils.Constants.TWO_POINTS;

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
            return -1.0;
        }
        String finishedParsePrice = currentPrice.toString()
                .replaceAll(HTML_SPACES, Strings.EMPTY).replaceAll(SEPARATOR_COMMA, SEPARATOR_DOT);
        if (isContainsPenny(finishedParsePrice)) {
            return Double.parseDouble(finishedParsePrice.replaceFirst(SEPARATOR_DOT, Strings.EMPTY));
        }
        return Double.parseDouble(finishedParsePrice);
    }

    private boolean isReleased(Element element, Game game) {
        return !Pattern.compile(REGEX_PATTERN_IS_RELEASED)
                .matcher(getReleased(element))
                .find() && game.getReleasedDate() == null;
    }

    private String getReleased(Element element) {
        return element.select(SELECTOR_RELEASED).text();
    }

    private LocalDate parseReleasedDate(Element element) {

        Matcher matcher = Pattern.compile(REGEX_PATTERN_RELEASED_DATE).matcher(getReleased(element));

        StringBuilder dateBuilder = new StringBuilder();
        while (matcher.find()) {
            dateBuilder.append(matcher.group());
        }
        String date = dateBuilder.toString();

        if (date.equals(Strings.EMPTY)) return null;

        return LocalDate.parse(date, DateTimeFormatter.ofPattern("MM/dd/yy"));
    }

    //Select * From Game where name='Evil Dead: The Game' or name = 'F1® Manager 2022'
    private boolean isContainsPenny(String finishedParsePrice) {
        int countPoints = 0;
        Matcher matcher = Pattern.compile(SEPARATOR_DOT).matcher(finishedParsePrice);
        while (matcher.find()) {
            countPoints++;
        }
        return countPoints == TWO_POINTS;
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
        game.setReleasedDate(parseReleasedDate(element));
        game.setReleased(isReleased(element, game));

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
