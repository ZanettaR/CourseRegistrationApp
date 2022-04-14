package dev.tyler.api;

import dev.tyler.data.UserDAOPostgresImpl;
import dev.tyler.entities.Course;
import dev.tyler.entities.User;
import dev.tyler.services.UserService;
import dev.tyler.services.UserServiceImpl;

import java.util.Scanner;

public class App {

    public static UserService userService = new UserServiceImpl(new UserDAOPostgresImpl());

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.println("(1) Sign in \n(2) Register as new user");
        try{
            int choice = scan.nextInt();
            User user;
            if(choice != 1){
                user = registerNewUser(scan);
            }else{
                System.out.println("Username: ");
                String username = scan.next();
                System.out.println("Password: ");
                String password = scan.next();
                user = App.userService.login(username, password);
            }

            switch(user.getRole()) {
                case "faculty": {
                    facultyOperations(user, scan);
                }break;
                case "student": {
                    studentOperations(user, scan);
                }break;
                default:
                    System.out.println("Invalid Choice");
            }
        }catch(Exception e){
        }
    }

    public static void facultyOperations(User user, Scanner scanner){
        printGreeting(user.getFirstName(), user.getLastName());
        boolean loggedOut = false;
        String action = "";
        while(!loggedOut){
            System.out.println("1. View Course Catalog" +
                    "\n2. Add Course \n3. Delete Course" +
                    "\n4. Update Course \n5. Logout"
            );
            System.out.println("Please select an option (1-5): ");
            int choice = scanner.nextInt();
            switch (choice){
                case 1: {
                    App.userService.viewAllCourses();
                }break;
                case 2: {
                    action = "Course creation";
                    Course course = new Course();
                    boolean added;
                    System.out.println("Please enter course details.");
                    try {
                        System.out.println("Course Name: ");
                        course.setCourseName(scanner.next());
                        System.out.println("Course Description: ");
                        course.setDescription(scanner.next());
                        System.out.println("Course Capacity: ");
                        String c = scanner.nextLine();
                        course.setCapacity(Integer.parseInt(c));
                        added = App.userService.addCourse(course);
                    }catch(Exception e){
                        added = false;
                    }
                    if(added){ successMessage(action);}else{failureMessage(action);}
                }break;
                case 3: {
                    action = "Course deletion";
                    System.out.println("Course ID: ");
                    boolean deleted;
                    try {
                        choice = scanner.nextInt();
                        deleted = App.userService.deleteCourse(choice);
                    }catch(Exception e){
                        deleted = false;
                    }
                    if(deleted){ successMessage(action);}else{failureMessage(action);}
                }break;
                case 4: {
                    action = "Course update";
                    Course course = new Course();
                    boolean updated;
                    try {
                        System.out.println("Course ID: ");
                        course.setId(scanner.nextInt());
                        System.out.println("Course Name: ");
                        course.setCourseName(scanner.next());
                        System.out.println("Course Description: ");
                        course.setDescription(scanner.nextLine());
                        updated = App.userService.changeCourse(course);
                    }catch (Exception e){
                        updated = false;
                    }
                    if(updated){ successMessage(action);}else{failureMessage(action);}
                }break;
                case 5: {
                    loggedOut = true;
                    successMessage("Log out");
                }break;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    public static void studentOperations(User user, Scanner scanner){
        printGreeting(user.getFirstName(), user.getLastName());
        boolean loggedOut = false;
        String action = "";
        while(!loggedOut){
            System.out.println("1. View Course Catalog \n2. View Enrolled Courses" +
                    "\n3. Enroll \n4. Drop Course \n5. Logout"
            );
            System.out.println("Please select an option (1-5): ");
            int choice = scanner.nextInt();
            switch (choice){
                case 1: {
                    App.userService.viewAllCourses();
                }break;
                case 2: {
                    App.userService.viewUserCourses(user.getId());
                }break;
                case 3: {
                    action = "Course enrollment";
                    System.out.println("Course ID: ");
                    choice = scanner.nextInt();
                    boolean dropped;
                    try{
                        dropped = App.userService.enroll(user.getId(), choice);
                    }catch (Exception e){
                        dropped = false;
                    }
                    if(dropped){ successMessage(action);}else{failureMessage(action);}
                }break;
                case 4: {
                    action = "Course drop";
                    System.out.println("Course ID: ");
                    boolean dropped;
                    try {
                        choice = scanner.nextInt();
                        dropped = App.userService.dropCourse(user.getId(), choice);
                    }catch (Exception e){
                        dropped = false;
                    }
                    if(dropped){ successMessage(action);}else{failureMessage(action);}
                }break;
                case 5: {
                    loggedOut = true;
                    successMessage("Log out");
                }break;
                default: System.out.println("Invalid choice.");
            }
        }
    }

    public static User registerNewUser(Scanner scanner){
        try{
            System.out.println("New User Registration: \nPlease answer the questions.");
            User user = new User();
            System.out.println("First Name: ");
            user.setFirstName(scanner.next());
            System.out.println("Last Name: ");
            user.setLastName(scanner.next());
            System.out.println("Username: ");
            user.setUsername(scanner.next());
            System.out.println("Password: ");
            user.setPassword(scanner.next());
            System.out.println("Are you a (1) student or (2) staff? ");
            int choice = scanner.nextInt();
            String role = choice == 1 ? "student" : "faculty";
            user.setRole(role);
            App.userService.addUser(user);
            return user;
        }catch (Exception e){
            System.out.println("Error occured. Please try again later.");
            return new User();
        }
    }

    private static void printGreeting(String firstName, String lastName){
        System.out.println("Welcome, " + firstName +" " + lastName);
    }

    private static void successMessage(String action){
        System.out.println(action + " successful.");
    }

    private static void failureMessage(String action){
        System.out.println(action + " failed.");
    }
}
