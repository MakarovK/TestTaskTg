package com.tasktg.converter;

import com.tasktg.dto.MinesweeperFieldDto;
import com.tasktg.dto.MinesweeperGameDto;
import com.tasktg.entity.MinesweeperGamePojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.tasktg.repository.MinesweeperFieldRepository;

@Component
public class MinesweeperGameConverter {

    @Autowired
    private MinesweeperFieldRepository minesweeperFieldRepository;
    public MinesweeperGameDto entityToDto(MinesweeperGamePojo entity) {
        return new MinesweeperGameDto()
                .setGame_id(entity.getGame_id())
                .setWidth(entity.getWidth())
                .setHeight(entity.getHeight())
                .setMines_count(entity.getMines_count())
                .setCompleted(entity.isCompleted())
                .setField(entity.getField().getField());
    }

    public MinesweeperGamePojo dtoToEntity(MinesweeperGameDto dto) {
        return new MinesweeperGamePojo()
                .setGame_id(dto.getGame_id())
                .setWidth(dto.getWidth())
                .setHeight(dto.getHeight())
                .setMines_count(dto.getMines_count())
                .setCompleted(dto.isCompleted())
                .setField(minesweeperFieldRepository.getReferenceById(dto.getGame_id()));
    }
}