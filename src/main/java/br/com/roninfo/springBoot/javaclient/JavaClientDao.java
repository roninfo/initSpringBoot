package br.com.roninfo.springBoot.javaclient;

import br.com.roninfo.springBoot.model.Student;
import br.com.roninfo.springBoot.util.RestTemplateUtil;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JavaClientDao {

    public Student save(Student student) {
        Student studentObject = RestTemplateUtil.getInstance().getAdmin().postForObject("/", student, Student.class);
        System.out.println(studentObject);
        return studentObject;
    }

    public void update(Student student) {
        RestTemplateUtil.getInstance().getAdmin().put("/", student);
        System.out.println("##UPDATE: "+student);
    }

    public void delete(long id) {
        RestTemplateUtil.getInstance().getAdmin().delete("/{id}", id);
        System.out.println("##DELETE: "+id);
    }

    public void delete(Student student) {
        RestTemplateUtil.getInstance().getAdmin().delete("/{id}", student.getId());
        System.out.println("##DELETE: "+student.getId());
    }

    public Student createStudentExchange(Student student) {
        ResponseEntity<Student> exchangePost = RestTemplateUtil.getInstance().getAdmin().exchange("/", HttpMethod.POST, new HttpEntity<>(student, createJsonHeader()), Student.class);
        System.out.println(exchangePost);
        return exchangePost.getBody();
    }

    public Student createStudentObjetc(Student student) {
        Student studentObject = RestTemplateUtil.getInstance().getAdmin().postForObject("/", student, Student.class);
        System.out.println(studentObject);
        return studentObject;
    }

    public Student createStudentEntity(Student student) {
        ResponseEntity<Student> studentResponseEntity = RestTemplateUtil.getInstance().getAdmin().postForEntity("/", student, Student.class);
        System.out.println(studentResponseEntity);
        return  studentResponseEntity.getBody();
    }

    public Student[] getAll() {
        ResponseEntity<Student[]> forEntity = RestTemplateUtil.getInstance().getUser().getForEntity("/all", Student[].class);
        return forEntity.getBody();
    }

    public Student getStudentFindById(int id) {
        Student student = RestTemplateUtil.getInstance().getUser().getForObject("/{id}", Student.class, id);
        System.out.println("ForObject: "+student);
        return student;
    }

    public ResponseEntity<Student> getStudentEntityFindById(int id) {
        ResponseEntity<Student> studentResponseEntity = RestTemplateUtil.getInstance().getUser().getForEntity("/{id}", Student.class, id);
        System.out.println("Entity body: "+studentResponseEntity.getBody());
        System.out.println("Entity complete: "+studentResponseEntity);
        return  studentResponseEntity;
    }

    public List<Student> getAllStudents() {

        ResponseEntity<List<Student>> exchange = RestTemplateUtil.getInstance().getUser().exchange("/all", HttpMethod.GET, null, new ParameterizedTypeReference<List<Student>>() {});
        System.out.println("Exchange: " + exchange.getBody());
        return exchange.getBody();
    }

    public List<Student> getAllListStudents() {

        ResponseEntity<ArrayList> forEntity = RestTemplateUtil.getInstance().getUser().getForEntity("/all", ArrayList.class);
        System.out.println("Entity complete array: "+ forEntity.getBody());

        return forEntity.getBody();
    }

    public Student[] getArrayAllStudents() {

        ResponseEntity<Student[]> forEntity = RestTemplateUtil.getInstance().getUser().getForEntity("/all", Student[].class);
        System.out.println("Entity complete array: "+ Arrays.toString(forEntity.getBody()));

        return forEntity.getBody();
    }

    private static HttpHeaders createJsonHeader() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return  headers;
    }
}
