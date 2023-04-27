package repositories;

import com.querydsl.jpa.impl.JPAQuery;
import interfaces.CrudOperations;
import jakarta.persistence.EntityManager;
import models.Post;
import models.QUser;
import models.User;

import java.util.List;

public class UserRepository implements CrudOperations<User> {

    private final EntityManager entityManager;
    private static final QUser Q_USER = QUser.user;
    private final JPAQuery<User> query;

    public UserRepository() {
        this(BaseRepository.getEntityManager());
    }

    public UserRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.query = new JPAQuery<>(entityManager);
    }

    @Override
    public long createOne(User data) {
        entityManager.getTransaction().begin();
        entityManager.persist(data);
        entityManager.getTransaction().commit();
        return data.getUserId();
    }

    @Override
    public List<User> getAll() {

        return query.from(Q_USER).fetch();
    }

    @Override
    public User getOne(long id) {
        return query.from(Q_USER).where(Q_USER.userId.eq(id)).fetchOne();
    }

    @Override
    public User update(User data) {
        return null;
    }

    @Override
    public void delete(User data) {

    }

    public long login(String username, String password) {
        User user = query.from(Q_USER).where(Q_USER.username.equalsIgnoreCase(username).and(Q_USER.password.eq(password))).fetchOne();
        return user != null ? user.getUserId() : 0;
    }

    public long signup(String name, String username, String password) {
        return createOne(new User(name, username, password));
    }

    public List<Post> myPosts(long userId) {
        User user = getOne(userId);
        if (user != null) {
            return user.getPosts();
        }
        return null;
    }

}
