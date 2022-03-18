package com.Scheduled.Scheduled_server.helper;


import com.Scheduled.Scheduled_server.model.Game;
import lombok.experimental.UtilityClass;

@UtilityClass
public class GameHelper {
    public Game updateGame(Game oldData, Game newData) {
        oldData.setName(newData.getName());
        oldData.setPrice(newData.getPrice());
        oldData.setDiscountPrice(newData.getDiscountPrice());
        oldData.setReference(newData.getReference());
        return oldData;
    }
}