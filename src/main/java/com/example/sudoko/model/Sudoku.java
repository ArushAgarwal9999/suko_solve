package com.example.sudoko.model;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("singleton")
public class Sudoku {
    public final int SRN = ((Double)Math.sqrt(9)).intValue();
    public final int N = 9;
    public final int K = 15;
    public int[][] sudoku = new int[N][N];
    public int consecutiveError ;
}
