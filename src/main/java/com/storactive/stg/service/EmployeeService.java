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

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    final EmployeeRepo employeeRepo;
    final UserRepo userRepo;
    final BCryptPasswordEncoder pwdEncoder;


    @Autowired
    public EmployeeService(EmployeeRepo employeeRepo,
                            UserRepo userRepo,
                            BCryptPasswordEncoder pwdEncoder) {
        this.userRepo = userRepo;
        this.employeeRepo = employeeRepo;
        this.pwdEncoder = pwdEncoder;
    }


    public List<Employee> getAll() {
        return employeeRepo.findAll();
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
            return employeeRepo.save(employee);
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
        return employeeRepo.save(employee);
    }


    public void delete(Integer id) {
        if (!employeeRepo.existsById(id))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee Not Found");
        employeeRepo.deleteById(id);
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
