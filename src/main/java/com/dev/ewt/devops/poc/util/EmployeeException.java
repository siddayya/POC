/**
 * 
 */
package com.dev.ewt.devops.poc.util;

/**
 * @author Sid
 *
 */
public class EmployeeException {
	
	
	private String errorMessage;
	
	/**
	 * 
	 * @param errorMessage
	 */
    public EmployeeException(String errorMessage){
        this.errorMessage = errorMessage;
    }
    
    /**
     * 
     * @return String
     */
    public String getErrorMessage() {
        return errorMessage;
    }
}
