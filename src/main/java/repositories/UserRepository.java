package repositories;

import interfaces.CrudOperations;
import models.User;

import java.util.List;

public class UserRepository extends BaseRepository implements CrudOperations<User> {

    public User login(String username, String password){
        return null;
    }

    public long signup(String name, String username, String password)
    {
        return createOne(new User(name, username, password));
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
        return null;
    }

    @Override
    public User getOne(long id) {
        return null;
    }

    @Override
    public User update(User data) {
        return null;
    }

    @Override
    public void delete(User data) {

    }
}
