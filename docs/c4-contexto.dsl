workspace "Lazy Ledger" "Sistema Inteligente de Asistencia Financiera Personal y Colaborativa" {

    model {
        // Actores
        cliente = person "Cliente" "Usuario que gestiona sus finanzas personales o grupales mediante formularios o lenguaje natural"
        admin = person "Administrador" "Gestiona la configuración y operación del sistema"
        
        // Sistemas Externos
        chatTerceros = softwareSystem "Plataformas de Chat" "WhatsApp, Telegram, Slack - Canales de comunicación externos" "External"
        llm = softwareSystem "Modelo de Lenguaje (LLM)" "GPT, Claude, Llama u otros - Procesamiento de lenguaje natural mediante MCP" "External"
        
        // Sistema Principal
        lazyLedger = softwareSystem "Lazy Ledger" "Sistema inteligente de asistencia financiera que permite registrar, analizar y consultar movimientos mediante interfaces tradicionales y conversacionales" {
            description "Asistente financiero con doble interfaz: formularios tradicionales y chat con IA"
            
            // Contenedores (Nivel 2)
            webApp = container "Aplicación Web" "Interfaz de usuario web para registro y consulta mediante formularios tradicionales" "React/Vue.js" "WebBrowser"
            mobileApp = container "Aplicación Móvil" "Interfaz nativa para dispositivos móviles con acceso completo al sistema" "iOS/Android" "Mobile"
            
            backend = container "API Backend" "Proporciona servicios REST, lógica de negocio, autenticación y procesamiento de datos" "Spring Boot / Java" "Backend" {
                // Componentes (Nivel 3) - Arquitectura real
                
                // Capa de Seguridad (Borde)
                seguridadFilter = component "Filtro de Seguridad" "Intercepta todas las peticiones, valida JWT, autentica y autoriza" "Spring Security Filter"
                
                // Controladores REST (API)
                controladores = component "Controladores REST" "Exponen endpoints REST, validan requests, delegan a servicios" "Spring REST Controllers"
                
                // MÓDULOS DE DOMINIO
                moduloUsuario = component "Módulo Usuario" "Gestión de clientes, perfiles, autenticación de usuarios" "Spring Module"
                moduloSeguridad = component "Módulo Seguridad" "Control de acceso, roles, auditoría, bloqueo de intentos fallidos" "Spring Security Module"
                moduloLedger = component "Módulo Ledger" "Gestión de ledgers, movimientos, objetivos, deudas, miembros" "Spring Module"
                
                // Adaptadores de Integración
                adaptadorWebhook = component "Adaptador Webhook" "Recibe webhooks de plataformas de chat (WhatsApp, Telegram, Slack)" "Spring REST Controller"
                moduloMCP = component "Módulo MCP" "Cliente MCP para comunicación con LLM, procesamiento de lenguaje natural" "MCP Client"
                
                // Repositorios
                repositorios = component "Repositorios" "Persistencia de datos, acceso a base de datos (JPA)" "Spring Data JPA"
                
                // Relaciones entre componentes
                seguridadFilter -> controladores "Pasa peticiones autenticadas"
                seguridadFilter -> adaptadorWebhook "Valida webhooks externos"
                
                controladores -> moduloUsuario "Gestiona usuarios y clientes"
                controladores -> moduloLedger "Gestiona ledgers y movimientos"
                controladores -> moduloSeguridad "Solicita validación de acceso"
                
                moduloSeguridad -> moduloUsuario "Valida credenciales y permisos"
                moduloLedger -> moduloUsuario "Obtiene información de miembros"
                
                adaptadorWebhook -> moduloLedger "Registra movimientos desde chat"
                adaptadorWebhook -> moduloMCP "Solicita interpretación de texto"
                
                moduloMCP -> moduloLedger "Envía entidades extraídas para registro"
                
                moduloUsuario -> repositorios "Persiste usuarios y clientes"
                moduloSeguridad -> repositorios "Registra auditoría"
                moduloLedger -> repositorios "Persiste ledgers, movimientos, objetivos, deudas"
            }
            
            database = container "Base de Datos" "Almacena usuarios, ledgers, movimientos, objetivos y deudas" "PostgreSQL" "Database"
            
            // Relaciones internas entre contenedores
            webApp -> backend "Realiza llamadas API" "HTTPS/JSON"
            mobileApp -> backend "Realiza llamadas API" "HTTPS/JSON"
            backend -> database "Lee y escribe datos" "SQL/JDBC"
            
            // RELACIONES DE COMPONENTES CON ELEMENTOS EXTERNOS
            // (Definidas dentro del sistema pero fuera del contenedor para que sean visibles en el diagrama de componentes)
            
            // Interfaces gráficas interactúan con componentes específicos
            webApp -> seguridadFilter "Envía peticiones HTTP autenticadas" "HTTPS/JSON"
            mobileApp -> seguridadFilter "Envía peticiones HTTP autenticadas" "HTTPS/JSON"
            
            // Persistencia
            repositorios -> database "CRUD de entidades" "JPA/JDBC"
            
            // Integración con LLM
            moduloMCP -> llm "Solicita interpretación de texto" "MCP Protocol"
            llm -> moduloMCP "Retorna entidades extraídas" "MCP Response"
            
            // Integración con plataformas de mensajería
            chatTerceros -> adaptadorWebhook "POST webhooks con mensajes" "HTTPS/JSON"
        }

        // Relaciones con usuarios y sistemas externos
        cliente -> webApp "Usa navegador web" "HTTPS"
        cliente -> mobileApp "Usa aplicación móvil" "HTTPS"
        cliente -> chatTerceros "Interactúa mediante lenguaje natural" "Mensajes de texto"

        admin -> webApp "Administra sistema mediante panel web" "HTTPS"
    }

    views {
        systemContext lazyLedger "DiagramaContexto" {
            include *
            autoLayout
            description "Diagrama de Contexto (C4 - Nivel 1) del Sistema Lazy Ledger"
        }
        
        container lazyLedger "DiagramaContenedores" {
            include *
            autoLayout
            description "Diagrama de Contenedores (C4 - Nivel 2) del Sistema Lazy Ledger"
        }
        
        component backend "DiagramaComponentes" {
            include *
            include database llm chatTerceros webApp mobileApp
            include "webApp->seguridadFilter"
            include "mobileApp->seguridadFilter"
            include "repositorios->database"
            include "moduloMCP->llm"
            include "llm->moduloMCP"
            include "chatTerceros->adaptadorWebhook"
            autoLayout
            description "Diagrama de Componentes (C4 - Nivel 3) del Backend - Módulos de dominio (Usuario, Seguridad, Ledger, MCP) con integraciones externas y interfaces gráficas"
        }

        styles {
            element "Person" {
                shape person
                background #08427b
                color #ffffff
            }
            element "Software System" {
                background #1168bd
                color #ffffff
            }
            element "External" {
                background #999999
                color #ffffff
            }
            element "WebBrowser" {
                shape WebBrowser
                background #438dd5
                color #ffffff
            }
            element "Mobile" {
                shape MobileDevicePortrait
                background #438dd5
                color #ffffff
            }
            element "Backend" {
                background #1168bd
                color #ffffff
            }
            element "Database" {
                shape Cylinder
                background #438dd5
                color #ffffff
            }
            element "Component" {
                background #85bbf0
                color #000000
            }
        }
    }
}

