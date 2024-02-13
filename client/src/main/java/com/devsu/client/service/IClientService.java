package com.devsu.client.service;

import com.devsu.client.dto.ClientCreateDto;
import com.devsu.client.dto.ClientResponse;
import org.apache.coyote.BadRequestException;

import java.util.List;

public interface IClientService {

    ClientResponse createClient(ClientCreateDto dto) throws BadRequestException;

    ClientResponse getById(Long id);

    List<ClientResponse> getAll();

    ClientResponse updateClient(Long id, ClientCreateDto dto);

    void deleteClient(Long id);
}
