package com.example.sudoko.service;

import com.example.sudoko.exceptions.InvalidInputValueException;
import com.example.sudoko.model.InputData;
import com.example.sudoko.model.StartRequest;
import com.example.sudoko.model.Sudoku;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;

@Service
public class CreateSudokuSkeleton {
    
    @Autowired
    private Sudoku sudoku;
    @Autowired
    private CheckMove checkMove;
    public void createRandomSudoku()
    {
        fillDiagonal();
        fillRemaining(0, sudoku.SRN);
        removeKDigits();
    }


    void fillDiagonal()
    {

        for (int i = 0; i<sudoku.N; i=i+sudoku.SRN)


            fillBox(i, i);
    }




    void fillBox(int row,int col)
    {
        int num;
        for (int i=0; i<sudoku.SRN; i++)
        {
            for (int j=0; j<sudoku.SRN; j++)
            {
                do
                {
                    num = randomGenerator(sudoku.N);
                }
                while (!checkMove.unUsedInBox(row, col, num));

                sudoku.sudoku[row+i][col+j] = num;
            }
        }
    }


    int randomGenerator(int num)
    {
        return (int) Math.floor((Math.random()*num+1));
    }


    boolean fillRemaining(int i, int j)
    {

        if (j>=sudoku.N && i<sudoku.N-1)
        {
            i = i + 1;
            j = 0;
        }
        if (i>=sudoku.N && j>=sudoku.N)
            return true;

        if (i < sudoku.SRN)
        {
            if (j < sudoku.SRN)
                j = sudoku.SRN;
        }
        else if (i < sudoku.N-sudoku.SRN)
        {
            if (j==(int)(i/sudoku.SRN)*sudoku.SRN)
                j =  j + sudoku.SRN;
        }
        else
        {
            if (j == sudoku.N-sudoku.SRN)
            {
                i = i + 1;
                j = 0;
                if (i>=sudoku.N)
                    return true;
            }
        }

        for (int num = 1; num<=sudoku.N; num++)
        {
            if (checkMove.checkIfSafe(i, j, num))
            {
                sudoku.sudoku[i][j] = num;
                if (fillRemaining(i, j+1))
                    return true;

                sudoku.sudoku[i][j] = 0;
            }
        }
        return false;
    }


    public void removeKDigits()
    {
        int count = sudoku.K;
        while (count != 0)
        {
            int cellId = randomGenerator(sudoku.N*sudoku.N)-1;
            int i = (cellId/sudoku.N);
            int j = cellId%9;
            if (j != 0)
                j = j - 1;
            if (sudoku.sudoku[i][j] != 0)
            {
                count--;
                sudoku.sudoku[i][j] = 0;
            }
        }
    }


    public void printSudoku()
    {
        for (int i = 0; i<sudoku.N; i++)
        {
            for (int j = 0; j<sudoku.N; j++)
                System.out.print(sudoku.sudoku[i][j] + " ");
            System.out.println();
        }
        System.out.println();
    }
    public void createUserDefineSudokuSkeleton(ArrayList<InputData> userInput)
    {

        for(InputData data : userInput)
        {
            checkMove.isValidRow(data.getRow());
            checkMove.isValidCol(data.getCol());
            checkMove.isValidNum(data.getValue());
        }
        for(int[] a: sudoku.sudoku)
        {
            Arrays.fill(a, 0);
        }
        for(InputData data : userInput)
        {
            sudoku.sudoku[data.getRow()][data.getCol()] = data.getValue();
        }


    }

    public boolean createSudokuSkeleton(StartRequest req)
    {

                if(req.getData() == null || req.getData().size() == 0)
                {
                    createRandomSudoku();
                }
                else{
                    createUserDefineSudokuSkeleton(req.getData());
                }

                printSudoku();
        return true;
    }
}
