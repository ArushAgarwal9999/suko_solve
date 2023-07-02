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
                unUsedInBox(i-i%sudoku.getSRN(), j-j%sudoku.getSRN(), num));
    }


    private boolean unUsedInRow(int i,int num)
    {
        for (int j = 0; j<sudoku.getN(); j++)
            if (sudoku.getSudoku()[i][j] == num)
                return false;
        return true;
    }


    private boolean unUsedInCol(int j,int num)
    {
        for (int i = 0; i<sudoku.getN(); i++)
            if (sudoku.getSudoku()[i][j] == num)
                return false;
        return true;
    }
    public boolean unUsedInBox(int rowStart, int colStart, int num)
    {
        for (int i = 0; i<sudoku.getSRN(); i++)
            for (int j = 0; j<sudoku.getSRN(); j++)
                if (sudoku.getSudoku()[rowStart+i][colStart+j]==num)
                    return false;

        return true;
    }
    public boolean isValidRow(int row)
    {
        if(row>=0 && row<sudoku.getSudoku().length)
            return true;
        throw new InvalidInputValueException("Invalid Row Value");
    }
    public boolean isValidCol(int col)
    {
        if(col>=0 && col<sudoku.getSudoku()[0].length)
            return true;
        throw new InvalidInputValueException("Invalid Row Value");
    }
    public boolean isValidNum(int num)
    {
        if(num>=1 && num <= 9)
                return true;
        throw new InvalidInputValueException("Invalid Sudoku Value");
    }
    public void isGameEnd()
    {
        for(int[]a :sudoku.getSudoku())
        {
            for(int num:a)
            {
                if(num ==0)
                    return;
            }
        }
        sudoku.setGameEnd(true);
    }
    public MoveResponse CheckIfSafeWithHint(Move request)
    {
        MoveResponse moveResponse = new MoveResponse();
        if(sudoku.isGameStart()== false)
        {
            moveResponse.setResult("Game is not started yet");
            return moveResponse;
        }
        else if(sudoku.isGameEnd())
        {
            moveResponse.setResult("Game is Over");
            return moveResponse;
        }
        else if(sudoku.getSudoku()[request.getRow()][request.getCol()] != 0)
        {
            if(sudoku.getSudoku()[request.getRow()][request.getCol()] == request.getNum())
            {
                moveResponse.setResult("Valid");
            }
            else{
                moveResponse.setResult("Invalid");
                sudoku.setConsecutiveError(sudoku.getConsecutiveError()+1);;
                if(sudoku.getConsecutiveError() ==3)
                {
                    moveResponse.setHint(getHint());
                    sudoku.setConsecutiveError(0);
                }
            }
        }
        else if(checkIfSafe(request.getRow(), request.getCol(),request.getNum()))
        {
            sudoku.setConsecutiveError(0);
            sudoku.getSudoku()[request.getRow()][request.getCol()] = request.getNum();
            moveResponse.setResult("Valid");
            isGameEnd();
        }
        else{
            moveResponse.setResult("Invalid");
            sudoku.setConsecutiveError(sudoku.getConsecutiveError()+1);;
            if(sudoku.getConsecutiveError() ==3)
            {
                moveResponse.setHint(getHint());
                sudoku.setConsecutiveError(0);
            }
        }
        //sudoku.printSudoku();
        return moveResponse;

    }
    public Move getHint()
    {

        for(int i = 0;i<sudoku.getSudoku().length;i++)
        {
            for(int j = 0;j<sudoku.getSudoku()[0].length;j++)
            {
                if(sudoku.getSudoku()[i][j] == 0)
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
