package com.lazyledger.backend.modulocliente.aplicacion.assembler;

import com.lazyledger.backend.api.HateoasLinkBuilder;
import com.lazyledger.backend.modulocliente.presentacion.dto.ClienteDTO;

import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Implementación específica del builder HATEOAS para el recurso Cliente.
 * Construye enlaces sin invadir el Controller.
 */
@Component
public class ClienteHateoasLinkBuilder extends HateoasLinkBuilder<ClienteDTO> {

    public ClienteHateoasLinkBuilder() {
        super("/clientes");
    }

    @Override
    public Map<String, Link> buildLinks(ClienteDTO cliente) {
        Map<String, Link> links = new HashMap<>();

        // Self link
        links.put("self", buildSelfLink(cliente.getId()));

        // Collection link
        links.put("collection", buildCollectionLink());

        // Acciones disponibles
        links.put("update", Link.of(baseUrl + "/" + cliente.getId()).withRel("update"));
        links.put("delete", Link.of(baseUrl + "/" + cliente.getId()).withRel("delete"));

        return links;
    }

    @Override
    public Map<String, Link> buildCollectionLinks(int page, int size, long totalElements, int totalPages) {
        Map<String, Link> links = new HashMap<>();

        // Self link con parámetros de paginación
        links.put("self", Link.of(baseUrl + "?page=" + page + "&size=" + size).withSelfRel());

        // Navigation links
        links.put("first", buildFirstLink());
        links.put("last", buildLastLink(totalPages));

        Link prevLink = buildPrevLink(page);
        if (prevLink != null) {
            links.put("prev", prevLink);
        }

        Link nextLink = buildNextLink(page, totalPages);
        if (nextLink != null) {
            links.put("next", nextLink);
        }

        return links;
    }
}