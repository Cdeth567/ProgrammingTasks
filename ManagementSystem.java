/*
Brief description of the problem:

Implement a University Courses Management System that manages courses, students,
and professors with specific rules and constraints. The system supports commands to
add courses, students, and professors, as well as to enroll/drop students and assign/exempt professors from courses.

Each course has a capacity of 3 students; students can take up to 3 courses, and professors can teach up to 2 courses.
Inputs are processed line by line, validating names, IDs, and constraints.
For each valid command, a success message is printed; on the first invalid
input or rule violation, an appropriate error message is printed, and the program terminates.
*/
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

// The type University course management system.
public class UniversityCourseManagementSystem {
    // The constant allMembers.
    private static ArrayList<UniversityMember> allMembers = new ArrayList<>();
    // The constant allCourses.
    private static ArrayList<Course> allCourses = new ArrayList<>();
    // The constant memberId.
    private static int memberId;
    // The constant COMMANDS.
    private static final List<String> COMMANDS = Arrays.asList("course", "student", "professor", "enroll",
            "drop", "exempt", "teach");
    // The constant courseId.
    private static int courseId;

    /**
     * The entry point of application.
     * @param args the input arguments
     */
    public static void main(String[] args) {
        try {
            fillInitialData();
            Scanner scanner = new Scanner(System.in);
            while (scanner.hasNextLine()) {
                String input = scanner.nextLine();
                switch (input) {
                    case "course":
                        String courseName = scanner.nextLine();
                        CourseLevel courseLevelEnum = null;
                        for (Course allCours : allCourses) {
                            if (allCours.getCourseName().toLowerCase().equals(courseName.toLowerCase())) {
                                System.out.println("Course exists");
                                System.exit(0);
                            }
                        }
                        String courseLevel = scanner.nextLine().toLowerCase();

                        for (String command : COMMANDS) {
                            if (command.equals(courseName)) {
                                System.out.println("Wrong inputs");
                                System.exit(0);
                            }
                        }
                        if (courseLevel.equals("bachelor")) {
                            courseLevelEnum = CourseLevel.BACHELOR;
                        } else if (courseLevel.equals("master")) {
                            courseLevelEnum = CourseLevel.MASTER;
                        } else {
                            System.out.println("Wrong inputs");
                            System.exit(0);
                        }

                        if (courseName.matches("^[a-zA-Z]+(_[a-zA-Z]+)*$")) {
                            allCourses.add(new Course(courseName, courseLevelEnum));
                            System.out.println("Added successfully");
                        } else {
                            System.out.println("Wrong inputs");
                            System.exit(0);
                        }
                        break;
                    case "student":
                        String studentName = scanner.nextLine();
                        for (String command : COMMANDS) {
                            if (command.equals(studentName)) {
                                System.out.println("Wrong inputs");
                                System.exit(0);
                            }
                        }
                        if (studentName.matches("^[a-zA-Z]+$")) {
                            allMembers.add(new Student(allMembers.size() + 1, studentName));
                            System.out.println("Added successfully");
                        } else {
                            System.out.println("Wrong inputs");
                            System.exit(0);
                        }
                        break;
                    case "professor":
                        String professorName = scanner.nextLine();
                        for (String command : COMMANDS) {
                            if (command.equals(professorName)) {
                                System.out.println("Wrong inputs");
                                System.exit(0);
                            }
                        }
                        if (professorName.matches("^[a-zA-Z]+$")) {
                            allMembers.add(new Professor(allMembers.size() + 1, professorName));
                            System.out.println("Added successfully");
                        } else {
                            System.out.println("Wrong inputs");
                            System.exit(0);
                        }
                        break;
                    case "enroll":
                        memberId = scanner.nextInt();
                        courseId = scanner.nextInt();
                        Student student = (Student) allMembers.get(memberId - 1);
                        Course course = allCourses.get(courseId - 1);
                        student.enroll(course);
                        break;
                    case "drop":
                        memberId = scanner.nextInt();
                        courseId = scanner.nextInt();
                        Student student1 = (Student) allMembers.get(memberId - 1);
                        Course course1 = allCourses.get(courseId - 1);
                        student1.drop(course1);
                        System.out.println("Dropped successfully");
                        break;
                    case "exempt":
                        memberId = scanner.nextInt();
                        courseId = scanner.nextInt();
                        Professor professor = (Professor) allMembers.get(memberId - 1);
                        Course course2 = allCourses.get(courseId - 1);
                        professor.exempt(course2);
                        System.out.println("Professor is exempted");
                        break;
                    case "teach":
                        memberId = scanner.nextInt();
                        courseId = scanner.nextInt();
                        Professor professor1 = (Professor) allMembers.get(memberId - 1);
                        Course course3 = allCourses.get(courseId - 1);
                        professor1.teach(course3);
                        System.out.println("Professor is successfully assigned to teach this course");
                        break;
                    default:
                        break;
                }
            }
        } catch (Exception e) {
            System.out.println("Wrong inputs");
        }
    }

    // Fill initial data.
    public static void fillInitialData() {
        Course course1 = new Course("java_beginner", CourseLevel.BACHELOR);
        allCourses.add(course1);
        Course course2 = new Course("java_intermediate", CourseLevel.BACHELOR);
        allCourses.add(course2);
        Course course3 = new Course("python_basics", CourseLevel.BACHELOR);
        allCourses.add(course3);
        Course course4 = new Course("algorithms", CourseLevel.MASTER);
        allCourses.add(course4);
        Course course5 = new Course("advanced_programming", CourseLevel.MASTER);
        allCourses.add(course5);
        Course course6 = new Course("mathematical_analysis", CourseLevel.MASTER);
        allCourses.add(course6);
        Course course7 = new Course("computer_vision", CourseLevel.MASTER);
        allCourses.add(course7);

        Student student1 = new Student(1, "Alice");
        student1.setEnrolledCourses(course1);
        student1.setEnrolledCourses(course2);
        student1.setEnrolledCourses(course3);
        allMembers.add(student1);
        Student student2 = new Student(2, "Bob");
        student2.setEnrolledCourses(course1);
        student1.setEnrolledCourses(course4);
        allMembers.add(student2);
        Student student3 = new Student(2 + 1, "Alex");
        student3.setEnrolledCourses(course5);
        allMembers.add(student3);
        Professor professor1 = new Professor(2 + 2, "Ali");
        professor1.setAssignedCourses(course1);
        professor1.setAssignedCourses(course2);
        allMembers.add(professor1);
        Professor professor2 = new Professor(2 + 2 + 1, "Ahmed");
        professor2.setAssignedCourses(course3);
        professor2.setAssignedCourses(course5);
        allMembers.add(professor2);
        Professor professor3 = new Professor(2 + 2 + 2, "Andrey");
        professor3.setAssignedCourses(course6);
        allMembers.add(professor3);
    }
}

// The type Professor.
class Professor extends UniversityMember {
    // The constant MAX_LOAD.
    private static final int MAX_LOAD = 2;
    // The Assigned courses.
    private final List<Course> assignedCourses = new ArrayList<>();

    /**
     * Sets assigned courses.
     * @param course the course
     */
    public void setAssignedCourses(Course course) {
        this.assignedCourses.add(course);
    }

    /**
     * Instantiates a new Professor.
     *
     * @param memberId   the member id
     * @param memberName the member name
     */
    public Professor(int memberId, String memberName) {
        super(memberId, memberName);
        setMemberName(memberName);
    }

    /**
     * Teach boolean.
     *
     * @param course the course
     * @return the boolean
     */
    public boolean teach(Course course) {
        if (!(assignedCourses.contains(course)) && (assignedCourses.size() < MAX_LOAD)) {
            assignedCourses.add(course);
            return true;
        } else if (assignedCourses.size() >= MAX_LOAD) {
            System.out.println("Professor's load is complete ");
            System.exit(0);
        } else if (assignedCourses.contains(course)) {
            System.out.println("Professor is already teaching this course");
            System.exit(0);
        }
        return false;
    }

    /**
     * Exempt boolean.
     *
     * @param course the course
     * @return the boolean
     */
    public boolean exempt(Course course) {

        if (assignedCourses.contains(course)) {
            assignedCourses.remove(course);
            return true;
        } else if (!(assignedCourses.contains(course))) {
            System.out.println("Professor is not teaching this course");
            System.exit(0);
        }
        return false;
    }
}

// The type University member.
abstract class UniversityMember {
    // The constant numberOfMembers.
    private static int numberOfMembers;
    // The Member id.
    private int memberId;

    // The Member name.
    private String memberName;

    /**
     * Gets member name.
     *
     * @return the member name
     */
    public String getMemberName() {
        return memberName;
    }

    /**
     * Sets member name.
     *
     * @param memberName the member name
     */
    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    /**
     * Instantiates a new University member.
     *
     * @param memberId   the member id
     * @param memberName the member name
     */
    public UniversityMember(int memberId, String memberName) {
        this.memberId = memberId;
        this.memberName = memberName;
    }
}

// The type Student.
class Student extends UniversityMember implements Enrollable {
    // The constant MAX_ENROLLMENT.
    private static final int MAX_ENROLLMENT = 3;
    // The Enrolled courses.
    private List<Course> enrolledCourses = new ArrayList<>();

    /**
     * Instantiates a new Student.
     *
     * @param memberId   the member id
     * @param memberName the member name
     */
    public Student(int memberId, String memberName) {
        super(memberId, memberName);
        setMemberName(memberName);
    }

    /**
     * Gets enrolled courses.
     *
     * @return the enrolled courses
     */
    public List<Course> getEnrolledCourses() {
        return enrolledCourses;
    }

    /**
     * Sets enrolled courses.
     *
     * @param course the course
     */
    public void setEnrolledCourses(Course course) {
        this.enrolledCourses.add(course);
    }

    /**
     * Drop boolean.
     *
     * @param course the course
     * @return the boolean
     */
    @Override
    public boolean drop(Course course) {

        if (enrolledCourses.contains(course)) {
            enrolledCourses.remove(course);
            return true;
        } else {
            System.out.println("Student is not enrolled in this course ");
            System.exit(0);
        }
        return false;
    }

    /**
     * Enroll boolean.
     *
     * @param course the course
     * @return the boolean
     */
    @Override
    public boolean enroll(Course course) {

        if (!(enrolledCourses.contains(course)) && (enrolledCourses.size() != MAX_ENROLLMENT) && (!course.isFull())) {
            enrolledCourses.add(course);
            course.addStudent(this);
            System.out.println("Enrolled successfully");
            return true;
        }
        if (enrolledCourses.contains(course)) {
            System.out.println("Student is already enrolled in this course");
            System.exit(0);
        }
        if (enrolledCourses.size() >= MAX_ENROLLMENT) {
            System.out.println("Maximum enrollment is reached for the student");
            System.exit(0);
        }
        if (course.isFull()) {
            System.out.println("Course is full");
            System.exit(0);
        }
        return false;
    }
}

// The interface Enrollable.
interface Enrollable {
    /**
     * Drop boolean.
     *
     * @param course the course
     * @return the boolean
     */
    public abstract boolean drop(Course course);

    /**
     * Enroll boolean.
     *
     * @param course the course
     * @return the boolean
     */
    public abstract boolean enroll(Course course);
}

// The enum Course level.
enum CourseLevel {
    // Bachelor course level.
    BACHELOR,
    // Master course level.
    MASTER
}

// The type Course.
class Course {
    // The constant CAPACITY.
    private static final int CAPACITY = 3;
    // The constant numberOfCourses.
    private static int numberOfCourses;
    // The Course id.
    private int courseId;
    // The Course name.
    private String courseName;

    /**
     * Gets course name.
     *
     * @return the course name
     */
    public String getCourseName() {
        return courseName;
    }

    // The Enrolled students.
    private List<Student> enrolledStudents = new ArrayList<>();

    /**
     * Add student.
     *
     * @param student the student
     */
    public void addStudent(Student student) {
        enrolledStudents.add(student);
    }

    // The Course level.
    private CourseLevel courseLevel;

    /**
     * Instantiates a new Course.
     *
     * @param courseName  the course name
     * @param courseLevel the course level
     */
    public Course(String courseName, CourseLevel courseLevel) {
        this.courseName = courseName;
        this.courseLevel = courseLevel;
        this.courseId = ++numberOfCourses;
    }

    /**
     * Is full boolean.
     *
     * @return the boolean
     */
    public boolean isFull() {
        if (enrolledStudents.size() >= CAPACITY) {
            System.out.println("Course is full");
            System.exit(0);
        }
        return false;
    }
}
