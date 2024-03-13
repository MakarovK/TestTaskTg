package com.tasktg.dto;

import com.tasktg.enums.MinesweeperGameCell;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.UUID;

/**
 * Сущность, представляющая игровое поле для игры "Сапёр".
 *
 * <p>Этот класс содержит методы для работы с ячейками игрового поля и инициализации поля.</p>
 *
 * @author Константин Макаров
 */
@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
public class MinesweeperFieldDto {
    /**
     * Уникальный идентификатор игры, к которой относится данное игровое поле.
     */
    private UUID game_id_ref;

    /**
     * Двумерный массив ячеек игрового поля.
     */
    private MinesweeperGameCell[][] field;
    /**
     * Дополнительный массив для хранения внутренних данных о состоянии ячеек.
     * Этот массив не виден для пользователя.
     */

}
