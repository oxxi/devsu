package com.devsu.client.service;

import com.devsu.client.dto.ClientCreateDto;
import com.devsu.client.dto.ClientResponse;
import com.devsu.client.entities.Cliente;
import com.devsu.client.respository.IClientRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Base64;
import java.util.List;


@Service
public class ClientServiceImpl implements IClientService{

    private final IClientRepository clientRepository;

    public ClientServiceImpl(IClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }


    @Override
    public ClientResponse createClient(ClientCreateDto dto)  {
        Cliente hasName = clientRepository.findByNombre(dto.getNombre());
        if (hasName != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("Client with name %s already exist!", dto.getNombre()));
        }
        Cliente entity = new Cliente();
        entity.setNombre(dto.getNombre());
        entity.setAddress(dto.getDireccion());
        entity.setPassword(toBase64(dto.getContrasena())); // TODO: agregar algun metodo de proteccion de la contraseña
        entity.setStatus(dto.getEstado());
        entity.setPhone(dto.getTelefono());

        entity =  clientRepository.save(entity);
        return new ClientResponse().toResponse(dto, entity.getId());
    }

    @Override
    public ClientResponse getById(Long id) {

        Cliente cliente = clientRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Client with id %s does`t exist!", id)));
        ClientResponse response = new ClientResponse();
        response.setId(cliente.getId());
        response.setNombre(cliente.getNombre());
        response.setDireccion(cliente.getAddress());
        response.setEstado(cliente.getStatus());
        response.setTelefono(cliente.getPhone());
        return response;
    }

    @Override
    public List<ClientResponse> getAll() {
       return clientRepository.findAll()
                .stream()
                .map(x->{
                    ClientResponse response = new ClientResponse();
                    response.setId(x.getId());
                    response.setNombre(x.getNombre());
                    response.setDireccion(x.getAddress());
                    response.setEstado(x.getStatus());
                    response.setTelefono(x.getPhone());
                    return response;
                })
                .toList();
    }

    @Override
    public ClientResponse updateClient(Long id, ClientCreateDto dto) {
        Cliente cliente = clientRepository
                        .findById(id)
                        .orElseThrow(
                                () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                                        String.format("Client with id %s does`t exist", id))
                        );

        cliente.setNombre(dto.getNombre() != null ? dto.getNombre() : cliente.getNombre());
        cliente.setAddress(dto.getDireccion() != null ? dto.getDireccion() : cliente.getAddress());
        cliente.setStatus(dto.getEstado() != null ? dto.getEstado() : cliente.getStatus());
        cliente.setPhone(dto.getTelefono() != null ? dto.getTelefono() : cliente.getPhone());

        cliente =  clientRepository.save(cliente);

        return cliente.toResponse();
    }

    @Override
    public void deleteClient(Long id) {
        Cliente cliente = clientRepository
                        .findById(id)
                        .orElseThrow(
                                () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                                        String.format("Client with id %s does`t exist", id))
                        );
        clientRepository.delete(cliente);
    }


    private String toBase64(String password) {
        //more validations most be done
        return Base64.getEncoder().encodeToString(password.getBytes());
    }


}
