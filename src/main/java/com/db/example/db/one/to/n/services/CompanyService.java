package com.db.example.db.one.to.n.services;

import com.db.example.db.one.to.n.dto.CompanyDTO;
import com.db.example.db.one.to.n.entities.Company;
import com.db.example.db.one.to.n.entities.Employee;
import com.db.example.db.one.to.n.repositories.CompanyRepository;
import com.db.example.db.one.to.n.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private EmployeeRepository employeeRepository;


    public CompanyDTO createCompany(Company company) {
        company.getEmployeeList().stream()
                .forEach(employee -> employee.setCompany(company));
        return new CompanyDTO(companyRepository.save(company));
    }

    public List<CompanyDTO> getAllCompany() {
        return companyRepository.findAll().stream()
                .map(company -> new CompanyDTO(company))
                .collect(Collectors.toList());
    }

    public CompanyDTO getCompanyById(Long id) {
        return new CompanyDTO(companyRepository.findById(id).get());
    }

    public List<CompanyDTO> getCompaniesByPage(int page, int pageSize) {
        return companyRepository.findAll(new PageRequest(page,pageSize)).stream()
                .map(company -> new CompanyDTO(company))
                .collect(Collectors.toList());
    }

    public ResponseEntity updateCompany(Company company) {
        company.getEmployeeList().stream().filter(employee -> employee.getCompany() == null).forEach(employee -> {
            employee.setCompany(company);
        });
        companyRepository.save(company);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    public ResponseEntity addEmployeeIntoCompany(Long id, Employee employee) {
        Company company = companyRepository.findById(id).get();
        employee.setCompany(company);
        employeeRepository.save(employee);
        companyRepository.save(company);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    public Company deleteCompany(Long id) {
        Company one = companyRepository.findById(id).get();
        companyRepository.delete(one);
        return one;
    }


}
