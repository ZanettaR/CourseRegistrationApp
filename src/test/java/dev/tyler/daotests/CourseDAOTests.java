package dev.tyler.daotests;


import dev.tyler.data.CourseDAO;
import dev.tyler.data.CourseDAOPostgresImpl;
import dev.tyler.entities.Course;
import dev.tyler.utilities.List;
import org.junit.jupiter.api.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CourseDAOTests {

    static CourseDAO courseDAO = new CourseDAOPostgresImpl();
    static Course testCourse= null;
    @Test
    @Order(1)
    void create_course_test(){
        Course psych = new Course(0, "Comp411" , " ", 30, 0, 0 );
        psych.setRegistrationDate(System.currentTimeMillis() + ((long)26*7*24*60*60*1000));
        Course savedCourse = courseDAO.createCourse(psych);
        CourseDAOTests.testCourse = savedCourse;
        Assertions.assertNotEquals(0, savedCourse.getId());
    }

    @Test
    @Order(2)
    void get_course_by_id(){
        Course retrievedCourse = courseDAO.getCourseById(testCourse.getId());
        Assertions.assertEquals("Psych102", retrievedCourse.getCourseName());
    }

    @Test
    @Order(3)
    void update_course(){
        CourseDAOTests.testCourse.setDescription("A more advanced intro to psychology.");
        courseDAO.updateCourse(testCourse);
        Course retrievedCourse = courseDAO.getCourseById(testCourse.getId());
        Assertions.assertEquals("A more advanced intro to psychology.", retrievedCourse.getDescription());
    }

    @Test
    @Order(4)
    void get_all_courses(){
        List<Course> courses = courseDAO.getAllCourses();
        int totalCourses = courses.size();
        Assertions.assertTrue(totalCourses >= 1);
    }
}
