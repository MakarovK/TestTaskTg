package com.tasktg.converter;

import com.tasktg.enums.MinesweeperGameCell;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Конвертер для преобразования игрового поля "Сапёр" между сущностным и строковым представлением в базе данных.
 */
@Converter
public class MinesweeperFieldConverter implements AttributeConverter<MinesweeperGameCell[][], String> {

    /**
     * Преобразует игровое поле "Сапёр" в строковое представление для сохранения в базе данных.
     *
     * @param field Игровое поле "Сапёр".
     * @return Строковое представление игрового поля "Сапёр".
     */
    @Override
    public String convertToDatabaseColumn(MinesweeperGameCell[][] field) {
        return Arrays.stream(field)
                .map(row -> Arrays.stream(row)
                        .map(MinesweeperGameCell::getValue)
                        .collect(Collectors.joining(",")))
                .collect(Collectors.joining(";"));
    }

    /**
     * Преобразует строковое представление игрового поля "Сапёр" из базы данных в сущностное представление.
     *
     * @param dbData Строковое представление игрового поля "Сапёр".
     * @return Сущностное представление игрового поля "Сапёр".
     */
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
}
