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
}
