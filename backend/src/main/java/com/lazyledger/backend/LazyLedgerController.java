package com.lazyledger.backend;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.lazyledger.backend.cliente.aplicacion.ClienteFacade;
import com.lazyledger.backend.cliente.presentacion.dto.ClienteDTO;
import com.lazyledger.backend.cliente.presentacion.dto.ClienteSaveRequest;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import com.lazyledger.backend.api.ApiResponse;
import com.lazyledger.backend.api.PagedResponse;
import org.springframework.hateoas.Link;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import java.util.List;
import java.util.ArrayList;

// Importaciones necesarias
import com.lazyledger.backend.cliente.dominio.Cliente;
import java.util.ArrayList;
import java.util.List;

@RestController
public class LazyLedgerController {

    private final ClienteFacade clienteFacade;

    List<Cliente> clientes = new ArrayList<>();

    ClienteDTO clienteDTO = new ClienteDTO();


    public LazyLedgerController(ClienteFacade clienteFacade) {
        this.clienteFacade = clienteFacade;
    }

    

    @GetMapping("/")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("LazyLedger is running!");
    }

    @GetMapping("/clientes")
    public ResponseEntity<PagedResponse<ClienteDTO>> getAllClientes(
            @RequestParam int page,
            @RequestParam int size) {
        var allClientes = clienteFacade.getAllClientes();
        long totalElements = allClientes.size();
        int totalPages = (int) Math.ceil((double) totalElements / size);
        int start = page * size;
        int end = Math.min(start + size, allClientes.size());
        var pageClientes = allClientes.subList(start, end);

        ClientePagedResponseBuilder builder = new ClientePagedResponseBuilder();
        PagedResponse<ClienteDTO> response = builder.build(pageClientes, page, size, totalElements, totalPages);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/clientes/{id}")
    public ResponseEntity<ApiResponse<ClienteDTO>> getClienteById(@PathVariable String id) {
        var cliente = clienteFacade.getClienteById(id);
        Link selfLink = linkTo(methodOn(LazyLedgerController.class).getClienteById(id)).withSelfRel();
        ApiResponse<ClienteDTO> response = new ApiResponse<>(cliente, selfLink);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/clientes")
    public ResponseEntity<ApiResponse<ClienteDTO>> createCliente(@RequestBody ClienteSaveRequest clienteSaveRequest) {
        var createdCliente = clienteFacade.createCliente(clienteSaveRequest);
        Link selfLink = linkTo(methodOn(LazyLedgerController.class).getClienteById(createdCliente.getId())).withSelfRel();
        ApiResponse<ClienteDTO> response = new ApiResponse<>(createdCliente, selfLink);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/clientes/{id}")
    public ResponseEntity<ApiResponse<ClienteDTO>> updateCliente(@PathVariable String id, @RequestBody ClienteDTO clienteDTO) {
        clienteDTO.setId(id);
        var updatedCliente = clienteFacade.updateCliente(clienteDTO);
        Link selfLink = linkTo(methodOn(LazyLedgerController.class).getClienteById(id)).withSelfRel();
        ApiResponse<ClienteDTO> response = new ApiResponse<>(updatedCliente, selfLink);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/clientes/{id}")
    public ResponseEntity<Void> deleteCliente(@PathVariable String id) {
        clienteFacade.deleteCliente(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Builder anidado para construir PagedResponse de Cliente con navegación HATEOAS completa.
     */
    private static class ClientePagedResponseBuilder {

        public PagedResponse<ClienteDTO> build(List<ClienteDTO> data, int page, int size, long totalElements, int totalPages) {
            Link selfLink = linkTo(methodOn(LazyLedgerController.class).getAllClientes(page, size)).withSelfRel();
            List<Link> links = new ArrayList<>();
            links.add(selfLink);

            if (page > 0) {
                Link prevLink = linkTo(methodOn(LazyLedgerController.class).getAllClientes(page - 1, size)).withRel("previous");
                links.add(prevLink);
            }
            if (page < totalPages - 1) {
                Link nextLink = linkTo(methodOn(LazyLedgerController.class).getAllClientes(page + 1, size)).withRel("next");
                links.add(nextLink);
            }
            if (page > 0) {
                Link firstLink = linkTo(methodOn(LazyLedgerController.class).getAllClientes(0, size)).withRel("first");
                links.add(firstLink);
            }
            if (page < totalPages - 1) {
                Link lastLink = linkTo(methodOn(LazyLedgerController.class).getAllClientes(totalPages - 1, size)).withRel("last");
                links.add(lastLink);
            }

            return new PagedResponse<>(data, page, size, totalElements, totalPages, links.toArray(new Link[0]));
        }
    }
}