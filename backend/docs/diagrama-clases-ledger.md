@startuml
package "moduloLedger" {
  class Ledger {
    nombre: String
    descripcion: String
    estado: EstadoLedger
    fechaCreacion: Date
    fechaCierre: Date
    cerrado: boolean
    saldoTotal: float

    cerrar()
    reabrir()
    cambiarEstado(nuevoEstado)
  }

  class MiembroLedger {
    rol: RolMiembro
    activo: boolean
    fechaIngreso: Date

    cambiarRol(nuevoRol)
    expulsar()
    aceptarInvitacion()
    rechazarInvitacion()
  }

  class Transaccion {
    descripcion: String
    monto: float
    fecha: Date
    categoria: CategoriaTransaccion
    estado: EstadoTransaccion

    modificar(descripcion, monto)
    cancelar()
  }

  class Deuda {
    descripcion: String
    monto: float
    fechaCreacion: Date
    fechaVencimiento: Date
    pagada: boolean

    registrarPago(monto)
    marcarComoPagada()
  }

  class Objetivo {
    descripcion: String
    meta: float
    progreso: float
    fechaLimite: Date
    cumplido: boolean

    avanzar(monto)
    completar()
  }

  enum EstadoLedger {
    ACTIVO
    INACTIVO
    CERRADO
  }

  enum RolMiembro {
    PROPIETARIO
    ADMINISTRADOR
    MIEMBRO
  }

  enum CategoriaTransaccion {
    ALIMENTACION
    TRANSPORTE
    VIVIENDA
    SALUD
    ENTRETENIMIENTO
    EDUCACION
    OTROS
  }

  enum EstadoTransaccion {
    COMPLETADA
    PENDIENTE
    CANCELADA
  }

  Ledger "1" *-- "0..*" MiembroLedger
  Ledger "1" *-- "0..*" Transaccion
  Ledger "1" *-- "0..*" Deuda
  Ledger "1" *-- "0..*" Objetivo
  
  Ledger --> EstadoLedger
  MiembroLedger --> RolMiembro
  Transaccion --> CategoriaTransaccion
  Transaccion --> EstadoTransaccion
}

package "moduloCliente" {
  class Cliente {
    nombre: String
    apellido: String
    email: String
    tipo: TipoCliente
    telefono: String

    actualizar(nombre, apellido, telefono)
    cambiarTipo(nuevoTipo)
  }

  enum TipoCliente {
    PERSONA
    EMPRESA
  }
  
  Cliente --> TipoCliente
}

package "moduloSeguridad" {
  class User {
    username: String
    contrasenaEncriptada: String
    ultimaConexion: Date
    activo: boolean

    registrarConexion()
    activar()
    desactivar()
  }
}

package "moduloAuditoria" {
  class Evento {
    entidad: String
    operacion: String
    fecha: Date
    usuario: String
    estadoAnterior: String
    estadoNuevo: String

    registrarCambio()
  }

  class Error {
    tipoError: String
    mensaje: String
    fecha: Date
    stackTrace: String
    recurrente: boolean

    marcarRecurrente()
    registrarOcurrencia()
  }
}

MiembroLedger --> Cliente
Cliente "1" *-- "1" User : tiene

moduloAuditoria ..> moduloLedger : audita
moduloAuditoria ..> moduloCliente : audita
moduloAuditoria ..> moduloSeguridad : audita

note right of Ledger
Aunque cada clase maneja su propio ciclo de vida con alta cohesión,
MiembroLedger, Transaccion, Deuda y Objetivo solo pueden ser gestionados
a través de un Ledger. Si un Ledger es eliminado, todos sus objetos
internos también desaparecen (composición).
end note

note right of moduloAuditoria
Evento: Registra cada cambio en el ciclo de vida de un objeto
(creación, modificación, eliminación, cambios de estado).

Error: Rastrea errores recurrentes del sistema y los separa
de los errores de usuario para facilitar el diagnóstico y
la resolución de problemas técnicos.
end note
@enduml