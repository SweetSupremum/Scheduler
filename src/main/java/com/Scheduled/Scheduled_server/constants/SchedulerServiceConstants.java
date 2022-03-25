package com.Scheduled.Scheduled_server.constants;

public interface SchedulerServiceConstants {
    String START_URL = "https://store.epicgames.com/ru/browse?sortBy=releaseDate&sortDir=DESC&count=40&start=";
    String SELECTOR_FOOTER_LI = "li.css-1rf09kn>a";
    int PAGINATION_START = 0;
    int PAGINATION_STEP = 40;
    String SELECTOR_GAMES = "a.css-1jx3eyg";
    String SELECTOR_NAME = ".css-2ucwu .css-1h2ruwl";
    String SELECTOR_FLAG_DISCOUNT = ".css-1rcj98u";
    String SELECTOR_PRICE = "aria-label";
    String SELECTOR_DISCOUNT_PRICE = ".css-l24hbj .css-z3vg5b";
    String LINK_STORE_EPIC_GAMES = "https://store.epicgames.com/";
    String REGEX_PATTERN_FREE = "Бесплатно";
    String REGEX_PATTERN_PRICE = "(((((\\d{1,3})?(\\s|&nbsp;| )(\\d{1,3})),(\\d+)((\\s|&nbsp;| )₽$))|(0$))|((RUB)(\\s|&nbsp;| )(\\d+)[,.]?(\\d+)))";
    String REGEX_PATTERN_DISCOUNT_PERCENT = "-?[^0-9]+";
    String HTML_SPACES = "[\\s₽&nbsp; RUB]";
    String SELECT_DISCOUNT_PERCENT = ".css-b0xoos";
    String REGEX_PATTERN_RUSSIAN_VERSION = "^/ru";
    String TAG_IMAGE = "img";
    String ATTRIBUTE_DATA_IMAGE = "data-image";
    String ATTRIBUTE_HREF = "href";
    String SEPARATOR_SLASH = "/";
    String SEPARATOR_COMMA = ",";
    String SEPARATOR_DOT = ".";
    int NO_VALID_PRICE_GAME = -1;

}
