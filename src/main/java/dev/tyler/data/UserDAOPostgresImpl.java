package dev.tyler.data;

import dev.tyler.entities.Course;
import dev.tyler.entities.User;
import dev.tyler.utilities.*;

import java.sql.*;

public class UserDAOPostgresImpl implements  UserDAO{

    static CourseDAO courseDAO = new CourseDAOPostgresImpl();

    @Override
    public User createUser(User user) {
        try {
            Connection conn = ConnectionUtil.createConnection();
            String sql = "insert into school_user values (default, ?, ?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, user.getFirstName());
            ps.setString(2, user.getLastName());
            ps.setString(3, user.getUsername());
            ps.setString(4, user.getPassword());
            ps.setString(5, user.getRole());

            ps.execute();
            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            int generatedId = rs.getInt("user_id");
            user.setId(generatedId);
            return user;

        } catch (SQLException e) {
            Logger.log(e.getMessage(), LogLevel.ERROR);
            return null;
        }
    }

    @Override
    public User getUserById(int id) {
        try {
            Connection conn = ConnectionUtil.createConnection();
            String sql = "select * from school_user where user_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();
            rs.next();
            User user = new User();
            user.setId(rs.getInt("user_id"));
            user.setFirstName(rs.getString("firstname"));
            user.setLastName(rs.getString("lastname"));
            user.setUsername(rs.getString("username"));
            user.setPassword(rs.getString("pw"));
            user.setRole(rs.getString("user_role"));
            return user;

        } catch (SQLException e) {
            Logger.log(e.getMessage(), LogLevel.ERROR);
            return null;
        }
    }

    @Override
    public User getUserByUsernamePassword(String username, String password) {
        try {
            Connection conn = ConnectionUtil.createConnection();
            String sql = "select * from school_user where username = ? and pw= ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();
            rs.next();
            User user = new User();
            user.setId(rs.getInt("user_id"));
            user.setFirstName(rs.getString("firstname"));
            user.setLastName(rs.getString("lastname"));
            user.setUsername(rs.getString("username"));
            user.setPassword(rs.getString("pw"));
            user.setRole(rs.getString("user_role"));
            return user;

        } catch (SQLException e) {
            Logger.log(e.getMessage(), LogLevel.ERROR);
            System.out.println("Username or password was incorrect.");
            return null;
        }
    }

    @Override
    public User updateUser(User user) {
        try{
            Connection conn = ConnectionUtil.createConnection();
            String sql = "update school_user set firstname = ?, lastname = ?, " +
                    "username = ?, pw = ?, user_role = ? where user_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, user.getFirstName());
            ps.setString(2, user.getLastName());
            ps.setString(3, user.getUsername());
            ps.setString(4, user.getPassword());
            ps.setString(5, user.getRole());
            ps.setInt(6, user.getId());
            ps.executeUpdate();
            return user;

        } catch (SQLException e){
            Logger.log(e.getMessage(), LogLevel.ERROR);
            return null;
        }
    }

    @Override
    public boolean addUserCourse(int id, int courseId) {
        try{
            Course c = courseDAO.getCourseById(courseId);
            if(c.getAvailability() < c.getCapacity() && System.currentTimeMillis() < c.getRegistrationDate()){
                Connection conn = ConnectionUtil.createConnection();
                String sql = "insert into user_course values (?, ?)";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setInt(1, id);
                ps.setInt(2, courseId);
                ps.executeUpdate();

                sql = "update course set availability = availability + 1 where course_id = ?";
                ps = conn.prepareStatement(sql);
                ps.setInt(1, courseId);
                ps.executeUpdate();
                return true;

            }else{
                return false;
            }
        } catch (SQLException e){
            Logger.log(e.getMessage(), LogLevel.ERROR);
            return false;
        }
    }

    @Override
    public List<User> getAllUsers() {
        try {
            Connection conn = ConnectionUtil.createConnection();
            String sql = "select * from school_user";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            List<User> users = new ArrayList();
            while (rs.next()){
                User user = new User();
                user.setId(rs.getInt("user_id"));
                user.setFirstName(rs.getString("firstname"));
                user.setLastName(rs.getString("lastname"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("pw"));
                user.setRole(rs.getString("user_role"));
                users.add(user);
            }
            return users;

        } catch (SQLException e) {
            Logger.log(e.getMessage(), LogLevel.ERROR);
            return null;
        }
    }

    @Override
    public List<Course> getUserCourses(int id) {
        try {
            Connection conn = ConnectionUtil.createConnection();
            String sql = "select course_id from user_course where user_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            List<Course> courses = new ArrayList();
            while (rs.next()){
                Course course = courseDAO.getCourseById(rs.getInt("course_id"));
                courses.add(course);
            }
            return courses;
        } catch (SQLException e) {
            Logger.log(e.getMessage(), LogLevel.ERROR);
            return null;
        }
    }

    @Override
    public boolean removeUserCourse(int id, int courseId) {
        try{
            Connection conn = ConnectionUtil.createConnection();
            String sql = "delete from user_course where user_id = ? and course_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ps.setInt(2, courseId);
            ps.execute();

            sql = "update course set availability = availability - 1 where course_id = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, courseId);
            ps.executeUpdate();
            return true;

        } catch (SQLException e){
            Logger.log(e.getMessage(), LogLevel.ERROR);
            return false;
        }
    }
}
