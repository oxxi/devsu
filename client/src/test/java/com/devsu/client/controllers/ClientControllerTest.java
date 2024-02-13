package com.devsu.client.controllers;

import com.devsu.client.dto.ClientCreateDto;
import com.devsu.client.dto.ClientResponse;
import com.devsu.client.service.IClientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@WebMvcTest(ClientController.class)
@ExtendWith(SpringExtension.class)
class ClientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IClientService service;

    private List<ClientResponse> clientResponses = new ArrayList<>();

    @BeforeEach
    void setUp() {


        ClientResponse clientResponse = new ClientResponse();
        clientResponse.setId(1L);
        clientResponse.setNombre("Juan");
        clientResponse.setDireccion("XXXXXXXXXXXXXX");
        clientResponse.setTelefono("504XXXXXXXXXX");
        clientResponse.setDireccion("P. Sherman ");

        ClientResponse clientResponse2 = new ClientResponse();
        clientResponse.setId(2L);
        clientResponse.setNombre("Luis");
        clientResponse.setDireccion("XXXXXXXXXXXXXX");
        clientResponse.setTelefono("504XXXXXXXXXX");
        clientResponse.setDireccion("P. Sherman ");

        clientResponses.add(clientResponse);
        clientResponses.add(clientResponse2);
    }

    @Test
    void getAll() throws Exception {

        Mockito.when(service.getAll()).thenReturn(clientResponses);
        mockMvc.perform(get("/clientes").contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());

    }

    @Test
    void getById() throws Exception {
        Mockito.when(service.getById(1L)).thenReturn(clientResponses.get(0));
        mockMvc.perform(get("/clientes/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void create() throws Exception {
        ClientResponse clientResponse = new ClientResponse();
        clientResponse.setId(1L);
        clientResponse.setNombre("Jose Lema");
        clientResponse.setDireccion("XXXXXXXXXXXXXX");
        clientResponse.setTelefono("504XXXXXXXXXX");
        clientResponse.setDireccion("P. Sherman ");

        Mockito.when(service.createClient(Mockito.any(ClientCreateDto.class))).thenReturn(clientResponse);

        String json = """
                {
                "nombre":"Jose Lema",
                "direccioncion":"P. Sherman",
                "telefono":"504XXXXXXXXXX",
                "contrasena":"123456789",
                "estado":true
                }
                """;
        mockMvc.perform(post("/clientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1L));
    }

    @Test
    void updateClient() {
    }

    @Test
    void deleteClient() {
    }
}