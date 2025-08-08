package payroll;

import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoadDatabase {
    private static final org.slf4j.Logger log = 
        LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(EmployeeRepository employeeRepository, OrderRespository orderRespository){
        return args -> {

            //preloading Employees
            employeeRepository.save(new Employee("Bilbo", "Baggins", "burglar"));
            employeeRepository.save(new Employee("Frodo", "Baggins", "theif"));
            
            employeeRepository.findAll().forEach(employee -> log.info("Preloaded " + employee));
            
            
            //preloading Orders
            orderRespository.save(new Order("MacBook Pro", Status.COMPLETED));
            orderRespository.save(new Order("iPhone", Status.IN_PROGRESS));

            orderRespository.findAll().forEach(order -> log.info("Preloaded " + order));

        };
    }
}
