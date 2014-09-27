package entity;

import anot.Column;
import anot.Entity;

/**
 * Created by tish on 01.09.2014.
 */
@Entity(name = "Student")
public class Student {
    @Column(name = "student_id")
    private int id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "age")
    private int age;
    @Column(name = "academic_group")
    private String academicGroup;

    public Student(int id, String firstName, String lastName, int age, String academicGroup) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.academicGroup = academicGroup;
    }

    public Student(String firstName, String lastName, int age, String academicGroup) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.academicGroup = academicGroup;
    }

    public Student() {
    }

    public int getId() {

        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {

        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getAcademicGroup() {
        return academicGroup;
    }

    public void setAcademicGroup(String academicGroup) {
        this.academicGroup = academicGroup;
    }
}
