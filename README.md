# lazy_ledger



# Requerimientos del Proyecto

Para la recopilación detallada de requisitos para el desarrollo del backend, consulte [requirements.md](requirements.md).

# Arquitectura seleccionada.

Basándonos en los requisitos funcionales y no funcionales, y utilizando Spring Boot para el desarrollo del backend, hemos optado por una Arquitectura de Microservicios orientada a la Web con una API RESTful y una interfaz de Bot de Telegram. Esta elección nos permite alcanzar altos niveles de escalabilidad, modularidad y resiliencia.
Componentes Principales
La arquitectura se compone de los siguientes elementos clave:
Backend (Microservicios con Spring Boot):
Auth & User Service: Gestiona la autenticación, el registro de usuarios y la gestión de perfiles (RF-01, RF-02, RF-03, RF-04, RNF-01). Utiliza Spring Security para la autenticación basada en JWT.
Ledger Service: Maneja la creación, gestión, invitación y asignación de roles dentro de los libros contables (RF-05, RF-06, RF-07, RF-08, RNF-03).
Transaction Service (Opcional/Futuro): Encargado de procesar y registrar transacciones, incluyendo las provenientes de audio (RNF-05).
API Gateway (Spring Cloud Gateway): Punto de entrada unificado para todas las solicitudes. Se encarga del enrutamiento, balanceo de carga, seguridad perimetral (JWT) y garantiza la comunicación a través de HTTPS (RNF-02).
Base de Datos (PostgreSQL):
Una base de datos relacional robusta utilizada por todos los microservicios para almacenar usuarios, libros contables, transacciones, roles y otra información estructurada. Elegida por su fiabilidad y escalabilidad.
Bot de Telegram:
Una interfaz conversacional que permite a los usuarios interactuar con el sistema Lazy Ledger. Proporciona menús y mensajes claros (RNF-06, RNF-07) y se comunica con el backend a través del API Gateway.
ASR & NLP Service (Reconocimiento de Voz y PNL):
Un servicio (interno o externo) dedicado a transformar grabaciones de audio en texto y extraer información relevante para la creación de transacciones contables (RNF-05). Este servicio se integra con el Transaction Service.


# Estándares de codiﬁcación adoptados.

1. Documentación con Javadoc
Utilizamos el estándar Javadoc para la documentación de código, lo que permite generar automáticamente la documentación API del proyecto.
Generación de Documentación: Para generar las páginas web Javadoc, se puede usar un IDE como jGRASP (File -> Generate Documentation) o el comando javadoc desde la línea de comandos de Maven (ver más abajo).
Errores Javadoc: Los errores de Javadoc se mostrarán en la ventana de compilación o en la consola.


2. Reglas Básicas de Codificación
Nombres Descriptivos: Utilizar nombres descriptivos y apropiados para todos los identificadores (variables, métodos, clases, constantes, etc.).
Comentarios Regulares: Se recomienda comentar cada 3-7 líneas de código para explicar lógica compleja.
Claridad y Orden: Mantener el código ordenado y bien estructurado.

3. Nomenclatura y Capitalización de Identificadores
Descriptivos: Siempre usar nombres descriptivos.
Identificadores de Una Letra: Solo permitidos para contadores en bucles (ej. i, j).
Nombres de Clase: Inician con mayúscula (PascalCase). Ej: UserService, LedgerController.
Nombres de Variables: Inician con minúscula (camelCase). Esto incluye parámetros, variables locales y campos de datos.
Excepción: Las constantes (variables final) usan mayúsculas y guiones bajos (UPPER_SNAKE_CASE). Ej: MAX_RETRIES.
Nombres de Métodos: Inician con minúscula (camelCase). Ej: createUser, getLedgerById.
Identificadores Multi-Palabra: Se capitalizan internamente (camelCase/PascalCase) sin usar guiones medios ni guiones bajos (excepto para constantes).
Ejemplo: float sumOfSquares = 0;
Constante: final int DAYS_IN_YEAR = 365;

4. Comentarios: Clases
Toda clase debe ir precedida de un comentario descriptivo utilizando la convención Javadoc. El comentario debe describir el propósito de la clase.
/**
 * Stores the first, middle, and last names for a president. 
 */
class President {
   //code...
}

5. Comentarios: Métodos
Cada definición de método debe ir precedida de un comentario descriptivo utilizando la convención Javadoc. Este debe incluir:
Descripción del Método: Breve explicación de lo que hace el método.
@param: Nombre y descripción de cada parámetro (omitir si no hay parámetros).
@return: Descripción del valor de retorno (omitir si el valor de retorno es void).
@exception: Clase y descripción de cualquier excepción checked que pueda ser lanzada por el método (omitir si no hay excepciones lanzadas, es decir, si se capturan dentro del método).
/**
 * Prints a word, prints a number, and returns integer 1
 *
 * @param word any string of Class String
 * @param number an integer of any value
 * @return the integer 1 
 * @exception MyException if the word starts with the letter 'z'
 * @exception YourException if the number is a zero(0)
 */ 
public static int method1(String word, Integer number) throws MyException, YourException{
  //code...
  if(word.charAt(0) == 'z'){
    throw new MyException();
  }
  if(number == 0){
    throw new YourException();
  }
  try{
    int x = 5 / 0;
  }
  catch(ArithmeticException exception){
    System.out.println("ERROR: Division by zero! " + exception); 
  }
  
  return 1; 
}

6. Comentarios: Variables Públicas
Toda variable pública debe ir precedida de un comentario descriptivo utilizando la convención Javadoc, explicando su propósito.
/** Toggles between frame and no frame mode. */
public boolean frameMode = true;
7. Comentarios: En Línea
Los comentarios en línea (//) deben usarse para explicar secciones de código complicadas, como bucles o algoritmos específicos. No deben usarse para comentar características obvias del lenguaje Java.
// Do a 3D transmogrification on the matrix.
for (int i = 0; i < matrix.length; i++) {
  for (int j = 0; j < matrix.length; j++) {
    for (int k = 0; k < matrix.length; k++) {
      values.transmogrify(matrix[i],matrix[j],matrix[k]);
    }
  }
}

7. Espaciado: Entre Líneas
Métodos: Usar dos líneas en blanco para separar cada método dentro de una definición de clase.
Secciones Lógicas: Usar una línea en blanco para separar secciones lógicas de código dentro de un método.
public static void main(String[] arg) {
    System.out.println("Hello" + " " + "World");
  }


  public void foo() { // Dos líneas en blanco
    // ...
  }

# Flujo de trabajo con GitFlow.

Este proyecto utiliza el flujo de trabajo **GitFlow** para gestionar el código fuente. A continuación se describen las ramas principales:

*   **`main`**: Esta rama contiene el código estable y listo para producción. Cada commit en `main` es una nueva versión.
*   **`develop`**: Es la rama principal de desarrollo. Integra todas las funcionalidades terminadas que se incluirán en la próxima versión.

### Ramas de Soporte

*   **`feature/<nombre-feature>`**: Se crean a partir de `develop` para trabajar en nuevas funcionalidades. Al finalizar, se fusionan de nuevo en `develop`.
    *   Ejemplo: `feature/autenticacion-jwt`
*   **`release/<version>`**: Se crean desde `develop` cuando se prepara un nuevo lanzamiento. Permiten realizar pruebas finales y corregir errores menores. Una vez lista, la rama se fusiona en `main` y `develop`.
    *   Ejemplo: `release/v1.2.0`
*   **`hotfix/<version>`**: Se crean a partir de `main` para solucionar errores críticos en producción. Una vez resuelto el problema, se fusionan tanto en `main` como en `develop`.
    *   Ejemplo: `hotfix/v1.1.1`
    *   
<img width="1168" height="507" alt="image" src="https://github.com/user-attachments/assets/07f10d7f-2578-4e29-a6d7-87621caf30a3" />


# Instrucciones de ejecución

## Opción 1: Spring Boot Run (Desarrollo Local)
1. Asegúrate de tener Java 21+ y Maven instalados.
2. Instala y configura PostgreSQL localmente.
3. Abre una terminal en la carpeta `backend`.
4. Ejecuta:

```
./mvnw spring-boot:run
```

O:

```
mvn spring-boot:run
```

La aplicación se iniciará en el puerto 8080.

## Opción 2: Docker (Producción/Desarrollo Completo)
1. Asegúrate de tener Docker y Docker Compose instalados.
2. Abre una terminal en la carpeta `backend`.
3. Ejecuta:

```
docker compose up --build -d
```

Esto iniciará PostgreSQL y la aplicación.

## TODOs para Inicialización Completa
- TODO: Configurar variables de entorno para producción.
- TODO: Implementar autenticación JWT.
- TODO: Agregar servicios de voz y NLP.
- TODO: Desarrollar frontend y bot de Telegram.

