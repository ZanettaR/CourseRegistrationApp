package dev.tyler.data;

import dev.tyler.entities.Course;
import dev.tyler.utilities.*;

import java.sql.*;

public class CourseDAOPostgresImpl implements CourseDAO{

    @Override
    public Course createCourse(Course course) {
        try {
            Connection conn = ConnectionUtil.createConnection();
            String sql = "insert into course values (default, ?, ?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, course.getCourseName());
            ps.setString(2, course.getDescription());
            ps.setInt(3, course.getCapacity());
            ps.setInt(4, course.getAvailability());
            ps.setLong(5, course.getRegistrationDate());
            ps.execute();
            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            int generatedId = rs.getInt("course_id");
            course.setId(generatedId);
            return course;

        } catch (SQLException e) {
            Logger.log(e.getMessage(), LogLevel.ERROR);
            return null;
        }
    }

    @Override
    public Course getCourseById(int id) {
        try {
            Connection conn = ConnectionUtil.createConnection();
            String sql = "select * from course where course_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();
            rs.next();
            Course course = new Course();
            course.setId(rs.getInt("course_id"));
            course.setCourseName(rs.getString("course_name"));
            course.setDescription(rs.getString("description"));
            course.setCapacity(rs.getInt("capacity"));
            course.setAvailability(rs.getInt("availability"));
            course.setRegistrationDate(rs.getLong("registration_date"));
            return course;

        } catch (SQLException e) {
            Logger.log(e.getMessage(), LogLevel.ERROR);
            return null;
        }
    }

    @Override
    public Course updateCourse(Course course) {
        try{
            Connection conn = ConnectionUtil.createConnection();
            String sql = "update course set course_name = ?, description = ?, " +
                         "capacity = ?, availability = ?, registration_date = ? where course_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, course.getCourseName());
            ps.setString(2, course.getDescription());
            ps.setInt(3, course.getCapacity());
            ps.setInt(4, course.getAvailability());
            ps.setLong(5, course.getRegistrationDate());
            ps.setInt(6, course.getId());
            ps.executeUpdate();
            return course;

        } catch (SQLException e){
            Logger.log(e.getMessage(), LogLevel.ERROR);
            return null;
        }
    }

    @Override
    public boolean deleteCourseById(int id) {
        try{
            Connection conn = ConnectionUtil.createConnection();
            String sql = "delete from course where course_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ps.execute();

            sql = "delete from user_course where course_id = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ps.execute();
            return true;

        } catch (SQLException e){
            Logger.log(e.getMessage(), LogLevel.ERROR);
            return false;
        }
    }

    @Override
    public List<Course> getAllCourses() {
        try {
            Connection conn = ConnectionUtil.createConnection();
            String sql = "select * from course";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            List<Course> courses = new ArrayList();
            while (rs.next()){
                Course course = new Course();
                course.setId(rs.getInt("course_id"));
                course.setCourseName(rs.getString("course_name"));
                course.setDescription(rs.getString("description"));
                course.setCapacity(rs.getInt("capacity"));
                course.setAvailability(rs.getInt("availability"));
                course.setRegistrationDate(rs.getLong("registration_date"));
                courses.add(course);
            }
            return courses;

        } catch (SQLException e) {
            Logger.log(e.getMessage(), LogLevel.ERROR);
            return null;
        }
    }
}
