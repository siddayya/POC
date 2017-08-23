/**
 * 
 */
package com.dev.ewt.devops.poc.services;

import java.util.List;

import com.dev.ewt.devops.poc.model.Employee;

/**
 * @author Sid
 *
 */
public interface EmployeeService {

	Employee findById(long id);
    
	Employee findByName(String name);
     
    void saveEmployee(Employee emp);
     
    void updateEmployee(Employee emp);
     
    void deleteEmployeeById(long id);
 
    List<Employee> findAllEmployees();
     
    void deleteAllEmployees();
     
    boolean isEmployeeExist(Employee emp);
}
