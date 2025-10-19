package com.lazyledger.backend.moduloLedger.transaccion.presentacion;

import com.lazyledger.backend.api.ApiResponse;
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

    /**
     * Endpoint de prueba para crear una transacción con parámetros mínimos por URL.
     * Uso: GET /transacciones/demo?ledgerId=xxx&clienteId=yyy&monto=100&tipo=INGRESO&categoria=ALIMENTACION&descripcion=Test
     */
    @GetMapping("/demo")
    public ResponseEntity<ApiResponse<TransaccionDTO>> crearTransaccionDemo(
            @RequestParam String ledgerId,
            @RequestParam String clienteId,
            @RequestParam double monto,
            @RequestParam String tipo,
            @RequestParam String categoria,
            @RequestParam(defaultValue = "Transacción de prueba") String descripcion) {
        
        TransaccionSaveRequest request = new TransaccionSaveRequest();
        request.setLedgerId(ledgerId);
        request.setClienteId(clienteId);
        request.setMonto(monto);
        request.setTipo(tipo);
        request.setCategoria(categoria);
        request.setDescripcion(descripcion);
        // fecha = null para usar fecha actual
        
        var apiResponse = transaccionFacade.createTransaccion(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }

}