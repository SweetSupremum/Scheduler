package com.Scheduled.Scheduled_server.helper;

import com.Scheduled.Scheduled_server.model.Game;
import lombok.experimental.UtilityClass;

import static com.Scheduled.Scheduled_server.constants.SchedulerServiceConstants.NO_VALID_PRICE_GAME;

@UtilityClass
public class GameHelper {
    public boolean isValidGame(Game game) {
        return game.getGameBase().getPrice() != NO_VALID_PRICE_GAME;
    }
}
