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
        fillRemaining(0, sudoku.getSRN());
        removeKDigits();
    }


    void fillDiagonal()
    {

        for (int i = 0; i<sudoku.getN(); i=i+sudoku.getSRN())


            fillBox(i, i);
    }




    void fillBox(int row,int col)
    {
        int num;
        for (int i=0; i<sudoku.getSRN(); i++)
        {
            for (int j=0; j<sudoku.getSRN(); j++)
            {
                do
                {
                    num = randomGenerator(sudoku.getN());
                }
                while (!checkMove.unUsedInBox(row, col, num));

                sudoku.getSudoku()[row+i][col+j] = num;
            }
        }
    }


    int randomGenerator(int num)
    {
        return (int) Math.floor((Math.random()*num+1));
    }


    boolean fillRemaining(int i, int j)
    {

        if (j>=sudoku.getN() && i<sudoku.getN()-1)
        {
            i = i + 1;
            j = 0;
        }
        if (i>=sudoku.getN() && j>=sudoku.getN())
            return true;

        if (i < sudoku.getSRN())
        {
            if (j < sudoku.getSRN())
                j = sudoku.getSRN();
        }
        else if (i < sudoku.getN()-sudoku.getSRN())
        {
            if (j==(int)(i/sudoku.getSRN())*sudoku.getSRN())
                j =  j + sudoku.getSRN();
        }
        else
        {
            if (j == sudoku.getN()-sudoku.getSRN())
            {
                i = i + 1;
                j = 0;
                if (i>=sudoku.getN())
                    return true;
            }
        }

        for (int num = 1; num<=sudoku.getN(); num++)
        {
            if (checkMove.checkIfSafe(i, j, num))
            {
                sudoku.getSudoku()[i][j] = num;
                if (fillRemaining(i, j+1))
                    return true;

                sudoku.getSudoku()[i][j] = 0;
            }
        }
        return false;
    }


    public void removeKDigits()
    {
        int count = sudoku.getK();
        while (count != 0)
        {
            int cellId = randomGenerator(sudoku.getN()*sudoku.getN())-1;
            int i = (cellId/sudoku.getN());
            int j = cellId%9;
            if (j != 0)
                j = j - 1;
            if (sudoku.getSudoku()[i][j] != 0)
            {
                count--;
                sudoku.getSudoku()[i][j] = 0;
            }
        }
    }



    public void createUserDefineSudokuSkeleton(ArrayList<InputData> userInput)
    {

        for(InputData data : userInput)
        {
            checkMove.isValidRow(data.getRow());
            checkMove.isValidCol(data.getCol());
            checkMove.isValidNum(data.getValue());
        }
        for(int[] a: sudoku.getSudoku())
        {
            Arrays.fill(a, 0);
        }
        for(InputData data : userInput)
        {
            if(checkMove.checkIfSafe(data.getRow(),data.getCol(),data.getValue()))
                sudoku.getSudoku()[data.getRow()][data.getCol()] = data.getValue();
            else
                throw new InvalidInputValueException("Invalid Input value");
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
                sudoku.setGameStart(true);
                //sudoku.printSudoku();
        return true;
    }
}
