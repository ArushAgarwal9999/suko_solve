package com.example.sudoko.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;

@ToString
public class StartRequest {
    @Getter
    @Setter
    String move;
    @Getter
    @Setter
    ArrayList<InputData> data;

}
