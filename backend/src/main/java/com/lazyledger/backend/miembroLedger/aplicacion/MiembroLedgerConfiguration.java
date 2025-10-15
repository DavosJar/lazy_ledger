package com.lazyledger.backend.miembroLedger.aplicacion;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.lazyledger.backend.cliente.dominio.repositorio.ClienteRepository;
import com.lazyledger.backend.miembroLedger.dominio.MiembroLedgerService;
import com.lazyledger.backend.miembroLedger.dominio.rerpositorio.MiembroLedgerRepository;

@Configuration
public class MiembroLedgerConfiguration {

    @Bean
    public InvitarMiembroUseCase invitarMiembroUseCase(MiembroLedgerRepository miembroLedgerRepository,
                                                       MiembroLedgerService miembroLedgerService,
                                                       ClienteRepository clienteRepository) {
        return new InvitarMiembroUseCase(miembroLedgerRepository, miembroLedgerService, clienteRepository);
    }

    @Bean
    public CambiarRolMiembroUseCase cambiarRolMiembroUseCase(MiembroLedgerRepository miembroLedgerRepository,
                                                             MiembroLedgerService miembroLedgerService) {
        return new CambiarRolMiembroUseCase(miembroLedgerRepository, miembroLedgerService);
    }

    @Bean
    public ExpulsarMiembroUseCase expulsarMiembroUseCase(MiembroLedgerRepository miembroLedgerRepository,
                                                         MiembroLedgerService miembroLedgerService) {
        return new ExpulsarMiembroUseCase(miembroLedgerRepository, miembroLedgerService);
    }

    @Bean
    public EliminarLedgerUseCase eliminarLedgerUseCase(MiembroLedgerRepository miembroLedgerRepository,
                                                       MiembroLedgerService miembroLedgerService) {
        return new EliminarLedgerUseCase(miembroLedgerRepository, miembroLedgerService);
    }

    @Bean
    public MiembroLedgerService miembroLedgerService() {
        return new MiembroLedgerService();
    }
}