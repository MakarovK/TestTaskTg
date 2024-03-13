package com.tasktg.enums;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Перечисление, представляющее возможные значения ячеек игры "Сапёр".
 */
public enum MinesweeperGameCell {
    EMPTY(" "),              // Пустая ячейка
    ZERO("0"),               // Ноль мин вокруг ячейки
    ONE("1"),                // Одна мина вокруг ячейки
    TWO("2"),                // Две мины вокруг ячейки
    THREE("3"),              // Три мины вокруг ячейки
    FOUR("4"),               // Четыре мины вокруг ячейки
    FIVE("5"),               // Пять мин вокруг ячейки
    SIX("6"),                // Шесть мин вокруг ячейки
    SEVEN("7"),              // Семь мин вокруг ячейки
    EIGHT("8"),              // Восемь мин вокруг ячейки
    MINE("M"),               // Ячейка содержит мину
    EXPLODED_MINE("X");      // Ячейка с подорванной миной

    private final String value;

    /**
     * Конструктор для создания объекта перечисления с указанным значением.
     *
     * @param value строковое представление значения ячейки
     */
    MinesweeperGameCell(String value) {
        this.value = value;
    }

    /**
     * Возвращает строковое представление значения ячейки.
     *
     * @return строковое представление значения ячейки
     */
    @JsonValue
    public String getValue() {
        return value;
    }

    /**
     * Получает экземпляр перечисления по его строковому значению.
     *
     * @param value строковое значение ячейки
     * @return экземпляр перечисления
     * @throws IllegalArgumentException если указанное значение не соответствует ни одному из значений перечисления
     */
    public static MinesweeperGameCell getByValue(String value) {
        for (MinesweeperGameCell cell : values()) {
            if (cell.value.equals(value)) {
                return cell;
            }
        }
        throw new IllegalArgumentException("Нет значения: " + value);
    }
}
