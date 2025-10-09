package dev.lazyledger.core;

import dev.lazyledger.core.api.ApiResponse;
import dev.lazyledger.core.api.ResourceFormat;
import dev.lazyledger.core.ledger.Ledger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;


import java.util.ArrayList;
import java.util.UUID;


@RestController
@RequestMapping("/ledgers")
public class Controller {
    private final UUID uuid = UUID.fromString("123e4567-e89b-12d3-a456-426614174000");
    @GetMapping
    ResponseEntity<ApiResponse<ResourceFormat<Ledger>>> index() {
        Ledger ledger = new Ledger(uuid, "Main Ledger", "This is the main ledger.");
        List<String> links = new ArrayList<>();
        links.add("/ledgers/" + ledger.getId());
        links.add("/ledgers/" + ledger.getId() + "/entries");

        ResourceFormat<Ledger> resourceFormat = new ResourceFormat<>(ledger, links);
        ApiResponse apiResponse = new ApiResponse<>("success", "Ledger retrieved successfully", resourceFormat);
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/{id}")
    ResponseEntity<ApiResponse<ResourceFormat<Ledger>>> show(@PathVariable UUID id) {
        Ledger ledger = new Ledger(id, "Main Ledger", "This is the main ledger.");
        List<String> links = new ArrayList<>();
        links.add("/ledgers/" + ledger.getId());
        links.add("/ledgers/" + ledger.getId() + "/entries");
        ResourceFormat<Ledger> resourceFormat = new ResourceFormat<>(ledger,links);
        ApiResponse apiResponse = new ApiResponse<>("success", "Ledger retrieved successfully", resourceFormat);
        return ResponseEntity.ok(apiResponse);
    }
}
