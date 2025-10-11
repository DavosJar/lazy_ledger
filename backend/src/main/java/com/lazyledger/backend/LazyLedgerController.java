package com.lazyledger.backend;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.lazyledger.backend.cliente.aplicacion.ClienteFacade;
import com.lazyledger.backend.cliente.presentacion.dto.ClienteDTO;
import com.lazyledger.backend.cliente.presentacion.dto.ClienteSaveRequest;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

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

    @GetMapping("/clientes/{id}")
    public ResponseEntity<ClienteDTO> getClienteById(@PathVariable String id) {
        var cliente = clienteFacade.getClienteById(id);
        return ResponseEntity.ok(cliente);
    }

    @PostMapping("/clientes")
    public ResponseEntity<ClienteDTO> createCliente(@RequestBody ClienteSaveRequest clienteSaveRequest) {
        var createdCliente = clienteFacade.createCliente(clienteSaveRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCliente);
    }

    @PutMapping("/clientes/{id}")
    public ResponseEntity<ClienteDTO> updateCliente(@PathVariable String id, @RequestBody ClienteDTO clienteDTO) {
        clienteDTO.setId(id);
        var updatedCliente = clienteFacade.updateCliente(clienteDTO);
        return ResponseEntity.ok(updatedCliente);
    }

    @DeleteMapping("/clientes/{id}")
    public ResponseEntity<Void> deleteCliente(@PathVariable String id) {
        clienteFacade.deleteCliente(id);
        return ResponseEntity.noContent().build();
    }
}