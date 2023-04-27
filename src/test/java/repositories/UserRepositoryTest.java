package repositories;

import models.Post;
import models.User;
import org.junit.jupiter.api.*;
import jakarta.persistence.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

class UserRepositoryTest {

    private EntityManagerFactory emf;
    private EntityManager em;
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        emf = Persistence.createEntityManagerFactory("blog_demo_test");
        em = emf.createEntityManager();
        userRepository = new UserRepository(em);
        em.getTransaction().begin();
        em.createQuery("DELETE FROM User").executeUpdate();
        em.getTransaction().commit();
    }

    @AfterEach
    void tearDown() {
        em.close();
        emf.close();
    }

    @Test
    void createOne() {
        User user = new User("John", "john123", "password");
        long userId = userRepository.createOne(user);
        assertTrue(userId != 0);
        assertEquals(1, userRepository.getAll().size());
    }

    @Test
    void getAll() {
        User user1 = new User("John", "john123", "password");
        User user2 = new User("Jane", "jane123", "password");
        userRepository.createOne(user1);
        userRepository.createOne(user2);
        List<User> users = userRepository.getAll();
        assertEquals(2, users.size());
        assertTrue(users.contains(user1));
        assertTrue(users.contains(user2));
    }

    @Test
    void getOne() {
        User user = new User("John", "john123", "password");
        long userId = userRepository.createOne(user);
        User retrievedUser = userRepository.getOne(userId);
        assertNotNull(retrievedUser);
        assertEquals(user.getName(), retrievedUser.getName());
        assertEquals(user.getUsername(), retrievedUser.getUsername());
        assertEquals(user.getPassword(), retrievedUser.getPassword());
    }

    @Test
    void login() {
        User user = new User("John", "john123", "password");
        userRepository.createOne(user);
        long userId = userRepository.login(user.getUsername(), user.getPassword());
        assertEquals(user.getUserId(), userId);
    }

    @Test
    void signup() {
        long userId = userRepository.signup("John", "john123", "password");
        assertEquals(1, userRepository.getAll().size());
        assertNotNull(userRepository.getOne(userId));
    }

    @Test
    void myPosts() {
        User user = new User("John", "john123", "password");
        userRepository.createOne(user);


        Post post1 = new Post("Title1", "Content1", user);
        Post post2 = new Post("Title2", "Content2", user);
        em.getTransaction().begin();
        em.persist(post1);
        em.persist(post2);
        em.getTransaction().commit();

        user.setPosts(List.of(post2,post1));
        userRepository.update(user);

        List<Post> posts = userRepository.myPosts(user.getUserId());
        assertEquals(2, posts.size());
        assertTrue(posts.contains(post1));
        assertTrue(posts.contains(post2));
    }
}
