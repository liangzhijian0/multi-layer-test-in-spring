package com.db.example.db.one.to.n.services;

import com.db.example.db.one.to.n.entities.Employee;
import com.db.example.db.one.to.n.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {
    @Autowired
    EmployeeRepository employeeRepository;

    public boolean createEmployee(Employee employee) {
        employeeRepository.save(employee);
        return true;
    }
}
