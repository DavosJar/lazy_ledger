package com.lazyledger.backend.cliente.presentacion;

import com.lazyledger.backend.api.PagedResponse;
import com.lazyledger.backend.cliente.presentacion.dto.ClienteDTO;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ClientePagedResponseBuilder {

    public PagedResponse<ClienteDTO> build(List<ClienteDTO> data, int page, int size, long totalElements, int totalPages) {
        Link selfLink = linkTo(methodOn(ClienteController.class).getAllClientes(page, size)).withSelfRel();
        List<Link> links = new ArrayList<>();
        links.add(selfLink);

        if (page > 0) {
            Link prevLink = linkTo(methodOn(ClienteController.class).getAllClientes(page - 1, size)).withRel("previous");
            links.add(prevLink);
        }
        if (page < totalPages - 1) {
            Link nextLink = linkTo(methodOn(ClienteController.class).getAllClientes(page + 1, size)).withRel("next");
            links.add(nextLink);
        }
        if (page > 0) {
            Link firstLink = linkTo(methodOn(ClienteController.class).getAllClientes(0, size)).withRel("first");
            links.add(firstLink);
        }
        if (page < totalPages - 1) {
            Link lastLink = linkTo(methodOn(ClienteController.class).getAllClientes(totalPages - 1, size)).withRel("last");
            links.add(lastLink);
        }

        return new PagedResponse<>(data, page, size, totalElements, totalPages, links.toArray(new Link[0]));
    }
}