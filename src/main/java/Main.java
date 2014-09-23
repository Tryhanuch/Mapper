import entity.Student;

import java.io.FileNotFoundException;

/**
 * Created by tish on 01.09.2014.
 */
public class Main {
    public static void main(String[] args) throws IllegalAccessException, FileNotFoundException {

        Student st = new Student(5, "Alex", "Ololosh", 23, "DZ-81");

        FileMapper fm = new FileMapper();
        fm.save(st);


//        List<Object> all = fm.loadAll(Student.class);
//        for (int i = 0; i < all.size(); i++) {
//            Student s = (Student) all.get(i);
//            System.out.println(s.getLastName() + " " + s.getId());
//        }

//        Student student = (Student)fm.load(3, Student.class);
//        System.out.println(student.getLastName() + " " + student.getId());
//
//        student.setLastName("Push-push");
//        fm.update(3, student);
//
//        Student student1 = (Student)fm.load(3, Student.class);
//        System.out.println(student1.getLastName() + " " + student1.getId());

//        fm.delete(3, Student.class);

    }
}
