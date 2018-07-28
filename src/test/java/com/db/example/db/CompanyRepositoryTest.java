package com.db.example.db;


import com.db.example.db.one.to.n.entities.Company;
import com.db.example.db.one.to.n.entities.Employee;
import com.db.example.db.one.to.n.repositories.CompanyRepository;
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
public class CompanyRepositoryTest {
    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void should_return_all_companies() throws Exception{

        //given
        Company saveCompany1 = entityManager.persistFlushFind(new Company("oocl"));
        Company saveCompany2 = entityManager.persistFlushFind(new Company("okukel"));

        //when
        List<Company> companies = companyRepository.findAll();

        //then
        Assertions.assertThat(companies.size()).isEqualTo(2);
        Assertions.assertThat(companies.get(0).getName()).isEqualTo(saveCompany1.getName());
        Assertions.assertThat(companies.get(1).getName()).isEqualTo(saveCompany2.getName());
    }

    @Test
    public void should_return_company_by_id() throws Exception{

        //given
        Long id = Long.valueOf(entityManager.persistAndGetId(new Company("oocl")).toString());

        //when
        Company company = companyRepository.findById(id).get();

        //then
        Assertions.assertThat(company.getName()).isEqualTo("oocl");
    }

    @Test
    public void should_return_company_with_details_when_given_page_and_pageSize() throws Exception{

        //given
        entityManager.persistFlushFind(new Company("oocl"));
        entityManager.persistFlushFind(new Company("delia"));
        entityManager.persistFlushFind(new Company("liaoe"));
        entityManager.persistFlushFind(new Company("hiale"));

        //when
        List<Company> companies = companyRepository.findAll(PageRequest.of(1,2)).getContent();

        //then
        Assertions.assertThat(companies.size()).isEqualTo(2);
        Assertions.assertThat(companies.get(0).getName()).isEqualTo("liaoe");
        Assertions.assertThat(companies.get(1).getName()).isEqualTo("hiale");
    }

//    @Test
//    public void should_return_employee_with_details_when_find_by_company_id() throws Exception{
//
//        //given
//        Employee employee1 = new Employee(1L,"ooce");
//        Employee employee2 = new Employee(2L,"delia");
//        List<Employee> employeeList = Arrays.asList(employee1,employee2);
//        entityManager.persistFlushFind(new Company(1L,"oocl",employeeList));
//
//        //when
//        List<Employee> employees = companyRepository.findById((long) 1).get().getEmployeeList();
//
//        //then
//        Assertions.assertThat(employees.size()).isEqualTo(2);
//        Assertions.assertThat(employees.get(0).getName()).isEqualTo("ooce");
//        Assertions.assertThat(employees.get(1).getName()).isEqualTo("delia");
//    }

    @Test
    public void should_return_new_company_list_after_add_a_company() throws Exception{

        //given
        entityManager.persistFlushFind(new Company("oocl"));
        Company company = new Company("olive");

        //when
        companyRepository.save(company);

        //then
        Assertions.assertThat(companyRepository.findAll().size()).isEqualTo(2);
        Assertions.assertThat(companyRepository.findAll().get(1).getName()).isEqualTo("olive");
    }

    @Test
    public void should_return_new_company_list_after_update_a_company() throws Exception{

        //given
        entityManager.persistFlushFind(new Company("oocl"));

        //when
        Long id = Long.valueOf(entityManager.persistAndGetId(new Company( "ilaie")).toString());
        int update = companyRepository.changeNameById(id,"huqai");

        //then
        Assertions.assertThat(update).isEqualTo(1);
        Assertions.assertThat(companyRepository.findById(id).get().getName()).isEqualTo("huqai");
    }

    @Test
    public void should_return_new_company_list_after_delete_a_company() throws Exception{

        //given
        entityManager.persistFlushFind(new Company("oocl"));

        //when
        Long id = Long.valueOf(entityManager.persistAndGetId(new Company( "ilaie")).toString());
        int delete = companyRepository.deleteCompanyById(id);

        //then
        Assertions.assertThat(delete).isEqualTo(1);
        Assertions.assertThat(companyRepository.findAll().size()).isEqualTo(1);
    }

}
