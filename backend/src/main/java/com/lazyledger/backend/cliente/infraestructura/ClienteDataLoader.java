package com.lazyledger.backend.cliente.infraestructura;

import com.lazyledger.backend.cliente.aplicacion.ClienteFacade;
import com.lazyledger.backend.cliente.presentacion.dto.ClienteSaveRequest;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

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
        var clientes = clienteFacade.getAllClientes();
        if (!clientes.isEmpty()) {
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

        System.out.println("Cargados 15 clientes de ejemplo.");
    }
}