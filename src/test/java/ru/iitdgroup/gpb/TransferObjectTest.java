package ru.iitdgroup.gpb;

public class TransferObjectTest {
    // im creating a StudentManger as Business Object
    // Student as Transfer Object representing the entities
    //TransferObjectTest is the test class and will be acting as a client and will use StudentManger and Student to demonstrate the Transfer of the object Object
    public static void main(String[] args) {
        StudentManager studentBusinessObject = new StudentManager();

        //print all the students
        for (StudentVO student : studentBusinessObject.getAllStudents()) {
            System.out.println("Student: [RollNo : " + student.getRollNo() + ", Name : " + student.getName() + " ]");
        }

        //update the student
        StudentVO student = studentBusinessObject.getAllStudents().get(0);
        student.setName("Michael");
        studentBusinessObject.updateStudent(student);

        //gets the student
        student = studentBusinessObject.getStudent(0);
        System.out.println("Student: [RollNo : " + student.getRollNo() + ", Name : " + student.getName() + " ]");
    }
}