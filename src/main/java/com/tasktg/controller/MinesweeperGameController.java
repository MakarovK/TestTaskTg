package com.tasktg.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tasktg.request.GameTurnRequest;
import com.tasktg.request.NewGameRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.tasktg.service.MinesweeperGameService;

import java.util.Collections;

/**
 * Контроллер для управления игровым процессом "Сапёр".
 *
 *  @author Константин Макаров
 */
@RestController
@RequestMapping(value = "/api")
public class MinesweeperGameController {
    private final MinesweeperGameService minesweeperGameService;

    @Autowired
    public MinesweeperGameController(MinesweeperGameService minesweeperGameService) {
        this.minesweeperGameService = minesweeperGameService;
    }

    /**
     * Создает новую игру "Сапёр".
     *
     * @param newGameRequest Запрос на создание новой игры.
     * @return Ответ с информацией о новой игре в формате JSON.
     */
    @CrossOrigin(origins = "https://minesweeper-test.studiotg.ru/")
    @PostMapping("/new")
    public ResponseEntity<?> createNewGame(@RequestBody NewGameRequest newGameRequest) {
        try {
            String jsonNewGame = minesweeperGameService.createGame(newGameRequest.getWidth(), newGameRequest.getHeight(), newGameRequest.getMines_count());
            return ResponseEntity.ok(jsonNewGame);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("error", e.getMessage()));
        }
    }

    /**
     * Выполняет ход в игре "Сапёр".
     *
     * @param gameTurnRequest Запрос на выполнение хода в игре.
     * @return Ответ с результатом хода в формате JSON.
     */
    @CrossOrigin(origins = "https://minesweeper-test.studiotg.ru/")
    @PostMapping("/turn")
    public ResponseEntity<?> turnInGame(@RequestBody GameTurnRequest gameTurnRequest) {
        try {
            String jsonTurn = minesweeperGameService.turn(gameTurnRequest.getGame_id(),
                    gameTurnRequest.getCol(),
                    gameTurnRequest.getRow());
            return ResponseEntity.ok(jsonTurn);
        } catch (RuntimeException | JsonProcessingException e) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("error", e.getMessage()));
        }
    }
}
