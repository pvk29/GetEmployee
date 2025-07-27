package payroll;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
class EmployeeModelAssembler implements RepresentationModelAssembler<Employee, EntityModel<Employee>> {

    /***
     * converting a non-model object (Employee) into a 
     * model-based object (EntityModel<Employee>).
     * <p>
     * Adds the following links:
     * <ul>
     * <li><b>self</b> — a link to this employee's resource (the "self" link)</li>
     * <li><b>employees</b> — a link to the collection of all employees</li>
     * </ul>
     * 
     * @param entity of type Employee to convert
     * @return EntityModel wrapping the given employee and containg REST links
     */
    @Override
    public EntityModel<Employee> toModel(Employee entity) {
        return EntityModel.of(entity,
            linkTo(methodOn(EmployeeController.class).one(entity.getId())).withSelfRel(),
            linkTo(methodOn(EmployeeController.class).all()).withRel("employees")
            );
    }
    
}
