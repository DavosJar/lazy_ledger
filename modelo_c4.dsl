workspace "LazyLedger" "plataforma de contabilidad inteligente para pequeñas y medianas empresas" {

    model {
        user = person "Usuario Contable" "Gestiona su contabilidad de forma simple y colaborativa." {
            tags "Usuario"
        }
        admin = person "Administrador" "Gestiona y monitoriza la salud de la plataforma." {
            tags "Admin"
        }
        telegram = softwareSystem "Telegram API" "Plataforma de mensajería para la interacción con el bot." {
            tags "Externo" "Telegram"
        }

        googleAI = softwareSystem "Google AI Platform" "Provee modelos LLM (Gemini) para el procesamiento de datos no estructurados." {
            tags "Externo" "Google"
        }
        lazyLedger = softwareSystem "LazyLedger" "Asistente contable para registro automatizado, análisis y colaboración." {
            tags "Interno"

            webApp = container "Aplicación Web" "Aplicación web para la gestión contable (usuario y administrador)." "Angular 17, TypeScript 5.0" {
                tags "WebApp"
                guiComponent = component "GUI Component" "Interfaz de usuario para gestión contable." "Angular 17, TypeScript 5.0" {
                    tags "GUI"
                }
            }

            mobileApp = container "Aplicación Móvil" "Aplicación móvil para la gestión contable." "Kotlin 1.9, Android SDK" {
                tags "MobileApp"
            }

            api = container "API Backend" "API REST para lógica de negocio y procesamiento." "Spring Boot 3.1, Java 21" {
                tags "API"

                // Arquitectura hexagonal - Capas principales
                presentacionLayer = component "Presentación Layer" "Controladores REST y DTOs para APIs." "Spring Web, Java 21" {
                    tags "Presentation"
                }

                aplicacionLayer = component "Aplicación Layer" "Casos de uso y lógica de aplicación." "Spring Boot, Java 21" {
                    tags "Application"
                }

                dominioLayer = component "Dominio Layer" "Modelo de dominio y reglas de negocio." "Java 21" {
                    tags "Domain"
                }

                infraestructuraLayer = component "Infraestructura Layer" "Repositorios JPA y configuración." "Spring Data JPA, Java 21" {
                    tags "Infrastructure"
                }

                // Módulos de negocio
                clienteModule = component "Cliente Module" "Módulo de gestión de clientes (persona/empresa)." "Spring Boot, Java 21" {
                    tags "Module"
                }

                securityModule = component "Security Module" "Módulo de seguridad y autenticación JWT." "Spring Security, Java 21" {
                    tags "Security"
                }

                ledgerModule = component "Ledger Module" "Módulo de gestión de ledger y operaciones contables." "Spring Boot, Java 21" {
                    tags "Module"
                }
            }

            db = container "Base de Datos" "Almacena transacciones, usuarios y datos contables." "PostgreSQL 16.9" {
                tags "Database"
            }

            bot = container "Servicio de Bot Telegram" "Maneja interacciones con el bot de Telegram." "Spring Boot 3.1, Java 21" {
                tags "Bot"
            }
            aiService = container "Servicio de IA" "Procesa datos no estructurados usando modelos LLM." "Spring Boot 3.1, Java 21" {
                tags "AI"
            }
        }
        user -> telegram "Interactúa con el bot de Telegram"
        lazyLedger -> telegram "Envía y recibe mensajes"
        lazyLedger -> googleAI "Solicita análisis y procesamiento de datos"
        user -> lazyLedger "Usa la aplicación web/móvil"
        admin -> lazyLedger "Monitoriza y mantiene el sistema"

        user -> webApp "Usa la aplicación web"
        user -> mobileApp "Usa la aplicación móvil"
        admin -> webApp "Usa la aplicación web"
        webApp -> api "Hace llamadas API"
        mobileApp -> api "Hace llamadas API"
        api -> db "Lee/escribe datos"
        api -> bot "Envía mensajes"
        bot -> telegram "Interactúa"
        api -> aiService "Solicita análisis"
        aiService -> googleAI "Usa modelos LLM"

        // Relaciones de arquitectura hexagonal
        presentacionLayer -> aplicacionLayer "Invoca casos de uso"
        aplicacionLayer -> dominioLayer "Implementa reglas de negocio"
        aplicacionLayer -> infraestructuraLayer "Accede a datos"
        infraestructuraLayer -> db "Persiste entidades JPA"

        // Relaciones entre módulos
        clienteModule -> presentacionLayer "Expone APIs REST"
        clienteModule -> aplicacionLayer "Implementa lógica de aplicación"
        clienteModule -> dominioLayer "Define modelo de dominio"
        clienteModule -> infraestructuraLayer "Accede a repositorios"

        ledgerModule -> presentacionLayer "Expone APIs REST"
        ledgerModule -> aplicacionLayer "Implementa lógica de aplicación"
        ledgerModule -> dominioLayer "Define modelo de dominio"
        ledgerModule -> infraestructuraLayer "Accede a repositorios"

        securityModule -> aplicacionLayer "Proporciona autenticación/autorización"
        securityModule -> infraestructuraLayer "Gestiona tokens JWT"

        // Relaciones con la GUI

        // Relaciones con servicios externos
        aiService -> api "Procesa datos no estructurados"
        bot -> api "Registra datos vía bot"
    }
    

    configuration {
       
    }
    views {
        systemContext lazyLedger "SystemContext" "Diagrama de Contexto del Sistema para LazyLedger." {
            include user admin telegram googleAI lazyLedger
            autoLayout lr
        }
        container lazyLedger "Container" "Diagrama de Contenedores para LazyLedger." {
            include *
            autoLayout lr
        }
        component api "Component" "Diagrama de Componentes para LazyLedger API Backend." {
            include *
            autoLayout lr
        }
        component webApp "WebAppComponent" "Diagrama de Componentes para LazyLedger Web App." {
            include *
            autoLayout lr
        }
        styles {
            element "Usuario" {
                shape Person
                background #00008B
                color #ffffff
            }
            element "Admin" {
                shape Person
                background #006400
                color #ffffff
            }
            element "Telegram" {
                background #CD853F
                color #ffffff
            }
            element "Google" {
                background #CD853F
                color #ffffff
            }
            element "Interno" {
                background #C9C9C9
                color #000000
            }
            element "WebApp" {
                background #4682B4
                color #ffffff
            }
            element "MobileApp" {
                background #4682B4
                color #ffffff
            }
            element "API" {
                background #228B22
                color #ffffff
            }
            element "Database" {
                shape Cylinder
                background #708090
                color #ffffff
            }
            element "Bot" {
                background #D2691E
                color #ffffff
            }
            element "AI" {
                background #6A5ACD
                color #ffffff
            }
            element "Component" {
                background #32CD32
                color #000000
            }
            element "Module" {
                background #FFD700
                color #000000
            }
            element "Repository" {
                background #FF6347
                color #ffffff
            }
            element "Security" {
                background #DC143C
                color #ffffff
            }
            element "GUI" {
                background #00CED1
                color #000000
            }
        }
    }
}