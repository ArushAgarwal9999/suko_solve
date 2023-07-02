package com.example.sudoko.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("singleton")
@ToString
public class Sudoku {
    @Getter
    @Setter
    private boolean isGameStart = false;
    @Getter
    @Setter
    private boolean isGameEnd = false;
    @Getter
    @Setter
    private  int SRN = ((Double)Math.sqrt(9)).intValue();
    @Getter
    @Setter
    private  int N = 9;
    @Getter
    @Setter
    private  int K = 15;
    @Getter
    @Setter
    private int[][] sudoku = new int[N][N];
    @Getter
    @Setter
    private int consecutiveError ;
    public void printSudoku()
    {
        for (int i = 0; i<N; i++)
        {
            for (int j = 0; j<N; j++)
                System.out.print(sudoku[i][j] + " ");
            System.out.println();
        }
        System.out.println();
    }
}
