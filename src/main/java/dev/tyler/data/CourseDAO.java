package dev.tyler.data;

import dev.tyler.entities.Course;
import dev.tyler.utilities.List;

public interface CourseDAO {

    Course createCourse(Course course);

    Course getCourseById(int id);

    Course updateCourse(Course course);

    boolean deleteCourseById(int id);

    List<Course> getAllCourses();

}
