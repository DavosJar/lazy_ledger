package com.lazyledger.backend.modulocliente.infraestructura;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.lazyledger.backend.modulocliente.aplicacion.ClienteFacade;
import com.lazyledger.backend.modulocliente.presentacion.dto.ClienteSaveRequest;

/**
 * Carga datos iniciales de clientes al iniciar la aplicación.
 */
@Component
public class ClienteDataLoader implements CommandLineRunner {

    private final ClienteFacade clienteFacade;

    public ClienteDataLoader(ClienteFacade clienteFacade) {
        this.clienteFacade = clienteFacade;
    }

    @Override
    public void run(String... args) throws Exception {
        // Verificar si ya hay clientes
        var clientesPage = clienteFacade.getAllClientes(0, 1000);
        if (!clientesPage.getData().isEmpty()) {
            return; // Ya hay datos, no cargar
        }

        // Crear 15 clientes de ejemplo
        for (int i = 1; i <= 15; i++) {
            ClienteSaveRequest request = new ClienteSaveRequest(
                "Cliente" + i,
                "Apellido" + i,
                "cliente" + i + "@example.com",
                i % 2 == 0 ? "INDIVIDUAL" : "EMPRESA",
                "12345678" + i
            );
            clienteFacade.createCliente(request);
        }

    // ...existing code...
    }
}