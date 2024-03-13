package com.tasktg.dto;

import com.tasktg.entity.MinesweeperFieldPojo;
import com.tasktg.enums.MinesweeperGameCell;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.UUID;

/**
 * Сущность, представляющая игру "Сапёр".
 *
 * @author Константин Макаров
 */
@Getter
@Setter
@Accessors(chain = true)
public class MinesweeperGameDto {
    /**
     * Уникальный идентификатор игры.
     */
    private UUID game_id;

    /**
     * Ширина игрового поля.
     */
    private int width;

    /**
     * Высота игрового поля.
     */
    private int height;

    /**
     * Количество мин на игровом поле.
     */
    private int mines_count;

    /**
     * Флаг, указывающий, завершена ли игра.
     */
    private boolean completed;

    /**
     * Игровое поле.
     */
    private MinesweeperGameCell[][] field;

    private String message;
}
