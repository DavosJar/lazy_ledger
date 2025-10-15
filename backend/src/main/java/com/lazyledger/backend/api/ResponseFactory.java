package com.lazyledger.backend.api;

import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Factory Pattern para crear respuestas REST unificadas.
 * Centraliza la creación de ApiResponse y PagedResponse con HATEOAS.
 */
@Component
public class ResponseFactory {

    /**
     * Crea una respuesta atómica con HATEOAS
     */
    public <T> ApiResponse<T> createResponse(T data, Map<String, Link> links) {
        return new ApiResponse<>(data, links);
    }

    /**
     * Crea una respuesta atómica sin HATEOAS
     */
    public <T> ApiResponse<T> createResponse(T data) {
        return new ApiResponse<>(data);
    }

    /**
     * Crea una respuesta paginada con HATEOAS
     */
    public <T> PagedResponse<T> createPagedResponse(
            List<T> data,
            PagedResponse.PaginationInfo pagination,
            Map<String, Link> links) {
        return new PagedResponse<>(data, pagination, links);
    }

    /**
     * Crea una respuesta paginada sin HATEOAS
     */
    public <T> PagedResponse<T> createPagedResponse(
            List<T> data,
            PagedResponse.PaginationInfo pagination) {
        return new PagedResponse<>(data, pagination);
    }

    /**
     * Crea una respuesta de error
     */
    public <T> ApiResponse<T> createErrorResponse(String message) {
        ApiResponse<T> response = new ApiResponse<>();
        response.setSuccess(false);
        response.setMessage(message);
        return response;
    }
}