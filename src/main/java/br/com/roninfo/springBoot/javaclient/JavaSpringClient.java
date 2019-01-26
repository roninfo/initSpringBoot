package br.com.roninfo.springBoot.javaclient;

import br.com.roninfo.springBoot.model.Student;

public class JavaSpringClient {

    public static void main(String[] args) {

        JavaClientDao javaClientDao = new JavaClientDao();

        getUsers(javaClientDao);

        createUsers(javaClientDao);

        Student student = javaClientDao.getStudentFindById(18);
        student.setName("HAUHSHDUHASDHASDHASDJAÇSOJDÇASLKJDÇLKASJDKLASJ");
        student.setEmail("654564643546463246843168764784@asasdasda.com");
        javaClientDao.update(student);

        javaClientDao.delete(20);

    }

    private static void getUsers(JavaClientDao javaClientDao) {
        javaClientDao.getAllListStudents();
        javaClientDao.getAll();
        javaClientDao.getAllStudents();
        javaClientDao.getArrayAllStudents();
        javaClientDao.getStudentFindById(18);
        javaClientDao.getStudentEntityFindById(21);
    }

    private static void createUsers(JavaClientDao javaClientDao) {
        Student student = new Student();
        student.setName("Larissa Palacio");
        student.setEmail("larissapalacio@gmail.com");
        javaClientDao.createStudentEntity(student);

        student.setName("Yohan Palacio");
        student.setEmail("yohanpalacio@gmail.com");
        javaClientDao.createStudentExchange(student);

        student.setName("Dante Palacio");
        student.setEmail("dantepalacio@gmail.com");
        javaClientDao.createStudentObjetc(student);
    }


}
