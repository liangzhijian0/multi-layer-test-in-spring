package com.db.example.db;

import com.db.example.db.one.to.n.entities.Company;
import com.db.example.db.one.to.n.repositories.CompanyRepository;
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
        List<Company> companyList = Arrays.asList(saveCompany1,saveCompany2);

        //when
        List<Company> companies = companyRepository.findAll();

        //then
        Assertions.assertThat(companies.get(0).getName()).isEqualTo(saveCompany1.getName());
        Assertions.assertThat(companies.get(1).getName()).isEqualTo(saveCompany2.getName());
    }

    @Test
    public void should_return_company_by_id() throws Exception{

        //given
        Company saveCompany = entityManager.persistFlushFind(new Company("oocl"));

        //when
        Company company = companyRepository.findById(1L).get();

        //then
        Assertions.assertThat(company.getName()).isEqualTo(saveCompany.getName());
    }

}
