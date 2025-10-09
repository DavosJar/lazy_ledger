package dev.lazyledger.core.api;

import java.util.List;

//
public class ResourceFormat<T> {
    private T resource;
    private List<String> links;

    public ResourceFormat(T resource, List<String> links) {
        this.resource = resource;
        this.links = links;
    }

    public T getResource() {
        return resource;
    }

    public void setResource(T resource) {
        this.resource = resource;
    }

    public List<String> getLinks() {
        return links;
    }
    public void setLinks(List<String> links) {
        this.links = links;
    }
}
