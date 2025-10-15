package com.lazyledger.backend.api;

import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Builder reutilizable para construir enlaces HATEOAS sin invadir el Controller.
 * Cada recurso implementa su propia lógica de construcción de enlaces.
 */
@Component
public abstract class HateoasLinkBuilder<T> {

    protected final String baseUrl;

    protected HateoasLinkBuilder(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    /**
     * Construye enlaces HATEOAS para un recurso individual
     */
    public abstract Map<String, Link> buildLinks(T resource);

    /**
     * Construye enlaces HATEOAS para una colección paginada
     */
    public abstract Map<String, Link> buildCollectionLinks(int page, int size, long totalElements, int totalPages);

    /**
     * Construye enlace self para un recurso
     */
    protected Link buildSelfLink(String resourceId) {
        return Link.of(baseUrl + "/" + resourceId).withSelfRel();
    }

    /**
     * Construye enlace a colección
     */
    protected Link buildCollectionLink() {
        return Link.of(baseUrl).withRel("collection");
    }

    /**
     * Construye enlaces de navegación para paginación
     */
    protected Link buildFirstLink() {
        return Link.of(baseUrl + "?page=0").withRel("first");
    }

    protected Link buildLastLink(int totalPages) {
        return Link.of(baseUrl + "?page=" + (totalPages - 1)).withRel("last");
    }

    protected Link buildPrevLink(int currentPage) {
        if (currentPage > 0) {
            return Link.of(baseUrl + "?page=" + (currentPage - 1)).withRel("prev");
        }
        return null;
    }

    protected Link buildNextLink(int currentPage, int totalPages) {
        if (currentPage < totalPages - 1) {
            return Link.of(baseUrl + "?page=" + (currentPage + 1)).withRel("next");
        }
        return null;
    }
}