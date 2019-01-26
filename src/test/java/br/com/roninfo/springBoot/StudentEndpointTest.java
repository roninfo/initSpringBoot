package br.com.roninfo.springBoot;

import br.com.roninfo.springBoot.model.Student;
import br.com.roninfo.springBoot.repository.StudentRepository;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class StudentEndpointTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentRepository studentRepository;

    @LocalServerPort
    private int porta;

    @TestConfiguration
    static class Config {
        @Bean
        public RestTemplateBuilder restTemplateBuilder() {
            return new RestTemplateBuilder().basicAuthentication("root","roninfo");
        }
    }

    /**
     * WARNING: An illegal reflective access operation has occurred
     * WARNING: Illegal reflective access by org.apache.catalina.loader.WebappClassLoaderBase (file:/home/roninfo/.m2/repository/org/apache/tomcat/embed/tomcat-embed-core/9.0.13/tomcat-embed-core-9.0.13.jar) to field java.io.ObjectStreamClass$Caches.localDescs
     * WARNING: Please consider reporting this to the maintainers of org.apache.catalina.loader.WebappClassLoaderBase
     * WARNING: Use --illegal-access=warn to enable warnings of further illegal reflective access operations
     * WARNING: All illegal access operations will be denied in a future releas
     */
    @Test
    public void credentialsInvalidReturnStatusCode401() {
        System.out.println("######### "+porta);
        restTemplate = restTemplate.withBasicAuth("a", "b");
        ResponseEntity<String> forEntity = restTemplate.getForEntity("/v1/protected/students/", String.class);
        Assertions.assertThat(forEntity.getStatusCodeValue()).isEqualTo(401);
    }

    @Test
    public void credentialsValidReturnStatusCode200() {
        System.out.println("######### "+porta);
        Student student = new Student("Ronizera", "roninfo@ronizera.com");
        BDDMockito.when(studentRepository.findById(10l)).thenReturn(Optional.of(student));

        ResponseEntity<Student> forEntity = restTemplate.getForEntity("/v1/protected/students/{id}", Student.class, 10);
        Assertions.assertThat(forEntity.getStatusCodeValue()).isEqualTo(200);
        Assertions.assertThat(forEntity.getBody().getName()).isEqualTo("Ronizera");
    }

    @Test
    public void studentNotExistsReturnStatusCode404() {
        System.out.println("######### "+porta);
        Student student = new Student("Ronizera", "roninfo@ronizera.com");
        BDDMockito.when(studentRepository.findById(10l)).thenReturn(Optional.of(student));

        ResponseEntity<Student> forEntity = restTemplate.getForEntity("/v1/protected/students/{id}", Student.class, -1);
        Assertions.assertThat(forEntity.getStatusCodeValue()).isEqualTo(404);
    }

    @Test
    @WithMockUser(username = "a", password = "b")
    public void deleteWhenUserHasRoleAdminReturnStatusCode403() throws Exception {
        BDDMockito.doNothing().when(studentRepository).deleteById(50l);
        mockMvc.perform(MockMvcRequestBuilders
                .delete("/v1/admin/students/{id}", 9999l))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }
}
