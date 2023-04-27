package repositories;

import static org.junit.jupiter.api.Assertions.*;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import models.Post;
import models.User;


public class PostRepositoryTest {

    private PostRepository postRepository;

    @BeforeEach
    public void setUp() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("blog_demo_test");
        postRepository = new PostRepository(emf.createEntityManager());
    }

    @Test
    public void testCreateAndGetOnePost() {
        // create a user to use as author of the post
        User author = new User("John Doe", "johnDoe1", "password");
        postRepository.getEntityManager().persist(author);

        long userId = postRepository.getEntityManager().merge(author).getUserId();

        // create a new post and persist it
        Post post = new Post("Test Title", "Test Content", author);
        long postId = postRepository.createOne(post);

        // retrieve the post from the repository
        Post retrievedPost = postRepository.getOne(postId);

        // verify that the post was retrieved correctly
        assertNotNull(retrievedPost);
        assertEquals(postId, retrievedPost.getPostId());
        assertEquals(post.getTitle(), retrievedPost.getTitle());
        assertEquals(post.getContent(), retrievedPost.getContent());
        assertEquals(userId, retrievedPost.getAuthor().getUserId());
    }

    @Test
    public void testUpdatePost() {
        // create a user to use as author of the post
        User author = new User("John Doe", "johnDoe2", "password");
        postRepository.getEntityManager().persist(author);
        long userId = postRepository.getEntityManager().merge(author).getUserId();

        // create a new post and persist it
        Post post = new Post("Test Title", "Test Content", author);
        long postId = postRepository.createOne(post);

        // update the post
        post.setTitle("New Title");
        post.setContent("New Content");
        postRepository.update(post);

        // retrieve the post from the repository
        Post retrievedPost = postRepository.getOne(postId);

        // verify that the post was updated correctly
        assertNotNull(retrievedPost);
        assertEquals(postId, retrievedPost.getPostId());
        assertEquals(post.getTitle(), retrievedPost.getTitle());
        assertEquals(post.getContent(), retrievedPost.getContent());
        assertEquals(userId, retrievedPost.getAuthor().getUserId());
    }

    @Test
    public void testDeletePost() {
        // create a user to use as author of the post
        User author = new User("John Doe", "johnDoe3", "password");
        postRepository.getEntityManager().persist(author);

        // create a new post and persist it
        Post post = new Post("Test Title", "Test Content", author);
        long postId = postRepository.createOne(post);

        // delete the post
        postRepository.delete(post);

        // retrieve the post from the repository
        Post retrievedPost = postRepository.getOne(postId);

        // verify that the post was deleted
        assertNull(retrievedPost);
    }
}
