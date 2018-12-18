package br.com.roninfo.springBoot.service;

import br.com.roninfo.springBoot.model.CredentialStudent;
import br.com.roninfo.springBoot.repository.CredentialStudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class CustomCredentialStudentDetailService implements UserDetailsService {

    private final CredentialStudentRepository credentialStudentRepository;

    @Autowired
    public CustomCredentialStudentDetailService(CredentialStudentRepository credentialStudentRepository) {
        this.credentialStudentRepository = credentialStudentRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        CredentialStudent credentialStudent = Optional.ofNullable(credentialStudentRepository.findByUsername(username)).orElseThrow(() -> new UsernameNotFoundException("user not found"));
        List<GrantedAuthority> authorityListAdmin = AuthorityUtils.createAuthorityList("ROLE_USER", "ROLE_ADMIN");
        List<GrantedAuthority> authorityListUser  = AuthorityUtils.createAuthorityList("ROLE_USER");
        return new User(credentialStudent.getUsername(), credentialStudent.getPassword(),
                credentialStudent.isAdmin() ? authorityListAdmin : authorityListUser);

    }
}
