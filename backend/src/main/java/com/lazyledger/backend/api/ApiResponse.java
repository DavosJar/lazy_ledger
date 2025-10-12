package com.lazyledger.backend.api;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.Link;
import java.util.Arrays;

/**
 * Wrapper genérico para respuestas de API con soporte HATEOAS.
 * Envuelve el objeto en un campo 'data' para permitir formateo futuro.
 */
public class ApiResponse<T> extends RepresentationModel<ApiResponse<T>> {

    private T data;

    public ApiResponse(T data) {
        this.data = data;
    }

    public ApiResponse(T data, Link... links) {
        this.data = data;
        this.add(Arrays.asList(links));
    }

    public ApiResponse(T data, Iterable<Link> links) {
        this.data = data;
        this.add(links);
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}