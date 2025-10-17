package com.lazyledger.backend.moduloLedger.transaccion.presentacion;

import com.lazyledger.backend.api.ApiResponse;
import com.lazyledger.backend.api.PagedResponse;
import com.lazyledger.backend.moduloLedger.transaccion.aplicacion.TransaccionFacade;
import com.lazyledger.backend.moduloLedger.transaccion.presentacion.dto.TransaccionDTO;
import com.lazyledger.backend.moduloLedger.transaccion.presentacion.dto.TransaccionSaveRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transacciones")
public class TransaccionController {
    private final TransaccionFacade transaccionFacade;

    public TransaccionController(TransaccionFacade transaccionFacade) {
        this.transaccionFacade = transaccionFacade;
    }

    @GetMapping
    public ResponseEntity<PagedResponse<TransaccionDTO>> getAllTransacciones(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        PagedResponse<TransaccionDTO> response = transaccionFacade.getAllTransacciones(page, size);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<TransaccionDTO>> getTransaccionById(@PathVariable String id) {
        var apiResponse = transaccionFacade.getTransaccionById(id);
        return ResponseEntity.ok(apiResponse);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<TransaccionDTO>> createTransaccion(@RequestBody TransaccionSaveRequest transaccionSaveRequest) {
        var apiResponse = transaccionFacade.createTransaccion(transaccionSaveRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<TransaccionDTO>> updateTransaccion(@PathVariable String id, @RequestBody TransaccionDTO transaccionDTO) {
        transaccionDTO.setId(id);
        var apiResponse = transaccionFacade.updateTransaccion(transaccionDTO);
        return ResponseEntity.ok(apiResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTransaccion(@PathVariable String id) {
        transaccionFacade.deleteTransaccion(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/ledger/{ledgerId}")
    public ResponseEntity<PagedResponse<TransaccionDTO>> getTransaccionesByLedgerId(
            @PathVariable String ledgerId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        PagedResponse<TransaccionDTO> response = transaccionFacade.getTransaccionesByLedgerId(ledgerId, page, size);
        return ResponseEntity.ok(response);
    }
}
