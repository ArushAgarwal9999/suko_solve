package com.example.sudoko.controller;

import com.example.sudoko.exceptions.InvalidInputValueException;
import com.example.sudoko.model.Move;
import com.example.sudoko.model.MoveResponse;
import com.example.sudoko.model.StartRequest;
import com.example.sudoko.model.StartResponse;
import com.example.sudoko.service.CheckMove;
import com.example.sudoko.service.CreateSudokuSkeleton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SudokuController {
    @Autowired
    CreateSudokuSkeleton createSudokuSkeleton;
    @Autowired
    CheckMove checkMove;

    @PostMapping("/start")
    public StartResponse initializeGame(@RequestBody  StartRequest request) throws InvalidInputValueException
    {
        StartResponse response  = new StartResponse();

            System.out.println(request.getMove());
            System.out.println(request);
            createSudokuSkeleton.createSudokuSkeleton(request);
            response.setResult("READY");


        return response;



    }
    @PostMapping("/move")
    public MoveResponse initializeGame(@RequestBody  Move request) throws InvalidInputValueException
    {

        return checkMove.CheckIfSafeWithHint(request);



    }
}
