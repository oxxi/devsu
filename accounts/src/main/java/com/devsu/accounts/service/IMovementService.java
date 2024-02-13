package com.devsu.accounts.service;

import com.devsu.accounts.dto.MovementRequest;
import com.devsu.accounts.dto.MovementResponse;

import java.util.List;

public interface IMovementService {

    MovementResponse createMovement(MovementRequest request);

    MovementResponse getMovement(Long id);

    MovementResponse updateMovement(Long id, MovementRequest request);

    void deleteMovement(Long id);

    List<MovementResponse> getMovements();
}
