package com.Scheduled.Scheduled_server.helper;


import com.Scheduled.Scheduled_server.model.Game;
import lombok.experimental.UtilityClass;
import org.jsoup.nodes.Element;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.Scheduled.Scheduled_server.constants.SchedulerServiceConstants.htmlSpaces;
import static com.Scheduled.Scheduled_server.constants.SchedulerServiceConstants.linkStoreEpicGames;
import static com.Scheduled.Scheduled_server.constants.SchedulerServiceConstants.regexPatternDiscountPercent;
import static com.Scheduled.Scheduled_server.constants.SchedulerServiceConstants.regexPatternFree;
import static com.Scheduled.Scheduled_server.constants.SchedulerServiceConstants.regexPatternPrice;
import static com.Scheduled.Scheduled_server.constants.SchedulerServiceConstants.selectDiscountPercent;
import static com.Scheduled.Scheduled_server.constants.SchedulerServiceConstants.selectorDiscountPrice;
import static com.Scheduled.Scheduled_server.constants.SchedulerServiceConstants.selectorFlagDiscount;
import static com.Scheduled.Scheduled_server.constants.SchedulerServiceConstants.selectorName;
import static com.Scheduled.Scheduled_server.constants.SchedulerServiceConstants.selectorPrice;

@UtilityClass
public class SchedulerHelper {
    public int pagesCount(List<String> pageList) {
        return Collections.max(Arrays.stream(pageList.toArray())
                .filter(e -> !e.toString().equals("")).map(e -> Integer.parseInt(e.toString()))
                .collect(Collectors.toList()), Comparator.naturalOrder());
    }
    public String parseId(Element id) {
        return id.attr("href").replaceFirst("/", "");
    }

    public String parseName(Element name) {
        return name.selectFirst(selectorName).text();
    }

    public Double parsePrice(Element priceElement) {
        return parsePrices(priceElement.attr(selectorPrice));
    }

    public Double parseDiscountPrice(Element discountPriceElement) {
        return parsePrices(discountPriceElement.select(selectorDiscountPrice).text());
    }

    private static Double parsePrices(String price) {
        if (price.equals(regexPatternFree)) return 0.0;
        StringBuilder currentPrice = new StringBuilder();
        Matcher matcher = Pattern.compile(regexPatternPrice).matcher(price);
        while (matcher.find()) {
            currentPrice.append(matcher.group());
        }
        return Double.parseDouble(currentPrice.toString()
                .replaceAll(htmlSpaces, "").replaceAll(",", "."));
    }
    public String parseLink(String link) {
        return linkStoreEpicGames + link;
    }

    public String parseImage(Element image) {
        return image.selectFirst("img").attr("data-image");
    }

    public Integer parseDiscountPercent(Element discountPercent) {
        return Integer.parseInt(discountPercent
                .select(selectDiscountPercent).text().replaceAll(regexPatternDiscountPercent, ""));
    }

    public Game parseGame(Element element) {
        String link = parseId(element);
        Game game = new Game(parseId(element), parseName(element), parsePrice(element), parseLink(link), parseImage(element));
        if (element.selectFirst(selectorFlagDiscount) != null) {
            game.setDiscountPrice(parseDiscountPrice(element));
            game.setDiscountPercent(parseDiscountPercent(element));
        }
        return game;
    }
}