package com.devsu.client.service;

import com.devsu.client.dto.ClientCreateDto;
import com.devsu.client.dto.ClientResponse;
import com.devsu.client.entities.Cliente;
import com.devsu.client.respository.IClientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ClientServiceImplTest {

    @Mock
    private IClientRepository clientRepository;

    @InjectMocks
    private ClientServiceImpl clientServiceImpl;

    private ClientCreateDto createClient;
    private Cliente cliente;


    @BeforeEach
    void setUp() {
        createClient = new ClientCreateDto();
        createClient.setContrasena("123456789");
        createClient.setDireccion("P. Sherman, 42 Wallaby Way, Sydney");
        createClient.setNombre("Dori");
        createClient.setEstado(true);
        createClient.setTelefono("1114444555");

        cliente = new Cliente();
        cliente.setId(1L);
        cliente.setNombre(createClient.getNombre());
        cliente.setAddress(createClient.getDireccion());
        cliente.setPhone(createClient.getTelefono());
        cliente.setPassword(createClient.getContrasena());
        cliente.setStatus(createClient.getEstado());
    }

    @Test
    void createClient() {

        Mockito.when(clientRepository.findByNombre(Mockito.anyString())).thenReturn(null);
        Mockito.when(clientRepository.save(Mockito.any(Cliente.class))).thenReturn(cliente);

        ClientResponse response = clientServiceImpl.createClient(createClient);

        assertNotNull(response);

        assertEquals(1L, response.getId());
        assertEquals(createClient.getNombre(), response.getNombre());

    }

    @Test
    void when_client_already_exist_then_throw_exception() {
        Mockito.when(clientRepository.findByNombre(Mockito.anyString())).thenReturn(cliente);

        ResponseStatusException responseStatusException = assertThrows(ResponseStatusException.class, () -> clientServiceImpl.createClient(createClient));

        assertEquals(HttpStatus.BAD_REQUEST, responseStatusException.getStatusCode());

        assertEquals("Client with name Dori already exist!", responseStatusException.getReason());

        Mockito.verify(clientRepository,Mockito.times(1)).findByNombre(Mockito.anyString());
    }


    @Test
    void getById() {
        Mockito.when(clientRepository.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(cliente));

        ClientResponse response = clientServiceImpl.getById(1L);

        assertNotNull(response);

        assertEquals(1L, response.getId());
        assertEquals(createClient.getNombre(), response.getNombre());
    }

    @Test
    void when_client_id_not_exist_then_throw_exception() {
        Mockito.when(clientRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());

        ResponseStatusException responseStatusException = assertThrows(ResponseStatusException.class, () -> clientServiceImpl.getById(1L));

        assertEquals(HttpStatus.NOT_FOUND, responseStatusException.getStatusCode());

        assertEquals("Client with id 1 does`t exist!", responseStatusException.getReason());

        Mockito.verify(clientRepository, Mockito.times(1)).findById(Mockito.anyLong());
    }


    @Test
    void getAll() {
    }

    @Test
    void updateClient() {

        Mockito.when(clientRepository.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(cliente));
        Mockito.when(clientRepository.save(Mockito.any(Cliente.class))).thenReturn(cliente);

        ClientResponse response = clientServiceImpl.updateClient(1L,createClient);

        assertNotNull(response);

        Mockito.verify(clientRepository,Mockito.times(1)).save(Mockito.any(Cliente.class));
    }

    @Test
    void deleteClient() {

        Mockito.when(clientRepository.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(cliente));

        clientServiceImpl.deleteClient(1L);

        Mockito.verify(clientRepository,Mockito.times(1)).delete(Mockito.any(Cliente.class));
    }
}