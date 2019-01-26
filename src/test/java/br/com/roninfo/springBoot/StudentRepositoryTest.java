package br.com.roninfo.springBoot;

import br.com.roninfo.springBoot.model.Student;
import br.com.roninfo.springBoot.repository.StudentRepository;
import org.assertj.core.api.Assertions;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

@RunWith(SpringRunner.class)
@DataJpaTest
public class StudentRepositoryTest {

    @Autowired
    private StudentRepository studentRepository;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void createShouldPersistData() {
        Student student = new Student("Iguana mesa", "iguana@gmail.com");
        this.studentRepository.save(student);
        Assertions.assertThat(student.getId()).isNotNull();
        Assertions.assertThat(student.getEmail()).isEqualTo("iguana@gmail.com");
        Assertions.assertThat(student.getName()).isEqualTo("Iguana mesa");
    }

    @Test
    public void deleteShouldRemoveData() {
        Student student = new Student("Roni", "roninfo@gmail.com");
        this.studentRepository.save(student);
        this.studentRepository.delete(student);
        Optional<Student> studentOptional = studentRepository.findById(student.getId());
        Assertions.assertThat(studentOptional.isPresent()).isFalse();
    }

    @Test
    public void updateShouldUpdateData() {
        Student student = new Student("Roni", "roninfo@gmail.com");
        this.studentRepository.save(student);
        student.setName("Roninfo Palacio");
        this.studentRepository.save(student);
        Optional<Student> studentOptional = studentRepository.findById(student.getId());
        Assertions.assertThat(studentOptional.get().getName()).isEqualTo("Roninfo Palacio");
    }
}
