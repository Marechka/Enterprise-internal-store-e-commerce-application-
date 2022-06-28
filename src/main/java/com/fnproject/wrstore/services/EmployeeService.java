package com.fnproject.wrstore.services;

import com.fnproject.wrstore.data.EmployeeRepository;
import com.fnproject.wrstore.models.Employee;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Transactional(rollbackOn = {DataAccessException.class})
public class EmployeeService {

    EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }

    @Transactional(rollbackOn = {NoSuchElementException.class})
    public Employee findByEmployeeId(Integer id) throws NoSuchElementException{
        return employeeRepository.findById(id).orElseThrow();
    }

    public void saveOrUpdate(Employee employee){
        log.info(employee.toString());
        employeeRepository.save(employee);

    }
    public void delete(Employee employee){
        employeeRepository.delete(employee);
    }


}
