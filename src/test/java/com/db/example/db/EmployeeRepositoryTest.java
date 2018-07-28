package com.db.example.db;

import com.db.example.db.one.to.n.entities.Company;
import com.db.example.db.one.to.n.entities.Employee;
import com.db.example.db.one.to.n.repositories.CompanyRepository;
import com.db.example.db.one.to.n.repositories.EmployeeRepository;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@DataJpaTest

public class EmployeeRepositoryTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void should_return_new_employee_list_after_add_a_employee() throws Exception{

        //given
        entityManager.persistFlushFind(new Employee("oocl"));
        Employee employee = new Employee("ocean");

        //when
        employeeRepository.save(employee);

        //then
        Assertions.assertThat(employeeRepository.findAll().size()).isEqualTo(2);
        Assertions.assertThat(employeeRepository.findAll().get(1).getName()).isEqualTo("ocean");
    }

    @Test
    public void should_return_all_employees() throws Exception{

        //given
        entityManager.persistFlushFind(new Employee("oocl"));
        entityManager.persistFlushFind(new Employee("oocl2"));
        entityManager.persistFlushFind(new Employee("oocl3"));

        //when
        List<Employee> employees = employeeRepository.findAll();

        //then
        Assertions.assertThat(employees.size()).isEqualTo(3);
        Assertions.assertThat(employees.get(1).getName()).isEqualTo("oocl2");
    }
}
