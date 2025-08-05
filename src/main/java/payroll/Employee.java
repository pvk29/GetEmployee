package payroll;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
class Employee{
    private @Id @GeneratedValue Long id;
    // private String name;
    private String firstname;
    private String lastname;
    private String role;
    
    Employee() {}

    Employee(String firstname, String lastname, String role){
        this.firstname = firstname;
        this.lastname = lastname;
        this.role = role;
    }

    
    public Long getId() {
        return this.id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getFirstname() {
        return this.firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return this.lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getName() {
        return this.firstname + " " + this.lastname;
    }

    public void setName(String name) {
        this.firstname = name.split(" ")[0];
        this.lastname = name.split(" ")[1];
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "Employee [id=" + id + ", firstname=" + firstname + ", lastname=" + lastname + ", role=" + role + "]";
    }
}