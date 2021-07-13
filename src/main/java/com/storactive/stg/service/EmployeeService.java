package com.storactive.stg.service;

import com.storactive.stg.exception.ValueAlreadyUsedException;
import com.storactive.stg.model.Employee;
import com.storactive.stg.model.User;
import com.storactive.stg.repository.EmployeeRepo;
import com.storactive.stg.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class EmployeeService {

    final String OBJ = "Employee";
    final EmployeeRepo employeeRepo;
    final HistoryService historySer;
    final UserRepo userRepo;

    final BCryptPasswordEncoder pwdEncoder;


    @Autowired
    public EmployeeService(EmployeeRepo employeeRepo,
                           HistoryService historySer, UserRepo userRepo,
                           BCryptPasswordEncoder pwdEncoder) {
        this.historySer = historySer;
        this.userRepo = userRepo;
        this.employeeRepo = employeeRepo;
        this.pwdEncoder = pwdEncoder;
    }


    public Employee create(Employee employee) {
        Optional<User> user = userRepo
                .findByUsernameIgnoreCaseOrCinIgnoreCase
                        (employee.getUsername(), employee.getCin());
        if (user.isEmpty()) {
            employee.setPassword(pwdEncoder.encode(employee.getPassword()));
            employee.setId(null);
            employee.setCin(employee.getCin().toUpperCase());
            employee.setUsername(employee.getUsername().toLowerCase());
            Employee employee1 = employeeRepo.save(employee);
            historySer.objetCreated(OBJ, employee1.getId());
            return employee1;
        }
        User user1 = user.orElse(null);
        if (user1.getCin().equals(employee.getCin()))
            throw new ValueAlreadyUsedException("cin '"+user1.getCin()+"' Already Exists");
        throw new ValueAlreadyUsedException("Username '"+user1.getUsername()+"' Already Exists");
    }


    public Employee update(Employee employee) {
        if (!employeeRepo.existsById(employee.getId()))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee Not Found");
        employee.setPassword(pwdEncoder.encode(employee.getPassword()));
        Employee employee1 = employeeRepo.save(employee);
        historySer.objetUpdated(OBJ, employee1.getId());
        return employee1;
    }


    public void delete(Integer id) {
        if (!employeeRepo.existsById(id))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee Not Found");
        employeeRepo.deleteById(id);
        historySer.objetDeleted(OBJ, id);
    }


    public Employee findById(int id) {
        return employeeRepo.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee Not Found"));
    }


    public Employee findByUsername(String username) {
        return employeeRepo.findByUsername(username).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee Not Found"));
    }

}
