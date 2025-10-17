# Registro de Prompts - Proyecto LazyLedger

## Prompt 1: Corrección del Diagrama LedgerModule
Oye, mira este diagrama que tengo del LedgerModule. Está muy mal organizado, tiene demasiada segregación en los use cases y poca agrupación. Necesito que lo corrijas para que tenga mejor agrupación. Por ejemplo, la gestión del ledger debería ser un solo use case, y los objetos internos del LedgerModule deberían estar mejor organizados.

Mira el diagrama actual:LedgerModule.planuml
Mejor agrupalos los USECASES demasiada segregacion y poco tiempo, la gestion del ledger un Usecase, y los objetos interno uni para cada uno en el LedgerModule.planuml

## Prompt 2: Actualización del Dossier Técnico
Oye, necesito que me ayudes a actualizar este documento técnico que tengo, el Mini-Dossier-Tecnico-API.md. Quiero que cambies todos los endpoints que tienen verbos por sustantivos, pero déjame explicarte bien:

Los endpoints de autenticación pueden quedarse con verbos porque es convención (como signup y signin), pero todos los demás quiero que usen sustantivos. Por ejemplo, en lugar de "/clientes/crear" sería "/clientes", en lugar de "/transacciones/agregar" sería "/transacciones", etc.

También hay algo importante que corregir: en el documento dice que se pueden crear clientes manualmente con POST /clientes, pero eso está mal. Los clientes NO se crean manualmente, se crean automáticamente cuando alguien se registra como usuario. Así que quita ese endpoint de crear cliente y explica en el documento que los clientes se crean automáticamente durante el registro.

Y otra cosa: en lugar de usar símbolos pon palabras normales como "Implementado" y "Pendiente" para que se vea más profesional.

¿Me puedes ayudar con esto? Solo actualiza el documento técnico, nada más.