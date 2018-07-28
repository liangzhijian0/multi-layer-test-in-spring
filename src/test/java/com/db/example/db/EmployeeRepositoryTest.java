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
import org.springframework.data.domain.PageRequest;
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
        entityManager.persistFlushFind(new Employee("oocl","male"));
        Employee employee = new Employee("ocean","male");

        //when
        employeeRepository.save(employee);

        //then
        Assertions.assertThat(employeeRepository.findAll().size()).isEqualTo(2);
        Assertions.assertThat(employeeRepository.findAll().get(1).getName()).isEqualTo("ocean");
    }

    @Test
    public void should_return_all_employees() throws Exception{

        //given
        entityManager.persistFlushFind(new Employee("oocl","male"));
        entityManager.persistFlushFind(new Employee("oocl2","male"));
        entityManager.persistFlushFind(new Employee("oocl3","male"));

        //when
        List<Employee> employees = employeeRepository.findAll();

        //then
        Assertions.assertThat(employees.size()).isEqualTo(3);
        Assertions.assertThat(employees.get(1).getName()).isEqualTo("oocl2");
    }

    @Test
    public void should_return_employee_by_id() throws Exception{

        //given
        Long id = Long.valueOf(entityManager.persistAndGetId(new Employee("oocl","male")).toString());

        //when
        Employee employee = employeeRepository.findById(id).get();

        //then
        Assertions.assertThat(employee.getName()).isEqualTo("oocl");
    }

    @Test
    public void should_return_male_employee() throws Exception{

        //given
        entityManager.persistFlushFind(new Employee("oocl","male"));
        entityManager.persistFlushFind(new Employee("oocl2","female"));
        entityManager.persistFlushFind(new Employee("oocl3","male"));

        //when
        List<Employee> employees = employeeRepository.findByGender("male");

        //then
        Assertions.assertThat(employees.size()).isEqualTo(2);
        Assertions.assertThat(employees.get(0).getName()).isEqualTo("oocl");
    }

    @Test
    public void should_return_employees_by_page() throws Exception{

        //given
        entityManager.persistFlushFind(new Employee("oocl","male"));
        entityManager.persistFlushFind(new Employee("oocl2","female"));
        entityManager.persistFlushFind(new Employee("oocl3","male"));
        entityManager.persistFlushFind(new Employee("oocl4","female"));

        //when
        List<Employee> employees = employeeRepository.findAll(PageRequest.of(1,2)).getContent();

        //then
        Assertions.assertThat(employees.size()).isEqualTo(2);
        Assertions.assertThat(employees.get(0).getName()).isEqualTo("oocl3");
        Assertions.assertThat(employees.get(1).getGender()).isEqualTo("female");
    }

    @Test
    public void should_return_change_name_when_update_employee() throws Exception{

        //given
        Long id = Long.valueOf(entityManager.persistAndGetId(new Employee("oocl","male")).toString());

        //when
        int update = employeeRepository.changeNameById(id,"ocean");

        //then
        Assertions.assertThat(update).isEqualTo(1);
        Assertions.assertThat(employeeRepository.findById(id).get().getName()).isEqualTo("ocean");
    }
}
