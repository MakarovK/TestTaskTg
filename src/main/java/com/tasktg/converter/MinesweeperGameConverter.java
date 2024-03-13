package com.tasktg.converter;

import com.tasktg.dto.MinesweeperGameDto;
import com.tasktg.entity.MinesweeperGamePojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.tasktg.repository.MinesweeperFieldRepository;

/**
 * Класс для преобразования объектов игры "Сапёр" между сущностным и DTO представлением.
 */
@Component
public class MinesweeperGameConverter {

    @Autowired
    private MinesweeperFieldRepository minesweeperFieldRepository;

    /**
     * Преобразует сущностное представление игры "Сапёр" в DTO представление.
     *
     * @param entity Сущностное представление игры "Сапёр".
     * @return DTO представление игры "Сапёр".
     */
    public MinesweeperGameDto entityToDto(MinesweeperGamePojo entity) {
        return new MinesweeperGameDto()
                .setGame_id(entity.getGame_id())
                .setWidth(entity.getWidth())
                .setHeight(entity.getHeight())
                .setMines_count(entity.getMines_count())
                .setCompleted(entity.isCompleted())
                .setField(entity.getField().getField());
    }
}
