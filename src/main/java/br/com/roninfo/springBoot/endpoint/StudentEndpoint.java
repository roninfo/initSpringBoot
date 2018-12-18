package br.com.roninfo.springBoot.endpoint;

import br.com.roninfo.springBoot.error.ResourceNotFoundException;
import br.com.roninfo.springBoot.model.Student;
import br.com.roninfo.springBoot.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController // adiciona o responsebody em todos objetos
@RequestMapping("students")
public class StudentEndpoint {

    @Autowired
    public StudentEndpoint(StudentRepository studentDao) {
        this.studentDao = studentDao;
    }

    private final StudentRepository studentDao;

    //@RequestMapping(method = RequestMethod.GET)
    @GetMapping
    public ResponseEntity<?> listAll(Pageable pageable) {
       // System.out.println("Data: " + dateUtil.formatLocalDateTimeToDatabaseStyle(LocalDateTime.now()));
        return new ResponseEntity<>(studentDao.findAll(pageable), HttpStatus.OK);
    }

    //@RequestMapping(method = RequestMethod.GET, path = "/{id}")
    @GetMapping(path = {"/{id}"})
    public ResponseEntity<?> getStudentById(@PathVariable("id") Long id) {
        verifyIfStudentsExists(id);

        Optional<Student> student = studentDao.findById(id);

        return new ResponseEntity<>(student.get(), HttpStatus.OK);
    }

    private void verifyIfStudentsExists(Long id) {
        Optional<Student> student = studentDao.findById(id);

        if (!student.isPresent()) {
            throw new ResourceNotFoundException("Studante not found: " + id);
        }
    }

    //usando
    @GetMapping(path = {"/findByNameIgnoreCaseContaining/{name}"})
    public ResponseEntity<?> findStudentsByName(@PathVariable("name") String name) {
        return new ResponseEntity<>(studentDao.findByNameIgnoreCaseContaining(name), HttpStatus.OK);
    }

    @GetMapping(path = {"/findStudentsBySexo/{sexo}"})
    public ResponseEntity<?> findStudentsBySexo(@PathVariable("sexo") String name) {
        return new ResponseEntity<>(studentDao.findBySexoIgnoreCaseContaining(name), HttpStatus.OK);
    }

    //@RequestMapping(method = RequestMethod.POST, path = "/salvar")
    @PostMapping
    @Transactional
    public ResponseEntity<?> save(@Valid @RequestBody Student student) {

        Student studentnew = studentDao.save(student);

        if (false) {
            studentDao.save(student);
            throw new ResourceNotFoundException("Teste de transação");
        }

        return new ResponseEntity<>(studentnew, HttpStatus.CREATED);
    }

    //@RequestMapping(method = RequestMethod.DELETE)
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        studentDao.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<Student> update(@RequestBody Student student) {
        studentDao.save(student);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
