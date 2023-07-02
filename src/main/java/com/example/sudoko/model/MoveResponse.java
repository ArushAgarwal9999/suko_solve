package com.example.sudoko.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
public class MoveResponse {
    @Getter
    @Setter
    String result;
    @Getter
    @Setter
    Move hint;
}
