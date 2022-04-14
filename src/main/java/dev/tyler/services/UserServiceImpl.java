package dev.tyler.services;

import dev.tyler.data.CourseDAOPostgresImpl;
import dev.tyler.data.UserDAO;
import dev.tyler.entities.Course;
import dev.tyler.entities.User;
import dev.tyler.utilities.List;

public class UserServiceImpl implements UserService{

    private UserDAO userDAO;
    private CourseService courseService = new CourseServiceImpl(new CourseDAOPostgresImpl());

    public UserServiceImpl(UserDAO userDAO){ this.userDAO = userDAO;}

    @Override
    public User addUser(User user) {
        return this.userDAO.createUser(user);
    }

    @Override
    public User login(String username, String password) {
        User user = this.userDAO.getUserByUsernamePassword(username, password);
        return user;
    }

    @Override
    public boolean enroll(int id, int courseId) { return this.userDAO.addUserCourse(id, courseId); }

    @Override
    public boolean dropCourse(int id, int courseId) { return this.userDAO.removeUserCourse(id, courseId); }

    @Override
    public boolean addCourse(Course course) {
        Course c = courseService.addCourse(course);
        if(c != null){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public boolean deleteCourse(int id) {
        boolean deleted = courseService.removeCourse(id);
        return deleted;
    }

    @Override
    public boolean changeCourse(Course course) {
        Course updatedCourse = courseService.updateCourse(course);
        if(updatedCourse != null){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public void viewAllCourses(){
        List<Course> courses = courseService.registrationCatalogue();
        if(courses.size() > 0){
            for(int i = 0; i < courses.size(); i++){
                System.out.println(courses.get(i));
            }
        }
        else{
            System.out.println("No courses available.");
        }
    }

    @Override
    public void viewUserCourses(int id) {
        List<Course> courses = this.userDAO.getUserCourses(id);
        if(courses.size() > 0){
            for(int i = 0; i < courses.size(); i++){
                System.out.println(courses.get(i));
            }
        }
        else{
            System.out.println("Schedule is empty.");
        }
    }
}
