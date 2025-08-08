package payroll;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "CUSTOMER_ORDER")
public class Order {
    private @Id @GeneratedValue Long id;
    private String description;
    private Status status;
    
    public Order() {
    }

    public Order(String description, Status status) {
        this.description = description;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o){
        if(this == o){
            return true;
        }
        else if (!(o instanceof Order)) {
            return false;
        }
        else {
            Order order = (Order) o;
            return Objects.equals(this.id, order.id) && 
                Objects.equals(this.description, order.description) &&
                this.status == order.status;
        }
    }
    
    @Override
    public int hashCode(){
        return Objects.hash(this.id, this.description, this.status);
    }

    @Override
    public String toString(){
        return "Order{id=" + id + 
               ", description='" + description + '\'' + 
               ", status=" + status + 
               '}';
    }
}
