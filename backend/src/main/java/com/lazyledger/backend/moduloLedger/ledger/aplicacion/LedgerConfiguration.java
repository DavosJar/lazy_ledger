package com.lazyledger.backend.moduloLedger.ledger.aplicacion;

import com.lazyledger.backend.commons.IdGenerator;
import com.lazyledger.backend.moduloLedger.ledger.dominio.repositorio.LedgerRepository;
import com.lazyledger.backend.moduloLedger.miembroLedger.dominio.repositorio.MiembroLedgerRepository;
import com.lazyledger.backend.modulocliente.dominio.repositorio.ClienteRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuración de beans para el módulo de Ledger.
 * Crea las instancias de los casos de uso con sus dependencias.
 */
@Configuration
public class LedgerConfiguration {

    @Bean
    public CrearLedgerUseCase crearLedgerUseCase(LedgerRepository ledgerRepository,
                                                  MiembroLedgerRepository miembroLedgerRepository,
                                                  ClienteRepository clienteRepository,
                                                  IdGenerator idGenerator) {
        return new CrearLedgerUseCase(ledgerRepository, miembroLedgerRepository, clienteRepository, idGenerator);
    }
}
