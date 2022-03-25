package com.Scheduled.Scheduled_server.utils;


import lombok.experimental.UtilityClass;
import org.apache.logging.log4j.util.Strings;
import org.jsoup.nodes.Element;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.Scheduled.Scheduled_server.utils.SchedulerServiceConstants.ATTRIBUTE_HREF;
import static com.Scheduled.Scheduled_server.utils.SchedulerServiceConstants.REGEX_PATTERN_RUSSIAN_VERSION;

@UtilityClass
public class SchedulerHelper {

    public int pagesCount(List<String> pageList) {
        return Collections.max(Arrays.stream(pageList.toArray())
                .filter(e -> !e.toString().equals(Strings.EMPTY)).map(e -> Integer.parseInt(e.toString()))
                .collect(Collectors.toList()), Comparator.naturalOrder());
    }

    public boolean isRussianVersion(Element elementId) {
        return Pattern.compile(REGEX_PATTERN_RUSSIAN_VERSION)
                .matcher(elementId.attr(ATTRIBUTE_HREF)).find();
    }

}
