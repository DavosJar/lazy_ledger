package com.lazyledger.backend.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.Link;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PagedResponse<T> {
    private boolean success;
    private List<T> data;
    private PaginationInfo pagination;
    private String message;
    private LocalDateTime timestamp;
    private Map<String, Link> _links;

    public PagedResponse() {
        this.timestamp = LocalDateTime.now();
        this.success = true;
    }

    public PagedResponse(List<T> data, PaginationInfo pagination) {
        this();
        this.data = data;
        this.pagination = pagination;
    }

    public PagedResponse(List<T> data, PaginationInfo pagination, Map<String, Link> links) {
        this(data, pagination);
        this._links = links;
    }

    public PagedResponse(List<T> data, PaginationInfo pagination, String message, Map<String, Link> links) {
        this(data, pagination, links);
        this.message = message;
    }

    // Factory method from Spring Page
    public static <T> PagedResponse<T> from(Page<T> page) {
        PaginationInfo pagination = new PaginationInfo(
            page.getNumber(),
            page.getSize(),
            page.getTotalElements(),
            page.getTotalPages(),
            page.isFirst(),
            page.isLast()
        );
        return new PagedResponse<>(page.getContent(), pagination);
    }

    // Getters and setters
    public boolean isSuccess() { return success; }
    public void setSuccess(boolean success) { this.success = success; }

    public List<T> getData() { return data; }
    public void setData(List<T> data) { this.data = data; }

    public PaginationInfo getPagination() { return pagination; }
    public void setPagination(PaginationInfo pagination) { this.pagination = pagination; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }

    public Map<String, Link> get_links() { return _links; }
    public void set_links(Map<String, Link> links) { this._links = links; }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class PaginationInfo {
        private int page;
        private int size;
        private long totalElements;
        private int totalPages;
        private boolean first;
        private boolean last;

        public PaginationInfo() {}

        public PaginationInfo(int page, int size, long totalElements, int totalPages, boolean first, boolean last) {
            this.page = page;
            this.size = size;
            this.totalElements = totalElements;
            this.totalPages = totalPages;
            this.first = first;
            this.last = last;
        }

        // Getters and setters
        public int getPage() { return page; }
        public void setPage(int page) { this.page = page; }

        public int getSize() { return size; }
        public void setSize(int size) { this.size = size; }

        public long getTotalElements() { return totalElements; }
        public void setTotalElements(long totalElements) { this.totalElements = totalElements; }

        public int getTotalPages() { return totalPages; }
        public void setTotalPages(int totalPages) { this.totalPages = totalPages; }

        public boolean isFirst() { return first; }
        public void setFirst(boolean first) { this.first = first; }

        public boolean isLast() { return last; }
        public void setLast(boolean last) { this.last = last; }
    }
}