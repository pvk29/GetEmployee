package payroll;


import java.util.List;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;


@RestController
public class EmployeeController {
    private final EmployeeRepository repository;

    public EmployeeController(EmployeeRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/employees")
    public List<Employee> all() {
        return repository.findAll();
    }

    @PostMapping("/employees")
    public Employee newEmployee(@RequestBody Employee newEmployee) {        
        return repository.save(newEmployee);
    }
    
    @GetMapping("/employees/{id}")
    public Employee one(@PathVariable Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException(id));
    }

    @PutMapping("/employees/{id}")
    public Employee replacEmployee(@PathVariable Long id, @RequestBody Employee newEmployee) {
        
        return repository.findById(id)
                .map((foundEmployee) -> {
                    foundEmployee.setName(newEmployee.getName());
                    foundEmployee.setRole(newEmployee.getRole());

                    return repository.save(foundEmployee);
                })
                .orElseGet(() -> {
                    return repository.save(newEmployee);
                });
    }

    @DeleteMapping("/employees/{id}")
    public void deleteEmployee(@PathVariable Long id){
        repository.deleteById(id);
    }
        
}
