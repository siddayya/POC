package com.dev.ewt.devops.poc;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.dev.ewt.devops.poc.services.EmployeeService;
import com.dev.ewt.devops.poc.controller.EmployeeController;
import com.dev.ewt.devops.poc.model.Employee;


@RunWith(SpringRunner.class)
@WebMvcTest(EmployeeController.class)
public class EmployeeControllerTest {

	@Autowired
    private MockMvc mvc;
 
    @MockBean
    private EmployeeService employeeService;
	
    @Test
    public void listAllEmployees__success() throws Exception{
    	List<Employee> employees = new ArrayList<Employee>();
    	employees.add(new Employee(1,"Daenerys Targaryen",30, 70000));
    	employees.add(new Employee(2,"John Snow",40, 50000));
    	employees.add(new Employee(3,"Theyron",45, 30000));
    	employees.add(new Employee(4,"Jamie lamister",50, 40000));
    	
        when(employeeService.findAllEmployees()).thenReturn(employees);
    	
    	mvc.perform(get("/api/employees/")
    		      .contentType(MediaType.APPLICATION_JSON))
    		      .andExpect(status().isOk())
    		      ;
    }
      
    @Test
    public void getEmployee_by_id_success() throws Exception {
    	List<Employee> users = new ArrayList<Employee>();
        users.add(new Employee(1,"Daenerys Targaryen",30, 70000));
        when(employeeService.findById(1)).thenReturn(users.get(0));
        
        mvc.perform(get("/api/employee/1")
  		      .contentType(MediaType.APPLICATION_JSON))
  		      .andExpect(status().isOk())
  		    .andExpect(jsonPath("$.id", is(1)))
            .andExpect(jsonPath("$.name", is("Daenerys Targaryen")))
            .andExpect(jsonPath("$.age", is(30)))
            .andExpect(jsonPath("$.salary", is(70000.0)));
    	
    }
	
}
