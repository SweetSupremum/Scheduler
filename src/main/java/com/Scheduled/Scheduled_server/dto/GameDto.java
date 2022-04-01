package com.Scheduled.Scheduled_server.dto;

import com.Scheduled.Scheduled_server.model.GameBase;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GameDto {
    private String id;
    private GameBase gameBase;
    private boolean inLibrary;
}
