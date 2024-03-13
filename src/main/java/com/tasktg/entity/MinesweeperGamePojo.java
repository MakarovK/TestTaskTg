package com.tasktg.entity;

import jakarta.persistence.*;
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
@Entity
@Table(name = "minesweeper_game")
public class MinesweeperGamePojo {
    /**
     * Уникальный идентификатор игры.
     */
    @Id
    private UUID game_id;

    /**
     * Ширина игрового поля.
     */
    @Column(name = "width")
    private int width;

    /**
     * Высота игрового поля.
     */
    @Column(name = "height")
    private int height;

    /**
     * Количество мин на игровом поле.
     */
    @Column(name = "mines_count")
    private int mines_count;

    /**
     * Флаг, указывающий, завершена ли игра.
     */
    @Column(name = "completed")
    private boolean completed;

    /**
     * Игровое поле.
     */
    @OneToOne
    @JoinColumn(name = "game_id")
    private MinesweeperFieldPojo field;
}
