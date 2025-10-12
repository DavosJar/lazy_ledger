package com.lazyledger.backend.api;

/**
 * Interfaz base para assemblers que construyen respuestas API con HATEOAS.
 * Define el patrón común para ensamblar cualquier objeto T en ApiResponse<T>.
 */
public interface BaseAssembler<T> {

    /**
     * Ensambla un objeto T en una ApiResponse<T> con enlaces HATEOAS.
     * @param dto el objeto a ensamblar
     * @return ApiResponse con el objeto y enlaces
     */
    ApiResponse<T> assemble(T dto);

    /**
     * Agrega enlaces comunes a la respuesta (método default para extensión).
     * @param response la respuesta a la que agregar enlaces
     */
    default void addCommonLinks(ApiResponse<T> response) {
        // Implementación por defecto vacía; módulos específicos pueden agregar enlaces comunes si es necesario
    }
}