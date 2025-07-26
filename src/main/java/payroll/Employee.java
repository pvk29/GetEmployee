package payroll;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
class Employee{
    private @Id @GeneratedValue Long id;
    private String name;
    private String role;

    Employee() {}

    Employee(String name, String role){
        this.name = name;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString(){
        return "Employee{" + "id=" + this.id + 
                ", name='" + this.name + '\'' + 
                ", role='" + this.role + '\'' +
                 '}';
    }
}