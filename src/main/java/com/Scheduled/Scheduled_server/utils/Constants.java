package com.Scheduled.Scheduled_server.utils;

public interface Constants {

    String START_URL = "https://store.epicgames.com/ru/browse?sortBy=title&sortDir=ASC&count=40&start=";
    String SELECTOR_PAGES_COUNT = "nav[data-testid=egs-pagination] ul>li:nth-last-child(2)";
    int PAGINATION_START = 0;
    int PAGINATION_STEP = 40;
    String SELECTOR_GAMES = "a.css-1jx3eyg";
    String SELECTOR_NAME = ".css-2ucwu .css-1h2ruwl";
    String SELECTOR_FLAG_DISCOUNT = ".css-1rcj98u";
    String SELECTOR_PRICE = "aria-label";
    String SELECTOR_DISCOUNT_PRICE = ".css-l24hbj .css-z3vg5b";
    String SELECTOR_RELEASED = ".css-11ksoqt";
    String LINK_STORE_EPIC_GAMES = "https://store.epicgames.com";
    String REGEX_PATTERN_FREE = "Бесплатно";
    String REGEX_PATTERN_PRICE = "(((((\\d{1,3})?(\\s|&nbsp;| )(\\d{1,3})),(\\d+)((\\s|&nbsp;| )₽$))|(0$))|((RUB)(\\s|&nbsp;| )(\\d+)[,.]?(\\d+)([,.]\\d++)?))";
    String REGEX_PATTERN_DISCOUNT_PERCENT = "-?[^0-9]+";
    String REGEX_PATTERN_ID = "(^/ru/p/)|(^/ru/)|(^/ru/bundles)";
    String HTML_SPACES = "[\\s₽&nbsp; RUB]";
    String SELECT_DISCOUNT_PERCENT = ".css-b0xoos";
    String REGEX_PATTERN_RUSSIAN_VERSION = "^/ru";
    String REGEX_PATTERN_IS_RELEASED = "(Будет доступно (\\d{2}/\\d{2}/\\d{2}))|(Скоро появится)";
    String REGEX_PATTERN_RELEASED_DATE = "(\\d{2}/\\d{2}/\\d{2})";
    String TAG_IMAGE = "img";
    String ATTRIBUTE_DATA_IMAGE = "data-image";
    String ATTRIBUTE_HREF = "href";
    String SEPARATOR_COMMA = ",";
    String SEPARATOR_DOT = "\\.";
    int TWO_POINTS = 2;
}
