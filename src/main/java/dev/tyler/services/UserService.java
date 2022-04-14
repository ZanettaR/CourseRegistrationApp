package dev.tyler.services;

import dev.tyler.entities.Course;
import dev.tyler.entities.User;

public interface UserService {
    User addUser(User user);
    User login(String username, String password);
    boolean enroll(int id, int courseId);
    boolean dropCourse(int id, int courseId);
    boolean addCourse(Course course);
    boolean deleteCourse(int id);
    boolean changeCourse(Course course);
    void viewAllCourses();
    void viewUserCourses(int id);
}
