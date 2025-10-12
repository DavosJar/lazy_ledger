package com.lazyledger.backend.cliente.presentacion;

import com.lazyledger.backend.api.ApiResponse;
import com.lazyledger.backend.api.PagedResponse;
import com.lazyledger.backend.cliente.aplicacion.ClienteFacade;
import com.lazyledger.backend.cliente.presentacion.dto.ClienteDTO;
import com.lazyledger.backend.cliente.presentacion.dto.ClienteSaveRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    private final ClienteFacade clienteFacade;
    public ClienteController(ClienteFacade clienteFacade) {
        this.clienteFacade = clienteFacade;
    }

    @GetMapping
    public ResponseEntity<PagedResponse<ClienteDTO>> getAllClientes(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        PagedResponse<ClienteDTO> response = clienteFacade.getAllClientes(page, size);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ClienteDTO>> getClienteById(@PathVariable String id) {
        var apiResponse = clienteFacade.getClienteById(id);
        return ResponseEntity.ok(apiResponse);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ClienteDTO>> createCliente(@RequestBody ClienteSaveRequest clienteSaveRequest) {
        var apiResponse = clienteFacade.createCliente(clienteSaveRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ClienteDTO>> updateCliente(@PathVariable String id, @RequestBody ClienteDTO clienteDTO) {
        clienteDTO.setId(id);
        var apiResponse = clienteFacade.updateCliente(clienteDTO);
        return ResponseEntity.ok(apiResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCliente(@PathVariable String id) {
        clienteFacade.deleteCliente(id);
        return ResponseEntity.noContent().build();
    }
}