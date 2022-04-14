package dev.tyler.data;

import dev.tyler.entities.Course;
import dev.tyler.entities.User;
import dev.tyler.utilities.List;

public interface UserDAO {

    User createUser(User user);

    User getUserById(int id);

    User getUserByUsernamePassword(String username, String password);

    User updateUser(User user);

    boolean addUserCourse(int id, int courseId);

    List<User> getAllUsers();

    List<Course> getUserCourses(int id);

    boolean removeUserCourse(int id, int courseId);
}
