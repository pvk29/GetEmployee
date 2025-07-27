package payroll;


import java.util.List;
// import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
public class EmployeeController {
    private final EmployeeRepository repository;

    public EmployeeController(EmployeeRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/employees")
    public CollectionModel<EntityModel<Employee>> all() {
        List<EntityModel<Employee>> employees = repository.findAll().stream()
                .map(eachEmployee -> EntityModel.of(eachEmployee,
                        linkTo(methodOn(EmployeeController.class).one(eachEmployee.getId())).withSelfRel(),
                        linkTo(methodOn(EmployeeController.class).all()).withRel("employees")))
                .toList(); // .collect(Collectors.toList());
                        

        return CollectionModel.of(employees,
                linkTo(methodOn(EmployeeController.class).all()).withSelfRel());
    }

    

    @PostMapping("/employees")
    public Employee newEmployee(@RequestBody Employee newEmployee) {        
        return repository.save(newEmployee);
    }
    
    @GetMapping("/employees/{id}")
    public EntityModel<Employee> one(@PathVariable Long id) {
        Employee employee = repository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException(id));
        
        return EntityModel.of(employee, linkTo(methodOn(EmployeeController.class).one(id)).withSelfRel(),
                                        linkTo(methodOn(EmployeeController.class).all()).withRel("employees"));
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
