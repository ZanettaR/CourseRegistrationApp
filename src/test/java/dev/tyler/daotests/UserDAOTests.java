package dev.tyler.daotests;

import dev.tyler.data.UserDAO;
import dev.tyler.data.UserDAOPostgresImpl;
import dev.tyler.entities.Course;
import dev.tyler.entities.User;
import dev.tyler.utilities.List;
import org.junit.jupiter.api.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserDAOTests {
    static UserDAO userDAO = new UserDAOPostgresImpl();
    static User testUser = null;

    @Test
    @Order(1)
    void create_user_test(){
        User zee = new User(0, "Zanetta" , "Tyler", "zeerii", "Rii114!", "student");
        User savedUser = userDAO.createUser(zee);
        UserDAOTests.testUser = savedUser;
        Assertions.assertNotEquals(0, savedUser.getId());
    }

    @Test
    @Order(2)
    void get_user_by_id(){
        User retrievedUser = userDAO.getUserById(testUser.getId());
        Assertions.assertEquals("Zanetta", retrievedUser.getFirstName());
    }

    @Test
    @Order(3)
    void get_user_by_username_password(){
        User retrievedUser = userDAO.getUserByUsernamePassword(testUser.getUsername(), testUser.getPassword());
        Assertions.assertEquals("Zanetta", retrievedUser.getFirstName());
    }

    @Test
    @Order(4)
    void update_user(){
        UserDAOTests.testUser.setUsername("zeetee");
        userDAO.updateUser(testUser);
        User retrievedUser = userDAO.getUserById(testUser.getId());
        Assertions.assertEquals("zeetee", retrievedUser.getUsername());
    }

    @Test
    @Order(5)
    void add_user_course(){
        boolean result = userDAO.addUserCourse(testUser.getId(), 2);
        Assertions.assertTrue(result);
    }

    @Test
    @Order(6)
    void get_all_users(){
        List<User> users = userDAO.getAllUsers();
        int totalUsers = users.size();
        Assertions.assertTrue(totalUsers >= 1);
    }

    @Test
    @Order(7)
    void get_user_courses(){
        List<Course> userCourses = userDAO.getUserCourses(testUser.getId());
        int totalCourses = userCourses.size();
        Assertions.assertTrue(totalCourses >= 1);
    }


    @Test
    @Order(8)
    void remove_user_course(){
        boolean result = userDAO.removeUserCourse(testUser.getId(), 2);
        Assertions.assertTrue(result);
    }
}
