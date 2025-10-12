package com.lazyledger.backend.api;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.Link;
import java.util.List;

/**
 * Respuesta paginada para listas con HATEOAS.
 */
public class PagedResponse<T> extends RepresentationModel<PagedResponse<T>> {

    private List<T> data;
    private int page;
    private int size;
    private long totalElements;
    private int totalPages;

    public PagedResponse(List<T> data, int page, int size, long totalElements, int totalPages) {
        this.data = data;
        this.page = page;
        this.size = size;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
    }

    public PagedResponse(List<T> data, int page, int size, long totalElements, int totalPages, Link... links) {
        this(data, page, size, totalElements, totalPages);
        this.add(links);
    }

    // Getters
    public List<T> getData() {
        return data;
    }

    public int getPage() {
        return page;
    }

    public int getSize() {
        return size;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public int getTotalPages() {
        return totalPages;
    }
}