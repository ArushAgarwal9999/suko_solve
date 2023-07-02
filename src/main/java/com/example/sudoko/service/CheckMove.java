package com.example.sudoko.service;

import com.example.sudoko.exceptions.InvalidInputValueException;
import com.example.sudoko.model.Move;
import com.example.sudoko.model.MoveResponse;
import com.example.sudoko.model.Sudoku;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CheckMove {
    @Autowired
    Sudoku sudoku;
    public boolean checkIfSafe(int i,int j,int num)
    {
        return (isValidRow(i)&& isValidCol(j)&& isValidNum(num)&& unUsedInRow(i, num) &&
                unUsedInCol(j, num) &&
                unUsedInBox(i-i%sudoku.SRN, j-j%sudoku.SRN, num));
    }


    private boolean unUsedInRow(int i,int num)
    {
        for (int j = 0; j<sudoku.N; j++)
            if (sudoku.sudoku[i][j] == num)
                return false;
        return true;
    }


    private boolean unUsedInCol(int j,int num)
    {
        for (int i = 0; i<sudoku.N; i++)
            if (sudoku.sudoku[i][j] == num)
                return false;
        return true;
    }
    public boolean unUsedInBox(int rowStart, int colStart, int num)
    {
        for (int i = 0; i<sudoku.SRN; i++)
            for (int j = 0; j<sudoku.SRN; j++)
                if (sudoku.sudoku[rowStart+i][colStart+j]==num)
                    return false;

        return true;
    }
    public boolean isValidRow(int row)
    {
        if(row>=0 && row<sudoku.sudoku.length)
            return true;
        throw new InvalidInputValueException("Invalid Row Value");
    }
    public boolean isValidCol(int col)
    {
        if(col>=0 && col<sudoku.sudoku[0].length)
            return true;
        throw new InvalidInputValueException("Invalid Row Value");
    }
    public boolean isValidNum(int num)
    {
        if(num>=1 && num <= 9)
                return true;
        throw new InvalidInputValueException("Invalid Sudoku Value");
    }
    public MoveResponse CheckIfSafeWithHint(Move request)
    {
        MoveResponse moveResponse = new MoveResponse();
        if(checkIfSafe(request.getRow(), request.getCol(),request.getNum()))
        {
            sudoku.consecutiveError = 0;
            moveResponse.setResult("success");
        }
        else{
            moveResponse.setResult("error");
            sudoku.consecutiveError +=1;
            if(sudoku.consecutiveError ==3)
            {
                moveResponse.setHint(getHint());
                sudoku.consecutiveError = 0;
            }
        }

        return moveResponse;

    }
    public Move getHint()
    {

        for(int i = 0;i<sudoku.sudoku.length;i++)
        {
            for(int j = 0;j<sudoku.sudoku[0].length;j++)
            {
                if(sudoku.sudoku[i][j] == 0)
                {
                    for(int num= 1;num<=9;num++)
                    {
                        if(checkIfSafe(i,j,num))
                        {
                            return new Move(i,j,num);
                        }
                    }
                }
            }
        }
        return null;
    }
}
