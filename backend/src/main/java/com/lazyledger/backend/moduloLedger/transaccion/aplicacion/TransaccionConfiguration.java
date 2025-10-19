package com.lazyledger.backend.moduloLedger.transaccion.aplicacion;

import com.lazyledger.backend.commons.IdGenerator;
import com.lazyledger.backend.moduloLedger.ledger.domainServices.LedgerTransaccionService;
import com.lazyledger.backend.moduloLedger.ledger.dominio.repositorio.LedgerRepository;
import com.lazyledger.backend.moduloLedger.miembroLedger.dominio.repositorio.MiembroLedgerRepository;
import com.lazyledger.backend.moduloLedger.transaccion.dominio.repositorio.TransaccionRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TransaccionConfiguration {

    @Bean
    public LedgerTransaccionService ledgerTransaccionService(
            TransaccionRepository transaccionRepository,
            LedgerRepository ledgerRepository,
            MiembroLedgerRepository miembroLedgerRepository) {
        return new LedgerTransaccionService(transaccionRepository, ledgerRepository, miembroLedgerRepository);
    }

    @Bean
    public CrearTransaccionUseCase crearTransaccionUseCase(
            LedgerTransaccionService ledgerTransaccionService,
            IdGenerator idGenerator) {
        return new CrearTransaccionUseCase(ledgerTransaccionService, idGenerator);
    }
}
