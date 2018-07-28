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

import java.util.List;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CompanyRepositoryTest {
    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private TestEntityManager entityManager;

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
