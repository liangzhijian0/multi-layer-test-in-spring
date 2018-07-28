package com.db.example.db.one.to.n.services;

import com.db.example.db.one.to.n.dto.EmployeeDTO;
import com.db.example.db.one.to.n.entities.Employee;
import com.db.example.db.one.to.n.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeService {
    @Autowired
    EmployeeRepository employeeRepository;

    public boolean createEmployee(Employee employee) {
        employeeRepository.save(employee);
        return true;
    }

    public List<EmployeeDTO> getAllEmployee() {
        return employeeRepository.findAll().stream()
                .map(employee -> new EmployeeDTO(employee))
                .collect(Collectors.toList());
    }

    public EmployeeDTO getEmployeeById(Long id) {
        return new EmployeeDTO(employeeRepository.findById(id).get());
    }
}
