import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

class Course {
    private String courseCode;
    private String title;
    private String description;
    private int capacity;
    private String schedule;
    private int enrolledStudents;

    public Course(String courseCode, String title, String description, int capacity, String schedule) {
        this.courseCode = courseCode;
        this.title = title;
        this.description = description;
        this.capacity = capacity;
        this.schedule = schedule;
        this.enrolledStudents = 0;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getCapacity() {
        return capacity;
    }

    public String getSchedule() {
        return schedule;
    }

    public int getAvailableSlots() {
        return capacity - enrolledStudents;
    }

    public boolean enrollStudent() {
        if (enrolledStudents < capacity) {
            enrolledStudents++;
            return true;
        }
        return false;
    }

    public boolean dropStudent() {
        if (enrolledStudents > 0) {
            enrolledStudents--;
            return true;
        }
        return false;
    }
}

class Student {
    private String studentId;
    private String name;
    private Set<String> registeredCourses;

    public Student(String studentId, String name) {
        this.studentId = studentId;
        this.name = name;
        this.registeredCourses = new HashSet<>();
    }

    public String getStudentId() {
        return studentId;
    }

    public String getName() {
        return name;
    }

    public Set<String> getRegisteredCourses() {
        return registeredCourses;
    }

    public void registerCourse(String courseCode) {
        registeredCourses.add(courseCode);
    }

    public void dropCourse(String courseCode) {
        registeredCourses.remove(courseCode);
    }
}

public class CourseManagementSystem {
    private Map<String, Course> courses = new HashMap<>();
    private Map<String, Student> students = new HashMap<>();

    public void addCourse(Course course) {
        courses.put(course.getCourseCode(), course);
    }

    public void addStudent(Student student) {
        students.put(student.getStudentId(), student);
    }

    public void displayCourses() {
        for (Course course : courses.values()) {
            System.out.println("Course Code: " + course.getCourseCode());
            System.out.println("Title: " + course.getTitle());
            System.out.println("Description: " + course.getDescription());
            System.out.println("Capacity: " + course.getCapacity());
            System.out.println("Schedule: " + course.getSchedule());
            System.out.println("Available Slots: " + course.getAvailableSlots());
            System.out.println();
        }
    }

    public void registerStudent(String studentId, String courseCode) {
        Student student = students.get(studentId);
        Course course = courses.get(courseCode);

        if (student != null && course != null && course.enrollStudent()) {
            student.registerCourse(courseCode);
            System.out.println("Student " + studentId + " registered for course " + courseCode);
        } else {
            System.out.println("Registration failed for student " + studentId + " and course " + courseCode);
        }
    }

    public void dropCourse(String studentId, String courseCode) {
        Student student = students.get(studentId);
        Course course = courses.get(courseCode);

        if (student != null && course != null && student.getRegisteredCourses().contains(courseCode)) {
            student.dropCourse(courseCode);
            course.dropStudent();
            System.out.println("Student " + studentId + " dropped course " + courseCode);
        } else {
            System.out.println("Course drop failed for student " + studentId + " and course " + courseCode);
        }
    }

    public static void main(String[] args) {
        CourseManagementSystem cms = new CourseManagementSystem();

        // Add some courses
        cms.addCourse(new Course("CS101", "Intro to Computer Science", "Basics of CS", 30, "MWF 9-10 AM"));
        cms.addCourse(new Course("MATH101", "Calculus I", "Introductory calculus", 25, "TTh 11-12:30 PM"));

        // Add some students
        cms.addStudent(new Student("S001", "John Doe"));
        cms.addStudent(new Student("S002", "Jane Smith"));

        // Display courses
        System.out.println("Available Courses:");
        cms.displayCourses();

        // Register students for courses
        cms.registerStudent("S001", "CS101");
        cms.registerStudent("S002", "MATH101");

        // Display courses again to see updated slots
        System.out.println("Available Courses after Registration:");
        cms.displayCourses();

        // Drop a course
        cms.dropCourse("S001", "CS101");

        // Display courses again to see updated slots
        System.out.println("Available Courses after Dropping:");
        cms.displayCourses();
    }
}