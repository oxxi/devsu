package com.devsu.client.controllers;



import com.devsu.client.dto.ClientCreateDto;
import com.devsu.client.dto.ClientResponse;
import com.devsu.client.service.IClientService;
import jakarta.validation.Valid;
import org.apache.coyote.BadRequestException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes")
public class ClientController {

    private final IClientService service;

    public ClientController(IClientService service) {
        this.service = service;
    }

    @GetMapping()
    public List<ClientResponse> GetAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ClientResponse GetById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PostMapping()
    @ResponseBody
    public ClientResponse create(@RequestBody @Valid ClientCreateDto dto) throws BadRequestException {
        return service.createClient(dto);
    }


    @PutMapping("/{id}")
    public ClientResponse updateClient(@PathVariable Long id,@RequestBody ClientCreateDto dto) {
        return service.updateClient(id, dto);
    }


    @DeleteMapping("/{id}")
    public void deleteClient(@PathVariable Long id) {
        service.deleteClient(id);
    }

}
