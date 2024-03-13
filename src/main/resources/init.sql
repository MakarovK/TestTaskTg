create table if not exists minesweeper_game (
    game_id uuid primary key,
    width int not null,
    height int not null,
    mines_count int not null,
    completed boolean not null
);


comment on table minesweeper_game is 'Таблица для хранения информации об играх в "Сапёр"';

comment on column minesweeper_game.game_id is 'Уникальный идентификатор игры';
comment on column minesweeper_game.width is 'Ширина игрового поля';
comment on column minesweeper_game.height is 'Высота игрового поля';
comment on column minesweeper_game.mines_count is 'Количество мин на игровом поле';
comment on column minesweeper_game.completed is 'Флаг, указывающий, завершена ли игра';


create table if not exists field_table (
    game_id_ref uuid primary key,
    user_field text not null,
    internal_field text not null,
    foreign key (game_id_ref) references minesweeper_game(game_id)
);

comment on table field_table is 'Таблица для хранения состояния игрового поля в "Сапёр"';

comment on column field_table.game_id_ref is 'Уникальный идентификатор таблицы полей';
comment on column field_table.user_field is 'Поле, видимое пользователю';
comment on column field_table.internal_field is 'Внутреннее поле (скрытое от пользователя)';