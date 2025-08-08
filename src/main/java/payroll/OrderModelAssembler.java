package payroll;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class OrderModelAssembler implements RepresentationModelAssembler<Order, EntityModel<Order>>{

    @Override
    public EntityModel<Order> toModel(Order order){

        //links to items wich do not depend on Status enum
        EntityModel<Order> orderModel = EntityModel.of(
            order, linkTo(methodOn(OrderController.class).one(order.getId())).withSelfRel(),
                   linkTo(methodOn(OrderController.class).all()).withRel("orders")
        );

        //Adding conditional links based on state of the order
        if(order.getStatus() == Status.IN_PROGRESS){
            orderModel.add(List.of(
                linkTo(methodOn(OrderController.class).cancel(order.getId())).withRel("cancel"),
                linkTo(methodOn(OrderController.class).complete(order.getId())).withRel("complete")
                ));
        }

        return orderModel;
    }
}
