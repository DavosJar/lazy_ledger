# lazy_ledger
# Requerimientos del Proyecto

## Requisitos Funcionales (RF)

### Gestión de Usuarios
- **R.F-01**: El sistema debe permitir a los usuarios nuevos crear una cuenta utilizando correo electrónico y contraseña.  
  **Condición**: Si el correo ya está registrado, se mostrará el mensaje:  
  "Vaya... El correo ingresado ya se encuentra actualmente en uso."
- **R.F-02**: El sistema autentica las credenciales del usuario.
- **R.F-03**: El sistema debe permitir a los usuarios iniciar sesión usando las credenciales (correo electrónico y contraseña).
- **R.F-04**: El sistema debe permitir la gestión del perfil del usuario (editar nombre, correo, contraseña).

### Gestión de Libros
- **R.F-05**: El sistema debe permitir al usuario autenticado crear un nuevo libro contable.  
  **Condición**: Al momento de crear el libro contable, el usuario tiene el rol de Owner.
- **R.F-06**: El Owner puede invitar a otros usuarios.  
  **Condición**: Únicamente se podrá invitar usuarios que ya estén registrados en la app.
- **R.F-07**: El Owner podrá asignar roles dentro del libro contable (analista o asistente) a los usuarios.
- **R.F-08**: El Owner podrá gestionar el libro contable (editar, invitar, eliminar, agregar).

## Requisitos No Funcionales (RNF)

### Seguridad
- **R.NF-01**: Todas las contraseñas deben almacenarse hasheadas y salteadas (ej. bcrypt).
- **R.NF-02**: Toda la comunicación externa con la API debe ser a través de HTTPS.
- **R.NF-03**: El acceso a los datos de un Ledger debe estar estrictamente limitado a sus miembros autorizados.

### Rendimiento
- **R.NF-04**: Las respuestas de la API para consultas de listas no deben superar los 500ms en condiciones de carga media.
- **R.NF-05**: El tiempo total desde que un usuario envía un audio hasta que recibe la confirmación no debe superar los 5 segundos.

### Usabilidad
- **R.NF-06**: El bot de Telegram debe proporcionar menús y botones para guiar al usuario en las acciones comunes.
- **R.NF-07**: Los mensajes de error presentados al usuario deben ser claros y no técnicos.

### Disponibilidad
- **R.NF-08**: El servicio debe aspirar a una disponibilidad del 95%.

### Escalabilidad
- **R.NF-09**: La arquitectura debe ser capaz de soportar un incremento de 10x en el número de usuarios y transacciones sin necesidad de un rediseño fundamental.


# Arquitectura seleccionada.

Basándonos en los requisitos y el uso de Spring Boot, la arquitectura que mejor se adapta es una Arquitectura de Microservicios orientada a la Web con una API RESTful y una interfaz de Bot de Telegram. Esto permitirá una alta escalabilidad, modularidad y resiliencia.
Componentes Principales:
Backend (Spring Boot Microservicios):
Servicio de Autenticación y Usuarios (Auth & User Service):
Responsabilidades: RF-01, RF-02, RF-03, RF-04, RNF-01 (Hash de contraseñas).
Tecnología: Spring Boot, Spring Security (JWT para tokens de sesión/autenticación).
Almacenamiento: Base de datos relacional (PostgreSQL) para usuarios, roles y credenciales.
Servicio de Libros Contables (Ledger Service):
Responsabilidades: RF-05, RF-06, RF-07, RF-08, RNF-03.
Tecnología: Spring Boot, Spring Data JPA.
Almacenamiento: Base de datos relacional (PostgreSQL) para libros, invitaciones, roles de usuario en libros y transacciones (si es que las transacciones se gestionan aquí, o se delegan a un servicio de Transacciones).
(Opcional) Servicio de Transacciones (Transaction Service):
Responsabilidades: Procesamiento de transacciones de audio (RF-05, asumiendo que es donde se ingresan los datos), RNF-05 (tiempo de respuesta de audio).
Tecnología: Spring Boot. Podría integrar un motor de reconocimiento de voz (ASR) externo o interno y un módulo de procesamiento de lenguaje natural (NLP), quizás utilizando Spring AI o integrando modelos de OpenAI/Google Cloud Speech-to-Text/etc.
Almacenamiento: Base de datos relacional (PostgreSQL) para detalles de transacciones.
Gateway API (API Gateway - Spring Cloud Gateway/Netflix Zuul):
Responsabilidades: RNF-02 (HTTPS), RNF-04 (Coordinación de respuestas), Balanceo de carga, enrutamiento de peticiones a microservicios, seguridad perimetral (autenticación JWT).
Tecnología: Spring Cloud Gateway (recomendado para Spring Boot 3+).
Beneficios: Punto de entrada unificado para clientes, gestión de seguridad y tráfico.
Base de Datos (PostgreSQL):
Tipo: Relacional (SQL).
Propósito: Almacenar usuarios, libros contables, transacciones, roles y cualquier otra información estructurada.
Escalabilidad: Se puede escalar horizontalmente con replicación y lectura de réplicas.
Tecnología: PostgreSQL (elegido por su robustez, funcionalidades avanzadas y popularidad en entornos de producción).
Bot de Telegram (Telegram Bot API):
Responsabilidades: RNF-06, RNF-07. Interacción con el usuario, recepción de comandos y audio.
Tecnología: Librería cliente de Telegram (puede ser implementada dentro de un microservicio de Spring Boot o como una aplicación separada si la lógica es compleja).
Interacción: Se comunicará con el Backend a través de la API Gateway.
Servicio de Reconocimiento de Voz y PNL (ASR/NLP Service):
Responsabilidades: Transformar audio a texto y extraer información relevante para crear transacciones contables. RNF-05.
Tecnología: Puede ser un servicio externo (ej. Google Cloud Speech-to-Text, OpenAI Whisper) o un módulo/microservicio interno si se utilizan librerías de ML.
Comunicación: El Bot de Telegram enviará el audio a este servicio (o a un microservicio de Backend que lo orqueste), el cual luego enviará el texto procesado al Ledger/Transaction Service.

# Estándares de codiﬁcación adoptados.



# Flujo de trabajo con GitFlow.
# Instrucciones de ejecución.
