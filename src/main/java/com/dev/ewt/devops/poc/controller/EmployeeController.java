/**
 * 
 */
package com.dev.ewt.devops.poc.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.dev.ewt.devops.poc.model.Employee;
import com.dev.ewt.devops.poc.services.EmployeeService;
import com.dev.ewt.devops.poc.util.EmployeeException;

/**
 * @author Sid
 *
 */

@RestController
@RequestMapping("/api")
public class EmployeeController {

	
	public static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);
	 
    @Autowired
    EmployeeService employeeService; 
 
    // -------------------Retrieve Employees ---------------------------------------------
 
    @RequestMapping(value = "/employees/", method = RequestMethod.GET)
    public ResponseEntity<List<Employee>> listAllEmployees() {
        List<Employee> employees = employeeService.findAllEmployees();
        if (employees.isEmpty()) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
            // You many decide to return HttpStatus.NOT_FOUND
        }
        return new ResponseEntity<List<Employee>>(employees, HttpStatus.OK);
    }
 
    // -------------------Retrieve employee------------------------------------------
 
    @RequestMapping(value = "/employee/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getEmployee(@PathVariable("id") long id) {
        logger.info("Fetching Employee with id {}", id);
        Employee employee = employeeService.findById(id);
        if (employee == null) {
            logger.error("employee with id {} not found.", id);
            return new ResponseEntity(new EmployeeException("employee with id " + id 
                    + " not found"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Employee>(employee, HttpStatus.OK);
    }
 
    // -------------------Create an employee-------------------------------------------
 
    @RequestMapping(value = "/employee/", method = RequestMethod.POST)
    public ResponseEntity<?> createEmployee(@RequestBody Employee employee, UriComponentsBuilder ucBuilder) {
        logger.info("Creating Employee : {}", employee);
 
        if (employeeService.isEmployeeExist(employee)) {
            logger.error("Unable to create. A employee with name {} already exist", employee.getName());
            return new ResponseEntity(new EmployeeException("Unable to create. A employee with name " + 
            		employee.getName() + " already exist."),HttpStatus.CONFLICT);
        }
        employeeService.saveEmployee(employee);
 
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/api/employee/{id}").buildAndExpand(employee.getId()).toUri());
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }
 
    // ------------------- Update an employee ------------------------------------------------
 
    @RequestMapping(value = "/employee/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateEmployee(@PathVariable("id") long id, @RequestBody Employee employee) {
        logger.info("Updating Employee with id {}", id);
 
        Employee currentEmployee = employeeService.findById(id);
 
        if (currentEmployee == null) {
            logger.error("Unable to update. employee with id {} not found.", id);
            return new ResponseEntity(new EmployeeException("Unable to upate. Employee with id " + id + " not found."),
                    HttpStatus.NOT_FOUND);
        }
 
        currentEmployee.setName(employee.getName());
        currentEmployee.setAge(employee.getAge());
        currentEmployee.setSalary(employee.getSalary());
 
        employeeService.updateEmployee(currentEmployee);
        return new ResponseEntity<Employee>(currentEmployee, HttpStatus.OK);
    }
 
    // ------------------- Delete an employee-----------------------------------------
 
    @RequestMapping(value = "/employee/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteEmployee(@PathVariable("id") long id) {
        logger.info("Fetching & Deleting employee with id {}", id);
 
        Employee employee = employeeService.findById(id);
        if (employee == null) {
            logger.error("Unable to delete. Employee with id {} not found.", id);
            return new ResponseEntity(new EmployeeException("Unable to delete. Employee with id " + id + " not found."),
                    HttpStatus.NOT_FOUND);
        }
        employeeService.deleteEmployeeById(id);
        return new ResponseEntity<Employee>(HttpStatus.NO_CONTENT);
    }
 
    // ------------------- Delete all employee-----------------------------
 
    @RequestMapping(value = "/employee/", method = RequestMethod.DELETE)
    public ResponseEntity<Employee> deleteAllEmployee() {
        logger.info("Deleting All employee");
 
        employeeService.deleteAllEmployees();
        return new ResponseEntity<Employee>(HttpStatus.NO_CONTENT);
    }
 
}
