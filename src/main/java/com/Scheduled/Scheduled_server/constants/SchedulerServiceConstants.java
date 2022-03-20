package com.Scheduled.Scheduled_server.constants;

public interface SchedulerServiceConstants {
    String startUrl = "https://store.epicgames.com/ru/browse?sortBy=releaseDate&sortDir=DESC&count=40&start=";
    String selectorFooterLi = "li.css-1rf09kn>a";
    int paginationStart = 0;
    int paginationStep = 40;
    String selectorGames = "a.css-1jx3eyg";
    String selectorName = ".css-2ucwu .css-1h2ruwl";
    String selectorFlagDiscount = ".css-1rcj98u";
    String selectorPrice = "aria-label";
    String selectorDiscountPrice = ".css-l24hbj .css-z3vg5b";
    String linkStoreEpicGames = "https://store.epicgames.com/";
    String regexPatternFree = "Бесплатно";
    String regexPatternPrice = "(((((\\d{1,3})?(\\s|&nbsp;| )(\\d{1,3})),(\\d+)((\\s|&nbsp;| )₽$))|(0$))" +
            "|((RUB)(\\s|&nbsp;| )(\\d+)[,.]?(\\d+)))";
    String regexPatternDiscountPercent = "-?[^0-9]+";
    String htmlSpaces = "[\\s₽&nbsp; RUB]";
    String selectDiscountPercent = ".css-b0xoos";

}
