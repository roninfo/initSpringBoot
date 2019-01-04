package br.com.roninfo.springBoot.javaclient;

import br.com.roninfo.springBoot.model.CustomPageableResponse;
import br.com.roninfo.springBoot.model.Student;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

public class JavaSpringClientPageable {

    public static void main(String[] args) {

        String url = "http://localhost:8080/v1/protected/students";
        String servico = "/";//"/?sort=id,desc";

        RestTemplate restTemplate = new RestTemplateBuilder()
                .rootUri(url)
                .basicAuthentication("root","roninfo")
                .build();

        ResponseEntity<CustomPageableResponse<Student>> exchange = restTemplate.exchange(servico, HttpMethod.GET, null, new ParameterizedTypeReference<CustomPageableResponse<Student>>() {});

        System.out.println("Exchange: " + exchange);
    }
}
