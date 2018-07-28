package com.db.example.db;

import com.db.example.db.one.to.n.controllers.CompanyController;
import com.db.example.db.one.to.n.dto.CompanyDTO;
import com.db.example.db.one.to.n.entities.Company;
import com.db.example.db.one.to.n.services.CompanyService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(CompanyController.class)
public class CompanyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CompanyService companyService;


    @Test
    public void should_return_all_company_with_details() throws Exception{
        //given
        Company company1 = new Company(1L,"oocl");
        Company company2 = new Company(2L,"olaei");
        CompanyDTO companyDTO1 = new CompanyDTO(company1);
        CompanyDTO companyDTO2 = new CompanyDTO(company2);
        List<CompanyDTO> companyDTOS = Arrays.asList(companyDTO1,companyDTO2);
        given(companyService.getAllCompany()).willReturn(companyDTOS);

        //when
        ResultActions result = mockMvc.perform(get("/companies"));

        //then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$",hasSize(2)))
                .andExpect(jsonPath("$[0].id",is(1)))
                .andExpect(jsonPath("$[0].name",is("oocl")))
                .andExpect(jsonPath("$[1].id",is(2)));
    }

//    @Test
//    public void getCompany_ReturnsCompanyDetails() throws Exception{
//        List<Company> companyList = new ArrayList<>();
//        given(companyRepository.findAll()).willReturn(companyList);
//
//        mockMvc.perform(get("/companies")).andExpect(status().isOk())
//                .andExpect(jsonPath())
//    }

//    @Test
//    public void getCompanyById_ReturnsCompanyDetails() throws Exception{
//        List<Company> companyList = new ArrayList<>();
//        given(companyRepository.findAll()).willReturn(companyList);
//
//        mockMvc.perform(get("/companies")).andExpect(status().isOk());
//    }

}
