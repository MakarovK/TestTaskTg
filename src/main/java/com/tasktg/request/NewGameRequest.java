package com.tasktg.request;

import lombok.Data;

@Data
public class NewGameRequest {
    private int width;
    private int height;
    private int mines_count;
}
