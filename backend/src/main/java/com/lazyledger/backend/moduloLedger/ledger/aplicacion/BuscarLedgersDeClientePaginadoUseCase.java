package com.lazyledger.backend.moduloLedger.ledger.aplicacion;

import com.lazyledger.backend.commons.identificadores.ClienteId;
import com.lazyledger.backend.moduloLedger.ledger.dominio.Ledger;
import com.lazyledger.backend.moduloLedger.ledger.dominio.repositorio.LedgerRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class BuscarLedgersDeClientePaginadoUseCase {
    private final LedgerRepository ledgerRepository;

    public BuscarLedgersDeClientePaginadoUseCase(LedgerRepository ledgerRepository) {
        this.ledgerRepository = ledgerRepository;
    }

    public Page<Ledger> ejecutar(ClienteId clienteId, String nombre, Pageable pageable) {
        return ledgerRepository.buscarPorClienteConFiltros(clienteId.value(), nombre, pageable);
    }
}