package payroll;

import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import org.springframework.http.HttpHeaders;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.mediatype.problem.Problem;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
public class OrderController {
    private final OrderRespository orderRespository;
    private final OrderModelAssembler assembler;
    
    public OrderController(OrderRespository orderRespository, OrderModelAssembler assembler) {
        this.orderRespository = orderRespository;
        this.assembler = assembler;
    }

    @GetMapping("/orders")
    public CollectionModel<EntityModel<Order>> all() {
        List<EntityModel<Order>> orders = orderRespository.findAll().stream()
            .map(assembler::toModel)
            .toList();

        return CollectionModel.of(orders, linkTo(methodOn(OrderController.class).all()).withSelfRel());
    }
       
    @GetMapping("/orders/{id}")
    public EntityModel<Order> one(@PathVariable Long id) {

        Order order = orderRespository.findById(id)
            .orElseThrow(() -> new OrderNotFoundException(id));

        return assembler.toModel(order);
    }

    @PostMapping("/orders")
    public ResponseEntity<EntityModel<Order>> newOrder(@RequestBody Order order) {        
        
        order.setStatus(Status.IN_PROGRESS);
        
        Order newOrder = orderRespository.save(order);

        return ResponseEntity.created(
                linkTo(methodOn(OrderController.class).one(newOrder.getId()))
                .toUri()
            ).body(assembler.toModel(newOrder));
    }

    @PatchMapping("/orders/{id}/cancel")
    ResponseEntity<?> cancel(@PathVariable Long id){
        Order order = orderRespository.findById(id)
            .orElseThrow(() -> new OrderNotFoundException(id));

        if(order.getStatus() == Status.IN_PROGRESS){
            order.setStatus(Status.CANCELLED);
            return ResponseEntity.ok(assembler.toModel(orderRespository.save(order)));
        }

        return ResponseEntity
            .status(HttpStatus.METHOD_NOT_ALLOWED)
            .header(HttpHeaders.CONTENT_TYPE, MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE)
            .body(Problem.create().withTitle("Method not allowed").withDetail("You can't cancel an order that is in the " + order.getStatus() + " status"));
    }
    
    @PatchMapping("/orders/{id}/complete")
    ResponseEntity<?> complete(@PathVariable Long id){
        Order order = orderRespository.findById(id)
            .orElseThrow(() -> new OrderNotFoundException(id));

        if(order.getStatus() == Status.IN_PROGRESS){
            order.setStatus(Status.COMPLETED);
            return ResponseEntity.ok(assembler.toModel(orderRespository.save(order)));
        }

        return ResponseEntity
            .status(HttpStatus.METHOD_NOT_ALLOWED)
            .header(HttpHeaders.CONTENT_TYPE, MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE)
            .body(Problem.create().withTitle("Method not allowed").withDetail("You can't complete an order that is in the " + order.getStatus() + " status"));
    }
    
}
