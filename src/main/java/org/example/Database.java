package org.example;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.hibernate.jpa.HibernatePersistenceProvider;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

public class Database {
    public static EntityManager entityManager = null;

    public static <T> T execute(Function<EntityManager, T> supplier) {
        var provider = new HibernatePersistenceProvider();
        try (var emf = provider.createEntityManagerFactory("test_persistence", Collections.emptyMap())) {
            entityManager = emf.createEntityManager();
            return supplier.apply(entityManager);
        }
    }


    public static <T> List<T> fetch_all(Class<T> entity_class){
        return execute((entityManager) -> {
            CriteriaBuilder builder = entityManager.getCriteriaBuilder();
            CriteriaQuery<T> query = builder.createQuery(entity_class);
            Root<T> variableRoot = query.from(entity_class);
            query.select(variableRoot);
            return entityManager.createQuery(query).getResultList();
        });
    }
}
