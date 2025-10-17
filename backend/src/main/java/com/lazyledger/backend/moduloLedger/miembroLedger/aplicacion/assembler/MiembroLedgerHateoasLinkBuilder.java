package com.lazyledger.backend.moduloLedger.miembroLedger.aplicacion.assembler;

import com.lazyledger.backend.api.HateoasLinkBuilder;
import com.lazyledger.backend.moduloLedger.miembroLedger.presentacion.dto.MiembroLedgerDTO;

import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Implementación específica del builder HATEOAS para el recurso MiembroLedger.
 * Construye enlaces para gestión de membresías en ledgers.
 */
@Component
public class MiembroLedgerHateoasLinkBuilder extends HateoasLinkBuilder<MiembroLedgerDTO> {

    public MiembroLedgerHateoasLinkBuilder() {
        super("/miembros-ledger");
    }

    @Override
    public Map<String, Link> buildLinks(MiembroLedgerDTO miembro) {
        Map<String, Link> links = new HashMap<>();

        // Self link
        links.put("self", buildSelfLink(miembro.getClienteId() + "/" + miembro.getLedgerId()));

        // Ledger details
        links.put("ledger", Link.of("/ledgers/" + miembro.getLedgerId()).withRel("ledger"));

        // Client details
        links.put("cliente", Link.of("/clientes/" + miembro.getClienteId()).withRel("cliente"));

        // Acciones disponibles basadas en el rol
        if ("PROPIETARIO".equals(miembro.getRol())) {
            links.put("invite", Link.of(baseUrl + "/invitar").withRel("invite"));
            links.put("change-role", Link.of(baseUrl + "/cambiar-rol").withRel("change-role"));
            links.put("expel", Link.of(baseUrl + "/expulsar").withRel("expel"));
            links.put("delete-ledger", Link.of(baseUrl + "/eliminar-ledger").withRel("delete-ledger"));
        }

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

    /**
     * Construye enlaces para la colección de ledgers de un usuario
     */
    public Map<String, Link> buildUserLedgersLinks(String clienteId, int page, int size, long totalElements, int totalPages) {
        Map<String, Link> links = new HashMap<>();

        // Self link con parámetros
        links.put("self", Link.of(baseUrl + "/usuario/" + clienteId + "/ledgers?page=" + page + "&size=" + size).withSelfRel());

        // Navigation links
        links.put("first", Link.of(baseUrl + "/usuario/" + clienteId + "/ledgers?page=0&size=" + size).withRel("first"));
        links.put("last", Link.of(baseUrl + "/usuario/" + clienteId + "/ledgers?page=" + (totalPages - 1) + "&size=" + size).withRel("last"));

        Link prevLink = buildPrevLink(page);
        if (prevLink != null) {
            links.put("prev", Link.of(baseUrl + "/usuario/" + clienteId + "/ledgers" + prevLink.getHref().substring(baseUrl.length())).withRel("prev"));
        }

        Link nextLink = buildNextLink(page, totalPages);
        if (nextLink != null) {
            links.put("next", Link.of(baseUrl + "/usuario/" + clienteId + "/ledgers" + nextLink.getHref().substring(baseUrl.length())).withRel("next"));
        }

        return links;
    }
}