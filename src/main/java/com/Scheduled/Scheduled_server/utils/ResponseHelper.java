package com.Scheduled.Scheduled_server.utils;

import com.Scheduled.Scheduled_server.dto.GameDto;
import lombok.experimental.UtilityClass;
import org.springframework.http.ResponseEntity;

import java.util.List;

@UtilityClass
public class ResponseHelper {

    public ResponseEntity<?> get(GameDto gameDto) {
        return gameDto != null ? ResponseEntity.ok(gameDto) : ResponseEntity.notFound().build();
    }

    public ResponseEntity<?> getAll(List<GameDto> gameDtos) {
        return gameDtos.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(gameDtos);
    }

    public ResponseEntity<?> delete() {
        return ResponseEntity.noContent().build();
    }
}
