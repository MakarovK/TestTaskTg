package com.tasktg.request;

import lombok.Data;

import java.util.UUID;
@Data
public class GameTurnRequest {
    private UUID game_id;
    private int col;
    private int row;
}
