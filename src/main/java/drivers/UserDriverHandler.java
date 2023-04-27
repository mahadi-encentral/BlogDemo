package drivers;

import models.Comment;
import models.Post;
import models.User;
import org.apache.log4j.Logger;
import repositories.BaseRepository;
import repositories.PostRepository;
import repositories.UserRepository;

import java.util.List;
import java.util.Scanner;

public class UserDriverHandler {
    private static final UserRepository repository = new UserRepository();
    private static final PostRepository post_repository = new PostRepository();
    private static final Logger logger = Logger.getLogger(UserDriverHandler.class);

    static long handleLogin(Scanner read) {
        System.out.print("Username: ");
        String username = read.nextLine();
        System.out.print("Password: ");
        String password = read.nextLine();
        long success = repository.login(username, password);
        if (success == 0) logger.error("Invalid Credentials");
        return success;
    }

    static long handleSignup() {
        System.out.print("Name: ");
        String name = AppDriver.in.nextLine();
        System.out.print("Username: ");
        String username = AppDriver.in.nextLine();
        System.out.print("Password: ");
        String password = AppDriver.in.nextLine();
        return repository.signup(name, username, password);
    }

    static void handleViewPosts(Scanner read) {
        post_repository.getAll().forEach(System.out::println);
    }

    static void handleMyPosts(long userId) {
        List<Post> myPosts = repository.myPosts(userId);
        if (myPosts == null) {
            logger.error("User not found");
            return;
        }
        myPosts.forEach(System.out::println);
    }

    static void handleCreatePost(long userId) {
        System.out.print("Title: ");
        String title = AppDriver.in.nextLine();
        System.out.print("Content: ");
        String content = AppDriver.in.nextLine();
        User user = repository.getOne(userId);
        if (user == null) {
            logger.error("User does not Exist!!");
            return;
        }
        Post newPost = new Post(title, content, user);
        user.getPosts().add(newPost);
        post_repository.createOne(newPost);
        logger.info("Post created successfully");
    }

    static void handleAllUsers() {
        repository.getAll().forEach(System.out::println);
    }

    static void handleCommentOnPost(long userId) {
        System.out.print("Post ID: ");
        long postId = AppDriver.in.nextLong();
        AppDriver.in.nextLine();
        User user = repository.getOne(userId);
        Post post = post_repository.getOne(postId);

        if (post == null || user == null) {
            logger.error("User or Post not found!");
            return;
        }
        System.out.print("comment: ");
        String commentText = AppDriver.in.nextLine();
        Comment comment = new Comment(commentText, post, user);
        post_repository.commentOnPost(postId, comment);
        logger.info("Commented Successfully");
    }

    static void handleAllPosts() {
        post_repository.getAll().forEach(System.out::println);
    }

    static void handleExit() {
        logger.info("User logout successfully");
        BaseRepository.close();
    }
}
