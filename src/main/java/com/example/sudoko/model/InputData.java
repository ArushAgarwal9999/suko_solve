package com.example.sudoko.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
public class InputData {
    @Getter
    @Setter
    int row;
    @Getter
    @Setter
    int col;
    @Getter
    @Setter
    int value;
}
