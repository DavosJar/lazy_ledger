package com.lazyledger.backend.commons.especificaciones;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public interface EspecificacionJpa<T> {
    Predicate aPredicado(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb);
}
