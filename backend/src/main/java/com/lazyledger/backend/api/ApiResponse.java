package com.lazyledger.backend.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.hateoas.Link;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {
    private boolean success;
    private T data;
    private String message;
    private LocalDateTime timestamp;
    private Map<String, Link> _links;

    public ApiResponse() {
        this.timestamp = LocalDateTime.now();
        this.success = true;
    }

    public ApiResponse(T data) {
        this();
        this.data = data;
    }

    public ApiResponse(T data, String message) {
        this(data);
        this.message = message;
    }

    public ApiResponse(T data, Link link) {
        this(data);
        if (link != null) {
            this._links = new java.util.HashMap<>();
            this._links.put(link.getRel().value(), link);
        }
    }

    public ApiResponse(T data, Map<String, Link> links) {
        this(data);
        this._links = links != null ? new java.util.HashMap<>(links) : null;
    }

    public ApiResponse(T data, String message, Map<String, Link> links) {
        this(data, message);
        this._links = links;
    }

    // Error response
    public static <T> ApiResponse<T> error(String message) {
        ApiResponse<T> response = new ApiResponse<>();
        response.success = false;
        response.message = message;
        return response;
    }

    // Getters and setters
    public boolean isSuccess() { return success; }
    public void setSuccess(boolean success) { this.success = success; }

    public T getData() { return data; }
    public void setData(T data) { this.data = data; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }

    public Map<String, Link> get_links() { return _links; }
    public void set_links(Map<String, Link> links) { this._links = links; }

    public void add(Link link) {
        if (this._links == null) {
            this._links = new java.util.HashMap<>();
        }
        this._links.put(link.getRel().value(), link);
    }
}