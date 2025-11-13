@startuml
' Diagrama de paquetes - Módulo Ledger (Clean Architecture)

package "presentacion.ledger" {
  [LedgerController]
}

package "aplicacion.ledger" {
  [LedgerFacade]
  [LedgerUseCase]
}

package "dominio.ledger" {
  [Ledger]
  [LedgerService]
  interface LedgerRepository
}

package "infraestructura.ledger" {
  [LedgerRepositoryImpl]
}

' Dependencias
[LedgerController] --> [LedgerFacade] : Usa
[LedgerFacade] --> [LedgerUseCase] : Orquesta
[LedgerUseCase] --> [LedgerService] : Invoca lógica
[LedgerService] ..> LedgerRepository : Depende de interfaz
[LedgerRepositoryImpl] ..|> LedgerRepository : Implementa

' Infraestructura depende de dominio solo por interfaces
[LedgerRepositoryImpl] ..> Ledger : Mapea entidades

' Notas
note right of LedgerRepository
  Interfaz expuesta por dominio.
  Implementada en infraestructura.
end note

note right of LedgerController
  Expone endpoints REST.
end note

@enduml

