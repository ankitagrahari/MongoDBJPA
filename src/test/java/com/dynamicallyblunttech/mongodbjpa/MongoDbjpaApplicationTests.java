package com.dynamicallyblunttech.mongodbjpa;

import com.dynamicallyblunttech.mongodbjpa.model.Employee;
import com.dynamicallyblunttech.mongodbjpa.repo.EmployeeRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest
class MongoDbjpaApplicationTests {

    @Autowired
    EmployeeRepo employeeRepo;
    List<Employee> empList = new ArrayList<>();

    @Test
    void contextLoads() {
    }

    @BeforeEach
    void setUp(){
        //Remove everything from Employee table
        employeeRepo.deleteAll();

        //Insert new Employee data
        Employee employee = new Employee("dynamically", "Amsterdam", 9);
        Employee employee1 = new Employee("blunt", "India", 5);
        Employee employee2 = new Employee("tech", "Germany", 6);
        Employee employee3 = new Employee("Subscribe", "Austin", 9);

        empList.add(employee); empList.add(employee1); empList.add(employee2); empList.add(employee3);
        employeeRepo.saveAll(empList);

        System.out.println("Employees data saved");
    }

    @Test
    public void fetchData(){
        List<Employee> savedEmpList = employeeRepo.findAll();
        Assertions.assertIterableEquals(empList, savedEmpList);
    }

    @Test
    void fetchDataByName(){
        List<Employee> employees = employeeRepo.findEmployeeByName("tech");
        Assertions.assertEquals("tech", employees.get(0).getName());
    }

    @Test
    void fetchDataByNamePart(){
        List<Employee> employees = employeeRepo.findEmployeeByRegexpName("c");
        Assertions.assertEquals(
                empList.stream().filter(emp -> emp.getName().contains("c")).count(),
                employees.size()
        );
    }

    @Test
    void fetchDataByExp(){
        List<Employee> employees = employeeRepo.findEmployeeByExperienceEquals(9);
        Assertions.assertEquals(
                empList.stream().filter(emp -> emp.getExperience().equals(9)).count(),
                employees.size()
        );
    }

    @Test
    void fetchDataByExpBetweenFiveAndTen(){
        List<Employee> employees = employeeRepo.findEmployeesByExperienceBetween(6, 10);
        Assertions.assertEquals(
                empList.stream().filter(emp -> emp.getExperience()>6 && emp.getExperience()<10).count(),
                employees.size()
        );
    }

    @Test
    void countTotal(){
        Assertions.assertEquals(4, employeeRepo.count());
    }

}
