//package com.db.example.db;
//
//import com.db.example.db.one.to.n.controllers.CompanyController;
//import com.db.example.db.one.to.n.entities.Company;
//import com.db.example.db.one.to.n.repositories.CompanyRepository;
//import com.db.example.db.one.to.n.repositories.EmployeeRepository;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.test.web.servlet.MockMvc;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.mockito.BDDMockito.given;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@RunWith(SpringRunner.class)
//@WebMvcTest(CompanyController.class)
//public class CompanyControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private CompanyRepository companyRepository;
//
//    @MockBean
//    private EmployeeRepository employeeRepository;
//
//
//    @Test
//    public void getCompany_ReturnsCompanyDetails() throws Exception{
////        Company company1 = new Company();
////        Company company2 = new Company();
////        given(companyRepository.save(company1)).willReturn(company2);
//        Company company = new Company();
//        company.setName("ocean");
//
//        String json = gson.toJson(company);
//
//        mockMvc.perform(post("/companies")
//                .content("name","e"))
//                .andExpect(status().isOk());
//
//    }
//
////    @Test
////    public void getCompany_ReturnsCompanyDetails() throws Exception{
////        List<Company> companyList = new ArrayList<>();
////        given(companyRepository.findAll()).willReturn(companyList);
////
////        mockMvc.perform(get("/companies")).andExpect(status().isOk())
////                .andExpect(jsonPath())
////    }
//
////    @Test
////    public void getCompanyById_ReturnsCompanyDetails() throws Exception{
////        List<Company> companyList = new ArrayList<>();
////        given(companyRepository.findAll()).willReturn(companyList);
////
////        mockMvc.perform(get("/companies")).andExpect(status().isOk());
////    }
//
//}
