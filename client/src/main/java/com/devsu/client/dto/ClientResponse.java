package com.devsu.client.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientResponse extends ClientCreateDto{

    private Long id;

    @Override
    @JsonIgnore
    public String getContrasena() {
        return super.getContrasena();
    }

    public ClientResponse toResponse(ClientCreateDto dto, Long id){

        ClientResponse response = new ClientResponse();
        response.setDireccion(dto.getDireccion());
        response.setNombre(dto.getNombre());
        response.setEstado(dto.getEstado());
        response.setTelefono(dto.getTelefono());
        response.setId(id);
        return response;
    }


}
