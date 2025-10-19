package com.lazyledger.backend.moduloLedger.miembroLedger.aplicacion;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.lazyledger.backend.moduloLedger.miembroLedger.dominio.MiembroLedgerService;
import com.lazyledger.backend.moduloLedger.miembroLedger.dominio.repositorio.MiembroLedgerRepository;
import com.lazyledger.backend.modulocliente.dominio.repositorio.ClienteRepository;

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

        @Bean
        public AceptarInvitacionUseCase aceptarInvitacionUseCase(MiembroLedgerRepository miembroLedgerRepository) {
            return new AceptarInvitacionUseCase(miembroLedgerRepository);
        }

        @Bean
        public RechazarInvitacionUseCase rechazarInvitacionUseCase(MiembroLedgerRepository miembroLedgerRepository) {
            return new RechazarInvitacionUseCase(miembroLedgerRepository);
        }

        @Bean
        public ListarInvitacionesPendientesUseCase listarInvitacionesPendientesUseCase(MiembroLedgerRepository miembroLedgerRepository) {
            return new ListarInvitacionesPendientesUseCase(miembroLedgerRepository);
        }
}