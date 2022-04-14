package dev.tyler.services;

import dev.tyler.entities.Course;
import dev.tyler.utilities.List;

public interface CourseService {

    Course addCourse(Course course);

    boolean removeCourse(int id);

    Course updateCourse(Course course);

    List<Course> registrationCatalogue();

}
