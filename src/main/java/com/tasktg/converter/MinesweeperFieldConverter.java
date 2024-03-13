package com.tasktg.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tasktg.dto.MinesweeperFieldDto;
import com.tasktg.entity.MinesweeperFieldPojo;
import com.tasktg.enums.MinesweeperGameCell;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.Arrays;
import java.util.stream.Collectors;

@Converter
public class MinesweeperFieldConverter implements AttributeConverter<MinesweeperGameCell[][], String> {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(MinesweeperGameCell[][] field) {
        return Arrays.stream(field)
                .map(row -> Arrays.stream(row)
                        .map(MinesweeperGameCell::getValue)
                        .collect(Collectors.joining(",")))
                .collect(Collectors.joining(";"));
    }

    @Override
    public MinesweeperGameCell[][] convertToEntityAttribute(String dbData) {
        String[] rows = dbData.split(";");
        MinesweeperGameCell[][] field = new MinesweeperGameCell[rows.length][];
        for (int i = 0; i < rows.length; i++) {
            String[] values = rows[i].split(",");
            field[i] = new MinesweeperGameCell[values.length];
            for (int j = 0; j < values.length; j++) {
                field[i][j] = MinesweeperGameCell.getByValue(values[j]);
            }
        }
        return field;
    }

    public MinesweeperFieldDto entityToDto(MinesweeperFieldPojo entity) {
        return new MinesweeperFieldDto()
                .setGame_id_ref(entity.getGame_id_ref())
                .setField(entity.getField());
    }
}
