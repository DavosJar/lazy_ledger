package com.lazyledger.backend.deuda.presentacion.mapper;

import org.springframework.stereotype.Component;

import com.lazyledger.backend.deuda.dominio.Deuda;
import com.lazyledger.backend.deuda.presentacion.dto.DeudaDTO;

@Component
public class DeudaMapper {
    
    public DeudaDTO toDTO(Deuda deuda){
        DeudaDTO dto = new DeudaDTO();
        dto.setId(deuda.getId().toString());
        dto.setNombre(deuda.getNombre());
        dto.setMontoTotal(deuda.getMontoTotal());
        dto.setDescripcion(deuda.getDescripcion());
        dto.setFechaCreacion(deuda.getFechaCreacion().toString());
        dto.setFechaVencimiento(deuda.getFechaVencimiento().toString());
        dto.setEstado(deuda.getEstado().name());
        return dto;
    }


    //terminar el mapper de deuda 
    // public Deuda toDomain(Deuda dto){
    //     try {
    //         return Deuda.create(
                
    //         )
    //     } catch ( e) {
    //         // TODO: handle exception
    //     }
    // }
}
