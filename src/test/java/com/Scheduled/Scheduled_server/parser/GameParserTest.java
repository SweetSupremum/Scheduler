package com.Scheduled.Scheduled_server.parser;

import com.Scheduled.Scheduled_server.model.Game;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import static com.Scheduled.Scheduled_server.utils.Constants.SELECTOR_GAMES;


@ExtendWith(SpringExtension.class)
@SpringBootTest
class GameParserTest {
    @Autowired
    public GameParser gameParser;

    @Test
    void parseGame() throws IOException {

        File file = new File("C:\\Users\\alexey\\IdeaProjects\\Scheduler-dev\\src\\test\\resources\\epic_DOM.html");
        Scanner scanner = new Scanner(file);
        StringBuilder htmlEpic = new StringBuilder();
        while (scanner.hasNext()) {
            htmlEpic.append(scanner.nextLine());
        }

        Document document = Jsoup.parse(htmlEpic.toString());
        Elements elements = document.select(SELECTOR_GAMES);
        List<Game> games = elements.stream().map(gameParser::parseGame).collect(Collectors.toList());
        Assert.assertTrue(games.contains(new Game("crash-drive-3", "Crash Drive 3"
                , 449.00
                , "https://store.epicgames.com/ru/p/crash-drive-3"
                , "https://cdn1.epicgames.com/salesEvent/salesEvent/eacae17c-6ea9-47bb-964f-4734c76d17e7_" +
                "1200x1600-8d431f6886ff29e953378da9d48a45b6?h=854&resize=1&w=640",
                449.00,
                0,
                true, null, false)));

    }
}