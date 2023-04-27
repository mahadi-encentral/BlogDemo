package drivers;

import java.util.Scanner;

import static drivers.GeneralMenu.showMenu;
import static drivers.UserDriverHandler.*;

public class AppDriver {

    static final Scanner in = new Scanner(System.in);

    public static void main(String[] args) {
        String[] userMenus = {"Signup", "Login", "Exit"};

        int choice = 0;
        long userId;
        while (choice != userMenus.length) {
            choice = showMenu(userMenus, in);
            switch (choice) {
                case 1:
                    userId = handleSignup();
                    if (userId > 0) blogMenu(userId);
                    break;
                case 2:
                    userId = handleLogin(in);
                    if (userId > 0) blogMenu(userId);
                    break;
                case 3:
                    in.close();
                    handleExit();
                    System.out.println("\nGood Bye");
                    break;
            }
        }
    }

    static void blogMenu(long userId) {
        String[] userMenus = {"All Users", "View ALl Posts", "Create Post", "Comment on Post", "My Posts", "Logout"};

        int choice = 0;
        while (choice != userMenus.length) {
            choice = showMenu(userMenus, in);
            switch (choice) {
                case 1:
                    handleAllUsers();
                    break;
                case 2:
                    handleAllPosts();
                    break;
                case 3:
                    handleCreatePost(userId);
                    break;
                case 4:
                    handleCommentOnPost(userId);
                case 5:
                    handleMyPosts(userId);
                    break;
                case 6:
                    System.out.println("Logout Successfully");
                    break;
            }
        }
    }
}
