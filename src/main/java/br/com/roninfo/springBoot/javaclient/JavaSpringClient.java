package br.com.roninfo.springBoot.javaclient;

import br.com.roninfo.springBoot.model.Student;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

public class JavaSpringClient {

    public static void main(String[] args) {

        String url = "http://localhost:8080/v1/protected/students";

        RestTemplate restTemplate = new RestTemplateBuilder()
                .rootUri(url)
                .basicAuthentication("root","roninfo")
                .build();

        Student student = restTemplate.getForObject("/{id}", Student.class, 17);

        ResponseEntity<Student> studentResponseEntity = restTemplate.getForEntity("/{id}", Student.class, 18);

        ResponseEntity<Student[]> forEntity = restTemplate.getForEntity("/all", Student[].class);

        ResponseEntity<List<Student>> exchange = restTemplate.exchange("/all", HttpMethod.GET, null, new ParameterizedTypeReference<List<Student>>() {});

        System.out.println("ForObject: "+student);
        System.out.println("Entity body: "+studentResponseEntity.getBody());
        System.out.println("Entity complete: "+studentResponseEntity);

        System.out.println("Entity complete array: "+Arrays.toString(forEntity.getBody()));

        System.out.println("Exchange: " + exchange.getBody());
    }
}
