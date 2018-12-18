package br.com.roninfo.springBoot.repository;

import br.com.roninfo.springBoot.model.CredentialStudent;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CredentialStudentRepository extends PagingAndSortingRepository<CredentialStudent, Long> {
    CredentialStudent findByUsername(String username);
}
