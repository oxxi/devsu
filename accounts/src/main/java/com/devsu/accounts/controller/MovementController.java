package com.devsu.accounts.controller;

import com.devsu.accounts.dto.MovementRequest;
import com.devsu.accounts.dto.MovementResponse;
import com.devsu.accounts.service.IMovementService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/movimientos")
public class MovementController {

    private final IMovementService  movementService;


    public MovementController(IMovementService movementService) {
        this.movementService = movementService;
    }


    @GetMapping
    public List<MovementResponse>  getMovements(){
        return movementService.getMovements();
    }

    @GetMapping("/{id}")
    public MovementResponse  getMovementById(@PathVariable Long id){
        return movementService.getMovement(id);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public MovementResponse  createMovement(@RequestBody @Valid MovementRequest movement){
        return movementService.createMovement(movement);
    }

    @PutMapping("/{id}")
    public MovementResponse  updateMovement(@PathVariable Long id, @RequestBody MovementResponse movement){
        return movementService.updateMovement(id, movement);
    }

    @DeleteMapping("/{id}")
    public void  deleteMovement(@PathVariable Long id){
        movementService.deleteMovement(id);
    }
}
