package dev.tyler.services;

import dev.tyler.data.CourseDAO;
import dev.tyler.entities.Course;
import dev.tyler.utilities.List;

public class CourseServiceImpl implements CourseService{
    private CourseDAO courseDAO;

    public CourseServiceImpl(CourseDAO courseDAO){ this.courseDAO = courseDAO; }

    @Override
    public Course addCourse(Course course) {
        course.setRegistrationDate(System.currentTimeMillis() + ((long)26*7*24*60*60*1000));
        return this.courseDAO.createCourse(course);
    }

    @Override
    public boolean removeCourse(int id) {
        return this.courseDAO.deleteCourseById(id);
    }

    @Override
    public Course updateCourse(Course course) {
        return this.courseDAO.updateCourse(course);
    }

    @Override
    public List<Course> registrationCatalogue() { return this.courseDAO.getAllCourses(); }
}
