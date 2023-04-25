package repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class BaseRepository{

    protected final EntityManagerFactory entityManagerFactory;
    protected final EntityManager entityManager;

    public BaseRepository() {
        entityManagerFactory = Persistence.createEntityManagerFactory("blog_crud");
        entityManager = entityManagerFactory.createEntityManager();
    }

    public void close(){
        entityManager.close();
        entityManagerFactory.close();
    }
}
