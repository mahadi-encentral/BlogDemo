package repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class BaseRepository {

    private static EntityManagerFactory entityManagerFactory;
    private static EntityManager entityManager;


    public static EntityManager getEntityManager() {
        if (entityManager == null) {
            entityManagerFactory = Persistence.createEntityManagerFactory("blog_crud");
            entityManager = entityManagerFactory.createEntityManager();
        }
        return entityManager;
    }

    public static void close() {
        if (entityManagerFactory != null) {

            entityManager.close();
            entityManagerFactory.close();
        }
        entityManager = null;
        entityManagerFactory = null;
    }
}
