package com.example.sudoko.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
public class Move {
    @Getter
    @Setter
    int row;
    @Getter
    @Setter
    int col;
    @Getter
    @Setter
    int num;
    public Move(int row, int col, int num)
    {
        this.row = row;
        this.col = col;
        this.num = num;
    }
    public Move()
    {

    }
}
