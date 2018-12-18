package br.com.roninfo.springBoot.repository;

import br.com.roninfo.springBoot.model.Student;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface StudentRepository extends PagingAndSortingRepository<Student, Long> {
    List<Student> findByNameIgnoreCaseContaining(String name);

    List<Student> findBySexoIgnoreCaseContaining(String sexo);


}
