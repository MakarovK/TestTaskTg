package com.tasktg.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tasktg.converter.MinesweeperGameConverter;
import com.tasktg.entity.MinesweeperFieldPojo;
import com.tasktg.entity.MinesweeperGamePojo;
import com.tasktg.enums.MinesweeperGameCell;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.tasktg.repository.MinesweeperFieldRepository;
import com.tasktg.repository.MinesweeperGameRepository;

import java.util.UUID;
/**
 * Сервис для управления игрой "Сапёр".
 *
 * <p>Этот сервис обеспечивает создание новых игр, а также обработку ходов в существующих играх.</p>
 *
 * @author Константин Макаров
 */
@Service
public class MinesweeperGameService {
    /**
     * ObjectMapper для преобразования объектов в JSON и обратно.
     */
    @Autowired
    private ObjectMapper objectMapper;

    /**
     * Репозиторий для доступа к данным игры "Сапёр".
     */
    @Autowired
    private MinesweeperGameRepository minesweeperGameRepository;

    /**
     * Репозиторий для доступа к данным игрового поля "Сапёр".
     */
    @Autowired
    private MinesweeperFieldRepository minesweeperFieldRepository;

    /**
     * Конвертер для преобразования между сущностями и DTO игры "Сапёр".
     */
    @Autowired
    private MinesweeperGameConverter minesweeperGameConverter;

    /**
     * Создаёт новую игру "Сапёр" с указанными параметрами.
     *
     * @param width      ширина игрового поля
     * @param height     высота игрового поля
     * @param minesCount количество мин на игровом поле
     * @return JSON-строка, представляющая новую игру
     * @throws RuntimeException если параметры игры некорректны или произошла ошибка при сохранении данных
     */
    public String createGame(int width, int height, int minesCount) {
        if (width > 30) {
            throw new RuntimeException("ширина поля должна быть не менее 2 и не более 30");
        }
        if (height > 30) {
            throw new RuntimeException("высота поля должна быть не менее 2 и не более 30");
        }
        if (minesCount > (width * height - 1)) {
            throw new RuntimeException("количество мин должно быть не менее 1 и не более " + (width * height - 1));
        }

        UUID gameId = UUID.randomUUID();
        MinesweeperFieldPojo field = new MinesweeperFieldPojo(width, height, minesCount)
                .setGame_id_ref(gameId);
        MinesweeperGamePojo minesweeperGamePojo = new MinesweeperGamePojo()
                .setGame_id(gameId)
                .setWidth(width)
                .setHeight(height)
                .setMines_count(minesCount)
                .setCompleted(false);

        minesweeperGameRepository.save(minesweeperGamePojo);
        minesweeperFieldRepository.save(field);

        minesweeperGamePojo.setField(field);
        try {
            return objectMapper.writeValueAsString(minesweeperGameConverter.entityToDto(minesweeperGamePojo));
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Произошла непредвиденная ошибка");
        }
    }

    /**
     * Выполняет ход в игре "Сапёр" с указанными координатами.
     *
     * @param game_id уникальный идентификатор игры
     * @param col     столбец, в который совершается ход
     * @param row     строка, в которую совершается ход
     * @return JSON-строка, представляющая результат хода
     * @throws RuntimeException если игра не найдена, завершена, или произошла ошибка при сохранении данных
     * @throws JsonProcessingException если возникла ошибка при преобразовании объекта в JSON
     */
    public String turn(UUID game_id, int col, int row) throws JsonProcessingException {

        MinesweeperGamePojo game = minesweeperGameRepository.findById(game_id)
                .orElseThrow(() -> new RuntimeException("Игра с айди: " + game_id + " не найдена"));

        if (game.isCompleted()) {
            throw new RuntimeException("игра завершена");
        }

        if (row < 0 || row >= game.getHeight() || col < 0 || col >= game.getWidth()) {
            return objectMapper.writeValueAsString(minesweeperGameConverter.entityToDto(game));
        }

        MinesweeperFieldPojo minesweeperFieldPojo = minesweeperFieldRepository.findById(game.getGame_id())
                .orElseThrow(() -> new RuntimeException("Игровое поле с айди: " + game.getGame_id() + " не найдено"));
        game.setField(minesweeperFieldPojo);

        MinesweeperFieldPojo field = game.getField();

        MinesweeperGameCell cell = field.getCell(row, col);
        MinesweeperGameCell hiddenCell = field.getHiddenCell(row, col);

        if (cell != MinesweeperGameCell.EMPTY) {
            throw new RuntimeException("уже открытая ячейка");
        }
        if (hiddenCell == MinesweeperGameCell.MINE) {
            showAllExplodedMine(field);
            game.setCompleted(true);
            game.setField(field);
            minesweeperGameRepository.save(game);
            return objectMapper.writeValueAsString(minesweeperGameConverter.entityToDto(game));

        }

        openCell(field, row, col);
        game.setField(field);

        if (checkVictory(minesweeperFieldPojo)){
            showAllMine(field);
            game.setCompleted(true);
            game.setField(field);
            minesweeperGameRepository.save(game);
            return objectMapper.writeValueAsString(minesweeperGameConverter.entityToDto(game));
        }
        minesweeperGameRepository.save(game);
        minesweeperFieldRepository.save(field);

        try {
            return objectMapper.writeValueAsString(minesweeperGameConverter.entityToDto(game));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * Отображает все бомбы на игровом поле. Метод для поражения в игре
     *
     * @param field игровое поле, на котором отображаются бомбы
     */
    private void showAllExplodedMine(MinesweeperFieldPojo field) {
        field.setField(field.getHiddenField());
        for (int i = 0; i < field.getField().length; i++) {
            for (int j = 0; j < field.getField()[0].length; j++) {
                if(field.getCell(i, j) == MinesweeperGameCell.MINE) {
                    field.getField()[i][j] = MinesweeperGameCell.EXPLODED_MINE;
                }
            }
        }
    }
    /**
     * Отображает все бомбы на игровом поле. Метод для победы в игре
     *
     * @param field игровое поле, на котором отображаются бомбы
     */
    private void showAllMine(MinesweeperFieldPojo field) {
        field.setField(field.getHiddenField());
    }

    /**
     * Открывает клетку на игровом поле с указанными координатами.
     *
     * @param field игровое поле, на котором открывается клетка
     * @param row   строка клетки
     * @param col   столбец клетки
     */
    private void openCell(MinesweeperFieldPojo field, int row, int col) {
        if (row < 0 || row >= field.getField().length || col < 0 || col >= field.getField()[0].length) {
            return;
        }
        if (field.getCell(row, col) != MinesweeperGameCell.EMPTY) {
            return;
        }
        field.getField()[row][col] = field.getHiddenCell(row, col);
        if (field.getHiddenCell(row, col) == MinesweeperGameCell.ZERO) {
            for (int i = row - 1; i <= row + 1; i++) {
                for (int j = col - 1; j <= col + 1; j++) {
                    if (i != row || j != col) {
                        openCell(field, i, j);
                    }
                }
            }
        }
    }
    /**
     * Проверяет, достигнута ли победа в игре.
     * Победа достигается, если все клетки, кроме мин, открыты.
     *
     * @param field объект MinesweeperFieldPojo, представляющий игровое поле
     * @return true, если победа достигнута, false в противном случае
     */
    private boolean checkVictory(MinesweeperFieldPojo field) {
        int emptyCells = 0;
        int mineCells = 0;
        for (int i = 0; i < field.getField().length; i++) {
            for (int j = 0; j < field.getField()[0].length; j++) {
                if (field.getCell(i, j) == MinesweeperGameCell.EMPTY) {
                    emptyCells++;
                }
                if (field.getHiddenCell(i, j) == MinesweeperGameCell.MINE) {
                    mineCells++;
                }
            }
        }
        return emptyCells == mineCells;
    }
}


