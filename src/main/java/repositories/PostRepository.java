package repositories;

import com.querydsl.jpa.impl.JPAQuery;
import interfaces.CrudOperations;
import jakarta.persistence.EntityManager;
import models.Comment;
import models.Post;
import models.QPost;

import java.util.List;

public class PostRepository implements CrudOperations<Post> {

    private static final QPost Q_POST = QPost.post;


    private final EntityManager entityManager;
    private final JPAQuery<Post> query;

    public PostRepository() {
        this(BaseRepository.getEntityManager());
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public PostRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.query = new JPAQuery<>(entityManager);
    }

    @Override
    public long createOne(Post data) {
        entityManager.getTransaction().begin();
        entityManager.persist(data);
        entityManager.getTransaction().commit();
        return data.getPostId();
    }

    @Override
    public List<Post> getAll() {
        return query.from(Q_POST).fetch();
    }

    @Override
    public Post getOne(long id) {
        return query.from(Q_POST).where(Q_POST.postId.eq(id)).fetchOne();
    }

    @Override
    public Post update(Post data) {
        entityManager.getTransaction().begin();
        Post template = getOne(data.getPostId());
        template.setTitle(data.getTitle());
        template.setContent(data.getContent());
        entityManager.getTransaction().commit();
        return template;

    }

    @Override
    public void delete(Post data) {
        entityManager.getTransaction().begin();
        entityManager.remove(data);
        entityManager.getTransaction().commit();
    }

    public Post commentOnPost(long postId, Comment comment) {
        entityManager.getTransaction().begin();
        Post template = getOne(postId);
        template.getComments().add(comment);
        entityManager.persist(comment);
        entityManager.getTransaction().commit();
        return template;
    }

}
