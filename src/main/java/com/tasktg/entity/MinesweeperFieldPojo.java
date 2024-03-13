package com.tasktg.entity;

import com.tasktg.converter.MinesweeperFieldConverter;
import com.tasktg.enums.MinesweeperGameCell;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Random;
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
@Accessors(chain = true)
@Entity
@Table(name = "field_table")
public class MinesweeperFieldPojo {
    /**
     * Уникальный идентификатор игры, к которой относится данное игровое поле.
     */
    @Id
    private UUID game_id_ref;

    /**
     * Двумерный массив ячеек игрового поля.
     */
    @Convert(converter = MinesweeperFieldConverter.class)
    @Column(name = "user_field", length = 20000)
    private MinesweeperGameCell[][] field;

    /**
     * Дополнительный массив для хранения внутренних данных о состоянии ячеек.
     * Этот массив не виден для пользователя.
     */
    @Convert(converter = MinesweeperFieldConverter.class)
    @Column(name = "internal_field", length = 20000)
    private MinesweeperGameCell[][] hiddenField;

    /**
     * Конструктор по умолчанию.
     */
    public MinesweeperFieldPojo() {
    }

    /**
     * Конструктор для создания игрового поля заданных размеров и с указанным количеством мин.
     *
     * @param width      ширина игрового поля
     * @param height     высота игрового поля
     * @param minesCount количество мин, которые нужно расположить на поле
     */
    public MinesweeperFieldPojo(int width, int height, int minesCount) {
        field = new MinesweeperGameCell[height][width];
        hiddenField = new MinesweeperGameCell[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                field[i][j] = MinesweeperGameCell.EMPTY;
                hiddenField[i][j] = MinesweeperGameCell.EMPTY;
            }
        }

        placeMines(width, height, minesCount);
        countCell(width, height);
    }

    /**
     * Размещает мины на поле.
     *
     * @param width      ширина игрового поля
     * @param height     высота игрового поля
     * @param minesCount количество мин, которые нужно разместить
     */
    private void placeMines(int width, int height, int minesCount) {
        Random random = new Random();
        int minesPlaced = 0;
        while (minesPlaced < minesCount) {
            int row = random.nextInt(height);
            int col = random.nextInt(width);
            if (hiddenField[row][col] != MinesweeperGameCell.MINE) {
                hiddenField[row][col] = MinesweeperGameCell.MINE;
                minesPlaced++;
            }
        }
    }

    /**
     * Подсчитывает количество мин вокруг каждой ячейки.
     *
     * @param width  ширина игрового поля
     * @param height высота игрового поля
     */
    private void countCell(int width, int height) {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (hiddenField[i][j] != MinesweeperGameCell.MINE) {
                    int minesAround = countMinesAround(i, j);
                    hiddenField[i][j] = MinesweeperGameCell.getByValue(Integer.toString(minesAround));
                }
            }
        }
    }

    /**
     * Подсчитывает количество мин вокруг заданной ячейки.
     *
     * @param i координата строки ячейки
     * @param j координата столбца ячейки
     * @return количество мин вокруг заданной ячейки
     */
    private int countMinesAround(int i, int j) {
        int count = 0;
        int height = hiddenField.length;
        int width = hiddenField[0].length;

        for (int r = Math.max(0, i - 1); r <= Math.min(i + 1, height - 1); r++) {
            for (int c = Math.max(0, j - 1); c <= Math.min(j + 1, width - 1); c++) {
                if (hiddenField[r][c] == MinesweeperGameCell.MINE) {
                    count++;
                }
            }
        }

        return count;
    }

    /**
     * Получает значение ячейки игрового поля по заданным координатам.
     *
     * @param row номер строки
     * @param col номер столбца
     * @return значение ячейки игрового поля
     */
    public MinesweeperGameCell getCell(int row, int col) {
        return field[row][col];
    }

    public MinesweeperGameCell getHiddenCell(int row, int col) {
        return hiddenField[row][col];
    }
}
