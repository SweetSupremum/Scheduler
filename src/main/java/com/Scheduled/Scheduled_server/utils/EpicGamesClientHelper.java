package com.Scheduled.Scheduled_server.utils;

import com.Scheduled.Scheduled_server.model.Game;
import lombok.experimental.UtilityClass;
import org.jsoup.nodes.Element;
import org.springframework.data.util.Pair;

import java.util.List;
import java.util.function.Supplier;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.Scheduled.Scheduled_server.utils.Constants.ATTRIBUTE_HREF;
import static com.Scheduled.Scheduled_server.utils.Constants.REGEX_PATTERN_RUSSIAN_VERSION;

@UtilityClass
public class EpicGamesClientHelper {
    private final Pattern russianVersion = Pattern.compile(REGEX_PATTERN_RUSSIAN_VERSION);

    public boolean isRussianVersion(Element elementId) {
        return russianVersion
                .matcher(elementId.attr(ATTRIBUTE_HREF)).find();
    }

    public boolean isNotRussianVersion(Element elementId)
    {
        return !isRussianVersion(elementId);
    }

}
