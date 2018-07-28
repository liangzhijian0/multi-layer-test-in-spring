package com.db.example.db.one.to.n.controllers;

import com.db.example.db.one.to.n.dto.CompanyDTO;
import com.db.example.db.one.to.n.entities.Company;
import com.db.example.db.one.to.n.entities.Employee;
import com.db.example.db.one.to.n.services.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/companies")
public class CompanyController {
    @Autowired
    private CompanyService companyService;

    @Transactional
    @PostMapping(path = "",produces = MediaType.APPLICATION_JSON_VALUE)
    public CompanyDTO createCompany(@RequestBody Company company){
        return companyService.createCompany(company);
    }

    @Transactional
    @GetMapping(path = "",produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CompanyDTO> getAllCompany(){
        return companyService.getAllCompany();

    }

    @Transactional
    @GetMapping (path = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public CompanyDTO getCompanyById(@PathVariable Long id){
        return companyService.getCompanyById(id);
    }

    @Transactional
    @PutMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity updateCompany(@RequestBody Company company) {
        return companyService.updateCompany(company);
    }

    @Transactional
    @PutMapping(path = "/{id}/employees")
    public ResponseEntity addEmployeeIntoCompany(@PathVariable Long id, @RequestBody Employee employee){
        return companyService.addEmployeeIntoCompany(id,employee);
    }

    @Transactional
    @DeleteMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Company deleteCompany(@PathVariable("id")Long id) {
        return companyService.deleteCompany(id);
    }
}
