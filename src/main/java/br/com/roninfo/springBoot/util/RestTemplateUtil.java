package br.com.roninfo.springBoot.util;

import br.com.roninfo.springBoot.handler.RestResponseExcetionHandler;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.client.RestTemplate;

public class RestTemplateUtil {

    private static RestTemplateUtil INSTANCE = new RestTemplateUtil();

    public static RestTemplateUtil getInstance() {
        return INSTANCE;
    }

    public RestTemplate getUser() {

        RestTemplate restTemplateUser = new RestTemplateBuilder()
                .rootUri("http://localhost:8080/v1/protected/students")
                .basicAuthentication("roninfo","roninfo")
                .errorHandler(new RestResponseExcetionHandler())
                .build();
        return restTemplateUser;
    }

    public RestTemplate getAdmin() {
        RestTemplate restTemplateAdmin = new RestTemplateBuilder()
                .rootUri("http://localhost:8080/v1/admin/students")
                .basicAuthentication("root","roninfo")
                .errorHandler(new RestResponseExcetionHandler())
                .build();
        return restTemplateAdmin;
    }

}
