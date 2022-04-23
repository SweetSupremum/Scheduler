package com.Scheduled.Scheduled_server.utils;

import lombok.experimental.UtilityClass;
import org.jsoup.nodes.Element;

import java.util.regex.Pattern;

import static com.Scheduled.Scheduled_server.utils.Constants.ATTRIBUTE_HREF;
import static com.Scheduled.Scheduled_server.utils.Constants.REGEX_PATTERN_RUSSIAN_VERSION;

@UtilityClass
public class EpicGamesClientHelper {
    private final Pattern russianVersion = Pattern.compile(REGEX_PATTERN_RUSSIAN_VERSION);

    public boolean isRussianVersion(Element elementId) {
        return russianVersion
                .matcher(elementId.attr(ATTRIBUTE_HREF)).find();
    }

    public boolean isNotRussianVersion(Element elementId) {
        return !isRussianVersion(elementId);
    }

}
