package com.Scheduled.Scheduled_server.parser;

import com.Scheduled.Scheduled_server.model.Game;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.jsoup.nodes.Element;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.Scheduled.Scheduled_server.constants.SchedulerServiceConstants.ATTRIBUTE_DATA_IMAGE;
import static com.Scheduled.Scheduled_server.constants.SchedulerServiceConstants.ATTRIBUTE_HREF;
import static com.Scheduled.Scheduled_server.constants.SchedulerServiceConstants.HTML_SPACES;
import static com.Scheduled.Scheduled_server.constants.SchedulerServiceConstants.LINK_STORE_EPIC_GAMES;
import static com.Scheduled.Scheduled_server.constants.SchedulerServiceConstants.REGEX_PATTERN_DISCOUNT_PERCENT;
import static com.Scheduled.Scheduled_server.constants.SchedulerServiceConstants.REGEX_PATTERN_FREE;
import static com.Scheduled.Scheduled_server.constants.SchedulerServiceConstants.REGEX_PATTERN_PRICE;
import static com.Scheduled.Scheduled_server.constants.SchedulerServiceConstants.SELECTOR_DISCOUNT_PRICE;
import static com.Scheduled.Scheduled_server.constants.SchedulerServiceConstants.SELECTOR_FLAG_DISCOUNT;
import static com.Scheduled.Scheduled_server.constants.SchedulerServiceConstants.SELECTOR_NAME;
import static com.Scheduled.Scheduled_server.constants.SchedulerServiceConstants.SELECTOR_PRICE;
import static com.Scheduled.Scheduled_server.constants.SchedulerServiceConstants.SELECT_DISCOUNT_PERCENT;
import static com.Scheduled.Scheduled_server.constants.SchedulerServiceConstants.SEPARATOR_COMMA;
import static com.Scheduled.Scheduled_server.constants.SchedulerServiceConstants.SEPARATOR_DOT;
import static com.Scheduled.Scheduled_server.constants.SchedulerServiceConstants.SEPARATOR_SLASH;
import static com.Scheduled.Scheduled_server.constants.SchedulerServiceConstants.TAG_IMAGE;

@AllArgsConstructor
public class GameParser {
    Element element;

    private String parseId() {
        return element.attr(ATTRIBUTE_HREF)
                .replaceFirst(SEPARATOR_SLASH, Strings.EMPTY);
    }

    private String parseName() {
        return element.selectFirst(SELECTOR_NAME).text();
    }

    private double parsePrice() {
        return parsePrices(element.attr(SELECTOR_PRICE));
    }

    private double parseDiscountPrice() {
        return parsePrices(element.select(SELECTOR_DISCOUNT_PRICE).text());
    }

    private double parsePrices(String price) {
        if (price.equals(REGEX_PATTERN_FREE)) return 0.0;
        StringBuilder currentPrice = new StringBuilder();
        Matcher matcher = Pattern.compile(REGEX_PATTERN_PRICE).matcher(price);
        while (matcher.find()) {
            currentPrice.append(matcher.group());
        }
        return Double.parseDouble(currentPrice.toString()
                .replaceAll(HTML_SPACES, Strings.EMPTY).replaceAll(SEPARATOR_COMMA, SEPARATOR_DOT));
    }

    private String parseLink(String link) {
        return LINK_STORE_EPIC_GAMES + link;
    }

    private String parseImage() {
        return element
                .selectFirst(TAG_IMAGE).attr(ATTRIBUTE_DATA_IMAGE);
    }

    public Integer parseDiscountPercent() {
        return Integer.parseInt(element
                .select(SELECT_DISCOUNT_PERCENT).text()
                .replaceAll(REGEX_PATTERN_DISCOUNT_PERCENT, Strings.EMPTY));
    }

    public Game parseGame() {
        String link = parseId();
        Game game = new Game(link, parseName(), parsePrice(), parseLink(link), parseImage());
        setDiscountIsNotEmpty(game);
        return game;
    }

    private void setDiscountIsNotEmpty(Game game) {
        if (element.selectFirst(SELECTOR_FLAG_DISCOUNT) != null) {
            game.setDiscountPrice(parseDiscountPrice());
            game.setDiscountPercent(parseDiscountPercent());
        }
    }
}
